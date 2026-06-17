package com.sc.en.quotes.exceptions;

public class PersonalRuntimeException extends RuntimeException {

  public PersonalRuntimeException() {
  }

  public PersonalRuntimeException(String message) {
    super(message);
  }

  public PersonalRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public PersonalRuntimeException(Throwable cause) {
    super(cause);
  }

//  public PersonalRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//    super(message, cause, enableSuppression, writableStackTrace);
//  }
}
