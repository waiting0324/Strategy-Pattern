package com.waiting.strategy.handler.impl;

import com.waiting.strategy.anno.OrderHandlerType;
import com.waiting.strategy.domain.Order;
import com.waiting.strategy.handler.OrderHandler;

@OrderHandlerType(source = "mobile", payMethod = "wechat")
public class MobileOrderHandler implements OrderHandler {
    @Override
    public void handle(Order order) {
        System.out.println("處理 手機端 WeChat 付款訂單");
    }
}
