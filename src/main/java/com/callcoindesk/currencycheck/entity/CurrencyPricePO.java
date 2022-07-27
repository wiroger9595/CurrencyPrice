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
@Table(name = "CURRENCY_PRICE")
@Data
@Accessors(chain = true)
public class CurrencyPricePO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String code;
    
    @Column
    private String symbol;
    
    @Column
	private BigDecimal rate;
    
    @Column
	private String description;
    
    @Column
	private LocalDateTime createTime;
    
    @Column
	private LocalDateTime updateTime;
    
    @Column
	private String isSuspend;

    
}
