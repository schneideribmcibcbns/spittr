package com.manning.sia.spittr.boot.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.manning.sia.spittr.boot.exception.DuplicateSpittleException;


@ControllerAdvice
public class AppWideExceptionHandler {

	@ExceptionHandler(DuplicateSpittleException.class) 
	public String handleNotFound() {
		return "error/duplicate";
	}
}
