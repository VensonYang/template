package model.system;

import java.util.Date;

import javax.validation.constraints.NotNull;

import model.system.PrivilegesVO.IAddPrivileges;

public class RoleVO {
	public interface IAddRole {
	}

	public interface IModifyRole {
	}

	public interface IAddRolePrivileges {

	}

	@NotNull(message = "角色ID不能为空", groups = { IModifyRole.class, IAddPrivileges.class })
	private Integer id;
	@NotNull(message = "角色名不能为空", groups = { IAddRole.class, IModifyRole.class })
	private String roleName;

	private Integer creator;
	private Integer modifier;
	private Date createTime;
	private Date modifyTime;
	@NotNull(message = "状态不能为空", groups = { IAddRole.class, IModifyRole.class })
	private String state;
	private String remark;
	@NotNull(message = "权限ID不能为空", groups = { IAddPrivileges.class })
	private int privilegesIds[];

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
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

	public int[] getPrivilegesIds() {
		return privilegesIds;
	}

	public void setPrivilegesIds(int[] privilegesIds) {
		this.privilegesIds = privilegesIds;
	}

}
