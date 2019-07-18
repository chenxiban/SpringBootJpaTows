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
import org.springframework.web.bind.annotation.RestController;

import com.cyj.jpa.entity.Result;
import com.cyj.jpa.entity.User;
import com.cyj.jpa.entitysearch.UserSearch;
import com.cyj.jpa.md5.PasswordEncoder;
import com.cyj.jpa.service.UserService;
import com.cyj.jpa.util.IsEmptyUtils;

@RestController
@RequestMapping(value = "/users", name = "用户模块")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 分页检索查询 http://localhost:8080/SpringBootJpaTows/users/getAllPageUsers
	 * 
	 * @param userSearch
	 * @return
	 */
	@RequestMapping(value = "/getAllPageUsers", name = "查询用户")
	public Object getAllPageUsers(UserSearch userSearch) {
		System.out.println("当前查询参数===>" + userSearch);
		Pageable pageable = PageRequest.of(userSearch.getPage() - 1, userSearch.getRows(), Sort.Direction.ASC,
				"userId");
		Page<User> page = userService.sreachByUser(userSearch, pageable);
		Long total = page.getTotalElements();
		List<User> list = page.getContent();

		System.out.println("查询到的数据为=====>" + list);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 添加用户信息 http://localhost:8080/SpringBootJpaTows/users/addUsers
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/addUsers", name = "添加用户")
	public Object addUsers(User u) {
		PasswordEncoder encoder = new PasswordEncoder(u.getLoginName(), "MD5");
		u.setPassWords(encoder.encode("cyj123"));
		u.setCreateTime(new Date());
		u.setIsLookout("否");
		u.setLeaveChi(0);
		u.setLeaveZao(0);
		u.setLeaveBoolean("是");
		u.setLeaveState("否");
		u.setPsdWrongTime(0);
		System.out.println(u);
		System.out.println(encoder.encode("cyj123"));
		User ulist = userService.getAllUser(u.getLoginName());
		if (IsEmptyUtils.isEmpty(ulist)) {
			if (userService.addUser(u)) {
				return new Result(true, "用户添加成功");
			} else {
				return new Result(false, "用户添加失败");
			}
		} else {
			return new Result(false, "用户名重复,请重新填写");
		}
	}

	/**
	 * 测试添加 http://localhost:8080/SpringBootJpaTows/users/testAdd
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping("/testAdd")
	public Object addAllUserRoles(User u) {
		u.setLoginName("大炮啊");
		PasswordEncoder encoder = new PasswordEncoder(u.getLoginName(), "MD5");
		u.setPassWords(encoder.encode("cyj123"));
		u.setCreateTime(new Date());
		u.setIsLookout("否");
		u.setLeaveChi(0);
		u.setLeaveZao(0);
		u.setLeaveBoolean("是");
		u.setLeaveState("否");
		u.setPsdWrongTime(0);
		System.out.println(encoder.encode("cyj123"));

		/*
		 * Set<Roles> roleSet = new HashSet<Roles>(); Roles roles=new Roles();
		 * roles.setCreateTime(new Date()); roles.setName("管理员"); roleSet.add(roles);
		 * u.setRoleSet(roleSet);
		 */

		User ulist = userService.getAllUser(u.getLoginName());
		if (IsEmptyUtils.isEmpty(ulist)) {
			return userService.addUser(u);
		} else {
			return new Result(false, "用户名重复,请重新填写");
		}
	}

	/**
	 * 修改用户锁定状态 http://localhost:8080/SpringBootJpaTows/users/lockUsers
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/lockUsers", name = "锁定用户")
	public Object lockUsers(User u) {
		User user = userService.getUserById(u.getUserId());
		user.setIsLookout(u.getIsLookout());
		user.setPsdWrongTime(u.getPsdWrongTime());
		user.setUserId(u.getUserId());
		if (userService.addUser(user)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除用户 http://localhost:8080/SpringBootJpaTows/users/delUsers
	 * 
	 * @return
	 */
	@RequestMapping(value = "/delUsers", name = "删除用户")
	public Object delUsers(String userId) {
		List<String> list = new ArrayList<String>();
		String[] ids = userId.split(",");
		for (String dids : ids) {
			list.add(dids);
		}
		System.out.println(list);
		if (userService.delUser(list)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 重置密码 http://localhost:8080/SpringBootJpaTows/users/clearUsers
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/clearUsers", name = "重置密码")
	public Object clearUsers(User u) {
		User user = userService.getUserById(u.getUserId());
		PasswordEncoder encoder = new PasswordEncoder(u.getLoginName(), "MD5");
		user.setPassWords(encoder.encode("cyj666"));// 修改为加密后的密码
		System.out.println();
		user.setUserId(u.getUserId());
		if (userService.addUser(user)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 重置密码 http://localhost:8080/SpringBootJpaTows/users/updUserPass
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/updUserPass", name = "修改密码")
	public Object updUserPass(User u) {
		PasswordEncoder encoder = new PasswordEncoder(u.getLoginName(), "MD5");
		String pass = encoder.encode(u.getPassWords());// 获得页面修改的密码并进行加密

		User us = userService.getAllUser(u.getLoginName());

		User user = userService.getUserById(u.getUserId());

		if (!u.getPassWords().equals(u.getPassWords())) {
			return new Result(false, "两次密码不一致");
		} else {
			user.setPassWords(pass);// 修改为加密后的密码
			user.setUserId(u.getUserId());
			;
			if (us.getPassWords().equals(encoder.encode(u.getPass()))) {
				if (userService.addUser(user)) {
					return new Result(true, "修改成功,即将退出返回登录页面");
				} else {
					return new Result(false, "修改失败");
				}
			} else {
				return new Result(false, "旧密码填写错误");
			}
		}

	}

	/**
	 * 修改用户 http://localhost:8080/SpringBootJpaTows/users/updUsers
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/updUsers", name = "修改用户")
	public Object updUsers(User u) {
		User user = userService.getUserById(u.getUserId());
		if (!IsEmptyUtils.isEmpty(u.getLoginName())) {
			user.setLoginName(u.getLoginName());
		}
		if (!IsEmptyUtils.isEmpty(u.getProtectEmail())) {
			user.setProtectEmail(u.getProtectEmail());
		}
		if (!IsEmptyUtils.isEmpty(u.getProtectMtel())) {
			user.setProtectMtel(u.getProtectMtel());
		}

		user.setUserId(u.getUserId());
		if (userService.addUser(user)) {
			return new Result(true, "用户修改成功");
		} else {
			return new Result(false, "用户名重复,请重新填写!");
		}

	}

}
