package common.parse.model;

import java.util.List;

import controller.result.StatusCode;

public interface Paper {

	String CHINESE = "语文";
	String MATH = "数学";
	String ENGLIST = "英语";
	String GEOGRAPHY = "地理";
	String BIOLOGY = "生物";
	String CHEMISTRY = "化学";
	String POLITICAL = "政治";
	String Physics = "物理";
	String HISTORY = "历史";

	String getName();

	void add(Question e);

	void remove(Question e);

	List<Question> getQuestion();

	String getMessage();

	int getStatus();

	Paper setResult(StatusCode code);
}
