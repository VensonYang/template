package model.common;

public class QueryVO {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name;
	private String sort;
	private String order;
	private int offset = 0;
	private int limit = 20;
	private String account;
	private String staTime;
	private String endTime;
	private String byTime;
	private String state;
	private String other1;
	private String other2;
	private String other3;
	private String other4;
	private String other5;
	private String other6;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStaTime() {
		return staTime;
	}

	public void setStaTime(String staTime) {
		this.staTime = staTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getByTime() {
		return byTime;
	}

	public void setByTime(String byTime) {
		this.byTime = byTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}

	public String getOther4() {
		return other4;
	}

	public void setOther4(String other4) {
		this.other4 = other4;
	}

	public String getOther5() {
		return other5;
	}

	public void setOther5(String other5) {
		this.other5 = other5;
	}

	public String getOther6() {
		return other6;
	}

	public void setOther6(String other6) {
		this.other6 = other6;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
