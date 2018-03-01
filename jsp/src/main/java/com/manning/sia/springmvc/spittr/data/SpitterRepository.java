package com.manning.sia.springmvc.spittr.data;

import com.manning.sia.springmvc.spittr.domain.Spitter;

public interface SpitterRepository {

	Spitter save(Spitter spitter);
	Spitter findByUsername(String username);
}
