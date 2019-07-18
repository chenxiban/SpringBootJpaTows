package com.cyj.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 应用启动类
 * @Description:   主模块
 * @ClassName:     Application.java 
 * @author         Chenyongjia 
 * @Date           2018-11-12 下午14:10  
 * @Email          chen867647213@163.com
 */
@SpringBootApplication
@EnableTransactionManagement// 启注解事务管理,等同于xml配置方式的<tx:annotation-driven />
@EnableJpaRepositories(basePackages = "com.cyj.jpa.dao")	// Spring Jpa 启用注解
@EntityScan(basePackages = "com.cyj.jpa.entity")		//扫描Jpa实体对象
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
	
}
