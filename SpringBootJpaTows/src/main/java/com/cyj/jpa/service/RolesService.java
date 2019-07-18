package com.cyj.jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyj.jpa.entity.Roles;
import com.cyj.jpa.entitysearch.RolesSearch;

@Service
public interface RolesService {
	
	/**
	 * 多条件分页检索查询
	 * @param rolesSearch
	 * @param pageable
	 * @return
	 */
	public Page<Roles> sreachByRoles(RolesSearch rolesSearch, Pageable pageable);
	
	/**
	 * 根据name查询
	 * @param u
	 * @return
	 */
	public Roles getAllRoles(String name);
	
	/**
	 * 根据id获取用户表未被设置的角色信息
	 * @param ids
	 * @return
	 */
	public List<Roles> getRolesList(List<Integer> ids);
	
	/**
	 * 获取所有用户信息
	 * @return
	 */
	public List<Roles> getRolesLists();
	
	/**
	 * 根据用户id查询该用户拥有的角色
	 * @param id
	 * @return
	 */
	public List<Roles> getUserRolesByUserId(Integer id);
	
	/**
	 * 添加角色信息
	 * 
	 * @param u
	 * @return
	 */
	public boolean addUser(Roles r);

	/**
	 * 删除角色信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean delRole(List<String> stuList);
	
	/**
	 * 根据id查询
	 * @param rid
	 * @return
	 */
	public Roles getRolesById(Integer rid);
	
	/**
	 * 删除角色
	 * @param stuList
	 * @return
	 */
	public boolean deleteByRoleId(Integer stuList,Integer userId);
	
	/**
	 * 增加角色
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public boolean addByRole(Integer roleId,Integer userId);
	
	/**
	 * 设置模块
	 * @param roleId
	 * @param moduleIds
	 * @return
	 */
	public Integer setRoleModule(Integer roleId,Integer moduleIds);
	
	/**
	 * 根据roleId删除模块
	 * @param roleId
	 * @return
	 */
	public boolean delRoleModule(Integer roleId);
	
}
