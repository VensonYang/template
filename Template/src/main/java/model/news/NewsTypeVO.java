package model.news;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class NewsTypeVO {
	public interface IAddNewsType {
	}

	public interface IModifyNewsType {
	}

	@NotNull(message = "ID不能为空", groups = { IModifyNewsType.class })
	private Integer id;
	@NotNull(message = "名称不能为空", groups = { IAddNewsType.class, IModifyNewsType.class })
	private String typeName;
	@NotNull(message = "状态不能为空", groups = { IAddNewsType.class, IModifyNewsType.class })
	private String state;
	private Date createTime;
	private Date modifyTime;
	private int creator;
	private Integer modifier;
	private int sort;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
