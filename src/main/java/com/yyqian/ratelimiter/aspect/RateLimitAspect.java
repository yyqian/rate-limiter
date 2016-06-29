package com.yyqian.ratelimiter.aspect;

import com.yyqian.ratelimiter.annotation.RateLimit;
import com.yyqian.ratelimiter.exception.RateLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yyqian on 6/29/16.
 * 处理 RateLimit
 * 根据 IP 和访问的地址来限制访问次数
 */
@Component
@Aspect
public class RateLimitAspect {
  private static final Logger logger =
      LoggerFactory.getLogger(RateLimitAspect.class);

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Pointcut("@annotation(rateLimit)")
  private void annotatedWithRateLimit(RateLimit rateLimit) {}

  @Pointcut("@within(org.springframework.stereotype.Controller)"
      + " || @within(org.springframework.web.bind.annotation.RestController)")
  private void controllerMethods() {}

  /**
   * 处理 RateLimit 标注的 controller 包下面的方法.
   */
  @Before("controllerMethods() && annotatedWithRateLimit(rateLimit)")
  public void rateLimitProcess(final JoinPoint joinPoint,
                               RateLimit rateLimit) throws RateLimitException {
    logger.debug("触发 rateLimitProcess()");
    HttpServletRequest request = getRequest(joinPoint.getArgs());
    if (request == null) {
      logger.error(String.format("方法[%s]中缺失 HttpServletRequest 参数",
                                 joinPoint.getSignature().toShortString()));
      return;
    }
    String ip = request.getRemoteHost();
    String url = request.getRequestURI();
    String key = String.format("req:lim:%s:%s", url, ip);
    long count = redisTemplate.opsForValue().increment(key, 1);
    logger.debug(String.format("[Redis] %s = %s", key, count));
    if (count == 1) {
      redisTemplate.expire(key, rateLimit.duration(), rateLimit.unit());
    }
    if (count > rateLimit.limit()) {
      logger.warn(String.format("用户IP[%s]短时间内连续[%d]次访问地址[%s], 超过了限定的次数[%d]",
                                ip, count, url, rateLimit.limit()));
      throw new RateLimitException();
    }
  }

  private HttpServletRequest getRequest(Object[] args) {
    for (Object arg : args) {
      if (arg instanceof HttpServletRequest) {
        return (HttpServletRequest)arg;
      }
    }
    return null;
  }
}
