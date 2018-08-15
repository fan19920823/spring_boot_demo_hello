package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.DataService;
import com.example.service.UserRedisService;
import com.example.service.UserService;

/** 
* @ClassName: UserController 
* @Description: User控制器1
* @author mengfanzhu
* @date 2017年2月20日 下午5:58:19 
*/
@RestController
@RequestMapping("/user")
public class UserController {
	
	protected static Logger logger=LoggerFactory.getLogger(UserController.class);  
	@Autowired
	private UserService userService;
	@Autowired
	private DataService dataService;
	@Autowired
	private UserRedisService userRedisService;
	
	@RequestMapping("/demo/{name}")
	@ResponseBody
	public String demoShowName(@PathVariable String name){
		 logger.debug("访问getUserByName,Name={}",name);  
		 return "name is " + name;
	}
	/** 
	 * @Title: UserController
	 * @Description: 数据初始化
	 * @author mengfanzhu
	 * @throws 
	 */
	@RequestMapping("/initdata")
	@ResponseBody
	public String initData(){
		dataService.initData();
		return "success";
	}
	
	/** 
	 * @Title: UserController
	 * @Description: 由loginName获取user
	 * @param loginName 
	 * @author mengfanzhu
	 * @throws 
	 */
	@RequestMapping("/getUserByLoginName/{loginName}")
	@ResponseBody
	public Map<String,Object> getUserByName(@PathVariable String loginName){
		Map<String,Object> result = new HashMap<String, Object>();
		long beginTime=System.currentTimeMillis();
		User user = userService.readByLoginName(loginName);
		long time=System.currentTimeMillis()-beginTime;
		System.out.println(time);
		Assert.notNull(user);
		result.put("name", user.getName());
		result.put("loginName", user.getLoginName());

		result.put("departmentName",user.getDepartment().getName());
		//result.put("roleName", user.getRoleList().get(0).getName());
		return result;
	}
	/**
	 * @Title: UserController
	 * @Description: 由loginName获取user
	 * @param loginName
	 * @author mengfanzhu
	 * @throws
	 */
	@RequestMapping("/getListUserByLoginName/{loginName}")
	@ResponseBody
	public List<Map<String,Object>> getListUserByName(@PathVariable String loginName){
		Map<String,Object> result = new HashMap<String, Object>();
		long beginTime=System.currentTimeMillis();
		List<User> users = userService.findListByLoginName(loginName);
		long time=System.currentTimeMillis()-beginTime;
		System.out.println(time);
		//Assert.notNull(user);

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
       for (User user : users) {
		  result.put("name", user.getName());
		  result.put("loginName", user.getLoginName());
		  result.put("departmentName", user.getDepartment().getName());
		  list.add(result);
		}
		//result.put("roleName", user.getRoleList().get(0).getName());
		return list;
	}
	/** 
	 * @Title: UserController
	 * @Description: 初始化redis数据
	 * @return  
	 * @author mengfanzhu
	 * @throws 
	 */
	@RequestMapping("/initRedisdata")
	@ResponseBody
	public String initRedisData(){
		userRedisService.redisInitData();
		return "success";
	}
	@RequestMapping("/getUserRedisByLoginName/{loginName}")
	@ResponseBody
	public Map<String,Object> getUserRedisByLoginName(@PathVariable String loginName){
		Map<String,Object> result = new HashMap<String, Object>();
		User user = userRedisService.getUserRedis(loginName);
		Assert.notNull(user);
		result.put("name", user.getName());
		result.put("loginName", user.getLoginName());
		result.put("departmentName",user.getDepartment().getName());
		//result.put("roleName", user.getRoleList().get(0).getName());
		return result;
	}
	@RequestMapping("/getbyid/{id}")
	@ResponseBody
	public User getbyid(@PathVariable String id){
		User user = userService.getById(id);
		return user;
	}
	@RequestMapping("/delbyid/{id}")
	@ResponseBody
	public Integer delByUserId(@PathVariable String id){
		userService.delByUserId(id);
		return 1;
	}
	@RequestMapping("/updateUser/{id}/{name}")
	@ResponseBody
	public Integer updateUser(@PathVariable String id,@PathVariable String name){
		//User user=userService.getById(id);
		//user.setLoginName(name);
		userService.updateUser(name,id);
		return 1;
	}
}
