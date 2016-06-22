package model.user;

import java.util.Map;

@SuppressWarnings("serial")
public class PriviledgesVectorVO implements java.io.Serializable {
	private Map<String, Object> priviledge;
	private Map<String, Boolean> priviledgesMatrix;

	public Map<String, Object> getPriviledge() {
		return priviledge;
	}

	public void setPriviledge(Map<String, Object> priviledge) {
		this.priviledge = priviledge;
	}

	public Map<String, Boolean> getPriviledgesMatrix() {
		return priviledgesMatrix;
	}

	public void setPriviledgesMatrix(Map<String, Boolean> priviledgesMatrix) {
		this.priviledgesMatrix = priviledgesMatrix;
	}

	@Override
	public String toString() {
		return "PriviledgesVector [priviledge=" + priviledge + ", priviledgesMatrix=" + priviledgesMatrix + "]";
	}

}
