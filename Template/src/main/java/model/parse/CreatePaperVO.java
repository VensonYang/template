package model.parse;

import javax.validation.constraints.NotNull;

public class CreatePaperVO {

	@NotNull(message = "ids不能为空")
	private String ids;
	private String title;
	private String style;
	private String head;
	private String sort;
	private boolean answer = true;
	private boolean content = true;
	private boolean after = false;
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
