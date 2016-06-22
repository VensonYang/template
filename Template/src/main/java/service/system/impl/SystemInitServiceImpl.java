package service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BaseDao;
import service.system.SystemInitService;

@Service("systemInitService")
public class SystemInitServiceImpl implements SystemInitService {

	public static final String[] NUM_ARRAY = { "零", "一", "二", "三", "四", "五", "六" };
	public static final Map<String, Integer> NUM_MAP = new HashMap<String, Integer>();
	static {
		NUM_MAP.put("一", 1);
		NUM_MAP.put("二", 2);
		NUM_MAP.put("三", 3);
		NUM_MAP.put("四", 4);
		NUM_MAP.put("五", 5);
		NUM_MAP.put("六", 6);
	}

	@Autowired
	private BaseDao baseDao;

	@Override
	public void initMoralStudent() {
		String hql = "DELETE TMoralStudent";
		baseDao.excuteHQL(hql);
	}

	@Override
	public void initStudentAppraisal() {
		String hql = "DELETE TStudentAppraisal";
		baseDao.excuteHQL(hql);
	}

	@Override
	public void initStudent() {
		// 清空学生
		// 1.清空德育记录
		String hql1 = "DELETE TMoralStudent";
		// 2.清空学生鉴定
		String hql2 = "DELETE TStudentAppraisal";
		// 3.清空用户角色
		String sql3 = "DELETE  a.* FROM t_user_role a LEFT JOIN t_user b ON a.userID=b.id WHERE b.userAccount IN (SELECT b.id FROM t_student b) ";
		// 4.清空用户
		String hql4 = "DELETE TUser WHERE userAccount IN (SELECT b.id FROM TStudent b)";
		// 5.清空学生
		String hql5 = "DELETE TStudent";
		baseDao.excuteHQL(hql1);
		baseDao.excuteHQL(hql2);
		baseDao.excuteSQL(sql3);
		baseDao.excuteHQL(hql4);
		baseDao.excuteHQL(hql5);
	}

	@Override
	public void initTeacher() {
		// 1.清空教师负责班级
		String hql1 = "DELETE TTeacherClass a";
		// 3.清空用户角色
		String sql2 = "DELETE  a.* FROM t_user_role a LEFT JOIN t_user b ON a.userID=b.id WHERE b.userAccount IN (SELECT b.id FROM t_teacher b WHERE b.id <> '9999') ";
		// 4.清空用户
		String hql3 = "DELETE TUser WHERE userAccount IN (SELECT b.id FROM TTeacher b WHERE b.id <> '9999')";
		// 5.清空教师
		String hql4 = "DELETE TTeacher  WHERE id <> '9999'";
		baseDao.excuteHQL(hql1);
		baseDao.excuteSQL(sql2);
		baseDao.excuteHQL(hql3);
		baseDao.excuteHQL(hql4);

	}

	@Override
	public void initClass() {
		String hql = "DELETE TClass";
		baseDao.excuteHQL(hql);

	}

	@Override
	public void initCourse() {
		String hql = "DELETE TXtEnumbank a WHERE a.enumtypeid='0005'";
		baseDao.excuteHQL(hql);

	}

	@Override
	public void initMoral() {
		String hql1 = "DELETE TMoralRules";
		String hql2 = "DELETE TMoralType";
		baseDao.excuteHQL(hql1);
		baseDao.excuteHQL(hql2);
	}

	@Override
	public void updateSystem() {
		// 更新学生用户状态语句
		String sql1 = "UPDATE t_user a SET a.status='0' WHERE a.status='1' AND a.userAccount IN "
				+ "(SELECT a.id FROM t_student a WHERE a.status='1' AND  a.classid IN "
				+ "(SELECT a.id FROM t_class a WHERE a.status='1' AND a.grade='6' AND a.termid<> "
				+ "(SELECT b.id FROM t_school_term b WHERE b.status='1')))";
		// 更新学生状态语句
		String sql2 = "UPDATE t_student a SET a.status='0' WHERE  a.classid IN "
				+ "(SELECT a.id FROM t_class a WHERE  a.status='1' AND a.status='1' AND a.grade='6' AND a.termid<> "
				+ "(SELECT b.id FROM t_school_term b WHERE b.status='1'))";
		// 更新学生班级语句
		String sql3 = "UPDATE t_class a SET a.status='0' WHERE a.status='1' AND a.grade='6' AND a.termid<> "
				+ "(SELECT b.id FROM t_school_term b WHERE b.status='1')";
		// 更新班级年级
		String sql4 = "UPDATE t_class a SET a.grade=(a.grade+1) WHERE  a.status='1' AND a.termid<> "
				+ "(SELECT b.id FROM t_school_term b WHERE b.status='1')";
		updateClassName();
		// 更新班级学期
		String sql5 = "UPDATE t_class a SET a.termid=(SELECT b.id FROM t_school_term b WHERE b.status='1') "
				+ "WHERE a.status='1' AND a.termid<> (SELECT b.id FROM t_school_term b WHERE b.status='1')";
		baseDao.excuteSQL(sql1);
		baseDao.excuteSQL(sql2);
		baseDao.excuteSQL(sql3);
		baseDao.excuteSQL(sql4);
		baseDao.excuteSQL(sql5);
	}

	/**
	 * 班级名称升级
	 */
	private void updateClassName() {
		String hql = "SELECT new Map(a.id as id,a.classname as name) FROM TClass a WHERE a.status='1' AND a.grade<>'6' AND a.TSchoolTerm.id<>(SELECT b.id FROM TSchoolTerm b WHERE b.status='1')";
		String hql1 = "UPDATE TClass a SET a.classname=:name WHERE a.id=:id";
		List<Map<String, Object>> data = baseDao.findAll(hql);
		Map<String, Object> params = new HashMap<String, Object>();
		for (Map<String, Object> map : data) {
			// 获取原始名称
			String name = map.get("name").toString();
			String startName = name.substring(0, 1);
			String endName = name.substring(1, name.length());
			// 将名称转换升级
			int index = NUM_MAP.get(startName) + 1;
			String grade = NUM_ARRAY[index];
			String newName = grade + endName;
			// 执行更新
			params.put("id", map.get("id"));
			params.put("name", newName);
			baseDao.update(hql1, params);

		}
	}

}
