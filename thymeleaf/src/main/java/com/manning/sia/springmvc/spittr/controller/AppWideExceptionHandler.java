package com.manning.sia.springmvc.spittr.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.manning.sia.springmvc.spittr.exception.DuplicateSpittleException;

@ControllerAdvice
public class AppWideExceptionHandler {

	@ExceptionHandler(DuplicateSpittleException.class) 
	public String handleNotFound() {
		return "error/duplicate";
	}
}
