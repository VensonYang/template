package model.parse;

import javax.validation.constraints.NotNull;

public class QueryItem {

	public interface IQueryItem {
	}

	public interface IDifficulty {
	}

	@NotNull(message = "课程不能为空！", groups = { IQueryItem.class, IDifficulty.class })
	private String course;
	@NotNull(message = "题型不能为空", groups = { IQueryItem.class, IDifficulty.class })
	private String question;
	@NotNull(message = "难度不能为空", groups = { IDifficulty.class })
	private String difficulty;
	@NotNull(message = "章节不能为空", groups = { IQueryItem.class, IDifficulty.class })
	private String section;
	private String subject;
	private Integer itemCount;
	private String filterdate;
	private String beginyear;
	private String endyear;
	private String level;
	private String comfrom;
	private boolean useLess = false;

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getFilterdate() {
		return filterdate;
	}

	public void setFilterdate(String filterdate) {
		this.filterdate = filterdate;
	}

	public String getBeginyear() {
		return beginyear;
	}

	public void setBeginyear(String beginyear) {
		this.beginyear = beginyear;
	}

	public String getEndyear() {
		return endyear;
	}

	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getComfrom() {
		return comfrom;
	}

	public void setComfrom(String comfrom) {
		this.comfrom = comfrom;
	}

	public boolean isUseLess() {
		return useLess;
	}

	public void setUseLess(boolean useLess) {
		this.useLess = useLess;
	}

}
