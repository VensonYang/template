package dao.model;
// Generated 2016-6-22 14:06:48 by Hibernate Tools 4.3.1.Final

/**
 * TRolePriviledges generated by hbm2java
 */
public class TRolePriviledges implements java.io.Serializable {

	private Integer id;
	private TPriviledges TPriviledges;
	private TRole TRole;

	public TRolePriviledges() {
	}

	public TRolePriviledges(TPriviledges TPriviledges, TRole TRole) {
		this.TPriviledges = TPriviledges;
		this.TRole = TRole;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TPriviledges getTPriviledges() {
		return this.TPriviledges;
	}

	public void setTPriviledges(TPriviledges TPriviledges) {
		this.TPriviledges = TPriviledges;
	}

	public TRole getTRole() {
		return this.TRole;
	}

	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

}
