package common.parse;

import common.parse.model.Paper;
import common.parse.model.Question;

public interface Parse {

	void parse(Question question, Paper paper);

	boolean hasError();

	String getMessage();

}
