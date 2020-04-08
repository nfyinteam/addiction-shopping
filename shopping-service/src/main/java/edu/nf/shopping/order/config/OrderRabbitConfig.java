package edu.nf.shopping.order.config;

import edu.nf.shopping.config.RabbitConfig;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Achine
 * @date 2020/4/8
 */
@Configuration
public class OrderRabbitConfig {

    public static final String ORDER_DETAILS_CREATE_QUEUE = "order.details.create.queue";
    public static final String ORDER_INIT_QUEUE = "order.init.queue";
    public static final String ORDER_DESTROY_QUEUE = "order.destroy.queue";

    public static final String ORDER_DETAILS_CREATE_ROUTER_KEY = "order.details.create";
    public static final String ORDER_INIT_ROUTER_KEY = "order.init";
    public static final String ORDER_DESTROY_ROUTER_KEY = "order.destroy";

    /**
     * 装配消息队列
     */

    @Bean
    public Queue orderDetailsCreateQueue(){
        return new Queue(ORDER_DETAILS_CREATE_QUEUE, true);
    }

    @Bean
    public Queue orderInitQueue(){
        return new Queue(ORDER_INIT_QUEUE, true);
    }

    @Bean
    public Queue orderDestroyQueue(){
        return new Queue(ORDER_DESTROY_QUEUE, true);
    }

    /**
     * 将queue绑定到exchange交换机
     */

    @Bean
    public Binding orderDetailsCreateBinding(@Qualifier("orderDetailsCreateQueue") Queue queue,
                                             @Qualifier("exchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_DETAILS_CREATE_ROUTER_KEY).noargs();
    }

    @Bean
    public Binding orderInitBinding(@Qualifier("orderInitQueue") Queue queue,
                                    @Qualifier("exchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_INIT_ROUTER_KEY).noargs();
    }

    @Bean
    public Binding orderDestroyBinding(@Qualifier("orderDestroyQueue") Queue queue,
                                    @Qualifier("delayExchange") CustomExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_DESTROY_ROUTER_KEY).noargs();
    }
}
