package com.callcoindesk.currencycheck.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "CURRENCY_PRICE_CHINESE_NAME")
@Data
@Accessors(chain = true)
public class CurrencyPriceChinesePO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String code;
    
    @Column
    private String chineseCode;
    
    @Column
	private BigDecimal rate;
   
}
