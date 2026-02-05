package com.sharif.voucher_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfig {
    private String version;
    private final Cors cors = new Cors();

    @Data
    public static class Cors {
        private String origins;
    }
}
