package com.deribittest.platform.client;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Scope("singleton")
@Service
public class ClientUtilityService {

  @Value("${deribit.base.url}")
  private String deribitBaseUrl;

  private final RestTemplateConfig restTemplateConfig;

  @Autowired
  public ClientUtilityService(RestTemplateConfig restTemplateConfig) {
    this.restTemplateConfig = restTemplateConfig;
  }

  public <T> T getForObject(String path, Map<String, String> params, Class<T> responseType) {
    RestTemplate restTemplate = restTemplateConfig.getRestTemplateWithBasicAuthentication();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(deribitBaseUrl)
                                                       .path(path);
    if (!params.isEmpty()) {
      params.forEach((k, v) -> {
        if (!v.isEmpty()) {
          builder.queryParam(k, v);
        }
      });
    }
    return restTemplate.getForObject(builder.toUriString(), responseType);
  }
}
