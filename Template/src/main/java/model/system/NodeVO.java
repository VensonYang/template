package model.system;

import java.util.LinkedList;
import java.util.List;

public class NodeVO {
	private int id;
	private String name;
	private String url;
	private String target;
	private int pid;
	private String icon;
	private String remark;

	private List<NodeVO> childNode = new LinkedList<NodeVO>();

	public NodeVO() {
	}

	public NodeVO(int id, String name, String url, int pid, String icon) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.pid = pid;
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void add(NodeVO node) {
		this.childNode.add(node);
	};

	public void remove(NodeVO node) {
		if (this.childNode.contains(node)) {
			this.childNode.remove(node);
		}
	}

	@Override
	public String toString() {
		return "NodeVO [id=" + id + ", name=" + name + ", childNode=" + childNode + "]";
	}

	public List<NodeVO> getChildNode() {
		return childNode;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setChildNode(List<NodeVO> childNode) {
		this.childNode = childNode;
	}

}
