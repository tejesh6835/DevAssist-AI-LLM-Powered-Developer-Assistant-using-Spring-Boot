package org.ai.devassistant.ratelimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ip = request.getRemoteAddr();

        boolean allowed = rateLimiterService.isAllowed(ip);

        if (!allowed) {
            response.setStatus(429);
            response.getWriter().write("""
            {
              "status": "FAILED",
              "message": "Too many requests. Please try again later."
            }
            """);
            return false;
        }

        return true;
    }
}