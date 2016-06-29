package com.yyqian.ratelimiter.exception;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * Created by yyqian on 6/29/16.
 * 继承了默认的 DefaultErrorAttributes, 移除其中的 exception 信息, 可以根据需要移除更多信息
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
                                                boolean includeStackTrace) {
    Map<String, Object> errorAttributes =
        super.getErrorAttributes(requestAttributes, includeStackTrace);
    errorAttributes.remove("exception");
    return errorAttributes;
  }

}
