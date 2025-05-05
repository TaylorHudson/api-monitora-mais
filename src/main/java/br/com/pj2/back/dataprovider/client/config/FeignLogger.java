package br.com.pj2.back.dataprovider.client.config;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignLogger extends Logger {

    @Override
    protected void log(final String configKey, final String format, final Object... args) {
        log.info("{} {}", configKey, String.format(format, args));
    }

    @Override
    protected void logRequest(final String configKey, final Level logLevel, final Request request) {
        log(configKey, "---> %s %s HTTP/1.1", request.httpMethod().name(), request.url());

        log(configKey, "Headers: %s", request.headers());
        if (hasRequestBody(request)) {
            String body = new String(request.body(), StandardCharsets.UTF_8);
            body = sanitizeBody(body);
            log(configKey, "%s", body);
        }

        log(configKey, "---> END HTTP (%s-byte body)", request.length());
    }

    @Override
    protected Response logAndRebufferResponse(final String configKey, final Level logLevel, final Response response, final long elapsedTime) throws IOException {
        log(configKey, "<--- HTTP/1.1 %s %s (%sms)", response.status(), getResponseReason(response), elapsedTime);

        if (hasResponseBody(response.body())) {
            byte[] body = Util.toByteArray(response.body().asInputStream());
            log(configKey, "%s", Util.decodeOrDefault(body, Util.UTF_8, "Binary data"));
            log(configKey, "<--- END HTTP (%s-byte body)", body.length);
            return response.toBuilder().body(body).build();
        }

        log(configKey, "<--- END HTTP (0-byte body)");
        return response;
    }

    private static boolean hasRequestBody(Request request) {
        return request.body() != null && request.length() > 0;
    }

    private static boolean hasResponseBody(Response.Body responseBody) {
        return responseBody != null;
    }

    private static String getResponseReason(Response response) {
        return response.reason() != null ? response.reason() : "";
    }

    private static String sanitizeBody(String body) {
        return body.replaceAll("(?i)(\"password\"\\s*:\\s*\")[^\"]*(\")", "$1********$2");
    }

}