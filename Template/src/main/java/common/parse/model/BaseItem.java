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

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	@Override
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String getImgPath() {
		return imgPath;
	}

	@Override
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String getDifficulty() {
		return difficulty;
	}

	@Override
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String getExplain() {
		return explain;
	}

	@Override
	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Override
	public String toString() {
		return "BaseItem [title=" + title + ", answer=" + answer + ", imgPath=" + imgPath + ", difficulty=" + difficulty
				+ ", explain=" + explain + "]";
	}

}
