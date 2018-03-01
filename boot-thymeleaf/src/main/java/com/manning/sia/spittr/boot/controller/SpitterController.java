package com.manning.sia.spittr.boot.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.manning.sia.spittr.boot.data.SpitterRepository;
import com.manning.sia.spittr.boot.domain.Spitter;

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
	public String processRegistration(@Valid Spitter spitter, Errors errors) 
		throws IllegalStateException, IOException {
		
		if (errors.hasErrors()) {
			return "registerForm";
		}
		
		spitterRepository.save(spitter);
		MultipartFile profilePicture = spitter.getProfilePicture();
		profilePicture.transferTo(new File("/" + spitter.getUsername() + ".jpg"));

		return "redirect:/spitter/" + spitter.getUsername();
	}
		
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		if (!model.containsAttribute("spitter")) {
			model.addAttribute(spitterRepository.findByUsername(username));
		}	
		
		return "profile";
	}
	
	@RequestMapping(value="/me", method=RequestMethod.GET)
	public String me() {
	    System.out.println("ME ME ME ME ME ME ME ME ME ME ME");
	    return "home";
	}
}
