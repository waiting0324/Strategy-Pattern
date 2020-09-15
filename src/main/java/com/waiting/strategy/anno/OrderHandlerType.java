package com.waiting.strategy.anno;


import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author Waiting
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface OrderHandlerType {

    String source();

    String payMethod();

}
