package com.manning.sia.spittr.boot.data;

import com.manning.sia.spittr.boot.domain.Spitter;

public interface SpitterRepository {

	Spitter save(Spitter spitter);
	Spitter findByUsername(String username);
}
