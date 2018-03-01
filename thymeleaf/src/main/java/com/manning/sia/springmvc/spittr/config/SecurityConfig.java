package com.manning.sia.springmvc.spittr.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.manning.sia.springmvc.spittr.data.SpitterRepository;
import com.manning.sia.springmvc.spittr.security.SpitterUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Authentication method #1: In memory user data
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* It's equivalent to:
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemory = auth.inMemoryAuthentication();
		UserDetailsBuilder udb = inMemory.withUser("user");
		udb.password("password");
		udb.roles("USER");
		*/
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")
			.and().withUser("admin").password("password").roles("USER", "ADMIN");
	}
	
	// Authentication method #2: Store user data in a database
	/*
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select username, password, true as enabled from spitter where username=?")
				.authoritiesByUsernameQuery("select username, 'ROLE_USER' as authority from spitter where username=?")
				.passwordEncoder(new StandardPasswordEncoder("53cr3t"));
	}
	*/

	// Authentication method #3: Store user data in LDAP
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication()
				.userSearchBase("ou=people")
				.userSearchFilter("uid={0}")
				.groupSearchBase("ou=groups")
				.groupSearchFilter("member={0}");
		// more stuff can be configured.
	}
	*/

	// // Authentication method #4: Using user details service 
	/* 
	@Autowired
	private SpitterRepository spitterRepository;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new SpitterUserService(spitterRepository));
	}
	*/

	@Override protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginPage("/login")
			.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
			.and()
			.rememberMe()
				.tokenRepository(new InMemoryTokenRepositoryImpl())
				.key("spittrKey")
				.tokenValiditySeconds(2419200)
			.and()
			.httpBasic()
				.realmName("Spittr")
			.and()
			.authorizeRequests()
				.antMatchers("/").authenticated()
				.antMatchers("/spitter/me").authenticated()
				.antMatchers(HttpMethod.POST, "/spittles").authenticated()
				.anyRequest().permitAll();		
	}
}
