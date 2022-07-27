package com.callcoindesk.currencycheck.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.client.RestClientException;

import com.callcoindesk.currencycheck.dto.request.ReqCurrencyPriceBody;
import com.callcoindesk.currencycheck.dto.response.ResCurrencyPriceBody;
import com.callcoindesk.currencycheck.dto.response.ResReturnMessageBody;
import com.callcoindesk.currencycheck.model.CurrencyPriceVo;

public interface CurrencyPriceService{

	List<ResCurrencyPriceBody> getAllInfo() throws IOException, RestClientException, URISyntaxException;

	ResCurrencyPriceBody getDataById(ReqCurrencyPriceBody reqCurrencyPriceBody);

	ResReturnMessageBody insertData(@Valid @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody);

	ResReturnMessageBody updateData(@Valid @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody);

	ResReturnMessageBody deleteData(@Valid @NotNull ReqCurrencyPriceBody reqPasswordBody);


	List<CurrencyPriceVo> callCoinDesk() throws ClientProtocolException, IOException;
	


}
