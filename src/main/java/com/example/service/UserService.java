package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {
	
	User readByLoginName(String name);
	List<User> findListByLoginName(String name);
	void delByUserId(String id);
	User getById(String id);
	void updateUser(String name,String id);
}
