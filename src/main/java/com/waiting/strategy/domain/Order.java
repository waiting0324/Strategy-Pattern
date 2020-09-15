package com.waiting.strategy.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    /**
     * 訂單來源
     */
    private String source;
    /**
     * 付款方式
     */
    private String payMethod;
    /**
     * 訂單編號
     */
    private String code;
    /**
     * 訂單金額
     */
    private BigDecimal amount;

}
