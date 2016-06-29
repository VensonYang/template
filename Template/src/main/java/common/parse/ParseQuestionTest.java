package common.parse;

import java.util.List;

import com.alibaba.fastjson.JSON;

import common.parse.model.Item;
import common.parse.model.Paper;
import common.parse.model.PaperImpl;
import common.parse.model.Question;

public class ParseQuestionTest {

	public static final String[] TEST_FILE = new String[] { "七年级地理题库【15-16七下中段考试卷】.doc", "试卷模板样式__历史中考.doc",
			"2016思想品德中考模拟卷（一）七年级.doc", "七年级数学试卷.doc", "七年级语文下学期模拟试题.doc", "七年级历史(一).doc", "七下生物考试.doc" };

	public static void main(String[] args) {
		String path = "F:/test.html";
		String param = TEST_FILE[1];
		String fileName = TEST_FILE[1];
		Parse parse = Resolver.getInstance();
		Paper paper = null;
		try {
			WordToHtml.getInstance().toHtml("f:/", fileName, "f:/", "test.html");
			paper = ParseHtml.getIntance().parse(path, param);
			if (paper.getStatus() == 0) {
				StringBuilder builder = new StringBuilder();
				builder.append(paper.getName());
				builder.append("试卷:总共有");
				List<Question> questions = paper.getQuestions();
				builder.append(paper.hasAnswer() ? questions.size() - 1 : questions.size());
				builder.append("大题，其中");
				for (Question quest : questions) {
					if (quest.getType() != Question.ANSWER) {
						builder.append(quest.getType());
						builder.append("共有");
						List<Item> items = quest.getItems();
						builder.append(items.size());
						builder.append("小题 ");
					}
				}
				builder.append("");
				System.out.println(builder.toString());

			} else {
				System.out.println(paper.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = JSON.toJSONString(paper);
		PaperImpl impl = JSON.parseObject(json, PaperImpl.class);
		Paper newPaper = new PaperImpl();
		List<Question> questions = impl.getQuestions();
		for (Question question : questions) {
			parse.parse(question, newPaper);
		}
	}

}