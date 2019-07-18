package com.cyj.jpa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyj.jpa.entity.Result;
import com.cyj.jpa.entity.Roles;
import com.cyj.jpa.entitysearch.RolesSearch;
import com.cyj.jpa.service.RolesService;
import com.cyj.jpa.service.UserService;
import com.cyj.jpa.util.IsEmptyUtils;

@RestController
@RequestMapping(value = "/roles", name = "角色模块")
public class RolesController {

	@Autowired
	private UserService userService;
	@Autowired
	private RolesService rolesService;

	/**
	 * 分页检索查询 http://localhost:8080/SpringBootJpaTows/roles/getAllPageRoles
	 * 
	 * @param userSearch
	 * @return
	 */
	@RequestMapping(value = "/getAllPageRoles", name = "查询角色")
	public Object getAllPageRoles(RolesSearch rolesSearch) {
		System.out.println("当前查询参数===>" + rolesSearch);
		Pageable pageable = PageRequest.of(rolesSearch.getPage() - 1, rolesSearch.getRows(), Sort.Direction.ASC,
				"roleId");
		Page<Roles> page = rolesService.sreachByRoles(rolesSearch, pageable);
		Long total = page.getTotalElements();
		List<Roles> list = page.getContent();

		System.out.println("查询到的数据为=====>" + list);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * http://localhost:8080/SpringBootJpaTows/roles/getRolesList 不带分页
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getRolesList", name = "不分页查询角色")
	public Object getRolesList(Integer[] roleId,@RequestParam(value = "id", required = false) Integer id) {
		List<Integer> urRoles = userService.getUserRole(id);
		System.out.println(urRoles);
		if (!IsEmptyUtils.isEmpty(urRoles)) {
			return rolesService.getRolesList(urRoles);
		} else {
			return rolesService.getRolesLists();
		}
	}

	/**
	 * http://localhost:8080/SpringBootJpaTows/roles/getUserRolesByUserId
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserRolesByUserId", name = "获取用户角色")
	public Object getUserRolesByUserId(Integer id) {
		return rolesService.getUserRolesByUserId(id);
	}

	/**
	 * 添加角色 http://localhost:8080/SpringBootJpaTows/roles/addRoles
	 * 
	 * @param r
	 * @return
	 */
	@RequestMapping(value = "/addRoles", name = "添加角色")
	public Object addRoles(Roles r) {
		r.setCreateTime(new Date());
		Roles rlist = rolesService.getAllRoles(r.getName());
		if (IsEmptyUtils.isEmpty(rlist)) {
			if (rolesService.addUser(r)) {
				return new Result(true, "角色添加成功");
			} else {
				return new Result(false, "角色添加失败");
			}
		} else {
			return new Result(false, "角色名重复,请重新填写");
		}
	}

	/**
	 * 删除角色 http://localhost:8080/SpringBootJpaTows/roles/delRoles
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/delRoles", name = "删除角色")
	public Object delRoles(String roleId) {
		List<String> list = new ArrayList<String>();
		String[] ids = roleId.split(",");
		for (String dids : ids) {
			list.add(dids);
		}
		System.out.println(list);
		if (rolesService.delRole(list)) {
			return new Result(true, "角色删除成功");
		} else {
			return new Result(false, "当前角色有用户正在使用,删除失败!");
		}
	}

	/**
	 * 修改角色 http://localhost:8080/SpringBootJpaTows/roles/updRoles
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/updRoles", name = "修改角色")
	public Object updRoles(Roles u) {
		Roles roles = rolesService.getRolesById(u.getRoleId());
		roles.setName(u.getName());
		roles.setRoleId(u.getRoleId());
		if (rolesService.addUser(roles)) {
			return new Result(true, "角色修改成功");
		} else {
			return new Result(false, "角色名重复,请重新填写!");
		}
	}

	/**
	 * 增加角色 http://localhost:8080/SpringBootJpaTows/roles/addByRole
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/addByRole", name = "增加角色")
	public Object addByRole(Integer userId, Integer roleId, Integer id) {
		if (rolesService.addByRole(roleId, userId)) {
			// 根据用户Id查询出该用户的所有权限
			List<String> permissionValueList = userService.queryPermissionValueByUserId(userId);
			List<Integer> urRoles = userService.getUserRole(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleIds", urRoles);
			map.put("permission", permissionValueList);// 响应给客户端的当前用户权限
			return new Result(true, map);
		} else {
			return new Result(false, "角色增加失败");
		}
	}

	/**
	 * 移除角色 http://localhost:8080/SpringBootJpaTows/roles/delRolesId
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/delRolesId", name = "移除角色")
	public Object delRolesId(Integer roleId, Integer userId, Integer id) {
		if (rolesService.deleteByRoleId(roleId, userId)) {
			// 根据用户Id查询出该用户的所有权限
			List<String> permissionValueList = userService.queryPermissionValueByUserId(id);
			List<Integer> urRoles = userService.getUserRole(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleIds", urRoles);
			map.put("permission", permissionValueList);// 响应给客户端的当前用户权限
			return new Result(true, map);
		} else {
			return new Result(false, "角色移除失败");
		}
	}
	
	/**
	 * http://localhost:8080/SpringBootJpaTows/role/setRoleModule
	 * 
	 * @return
	 */
	@RequestMapping(value = "/setRoleModule", name = "角色设置菜单模块")
	public Object setRoleModule(
			Integer roleId,
			@RequestParam(value = "moduleId", required = false) Integer[] moduleId) {
		String msg = null;
		System.out.println("移除当前角色权限====>"+rolesService.delRoleModule(roleId));
		for (Integer moduleIds:moduleId) {
			int k = rolesService.setRoleModule(roleId, moduleIds);
			msg = "角色roleId=>" + roleId + "->成功设置" + k + "个菜单模块.";
		}
		return new Result(true, msg);// 设置成功
	}

}
