package model.system;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class PriviledgesVO {
	public interface IAddPriviledges {
	}

	public interface IModifyPriviledges {
	}

	@NotNull(message = "ID不能为空", groups = { IModifyPriviledges.class })
	private Integer id;
	@NotNull(message = "名称不能为空", groups = { IAddPriviledges.class, IModifyPriviledges.class })
	private String name;
	@NotNull(message = "url不能为空", groups = { IAddPriviledges.class, IModifyPriviledges.class })
	private String url;
	@NotNull(message = "状态不能为空", groups = { IAddPriviledges.class, IModifyPriviledges.class })
	private String status;
	private Date createTime;
	private Date modifyTime;
	private Integer pid;
	private String icon;
	private Integer creator;
	private String memo;
	private Boolean isParent;

	public PriviledgesVO() {
	}

	public PriviledgesVO(Integer id, String name, Integer pid) {
		this.id = id;
		this.name = name;
		this.pid = pid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

}
