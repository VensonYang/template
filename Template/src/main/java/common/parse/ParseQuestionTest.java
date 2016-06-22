package common.parse;

import java.util.List;

import common.parse.model.Choice;
import common.parse.model.Item;
import common.parse.model.Paper;
import common.parse.model.Question;
import common.parse.model.QuestionImpl;

public class ParseQuestionTest {

	public static void main(String[] args) {

		String path = "F:/word/试题解析/1461226897115.html";
		try {
			Paper paper = ParseHtml.parse(path, Paper.CHINESE, new myParse());
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

	static class myParse implements ParseQuestion {

		@Override
		public void parse(Question question, Paper paper) {
			System.out.println(question.getType().hashCode());
			// 判断是否含有选择题，有的话就执行自定义解析
			if (question.getType() == Question.CHOICE) {
				System.out.println(Question.CHOICE.hashCode());
				// 定义一个指定题型的大题保存对象
				Question chioces = new QuestionImpl(Question.CHOICE);
				System.out.println("--------------------解析选择题--------------------");
				// 定义一个指定题型的大题保存对象
				List<Item> items = question.getItem();
				for (Item item : items) {
					// 定义一道选择题保存对象
					Choice choice = new Choice();
					// 按照指定的正则表达式去拆分题目
					String[] arr = item.getTitle().split("[A-D][.、．]");
					// 保存到答案对象
					choice.setTitle(arr[0]);
					choice.setImgPath(item.getImgPath());
					choice.setAnswerA(arr[1]);
					choice.setAnswerB(arr[2]);
					choice.setAnswerC(arr[3]);
					choice.setAnswerD(arr[4]);
					chioces.add(choice);
				}
				question = chioces;
			} else if (question.getType() == Question.COMPLETE) {
				System.out.println("--------------------填空题--------------------");
			}
			paper.add(question);

		}

	}

}
