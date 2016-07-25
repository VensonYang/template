package utils.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 中文乱码处理类 <br>
 * 类说明:将字符串的编码设置为UTF-8
 */
public class CharatorEncodingUtil {

	private CharatorEncodingUtil() {

	}

	public static String toString(String str) {
		String text = "";
		if (str != null && !"".equals(str)) {
			try {
				text = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return text;
	}

	public static void main(String[] args) {
		try {
			String test = URLEncoder.encode("测试", "utf-8");
			System.out.println(URLDecoder.decode(test, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
