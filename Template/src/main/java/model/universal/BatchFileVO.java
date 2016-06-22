package model.universal;

import javax.validation.constraints.NotNull;

public class BatchFileVO {

	@NotNull(message = "文件名不能为空")
	private String[] fileNames;
	@NotNull(message = "文件类型不能为空")
	private String[] fileTypes;
	@NotNull(message = "文件大小不能为空")
	private Integer[] fileSizes;
	@NotNull(message = "文件名不能为空")
	private String[] builderNames;

	private String floder;

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String[] getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String[] fileTypes) {
		this.fileTypes = fileTypes;
	}

	public Integer[] getFileSizes() {
		return fileSizes;
	}

	public void setFileSizes(Integer[] fileSizes) {
		this.fileSizes = fileSizes;
	}

	public String[] getBuilderNames() {
		return builderNames;
	}

	public void setBuilderNames(String[] builderNames) {
		this.builderNames = builderNames;
	}

	public String getFloder() {
		return floder;
	}

	public void setFloder(String floder) {
		this.floder = floder;
	}

}
