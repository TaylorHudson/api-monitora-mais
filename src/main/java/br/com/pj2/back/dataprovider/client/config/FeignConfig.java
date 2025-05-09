package br.com.pj2.back.dataprovider.client.config;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.UnauthorizedException;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

public class FeignConfig extends FeignClientsConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignConfig.CustomFeignErrorDecoder();
    }

    @Bean
    public Logger logger() {
        return new FeignLogger();
    }

    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    RequestInterceptor requestHeaderInterceptor() {
        return requestTemplate -> {
            if (Request.HttpMethod.GET != requestTemplate.request().httpMethod()) {
                requestTemplate.header("Content-Type", "application/json; charset=utf-8");
            }
            requestTemplate.header("Accept", "application/json; charset=utf-8");
        };
    }

    @Component
    public static class CustomFeignErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            HttpStatus status = HttpStatus.valueOf(response.status());
            return switch (status) {
                case UNAUTHORIZED, TOO_MANY_REQUESTS -> new UnauthorizedException(ErrorCode.INVALID_CREDENTIALS);
                default -> defaultDecoder.decode(methodKey, response);
            };
        }
    }

}
