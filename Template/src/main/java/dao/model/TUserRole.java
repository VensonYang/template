package dao.model;
// Generated 2016-6-22 14:06:48 by Hibernate Tools 4.3.1.Final

/**
 * TUserRole generated by hbm2java
 */
public class TUserRole implements java.io.Serializable {

	private Integer id;
	private TRole TRole;
	private TUser TUser;

	public TUserRole() {
	}

	public TUserRole(TRole TRole, TUser TUser) {
		this.TRole = TRole;
		this.TUser = TUser;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TRole getTRole() {
		return this.TRole;
	}

	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

}
