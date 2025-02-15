package com.example.doantotnghiep_tranhuytung.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.ErrorResponse;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * Cấu hình CORS và WebMvc cho ứng dụng.
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Cấu hình CORS để cho phép frontend (React/Vue/Angular, v.v.) gọi API từ backend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho tất cả các endpoint
                .allowedOrigins("http://localhost:3000")  // Cho phép frontend chạy trên cổng 3000 gọi API
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP được phép
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization") // Các headers được phép
                .allowCredentials(true) // Cho phép gửi cookie, token đăng nhập
                .maxAge(3600); // Thời gian cache CORS (tính bằng giây)
    }

    /**
     * Cấu hình cách Spring MVC xử lý mapping URL.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        WebMvcConfigurer.super.configurePathMatch(configurer);
    }

    /**
     * Cấu hình cách Spring xử lý các định dạng dữ liệu đầu vào/đầu ra.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        WebMvcConfigurer.super.configureContentNegotiation(configurer);
    }

    /**
     * Cấu hình hỗ trợ xử lý bất đồng bộ.
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        WebMvcConfigurer.super.configureAsyncSupport(configurer);
    }

    /**
     * Cấu hình để Spring có thể xử lý các request tĩnh (như hình ảnh, CSS, JS).
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    /**
     * Cấu hình CORS và xử lý lỗi API.
     */
    @Override
    public void addErrorResponseInterceptors(List<ErrorResponse.Interceptor> interceptors) {
        WebMvcConfigurer.super.addErrorResponseInterceptors(interceptors);
    }

    /**
     * Cấu hình các bộ chuyển đổi dữ liệu HTTP.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    /**
     * Mở rộng các bộ chuyển đổi dữ liệu HTTP nếu cần.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }

    /**
     * Cấu hình bộ xử lý lỗi tùy chỉnh.
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        WebMvcConfigurer.super.configureHandlerExceptionResolvers(resolvers);
    }

    /**
     * Mở rộng bộ xử lý lỗi nếu cần.
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        WebMvcConfigurer.super.extendHandlerExceptionResolvers(resolvers);
    }

    /**
     * Lấy trình xác thực dữ liệu nếu có.
     */
    @Override
    public Validator getValidator() {
        return WebMvcConfigurer.super.getValidator();
    }

    /**
     * Lấy trình giải mã mã lỗi.
     */
    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return WebMvcConfigurer.super.getMessageCodesResolver();
    }
}
