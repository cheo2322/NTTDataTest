package com.nttdata_test.person.handler.ex;

public class DuplicateEntityException extends IllegalArgumentException {

  public DuplicateEntityException(String message) {
    super(message);
  }
}
