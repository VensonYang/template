package common.parse.model;

import java.util.List;

import controller.result.StatusCode;

public interface Paper {

	String CHINESE = "语文";
	String MATH = "数学";
	String ENGLIST = "英语";

	String getName();

	void add(Question e);

	void remove(Question e);

	List<Question> getQuestion();

	String getMessage();

	int getStatus();

	Paper setResult(StatusCode code);
}
