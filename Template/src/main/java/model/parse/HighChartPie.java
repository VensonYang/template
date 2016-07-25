package model.parse;

import java.util.LinkedList;
import java.util.List;

public class HighChartPie {
	private String type;
	private String name;
	private List<Object> data = new LinkedList<Object>();

	public HighChartPie() {
	}

	public HighChartPie(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void add(Object e) {
		this.data.add(e);
	}

	public void remove(Object e) {
		this.data.remove(e);
	}

	public void setType(String type) {
		this.type = type;
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
