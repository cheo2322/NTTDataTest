package com.nttdata_test.person.service.impl;

import com.nttdata_test.person.entity.Client;
import com.nttdata_test.person.entity.Person;
import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.mapper.PersonMapper;
import com.nttdata_test.person.repository.ClientRepository;
import com.nttdata_test.person.repository.PersonRepository;
import com.nttdata_test.person.service.ClientService;
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
        .flatMap(
            person -> {
              Client client = PersonMapper.dtoToClient(clientDto);
              client.setPerson(person);

              return clientRepository.save(client);
            })
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  Person person = PersonMapper.dtoToPerson(clientDto);
                  personRepository
                      .save(person)
                      .map(
                          personDB -> {
                            Client client = PersonMapper.dtoToClient(clientDto);
                            client.setPerson(personDB);

                            return clientRepository.save(client);
                          });

                  return null;
                }))
        .then();
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
