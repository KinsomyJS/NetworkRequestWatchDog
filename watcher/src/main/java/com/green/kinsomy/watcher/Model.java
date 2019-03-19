package com.green.kinsomy.watcher;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Model {

	public static <T> T create(JsonElement json, Class<T> classOfModel) {
		return gson().fromJson(json, classOfModel);
	}


	public static <T> List<T> createList(JsonElement json, Class<T> classOfModel) {
		List<T> list = new ArrayList<T>();
		JsonArray array = json.getAsJsonArray();
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			list.add(create(iterator.next(), classOfModel));
		}
		return list;
	}

	protected static Gson gson() {
		return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}

	public static String toJson(Object model) {
		Gson gson = gson();
		return gson.toJson(model);
	}

	public static <T> T fromJson(String json, Class<T> classOfModel) {
		Gson gson = gson();
		return gson.fromJson(json, classOfModel);
	}

	public static <T> List<T> fromJsonToList(String json, Class<T> classOfModel) {
		return createList(new JsonParser().parse(json), classOfModel);
	}

	public static <K, V> Map<K, V> fromJsonToMap(String json, Type type) {
		return gson().fromJson(json, type);
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" Object {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			result.append("  ");
			try {
				result.append(field.getName());
				result.append(": ");
				result.append(field.get(this));

			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
