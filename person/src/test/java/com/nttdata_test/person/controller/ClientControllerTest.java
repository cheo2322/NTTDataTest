package com.nttdata_test.person.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import com.nttdata_test.person.TestHelper;
import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.entity.dto.Gender;
import com.nttdata_test.person.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ClientControllerUnitTest {

  @Mock private ClientService clientService;

  @InjectMocks private ClientController clientController;

  private ClientDto clientDto;
  private static final String CLIENT_ID = "client";

  @BeforeEach
  void setup() {
    clientDto = TestHelper.buildClientDto();
  }

  @Test
  void shouldCreateClientAndReturnVoid() {
    // given
    when(clientService.createClient(clientDto)).thenReturn(Mono.empty());

    // when
    Mono<Void> createClient = clientController.createClient(clientDto);

    // then
    StepVerifier.create(createClient).verifyComplete();

    verify(clientService).createClient(clientDto);
  }

  @Test
  void shouldReturnClient() {
    // given
    when(clientService.getClient(CLIENT_ID)).thenReturn(Mono.just(clientDto));

    // when
    Mono<ClientDto> getClient = clientController.getClient(CLIENT_ID);

    // then
    StepVerifier.create(getClient)
        .assertNext(
            response -> {
              assertNotNull(response);
              assertEquals("Name", response.name());
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
    verify(clientService, times(1)).getClient(CLIENT_ID);
  }

  @Test
  void shouldUpdateClientAndReturnVoid() {
    // given
    when(clientService.updateClient(CLIENT_ID, clientDto)).thenReturn(Mono.empty());

    // when
    Mono<Void> response = clientController.updateClient(CLIENT_ID, clientDto);

    // then
    StepVerifier.create(response).verifyComplete();

    verify(clientService, times(1)).updateClient(CLIENT_ID, clientDto);
  }

  @Test
  void shouldDeleteClientAndReturnVoid() {
    // given
    String clientId = "12345";
    when(clientService.deleteClient(clientId)).thenReturn(Mono.empty());

    // when
    Mono<Void> response = clientController.deleteClient(clientId);

    // then
    StepVerifier.create(response).verifyComplete();

    verify(clientService, times(1)).deleteClient(clientId);
  }
}
