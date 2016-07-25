package service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TDepartment;
import model.common.QueryVO;
import model.system.DepartmentVO;

public interface DepartmentService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryDepartment(QueryVO queryVO);

	/**
	 * 增加对象
	 * 
	 * @param departmentVO
	 *            值模型对象
	 */
	Serializable addDepartment(DepartmentVO departmentVO);

	/**
	 * 修改对象
	 * 
	 * @param departmentVO
	 *            值模型对象
	 */
	void modifyDepartment(DepartmentVO departmentVO);

	/**
	 * 根据Id删除对象
	 * 
	 * @param departmentId
	 */
	void deleteDepartment(Object departmentId);

	/**
	 * 根据Id获取表对象
	 * 
	 * @param departmentId
	 */
	TDepartment getDepartmentById(Object departmentId);

	/**
	 * 根据Id获取值模型对象
	 * 
	 * @param departmentId
	 */
	DepartmentVO getDepartmentVOById(Object departmentId);

	/* 获取所有的部门 **/
	List<Map<String, Object>> getAllDepartment();

}
