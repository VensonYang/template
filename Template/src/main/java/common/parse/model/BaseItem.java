package common.parse.model;

public class BaseItem implements Item {

	// 题目
	protected String title;
	// 答案
	protected String answer;
	// 图片
	protected String imgPath;
	// 难度
	protected String difficulty;
	// 说明
	protected String explain;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Override
	public String toString() {
		return "BaseItem [title=" + title + ", answer=" + answer + ", imgPath=" + imgPath + ", difficulty=" + difficulty
				+ ", explain=" + explain + "]";
	}

}
