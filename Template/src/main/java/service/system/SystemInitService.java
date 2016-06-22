package service.system;

public interface SystemInitService {

	/**
	 * 清空德育记录表
	 */
	void initMoralStudent();

	/**
	 * 清空学期鉴定表
	 */
	void initStudentAppraisal();

	/**
	 * 清空学生表
	 */
	void initStudent();

	/**
	 * 清空教师表
	 */
	void initTeacher();

	/**
	 * 清空班级表
	 */
	void initClass();

	/**
	 * 清空课程表
	 */
	void initCourse();

	/**
	 * 清空德育类别，规则表
	 */
	void initMoral();

	/**
	 * <p>
	 * 系统升级<br>
	 * 学生年级更新,最高年级下的班级、学生、用户等状态禁用<br>
	 * 上一学年低年级班级升至高一级年级
	 * </p>
	 */
	void updateSystem();
}
