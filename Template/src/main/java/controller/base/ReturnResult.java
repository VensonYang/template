package controller.base;

public class ReturnResult {
	private int status;
	private Object total;
	private String message;
	private Object rows;

	public int getStatus() {
		return this.status;
	}

	public ReturnResult setStatus(StatusType status) {
		this.status = status.getStatus();
		this.message = status.getMessage();
		if (this.status != 0) {
			this.rows = null;
			this.total = 0;
		}
		return this;
	}

	public String getMessage() {
		return this.message;
	}

	public ReturnResult setMessage(StatusType message) {
		this.message = message.getMessage();
		return this;
	}

	public Object getTotal() {
		return this.total;
	}

	public ReturnResult setTotal(Object total) {
		this.total = total;
		return this;
	}

	public Object getRows() {
		return this.rows;
	}

	public ReturnResult setRows(Object rows) {
		this.rows = rows;
		return this;
	}

}