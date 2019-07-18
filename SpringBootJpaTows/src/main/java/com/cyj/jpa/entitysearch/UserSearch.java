package com.cyj.jpa.entitysearch;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @Description: 查询条件实体类
 * @ClassName: UserSearch.java
 * @author Chenyongjia
 * @Date 2018年11月12日 下午22:01
 * @Email 867647213@qq.com
 */
@Data // 自动生成set方法,自动生成get方法
@AllArgsConstructor // 自动所有参数的构造方法方法
@NoArgsConstructor // 自动无参的构造方法方法
public class UserSearch {

	private String loginName;
	private String isLookout;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthStart;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthEnd;
	private String email;
	private String mtel;
	private int page = 0;
	private int rows = 10;

}
