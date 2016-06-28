package common.parse.model;

import java.util.LinkedList;
import java.util.List;

public class complexItem extends BaseItem {

	private List<Item> items = new LinkedList<Item>();

	public void add(Item e) {
		items.add(e);
	}

	public void remove(Item e) {
		items.remove(e);
	}

	public List<Item> getItem() {
		return this.items;
	}

	@Override
	public String toString() {
		return "complexItem [items=" + items + ", title=" + title + ", answer=" + answer + ", imgPath=" + imgPath + "]";
	}

}
