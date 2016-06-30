package common.parse.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Paper {
	public static final String CHINESE = "语文";
	public static final String MATH = "数学";
	public static final String ENGLIST = "英语";
	public static final String PHYSICS = "物理";
	public static final String CHEMISTRY = "化学";
	public static final String BIOLOGY = "生物";
	public static final String POLITICAL = "政治";
	public static final String HISTORY = "历史";
	public static final String GEOGRAPHY = "地理";

	private String name;
	private int status;
	private String message;
	private List<Question> questions = new LinkedList<Question>();

	public Paper(String name) {
		this.name = name;
		this.status = 0;
	}

	public Paper() {
		this.name = "抽象";
		this.status = 0;
	}

	public void add(Question e) {
		questions.add(e);
	}

	public void remove(Question e) {
		questions.remove(e);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Paper [name=" + name + ", status=" + status + ", message=" + message + ", questions=" + questions + "]";
	}

	public String getMessage() {
		return this.message;
	}

	public boolean hasAnswer() {
		if (questions.isEmpty()) {
			return false;
		} else {
			Iterator<Question> it = questions.iterator();
			while (it.hasNext()) {
				Question question = it.next();
				if (question.getType().equals(Question.ANSWER)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Paper setMessage(String msg) {
		this.message = msg;
		return this;
	}

	public boolean hasError() {
		if (this.message == null || this.message.trim().length() == 0)
			return false;
		else
			return true;
	}

}
