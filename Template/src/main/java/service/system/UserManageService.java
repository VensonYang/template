package service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TUser;
import model.common.QueryVO;
import model.system.UserVO;

public interface UserManageService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 * @param userId
	 *            用户Id
	 */
	Map<String, Object> qeuryUser(QueryVO queryVO, int userId);

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	Serializable addUser(UserVO obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	void modifyUser(UserVO obj);

	/**
	 * 根据id删除对象
	 * 
	 * @param id
	 *            用户Id
	 */
	void deleteUser(int id);

	/**
	 * 根据用户Id重置用户密码
	 * 
	 * @param id
	 *            用户Id
	 * @param password
	 *            密码
	 */
	void resetPassword(int id, String password);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	TUser getUserByUserId(int id);

	/**
	 * 根据Id获取值模型对象
	 * 
	 * @param id
	 */
	UserVO getUserVOByUserId(int id);

	/**
	 * 为用户增加角色
	 * 
	 * @param roleId
	 *            角色Id
	 * @param userId
	 *            用户Id
	 */
	Serializable addUserRoleByUserId(int roleId, int userId);

	/**
	 * 根据用户Id获取用户角色
	 * 
	 * @param userId
	 *            用户Id
	 */
	List<Map<String, Object>> getUserRoleByUserId(int userId);

}
