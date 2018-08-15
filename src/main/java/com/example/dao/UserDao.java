package com.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.User;
@Repository
public interface UserDao extends JpaRepository<User, Long> {
	
	User findByLoginNameLike(String name);

	User readByLoginName(String name);

	List<User> findListByLoginName(String name);

	List<User> getByCreatedateLessThan(Date star);
	 void deleteUserById(long id);
	 User getById(long id);
	 @Modifying
	 @Query("update User set name=:name where id=:id")
	 void updateUser(@Param(value = "name")String name,@Param(value = "id")Long id);
}
