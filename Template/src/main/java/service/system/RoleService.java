package service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TRole;
import model.common.QueryVO;
import model.system.PrivilegesVO;
import model.system.RoleVO;

public interface RoleService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryRole(QueryVO queryVO);

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	Serializable addRole(RoleVO obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	void modifyRole(RoleVO obj);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	void deleteRole(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	TRole getRoleById(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	RoleVO getRoleVOById(int id);

	/**
	 * 获取全部角色
	 * 
	 */
	List<Map<String, Object>> getRoles();

	/**
	 * 增加角色权限
	 * 
	 * @param obj
	 *            值模型对象
	 */
	Serializable addRolePrivileges(RoleVO obj);

	List<PrivilegesVO> getPrivilegesVOByRoleId(int id);

}
