package com.callcoindesk.currencycheck.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.callcoindesk.currencycheck.entity.CurrencyPriceChinesePO;
import com.callcoindesk.currencycheck.entity.CurrencyPricePO;

@Repository
public interface CurrencyPriceChinesePORepository extends JpaRepositoryImplementation<CurrencyPriceChinesePO, Long> {

	CurrencyPriceChinesePO findByChineseCode(String chineseCode);

	CurrencyPriceChinesePO findByCode(String code);
	

	
	
	
	

}
