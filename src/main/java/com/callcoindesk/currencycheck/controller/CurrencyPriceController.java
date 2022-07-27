package com.callcoindesk.currencycheck.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.callcoindesk.currencycheck.dto.request.ReqCurrencyPriceBody;
import com.callcoindesk.currencycheck.dto.response.ResCurrencyPriceBody;
import com.callcoindesk.currencycheck.dto.response.ResReturnMessageBody;
import com.callcoindesk.currencycheck.model.CurrencyPriceVo;
import com.callcoindesk.currencycheck.service.CurrencyPriceService;

@RestController
@RequestMapping(value = "/coindesk")
public class CurrencyPriceController {
	
	@Autowired
	CurrencyPriceService currencyPriceService;
	
	
	
	/**
	 * 
	 * get all data
	 * @return
	 * @throws IOException
	 * @throws RestClientException
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "/findCurrencyPrice/all", method = RequestMethod.GET)
    @ResponseBody
	public List<ResCurrencyPriceBody> listAllInfo() throws IOException, RestClientException, URISyntaxException {
		return currencyPriceService.getAllInfo();
	}

	/**
	 * 
	 * 
	 * 
	 * @param reqCurrencyPriceBody
	 * @return
	 */
    @RequestMapping(value = "/findCurrencyPriceByCode", method = RequestMethod.GET)
    @ResponseBody
	public ResCurrencyPriceBody listOneInfo(@Valid @RequestBody @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody) {
    	ResCurrencyPriceBody resCurrencyPriceBody = currencyPriceService.getDataById(reqCurrencyPriceBody);
		return resCurrencyPriceBody;
	}
	
    
    /**
     * 
     * 
     * add data
     * @param reqCurrencyPriceBody
     * @return
     */
    @RequestMapping(value = "/addCurrencyPrice", method = RequestMethod.POST)
    @ResponseBody
	public ResReturnMessageBody insertNewData(@Valid @RequestBody @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody) {
    	ResReturnMessageBody resReturnMessageBody = currencyPriceService.insertData(reqCurrencyPriceBody);
		return resReturnMessageBody;
	}
    
    
    /**
     * 
     * 
     * update data
     * @param reqCurrencyPriceBody
     * @return
     */
    @RequestMapping(value = "/updateCurrencyPrice", method = RequestMethod.PUT)
    @ResponseBody
	public ResReturnMessageBody updateNewData(@Valid @RequestBody @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody) {
    	ResReturnMessageBody resReturnMessageBody = currencyPriceService.updateData(reqCurrencyPriceBody);
		return resReturnMessageBody;
	}
    
    
    
    /**
     * 
     * 
     * delete data
     * @param reqPasswordBody
     * @return
     */
    @RequestMapping(value = "/deleteCurrencyPrice", method = RequestMethod.DELETE)
    @ResponseBody
	public ResReturnMessageBody deleteData(@Valid @RequestBody @NotNull ReqCurrencyPriceBody reqPasswordBody) {
    	ResReturnMessageBody resReturnMessageBody = currencyPriceService.deleteData(reqPasswordBody);
		return resReturnMessageBody;
	}
	
	
	
    /**
     * 
     * 
     * display API data
     * @param reqPasswordBody
     * @return
     */
    @RequestMapping(value = "/callCurrencyPrice", method = RequestMethod.GET)
    @ResponseBody
	public List<CurrencyPriceVo> callCoinDesk() throws ClientProtocolException, IOException {
    	List<CurrencyPriceVo> currencyPriceVos = currencyPriceService.callCoinDesk();
		return currencyPriceVos;
	}
	
	
}
