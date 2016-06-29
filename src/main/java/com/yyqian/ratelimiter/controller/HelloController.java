package com.yyqian.ratelimiter.controller;

import com.yyqian.ratelimiter.annotation.RateLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by yyqian on 6/29/16.
 * 测试用的 Controller
 */
@RestController
public class HelloController {

  @RateLimit(limit = 3, duration = 60, unit = TimeUnit.SECONDS)
  @RequestMapping("/hello")
  public String hello(HttpServletRequest request) {
    return "hello: " + request.getRemoteAddr() + " | " + request.getRemoteHost();
  }

  // 如果参数列表缺少 HttpServletRequest, 日志会记录错误信息
  //@RateLimit(limit = 3)
  @RequestMapping("/hello2")
  public String hello2() {
    return "hello2";
  }
}
