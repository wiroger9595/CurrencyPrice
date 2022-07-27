package com.callcoindesk.currencycheck.dto.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.callcoindesk.currencycheck.dto.request.ReqCurrencyPriceBody;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ResCurrencyPriceBody {

    private String code;

    private String chineseCode;

    private String symbol;

    private BigDecimal rate;
    
    private String description;
    
    
    
    
	
}
