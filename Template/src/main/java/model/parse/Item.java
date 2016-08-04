package model.parse;

import java.util.LinkedList;
import java.util.List;

public class Item {
	// ID
	private Integer id;
	// 题目
	private String title;
	// 答案
	private String answer;
	// 图片
	private String imgPath;
	// 难度
	private String difficulty;
	// 说明
	private String explain;
	// 题型
	private String question;

	// 错误
	private String error;

	private List<Item> items = new LinkedList<Item>();

	public Item() {
	}

	public Item(String title, String imgPath, String question) {
		this.title = title;
		this.imgPath = imgPath;
		this.question = question;
	}

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

	public boolean hasPicture() {
		if (this.imgPath == null || this.imgPath.trim().length() < 2)
			return false;
		else
			return true;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", answer=" + answer + ", imgPath=" + imgPath + ", difficulty=" + difficulty
				+ ", explain=" + explain + "]";
	}

}
