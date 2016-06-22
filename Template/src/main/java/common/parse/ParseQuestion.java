package common.parse;

import common.parse.model.Paper;
import common.parse.model.Question;

public interface ParseQuestion {

	void parse(Question question, Paper paper);
}
