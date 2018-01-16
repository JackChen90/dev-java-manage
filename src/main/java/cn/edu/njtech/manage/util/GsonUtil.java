package cn.edu.njtech.manage.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import java.util.Date;

public class GsonUtil {

	/**
	 * 解决gson 2.8.1 bug，date类型为null，报空指针
	 * <p>不建议使用static，gson多线程不安全（老版本是这样，新版本未知）</p>
	 *
	 * @return gson实例
	 */
	public Gson getDateSafeGson() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		// Get the date adapter
		TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
		// Ensure the DateTypeAdapter is null safe
		TypeAdapter<Date> safeDateTypeAdapter = dateTypeAdapter.nullSafe();
		gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, safeDateTypeAdapter)
				.registerTypeAdapter(Date.class, new DateDeserializer())
				.create();
		return gson;
	}


}