package com.callcoindesk.currencycheck.exception;

import org.apache.commons.lang3.StringUtils;


public class DataNotFoundException  extends ErrorMessageException{
	
	public DataNotFoundException() {
		super("data is not found on database");
	}

	}