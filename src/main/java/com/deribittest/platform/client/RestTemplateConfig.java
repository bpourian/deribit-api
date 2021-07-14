package com.deribittest.platform.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Value("${deribit.base.url}")
  private String deribitBaseUrl;

  @Value("${deribit.client.id}")
  private String clientId;

  @Value("${deribit.client.secret}")
  private String clientSecret;

  public RestTemplate getRestTemplateWithBasicAuthentication() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add((request, body, clientHttpRequestExecution) -> {
      HttpHeaders headers = request.getHeaders();
      if (!headers.containsKey("Authorization")) {
        byte[] data = String.format("%s:%s", clientId, clientSecret)
                            .getBytes(StandardCharsets.UTF_8);
        request.getHeaders()
               .add("Authorization", "Basic " + Base64.getEncoder().encodeToString(data));
      }
      return clientHttpRequestExecution.execute(request, body);
    });
    return restTemplate;
  }
}
