package utils.parse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FormatHtml {

	private static final FormatHtml instanct = new FormatHtml();

	public static FormatHtml getInstanct() {
		return instanct;
	}

	private FormatHtml() {

	}

	public void format(Document doc) throws IOException {
		Element body = doc.body();
		boolean isO7 = false;
		// 判断是否是07格式
		if (!body.getElementsByTag("div").isEmpty()) {
			// 07
			Element div = body.getElementsByTag("div").get(0);
			String html = div.html();
			div.remove();
			doc.body().append(html);
			isO7 = true;
		}
		// 采集样式
		Map<String, String> style = collectionStyle(doc);
		// 替换样式
		replaceStyle(style, body.children());
		if (isO7) {
			// 清除空格
			clearHtmlLabel(body.children());
		}
		// 去除多余的空格
		String html = body.html().replaceAll("(&nbsp;){3,}",
				WordToHtml.HTML_SPACE + WordToHtml.HTML_SPACE + WordToHtml.HTML_SPACE);
		body.html(html);
	}

	//
	private void clearHtmlLabel(Elements elems) {
		for (Element ele : elems) {
			// 不清除表格和图片
			if (ele.tagName().equals("table")) {
				continue;
			}
			// 清除空格
			String text = ele.text();
			text = text.replaceAll("\\s*", "");
			if (ele.html().contains("img")) {
				Elements links = ele.getElementsByTag("img");
				for (Element link : links) {
					text += link;
				}
			}
			ele.html(text);
		}

	}

	/**
	 * 采集样式
	 */
	private Map<String, String> collectionStyle(org.jsoup.nodes.Document doc) {
		Map<String, String> className = new HashMap<>();
		Elements style = doc.getElementsByTag("style");
		if (!style.isEmpty()) {
			String[] classNames = style.html().split("\\}");
			for (String cla : classNames) {
				String[] r = cla.split("\\{");
				className.put(r[0].trim().substring(1), r[1]);
			}
		}
		return className;
	}

	private void replaceStyle(Map<String, String> className, Elements elems) {
		for (Element ele : elems) {
			// 替换样式
			// String claname = ele.className();
			ele.removeAttr("class");
			// if (!claname.isEmpty()) {
			// if (className.containsKey(claname)) {
			// String style = className.get(claname);
			// if (style.contains("text-decoration:underline")) {
			// String html = ele.html();
			// ele.html(html.replaceAll(WordToHtml.HTML_SPACE, "_"));
			// }
			//
			// }
			// }
			String style = ele.attr("style");
			if (style.contains("text-decoration:underline")) {
				String html = ele.html();
				ele.html(html.replaceAll(WordToHtml.HTML_SPACE, "_"));
			}
			ele.removeAttr("style");
			if (ele.children().size() > 0) {
				replaceStyle(className, ele.children());
			}
		}
	}
}
