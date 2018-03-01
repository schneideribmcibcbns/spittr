package com.manning.sia.spittr.boot.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.manning.sia.spittr.boot.domain.Spittle;


@Repository
public class JdbcSpittleRepository implements SpittleRepository {
	private JdbcOperations jdbc;
	
	@Autowired
	public JdbcSpittleRepository(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}
	
	private static class SpittleRowMapper implements RowMapper<Spittle> {
		@Override
		public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Spittle(
				rs.getLong("id"),
				rs.getString("message"),
				rs.getDate("created_at"),
				rs.getDouble("latitude"),
				rs.getDouble("longitude"));
		}
	}
	
	@Override
	public List<Spittle> findSpittles(long max, int count) {
		return jdbc.query(
			"select id, message, created_at, latitude, longitude from spittle where id < ? order by created_at desc limit ?",
			new SpittleRowMapper(), 
			max,
			count);
	}

	@Override
	public List<Spittle> findRecentSpittles() {
		return jdbc.query(
			"select id, message, created_at, latitude, longitude from spittle order by created_at desc limit ?", 
			new SpittleRowMapper());
	}

	@Override
	public Spittle findOne(Long id) {
		return jdbc.queryForObject(
			"select id, message, created_at, latitude, longitude from spittle where id = ?", 
			new SpittleRowMapper(),
			id);
	}

	@Override
	public void save(Spittle spittle) {
		jdbc.update(
			"insert into spittle(message, created_at, latitude, longitude) values(?, ?, ?, ?)",
			spittle.getMessage(),
			spittle.getTime(),
			spittle.getLatitude(),
			spittle.getLongitude()
		);
	}

}
