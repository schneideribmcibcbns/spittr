package com.manning.sia.spittr.boot.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SpittleForm {

	@NotNull
	@Size(min=1, max=140)
	private String message;
	
	@Min(-180)
	@Max(180)
	private Double latitude;
	
	@Min(-90)
	@Max(90)
	private Double longitude;
}
