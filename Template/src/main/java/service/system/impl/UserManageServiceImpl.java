package service.system.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.StaticsConstancts;
import dao.BaseDao;
import dao.model.TDepartment;
import dao.model.TUser;
import model.common.QueryVO;
import model.system.UserVO;
import service.system.UserManageService;
import utils.bean.BeanCopyUtils;
import utils.common.MD5Util;

@Service("userManageService")
public class UserManageServiceImpl implements UserManageService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> qeuryUser(QueryVO queryVO, int userId) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append("SELECT new map(id as id,userName as userName,sex as sex,userAccount as userAccount,"
				+ "(CASE status WHEN '1' THEN '启用' ELSE '停用' END) as status,(select userName from TUser where id=a.creator) as creator,createTime as createTime,"
				+ " (select userName from TUser where id=a.modifier) as modifier, modifyTime as modifyTime,memo as memo) "
				+ "FROM TUser a WHERE  1=1  ");
		totalHQL.append("SELECT COUNT(*) FROM TUser a WHERE  1=1  ");
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("userId", userId);
		buildHQL(queryVO, dataHQL, totalHQL, params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(StaticsConstancts.DATA,
				baseDao.findByPage(dataHQL.toString(), params, queryVO.getOffset(), queryVO.getLimit()));
		result.put(StaticsConstancts.TOTAL, baseDao.get(totalHQL.toString(), params));
		return result;
	}

	private void buildHQL(QueryVO queryVO, StringBuilder dataHQL, StringBuilder totalHQL, Map<String, Object> params) {
		if (StringUtils.isNotBlank(queryVO.getAccount())) {
			dataHQL.append("AND a.userAccount=:userAccount  ");
			totalHQL.append("AND a.userAccount=:userAccount  ");
			params.put("userAccount", queryVO.getAccount());
		}
		if (StringUtils.isNotBlank(queryVO.getStatus())) {
			dataHQL.append("AND a.status=:status  ");
			totalHQL.append("AND a.status=:status  ");
			params.put("status", queryVO.getStatus());
		}
		if (StringUtils.isNotBlank(queryVO.getName())) {
			String keyword = "%" + queryVO.getName() + "%";
			dataHQL.append("AND userName LIKE :keyword OR userAccount LIKE :keyword ");
			totalHQL.append("AND userName LIKE :keyword OR userAccount LIKE :keyword ");
			params.put("keyword", keyword);
		}
		if (StringUtils.isNotBlank(queryVO.getSort())) {
			dataHQL.append("Order By " + queryVO.getSort() + " " + queryVO.getOrder());
		}
	}

	@Override
	public Serializable addUser(UserVO obj) {
		TUser tobj = new TUser();
		BeanCopyUtils.copyProperties(obj, tobj);
		// 设置密码
		String password = MD5Util.getMD5String(tobj.getPassword());
		tobj.setPassword(password);
		// 设置部门
		TDepartment department = new TDepartment();
		department.setId(obj.getDeptId());
		tobj.setTDepartment(department);
		// 保存用户
		baseDao.save(tobj);
		// 添加角色
		addUserRoleByUserId(obj.getRoleId(), tobj.getId());
		return tobj.getId();

	}

	@Override
	public void modifyUser(UserVO obj) {
		TUser tobj = getUserByUserId(obj.getId());
		String password;
		if (StringUtils.isBlank(obj.getPassword())) {
			password = tobj.getPassword();
		} else {
			password = MD5Util.getMD5String(obj.getPassword());
		}
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setPassword(password);
		// 查看是否有更改角色
		if (null != obj.getRoleId()) {
			addUserRoleByUserId(obj.getRoleId(), tobj.getId());
		}
		// 设置部门
		TDepartment department = new TDepartment();
		department.setId(obj.getDeptId());
		tobj.setTDepartment(department);
		baseDao.update(tobj);
	}

	@Override
	public void deleteUser(int id) {
		// 删除用户的角色和课程
		String hql1 = "DELETE TUserRole a WHERE a.TUser.id=:id";
		String hql2 = "DELETE TUserCourse a WHERE a.TUser.id=:id";
		String hql3 = "DELETE TUser WHERE id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		baseDao.delete(hql1, params);
		baseDao.delete(hql2, params);
		baseDao.delete(hql3, params);
	}

	@Override
	public TUser getUserByUserId(int id) {
		return baseDao.get(TUser.class, id);
	}

	@Override
	public Serializable addUserRoleByUserId(int roleId, int userId) {
		String DHQL = "DELETE  TUserRole a WHERE a.TUser.id=:userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		baseDao.delete(DHQL, params);
		String ISQL = "insert into t_user_role(roleid,userid) values(:roleId,:userId)";
		params.put("roleId", roleId);
		return baseDao.save(ISQL, params);
	}

	@Override
	public void resetPassword(int id, String password) {
		TUser user = getUserByUserId(id);
		user.setPassword(MD5Util.getMD5String(password));
		baseDao.update(user);
	}

	@Override
	public List<Map<String, Object>> getUserRoleByUserId(int userId) {
		String sql = "SELECT new Map(a.TRole.id as roleId) FROM TUserRole a WHERE a.TUser.id=:userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return baseDao.findAll(sql, params);
	}

	@Override
	public UserVO getUserVOByUserId(int id) {
		UserVO userVO = new UserVO();
		TUser user = getUserByUserId(id);
		BeanCopyUtils.copyProperties(user, userVO, "password");
		// 设置用户角色
		for (Map<String, Object> map : getUserRoleByUserId(id)) {
			userVO.setRoleId((Integer) map.get("roleId"));
		}
		if (null != user.getTDepartment()) {
			userVO.setDeptId(user.getTDepartment().getId());
		}
		return userVO;
	}

}
