package com.cyj.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyj.jpa.dao.PermissionRepository;
import com.cyj.jpa.entity.Permission;
import com.cyj.jpa.entitysearch.Node;
import com.cyj.jpa.entitysearch.NodeInterf;
import com.cyj.jpa.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	/**
	 * 根据角色Ids查询出角色设置的权限树
	 */

	public List<Permission> queryRoleSetPermission(List<Integer> roleId) {
		List<Integer> permissionIds = permissionRepository.queryPermissionIdsByRoleIds(roleId);// 查询出角色拥有的权限Ids
		List<Permission> permissionTree = permissionRepository.findsBy();// 查询出所有的权限树
		List<Permission> nodes = new ArrayList<Permission>();
		for (int i = 0; i < permissionTree.size(); i++) {
			Permission node=new Permission();
			System.out.println("Permission对象为===>"+node);
			node.setText(permissionTree.get(i).getPermission_module());
			// 为node对象设置children属性
			node.setChildren(this.findsByPermissionModules(permissionTree.get(i).getPermission_module()));
			System.out.println("查到的Children属性为====>"+node.getChildren());
			System.out.println("node对象为===>"+node);// 错误在这里
			nodes.add(node);
		}
		System.out.println("nodes集合为===>"+nodes);

		// 把角色拥有的权限树设置为选中
		this.setPermissionTreeChecked(nodes, permissionIds);
		return nodes;
	}

	/**
	 * 把角色拥有的权限树设置为选中
	 * 
	 * @param permissionTree
	 * @param permissionIds
	 */
	private void setPermissionTreeChecked(List<Permission> permissionTree, List<Integer> permissionIds) {
		for (Permission module : permissionTree) {// 遍历出所有权限所属模块
			System.out.println("module=============>"+module);
			for (Permission p : module.getChildren()) {// 遍历出所有权限节点
				System.out.println("module.getChildren()==========>"+module.getChildren());
				if (permissionIds.contains(p.getId())) {
					p.setChecked(true);// 角色拥有的权限勾选中
				}
			}

		}
	}

	@Override
	public boolean deletePermission(Integer roleId) {
		try {
			permissionRepository.deletePermission(roleId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean setRolePermission(Integer roleId, Integer permissionIds) {
		try {
			permissionRepository.setRolePermission(roleId, permissionIds);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Permission> findsByPermissionModules(String permissionModule) {
		List<Permission>  objectsList = permissionRepository.findsByPermissionModule(permissionModule);
		for (int i = 0; i < objectsList.size(); i++) {
			objectsList.get(i).setId(objectsList.get(i).getPermission_id());
			objectsList.get(i).setText(objectsList.get(i).getPermission_name());
			objectsList.get(i).setPermission_last_update_time(objectsList.get(i).getPermission_last_update_time());
		}
		return objectsList;
	}

}
