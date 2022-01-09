package com.wirebarley.jpa.exchange.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wirebarley.jpa.exchange.model.ExchangeRateInfo;
import com.wirebarley.jpa.exchange.model.ResponseError;
import com.wirebarley.jpa.exchange.service.ForeignExchangeService;

import lombok.RequiredArgsConstructor;

/**
 * @packageName : com.wirebarley.jpa.exchange.controller
 * @fileName    : ApiForeignExchangeController.java
 * @author      : 박유석
 * @date        : 2022. 1. 9
 * @version     : 1.0 
 * <pre>
 * @description : 환율 계산을 위한 컨트롤러 클래스
 * ===========================================================
 * DATE           AUTHOR       NOTE
 * -----------------------------------------------------------
 * 2022.01.09     박유석               최초 생성
 * </pre>
 */

@RequiredArgsConstructor
@RestController
public class ApiForeignExchangeController {

	private final ForeignExchangeService foreignExchangeService;
	
	/**
	 * 수취 금액 구하는 API
	 * @param currencies
	 * @param remittance
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/api/foreignExchange")
	public ResponseEntity<Object> getReceivedAmount(@Valid ExchangeRateInfo exchangeRateInfo, Errors errors) {
		// 유효성 검사에 걸리면 Errors에 에러가 떨어진다.
		if (errors.hasErrors()) {
			List<ResponseError> responseErrors = new ArrayList<>();
			// 에러 객체에서 필요한 정보만 선별해서 저장한다.
			errors.getAllErrors().stream().forEach(e -> {
				responseErrors.add(ResponseError.of((FieldError) e));
			});

			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
		}
		
		Double ReceivedAmount = foreignExchangeService.getReceivedAmount(exchangeRateInfo);
		if (ReceivedAmount < 0.0) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}

        DecimalFormat df = new DecimalFormat("###,##0.00");
        String result = df.format(ReceivedAmount);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * 환율 구하는 API
	 * @param currencies
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/api/exchangeRate")
	public ResponseEntity<Object> getExchangeRate(@RequestParam(name = "currencies") String currencies) {
		
		if ("".equals(currencies) || currencies == null) {
			return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		
		Double quotes = foreignExchangeService.getExchangeRate(currencies);
		if (quotes < 0.0) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}

        DecimalFormat df = new DecimalFormat("###,##0.00");
        String exchangeRate = df.format(quotes);
        
        return new ResponseEntity<>(exchangeRate, HttpStatus.OK);
	}
	
}
