package com.example.short_kki.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {

  private Map<String, Provider> provider = new HashMap<>();

  @Getter
  @Setter
  public static class Provider {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String tokenUri;
    private String userInfoUri;
  }

  public Provider getProvider(String providerName) {
    Provider p = provider.get(providerName.toLowerCase());
    if (p == null) {
      throw new IllegalArgumentException("Unsupported OAuth provider: " + providerName);
    }
    return p;
  }
}
