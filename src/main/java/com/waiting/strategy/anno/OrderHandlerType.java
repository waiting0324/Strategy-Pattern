package com.waiting.strategy.anno;


import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author Waiting
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service // 讓被此註解標註的 Class 可以自動被注入到 IOC 容器中
public @interface OrderHandlerType {

    String source();

    String payMethod();

}
