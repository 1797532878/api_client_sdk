package com.api.api_client_sdk;

import com.api.api_client_sdk.client.CApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("capi.clcient")
@ComponentScan
@Data
public class CApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public CApiClient cApiClient() {
        return new CApiClient(accessKey,secretKey);
    }

}
