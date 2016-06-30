package common.parse.model;

import java.util.LinkedList;
import java.util.List;

public class Question {
	public static final String CHOICE = "选择题";
	public static final String COMPLETE = "填空题";
	public static final String SHORT_ANSWER = "简答题";
	public static final String ADDITIONAL = "附加题";
	public static final String READING = "阅读题";
	public static final String COMPREHENSIVE = "综合题";
	public static final String SOLVE = "解答题";
	public static final String ANSWER = "***答案区***";
	private String type;
	private List<Item> items = new LinkedList<Item>();

	public Question(String type) {
		this.type = type;
	}

	public Question() {
		this.type = "抽象题";
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void add(Item e) {
		items.add(e);
	}

	public void remove(Item e) {
		items.remove(e);
	}

	public String toString() {
		return "Question [type=" + type + ", items=" + items + "]";
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
