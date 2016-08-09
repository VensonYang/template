package utils.parse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;

import model.parse.CreatePaperVO;
import model.parse.HighChartBar;
import model.parse.HighChartPie;
import model.parse.Item;
import model.parse.Paper;
import model.parse.Question;
import model.parse.Tree;

public class ParseUtil {
	public static final String[] BIG_NUM = new String[] { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

	/**
	 * 排序题型
	 */
	private static List<Question> sortQuestion(List<Question> questions, String[] Order) {
		List<Question> newQ = new LinkedList<Question>();
		for (String order : Order) {
			for (Question q : questions) {
				if (q.getName().equals(order)) {
					newQ.add(q);
				}
			}
		}
		return newQ;
	}

	/**
	 * 将试卷对象转tree目录类型数据
	 * 
	 * @param paper
	 *            试卷对象
	 * @param isOrigin
	 *            是否保留数据源
	 * @see model.parse.Paper
	 */
	public static Tree paperToTree(Paper paper, boolean isOrigin) {
		Tree tree = new Tree();
		tree.setText(paper.getName());
		// tree.setOrigin(paper);
		for (Question q : paper.getQuestions()) {
			if (!q.getName().equals(Question.ANSWER)) {
				Tree branch = new Tree();
				// branch.setOrigin(q);
				branch.setText(q.getName());
				for (Item it : q.getItems()) {
					Tree leaf = new Tree();
					if (isOrigin) {
						leaf.setOrigin(it);
					}
					String title = Jsoup.parse(it.getTitle()).text();
					title = title.length() > 10 ? title.substring(0, 10) : title;
					leaf.setText(title + "...");
					branch.add(leaf);
				}
				tree.add(branch);
			}
		}
		return tree;
	}

	public static Tree paperToTree(Paper paper) {
		return paperToTree(paper, true);
	}

	public static Paper parseQuestion(Paper paper) {
		Parse resolver = Resolver.getInstance();
		Paper newPaper = new Paper(paper.getName());
		List<Question> questiones = paper.getQuestions();
		for (Question question : questiones) {
			resolver.parse(question, newPaper);
		}
		return newPaper;
	}

	public static List<Item> treeToItems(String tree) {
		System.out.println(tree);
		return treeToItems(JSON.parseObject(tree, Tree.class));
	}

	public static List<Item> treeToItems(Tree tree) {
		List<Item> items = new LinkedList<Item>();
		if (tree != null) {
			// 判断是导入一题还是全部导入
			int size = tree.getChildren().size();
			if (size == 0) {
				if (tree.getOrigin() != null) {
					Item it = JSON.parseObject(tree.getOrigin().toString(), Item.class);
					items.add(it);
				}
			} else {
				for (Tree branch : tree.getChildren()) {
					for (Tree leaf : branch.getChildren()) {
						Item it = JSON.parseObject(leaf.getOrigin().toString(), Item.class);
						items.add(it);
					}
				}
			}

		}
		return items;

	}

	/**
	 * 将章节数据转为tree目录数据
	 * 
	 * @param subject
	 *            科目
	 * @param sections
	 *            数据源
	 * 
	 */
	public static Tree sectionToTree(String subject, List<Map<String, Object>> sections) {
		Tree tree = new Tree();
		tree.setText(subject);
		Map<Integer, Tree> pids = new HashMap<Integer, Tree>();
		Map<Integer, String> couId = new HashMap<Integer, String>();
		Map<Integer, Tree> courses = new HashMap<Integer, Tree>();
		// 先找出所有课程节点
		for (Map<String, Object> section : sections) {
			if (null != section.get("level") && (Integer) section.get("level") == 2) {
				Integer courseId = (Integer) section.get("courseid");
				couId.put(courseId, section.get("coursename").toString());
			}
		}
		for (Entry<Integer, String> entry : couId.entrySet()) {
			Tree branch = new Tree();
			branch.setText(entry.getValue());
			branch.setOrigin(entry);
			courses.put(entry.getKey(), branch);
			// iteratorSection(branch, pid, sections);
			tree.add(branch);
		}
		// 先找出所有父节点
		for (Map<String, Object> section : sections) {
			// 级别为2的就是父节点
			Integer pid = (Integer) section.get("courseid");
			if (null != section.get("level") && (Integer) section.get("level") == 2) {
				// 创建单元节点
				Tree branch = courses.get(pid);
				Tree parent = new Tree();
				parent.setText(section.get("name").toString());
				parent.setOrigin(section);
				pids.put((Integer) section.get("id"), parent);
				branch.add(parent);
			}
		}
		// 判断当前子节点是否有父节点，并且是否属于子节点的父节点
		for (Map<String, Object> section : sections) {
			Integer pid = (Integer) section.get("parentid");
			if (null != pid && pids.containsKey(pid)) {
				Tree parent = pids.get(pid);
				Tree children = new Tree();
				children.setText(section.get("name").toString());
				children.setOrigin(section);
				parent.add(children);

			}
		}
		return tree;

	}

	/**
	 * 根据试卷对象和条件对象创建html
	 * 
	 * @param paper
	 * @param vo
	 * @see model.parse.Paper
	 * @see model.parse.CreatePaperVO
	 */
	public static String createHtml(Paper paper, CreatePaperVO vo) {
		String paperTitle = paper.getName();
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		html.append("<html lang='zh-CN'>");
		html.append("<head>");
		html.append("<title>" + paperTitle + "</title>");
		html.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		// 样式
		html.append(
				"<style type='text/css'> .underline{text-decoration:underline;}td{border:solid 1px;text-align:center;} "
						+ "tr{keep-together:always;}table{table-layout:fixed;border-collapse:collapse;border-spacing:0;}"
						+ "</style>");

		html.append("</head>");
		html.append("<body class='b1 b2'>");
		// 卷头
		if (vo.hasHead()) {
			html.append("<div class='paperTitle' style='text-align:center; font-weight:bolder;'><h2>" + vo.getHead()
					+ "</h2></div>");
		} else {
			html.append("<div class='paperTitle' style='text-align:center; font-weight:bolder;'><h2>" + paperTitle
					+ "</h2></div>");
		}
		StringBuilder html_title = new StringBuilder();
		html_title.append("<!--内容区开始--><div id='content'>");
		StringBuilder html_answer = new StringBuilder();
		html_answer.append(
				"<!--答案区开始--><div id='contentAnswer'><h2 style='text-align:center; font-weight:bolder;'>答案</h2>");
		// 是否排序题型
		List<Question> questions;
		if (vo.hasSort()) {
			questions = sortQuestion(paper.getQuestions(), vo.getSort().trim().split(","));
		} else {
			questions = paper.getQuestions();
		}
		int no = 1;
		for (int i = 0; i < questions.size(); i++) {
			// 每道大题下的小题编号是否从1开始
			if (vo.isStartOne()) {
				no = 1;
			}
			Question question = questions.get(i);
			html_title.append("<!--大题开始--><div class='question'> <h4 style='font-weight:bolder;'>" + BIG_NUM[i] + "、"
					+ question.getName() + "</h4>");
			html_answer.append("<!--大题答案开始--><div class='questionAnswer'> <h4 style='font-weight:bolder;'>" + BIG_NUM[i]
					+ "、" + question.getName() + "</h4>");
			// 复合题型
			List<Item> items = question.getItems();
			if (question.isComplex()) {
				for (Item item : items) {
					// 添加标题
					html_title.append("<!--复合题开始--><div >" + no + "." + item.getTitle());
					// 添加图片
					if (StringUtils.isNotBlank(item.getImgPath())) {
						for (String imgPath : item.getImgPath().split(",")) {
							if (StringUtils.isNotBlank(imgPath)) {
								html_title.append(imgPath);
							}
						}
					}
					html_answer.append("<!--复合题答案开始--><div class='itemAnswer'>" + no + ".");
					List<Item> its = item.getItems();
					for (int k = 0; k < its.size(); k++) {
						Item it = its.get(k);
						// 添加标题
						html_title.append("<!--复合题小题开始--><div id='item_" + it.getId() + "' class='item'>(" + (k + 1)
								+ ")" + it.getTitle());
						// 添加答案
						if (StringUtils.isNotBlank(it.getAnswer())) {
							if (vo.isAfter()) {
								html_title.append("<br>答案：" + it.getAnswer());
								html_title.append("<br><br>");
							} else {
								html_answer.append("<br>(" + (k + 1) + ")" + it.getAnswer());
							}
						}
						html_title.append("</div><!--复合题小题结束-->");
					}
					no++;
					html_title.append("</div><br><br><br><!--复合题结束-->");
					html_answer.append("</div><!--复合题答案结束-->");

				}
			} else {
				for (int k = 0; k < items.size(); k++) {
					Item it = items.get(k);
					// 添加标题
					html_title.append(
							"<!--普通题开始--><div id='item_" + it.getId() + "' class='item'>" + no + "." + it.getTitle());
					// 添加图片
					if (StringUtils.isNotBlank(it.getImgPath())) {
						for (String imgPath : it.getImgPath().split(",")) {
							if (StringUtils.isNotBlank(imgPath)) {
								html_title.append(imgPath);
							}
						}
					}
					// 添加答案
					if (StringUtils.isNotBlank(it.getAnswer())) {
						if (vo.isAfter()) {
							html_title.append("<br>答案：" + it.getAnswer());
							html_title.append("<br><br>");
						} else {
							html_answer.append("<!--普通题答案开始--><div  class='itemAnswer'>" + no + "." + it.getAnswer());
							html_answer.append("</div><!--普通题答案结束-->");
						}
					}
					html_title.append("</div><br><!--普通题结束-->");

					no++;
				}

			}

			html_title.append("</div><!--大题结束-->");
			html_answer.append("</div><!--大题答案结束-->");
		}
		html_title.append("</div><!--内容结束-->");
		html_answer.append("</div><!--答案结束-->");
		String title = html_title.toString();
		String answer = html_answer.toString();
		if (vo.isContent()) {
			html.append(title);
		}
		if (vo.isAnswer() && !vo.isAfter()) {
			html.append(answer);
		}
		html.append("</body></html>");
		return html.toString().replaceAll("\n", "<br>");
	}

	public static String createHtml(Paper paper) {
		CreatePaperVO vo = new CreatePaperVO();
		vo.setSort(calculateQuesionSort(paper));
		return createHtml(paper, vo);
	}

	/**
	 * 根据题型类型来排序题型顺序
	 */
	private static String calculateQuesionSort(Paper paper) {
		List<Question> qs = paper.getQuestions();
		StringBuilder simple = new StringBuilder();
		StringBuilder complex = new StringBuilder();
		for (Question q : qs) {
			if (q.isComplex()) {
				complex.append(q.getName());
				complex.append(",");
			} else {
				simple.append(q.getName());
				simple.append(",");
			}
		}
		String result = simple.toString() + complex.toString();
		return result.substring(0, result.length() - 1);
	}

	public static Tree dataToTree(List<Map<String, Object>> data, String text) {
		Tree tree = new Tree();
		tree.setText(text);
		if (data != null && !data.isEmpty()) {
			for (Map<String, Object> map : data) {
				Tree leaf = new Tree();
				leaf.setText(map.get("text").toString());
				leaf.setOrigin(map);
				tree.add(leaf);
			}
		}
		return tree;
	}

	public static List<HighChartBar> buildHighChartBar(List<Map<String, Object>> data) {
		List<HighChartBar> bars = new LinkedList<HighChartBar>();
		for (Map<String, Object> d : data) {
			HighChartBar bar = new HighChartBar();
			bar.setName((String) d.get("name"));
			bar.add(d.get("amount"));
			bars.add(bar);
		}
		return bars;
	}

	public static HighChartPie bulidHighChart(List<Map<String, Object>> difficultys, String name, String param) {
		HighChartPie chartD = new HighChartPie("pie", name);
		for (Map<String, Object> difficulty : difficultys) {
			Object obj = difficulty.get(param);
			Object amount = difficulty.get("amount");
			List<Object> data = new LinkedList<Object>();
			data.add(obj);
			data.add(amount);
			chartD.add(data);
		}
		return chartD;
	}
}
