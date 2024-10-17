package com.webank.blockchain.dca.gateway.aspect;

import java.lang.annotation.*;


@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Record {
    String type() default "";
}
