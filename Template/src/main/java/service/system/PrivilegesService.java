package service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TPrivileges;
import model.common.QueryVO;
import model.system.NodeVO;
import model.system.PrivilegesVO;
import model.system.PrivilegesVectorVO;

public interface PrivilegesService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryPrivileges(QueryVO queryVO);

	/**
	 * 根据用户Id获取用户权限矩阵集
	 * 
	 * @param userId
	 *            用户Id
	 */
	Map<Integer, PrivilegesVectorVO> getPrivilegesVectors(int userId);

	/**
	 * 根据用户Id获取用户权限
	 * 
	 * @param userId
	 *            用户Id
	 */
	List<TPrivileges> getPrivilegesByUserId(int userId);

	/**
	 * 根据角色Id获取角色权限
	 * 
	 * @param roleId
	 *            角色Id
	 */
	List<TPrivileges> getPrivilegesByRoleId(int roleId);

	/**
	 * 根据用户Id获取用户菜单
	 * 
	 * @param userId
	 *            用户Id
	 */
	NodeVO getMenuByUserId(int userId);

	/**
	 * 获取所有菜单
	 */
	NodeVO getMenu();

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	Serializable addPrivileges(PrivilegesVO obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	void modifyPrivileges(PrivilegesVO obj);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	void deletePrivileges(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	TPrivileges getPrivilegesById(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	PrivilegesVO getPrivilegesVOById(int id);
}
