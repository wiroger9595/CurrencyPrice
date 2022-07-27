package com.callcoindesk.currencycheck.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ReqCurrencyPriceBody {
 
	    private String code;

	    private String chineseCode;
	    
	    private String symbol;

	    private BigDecimal rate;
	    
	    private String description;

	    public ReqCurrencyPriceBody(){}


}
