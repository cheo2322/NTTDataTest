package com.nttdata_test.person.service.impl;

import com.nttdata_test.person.entity.Client;
import com.nttdata_test.person.entity.Person;
import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.handler.ex.DuplicateEntityException;
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

  private Mono<Client> saveClient(ClientDto clientDto, Person personDB) {
    Client client = mapClient(clientDto, personDB);

    return clientRepository
        .save(client)
        .onErrorResume(
            DuplicateKeyException.class,
            e -> Mono.error(new DuplicateEntityException("Client already exists.")));
  }

  private Mono<Client> savePersonAndClient(ClientDto clientDto) {
    Person person = PersonMapper.dtoToPerson(clientDto);
    return personRepository
        .save(person)
        .flatMap(personDB -> clientRepository.save(mapClient(clientDto, personDB)));
  }

  private Client mapClient(ClientDto clientDto, Person person) {
    Client client = PersonMapper.dtoToClient(clientDto);
    client.setPersonId(person.getId());

    return client;
  }

  @Override
  public Mono<ClientDto> getClient(String clientId) {
    return null;
  }

  @Override
  public Mono<Void> updateClient(Long clientId, ClientDto clientDto) {
    return null;
  }

  @Override
  public Mono<Void> deleteClient(Long clientId) {
    return null;
  }
}
