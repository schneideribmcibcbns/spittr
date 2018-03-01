package com.manning.sia.springmvc.spittr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.manning.sia.springmvc.spittr.controller.HomeController;

public class HomeControllerTest {

	@Test
	public void testHomePage() throws Exception {
		HomeController controller = new HomeController();
		MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
		mock.perform(get("/"))
			.andExpect(view().name("home"));
	}
}
