package utils.parse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import model.parse.Item;
import model.parse.Paper;
import model.parse.Question;

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
	private final Pattern patternItem = Pattern.compile(REGEX_ITEM_INFO);

	private Resolver() {
	}

	private final static Resolver instance;

	static {
		instance = new Resolver();
	}

	public static Resolver getInstance() {
		return instance;
	}

	@Override
	public void parse(Question question, Paper paper) {
		if (question.getName().equals(Question.ANSWER)) {
			parseAnswer(question, paper);
			return;
		}
		if (checkQuestion(question)) {
			parseComplexItem(question, paper);
		} else {
			parseBaseItem(question, paper);
		}
	}

	private boolean checkQuestion(Question question) {
		// 检查题型，如果小题数目和试题信息数目一致时，则该题型为负责题型
		for (Item item : question.getItems()) {
			String text = Jsoup.parse(item.getTitle()).text();
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
			paper.setMessage("答案区中未找到可以解析的答案");
			return;
		}
		// 获取试卷的所有类型
		List<Question> questions = paper.getQuestions();
		Map<String, Question> types = new HashMap<String, Question>();
		for (int i = 0; i < questions.size(); i++) {
			types.put(questions.get(i).getName(), questions.get(i));
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
				paper.setMessage("没有找到答案,请检查大题编号：" + s);
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
						// 判断改题是否属于复杂题型
						if (item.hasItem()) {
							// 截取答案
							String[] arr = as.get(i).split(REGEX_COMLLEX_ITEM);
							List<String> answers = trimArray(arr);
							int size = item.getItems().size();
							// 判断小题数目与答案是否一致
							if (size == answers.size()) {
								// 保存答案
								for (int j = 0; j < size; j++) {
									Item base = item.getItems().get(j);
									base.setAnswer(answers.get(j));
								}
							} else {
								paper.setMessage(q.getName() + "小题个数和答案个数不一致" + size + "," + answers.size());
								return;
							}
						} else {
							item.setAnswer(as.get(i));
						}
					}
				} else {
					paper.setMessage(q.getName() + "题目个数和答案个数不一致" + +q.getItems().size() + "," + as.size() + as);
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
		Question q = new Question(question.getName());
		q.setComplex(true);
		for (Item item : question.getItems()) {
			String text = item.getTitle();
			String[] arr = text.split(REGEX_COMLLEX_ITEM);
			int i = 0;
			for (String s : arr) {
				if (i == 0) {
					// 分离题号不在此处处理
					// item.setTitle(s.replaceAll(REGEX_SMALL_ITEM_START, ""));
					item.setTitle(s);
				} else {
					// 分离章节，难度
					Item it = new Item();
					it.setQuestion(item.getQuestion());
					parseInfo(it, s);
					item.add(it);
				}
				i++;
			}
			q.add(item);
		}
		paper.add(q);
	}

	/**
	 * 解析试题中的章节、难度、答案 <br>
	 * 格式：【九年级下册第四单元第19课，D，难】
	 */
	private void parseInfo(Item item, String html) {
		String text = Jsoup.parse(html).text();
		Matcher m = patternItem.matcher(text);
		String title = html.replaceAll(REGEX_ITEM_INFO, "");
		// 分离题号不在此处处理
		// item.setTitle(title.replaceAll(REGEX_SMALL_ITEM_START, ""));
		item.setTitle(title);
		// 分离章节，难度
		if (m.find()) {
			String g = m.group();
			String[] t = g.substring(1, g.length() - 1).split("[，,]");
			if (t.length < 2 || t.length > 3) {
				item.setError("章节格式有误：" + g);
			}
			for (String str : t) {
				if (str.trim().length() > 1) {
					item.setExplain(str);
				} else if (str.trim().getBytes().length == 1) {
					item.setAnswer(str);
				} else {
					item.setDifficulty(str);
				}
			}
		} else {
			item.setError("题型出错，未找到章节信息【九年级下册第四单元第19课，难】");
		}
	}

	/**
	 * 解析基本题型
	 */
	private void parseBaseItem(Question question, Paper paper) {
		Question q = new Question(question.getName());
		for (Item it : question.getItems()) {
			parseInfo(it, it.getTitle());
			q.add(it);
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

}
