package edu.nf.shopping.order.service.impl;

import com.rabbitmq.client.Channel;
import edu.nf.shopping.comment.entity.Praise;
import edu.nf.shopping.config.RabbitConfig;
import edu.nf.shopping.goods.entity.SkuAllInfo;
import edu.nf.shopping.goods.entity.SkuInfo;
import edu.nf.shopping.goods.entity.SkuRelation;
import edu.nf.shopping.goods.exception.SkuInfoException;
import edu.nf.shopping.goods.service.SkuInfoService;
import edu.nf.shopping.order.dao.OrderDao;
import edu.nf.shopping.order.entity.OrderDetails;
import edu.nf.shopping.order.entity.OrderInfo;
import edu.nf.shopping.order.exception.OrderException;
import edu.nf.shopping.order.service.InitOrderDetailsService;
import edu.nf.shopping.order.service.InitOrderInfoService;
import edu.nf.shopping.order.service.OrderDetailsService;
import edu.nf.shopping.user.dao.UserInfoDao;
import edu.nf.shopping.user.entity.UserInfo;
import edu.nf.shopping.util.TimeMillisUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Achine
 * @date 2020/4/5
 */
@Service("initOrderInfoService")
public class InitOrderInfoServiceImpl implements InitOrderInfoService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 初始化订单
     * @param userId 用户编号
     * @param orderDetails 订单明细集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = {OrderException.class})
    public OrderInfo initOrderInfo(String userId,  List<OrderDetails> orderDetails) {
        try {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(TimeMillisUtils.getCurrentTimeMillisName());
            if(userId == null || userId == ""){
                throw new OrderException("该用户不能为空");
            }
            UserInfo userInfo = userInfoDao.getUserInfo(userId);
            if(userInfo == null){
                throw new OrderException("该用户不能为空");
            }
            orderInfo.setBuyUser(userInfo);
            orderInfo.setOrderState("提交中");
            orderInfo.setCreateTime(new Date());
            orderInfo.setCheapPrice(new BigDecimal(0));
            orderInfo.setTransportPrice(new BigDecimal(0));
            orderInfo.setDel(false);
            BigDecimal buyPrice = new BigDecimal(0);
            List<OrderDetails> detailsList = new ArrayList<>();
            for (OrderDetails details : orderDetails){
                OrderDetails d = new OrderDetails();
                SkuAllInfo skuAllInfo = skuInfoService.getSkuAllInfoBySkuId(details.getSkuId());
                SkuInfo skuInfo = skuAllInfo.getSkuInfo();
                System.out.println(skuInfo.getSkuId());
                System.out.println(skuInfo.getGood().getGoodsName());
                if(skuAllInfo == null){
                    throw new SkuInfoException("该sku不存在");
                }
                d.setOrderId(orderInfo.getOrderId());
                d.setSkuId(skuInfo.getSkuId());
                d.setSkuPrice(skuInfo.getSkuPrice());
                d.setSkuNum(details.getSkuNum());
                String attribute = "";
                int i = 0;
                for (SkuRelation relation : skuAllInfo.getSkuRelations()){
                    if (i != 0){
                        attribute += ",";
                    }
                    attribute += relation.getKey().getKeyName() + ":" + relation.getValue().getValueName();
                    i++;
                }
                d.setSkuAttribute(attribute);
                d.setGoodsId(skuInfo.getGood().getGoodsId());
                d.setGoodsName(skuInfo.getGood().getGoodsName());
                d.setGoodsFile(skuAllInfo.getImgsInfo().getImgFile());
                //校验库存，同步库存
                if(skuInfo.getSkuStock() == 0 || skuInfo.getSkuStock() - d.getSkuNum() < 0){
                    throw new SkuInfoException("库存不足");
                }
                skuInfo.setSkuStock(skuInfo.getSkuStock() - d.getSkuNum());
                skuInfoService.updateSkuInfo(skuInfo);
                skuAllInfo.setSkuInfo(skuInfo);
                //同步缓存
                if(redisTemplate.opsForValue().get("skuInfoCache::" + skuInfo.getSkuId()) != null){
                    redisTemplate.opsForValue().set("skuInfoCache::" + skuInfo.getSkuId(), skuAllInfo);
                }
                BigDecimal buy = d.getSkuPrice().multiply(new BigDecimal(d.getSkuNum()));
                System.out.println(buy.toString());
                buyPrice = buyPrice.add(buy);
                detailsList.add(d);
            }
            System.out.println(buyPrice.toString());
            orderInfo.setBuyPrice(buyPrice);
            orderInfo.setOrderDetails(detailsList);
            orderDao.addOrderInfo(orderInfo);
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(orderInfo.getOrderId());
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "order.init", detailsList, correlationData);
            return orderInfo;
        }catch (Exception e){
            throw new OrderException(e);
        }
    }
}
