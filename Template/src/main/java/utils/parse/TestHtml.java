package utils.parse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.parse.Paper;
import model.parse.ParseHtmlVO;
import model.parse.Question;

public class TestHtml {
	static List<Map<String, Object>> questions = new LinkedList<>();
	static {
		Map<String, Object> e = new HashMap<>();
		e.put("text", "选择题");
		Map<String, Object> e2 = new HashMap<>();
		e2.put("text", "非选择题");
		Map<String, Object> e3 = new HashMap<>();
		e3.put("text", "简答题");
		questions.add(e);
		questions.add(e2);
		questions.add(e3);
	}

	public static void main(String[] args) {

		testParseHtml();
		// testRegx();
		// testToHtml();

	}

	private static void testToHtml() {
		try {
			WordToHtml.getInstance().toHtml("f:/", "test.docx", "f:/", "test.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testParseHtml() {
		ParseHtmlVO vo = new ParseHtmlVO("f:/test.html", "生物", questions);
		try {
			Paper result = ParseHtml.getIntance().parse(vo);
			Paper paper = new Paper();
			for (Question question : result.getQuestions()) {
				Resolver.getInstance().parse(question, paper);
			}
			if (paper.hasMessage()) {
				System.out.println(paper.getMessage());
			} else {
				System.out.println(paper);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testRegx() {
		String text = "27.（11分）现出明显的区域特征，即南方地区(1)部的贸易和交流(1频（1具体史实。（3分）";
		Pattern p = Pattern.compile(Resolver.REGEX_COMLLEX_ITEM);
		Matcher m = p.matcher(text);
		while (m.find()) {
			System.out.println(m.group());
		}
	}

	public static String lostGaps(String content) {
		String regex = "(?<=\\>)(?:\\s*\r?\n?)(?=\\<)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		// System.out.println(matcher.find());
		while (matcher.find()) {
			// System.out.println("get here");
			content = matcher.group(1) + matcher.group(2);
			matcher = pattern.matcher(content);
		}
		return content;
	}
}
