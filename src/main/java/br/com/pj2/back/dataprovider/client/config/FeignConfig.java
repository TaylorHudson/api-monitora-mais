package br.com.pj2.back.dataprovider.client.config;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;

public class FeignConfig extends FeignClientsConfiguration {

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
}
