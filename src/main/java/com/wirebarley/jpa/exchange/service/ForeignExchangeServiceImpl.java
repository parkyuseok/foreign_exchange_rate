package com.wirebarley.jpa.exchange.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.wirebarley.jpa.exchange.constants.CommonConstants;
import com.wirebarley.jpa.exchange.model.ExchangeRateInfo;

/**
 * @packageName : com.wirebarley.jpa.exchange.service
 * @fileName    : ForeignExchangeServiceImpl.java
 * @author      : 박유석
 * @date        : 2022. 1. 9
 * @version     : 1.0 
 * <pre>
 * @description : 환율 계산을 위한 서비스 구현 클래스
 * ===========================================================
 * DATE           AUTHOR       NOTE
 * -----------------------------------------------------------
 * 2022.01.09     박유석               최초 생성
 * </pre>
 */

@Service
public class ForeignExchangeServiceImpl implements ForeignExchangeService {

	@Override
	public Double getReceivedAmount(ExchangeRateInfo exchangeRateInfo) {
	    Double quotes = getExchangeRate(exchangeRateInfo.getCurrencies());
	    Double ReceivedAmount = quotes * exchangeRateInfo.getRemittance();
		
		return ReceivedAmount;
	}

	@Override
	public Double getExchangeRate(String currencies) {
		HttpURLConnection con = null;
	    JSONObject responseJson = null;
	    Double quotes = null;
	    
		try {
            String apiURL = CommonConstants.BASE_URL + CommonConstants.ENDPOINT
            		+ "?access_key=" + CommonConstants.ACCESS_KEY
            		+ "&currencies=" + currencies;
            
            URL url = new URL(apiURL);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            
            int responseCode = con.getResponseCode();
            BufferedReader br;
            
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            
            br.close();
            
            responseJson = new JSONObject(response.toString());
            String key = "USD" + currencies;
            if (responseJson.has("quotes")) {
            	quotes = (Double)responseJson.getJSONObject("quotes").get(key);				
			} else {
				quotes = -1.0;
			}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
		return quotes;
	}

}
