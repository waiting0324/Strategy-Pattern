package com.waiting.strategy.handler;

import com.waiting.strategy.domain.Order;

public interface OrderHandler {

    void handle(Order order);

}
