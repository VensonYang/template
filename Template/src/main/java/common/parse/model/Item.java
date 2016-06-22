package common.parse.model;

public interface Item {

	String getTitle();

	String getExplain();

	String getAnswer();

	String getImgPath();

	Item setTitle(String title);

	Item setExplain(String explain);

	Item setAnswer(String answer);

	Item setImgPath(String imgPath);
}
