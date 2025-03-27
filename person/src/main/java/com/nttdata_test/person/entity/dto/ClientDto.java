package com.nttdata_test.person.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientDto(
    String name,
    String gender,
    Integer age,
    String identification,
    String address,
    String phoneNumber,
    String clientId,
    String password,
    Boolean status) {}
