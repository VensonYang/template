package model.system;

import java.util.Date;

import javax.validation.constraints.NotNull;

import model.system.PriviledgesVO.IAddPriviledges;

public class RoleVO {
	public interface IAddRole {
	}

	public interface IModifyRole {
	}

	public interface IAddRolePriviledges {

	}

	@NotNull(message = "角色ID不能为空", groups = { IModifyRole.class, IAddPriviledges.class })
	private Integer id;
	@NotNull(message = "角色名不能为空", groups = { IAddRole.class, IModifyRole.class })
	private String name;
	private Date createTime;
	private Date modifyTime;
	@NotNull(message = "状态不能为空", groups = { IAddRole.class, IModifyRole.class })
	private String status;
	private String memo;
	@NotNull(message = "权限ID不能为空", groups = { IAddPriviledges.class })
	private int priviledgesIds[];

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int[] getPriviledgesIds() {
		return priviledgesIds;
	}

	public void setPriviledgesIds(int[] priviledgesIds) {
		this.priviledgesIds = priviledgesIds;
	}

}
