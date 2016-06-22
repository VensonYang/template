package controller.base;

public enum ValidParam implements ValidType {
	NUM("num", "id"), NOT_BLANK("notBlank", "id");

	private String vaildType;
	private String param;

	private ValidParam(String vaildType, String param) {
		this.vaildType = vaildType;
		this.param = param;
	}

	public String getVaildType() {
		return vaildType;
	}

	public String getParam() {
		return param;
	}

}
