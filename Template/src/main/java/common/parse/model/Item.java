package common.parse.model;

import java.util.LinkedList;
import java.util.List;

public class Item {
	// 题目
	protected String title;
	// 答案
	protected String answer;
	// 图片
	protected String imgPath;
	// 难度
	protected String difficulty;
	// 说明
	protected String explain;

	private List<Item> items = new LinkedList<Item>();

	public void add(Item e) {
		items.add(e);
	}

	public void remove(Item e) {
		items.remove(e);
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public boolean hasItem() {
		if (this.items.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", answer=" + answer + ", imgPath=" + imgPath + ", difficulty=" + difficulty
				+ ", explain=" + explain + ", items=" + items + "]";
	}

}
