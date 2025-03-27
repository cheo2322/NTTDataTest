package com.nttdata_test.person.controller;

import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

  @PatchMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> updateClient(@PathVariable String clientId, @RequestBody ClientDto clientDto) {
    return clientService.updateClient(clientId, clientDto);
  }

  @DeleteMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> deleteClient(@PathVariable Long clientId) {
    return clientService.deleteClient(clientId);
  }
}
