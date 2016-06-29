package com.yyqian.ratelimiter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yyqian on 6/29/16.
 *
 */
@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException extends RuntimeException {

  public RateLimitException() {
    super("Rate limit exceeded.");
  }

  public RateLimitException(String msg) {
    super(msg);
  }

}
