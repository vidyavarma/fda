package com.nrg.lau.commons;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public interface IGenericDAO<T> {
	
	public List<T> findAll(SimpleJdbcTemplate template);
	
	public T findById(SimpleJdbcTemplate template, String userName);

	public T create(SimpleJdbcTemplate template, T item);

	public boolean update(SimpleJdbcTemplate template, T item);

	public boolean remove(SimpleJdbcTemplate template, T item);
	
	public T findUser(SimpleJdbcTemplate template, String userName,String attribute);
	
}