package com.callcoindesk.currencycheck.model;

import java.math.BigDecimal;

import javax.persistence.OneToOne;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CurrencyPriceChineseVo {
	
	private String currencyCode;

	private String currencyChineseCode;

	private BigDecimal exchangeRate;

}