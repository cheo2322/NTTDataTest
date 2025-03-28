package com.nttdata_test.account.handler.ex;

public class DuplicateEntityException extends IllegalArgumentException {

  public DuplicateEntityException(String message) {
    super(message);
  }
}
