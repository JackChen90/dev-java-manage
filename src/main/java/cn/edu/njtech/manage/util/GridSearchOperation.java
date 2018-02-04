package cn.edu.njtech.manage.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/2/1
 * @description GridSearchOperation
 */
public enum GridSearchOperation {
	EQ("eq"),
	NE("ne"),
	CN("cn"),
	NC("nc");

	GridSearchOperation(String type) {
		this.type = type;
	}

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 对应sql操作符
	 */
	private String operation;

	public String getType() {
		return type;
	}

	public String getOperationStr(String searchString) {
		switch (this) {
			case EQ:
			default:
				operation = " = \'" + searchString + "\' ";
				break;
			case NE:
				operation = " <> \'" + searchString + "\' ";
				break;
			case CN:
				operation = " like \'%" + searchString + "%\' ";
				break;
			case NC:
				operation = " not like \'%" + searchString + "%\' ";
		}
		return operation;
	}

	private static Map<String, GridSearchOperation> map = new HashMap<>();

	static {
		for (GridSearchOperation searchOperation :
				values()) {
			map.put(searchOperation.getType(), searchOperation);
		}
	}

	public static GridSearchOperation strToEnum(String type) {
		return map.get(type);
	}
}
