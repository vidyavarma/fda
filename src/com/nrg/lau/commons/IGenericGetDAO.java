package com.nrg.lau.commons;

import java.util.List;

public interface IGenericGetDAO<T> {
	
	public List<T> findAll();	
	
	public T findById(String userName);
	
	public T findUser(String userName,String attribute);	

}