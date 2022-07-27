package com.callcoindesk.currencycheck.dto.response;

import javax.validation.constraints.NotBlank;

public class ResReturnMessageBody {

	@NotBlank(message = "return message can't be empty")
	private String returnMessage;

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}


	
}
