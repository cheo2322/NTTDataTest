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
    this.webClient = WebClient.builder().baseUrl("http://127.0.0.1:8080").build();
  }

  public Mono<ClientDto> getClient(Long id) {
    return webClient
        .get()
        .uri("/" + id)
        .retrieve()
        .bodyToMono(ClientDto.class)
        .onErrorResume(
            WebClientResponseException.class,
            e -> Mono.error(new ExternalServiceException("Error with Client service.")));
  }
}
