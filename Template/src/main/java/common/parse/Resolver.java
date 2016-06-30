package common.parse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.parse.model.BaseItem;
import common.parse.model.ComplexItem;
import common.parse.model.Item;
import common.parse.model.Paper;
import common.parse.model.Question;
import common.parse.model.QuestionImpl;

public class Resolver implements Parse {

	/* 正则表达式 */
	// 匹配【七年级下册课外古诗《春夜洛城闻笛》，易】题型
	public static final String REGEX_ITEM_INFO = "【.{7,40}】";
	// 匹配（1）（2）（2）题型(简答题中的小题)
	// public static final String REGEX_COMLLEX_ITEM =
	// "[(（][1-9][）)]|[(（]\\d{1,2}+(?!分)";
	public static final String REGEX_COMLLEX_ITEM = "[(（]\\d{1,2}(?:[）)]|)(?!分)";
	// 匹配1．2．3．题型(答案区小题)
	public static final String REGEX_SMALL_ITEM = "\\d{1,2}[.、．]";
	// 匹配1．2．3．题型(答案区小题)
	public static final String REGEX_SMALL_ITEM_ANSWER = "\\d{1,2}[.．](?!png)";
	// 匹配起始头为1．2．3．题型(试卷小题)
	public static final String REGEX_SMALL_ITEM_START = "^" + REGEX_SMALL_ITEM;
	// 匹配一、二、题型
	public static final String REGEX_BIG_NUM = "[一二三四五六七八九十][、.．]";
	// 匹配一、XX题二、XX题 题型(试卷大题，答案区小题)
	public static final String REGEX_BIG_QUESTION = REGEX_BIG_NUM + "[\u4e00-\u9fa5]*题";
	// 小题正则
	private Pattern patternItem;
	// 提示信息
	private String message;

	private Resolver() {
		patternItem = Pattern.compile(REGEX_ITEM_INFO);
	}

	private static Resolver instance = new Resolver();

	public static Resolver getInstance() {
		return instance;
	}

	@Override
	public void parse(Question question, Paper paper) {
		if (question.getType().equals(Question.ANSWER)) {
			parseAnswer(question, paper);
			return;
		}
		if (checkQuestion(question)) {
			System.out.println("--------------------复杂解析" + question.getType() + "--------------------");
			parseComplexItem(question, paper);
		} else {
			System.out.println("--------------------基本解析" + question.getType() + "--------------------");
			parseBaseItem(question, paper);
		}
	}

	private boolean checkQuestion(Question question) {
		// 检查题型，如果小题数目和试题信息数目一致时，则该题型为负责题型
		for (Item item : question.getItems()) {
			String text = item.getTitle();
			int infoCount = getFindCount(REGEX_ITEM_INFO, text);
			int itemCount = getFindCount(REGEX_COMLLEX_ITEM, text);
			// System.err.println("-----------" + infoCount + "-----------" +
			// itemCount);
			if (infoCount == 0 || infoCount != itemCount) {
				return false;
			}
		}
		return true;

	}

	/**
	 * 
	 * 解析答案
	 */
	public void parseAnswer(Question question, Paper paper) {
		if (question.getItems() == null || question.getItems().isEmpty()) {
			this.message = "答案区中未找到可以解析的答案";
			return;
		}
		// 获取试卷的所有类型
		List<Question> questions = paper.getQuestions();
		Map<String, Question> types = new HashMap<String, Question>();
		for (int i = 0; i < questions.size(); i++) {
			types.put(questions.get(i).getType(), questions.get(i));
		}
		// 获取答案
		String answer = question.getItems().get(0).getTitle();
		// 寻找大题
		Pattern p = Pattern.compile(REGEX_BIG_QUESTION);
		Matcher m = p.matcher(answer);
		// 截取答案
		String[] a = answer.split(REGEX_BIG_QUESTION);
		List<String> trimArr = trimArray(a);
		Map<String, String> map = new HashMap<String, String>();
		// 将答案放入对应的大题中
		for (String s : trimArr) {
			if (m.find()) {
				String key = m.group().replaceAll(REGEX_BIG_NUM, "");
				map.put(key, s);
			} else {
				this.message = "没有找到答案,请检查大题编号：" + s;
				return;
			}
		}
		// 将答案保存到小题中
		for (Entry<String, String> entry : map.entrySet()) {
			// 无法获取键值
			if (types.containsKey(entry.getKey())) {
				// 拆分小题答案
				String[] split = entry.getValue().split(REGEX_SMALL_ITEM_ANSWER);
				// 保存小题答案
				List<String> as = trimArray(split);
				// 设置答案
				Question q = types.get(entry.getKey());
				// 判断试题与答案是否一致
				if (q.getItems().size() == as.size()) {
					for (int i = 0; i < q.getItems().size(); i++) {
						Item item = q.getItems().get(i);
						// 判断改题是否属于负责题型
						if (item instanceof ComplexItem) {
							ComplexItem complex = (ComplexItem) item;
							// 截取答案
							String[] arr = as.get(i).split(REGEX_COMLLEX_ITEM);
							List<String> answers = trimArray(arr);
							int size = complex.getItems().size();
							// 判断小题数目与答案是否一致
							if (size == answers.size()) {
								// 保存答案
								for (int j = 0; j < size; j++) {
									BaseItem base = (BaseItem) complex.getItems().get(j);
									base.setAnswer(answers.get(j));
								}
							} else {
								this.message = q.getType() + "小题个数和答案个数不一致" + size + "," + answers.size();
								return;
							}
						} else {
							BaseItem base = (BaseItem) item;
							base.setAnswer(as.get(i));
						}
					}
				} else {
					this.message = q.getType() + "题目个数和答案个数不一致" + +q.getItems().size() + "," + as.size() + as;
					return;
				}
			}
		}
	}

	private List<String> trimArray(String[] arr) {
		List<String> result = new LinkedList<String>();
		if (arr.length == 1) {
			result.add(arr[0]);
			return result;
		}
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				result.add(arr[i]);
			}
		}
		return result;
	}

	private void parseComplexItem(Question question, Paper paper) {
		Question q = new QuestionImpl(question.getType());
		for (Item it : question.getItems()) {
			ComplexItem item = new ComplexItem();
			item.setTitle(it.getTitle());
			item.setImgPath(item.getImgPath());
			String text = item.getTitle();
			parse(item, text);
			q.add(item);
		}
		paper.add(q);
	}

	/**
	 * 解析复杂题型，即题中包含小题
	 */
	private void parse(ComplexItem item, String text) {
		// 分离小题
		String[] arr = text.split(REGEX_COMLLEX_ITEM);
		int i = 0;
		for (String s : arr) {
			if (i == 0) {
				// 分离标题与题号
				item.setTitle(s.replaceAll(REGEX_SMALL_ITEM_START, ""));
			} else {
				// 分离章节，难度
				BaseItem baseItem = new BaseItem();
				parse(baseItem, s);
				item.add(baseItem);
			}
			i++;
		}
	}

	/**
	 * 解析试题中的章节、难度、答案 <br>
	 * 格式：【九年级下册第四单元第19课，D，难】
	 */
	private void parse(BaseItem item, String text) {
		Matcher m = patternItem.matcher(text);
		// 分离章节，难度
		if (m.find()) {
			String title = text.replaceAll(REGEX_ITEM_INFO, "");
			item.setTitle(title.replaceAll(REGEX_SMALL_ITEM_START, ""));
			String g = m.group();
			String[] t = g.substring(1, g.length() - 1).split("[，,]");
			if (t.length < 2 || t.length > 3) {
				this.message = "第" + text.substring(0, 1) + "题格式有误：" + g;
				return;
			}
			for (String str : t) {
				if (str.length() > 1) {
					item.setExplain(str);
				} else if (str.getBytes().length == 1) {
					item.setAnswer(str);
				} else {
					item.setDifficulty(str);
				}
			}
		} else {
			this.message = "第" + text.substring(0, 1) + "题题型出错，未找到【九年级下册第四单元第19课，难】";
			return;
		}
	}

	/**
	 * 解析基本题型
	 */
	private void parseBaseItem(Question question, Paper paper) {
		Question q = new QuestionImpl(question.getType());
		for (Item it : question.getItems()) {
			BaseItem bt = new BaseItem();
			bt.setImgPath(it.getImgPath());
			bt.setTitle(it.getTitle());
			String text = bt.getTitle();
			parse(bt, text);
			q.add(bt);
		}
		paper.add(q);
	}

	private int getFindCount(String regex, String text) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		int c = 0;
		while (m.find()) {
			c++;
		}
		return c;
	}

	@Override
	public boolean hasError() {
		if (this.message == null || this.message.trim().length() == 0)
			return false;
		else
			return true;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
