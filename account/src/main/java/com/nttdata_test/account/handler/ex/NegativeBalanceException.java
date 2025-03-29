package com.nttdata_test.account.handler.ex;

public class NegativeBalanceException extends IllegalArgumentException {

  public NegativeBalanceException(String s) {
    super(s);
  }
}
