# Rate Limiter

This is an annotation-based Rate Limiter for Spring Controller Methods based on Spring AOP.

## Usage

1. Annotate a Controller method with `@RateLimit`.
2. Because the user is identified by IP address, the `HttpServletRequest` parameter is required for the method.

```
@RestController
public class HelloController {

  @RateLimit(limit = 3, duration = 60, unit = TimeUnit.SECONDS)
  @RequestMapping("/hello")
  public String hello(HttpServletRequest request) {
    return "Hello world!";
  }

}
```

If the client exceeds the rate limit, the response would be:

```
{
  "timestamp": 1467192416435,
  "status": 429,
  "error": "Too Many Requests",
  "message": "Rate limit exceeded.",
  "path": "/hello"
}
```