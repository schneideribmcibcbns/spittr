package com.manning.sia.spittr.boot.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.manning.sia.spittr.boot.domain.Spitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JdbcSpitterRepository implements SpitterRepository {
	private JdbcOperations jdbcTemplate;
	
	@Autowired
	public JdbcSpitterRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Spitter save(Spitter spitter) {
		jdbcTemplate.update(
			"insert into spitter(username, password, first_name, last_name, email) values(?, ?, ?, ?, ?)",
			spitter.getUsername(),
			spitter.getPassword(),
			spitter.getFirstName(),
			spitter.getLastName(),
			spitter.getEmail()
		);
		
		return spitter;
	}

	@Override
	public Spitter findByUsername(String username) {
		return jdbcTemplate.queryForObject(
			"select id, username, null, first_name, last_name, email from spitter where username=?",
			new SpitterRowMapper(), 
			username);
	}
	
	private static class SpitterRowMapper implements RowMapper<Spitter>{
		@Override
		public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Spitter(
				rs.getLong("id"),
				rs.getString("username"),
				null,
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("email")
			);
		}
	}

}
