package com.manning.sia.spittr.boot.domain;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.Data;

@Data
public class Spittle {

	private final Long id;
	
	@NotNull
	@Size(min=1, max=140)
	private final String message;
	
	private final Date time;
	
	@Min(-180)
	@Max(180)
	private Double latitude;
	
	@Min(-90)
	@Max(90)
	private Double longitude;
	
	public Spittle(Long id, String message, Date createdAt, Double longitude, Double latitude) {
		this.id = id;
		this.message = message;
		this.time = createdAt;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Spittle(String message, Date createdAt, Double longtiude, Double latitude) {
		this(null, message, createdAt, longtiude, latitude);
	}
	
	@Override
	public boolean equals(Object that) {
	return EqualsBuilder.reflectionEquals(this, that, "id", "createdAt");
	}
	@Override
	public int hashCode() {
	return HashCodeBuilder.reflectionHashCode(this, "id", "createdAt");
	}
}
