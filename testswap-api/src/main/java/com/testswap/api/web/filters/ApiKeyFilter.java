package com.testswap.api.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple API key filter. If TESTSWAP_API_KEY is empty, auth is bypassed.
 * Checks X-API-Key header. Allows common public paths.
 */
public class ApiKeyFilter implements Filter {
    private final String expectedApiKey;
    private final Set<String> publicPrefixes = new HashSet<>(Arrays.asList(
            "/healthz", "/api/ping", "/swagger", "/v3/api-docs", "/", "/version"
    ));

    public ApiKeyFilter(String expectedApiKey) {
        this.expectedApiKey = expectedApiKey == null ? "" : expectedApiKey.trim();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();
        if (isPublic(path) || expectedApiKey.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        String provided = req.getHeader("X-API-Key");
        if (provided != null && provided.equals(expectedApiKey)) {
            chain.doFilter(request, response);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"error\":\"unauthorized\"}");
        }
    }

    private boolean isPublic(String path) {
        if (path == null || path.isEmpty()) return true;
        String p = path.toLowerCase();
        for (String prefix : publicPrefixes) {
            if (p.startsWith(prefix)) return true;
        }
        // Swagger assets under /swagger-ui/
        if (p.startsWith("/swagger-ui")) return true;
        return false;
    }
}
