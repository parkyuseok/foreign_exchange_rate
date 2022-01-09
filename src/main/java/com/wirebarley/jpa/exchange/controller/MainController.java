package com.wirebarley.jpa.exchange.controller;

import java.text.DecimalFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wirebarley.jpa.exchange.service.ForeignExchangeService;

import lombok.RequiredArgsConstructor;

/**
 * @packageName : com.wirebarley.jpa.exchange.controller
 * @fileName    : MainController.java
 * @author      : 박유석
 * @date        : 2022. 1. 9
 * @version     : 1.0 
 * <pre>
 * @description : main 화면을 위한 컨트롤러 클래스
 * ===========================================================
 * DATE           AUTHOR       NOTE
 * -----------------------------------------------------------
 * 2022.01.09     박유석               최초 생성
 * </pre>
 */

@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final ForeignExchangeService foreignExchangeService;
	
	@GetMapping("/")
	public ModelAndView main(ModelAndView mv) {
		Double quotes = foreignExchangeService.getExchangeRate("KRW");
		
		// 소숫점 2째자리까지
        DecimalFormat df = new DecimalFormat("###,##0.00");
        String exchangeRate = df.format(quotes);
		
        if (quotes < 0.0) {
        	mv.addObject("exchangeRate", "");
        } else {
        	mv.addObject("exchangeRate", exchangeRate);
		}
		mv.setViewName("/index");
		
		return mv;
	}
	
}
