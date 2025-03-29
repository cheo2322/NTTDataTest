package com.nttdata_test.account.web;

import com.nttdata_test.account.entity.response.ClientDto;
import com.nttdata_test.account.handler.ex.ExternalServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class ClientWebClient {

  private final WebClient webClient;

  public ClientWebClient() {
    this.webClient = WebClient.builder().baseUrl("http://person:8080/nttdata/test/v1").build();
  }

  public Mono<ClientDto> getClient(String clientId) {
    return webClient
        .get()
        .uri("/clients/" + clientId)
        .retrieve()
        .bodyToMono(ClientDto.class)
        .onErrorResume(
            WebClientResponseException.class,
            e -> Mono.error(new ExternalServiceException("Error with Client service.")));
  }
}
