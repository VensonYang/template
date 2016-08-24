package service.system.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.StaticsConstancts;
import dao.BaseDao;
import dao.model.TPrivileges;
import dao.model.TRole;
import model.common.QueryVO;
import model.system.PrivilegesVO;
import model.system.RoleVO;
import service.system.PrivilegesService;
import service.system.RoleService;
import utils.bean.BeanCopyUtils;
import utils.common.SQLBudlider;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private BaseDao baseDao;
	@Autowired
	private PrivilegesService privilegesService;

	@Override
	public Map<String, Object> queryRole(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append(SQLBudlider.bulider(TRole.class));
		dataHQL.append(" WHERE 1=1  ");
		totalHQL.append("SELECT COUNT(*) FROM TRole a  WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		buildHQL(queryVO, dataHQL, totalHQL, params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(StaticsConstancts.DATA,
				baseDao.findByPage(dataHQL.toString(), params, queryVO.getOffset(), queryVO.getLimit()));
		result.put(StaticsConstancts.TOTAL, baseDao.get(totalHQL.toString(), params));
		return result;
	}

	private void buildHQL(QueryVO queryVO, StringBuilder dataHQL, StringBuilder totalHQL, Map<String, Object> params) {
		if (StringUtils.isNotBlank(queryVO.getName())) {
			String keyword = "%" + queryVO.getName() + "%";
			dataHQL.append("AND a.roleName LIKE :keyword  ");
			totalHQL.append("AND a.roleName LIKE :keyword  ");
			params.put("keyword", keyword);
		}
		if (StringUtils.isNotBlank(queryVO.getState())) {
			dataHQL.append("AND a.state=:state  ");
			totalHQL.append("AND a.state=:state  ");
			params.put("state", queryVO.getState());
		}
		if (StringUtils.isNotBlank(queryVO.getSort())) {
			dataHQL.append("Order By " + queryVO.getSort() + " " + queryVO.getOrder());
		}
	}

	@Override
	public Serializable addRole(RoleVO obj) {
		// TODO Auto-generated method stub
		TRole tobj = new TRole();
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setCreateTime(new Date());
		return baseDao.save(tobj);
	}

	@Override
	public void modifyRole(RoleVO obj) {
		// TODO Auto-generated method stub
		TRole tobj = getRoleById(obj.getId());
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setModifyTime(new Date());
		baseDao.update(tobj);
	}

	@Override
	public void deleteRole(int id) {
		String hql1 = "DELETE TUserRole a WHERE a.TRole.id=:id";
		String hql2 = "DELETE TRolePrivileges a WHERE a.TRole.id=:id";
		String hql3 = "DELETE TPrivilegesMatrix a WHERE a.TRole.id=:id";
		String hql4 = "DELETE TRole WHERE id=:id";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		baseDao.delete(hql1, param);
		baseDao.delete(hql2, param);
		baseDao.delete(hql3, param);
		baseDao.delete(hql4, param);
	}

	@Override
	public TRole getRoleById(int id) {
		return baseDao.get(TRole.class, id);
	}

	@Override
	public RoleVO getRoleVOById(int id) {
		// TODO Auto-generated method stub
		RoleVO roleVO = new RoleVO();
		BeanCopyUtils.copyProperties(getRoleById(id), roleVO);
		return roleVO;
	}

	@Override
	public List<Map<String, Object>> getRoles() {
		String hql = "SELECT new Map(id as id,roleName as text) FROM TRole";
		return baseDao.findAll(hql);
	}

	@Override
	public Serializable addRolePrivileges(RoleVO obj) {
		String delHQL1 = "DELETE FROM TRolePrivileges a where a.TRole.id=:roleId";
		String delHQL2 = "DELETE FROM TPrivilegesMatrix a where a.TRole.id=:roleId";
		String saveSQL1 = "insert into t_role_privileges(privilegesID,roleID) values(:id,:roleId)";
		String saveSQL2 = "insert into t_privileges_matrix(roleID,privilegesID,iscreate,isdelete,ismodify,isselect,isprint,isimport,isexport,remark) values(:roleId,:id,1,1,1,1,1,1,1,'auto')";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", obj.getId());
		baseDao.delete(delHQL1, params);
		baseDao.delete(delHQL2, params);
		int[] ids = obj.getPrivilegesIds();
		if (ids != null) {
			for (int id : ids) {
				params.put("id", id);
				baseDao.save(saveSQL1, params);
				if (!privilegesService.getPrivilegesVOById(id).getUrl().trim().equals("#")) {
					baseDao.save(saveSQL2, params);
				}

			}
		}
		return obj.getId();
	}

	@Override
	public List<PrivilegesVO> getPrivilegesVOByRoleId(int id) {
		List<PrivilegesVO> PrivilegesVO = new LinkedList<PrivilegesVO>();
		for (TPrivileges Privileges : privilegesService.getPrivilegesByRoleId(id)) {
			PrivilegesVO vo = new PrivilegesVO();
			BeanCopyUtils.copyProperties(Privileges, vo);
			PrivilegesVO.add(vo);
		}
		return PrivilegesVO;
	}
}
