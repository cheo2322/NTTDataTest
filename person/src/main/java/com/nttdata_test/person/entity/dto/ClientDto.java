package com.nttdata_test.person.entity.dto;

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
