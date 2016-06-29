package common.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
		// 截取图片
		// String text =
		// "[imgPath=test/438947b9-b79a-4e8e-9e75-2a12f4c3d48b.png] "
		// + " sdf sdf sdf "
		// + "[imgPath=test/3dd1a8e4-2cc4-414b-91de-039decf77c98.png]∴秒 ";
		// Pattern p = Pattern.compile("\\[imgPath=.{0,60}\\]");
		// Matcher m = p.matcher(text);
		// while (m.find()) {
		// String result = m.group();
		// System.out.println(result.substring(9, result.length() - 1));
		// }
		// 分离小题
		String text = "26.材料一：周室微，诸侯力政，争相并。秦僻在雍州，不与中国诸侯之会盟，夷翟遇之。";
		// getFindCount(text);
		System.out.println(" ".getBytes().length);

	}

	private static int getFindCount(String text) {
		Pattern p = Pattern.compile(Resolver.REGEX_COMLLEX_ITEM + "(?!分)");
		Matcher m = p.matcher(text);
		int c = 0;
		while (m.find()) {
			c++;
			System.out.println(m.group());
		}
		return c;
	}
}
