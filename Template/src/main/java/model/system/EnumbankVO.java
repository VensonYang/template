package model.system;

public class EnumbankVO {

	public interface IAddEnumbank {
	}

	public interface IModifyEnumbank {
	}

	private Integer id;
	private String enumtypeid;
	private String enumtypename;
	private String enumid;
	private String enumvalue;
	private Byte flag;
	private String remark;

	public EnumbankVO() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnumtypeid() {
		return this.enumtypeid;
	}

	public void setEnumtypeid(String enumtypeid) {
		this.enumtypeid = enumtypeid;
	}

	public String getEnumtypename() {
		return this.enumtypename;
	}

	public void setEnumtypename(String enumtypename) {
		this.enumtypename = enumtypename;
	}

	public String getEnumid() {
		return this.enumid;
	}

	public void setEnumid(String enumid) {
		this.enumid = enumid;
	}

	public String getEnumvalue() {
		return this.enumvalue;
	}

	public void setEnumvalue(String enumvalue) {
		this.enumvalue = enumvalue;
	}

	public Byte getFlag() {
		return this.flag;
	}

	public void setFlag(Byte flag) {
		this.flag = flag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
