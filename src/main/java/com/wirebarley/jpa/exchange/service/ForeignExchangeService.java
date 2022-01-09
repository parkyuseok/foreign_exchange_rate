package com.wirebarley.jpa.exchange.service;

import com.wirebarley.jpa.exchange.model.ExchangeRateInfo;

/**
 * @packageName : com.wirebarley.jpa.exchange.service
 * @fileName    : ForeignExchangeService.java
 * @author      : 박유석
 * @date        : 2022. 1. 9
 * @version     : 1.0 
 * <pre>
 * @description : 환율 계산을 위한 서비스 인터페이스 클래스
 * ===========================================================
 * DATE           AUTHOR       NOTE
 * -----------------------------------------------------------
 * 2022.01.09     박유석               최초 생성
 * </pre>
 */

public interface ForeignExchangeService {
	public Double getReceivedAmount(ExchangeRateInfo exchangeRateInfo);
	public Double getExchangeRate(String currencies);
}
