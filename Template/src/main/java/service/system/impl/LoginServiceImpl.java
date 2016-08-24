package service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BaseDao;
import service.system.LoginService;
import utils.common.MD5Util;

@Service("loginService")
@SuppressWarnings("unchecked")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getPrivilegesByUserId(int id) {
		// TODO Auto-generated method stub
		String hql = " SELECT DISTINCT a.id as id,a.privileges_name as name,a.url as url,a.state as state,a.pid as pid,"
				+ "a.icon as icon,a.target as target,a.remark as remark  FROM t_privileges a "
				+ " LEFT JOIN t_role_privileges b ON a.id=b.privileges_id LEFT JOIN t_user_role c ON b.role_id=c.role_id "
				+ " WHERE c.user_id=:userId ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		return baseDao.sqlFindAll(hql, params);
	}

	@Override
	public List<Map<String, Object>> getPrivilegesMatrixByUserId(int id) {

		String hql = " SELECT DISTINCT a.privileges_id AS privilegesID ,a.is_create AS iscreate ,a.is_delete "
				+ "AS isdelete,a.is_modify AS ismodify ,a.is_print AS isprint,a.is_import AS isimport,a.is_export AS isexport "
				+ " FROM t_privileges_matrix a LEFT JOIN t_user_role b ON a.role_id=b.role_id "
				+ " WHERE b.user_id=:userId";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		return baseDao.sqlFindAll(hql, params);
	}

	@Override
	public Map<String, Object> getUserByCode(String userAccount, String password) {
		StringBuilder hql = new StringBuilder("select new map(a.id as userId,a.userAccount as userAccount,"
				+ "a.userName as userName,a.sex as userSex,b.deptName as department,a.createTime as createTime,a.modifyTime as modifyTime,a.modifier "
				+ "as modifier,a.state as state,a.creator as creator,a.headImage as headImage,"
				+ "a.remark as remark) from TUser a left join a.TDepartment b where a.userAccount=? ");
		Object[] param = new Object[] { userAccount };
		if (password != null) {
			hql.append(" and a.password=?");
			password = MD5Util.getMD5String(password);
			param = new Object[] { userAccount, password };
		}
		return (Map<String, Object>) baseDao.get(hql.toString(), param);
	}

}
