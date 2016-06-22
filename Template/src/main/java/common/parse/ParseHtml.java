package common.parse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.parse.model.AbstractItem;
import common.parse.model.Item;
import common.parse.model.Paper;
import common.parse.model.PaperImpl;
import common.parse.model.Question;
import common.parse.model.QuestionImpl;
import controller.result.StatusCode;

public class ParseHtml {

	public static void main(String[] args) {
		String path = "F:/word/试题解析/1461226897115.html";
		try {
			Paper paper = parse(path);
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

	public static String[] DEFAULT_NUMBERS;
	public static Map<String, String> types;
	static {
		// 定义小题的默认数量
		DEFAULT_NUMBERS = new String[50];
		for (int i = 0; i < 50; i++) {
			DEFAULT_NUMBERS[i] = String.valueOf(i);
		}
		// 利用java反射获取题型
		Class<Question> clazz = Question.class;
		Field[] fields = clazz.getDeclaredFields();
		types = new HashMap<String, String>();
		for (Field field : fields) {
			try {
				types.put(field.getName(), (String) field.get(clazz));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 用于匹配小题的正则表达式
	 * 
	 * @param text
	 *            需要匹配的文本
	 * @param headers
	 *            匹配的起始头
	 */
	public static boolean matches(String text, String[] headers) {
		for (int i = 0; i < headers.length; i++) {
			if (text.matches("^" + headers[i] + "[.、．].*")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据指定的节点进行解析
	 * 
	 * @param children
	 *            节点
	 * @param staIdx
	 *            起始索引
	 * @param length
	 *            长度
	 */
	public static Question getQuestion(Elements children, int staIdx, int length, String type) {
		Question question = new QuestionImpl(type);
		// List<Map<String, String>> result = new LinkedList<Map<String,
		// String>>();
		StringBuilder builder = new StringBuilder();
		// Map<String, String> obj = new HashMap<String, String>();
		AbstractItem item = new AbstractItem();
		StringBuilder path = new StringBuilder();
		boolean isStart = false;
		for (int i = 0; i < length; i++) {
			Element e = children.get(staIdx);
			String text = e.text();
			String html = e.html();
			if (matches(text, DEFAULT_NUMBERS)) {
				isStart = true;
				if (StringUtils.isNotBlank(builder.toString())) {
					String imgPath = path.toString();
					imgPath = StringUtils.isBlank(imgPath) ? null : imgPath;
					item.setTitle(builder.toString()).setImgPath(imgPath);
					question.add(item);
					// obj.put("title", builder.toString());
					// obj.put("img", imgPath);
					// result.add(obj);
				}
				builder = new StringBuilder();
				path = new StringBuilder();
				item = new AbstractItem();
				// obj = new HashMap<String, String>();
			}
			if (isStart) {
				builder.append(text);
				builder.append("\n");
			}
			if (html.contains("img")) {
				Elements elems = e.children();
				for (Element em : elems) {
					if (em.nodeName().equals("img")) {
						path.append(em.attr("src"));
						path.append("]");
					}
				}
			}
			staIdx++;
		}
		if (StringUtils.isNotBlank(builder.toString())) {
			String imgPath = path.toString();
			imgPath = StringUtils.isBlank(imgPath) ? null : imgPath;
			item.setTitle(builder.toString()).setImgPath(imgPath);
			question.add(item);
		}
		return question;
	}

	/**
	 * 根据指定节点获取题型
	 * 
	 * @param children
	 *            节点
	 */
	public static Map<String, String> getType(Elements children) {
		Map<String, String> map = new HashMap<String, String>();
		for (Element e : children) {
			// 从试卷里面查找题型
			for (String type : types.values()) {
				String text = e.text();
				if (text.contains(type)) {
					map.put(String.valueOf(e.elementSiblingIndex()), type);
				}
			}
		}
		if (map.size() == 0) {
			return null;
		}
		return map;
	}

	/**
	 * 根据题型进行索引排序
	 * 
	 * @param map
	 *            题型键值对
	 */
	public static String[] sortIndex(Map<String, String> map) {
		if (map == null) {
			return null;
		}
		if (map.size() > 1) {
			String[] result = map.keySet().toArray(new String[] {});
			Arrays.sort(result, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					int i = Integer.parseInt(o1.split(":")[0]);
					int j = Integer.parseInt(o2.split(":")[0]);
					return i - j;
				}
			});
			return result;
		} else {
			return map.keySet().toArray(new String[] {});
		}

	}

	/**
	 * 利用Jsoup组件对html试卷进行解析，定义解析试题方法
	 * 
	 * @param path
	 *            文件路径
	 * @param subject
	 *            科目
	 * @param parse
	 *            自定义解析接口
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Paper parse(String path, String subject, ParseQuestion parse) throws Exception {
		// 定义一个指定科目的试卷保存对象
		Paper paper = new PaperImpl(subject);
		// 读取文件
		Document doc = Jsoup.parse(new File(path), "utf-8");
		// 获取body标签内的所有子标签
		Element body = doc.body();
		Elements children = body.children();
		// 获取试卷内包含的题型
		Map<String, String> map = getType(children);
		// 将题型索引按升序排序
		String[] array = sortIndex(map);
		if (array == null) {
			return paper.setResult(StatusCode.PARAMETER_ERROR.setMessage("文档中未找到可以解析的题型"));

		}
		int size = array.length;
		try {
			// 按题型开始解析
			for (int i = 0; i < size; i++) {
				// 当前题型索引
				int curIdx = Integer.valueOf(array[i]);
				// 起始题型索引
				int staIdx = curIdx + 1;
				// 获取该题型的长度，先判断当前题型是否为末尾题型如果不是则：长度=下一题型索引-当前题型索引-1，否则：长度=总长度-当前题型索引-1
				int length = i < size - 1 ? Integer.valueOf(array[i + 1]) - curIdx - 1 : children.size() - curIdx - 1;
				// 匹配已有题型和试卷题型
				for (Entry<String, String> type : types.entrySet()) {
					if (map.get(array[i]).equals(type.getValue())) {
						Question question = getQuestion(children, staIdx, length, type.getValue());
						if (parse != null) {
							parse.parse(question, paper);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return paper.setResult(StatusCode.FAIL.setMessage("试题格式有误，请检查格式"));

		}
		boolean isNull = false;
		for (Question question : paper.getQuestion()) {
			List<Item> items = question.getItem();
			if (items.size() != 0)
				isNull = true;
		}
		if (!isNull)
			return paper.setResult(StatusCode.PARAMETER_ERROR.setMessage("文档中未找到可以解析的题型"));
		return paper;
	}

	/**
	 * 利用Jsoup组件对html试卷进行解析,使用默认的解析方法
	 * 
	 * @param path
	 *            文件路径
	 * @param subject
	 *            科目
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Paper parse(String path, String subject) throws Exception {
		return parse(path, subject, new ParseQuestion() {

			@Override
			public void parse(Question question, Paper paper) {
				paper.add(question);
			}
		});
	}

	/**
	 * 利用Jsoup组件对html试卷进行解析,使用默认的解析方法
	 * 
	 * @param path
	 *            文件路径
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Paper parse(String path) throws Exception {
		return parse(path, null);
	}

}
