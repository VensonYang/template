package model.parse;

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
	private String name;
	private boolean complex = false;
	private List<Item> items = new LinkedList<Item>();

	public Question(String name) {
		this.name = name;
	}

	public Question() {
		this.name = "抽象题";
	}

	public boolean isComplex() {
		return complex;
	}

	public void setComplex(boolean complex) {
		this.complex = complex;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void add(Item e) {
		items.add(e);
	}

	public void remove(Item e) {
		items.remove(e);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Question [name=" + name + ", complex=" + complex + ", items=" + items + "]";
	}

}
