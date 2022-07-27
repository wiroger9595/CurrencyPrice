package com.callcoindesk.currencycheck.exception;

import org.springframework.http.HttpStatus;

public interface IErrorMessage {

    public String errorCode();
    public String errorMessage();
}
