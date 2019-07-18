package com.cyj.jpa.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.cyj.jpa.entity.Modules;

@Service
public interface ModulesService {
	
	/**
	 * 显示所有菜单
	 * @return
	 */
	public List<Modules> showModules(); 
	
	/**
	 * 根据角色id查询拥有的模块
	 * @param roles
	 * @return
	 */
	public List<Modules> getModulesRoles(List<Integer> roles);
	
	/**
	 * 根据id查询子菜单
	 * 
	 * @param id
	 * @param moduleId
	 * @return
	 */
	public List<Modules> queryChildrenById(int id, List<Integer> moduleId);
	
	/**
	 * 根据根节点查询
	 * @param parentId
	 * @return
	 */
	public List<Modules> queryChildren(Integer parentId);
	
	/**
	 * 添加模块
	 * @param m
	 * @return
	 */
	public boolean addModules(Modules m);
	
	/**
	 * 修改模块
	 * @param m
	 * @return
	 */
	public boolean updModules(Modules m);
	
	/**
	 * 根据id查询模块
	 * @param parentId
	 * @return
	 */
	public Modules getById(Integer id);
	
	/**
	 * 批量删除模块
	 * @param id
	 * @return
	 */
	public boolean delModules(List<String> id); 
	
	/**
	 * 根据父id查询子菜单
	 * @param parentId
	 * @return
	 */
	public List<String> getChildrenByParentId(List<String> parentId);
	
	/**
	 * 根据父id查询孙子菜单
	 * @param parentId
	 * @return
	 */
	public List<String> getChildrenByParentIds(List<String> parentId); 
	
	/**
	 * 根据roleId查询模块
	 * @param roleId
	 * @return
	 */
	public List<Modules> queryRoleSetModuleTree(List<Integer> roleId);
	
}
