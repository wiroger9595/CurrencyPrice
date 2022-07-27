package com.callcoindesk.currencycheck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@ToString
public class CurrencyPriceVo {

    @Column
	private String code;
    
    @Column
    private String symbol;
    
    @Column
	private BigDecimal rate;
    
    @Column
	private String description;
    
    @Column
	private String updateTime;
    

}