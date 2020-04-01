package edu.nf.shopping.order.entity;

import edu.nf.shopping.admin.entity.StaffInfo;
import edu.nf.shopping.user.entity.UserInfo;
import edu.nf.shopping.warehouse.entity.CargoInfo;
import edu.nf.shopping.warehouse.entity.City;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lishun
 * @date 2020/3/11
 * 订单信息表
 */
public class OrderInfo {
    /**
     * 时间戳、主键
     */
    private String orderId;
    /**
     * 货物编号
     */
    private CargoInfo cargo;
    /**
     * 购买价格
     */
    private BigDecimal buyPrice;
    /**
     * 购买优惠
     */
    private BigDecimal cheapPrice;
    /**
     * 运送费用
     */
    private BigDecimal transportPrice;
    /**
     * 购买时间
     */
    private Date buyTime;
    /**
     * 客服人员
     */
    private StaffInfo buyAdmin;
    /**
     * 用户
     */
    private UserInfo buyUser;
    /**
     * 收货地区
     */
    private City collectRegion;
    /**
     * 收货详细地址
     */
    private String collectAddress;
    /**
     * 买家备注
     */
    private String buyRemark;
    /**
     * 订单备注
     */
    private String orderRemark;
    /**
     * 订单状态
     */
    private OrderState orderState;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getCheapPrice() {
        return cheapPrice;
    }

    public void setCheapPrice(BigDecimal cheapPrice) {
        this.cheapPrice = cheapPrice;
    }

    public BigDecimal getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(BigDecimal transportPrice) {
        this.transportPrice = transportPrice;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public StaffInfo getBuyAdmin() {
        return buyAdmin;
    }

    public void setBuyAdmin(StaffInfo buyAdmin) {
        this.buyAdmin = buyAdmin;
    }

    public UserInfo getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(UserInfo buyUser) {
        this.buyUser = buyUser;
    }

    public City getCollectRegion() {
        return collectRegion;
    }

    public void setCollectRegion(City collectRegion) {
        this.collectRegion = collectRegion;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }

    public String getBuyRemark() {
        return buyRemark;
    }

    public void setBuyRemark(String buyRemark) {
        this.buyRemark = buyRemark;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public CargoInfo getCargo() {
        return cargo;
    }

    public void setCargo(CargoInfo cargo) {
        this.cargo = cargo;
    }
}
