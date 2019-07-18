package com.cyj.jpa.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 基于Token的身份验证的主流程如下:
 用户通过用户名和密码发送请求；
 程序验证；
 程序返回一个签名的token 给客户端；
 客户端储存token,并且每次用于每次发送请求。
 */

/**
 * JSON Web Token 注意:需要JDK1.7以上 一。定义一个JsonWebToken的工具类，具有加密和解密token的功能
 * 
 * 需要依赖的两个Jar java-jwt-3.2.0.jar commons-codec-1.10.jar
 * 
 * 和任意一个json转换工具 jackson-annotations-2.6.1.jar jackson-core-2.6.1.jar
 * jackson-databind-2.6.1.jar
 * 
 */
public class JwtToken {

	// 密钥
	private static final String SECRET = "secret";

	// jackson
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * header数据
	 * 
	 * @return
	 */
	private static Map<String, Object> createHead() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typ", "JWT");
		map.put("alg", "HS256");
		return map;
	}

	/**
	 * 生成token
	 * 
	 * @param obj    对象数据
	 * @param maxAge 有效期
	 * @param        <T>
	 * @return
	 */
	public static <T> String sign(T obj, long maxAge) throws UnsupportedEncodingException, JsonProcessingException {
		JWTCreator.Builder builder = JWT.create();

		builder.withHeader(createHead())// header
				.withSubject(mapper.writeValueAsString(obj)); // payload

		if (maxAge >= 0) {
			long expMillis = System.currentTimeMillis() + maxAge;
			Date exp = new Date(expMillis);
			builder.withExpiresAt(exp);
		}

		return builder.sign(Algorithm.HMAC256(SECRET));
	}

	/**
	 * 解密
	 * 
	 * @param token  token字符串
	 * @param classT 解密后的类型
	 * @param        <T>
	 * @return
	 */
	public static <T> T unsign(String token, Class<T> classT) throws IOException {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
		DecodedJWT jwt = verifier.verify(token);

		Date exp = jwt.getExpiresAt();
		if (exp != null && exp.after(new Date())) {
			String subject = jwt.getSubject();
			return mapper.readValue(subject, classT);
		}

		return null;
	}

}
