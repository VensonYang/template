package common.parse;

public class Test {
	public static void main(String[] args) {
		String text = "CHOICE";
		// Pattern p = Pattern.compile("[1-9]\\d?[.、．]");
		// Matcher m = p.matcher(text);
		// while (m.find()) {
		// System.out.println(m.group());
		//
		// }
		String[] a = text.split("_");
		String methodName = "parse";
		for (String s : a) {
			methodName += s.substring(0, 1) + s.substring(1, s.length()).toLowerCase();
		}
		System.out.println(methodName);
	}
}
