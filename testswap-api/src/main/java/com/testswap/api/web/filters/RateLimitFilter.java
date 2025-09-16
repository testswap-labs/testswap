package com.testswap.api.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Very simple per-client-IP rate limiter: allow N requests per minute.
 * Intended for demo/dev only.
 */
public class RateLimitFilter implements Filter {
    private final int limitPerMinute;

    private static class Counter {
        int count;
        long windowStartEpochMinute;
    }

    private final Map<String, Counter> counters = new ConcurrentHashMap<>();

    public RateLimitFilter(int limitPerMinute) {
        this.limitPerMinute = Math.max(1, limitPerMinute);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String ip = getClientIp(req);
        long nowMinute = Instant.now().getEpochSecond() / 60;

        Counter c = counters.computeIfAbsent(ip, k -> new Counter());
        synchronized (c) {
            if (c.windowStartEpochMinute != nowMinute) {
                c.windowStartEpochMinute = nowMinute;
                c.count = 0;
            }
            c.count++;
            if (c.count > limitPerMinute) {
                resp.setStatus(429);
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write("{\"error\":\"rate_limited\",\"message\":\"too many requests\"}");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest req) {
        String h = req.getHeader("X-Forwarded-For");
        if (h != null && !h.isEmpty()) {
            int idx = h.indexOf(',');
            return idx > 0 ? h.substring(0, idx).trim() : h.trim();
        }
        return req.getRemoteAddr();
    }
}
