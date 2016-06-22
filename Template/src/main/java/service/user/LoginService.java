package service.user;

import java.util.List;
import java.util.Map;

public interface LoginService {
	/**
	 * 根据用户账号和密码查找用户
	 * 
	 * @param userAccount
	 *            用户账号
	 * @param password
	 *            用户密码
	 */
	Map<String, Object> getUserByCode(String userAccount, String password);

	/**
	 * 根据用户Id获取用户权限
	 * 
	 * @param id
	 *            用户Id
	 */
	List<Map<String, Object>> getPriviledgesByUserId(int id);

	/**
	 * 根据用户Id获取用户权限矩阵
	 * 
	 * @param id
	 *            用户Id
	 */
	List<Map<String, Object>> getPriviledgesMatrixByUserId(int id);
}