package model.parse;

import java.util.LinkedList;
import java.util.List;

public class Tree {
	private String text;
	private List<Tree> children = new LinkedList<Tree>();
	private Object origin;

	public void add(Tree e) {
		this.children.add(e);

	}

	public boolean remove(Tree e) {
		return this.children.remove(e);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public boolean hasChild() {
		if (this.children.isEmpty())
			return false;
		else
			return true;

	}

	public Object getOrigin() {
		return origin;
	}

	public void setOrigin(Object origin) {
		this.origin = origin;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Tree [text=" + text + ", children=" + children + ", origin=" + origin + "]";
	}

}
