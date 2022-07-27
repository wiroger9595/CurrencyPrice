package com.callcoindesk.currencycheck.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import com.callcoindesk.currencycheck.dto.request.ReqCurrencyPriceBody;
import com.callcoindesk.currencycheck.dto.response.ResCurrencyPriceBody;
import com.callcoindesk.currencycheck.dto.response.ResReturnMessageBody;
import com.callcoindesk.currencycheck.entity.CurrencyPriceChinesePO;
import com.callcoindesk.currencycheck.entity.CurrencyPricePO;

import com.callcoindesk.currencycheck.exception.DataNotFoundException;
import com.callcoindesk.currencycheck.model.CurrencyPriceVo;
import com.callcoindesk.currencycheck.repository.CurrencyPriceChinesePORepository;
import com.callcoindesk.currencycheck.repository.CurrencyPricePORepository;
import com.callcoindesk.currencycheck.service.CurrencyPriceService;
import com.callcoindesk.currencycheck.template.CurrencyPriceTemplate;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class CurrencyPriceServiceImpl implements CurrencyPriceService {

	@Autowired
	CurrencyPricePORepository currencyPricePORepository;

	@Autowired
	CurrencyPriceChinesePORepository currencyPriceChinesePORepository;

	DateTimeFormatter utcFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss");
	DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	DateTimeFormatter ukFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm");

	/**
	 * 
	 * inital data
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@PostConstruct
	public void initalData() throws ClientProtocolException, IOException {

		CurrencyPriceTemplate currencyPriceTemplate = new CurrencyPriceTemplate();

		Map<String, Object> wholeData = currencyPriceTemplate.getCurrencyPriceFromCoinDesk();

		Map<String, Object> timeMap = (Map<String, Object>) wholeData.get("time");
		Map<String, Object> bpiMap = (Map<String, Object>) wholeData.get("bpi");

		Map<String, Object> usaMap = (Map<String, Object>) bpiMap.get("USD");
		Map<String, Object> gbpMap = (Map<String, Object>) bpiMap.get("GBP");
		Map<String, Object> eurMap = (Map<String, Object>) bpiMap.get("EUR");

		List<CurrencyPricePO> currencyPricePOs = new ArrayList<CurrencyPricePO>();
		List<CurrencyPriceChinesePO> currencyPriceChinesePOs = new ArrayList<CurrencyPriceChinesePO>();

		CurrencyPricePO currencyPricePO = new CurrencyPricePO();
		currencyPricePO.setCode(usaMap.get("code").toString());
		currencyPricePO.setCreateTime(LocalDateTime.now());
		currencyPricePO.setDescription(usaMap.get("description").toString());
		currencyPricePO.setSymbol(usaMap.get("symbol").toString());
		currencyPricePO.setIsSuspend("N");

		DecimalFormat format = new DecimalFormat();
		format.setParseBigDecimal(true);
		ParsePosition parsePosition = new ParsePosition(0);

		BigDecimal rate = (BigDecimal) format.parse(usaMap.get("rate").toString(), parsePosition);
		currencyPricePO.setRate(rate);

		String updateTime = StringUtils.substringBeforeLast(StringUtils.remove(timeMap.get("updated").toString(), ","),
				" ");
		currencyPricePO.setUpdateTime(LocalDateTime.parse(updateTime, utcFormatter));

		currencyPricePOs.add(currencyPricePO);

		CurrencyPriceChinesePO currencyPriceChinesePO = new CurrencyPriceChinesePO();
		currencyPriceChinesePO.setChineseCode("美金");
		currencyPriceChinesePO.setCode(usaMap.get("code").toString());
		currencyPriceChinesePO.setRate(rate);

		currencyPriceChinesePOs.add(currencyPriceChinesePO);

		// =======================================
		currencyPricePO = new CurrencyPricePO();
		currencyPricePO.setCode(gbpMap.get("code").toString());
		currencyPricePO.setCreateTime(LocalDateTime.now());
		currencyPricePO.setDescription(gbpMap.get("description").toString());
		currencyPricePO.setSymbol(gbpMap.get("symbol").toString());
		currencyPricePO.setIsSuspend("N");

		format = new DecimalFormat();
		format.setParseBigDecimal(true);
		parsePosition = new ParsePosition(0);
		rate = (BigDecimal) format.parse(gbpMap.get("rate").toString(), parsePosition);
		currencyPricePO.setRate(rate);

		updateTime = StringUtils.substringBeforeLast(StringUtils.remove(timeMap.get("updatedISO").toString(), ","),
				"+");
		currencyPricePO.setUpdateTime(LocalDateTime.parse(updateTime, isoFormatter));

		currencyPricePOs.add(currencyPricePO);

		currencyPriceChinesePO = new CurrencyPriceChinesePO();
		currencyPriceChinesePO.setChineseCode("英鎊");
		currencyPriceChinesePO.setCode(gbpMap.get("code").toString());
		currencyPriceChinesePO.setRate(rate);

		currencyPriceChinesePOs.add(currencyPriceChinesePO);

		// ========================================
		currencyPricePO = new CurrencyPricePO();
		currencyPricePO.setCode(eurMap.get("code").toString());
		currencyPricePO.setCreateTime(LocalDateTime.now());
		currencyPricePO.setDescription(eurMap.get("description").toString());
		currencyPricePO.setSymbol(eurMap.get("symbol").toString());
		currencyPricePO.setIsSuspend("N");

		format = new DecimalFormat();
		format.setParseBigDecimal(true);
		parsePosition = new ParsePosition(0);
		rate = (BigDecimal) format.parse(eurMap.get("rate").toString(), parsePosition);
		currencyPricePO.setRate(rate);

		updateTime = StringUtils.substringBeforeLast(timeMap.get("updateduk").toString(), " ");
		currencyPricePO.setUpdateTime(LocalDateTime.parse(updateTime, ukFormatter));

		currencyPricePOs.add(currencyPricePO);

		currencyPriceChinesePO = new CurrencyPriceChinesePO();
		currencyPriceChinesePO.setChineseCode("歐元");
		currencyPriceChinesePO.setCode(eurMap.get("code").toString());
		currencyPriceChinesePO.setRate(rate);

		currencyPriceChinesePOs.add(currencyPriceChinesePO);

		currencyPriceChinesePORepository.saveAll(currencyPriceChinesePOs);

		currencyPricePORepository.saveAll(currencyPricePOs);

	}

	/**
	 * 
	 * get all data
	 * 
	 */
	@Override
	public List<ResCurrencyPriceBody> getAllInfo() {

		List<ResCurrencyPriceBody> listAllData = new ArrayList<ResCurrencyPriceBody>();

		List<CurrencyPricePO> currencyDatas = currencyPricePORepository.findAll();
		List<CurrencyPriceChinesePO> currencyPriceChinesePO = currencyPriceChinesePORepository.findAll();

		currencyDatas.stream().forEach(currencyData -> {

			ResCurrencyPriceBody resCurrencyPriceBody = new ResCurrencyPriceBody();
			resCurrencyPriceBody.setCode(currencyData.getCode());
			resCurrencyPriceBody.setDescription(currencyData.getDescription());
			resCurrencyPriceBody.setRate(currencyData.getRate());
			resCurrencyPriceBody.setSymbol(currencyData.getSymbol());

			resCurrencyPriceBody.setChineseCode(
					currencyPriceChinesePORepository.findByCode(currencyData.getCode()).getChineseCode());

			listAllData.add(resCurrencyPriceBody);

		});

		return listAllData;
	}

	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public ResCurrencyPriceBody getDataById(ReqCurrencyPriceBody reqCurrencyPriceBody) {

		checkCodeAndChineseCode(reqCurrencyPriceBody);

		CurrencyPricePO currencyPricePO = Optional
				.ofNullable(currencyPricePORepository.findByCodeAndIsSuspend(reqCurrencyPriceBody.getCode(), "N"))
				.orElseThrow(DataNotFoundException::new);

		ResCurrencyPriceBody resCurrencyPriceBody = new ResCurrencyPriceBody();
		resCurrencyPriceBody.setCode(currencyPricePO.getCode());
		resCurrencyPriceBody.setDescription(currencyPricePO.getDescription());
		resCurrencyPriceBody.setRate(currencyPricePO.getRate());
		resCurrencyPriceBody.setSymbol(currencyPricePO.getSymbol());

		return resCurrencyPriceBody;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public ResReturnMessageBody insertData(@Valid @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody) {

		checkCodeAndChineseCode(reqCurrencyPriceBody);

		CurrencyPricePO oldCurrencyPricePO = currencyPricePORepository
				.findByCodeAndIsSuspend(reqCurrencyPriceBody.getCode(), "N");

		if (oldCurrencyPricePO != null) {
			oldCurrencyPricePO.setIsSuspend("Y");
		}

		CurrencyPricePO currencyPricePO = new CurrencyPricePO();
		currencyPricePO.setCode(reqCurrencyPriceBody.getCode());
		currencyPricePO.setDescription(reqCurrencyPriceBody.getDescription());
		currencyPricePO.setCreateTime(LocalDateTime.now());
		currencyPricePO.setIsSuspend("N");
		currencyPricePO.setUpdateTime(LocalDateTime.now());
		currencyPricePO.setRate(reqCurrencyPriceBody.getRate());
		currencyPricePO.setSymbol(reqCurrencyPriceBody.getSymbol());

		currencyPricePORepository.save(currencyPricePO);

		CurrencyPriceChinesePO currencyPriceChinesePO = new CurrencyPriceChinesePO();
		currencyPriceChinesePO.setChineseCode(reqCurrencyPriceBody.getChineseCode());
		currencyPriceChinesePO.setCode(reqCurrencyPriceBody.getCode());
		currencyPriceChinesePO.setRate(reqCurrencyPriceBody.getRate());

		currencyPriceChinesePORepository.save(currencyPriceChinesePO);

		ResReturnMessageBody resReturnMessageBody = new ResReturnMessageBody();
		resReturnMessageBody.setReturnMessage("insert success");

		return resReturnMessageBody;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public ResReturnMessageBody updateData(@Valid @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody) {

		checkCodeAndChineseCode(reqCurrencyPriceBody);

		CurrencyPricePO updateCurrencyPricePO = currencyPricePORepository
				.findByCodeAndIsSuspend(reqCurrencyPriceBody.getCode(), "N");

		if (updateCurrencyPricePO == null) {
			throw new DataNotFoundException();
		}

		updateCurrencyPricePO.setCode(reqCurrencyPriceBody.getCode());
		updateCurrencyPricePO.setDescription(reqCurrencyPriceBody.getDescription());
		updateCurrencyPricePO.setCreateTime(LocalDateTime.now());
		updateCurrencyPricePO.setIsSuspend("N");
		updateCurrencyPricePO.setUpdateTime(LocalDateTime.now());
		updateCurrencyPricePO.setRate(reqCurrencyPriceBody.getRate());
		updateCurrencyPricePO.setSymbol(reqCurrencyPriceBody.getSymbol());

		currencyPricePORepository.save(updateCurrencyPricePO);

		ResReturnMessageBody resReturnMessageBody = new ResReturnMessageBody();
		resReturnMessageBody.setReturnMessage("update success");

		return resReturnMessageBody;
	}

	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public ResReturnMessageBody deleteData(@Valid @NotNull ReqCurrencyPriceBody reqCurrencyPriceBody) {

		checkCodeAndChineseCode(reqCurrencyPriceBody);

		ResReturnMessageBody resReturnMessageBody = new ResReturnMessageBody();

		CurrencyPricePO deleteCurrencyPricePO = currencyPricePORepository
				.findByCodeAndIsSuspend(reqCurrencyPriceBody.getCode(), "N");

		if (deleteCurrencyPricePO == null) {
			throw new DataNotFoundException();
		} else {
			deleteCurrencyPricePO.setIsSuspend("Y");
			
			CurrencyPriceChinesePO currencyPriceChinesePO = currencyPriceChinesePORepository.findByCode(deleteCurrencyPricePO.getCode());
			currencyPriceChinesePORepository.delete(currencyPriceChinesePO);
			
			currencyPricePORepository.save(deleteCurrencyPricePO);

			resReturnMessageBody.setReturnMessage("soft delete success");
			return resReturnMessageBody;

		}

	}

	/**
	 * 
	 * 
	 * @param reqCurrencyPriceBody
	 */
	private void checkCodeAndChineseCode(ReqCurrencyPriceBody reqCurrencyPriceBody) {
		if (reqCurrencyPriceBody.getCode() == null && reqCurrencyPriceBody.getChineseCode() == null) {
			throw new DataNotFoundException();
		}

		if (reqCurrencyPriceBody.getCode() == null && reqCurrencyPriceBody.getChineseCode() != null) {
			CurrencyPriceChinesePO currencyPriceChinesePO = currencyPriceChinesePORepository
					.findByChineseCode(reqCurrencyPriceBody.getChineseCode());
			reqCurrencyPriceBody.setCode(currencyPriceChinesePO.getCode());
		}
	}

	/**
	 * 
	 * call coindesk data
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 
	 */
	@Override
	public List<CurrencyPriceVo> callCoinDesk() throws ClientProtocolException, IOException {
		
		CurrencyPriceTemplate currencyPriceTemplate = new CurrencyPriceTemplate();

		Map<String, Object> wholeData = currencyPriceTemplate.getCurrencyPriceFromCoinDesk();

		Map<String, Object> timeMap = (Map<String, Object>) wholeData.get("time");
		Map<String, Object> bpiMap = (Map<String, Object>) wholeData.get("bpi");

		Map<String, Object> usaMap = (Map<String, Object>) bpiMap.get("USD");
		Map<String, Object> gbpMap = (Map<String, Object>) bpiMap.get("GBP");
		Map<String, Object> eurMap = (Map<String, Object>) bpiMap.get("EUR");

		List<CurrencyPriceVo> currencyPriceVOs = new ArrayList<CurrencyPriceVo>();
		CurrencyPriceVo currencyPriceVo = new CurrencyPriceVo();
		currencyPriceVo.setCode(usaMap.get("code").toString());
		currencyPriceVo.setDescription(usaMap.get("descrption").toString());
		currencyPriceVo.setUpdateTime(timeMap.get("updated").toString());
		
		DecimalFormat format = new DecimalFormat();
		format.setParseBigDecimal(true);
		ParsePosition parsePosition = new ParsePosition(0);
		BigDecimal rate = (BigDecimal) format.parse(usaMap.get("rate").toString(), parsePosition);
		currencyPriceVo.setRate(rate);
		currencyPriceVOs.add(currencyPriceVo);
		
		
		currencyPriceVo = new CurrencyPriceVo();
		currencyPriceVo.setCode(gbpMap.get("code").toString());
		currencyPriceVo.setDescription(gbpMap.get("descrption").toString());
		currencyPriceVo.setUpdateTime(timeMap.get("updatedISO").toString());
		
		format = new DecimalFormat();
		format.setParseBigDecimal(true);
		parsePosition = new ParsePosition(0);
		rate = (BigDecimal) format.parse(gbpMap.get("rate").toString(), parsePosition);
		currencyPriceVo.setRate(rate);
		currencyPriceVOs.add(currencyPriceVo);
		
		
		currencyPriceVo = new CurrencyPriceVo();
		currencyPriceVo.setCode(eurMap.get("code").toString());
		currencyPriceVo.setDescription(eurMap.get("descrption").toString());
		currencyPriceVo.setUpdateTime(timeMap.get("updateduk").toString());
		
		format = new DecimalFormat();
		format.setParseBigDecimal(true);
		parsePosition = new ParsePosition(0);
		rate = (BigDecimal) format.parse(eurMap.get("rate").toString(), parsePosition);
		currencyPriceVo.setRate(rate);
		currencyPriceVOs.add(currencyPriceVo);
		
		return currencyPriceVOs;
	}

}
