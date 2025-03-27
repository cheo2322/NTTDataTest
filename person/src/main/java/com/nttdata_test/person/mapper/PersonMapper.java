package com.nttdata_test.person.mapper;

import com.nttdata_test.person.entity.Client;
import com.nttdata_test.person.entity.Person;
import com.nttdata_test.person.entity.dto.ClientDto;
import com.nttdata_test.person.entity.dto.Gender;

public class PersonMapper {

  public static Person dtoToPerson(ClientDto clientDto) {
    return Person.builder()
        .name(clientDto.name())
        .gender(Gender.valueOf(clientDto.gender()))
        .age(clientDto.age())
        .identification(clientDto.identification())
        .address(clientDto.address())
        .phoneNumber(clientDto.phoneNumber())
        .build();
  }

  public static Client dtoToClient(ClientDto clientDto) {
    return Client.builder()
        .clientId(clientDto.clientId())
        .password(clientDto.password())
        .status(clientDto.status())
        .build();
  }

  public static ClientDto clientDto(Person person, Client client) {
    return new ClientDto(
        person.getName(),
        person.getGender().name(),
        person.getAge(),
        person.getIdentification(),
        person.getAddress(),
        person.getPhoneNumber(),
        client.getClientId(),
        null,
        client.getStatus());
  }
}
