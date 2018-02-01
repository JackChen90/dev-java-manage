package cn.edu.njtech.manage.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/1/30
 * @description OperationEnum
 */
public enum OperationEnum {

	ADD("add"),
	DELETE("delete"),
	UPDATE("update"),
	IMPORT("import"),
	EXPORT("export");

	private String type;

	OperationEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static Map<String, OperationEnum> maps = new HashMap<>();

	static {
		for (OperationEnum operation :
				values()) {
			maps.put(operation.getType(), operation);
		}
	}

	public static OperationEnum strToEnum(String type) {
		return maps.get(type);
	}
}
