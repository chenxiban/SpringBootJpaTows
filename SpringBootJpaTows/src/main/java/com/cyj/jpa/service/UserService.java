package com.cyj.jpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyj.jpa.entity.User;
import com.cyj.jpa.entitysearch.UserSearch;

@Service
public interface UserService {

	/**
	 * 多条件分页检索查询
	 * @param loginName
	 * @param isLookout
	 * @param birthStart
	 * @param birthEnd
	 * @param email
	 * @param mtel
	 * @param page
	 * @param rows
	 * @return
	 */
	public Page<User> sreachByUser(UserSearch userSearch, Pageable pageable);
	
	/**
	 * 根据user查询
	 * @param u
	 * @return
	 */
	public User getAllUser(String name);
	
	/**
	 * 添加用户信息
	 * 
	 * @param u
	 * @return
	 */
	public boolean addUser(User u);

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean delUser(List<String> stuList);

	/**
	 * 根据用户id查询用户角色
	 * @param id
	 * @return
	 */
	public List<Integer> getUserRole(Integer id);
	
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id);
	
	/**
	 * 用户登录
	 * @param u
	 * @return
	 */
	public boolean loginUser(String passWords,String loginName);// 用户登录

	/**
	 * 修改错误次数
	 * @param lastLoginTime
	 * @param psdWrongTime
	 * @param id
	 * @return
	 */
	public boolean updUserByPsdWrongTime(String IsLookout,Date lastLoginTime,Integer psdWrongTime,Integer id);
	
	/**
	 * 查询用户权限
	 * @param id
	 * @return
	 */
	public List<String> queryPermissionValueByUserId(Integer id);
	
}
