package com.manning.sia.springmvc.spittr.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.manning.sia.springmvc.spittr.data.SpitterRepository;
import com.manning.sia.springmvc.spittr.domain.Spitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/spitter")
public class SpitterController {

	private SpitterRepository spitterRepository;
	
	@Autowired
	public SpitterController(SpitterRepository repo) {
		spitterRepository = repo;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute(new Spitter());
		return "registerForm";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String processRegistration(@Valid Spitter spitter, Errors errors) {
		log.info(errors.toString());
/*		log.info(spitter.toString());
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<Spitter>> violations = validator.validate(spitter);
		for (ConstraintViolation<Spitter> violation:violations) {
			log.error(violation.getMessage());
		}*/
		if (errors.hasErrors()) {
			return "registerForm";
		}
		
		spitterRepository.save(spitter);
		return "redirect:/spitter/" + spitter.getUsername();
	}
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		Spitter spitter = spitterRepository.findByUsername(username);
		model.addAttribute(spitter);
		
		return "profile";
	}
}
