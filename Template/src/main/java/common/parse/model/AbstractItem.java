package common.parse.model;

public class AbstractItem implements Item {

	protected String title;
	protected String answer;
	protected String explain;
	protected String imgPath;

	public String getTitle() {
		return title;
	}

	public AbstractItem setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAnswer() {
		return answer;
	}

	public AbstractItem setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

	public String getExplain() {
		return explain;
	}

	public AbstractItem setExplain(String explain) {
		this.explain = explain;
		return this;
	}

	public String getImgPath() {
		return imgPath;
	}

	public AbstractItem setImgPath(String imgPath) {
		this.imgPath = imgPath;
		return this;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + (imgPath != null ? ", imgPath=" + imgPath : "") + "]";
	}

}
