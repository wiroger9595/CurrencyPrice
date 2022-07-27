package com.callcoindesk.currencycheck.enums;

import lombok.Getter;
import java.util.stream.Stream;

@Getter
public enum CurrencyChineseNameEnum {

	USD("美金"), GBP("英鎊"), EUR("歐元"), UNKNOWN("未知幣別");

	private String codeTW;

	CurrencyChineseNameEnum(String codeTW) {
		this.codeTW = codeTW;
	}

	public static CurrencyChineseNameEnum get(String code) {
		return Stream.of(CurrencyChineseNameEnum.values()).filter(c -> c.toString().equalsIgnoreCase(code)).findFirst()
				.orElse(CurrencyChineseNameEnum.UNKNOWN);
	}

}
