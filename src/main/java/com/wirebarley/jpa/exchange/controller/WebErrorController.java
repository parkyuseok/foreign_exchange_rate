package com.wirebarley.jpa.exchange.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @packageName : com.wirebarley.jpa.exchange.controller
 * @fileName    : WebErrorController.java
 * @author      : 박유석
 * @date        : 2022. 1. 9
 * @version     : 1.0 
 * <pre>
 * @description : ERROR 관리를 위한 컨트롤러 클래스
 * ===========================================================
 * DATE           AUTHOR       NOTE
 * -----------------------------------------------------------
 * 2022.01.09     박유석               최초 생성
 * </pre>
 */

@Controller
public class WebErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            int statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404error";
            } else {
                return "error/error";
            }
        }

        return "error/error";
    }

}