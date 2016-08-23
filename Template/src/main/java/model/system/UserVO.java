package model.system;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class UserVO {
	public interface IAddUser {
	}

	public interface IModifyUser {
	}

	@NotNull(message = "Id不能为空", groups = { IModifyUser.class })
	private Integer id;
	@NotNull(message = "姓名不能为空", groups = { IAddUser.class, IModifyUser.class })
	private String userName;
	@NotNull(message = "账号不能为空", groups = { IAddUser.class, IModifyUser.class })
	private String userAccount;
	private String password;
	@NotNull(message = "性别不能为空", groups = { IAddUser.class, IModifyUser.class })
	private String sex;
	@NotNull(message = "状态不能为空", groups = { IAddUser.class, IModifyUser.class })
	private String state;
	@NotNull(message = "用户角色不能为空", groups = { IAddUser.class, IModifyUser.class })
	private Integer roleId;
	@NotNull(message = "用户部门不能为空", groups = { IAddUser.class, IModifyUser.class })
	private Integer deptId;
	private String kemu;
	private String courseIds;
	private Integer creator;
	private Date createTime;
	private Integer modifier;
	private Date modifyTime;
	private String headImage = "../images/default-img.gif";

	private String email;
	private String mobile;
	private Date loginDate;
	private String loginIp;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getKemu() {
		return kemu;
	}

	public void setKemu(String kemu) {
		this.kemu = kemu;
	}

	public String getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
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

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
