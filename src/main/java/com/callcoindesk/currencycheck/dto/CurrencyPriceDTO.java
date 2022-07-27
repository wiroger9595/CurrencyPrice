package com.callcoindesk.currencycheck.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CurrencyPriceDTO {
	
	private long id;
	private String code;
	private String symbol;
	private BigDecimal rate;
	private String desc;
	private double rateFloat;
	
	
	public CurrencyPriceDTO(long id, String code, String symbol, BigDecimal rate, String desc, double rateFloat) {
		super();
		this.id = id;
		this.code = code;
		this.symbol = symbol;
		this.rate = rate;
		this.desc = desc;
		this.rateFloat = rateFloat;
	}
	
	
	
	
	

}
