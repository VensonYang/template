package common.parse.model;

import java.util.LinkedList;
import java.util.List;

public class QuestionImpl implements Question {
	private String type;
	private List<Item> items = new LinkedList<Item>();

	public QuestionImpl(String type) {
		this.type = type;
	}

	public QuestionImpl() {
		this.type = "抽象题";
	}

	@Override
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void add(Item e) {
		items.add(e);
	}

	@Override
	public void remove(Item e) {
		items.remove(e);
	}

	@Override
	public String toString() {
		return "Question [type=" + type + ", items=" + items + "]";
	}

	@Override
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
