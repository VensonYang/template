package service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TPriviledges;
import model.common.QueryVO;
import model.system.PriviledgesVO;
import model.user.NodeVO;
import model.user.PriviledgesVectorVO;

public interface PriviledgesService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryPriviledges(QueryVO queryVO);

	/**
	 * 根据用户Id获取用户权限矩阵集
	 * 
	 * @param userId
	 *            用户Id
	 */
	Map<Integer, PriviledgesVectorVO> getPriviledgesVectors(int userId);

	/**
	 * 根据用户Id获取用户权限
	 * 
	 * @param userId
	 *            用户Id
	 */
	List<TPriviledges> getPriviledgesByUserId(int userId);

	/**
	 * 根据角色Id获取角色权限
	 * 
	 * @param roleId
	 *            角色Id
	 */
	List<TPriviledges> getPriviledgesByRoleId(int roleId);

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
	Serializable addPriviledges(PriviledgesVO obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	void modifyPriviledges(PriviledgesVO obj);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	void deletePriviledges(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	TPriviledges getPriviledgesById(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	PriviledgesVO getPriviledgesVOById(int id);
}
