package com.wirebarley.jpa.exchange.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @packageName : com.example.jpa.notice.model
 * @fileName    : ResponseError.java
 * @author      : 박유석
 * @date        : 2021. 11. 5
 * @version     : 1.0 
 * <pre>
 * @description : 어떤 필드에서 어떤 에러가 났는지 조회하는 클래스
 * ===========================================================
 * DATE           AUTHOR       NOTE
 * -----------------------------------------------------------
 * 2021.11.05     박유석               최초 생성
 * </pre>
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseError {
	private String field;
	private String message;
	
	public static ResponseError of(FieldError e) {
		return ResponseError.builder()
				.field(e.getField())
				.message(e.getDefaultMessage())
				.build();
	}

	public static List<ResponseError> of(List<ObjectError> errors) {
		List<ResponseError> responseErrors = new ArrayList<>();
		if (errors != null) {
			errors.stream().forEach((e) -> {
				responseErrors.add(ResponseError.of((FieldError)e));
			});
		}
		return responseErrors;
	}
	
}
