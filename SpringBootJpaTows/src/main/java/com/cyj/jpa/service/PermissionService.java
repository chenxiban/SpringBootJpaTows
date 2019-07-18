package com.cyj.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cyj.jpa.entity.Permission;

@Service
public interface PermissionService {
	
	/**
	 * 根据角色id查询权限
	 * @param roleIds
	 * @return
	 */
	public List<Permission> queryRoleSetPermission(List<Integer> roleIds); 
	
	/**
	 * 根据text名称查询所属孩子节点
	 * @param permissionModule
	 * @return
	 */
	public List<Permission> findsByPermissionModules(String permissionModule);
	
	/**
	 * 移除角色模块
	 * @param roleId
	 * @return
	 */
	public boolean deletePermission(Integer roleId);
	
	/**
	 * 设置权限
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	public boolean setRolePermission(Integer roleId, Integer permissionIds);
	
}
