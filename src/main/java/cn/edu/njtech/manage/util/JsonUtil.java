/*
 * 文 件 名:  JsonUtil.java
 * 版    权:  Copyright 2016 咪咕互动娱乐有限公司,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  jiangweijia
 * 创建时间:  2016年12月5日
 
 */
package cn.edu.njtech.manage.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 
 * json转换工具类
 * 
 * @author JiangWeijia
 * @version [1.0.0, 2016年12月5日]
 * @since [点数平台/公用模块]
 */
public final class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static final ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * (1)转换为普通JavaBean：readValue(json,Student.class)
	 * (2)转换为List,如List<Student>,将第二个参数传递为Student[].class
	 * 然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
	 * 
	 * @param jsonStr
	 * @param valueType
	 * @return
	 */
	public static <T> T readValue(String jsonStr, Class<T> clazz) {

		try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			logger.error("readValue error:", e);
		}

		return null;
	}

	/**
	 * json数组转List
	 * 
	 * @param jsonStr
	 * @param valueTypeRef
	 * @return
	 */
	public static <T> T readValue(String jsonStr, TypeReference<T> clazz) {

		try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			logger.error("readValue error:", e);
		}

		return null;
	}

	/**
	 * 把JavaBean转换为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSonString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("toJSonString error:", e);
		}

		return null;
	}

	/**
	 * Map 转 JavaBean,可以解析属性中的对象
	 * 
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static <T> T transMap2Bean(Class<T> clazz, Map<?, ?> map) {
		try {
			return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
			logger.error("transMap2Bean error:", e);
		}
		return null;
	}
}
