package model.system;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class PrivilegesVO {
	public interface IAddPrivileges {
	}

	public interface IModifyPrivileges {
	}

	@NotNull(message = "ID不能为空", groups = { IModifyPrivileges.class })
	private Integer id;
	@NotNull(message = "名称不能为空", groups = { IAddPrivileges.class, IModifyPrivileges.class })
	private String privilegesName;
	@NotNull(message = "url不能为空", groups = { IAddPrivileges.class, IModifyPrivileges.class })
	private String url;
	private String target;
	private Integer pid;
	private String icon;
	private Integer creator;
	private Date createTime;
	private Integer modifier;
	private Date modifyTime;
	@NotNull(message = "状态不能为空", groups = { IAddPrivileges.class, IModifyPrivileges.class })
	private String state;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrivilegesName() {
		return privilegesName;
	}

	public void setPrivilegesName(String privilegesName) {
		this.privilegesName = privilegesName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
