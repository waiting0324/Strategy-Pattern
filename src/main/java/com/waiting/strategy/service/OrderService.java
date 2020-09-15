package com.waiting.strategy.service;

import com.waiting.strategy.anno.OrderHandlerType;
import com.waiting.strategy.domain.Order;
import com.waiting.strategy.handler.OrderHandler;
import com.waiting.strategy.anno.OrderHandlerTypeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    // 註解類 與 策略實現類 映射結果
    private Map<OrderHandlerType, OrderHandler> orderHandleMap;

    @Autowired
    public void setOrderHandleMap(List<OrderHandler> orderHandlers) { // 注入各種類型的訂單處理類
        // 將 註解類 與 策略實現類 進行映射
        orderHandleMap = orderHandlers.stream().collect(
                Collectors.toMap(orderHandler ->
                        // 找出 策略實現類 上的 OrderHandlerType 註解
                        AnnotationUtils.findAnnotation(orderHandler.getClass(), OrderHandlerType.class),
                        // 將 value(策略實現類) 進行處理
                        v -> v,
                        // 如果 key(OrderHandlerType註解) 有對應到多個 value(策略實現類) 則選擇 v1(第一個)
                        (v1, v2) -> v1));
    }

    public void orderService(Order order) {
        // ...一些前置處理

        // 根據 傳入的訂單屬性 找到對應的 註解類
        OrderHandlerType orderHandlerType = new OrderHandlerTypeImpl(order.getSource(), order.getPayMethod());
        // 根據 註解類 找到 策略實現類
        OrderHandler orderHandler = orderHandleMap.get(orderHandlerType);
        // 調用策略實現類方法
        orderHandler.handle(order);

        // ...一些後置處理
    }
}
