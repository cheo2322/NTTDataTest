package com.nttdata_test.person.controller;

import com.nttdata_test.person.entity.dto.ClientDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientControllerIntegrationTest {

  @Autowired private WebTestClient webTestClient;

  @BeforeAll
  void setup() {
    // Some tests need a client to be created before their execution
    ClientDto clientGet =
        new ClientDto(
            "Juan Osorio",
            "MALE",
            29,
            "1",
            "13 de junio y Equinoccial",
            "098874587",
            "testGet",
            "1245",
            true);

    webTestClient
        .post()
        .uri("/nttdata/test/v1/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(clientGet)
        .exchange()
        .expectStatus()
        .isCreated();

    ClientDto clientPatch =
        new ClientDto(
            "Juan Osorio",
            "MALE",
            29,
            "2",
            "13 de junio y Equinoccial",
            "098874587",
            "clientPatch",
            "1245",
            true);

    webTestClient
        .post()
        .uri("/nttdata/test/v1/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(clientPatch)
        .exchange()
        .expectStatus()
        .isCreated();

    ClientDto clientDelete =
        new ClientDto(
            "Juan Osorio",
            "MALE",
            29,
            "3",
            "13 de junio y Equinoccial",
            "098874587",
            "clientDelete",
            "1245",
            true);

    webTestClient
        .post()
        .uri("/nttdata/test/v1/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(clientDelete)
        .exchange()
        .expectStatus()
        .isCreated();
  }

  @Test
  public void testCreateClient() {
    ClientDto client =
        new ClientDto(
            "Juan Osorio",
            "MALE",
            29,
            "0",
            "13 de junio y Equinoccial",
            "098874587",
            "josorio",
            "1245",
            true);

    webTestClient
        .post()
        .uri("/nttdata/test/v1/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(client)
        .exchange()
        .expectStatus()
        .isCreated();
  }

  @Test
  public void testGetClient() {
    webTestClient
        .get()
        .uri("/nttdata/test/v1/clients/{clientId}", "testGet")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(ClientDto.class)
        .consumeWith(
            response -> {
              ClientDto clientResponse = response.getResponseBody();
              assert clientResponse != null;
              assert clientResponse.clientId().equals("testGet");
              assert clientResponse.name().equals("Juan Osorio");
            });
  }

  @Test
  public void testUpdateClient() {
    ClientDto updatedClient =
        new ClientDto(
            "",
            "",
            null,
            "",
            "13 de junio y Equinoccial 2",
            "098874580",
            "clientUpdated",
            "",
            null);

    webTestClient
        .patch()
        .uri("/nttdata/test/v1/clients/{clientId}", "clientPatch")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updatedClient)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  public void testDeleteClient() {
    webTestClient
        .delete()
        .uri("/nttdata/test/v1/clients/{clientId}", "clientDelete")
        .exchange()
        .expectStatus()
        .isOk();
  }
}
