package com.manning.sia.spittr.boot.data;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.manning.sia.spittr.boot.domain.Spittle;


public interface SpittleRepository {

	List<Spittle> findSpittles(long max, int count);
	
	@Cacheable("spittleCache")
	List<Spittle> findRecentSpittles();
	
	@Cacheable("spittleCache")
	Spittle findOne(Long id);
	
	@CachePut(value="spittleCache", key="#result.id")
	void save(Spittle spittle);
}
