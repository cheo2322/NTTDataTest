package com.nttdata_test.person.service.impl;

import com.nttdata_test.person.entity.Client;
import com.nttdata_test.person.entity.Person;
import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.handler.ex.DuplicateEntityException;
import com.nttdata_test.person.handler.ex.EntityNotFoundException;
import com.nttdata_test.person.mapper.PersonMapper;
import com.nttdata_test.person.repository.ClientRepository;
import com.nttdata_test.person.repository.PersonRepository;
import com.nttdata_test.person.service.ClientService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

  private final PersonRepository personRepository;
  private final ClientRepository clientRepository;

  public ClientServiceImpl(PersonRepository personRepository, ClientRepository clientRepository) {
    this.personRepository = personRepository;
    this.clientRepository = clientRepository;
  }

  @Override
  public Mono<Void> createClient(ClientDto clientDto) {
    return personRepository
        .findByIdentification(clientDto.identification())
        .flatMap(personDB -> saveClient(clientDto, personDB))
        .switchIfEmpty(Mono.defer(() -> savePersonAndClient(clientDto)))
        .then();
  }

  @Override
  public Mono<ClientDto> getClient(String clientId) {
    return clientRepository
        .findByClientId(clientId)
        .flatMap(
            client ->
                personRepository
                    .findById(client.getPersonId())
                    .map(person -> PersonMapper.clientDto(person, client))
                    .switchIfEmpty(Mono.empty()))
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Client not found.")));
  }

  @Override
  public Mono<Void> updateClient(Long clientId, ClientDto clientDto) {
    return null;
  }

  @Override
  public Mono<Void> deleteClient(Long clientId) {
    return null;
  }

  /**
   * Save {@link Client} only, in case the Person doesn't exist
   *
   * @param clientDto with the information from the Client.
   * @param personDB to add the Person ID to the Client.
   * @return a Mono containing the saved Client.
   */
  private Mono<Client> saveClient(ClientDto clientDto, Person personDB) {
    return clientRepository
        .save(mapClient(clientDto, personDB.getId()))
        .onErrorResume(
            DuplicateKeyException.class,
            e -> Mono.error(new DuplicateEntityException("Client already exists.")));
  }

  /**
   * Save {@link Client} and {@link Person}, when this last one doesn't exist.
   *
   * @param clientDto with the information from the Client.
   * @return a Mono containing the saved Client.
   */
  private Mono<Client> savePersonAndClient(ClientDto clientDto) {
    return personRepository
        .save(PersonMapper.dtoToPerson(clientDto))
        .flatMap(personDB -> this.saveClient(clientDto, personDB));
  }

  /**
   * Create the {@link Client} instance to be saved.
   *
   * @param clientDto with the information from the Client.
   * @param personId to relate the Client.
   * @return The Client entity to be saved.
   */
  private Client mapClient(ClientDto clientDto, Long personId) {
    Client client = PersonMapper.dtoToClient(clientDto);
    client.setPersonId(personId);

    return client;
  }
}
