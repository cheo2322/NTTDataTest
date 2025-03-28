package com.nttdata_test.person.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nttdata_test.person.TestHelper;
import com.nttdata_test.person.entity.Client;
import com.nttdata_test.person.entity.Person;
import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.entity.dto.Gender;
import com.nttdata_test.person.handler.ex.DuplicateEntityException;
import com.nttdata_test.person.handler.ex.EntityNotFoundException;
import com.nttdata_test.person.repository.ClientRepository;
import com.nttdata_test.person.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

  @Mock private PersonRepository personRepository;

  @Mock private ClientRepository clientRepository;

  @InjectMocks private ClientServiceImpl clientService;

  private Client client;
  private Person person;
  private ClientDto clientDto;

  @BeforeEach
  void setup() {
    client = TestHelper.buildClient();
    person = TestHelper.buildPerson();
    clientDto = TestHelper.buildClientDto();
  }

  @Test
  void shouldCreateClientAndReturnVoid_whenPersonExists() {
    // given
    when(personRepository.findByIdentification(anyString())).thenReturn(Mono.just(person));
    when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(client));

    // when
    Mono<Void> clientResponse = clientService.createClient(clientDto);

    // then
    StepVerifier.create(clientResponse).verifyComplete();

    verify(personRepository).findByIdentification(clientDto.identification());
    verify(clientRepository).save(any(Client.class));
  }

  @Test
  void shouldCreateClientAndPersonAndReturnVoid_whenPersonDoesNotExists() {
    // given
    when(personRepository.findByIdentification(anyString())).thenReturn(Mono.empty());
    when(personRepository.save(any(Person.class))).thenReturn(Mono.just(person));
    when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(client));

    // when
    Mono<Void> clientResponse = clientService.createClient(clientDto);

    // then
    StepVerifier.create(clientResponse).verifyComplete();

    verify(personRepository).findByIdentification(clientDto.identification());
    verify(personRepository).save(any(Person.class));
    verify(clientRepository).save(any(Client.class));
  }

  @Test
  void shouldThrowAnException_whenClientAlreadyExists_inCreateClient() {
    // given
    when(personRepository.findByIdentification(anyString())).thenReturn(Mono.just(person));
    when(clientRepository.save(any(Client.class)))
        .thenReturn(Mono.error(new DuplicateEntityException("Client already exists.")));

    // when
    Mono<Void> clientResponse = clientService.createClient(clientDto);

    // then
    StepVerifier.create(clientResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof DuplicateEntityException
                    && throwable.getMessage().equals("Client already exists."))
        .verify();

    verify(personRepository).findByIdentification(clientDto.identification());
    verify(clientRepository).save(any(Client.class));
  }

  @Test
  void shouldGetClient() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));
    when(personRepository.findById(anyLong())).thenReturn(Mono.just(person));

    // when
    Mono<ClientDto> clientResponse = clientService.getClient("client");

    // then
    StepVerifier.create(clientResponse)
        .assertNext(
            response -> {
              assertNotNull(response);
              assertEquals("name", response.name());
              assertEquals(Gender.MALE.name(), response.gender());
              assertEquals(30, response.age());
              assertEquals("1234567890", response.identification());
              assertEquals("address", response.address());
              assertEquals("+593", response.phoneNumber());
              assertEquals("client", response.clientId());
              assertNull(response.password());
              assertTrue(response.status());
            })
        .verifyComplete();

    verify(clientRepository).findByClientId(clientDto.clientId());
    verify(personRepository).findById(0L);
  }

  @Test
  void shouldReturnAnError_whenPersonNotFound_inGetClient() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));
    when(personRepository.findById(anyLong())).thenReturn(Mono.empty());

    // when
    Mono<ClientDto> clientResponse = clientService.getClient("client");

    // then
    StepVerifier.create(clientResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
    verify(personRepository).findById(0L);
  }

  @Test
  void shouldReturnAnError_whenClientStatusIsFalse_inGetClient() {
    // given
    client.setStatus(false);

    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));

    // when
    Mono<ClientDto> clientResponse = clientService.getClient("client");

    // then
    StepVerifier.create(clientResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
  }

  @Test
  void shouldReturnAnError_whenClientDoesNotExist_inGetClient() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.empty());

    // when
    Mono<ClientDto> clientResponse = clientService.getClient("client");

    // then
    StepVerifier.create(clientResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
  }

  @Test
  void shouldUpdateClientAndReturnVoid() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));
    when(personRepository.findById(client.getPersonId())).thenReturn(Mono.just(person));

    when(personRepository.save(any(Person.class))).thenReturn(Mono.just(person));
    when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(client));

    // when
    Mono<Void> updateResponse = clientService.updateClient("client", clientDto);

    // then
    StepVerifier.create(updateResponse).verifyComplete();

    verify(clientRepository).findByClientId(clientDto.clientId());
    verify(personRepository).findById(0L);
    verify(personRepository).save(any(Person.class));
    verify(clientRepository).save(any(Client.class));
  }

  @Test
  void shouldReturnMonoError_whenPersonDoesNotExist_inUpdateClient() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));
    when(personRepository.findById(client.getPersonId())).thenReturn(Mono.empty());

    // when
    Mono<Void> updateResponse = clientService.updateClient("client", clientDto);

    // then
    StepVerifier.create(updateResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
    verify(personRepository).findById(0L);
  }

  @Test
  void shouldReturnMonoError_whenClientStatusIsFalse_inUpdateClient() {
    // given
    client.setStatus(false);

    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));

    // when
    Mono<Void> updateResponse = clientService.updateClient("client", clientDto);

    // then
    StepVerifier.create(updateResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
  }

  @Test
  void shouldReturnMonoError_whenClientDoesntExist_inUpdateClient() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.empty());

    // when
    Mono<Void> updateResponse = clientService.updateClient("client", clientDto);

    // then
    StepVerifier.create(updateResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
  }

  @Test
  void shouldDeleteClientAndReturnVoid() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));
    when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(client));

    // when
    Mono<Void> deleteClient = clientService.deleteClient("client");

    // then
    StepVerifier.create(deleteClient).verifyComplete();

    verify(clientRepository).findByClientId(clientDto.clientId());
    verify(clientRepository).save(any(Client.class));
  }

  @Test
  void shouldReturnMonoError_whenClientStatusIsFalse_inDeleteClient() {
    // given
    client.setStatus(false);

    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.just(client));

    // when
    Mono<Void> deleteClient = clientService.deleteClient("client");

    // then
    StepVerifier.create(deleteClient)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
  }

  @Test
  void shouldReturnMonoError_whenClientDoesntExist_inDeleteClient() {
    // given
    when(clientRepository.findByClientId(anyString())).thenReturn(Mono.empty());

    // when
    Mono<Void> deleteClient = clientService.deleteClient("client");

    // then
    StepVerifier.create(deleteClient)
        .expectErrorMatches(
            throwable ->
                throwable instanceof EntityNotFoundException
                    && throwable.getMessage().equals("Client not found."))
        .verify();

    verify(clientRepository).findByClientId(clientDto.clientId());
  }
}
