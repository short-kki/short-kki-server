package com.shortkki.global.config;

import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        var settings = new ClientHttpRequestFactorySettings(
                ClientHttpRequestFactorySettings.Redirects.FOLLOW_WHEN_POSSIBLE,
                Duration.ofSeconds(5),
                Duration.ofSeconds(10),
                null
        );

        var requestFactory = ClientHttpRequestFactoryBuilder
                .detect()
                .build(settings);

        return builder.requestFactory(requestFactory).build();
    }
}
