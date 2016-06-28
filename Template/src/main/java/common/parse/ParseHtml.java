package common.parse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.parse.model.BaseItem;
import common.parse.model.Item;
import common.parse.model.Paper;
import common.parse.model.PaperImpl;
import common.parse.model.Question;
import common.parse.model.QuestionImpl;
import controller.result.StatusCode;

public class ParseHtml {

	/**
	 * 用于匹配小题的正则表达式
	 * 
	 * @param text
	 *            需要匹配的文本
	 * @param headers
	 *            匹配的起始头
	 */
	public static boolean matches(String text) {
		if (text.matches(Resolver.REGEX_SMALL_ITEM_START + ".*"))
			return true;
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
		StringBuilder builder = new StringBuilder();
		BaseItem item = new BaseItem();
		StringBuilder path = new StringBuilder();
		boolean isStart = false;
		for (int i = 0; i < length; i++) {
			Element e = children.get(staIdx);
			String text = e.text().replace((char) 12288, ' ').trim();
			String html = e.html();
			if (matches(text)) {
				isStart = true;
				if (StringUtils.isNotBlank(builder.toString())) {
					String imgPath = path.toString();
					imgPath = StringUtils.isBlank(imgPath) ? null : imgPath;
					item.setTitle(builder.toString());
					item.setImgPath(imgPath);
					question.add(item);
				}
				builder = new StringBuilder();
				path = new StringBuilder();
				item = new BaseItem();
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
						path.append(",");
					}
				}
			}
			staIdx++;
		}
		if (StringUtils.isNotBlank(builder.toString())) {
			String imgPath = path.toString();
			imgPath = StringUtils.isBlank(imgPath) ? null : imgPath;
			item.setTitle(builder.toString());
			item.setImgPath(imgPath);
			question.add(item);
		}
		return question;
	}

	/**
	 * 根据指定的节点获取答案区
	 * 
	 * @param children
	 *            节点
	 * @param staIdx
	 *            起始索引
	 * @param length
	 *            长度
	 */
	public static Question getAnswer(Elements children, int staIdx, int length) {
		Question question = new QuestionImpl(Question.ANSWER);
		StringBuilder builder = new StringBuilder();
		BaseItem item = new BaseItem();
		for (int i = 0; i < length; i++) {
			Element e = children.get(staIdx);
			String text = e.text();
			builder.append(text);
			String html = e.html();
			if (html.contains("img")) {
				Elements elems = e.children();
				for (Element em : elems) {
					if (em.nodeName().equals("img")) {
						builder.append("[imgPath=");
						builder.append(em.attr("src"));
						builder.append("]");
					}
				}
			}
			staIdx++;
		}
		if (StringUtils.isNotBlank(builder.toString())) {
			item.setTitle(builder.toString());
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
			String text = e.text();
			if (text.contains(Question.ANSWER)) {
				// 如果文档中包含答案区，则不再往下查找
				map.put(String.valueOf(e.elementSiblingIndex()), Question.ANSWER);
				return map;
			} else {
				Pattern p = Pattern.compile("^" + Resolver.REGEX_BIG_QUESTION);
				Matcher m = p.matcher(text);
				if (m.find()) {
					String name = text.split(Resolver.REGEX_BIG_NUM)[1].replace((char) 12288, ' ').trim();
					System.out.println(m.matches());
					map.put(String.valueOf(e.elementSiblingIndex()), name);
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
	 * @throws Exception
	 */
	public static Paper parse(String path, String subject, Parse parse) throws Exception {
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
				Question question;
				String type = map.get(array[i]);
				if (type == Question.ANSWER) {
					question = getAnswer(children, staIdx, length);
				} else {
					question = getQuestion(children, staIdx, length, type);
				}
				if (parse != null) {
					parse.parse(question, paper);
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
	 * @throws Exception
	 */
	public static Paper parse(String path, String subject) throws Exception {
		return parse(path, subject, new Parse() {

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
