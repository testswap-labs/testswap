package com.testswap.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import com.testswap.api.web.filters.ApiKeyFilter;
import com.testswap.api.web.filters.RateLimitFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${TESTSWAP_API_KEY:}")
    private String apiKey;

    @Value("${TESTSWAP_RATE_LIMIT_PER_MIN:60}")
    private int rateLimitPerMin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration() {
        FilterRegistrationBean<ApiKeyFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new ApiKeyFilter(apiKey));
        reg.addUrlPatterns("/*");
        reg.setOrder(1);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration() {
        FilterRegistrationBean<RateLimitFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new RateLimitFilter(rateLimitPerMin));
        reg.addUrlPatterns("/*");
        reg.setOrder(2);
        return reg;
    }
}
