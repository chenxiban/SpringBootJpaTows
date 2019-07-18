package com.cyj.jpa.tools;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 备注：@Configuration，@Bean这两个注解代替xml配置，@Configuration 相当于xml中的标签
 * @Bean相当于标签 ，通常两者结合使用。
 * @author Chenyongjia
 *
 */
@Configuration // 相当于xml文件中的<beans>
public class ConfigMyTools {
	
	/**
	 * 格式化当前日期
	 * 
	 * @return
	 */
	@Bean("dateFormat")
	public SimpleDateFormat dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
}
