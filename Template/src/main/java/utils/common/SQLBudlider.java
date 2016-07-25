package utils.common;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SQLBudlider {
	private static Set<String> base_type = new HashSet<String>();
	static {
		base_type.add("java.lang.Integer");
		base_type.add("java.lang.Double");
		base_type.add("java.lang.Float");
		base_type.add("java.lang.Long");
		base_type.add("java.lang.Short");
		base_type.add("java.lang.Boolean");
		base_type.add("java.lang.String");
		base_type.add("java.lang.Character");
		base_type.add("int");
		base_type.add("double");
		base_type.add("float");
		base_type.add("long");
		base_type.add("short");
		base_type.add("boolean");
		base_type.add("char");
		base_type.add("java.util.Date");
	}

	public static String bulider(Class<?> class1, Set<?> filterField) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new Map( ");
		String name = class1.getSimpleName();
		Field[] fields = class1.getDeclaredFields();
		boolean flag = true;
		for (Field field : fields) {
			if (base_type.contains(field.getType().getName())) {
				if (!filterField.contains(field.getName())) {
					if (flag) {
						if (field.getName().equals("status")) {
							buffer.append("(CASE a.status WHEN '1' THEN '启用' ELSE '停用' END) as status ");
						} else {
							buffer.append("a.");
							buffer.append(field.getName());
							buffer.append(" as ");
							buffer.append(field.getName());
						}
						flag = false;
					} else {
						if (field.getName().equals("status")) {
							buffer.append(",(CASE a.status WHEN '1' THEN '启用' ELSE '停用' END) as status ");
						} else {
							buffer.append(" , a.");
							buffer.append(field.getName());
							buffer.append(" as ");
							buffer.append(field.getName());
						}

					}
				}
			}

		}
		buffer.append(") FROM ");
		buffer.append(name);
		buffer.append(" a ");
		return buffer.toString();
	}

	public static String bulider(Class<?> class1) {
		return bulider(class1, Collections.EMPTY_SET);
	}
}
