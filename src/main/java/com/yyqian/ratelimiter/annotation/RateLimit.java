package com.yyqian.ratelimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Created by yyqian on 6/29/16.
 * 限制访问次数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimit {
  /**
   * @return 限制的次数
   */
  int limit() default Integer.MAX_VALUE;

  /**
   * @return 限制的时间, 默认为一分钟
   */
  long duration() default 1;

  /**
   * @return 时间单位
   */
  TimeUnit unit() default TimeUnit.MINUTES;
}
