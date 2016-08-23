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
		String hql = " SELECT DISTINCT a.id as id,a.name as name,a.url as url,a.status as status,a.pid as pid,a.icon as icon,a.isParent as isParent "
				+ " FROM t_privileges a "
				+ " LEFT JOIN t_role_privileges b ON a.id=b.privilegesID LEFT JOIN t_user_role c ON b.roleID=c.roleId "
				+ " WHERE c.userId=:userId ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		return baseDao.sqlFindAll(hql, params);
	}

	@Override
	public List<Map<String, Object>> getPrivilegesMatrixByUserId(int id) {

		String hql = " SELECT DISTINCT a.privilegesID AS privilegesID ,a.iscreate AS iscreate ,a.isdelete AS isdelete,a.ismodify AS ismodify ,a.isprint AS isprint,a.isimport AS isimport,a.isexport AS isexport "
				+ " FROM t_privileges_matrix a LEFT JOIN t_user_role b ON a.roleID=b.roleID "
				+ " WHERE b.userID=:userId";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		return baseDao.sqlFindAll(hql, params);
	}

	@Override
	public Map<String, Object> getUserByCode(String userAccount, String password) {
		StringBuilder hql = new StringBuilder("select new map(a.id as userId,a.userAccount as userAccount,"
				+ "a.userName as userName,a.sex as userSex,b.name as department,a.createTime as createTime,a.modifyTime as modifyTime,a.modifier "
				+ "as modifier,a.status as status,a.creator as creator,a.headImage as headImage,"
				+ "a.memo as memo) from TUser a left join a.TDepartment b where a.userAccount=? ");
		Object[] param = new Object[] { userAccount };
		if (password != null) {
			hql.append(" and a.password=?");
			password = MD5Util.getMD5String(password);
			param = new Object[] { userAccount, password };
		}
		return (Map<String, Object>) baseDao.get(hql.toString(), param);
	}

}
