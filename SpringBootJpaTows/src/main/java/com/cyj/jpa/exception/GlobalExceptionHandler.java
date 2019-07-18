package com.cyj.jpa.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * SpringBoot框架对全局异常 捕获提供了很好的支持，
 * 并且操作非常简单。我们只需要创建一个类和一个方法，并添加两个注解：
 * 
 * @ControllerAdvice底层实现原理是AOP面向切面技术
 * @ExceptionHandler即可，如： 
 * @author  
  *  陈永佳 2018-10-18
 * 
 */
@ControllerAdvice // 还可以指定具体的捕获包
//@ResponseBody // 写该注解转字符串
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class) // 捕获所有运行时异常,任意异常随意写
	public String exceptionHandler() {
		return "error";
	}

}
