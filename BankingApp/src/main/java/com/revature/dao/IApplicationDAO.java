package com.revature.dao;

import java.util.List;

import com.revature.models.Application;

public interface IApplicationDAO {

	public List<Application> findAll();
	public Application findById(int id);
	public int insert (Application a);
	public boolean update(Application a);
	public boolean delete(int id);
}
