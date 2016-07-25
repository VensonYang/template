package model.parse;

import java.util.LinkedList;
import java.util.List;

public class HighChartBar {
	private String name;
	private List<Object> data = new LinkedList<Object>();

	public HighChartBar() {
	}

	public HighChartBar(String name) {
		this.name = name;
	}

	public void add(Object e) {
		this.data.add(e);
	}

	public void remove(Object e) {
		this.data.remove(e);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

}
