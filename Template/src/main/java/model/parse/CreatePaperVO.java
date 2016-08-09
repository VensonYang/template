package model.parse;

import javax.validation.constraints.NotNull;

/**
 * 
 * 创建试卷条件
 * 
 * @author venson
 */
public class CreatePaperVO {

	// 试题ids
	@NotNull(message = "ids不能为空")
	private String ids;
	// 试卷标题
	private String title;
	// 样式
	private String style;
	// 卷头
	private String head;
	// 试题排序
	private String sort;
	// 答案
	private boolean answer = true;
	// 内容
	private boolean content = true;
	// 是否在答案后面
	private boolean after = false;
	// 每道大题下的小题编号从1开始
	private boolean startOne = false;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public boolean isContent() {
		return content;
	}

	public void setContent(boolean content) {
		this.content = content;
	}

	public boolean isAfter() {
		return after;
	}

	public void setAfter(boolean after) {
		this.after = after;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean hasHead() {
		if (null == head || head.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean hasSort() {
		if (null == sort || sort.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isStartOne() {
		return startOne;
	}

	public void setStartOne(boolean startOne) {
		this.startOne = startOne;
	}

}
