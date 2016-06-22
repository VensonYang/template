package common.parse.model;

import java.util.LinkedList;
import java.util.List;

import controller.result.StatusCode;

public class PaperImpl implements Paper {
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

	public PaperImpl(String name) {
		this.name = name;
		this.status = 0;
	}

	@Override
	public void add(Question e) {
		questions.add(e);
	}

	@Override
	public void remove(Question e) {
		questions.remove(e);
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Paper [name=" + name + ", status=" + status + ", message=" + message + ", questions=" + questions + "]";
	}

	@Override
	public List<Question> getQuestion() {
		return this.questions;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public int getStatus() {
		return this.status;
	}

	@Override
	public Paper setResult(StatusCode code) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		return this;
	}

}
