package com.webank.blockchain.dca.gateway.aspect;

import java.lang.annotation.*;

/**
 * UseTime @Description: UseTime
 *
 * @author maojiayu
 * @data Jul 5, 2019 5:57:29 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface UseTime {
    String value() default "";
}
