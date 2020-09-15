package com.waiting.strategy.handler.impl;

import com.waiting.strategy.anno.OrderHandlerType;
import com.waiting.strategy.domain.Order;
import com.waiting.strategy.handler.OrderHandler;

@OrderHandlerType(source = "pc", payMethod = "creditCard")
public class PCOrderHandler implements OrderHandler {
    @Override
    public void handle(Order order) {
        System.out.println("處理 PC端 信用卡 付款訂單");
    }
}
