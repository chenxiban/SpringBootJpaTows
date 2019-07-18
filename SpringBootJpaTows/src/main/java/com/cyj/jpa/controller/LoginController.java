package com.cyj.jpa.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyj.jpa.entity.Result;
import com.cyj.jpa.entity.Token;
import com.cyj.jpa.entity.User;
import com.cyj.jpa.md5.PasswordEncoder;
import com.cyj.jpa.service.UserService;
import com.cyj.jpa.util.IsEmptyUtils;
import com.cyj.jpa.util.JwtToken;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	private Date date = new Date();
	private Timestamp nousedate = new Timestamp(date.getTime());
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 登录操作
	 * http://localhost:8080/SpringBootJpaTows/login/loginUsers
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loginUsers", name = "登录用户")
	public Object loginUsers(User u) {
		int pwd = 0;// 密码错误次数
		String token = null;// token
		System.out.println("loginName>=" + u.getLoginName() + ",passwords>="
				+ u.getPassWords());
		if (IsEmptyUtils.isEmpty(u.getLoginName())) {
			return new Result(false, "用户名不能为空!");
		} else if (IsEmptyUtils.isEmpty(u.getPassWords())) {
			return new Result(false, "用户密码不能为空!");
		}
		
		User us = userService.getAllUser(u.getLoginName());
		System.out.println(us);
		if (!IsEmptyUtils.isEmpty(us)) {// 查询当前用户存在不
			int uid = us.getUserId();// 当前用户id
			// 盐值加密
			String loginName = u.getLoginName();
			String loginPwd = u.getPassWords();
			PasswordEncoder encoder = new PasswordEncoder(loginName, "MD5");
			String password = encoder.encode(loginPwd);
			
			System.out.println(password+"|"+us.getPassWords());
			if (password.equals(us.getPassWords())) {// 验证密码是否正确
				u.setPassWords(password);
				if (us.getPsdWrongTime() == 3) {
					return new Result(false, "当前用户已被锁定请联系管理员!");
				} else if (us.getPsdWrongTime() < 3) {
					int n = 0;
					User aUsers = new User();
					aUsers.setPsdWrongTime(n);
					aUsers.setIsLookout("否");
					aUsers.setUserId(uid);
					userService.updUserByPsdWrongTime(aUsers.getIsLookout(),aUsers.getLastLoginTime(),aUsers.getPsdWrongTime(),aUsers.getUserId());// 修改密码错误次数
				}
				if (us.getIsLookout().equals("否")) {// 用户状态未锁定允许登录
					System.out.println("登录是否成功>=" + userService.loginUser(u.getPassWords(),u.getLoginName()));
					if (userService.loginUser(u.getPassWords(),u.getLoginName()) == true) {// 当前用户登录是否成功
						us.setLastLoginTime(nousedate);
						userService.updUserByPsdWrongTime(us.getIsLookout(),us.getLastLoginTime(),us.getPsdWrongTime(),us.getUserId());// 修改登录时间
						
						// 根据用户Id查询出该用户的所有权限
						List<String> permissionValueList = userService
								.queryPermissionValueByUserId(us.getUserId());
						// 构造一个token对象,存储用户和权限信息
						Token tokenObj = new Token(us.getUserId(),
								permissionValueList);
						try {
							token = JwtToken.sign(tokenObj, 6 * 60 * 60 * 1000);// 1*60*60*1000
																			// 1个小时有效期的token
							System.out.println("生成token大小=>" + token.length());
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						List<Integer> urRoles=userService.getUserRole(us.getUserId());
						String today = formatter.format(date);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("loginId", us.getUserId());
						map.put("loginName", us.getLoginName());
						map.put("roleIds", urRoles);
						map.put("token", token);// 响应给客户端的token令牌
						map.put("permission", permissionValueList);// 响应给客户端的当前用户权限
						return new Result(true, map);
					} else {
						return new Result(false, "当前用户登录失败!");
					}
				} else {
					return new Result(false, "当前用户已被锁定请联系管理员!");
				}
			} else {
				pwd = us.getPsdWrongTime();
				pwd++;
				User aUsers = new User();
				aUsers.setIsLookout("否");
				aUsers.setPsdWrongTime(pwd);
				aUsers.setUserId(uid);;
				userService.updUserByPsdWrongTime(aUsers.getIsLookout(),aUsers.getLastLoginTime(),aUsers.getPsdWrongTime(),aUsers.getUserId());// 修改密码错误次数

				us = userService.getAllUser(u.getLoginName());// 二次查询当前用户信息,获得更新后的错误次数
				int n = us.getPsdWrongTime();// 密码错误次数
				if (n >= 3) {// 验证错误次数是否大于3
					us.setIsLookout("是");
					us.setLockTime(new Date());
					userService.updUserByPsdWrongTime(us.getIsLookout(),us.getLastLoginTime(),us.getPsdWrongTime(),us.getUserId());;
					return new Result(false, "当前用户已被锁定请联系管理员!");
				}
				return new Result(false, "用户密码错误!");
			}
		} else {
			return new Result(false, "当前用户不存在!");
		}
	}
	
	/**
	 * 
	 * 响应失败状态码 1未登录 2token过期 3无权访问
	 * 
	 * 
	 * 非法访问统一处理响应:无权访问 http://localhost:8080/SSMCyj/login/unLogin
	 * 
	 * @return
	 */
	@RequestMapping("/unLogin")
	public Object unLogin() {
		return new Result(false, "未登录或请求没有携带合法Token!");// 1未登录
	}

	/**
	 * 
	 * 响应失败状态码 1未登录 2token过期 3无权访问
	 * 
	 * 
	 * 非法访问统一处理响应:无权访问 http://localhost:8080/SSMCyj/login/tokenExpired
	 * 
	 * @return
	 */
	@RequestMapping("/tokenExpired")
	public Object tokenExpired() {
		return new Result(false, "请求Token过期或携带信息不安全!");// 2token过期
	}

	/**
	 * 
	 * 响应失败状态码 1未登录 2token过期 3无权访问
	 * 
	 * 
	 * 非法访问统一处理响应:无权访问 http://localhost:8080/SSMCyj/login/noPermission
	 * 
	 * @return
	 */
	@RequestMapping("/noPermission")
	public Object noPermission() {
		return new Result(false, "权限不足或无权访问请求,请获取合法身份登陆!");// 3无权访问
	}

	/**
	 * http://localhost:8080/SSMCyj/login/sysError 其它异常类友好提示信息
	 */
	@RequestMapping("/sysError")
	public Object sysError() {
		return new Result(false, "系统发生未知异常,我们已经杀了一个程序员祭天!请稍后重试!!!");
	}

	/**
	 * http://localhost:8080/SSMCyj/login/testError?num=4 测试其他类异常
	 */
	@RequestMapping("/testError")
	public Object testError(Integer num) {
		int k = 8 / num;
		return k;
	}

	public static void main(String[] args) {
		String[] pArr = { "users:addUsers", "user:delUsers" };
		List<String> pList = Arrays.asList(pArr);// 设置当前登录用户的权限集合
		String p = "users:delUsers";
		System.out.println(pList.contains(p));
	}
	
}
