package com.yyqian.ratelimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RatelimiterApplication {

  /**
   * 加载所有的 Beans.
   */
  public static void main(String[] args) {
    SpringApplication.run(RatelimiterApplication.class, args);
  }

}
