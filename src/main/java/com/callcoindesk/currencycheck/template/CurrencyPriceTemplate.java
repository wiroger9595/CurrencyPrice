package com.callcoindesk.currencycheck.template;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.callcoindesk.currencycheck.entity.CurrencyPricePO;
import com.callcoindesk.currencycheck.model.CurrencyPriceChineseVo;
import com.callcoindesk.currencycheck.model.CurrencyPriceVo;
import com.callcoindesk.currencycheck.repository.CurrencyPricePORepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Service
@Configurable
public class CurrencyPriceTemplate {
	
	

	private static SimpleDateFormat UTC_DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.US);
	private static SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.UK);
	private static SimpleDateFormat UK_DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy 'at' HH:mm", Locale.UK);

	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private final String callAPI = "https://api.coindesk.com/v1/bpi/currentprice.json";
	
	
	CurrencyPricePORepository currencyPricePORepository;
	
	@Autowired
	public void setCurrencyPricePORepository(CurrencyPricePORepository currencyPricePORepository) {
		this.currencyPricePORepository = currencyPricePORepository;
	}

	public Map<String, Object>  getCurrencyPriceFromCoinDesk() throws ClientProtocolException, IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(callAPI);
		String charSet = "UTF-8";
		CloseableHttpResponse response = null;
		response = httpclient.execute(httpget);
		StatusLine status = response.getStatusLine();
		int state = status.getStatusCode();
		if (state == HttpStatus.SC_OK) {
			HttpEntity responseEntity = response.getEntity();

			JSONObject rtnObject = new JSONObject(EntityUtils.toString(responseEntity, charSet));

			//////////////////////////
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> wholeData = null;

			wholeData = mapper.readValue(rtnObject.toString(), new TypeReference<Map<String, Object>>() {});

			Map<String, Object> timeMap = (Map<String, Object>) wholeData.get("time");
			Map<String, Object> bpiMap = (Map<String, Object>) wholeData.get("bpi");

//			Map<String, Object> chineseNameMap = convertToMap(getCurrencyChineseNameList());
//
//			List<CurrencyPriceVo> currencyList = new ArrayList<>();
//			
//			
//			Map<String, Object> uasMap = (Map<String, Object>) bpiMap.get("USD");
//			Map<String, Object> gbpMap = (Map<String, Object>) bpiMap.get("GBP");
//			Map<String, Object> eurMap = (Map<String, Object>) bpiMap.get("EUR");
//			
//			List<CurrencyPricePO> currencyPricePOs = new ArrayList<CurrencyPricePO>();
//			
//			
//			CurrencyPricePO currencyPricePO = new CurrencyPricePO();
//			currencyPricePO.setCode(uasMap.get("code").toString());
//			currencyPricePO.setCreateTime(LocalDateTime.now());
//			currencyPricePO.setDescription(uasMap.get("description").toString());
//			currencyPricePO.setIsSuspend("N");
//			
//			DecimalFormat format = new DecimalFormat();
//			format.setParseBigDecimal(true);
//			
////			currencyPricePO.setRate(BigDecimal.valueOf(Double.valueOf(String.valueOf(uasMap.get("rate"))).intValue()));
//			currencyPricePO.setRate(new BigDecimal(42));
//			currencyPricePO.setSymbol("$");
//			currencyPricePO.setUpdateTime(LocalDateTime.now());
//			currencyPricePO.setId(1);
//			
//			currencyPricePOs.add(currencyPricePO);
//			
//			currencyPricePORepository.saveAll(currencyPricePOs);
		
			


			
			
			return wholeData;

		} else {
			System.out.println("請求返回:" + state + "(" + callAPI + ")");
		}

		if (response != null) {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

//	private Map<String, Object> convertToMap(List<CurrencyPriceChineseVo> currencyChineseNameList) {
//
//		Map<String, Object> returnMap = new HashMap<>();
//
//		if (currencyChineseNameList.size() > 0) {
//
//			for (CurrencyPriceChineseVo object : currencyChineseNameList) {
//				returnMap.put(object.getCurrencyCode(), object.getCodeName());
//			}
//
//		}
//
//		return returnMap;
//	}

	public List<CurrencyPriceChineseVo> getCurrencyChineseNameList() throws IOException {
//        List<CurrencyPriceChineseVo> returnData = currencyPriceChineseDao.findCurrencyChineseNameList();
		List<CurrencyPriceChineseVo> returnData = new ArrayList<CurrencyPriceChineseVo>();
		return returnData;
	}

//	private CurrencyPriceVo getCurrencyObject(String updatedISO, Map<String, Object> currencyMap,
//			SimpleDateFormat format, String zone, Map<String, Object> chineseMap) {
//
//		CurrencyPriceVo returnData = new CurrencyPriceVo();
//		try {
//			if (false == StringUtils.isEmpty(zone)) {
////	                format.setTimeZone(TimeZone.getTimeZone(zone));
//				updatedISO = updatedISO.replace(zone, "").trim();
//			}
//			Date updateDate = format.parse(updatedISO);
//			returnData.setUpdateTime(DATE_FORMAT.format(updateDate));
//			returnData.setCurrencyCode(currencyMap.get("code").toString());
////	            returnData.setCodeName(chineseMap.get(currencyMap.get("code").toString()).toString());
//			returnData.setRate((Double) currencyMap.get("rate_float"));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return returnData;
//	}

}
