package com.cyj.jpa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyj.jpa.entity.Permission;
import com.cyj.jpa.entity.Result;
import com.cyj.jpa.service.PermissionService;
import com.cyj.jpa.service.UserService;
import com.cyj.jpa.util.IsEmptyUtils;

@RestController
@RequestMapping(value = "/permission", name = "权限模块")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	@Autowired
	private UserService userService;

	/**
	 * http://localhost:8080/SpringBootJpaTows/permission/queryNodess 更新系统权限信息
	 */
	@RequestMapping(value = "/queryNodess", name = "查询所有权限")
	public Object queryNodess(@RequestParam(value = "roleIds", required = false) Integer[] roleId) {
		List<Integer> roleIds = new ArrayList<Integer>();
		for (int i = 0; i < roleId.length; i++) {
			roleIds.add(roleId[i]);
		}
		System.out.println("roleId=>" + roleIds);
		List<Permission> list = permissionService.queryRoleSetPermission(roleIds);
		System.out.println("list=>" + list);
		return list;
	}

	/**
	 * 角色设置操作权限 http://localhost:8080/SpringBootJpaTows/permission/setRolePermission
	 * 
	 * @return
	 */
	@RequestMapping(value = "/setRolePermission", name = "角色设置操作权限")
	public Object setRolePermission(Integer roleId, String permissionIds, int id) {
		if (permissionService.deletePermission(roleId)) {
			List<Integer> list = new ArrayList<Integer>();
			String[] ids = permissionIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				if (!IsEmptyUtils.isEmpty(ids[i])) {
					list.add(Integer.parseInt(ids[i]));
				}
			}
			System.out.println("list=>" + list);
			//String msg=null;
			boolean k;
			for (Integer permissionId2 : list) {
				k = permissionService.setRolePermission(roleId, permissionId2);
				//msg = "角色roleId=>" + roleId + "->成功设置" + k + "个操作权限.";
				if (!k) {
					return new Result(false, "设置失败");// 设置成功
				}
			}
			// 根据用户Id查询出该用户的所有权限
			List<String> permissionValueList = userService.queryPermissionValueByUserId(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("permission", permissionValueList);// 响应给客户端的当前用户权限
			return new Result(true, map);// 设置成功
		} else {
			return new Result(false, "设置失败");// 设置成功
		}
	}

}
