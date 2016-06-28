package common.parse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import common.parse.model.BaseItem;
import common.parse.model.Item;
import common.parse.model.Paper;
import common.parse.model.Question;
import common.parse.model.QuestionImpl;
import common.parse.model.complexItem;

public class Resolver implements Parse {

	public static Map<String, String> QUESTION_TYPE;
	/* 正则表达式 */
	// 匹配【七年级下册课外古诗《春夜洛城闻笛》，易】题型
	public static final String REGEX_ITEM_INFO = "【.{7,50}】";
	// 匹配（1）（2）（2）题型(简答题中的小题)
	public static final String REGEX_COMLLEX_ITEM = "[(（][1-9][）)]|[(（]\\d{1,2}+(?!分)";
	// 匹配1．2．3．题型(答案区小题)
	public static final String REGEX_SMALL_ITEM = "\\d{1,2}[.、．]";
	// 匹配1．2．3．题型(答案区小题)
	public static final String REGEX_SMALL_ITEM_ANSWER = REGEX_SMALL_ITEM + "(?!png)";
	// 匹配起始头为1．2．3．题型(试卷小题)
	public static final String REGEX_SMALL_ITEM_START = "^" + REGEX_SMALL_ITEM;
	// 匹配一、二、题型
	public static final String REGEX_BIG_NUM = "[一二三四五六七八九十][、.．]";
	// 匹配一、XX题二、XX题 题型(试卷大题，答案区小题)
	public static final String REGEX_BIG_QUESTION = REGEX_BIG_NUM + "[\u4e00-\u9fa5]*题";
	Pattern patternItem;
	static {
		QUESTION_TYPE = new HashMap<String, String>();
		// 利用java反射获取基本题型
		Class<Question> clazz = Question.class;
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				String methodName = "parse";
				for (String s : field.getName().split("_")) {
					methodName += s.substring(0, 1) + s.substring(1, s.length()).toLowerCase();
				}
				QUESTION_TYPE.put((String) field.get(clazz), methodName);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private Resolver() {
		patternItem = Pattern.compile(REGEX_ITEM_INFO);
	}

	private static Resolver resolver = new Resolver();

	public static Resolver getResolverInstance() {
		return resolver;
	}

	public static void add(String key, String value) {
		QUESTION_TYPE.put(key, value);
	}

	public static void remove(String key) {
		QUESTION_TYPE.remove(key);
	}

	public void invoke(Question question, Paper paper, String type) {
		System.out.println("--------------------解析" + type + "--------------------");
		try {
			Method method = resolver.getClass().getMethod(QUESTION_TYPE.get(type), Question.class, Paper.class);
			method.invoke(resolver, question, paper);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void parse(Question question, Paper paper) {
		if (QUESTION_TYPE.containsKey(question.getType()))
			resolver.invoke(question, paper, question.getType());
		else
			paper.add(question);

	}

	/**
	 * 
	 * 解析答案
	 */
	public void parseAnswer(Question question, Paper paper) {
		if (question.getItem() == null || question.getItem().size() == 0) {
			throw new IllegalArgumentException("答案区中未找到可以解析的答案");
		}
		// 获取试卷的所有类型
		List<Question> questions = paper.getQuestion();
		Map<String, Question> types = new HashMap<String, Question>();
		for (int i = 0; i < questions.size(); i++) {
			types.put(questions.get(i).getType(), questions.get(i));
		}
		// 获取答案
		String answer = question.getItem().get(0).getTitle();
		// 寻找大题
		Pattern p = Pattern.compile(REGEX_BIG_QUESTION);
		Matcher m = p.matcher(answer);
		// 截取答案
		String[] a = answer.split(REGEX_BIG_QUESTION);
		Map<String, String> map = new HashMap<String, String>();
		// 将答案放入对应的大题中
		for (String s : a) {
			if (StringUtils.isNotBlank(s)) {
				m.find();
				String key = m.group().split(REGEX_BIG_NUM)[1];
				map.put(key, s);
			}
		}
		// 将答案保存到小题中
		for (Entry<String, String> entry : map.entrySet()) {
			// 无法获取键值
			if (types.keySet().contains(entry.getKey())) {
				// 拆分小题答案
				String[] split = entry.getValue().split(REGEX_SMALL_ITEM_ANSWER);
				// 保存小题答案
				List<String> as = new LinkedList<String>();
				for (String e : split) {
					if (StringUtils.isNotBlank(e)) {
						as.add(e.trim());
					}
				}
				// 设置答案
				Question q = types.get(entry.getKey());
				if (q.getItem().size() == as.size()) {
					for (int i = 0; i < q.getItem().size(); i++) {
						BaseItem item = (BaseItem) q.getItem().get(i);
						item.setAnswer(as.get(i));
					}
				} else {
					throw new IllegalArgumentException(
							q.getType() + "题目个数和答案个数不一致" + +q.getItem().size() + "," + as.size());
				}
			}
		}
	}

	/**
	 * 解析简答题
	 */
	public void parseShortAnswer(Question question, Paper paper) {
		parseComplexItem(question, paper);
	}

	private void parseComplexItem(Question question, Paper paper) {
		Question q = new QuestionImpl(question.getType());
		for (Item it : question.getItem()) {
			BaseItem bt = (BaseItem) it;
			complexItem item = new complexItem();
			item.setImgPath(bt.getImgPath());
			String text = bt.getTitle();
			parse(item, text);
			q.add(item);
		}
		paper.add(q);
	}

	/**
	 * 解析复杂题型，即题中包含小题
	 */
	private void parse(complexItem item, String text) {
		// 分离小题
		String[] s = text.split(REGEX_COMLLEX_ITEM);
		for (int i = 0; i < s.length; i++) {
			if (StringUtils.isNotBlank(s[i])) {
				if (i == 0) {
					// 分离第一道小题与标题
					item.setTitle(s[i].split(REGEX_SMALL_ITEM_START)[1]);
				} else {
					// 分离章节，难度
					BaseItem baseItem = new BaseItem();
					parse(baseItem, s[i]);
					item.add(baseItem);
				}
			}
		}
	}

	/**
	 * 解析选择题
	 */
	public void parseChoice(Question question, Paper paper) {
		parseBaseItem(question, paper);
	}

	/**
	 * 解析填空题
	 */
	public void parseComplete(Question question, Paper paper) {
		parseBaseItem(question, paper);
	}

	/**
	 * 解析试题中的章节、难度、答案
	 */
	private void parse(BaseItem item, String text) {
		Matcher m = patternItem.matcher(text);
		// 分离章节，难度
		if (m.find()) {
			String[] s = text.split(REGEX_ITEM_INFO);
			String title = "";
			for (String t : s) {
				title += t;
			}
			String[] a = title.split(REGEX_SMALL_ITEM);
			title = a.length == 2 ? a[1] : a[0];
			item.setTitle(title);
			String g = m.group();
			String[] t = g.substring(1, g.length() - 1).split("[，,]");

			if (t.length < 2 || t.length > 3) {
				throw new IllegalArgumentException("格式有误" + text);
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
			throw new IllegalArgumentException("题型出错" + text);
		}
	}

	/**
	 * 解析阅读题
	 */
	public void parseReading(Question question, Paper paper) {
		paper.add(question);
	}

	/**
	 * 解析解答题
	 */
	public void parseSolve(Question question, Paper paper) {
		parseBaseItem(question, paper);
	}

	/**
	 * 解析附加题
	 */
	public void parseAdditional(Question question, Paper paper) {
		paper.add(question);
	}

	/**
	 * 解析综合题
	 */
	public void parseComprehensive(Question question, Paper paper) {
		paper.add(question);
	}

	/**
	 * 解析基本题型
	 */
	private void parseBaseItem(Question question, Paper paper) {
		Question q = new QuestionImpl(question.getType());
		for (Item it : question.getItem()) {
			BaseItem bt = (BaseItem) it;
			String text = bt.getTitle();
			parse(bt, text);
			q.add(bt);
		}
		paper.add(q);
	}
}
