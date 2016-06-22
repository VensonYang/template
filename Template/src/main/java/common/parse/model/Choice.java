package common.parse.model;

public class Choice extends AbstractItem {

	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;

	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	public String getAnswerD() {
		return answerD;
	}

	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}

	@Override
	public String toString() {
		return "Choice [title=" + title + "\nanswerA=" + answerA + "\n answerB=" + answerB + "\n answerC=" + answerC
				+ "\n answerD=" + answerD + "\nimgPath=" + imgPath + "]";
	}

}
