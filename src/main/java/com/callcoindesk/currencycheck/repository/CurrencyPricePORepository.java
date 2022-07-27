package com.callcoindesk.currencycheck.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.callcoindesk.currencycheck.entity.CurrencyPriceChinesePO;
import com.callcoindesk.currencycheck.entity.CurrencyPricePO;

@Repository
public interface CurrencyPricePORepository extends JpaRepositoryImplementation<CurrencyPricePO, Long> {
		
	
	
    Optional<CurrencyPricePO> findByCode(String code);

	CurrencyPricePO findByCodeAndIsSuspend(String code, String string);

}
