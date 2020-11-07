package com.dapr.service.controller;

/**
 * @author Zhang_Xiang
 * @since 2020/11/7 17:24:26
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

/**
 * SpringBoot Controller to handle input binding.
 * @author zhangxiang
 */
@RestController
public class HelloController {

    /**
     * Json serializer/deserializer.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Format to output date and time.
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Handles a dapr service invocation endpoint on this app.
     * @param body The body of the http message.
     * @param headers The headers of the http message.
     * @return A message containing the time.
     */
    @PostMapping(path = "/say")
    public Mono<String> handleMethod(@RequestBody(required = false) byte[] body,
                                     @RequestHeader Map<String, String> headers) {
        return Mono.fromSupplier(() -> {
            try {
                String message = body == null ? "" : new String(body, StandardCharsets.UTF_8);

                Calendar utcNow = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                String utcNowAsString = DATE_FORMAT.format(utcNow.getTime());

                String metadataString = headers == null ? "" : OBJECT_MAPPER.writeValueAsString(headers);

                // Handles the request by printing message.
                System.out.println(
                        "Server: " + message + " @ " + utcNowAsString + " and metadata: " + metadataString);

                return utcNowAsString;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}

