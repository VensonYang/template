package utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TemplateNameUtil {
	private static Properties props = new Properties();
	// 静态读取配置文件
	static {
		try {
			InputStream in = TemplateNameUtil.class.getClassLoader().getResourceAsStream("exceltemplate.properties");
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private TemplateNameUtil() {
	}

	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param key
	 *            主键
	 * @return String
	 */
	public static String getKeyValue(String key) {
		return props.getProperty(key);
	}
}
