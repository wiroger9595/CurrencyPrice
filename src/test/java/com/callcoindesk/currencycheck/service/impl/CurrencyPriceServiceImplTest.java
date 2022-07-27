package com.callcoindesk.currencycheck.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestClientException;

import com.callcoindesk.currencycheck.controller.CurrencyPriceController;
import com.callcoindesk.currencycheck.dto.request.ReqCurrencyPriceBody;
import com.callcoindesk.currencycheck.dto.response.ResCurrencyPriceBody;
import com.callcoindesk.currencycheck.entity.CurrencyPriceChinesePO;
import com.callcoindesk.currencycheck.entity.CurrencyPricePO;
import com.callcoindesk.currencycheck.model.CurrencyPriceChineseVo;
import com.callcoindesk.currencycheck.model.CurrencyPriceVo;
import com.callcoindesk.currencycheck.repository.CurrencyPriceChinesePORepository;
import com.callcoindesk.currencycheck.repository.CurrencyPricePORepository;
import com.callcoindesk.currencycheck.service.CurrencyPriceService;
import com.callcoindesk.currencycheck.service.impl.CurrencyPriceServiceImpl;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CurrencyPriceServiceImplTest {
	
//	@Mock
//	private ReqPasswordBody reqPasswordBody;
//
//	@InjectMocks
//	private PasswordCheckService passwordCheckService = new PasswordCheckImpl();
//	
	@Autowired
    CurrencyPricePORepository currencyPricePORepository;
	
	@Autowired
	CurrencyPriceChinesePORepository currencyPriceChinesePORepository;

    @Autowired
    CurrencyPriceService currencyPriceService;
    
    
    
    

//    @BeforeEach
//    public void setCurrencyChineseData() {
//
//        try {
//        	currencyPriceService.initCurrencyChineseNameData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 測試呼叫查詢幣別對應表資料 API
     * @throws URISyntaxException 
     * @throws RestClientException 
     */
    @Order(1)
    @Test
    public void testGetCurrencyChineseNameData() throws IOException, RestClientException, URISyntaxException {

        List<CurrencyPriceChineseVo> expected = new ArrayList<>();
        CurrencyPriceChineseVo testDataA = new CurrencyPriceChineseVo();
        testDataA.setCurrencyCode("USD");
        testDataA.setCurrencyChineseCode("美元");
        testDataA.setExchangeRate(new BigDecimal(1.24532));

        CurrencyPriceChineseVo testDataB = new CurrencyPriceChineseVo();
        testDataB.setCurrencyCode("GBP");
        testDataB.setCurrencyChineseCode("英鎊");
        testDataB.setExchangeRate(new BigDecimal(2.24532));

        CurrencyPriceChineseVo testDataC = new CurrencyPriceChineseVo();
        testDataC.setCurrencyCode("EUR");
        testDataC.setCurrencyChineseCode("歐元");
        testDataB.setExchangeRate(new BigDecimal(1.324532));

        expected.add(testDataA);
        expected.add(testDataB);
        expected.add(testDataC);

        List<ResCurrencyPriceBody> actual = currencyPriceService.getAllInfo();

        assertEquals(expected.size(), actual.size());
        assertThat(actual.get(0).getCode())
                .isEqualTo("USD");
        assertThat(actual.get(1).getCode())
                .isEqualTo("GBP");
        assertThat(actual.get(2).getCode())
                .isEqualTo("EUR");

        System.out.println(" =====testGetCurrencyChineseNameData===== ");
        for (ResCurrencyPriceBody data : actual) {
            System.out.println(data.toString());
        }

    }

    /**
     * 測試呼叫新增幣別對應表資料 API
     * @throws URISyntaxException 
     * @throws IOException 
     * @throws RestClientException 
     */
    @Order(2)
    @Test
    public void testInsertCurrencyChineseNameData() throws RestClientException, IOException, URISyntaxException {

        List<CurrencyPriceChineseVo> expected = new ArrayList<>();
        CurrencyPriceChineseVo currencyPriceChineseVo = new CurrencyPriceChineseVo();

        currencyPriceChineseVo.setCurrencyCode("TWD");
        currencyPriceChineseVo.setCurrencyChineseCode("台幣");
        currencyPriceChineseVo.setExchangeRate(new BigDecimal(31.324532));
        expected.add(currencyPriceChineseVo);
        
        ReqCurrencyPriceBody reqCurrencyPriceBody = new ReqCurrencyPriceBody();
        reqCurrencyPriceBody.setChineseCode(currencyPriceChineseVo.getCurrencyChineseCode());
        reqCurrencyPriceBody.setCode(currencyPriceChineseVo.getCurrencyCode());
        reqCurrencyPriceBody.setSymbol("$");
        reqCurrencyPriceBody.setRate(currencyPriceChineseVo.getExchangeRate());
        
        currencyPriceService.insertData(reqCurrencyPriceBody);

        List<ResCurrencyPriceBody> actual = currencyPriceService.getAllInfo();

        assertEquals(expected.get(0).getCurrencyCode(), actual.get(actual.size() - 1).getCode());
        assertEquals(expected.get(0).getCurrencyChineseCode(), actual.get(actual.size() - 1).getChineseCode());

    }

    /**
     * 測試呼叫更新幣別對應表資料 API
     */
    @Order(3)
    @Test
    public void testUpdateCurrencyChineseNameData() {

        List<CurrencyPriceChineseVo> expected = new ArrayList<>();
        CurrencyPriceChineseVo currencyPriceChineseVo = new CurrencyPriceChineseVo();

        currencyPriceChineseVo.setCurrencyCode("USD");
        currencyPriceChineseVo.setCurrencyChineseCode("美金");
        currencyPriceChineseVo.setExchangeRate(new BigDecimal(1.8605));
        expected.add(currencyPriceChineseVo);
        
        ReqCurrencyPriceBody reqCurrencyPriceBody = new ReqCurrencyPriceBody();
        reqCurrencyPriceBody.setChineseCode(currencyPriceChineseVo.getCurrencyChineseCode());
        reqCurrencyPriceBody.setCode(currencyPriceChineseVo.getCurrencyCode());
        reqCurrencyPriceBody.setSymbol("$");
        reqCurrencyPriceBody.setRate(currencyPriceChineseVo.getExchangeRate());

        currencyPriceService.updateData(reqCurrencyPriceBody);
        
        CurrencyPriceChinesePO actual = currencyPriceChinesePORepository.findByCode(reqCurrencyPriceBody.getCode());

        assertEquals(expected.get(0).getCurrencyCode(), actual.getCode());
        assertEquals(expected.get(0).getCurrencyChineseCode(), actual.getChineseCode());
        
        System.out.println(" =====testUpdateCurrencyChineseNameData===== ");
        System.out.println(actual.toString());
    }

    /**
     * 測試呼叫刪除幣別對應表資料 API
     * @throws URISyntaxException 
     * @throws IOException 
     * @throws RestClientException 
     */
    @Order(4)
    @Test
    public void testDeleteCurrencyChineseNameData() throws RestClientException, IOException, URISyntaxException {
    	
    	ReqCurrencyPriceBody reqCurrencyPriceBody = new ReqCurrencyPriceBody();
        reqCurrencyPriceBody.setChineseCode("台幣");
        reqCurrencyPriceBody.setCode("TWD");
        reqCurrencyPriceBody.setSymbol("$");

        currencyPriceService.deleteData(reqCurrencyPriceBody);
        

        CurrencyPricePO actual = currencyPricePORepository.findByCodeAndIsSuspend(reqCurrencyPriceBody.getCode(), "N");


        assertThat(actual)
                .isNull();

    }

    /**
     * 測試呼叫 coindesk API，並顯示其內容。
     */
    @Order(5)
    @Test
    public void testGetCoindeskData() {

        try {

            List<CurrencyPriceVo> actual = currencyPriceService.callCoinDesk();

            assertNotNull(actual);
            assertEquals(actual.size(), 3);
            System.out.println(" =====testGetCoindeskData===== ");
            System.out.println(actual);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 測試呼叫資料轉換的 API，並顯示其內容
     */
    @Order(6)
    @Test
    public void testGetCurrencyData() {

        List<CurrencyPriceChineseVo> expected = new ArrayList<CurrencyPriceChineseVo>();
        CurrencyPriceChineseVo currencyPriceChineseVoA = new CurrencyPriceChineseVo();

        currencyPriceChineseVoA.setCurrencyCode("USD");
        currencyPriceChineseVoA.setCurrencyChineseCode("美元");

        CurrencyPriceChineseVo currencyPriceChineseVoB = new CurrencyPriceChineseVo();

        currencyPriceChineseVoB.setCurrencyCode("GBP");
        currencyPriceChineseVoB.setCurrencyChineseCode("英鎊");

        CurrencyPriceChineseVo currencyPriceChineseVoC = new CurrencyPriceChineseVo();

        currencyPriceChineseVoC.setCurrencyCode("EUR");
        currencyPriceChineseVoC.setCurrencyChineseCode("歐元");

        expected.add(currencyPriceChineseVoA);
        expected.add(currencyPriceChineseVoB);
        expected.add(currencyPriceChineseVoC);

        List<CurrencyPriceChinesePO> actual = new ArrayList<>();
        actual = currencyPriceChinesePORepository.findAll();

        assertEquals(expected.size(), actual.size());
        assertThat(actual.get(0).getCode())
                .isEqualTo("USD");
        assertThat(actual.get(1).getCode())
                .isEqualTo("GBP");
        assertThat(actual.get(2).getCode())
                .isEqualTo("EUR");

        System.out.println(" =====testGetCurrencyData===== ");
        for (CurrencyPriceChinesePO data : actual) {
            System.out.println(data.toString());
        }

    }	

}
