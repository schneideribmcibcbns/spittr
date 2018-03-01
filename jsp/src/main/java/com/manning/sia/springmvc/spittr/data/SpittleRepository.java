package com.manning.sia.springmvc.spittr.data;

import java.util.List;

import com.manning.sia.springmvc.spittr.domain.Spittle;

public interface SpittleRepository {

	List<Spittle> findSpittles(long max, int count);
	List<Spittle> findRecentSpittles();
	Spittle findOne(Long id);
	void save(Spittle spittle);
}
