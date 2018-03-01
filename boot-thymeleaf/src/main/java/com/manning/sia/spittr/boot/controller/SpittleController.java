package com.manning.sia.spittr.boot.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.manning.sia.spittr.boot.data.SpittleRepository;
import com.manning.sia.spittr.boot.domain.Spittle;
import com.manning.sia.spittr.boot.exception.SpittleNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/spittles")
public class SpittleController {

	private final String MAX_LONG_AS_STRING = "9223372036854775807"; 
	private SpittleRepository spittleRepository;
	
	@Autowired
	public SpittleController(SpittleRepository spittleRepository) {
		this.spittleRepository = spittleRepository;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String spittles(
			Model model, 
			@RequestParam(name="max", defaultValue=MAX_LONG_AS_STRING) long max, 
			@RequestParam(name="count", defaultValue="20") int count) {
		model.addAttribute(spittleRepository.findSpittles(max, count));
		return "spittles";
	}

	/* Alternative way 
	@RequestMapping(method=RequestMethod.GET)
	public List<Spittle> spittles() {
		return spittleRepository.findSpittles(Long.MAX_VALUE, 20);
	}
	*/
	
	@RequestMapping(value="/{spittleId}", method=RequestMethod.GET) 
	public String spittle(@PathVariable("spittleId") long spittleId, Model model) {
		Spittle spittle = null;
		try {
			spittle = spittleRepository.findOne(spittleId);
		}
		catch (Exception ex) {
			throw new SpittleNotFoundException();
		}
		
		if (spittle==null) {
			throw new SpittleNotFoundException(); 
		}
		
		model.addAttribute(spittle);
		return "spittle";
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	public String addSpittle(SpittleForm form, Model model) {
		spittleRepository.save(new Spittle(null, form.getMessage(), new Date(), form.getLatitude(), form.getLongitude()));
		
		return "redirect:/spittles";
	}
}
