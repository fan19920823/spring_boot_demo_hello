package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Cacheable(value = "UserCurret",keyGenerator = "accountKeyGenerator")
	@Override
	public User readByLoginName(String name) {
		return userDao.readByLoginName(name);
	}
	@Cacheable(value = "UserList",keyGenerator = "accountKeyGenerator")
	@Override
	public List<User> findListByLoginName(String name) {
		return userDao.findListByLoginName(name);
	}
	@Cacheable(value = "UserCurret",key = "'userid_'+#id")
	@Override
	public User getById(String id) {
		return userDao.getById(Long.parseLong(id));
	}
	@CacheEvict(value = "UserCurret",key = "'userid_'+#id")
	public void delByUserId(String id){
		userDao.deleteUserById(Long.parseLong(id));
	}
	@CacheEvict(value = "UserCurret",key = "'userid_'+#id")
	public void updateUser(String name,String id){
		userDao.updateUser(name,Long.parseLong(id));
	}
}
