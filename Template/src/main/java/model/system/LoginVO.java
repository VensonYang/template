package model.system;

import javax.validation.constraints.NotNull;

public class LoginVO {
	@NotNull(message = "用户账号不能为空！", groups = { Ilogin.class })
	private String userAccount;
	@NotNull(message = "用户密码不能为空！", groups = { Ilogin.class, IModifyPas.class })
	private String password;
	private String userType;
	@NotNull(message = "旧密码不能为空！", groups = { IModifyPas.class })
	private String oldPassword;
	private boolean savePassword;
	private String verifyCode;

	public interface Ilogin {
	}

	public interface IModifyPas {
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public boolean isSavePassword() {
		return savePassword;
	}

	public void setSavePassword(boolean savePassword) {
		this.savePassword = savePassword;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public String toString() {
		return "LoginVO [userAccount=" + userAccount + ", password=" + password + ", userType=" + userType
				+ ", oldPassword=" + oldPassword + ", savePassword=" + savePassword + ", verifyCode=" + verifyCode
				+ "]";
	}

}
