package com.waiting.strategy;

import com.waiting.strategy.domain.Order;
import com.waiting.strategy.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StrategyApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {

        Order order = new Order();
        order.setSource("pc");
        order.setPayMethod("creditCard");

        orderService.orderService(order);

    }

}
