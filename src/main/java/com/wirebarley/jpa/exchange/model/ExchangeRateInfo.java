package com.wirebarley.jpa.exchange.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @packageName : com.wirebarley.jpa.exchange.model
 * @fileName    : ExchangeRateInfo.java
 * @author      : 박유석
 * @date        : 2022. 1. 9
 * @version     : 1.0 
 * <pre>
 * @description : 
 * ===========================================================
 * DATE           AUTHOR       NOTE
 * -----------------------------------------------------------
 * 2022.01.09     박유석               최초 생성
 * </pre>
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRateInfo {
	
	@NotBlank(message = "수취 국가를 선택해주세요.")
	private String currencies; //통화 : 한국(KRW), 일본(JYP), 필리핀(PHP)
	
	@Min(value = 0, message = "송금액은 0과 10,000 사이의 금액입니다.")
	@Max(value = 10000, message = "송금액은 0과 10,000 사이의 금액입니다.")
	@NotNull(message = "송금액을 입력해주세요.")
	private Integer remittance; //송금액
	
}
