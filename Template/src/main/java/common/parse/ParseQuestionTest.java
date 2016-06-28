package common.parse;

import java.util.List;

import common.parse.model.Item;
import common.parse.model.Paper;
import common.parse.model.Question;

public class ParseQuestionTest {

	public static final String[] TEST_FILE = new String[] { "七年级地理题库【15-16七下中段考试卷】.doc", "试卷模板样式__历史中考.doc",
			"2016思想品德中考模拟卷（一）七年级.doc", "七年级数学试卷.doc", "七年级语文下学期模拟试题.doc", "七年级历史(一).doc" };

	public static void main(String[] args) {
		String path = "F:/test.html";
		String param = "语文";
		String fileName = TEST_FILE[3];
		Parse parse = Resolver.getResolverInstance();
		try {
			WordToHtml.toHtml("f:/", fileName, "f:/", "test.html");
			Paper paper = ParseHtml.parse(path, param, parse);
			if (paper.getStatus() == 0) {
				System.out.println(paper.getName());
				List<Question> questions = paper.getQuestion();
				for (Question quest : questions) {
					System.out.println(quest.getType());
					List<Item> items = quest.getItem();
					for (Item item : items) {
						System.out.println(item);
					}
				}
			} else {
				System.out.println(paper.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
