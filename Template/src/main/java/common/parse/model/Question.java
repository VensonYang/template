package common.parse.model;

import java.util.List;

public interface Question {
	String CHOICE = "选择题";
	String COMPLETE = "填空题";
	String SHORT_ANSWER = "简答题";
	String ADDITIONAL = "附加题";
	String READING = "阅读题";

	void setType(String type);

	String getType();

	void add(Item e);

	void remove(Item e);

	List<Item> getItem();
}
