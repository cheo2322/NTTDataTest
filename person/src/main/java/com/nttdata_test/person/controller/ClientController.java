package com.nttdata_test.person.controller;

import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/nttdata/test/v1/clients")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createClient(@RequestBody ClientDto clientDto) {
    return clientService.createClient(clientDto);
  }

  @GetMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ClientDto> getClient(@PathVariable String clientId) {
    return clientService.getClient(clientId);
  }

  @PutMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> updateClient(@PathVariable Long clientId, @RequestBody ClientDto clientDto) {
    return clientService.updateClient(clientId, clientDto);
  }

  @DeleteMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> deleteClient(@PathVariable Long clientId) {
    return clientService.deleteClient(clientId);
  }
}
