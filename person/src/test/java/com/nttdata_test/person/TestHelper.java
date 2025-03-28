package com.nttdata_test.person;

import com.nttdata_test.person.entity.Client;
import com.nttdata_test.person.entity.Person;
import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.entity.dto.Gender;

public class TestHelper {

  public static Person buildPerson() {
    return Person.builder()
        .id(0L)
        .name("name")
        .gender(Gender.MALE)
        .age(30)
        .identification("1234567890")
        .address("address")
        .phoneNumber("+593")
        .build();
  }

  public static Client buildClient() {
    return Client.builder()
        .id(0L)
        .clientId("client")
        .password("pass")
        .status(true)
        .personId(0L)
        .build();
  }

  public static ClientDto buildClientDto() {
    return new ClientDto("Name", "MALE", 30, "1234567890", "address", "+593", "client", null, true);
  }
}
