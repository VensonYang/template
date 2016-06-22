package upload.progress;

public class UploadStatusVO {
	private long bytesRead;
	private long contentLength;
	private int items;
	private long startTime = System.currentTimeMillis();

	public long getBytesRead() {
		return this.bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}

	public long getContentLength() {
		return this.contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public int getItems() {
		return this.items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String toString() {
		return "ResourceFileUploadStatus [bytesRead=" + this.bytesRead + ", contentLength=" + this.contentLength
				+ ", items=" + this.items + ", startTime=" + this.startTime + "]";
	}
}