package service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BaseDao;
import model.system.LoginVO;
import model.system.NodeVO;
import model.system.PriviledgesVectorVO;
import service.system.LoginService;
import service.system.PriviledgesService;
import service.system.UserService;
import utils.common.MD5Util;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private LoginService loginService;
	@Autowired
	private PriviledgesService priviledgesService;
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getUserByCode(LoginVO loginVO) {
		// TODO Auto-generated method stub
		return loginService.getUserByCode(loginVO.getUserAccount(), loginVO.getPassword());
	}

	@Override
	public void modifyPassword(int id, String pas) {
		// TODO Auto-generated method stub
		String hql = "UPDATE TUser a SET a.password=:password WHERE a.id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", MD5Util.getMD5String(pas));
		params.put("id", id);
		baseDao.update(hql, params);
	}

	@Override
	public List<Map<String, Object>> getUserRoleByUserId(int id) {
		String hql = "SELECT new map(b.id as id,b.name as name) FROM TUserRole a LEFT JOIN a.TRole b WHERE a.TUser.id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return baseDao.findAll(hql, params);
	}

	@Override
	public Map<String, Object> getUserByAccount(String userAccount) {
		return loginService.getUserByCode(userAccount, null);
	}

	@Override
	public void updateHeadImage(String path, int id) {
		// TODO Auto-generated method stub
		String hql = "UPDATE TUser a SET a.headImage=:headImage WHERE a.id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("headImage", path);
		params.put("id", id);
		baseDao.update(hql, params);
	}

	@Override
	public Map<Integer, PriviledgesVectorVO> getPriviledgesVectors(int userId) {
		// TODO Auto-generated method stub
		return priviledgesService.getPriviledgesVectors(userId);
	}

	@Override
	public NodeVO getMenuByUserId(int userId) {
		// TODO Auto-generated method stub
		return priviledgesService.getMenuByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getUserCourseByUserId(int id) {
		String hql = "SELECT new map(b.id as id,b.name as name) FROM TUserCourse a LEFT JOIN a.TCourse b WHERE a.TUser.id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return baseDao.findAll(hql, params);
	}

}
