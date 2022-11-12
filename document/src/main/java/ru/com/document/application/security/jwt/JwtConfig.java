package ru.com.document.application.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Getter
@Setter
public class JwtConfig {

    private Jwt jwt;

    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private long ttl;
        private String subject;
        private Keys keys;

        @Getter
        @Setter
        public static class Keys {
            private String user;
            private String roles;
            private String name;
        }
    }

}
