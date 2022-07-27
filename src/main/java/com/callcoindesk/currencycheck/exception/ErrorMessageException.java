package com.callcoindesk.currencycheck.exception;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class ErrorMessageException  extends RuntimeException{
	
	public ErrorMessageException(String errorMessage) {
		super(errorMessage);
	}

	}