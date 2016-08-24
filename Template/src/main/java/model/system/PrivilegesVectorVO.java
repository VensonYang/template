package model.system;

import java.util.Map;

@SuppressWarnings("serial")
public class PrivilegesVectorVO implements java.io.Serializable {
	private Map<String, Object> privilege;
	private Map<String, Boolean> privilegeMatrix;

	public Map<String, Object> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Map<String, Object> privilege) {
		this.privilege = privilege;
	}

	public Map<String, Boolean> getPrivilegeMatrix() {
		return privilegeMatrix;
	}

	public void setPrivilegeMatrix(Map<String, Boolean> privilegeMatrix) {
		this.privilegeMatrix = privilegeMatrix;
	}

}
