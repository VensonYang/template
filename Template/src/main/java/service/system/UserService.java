package service.system;

import java.util.List;
import java.util.Map;

import model.system.LoginVO;
import model.system.NodeVO;
import model.system.PrivilegesVectorVO;

public interface UserService {

	/**
	 * 根据用户账号和密码查找用户
	 * 
	 * @param loginVO
	 *            登录模型值对象
	 */
	Map<String, Object> getUserByCode(LoginVO loginVO);

	/**
	 * 根据用户账号查找用户
	 * 
	 * @param userAccount
	 *            用户账号
	 */
	Map<String, Object> getUserByAccount(String userAccount);

	/**
	 * 修改用户密码
	 * 
	 * @param id
	 *            用户Id
	 * @param pas
	 *            密码
	 */
	void modifyPassword(int id, String pas);

	/**
	 * 根据用户Id获取用户角色
	 * 
	 * @param id
	 *            用户Id
	 */
	List<Map<String, Object>> getUserRoleByUserId(int id);

	/**
	 * 根据用户Id获取用户角色
	 * 
	 * @param id
	 *            用户Id
	 */
	List<Map<String, Object>> getUserCourseByUserId(int id);

	/**
	 * 上传用户头像
	 * 
	 * @param path
	 *            图片路径
	 * @param id
	 *            用户Id
	 */
	void updateHeadImage(String path, int id);

	/**
	 * 根据用户Id获取用户权限
	 * 
	 * @param id
	 *            用户Id
	 */
	Map<Integer, PrivilegesVectorVO> getPrivilegesVectors(int userId);

	/**
	 * 根据用户Id获取用户菜单
	 * 
	 * @param userId
	 *            用户Id
	 */
	NodeVO getMenuByUserId(int userId);
}