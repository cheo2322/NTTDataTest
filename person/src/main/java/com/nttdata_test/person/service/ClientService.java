package com.nttdata_test.person.service;

import com.nttdata_test.person.entity.dto.ClientDto;
import reactor.core.publisher.Mono;

/** Service for CRUD operations and any functionality related with Client entity. */
public interface ClientService {

  /**
   * Create a new Client.
   *
   * @param clientDto with information for the Client.
   * @return a void response.
   */
  Mono<Void> createClient(ClientDto clientDto);

  /**
   * Get a Client.
   *
   * @param clientId to find the Client.
   * @return a ClientDto with the Client's information.
   */
  Mono<ClientDto> getClient(String clientId);

  /**
   * Update a Client.
   *
   * @param clientId to find the Client.
   * @param clientDto with the Client's new information.
   * @return a void response.
   */
  Mono<Void> updateClient(String clientId, ClientDto clientDto);

  /**
   * Deactivate a Client.
   *
   * @param clientId to find the Client
   * @return a void response.
   */
  Mono<Void> deleteClient(Long clientId);
}
