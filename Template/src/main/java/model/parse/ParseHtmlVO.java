package model.parse;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParseHtmlVO {
	private String path;
	private String subject;
	private List<Map<String, Object>> questions;

	public ParseHtmlVO(String path) {
		this.path = path;
		this.subject = "抽象";
	}

	public ParseHtmlVO(String path, String subject) {
		this.path = path;
		this.subject = subject;
	}

	public ParseHtmlVO(String path, String subject, List<Map<String, Object>> questions) {
		this.path = path;
		this.subject = subject;
		this.questions = questions;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Set<String> getQuestions() {
		Set<String> set = new HashSet<String>();
		if (null != questions && !questions.isEmpty()) {
			for (Map<String, Object> question : questions) {
				set.add((String) question.get("text"));
			}
		}
		return set;

	}

	public void setQuestions(List<Map<String, Object>> questions) {
		this.questions = questions;
	}

}
