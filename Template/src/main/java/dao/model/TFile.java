package dao.model;
// Generated 2016-8-23 9:45:27 by Hibernate Tools 4.3.4.Final

import java.util.Date;

/**
 * TFile generated by hbm2java
 */
public class TFile implements java.io.Serializable {

	private Integer id;
	private String fileDesc;
	private String fileName;
	private String fileType;
	private Integer fileSize;
	private String filePath;
	private Integer creator;
	private Date createTime;
	private Character state;
	private String remark;

	public TFile() {
	}

	public TFile(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public TFile(String fileDesc, String fileName, String fileType, Integer fileSize, String filePath, Integer creator,
			Date createTime, Character state, String remark) {
		this.fileDesc = fileDesc;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.creator = creator;
		this.createTime = createTime;
		this.state = state;
		this.remark = remark;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileDesc() {
		return this.fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getCreator() {
		return this.creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Character getState() {
		return this.state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
