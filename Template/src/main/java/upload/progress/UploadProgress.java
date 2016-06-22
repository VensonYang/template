package upload.progress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

public class UploadProgress implements ProgressListener {
	private HttpSession session;
	public static final String CURRENT_UPLOAD_STATUS = "currentUploadStatus";

	public UploadProgress(HttpServletRequest request) {
		this.session = request.getSession();

		UploadStatusVO newUploadStatus = new UploadStatusVO();

		this.session.setAttribute(CURRENT_UPLOAD_STATUS, newUploadStatus);
	}

	public void update(long bytesRead, long contentLength, int currentItem) {
		UploadStatusVO status = (UploadStatusVO) this.session.getAttribute(CURRENT_UPLOAD_STATUS);

		status.setBytesRead(bytesRead);
		status.setContentLength(contentLength);
		status.setItems(currentItem);
	}
}