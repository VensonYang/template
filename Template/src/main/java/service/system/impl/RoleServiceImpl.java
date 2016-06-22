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

import bean.utils.BeanCopyUtils;
import common.StaticsConstancts;
import common.utils.SQLBudlider;
import dao.BaseDao;
import dao.model.TPriviledges;
import dao.model.TRole;
import model.common.QueryVO;
import model.system.PriviledgesVO;
import model.system.RoleVO;
import service.system.PriviledgesService;
import service.system.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private BaseDao baseDao;
	@Autowired
	private PriviledgesService priviledgesService;

	@Override
	public Map<String, Object> queryRole(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append(SQLBudlider.bulider(TRole.class));
		dataHQL.append(" WHERE a.id<>1  ");
		totalHQL.append("SELECT COUNT(*) FROM TRole a  WHERE a.id<>1 ");
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
			dataHQL.append("AND a.name LIKE :keyword  ");
			totalHQL.append("AND a.name LIKE :keyword  ");
			params.put("keyword", keyword);
		}
		if (StringUtils.isNotBlank(queryVO.getStatus())) {
			dataHQL.append("AND a.status=:status  ");
			totalHQL.append("AND a.status=:status  ");
			params.put("status", queryVO.getStatus());
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
		String hql2 = "DELETE TRolePriviledges a WHERE a.TRole.id=:id";
		String hql3 = "DELETE TPriviledgesMatrix a WHERE a.TRole.id=:id";
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
		String hql = "SELECT new Map(id as id,name as text) FROM TRole";
		return baseDao.findAll(hql);
	}

	@Override
	public Serializable addRolePriviledges(RoleVO obj) {
		String delHQL1 = "DELETE FROM TRolePriviledges a where a.TRole.id=:roleId";
		String delHQL2 = "DELETE FROM TPriviledgesMatrix a where a.TRole.id=:roleId";
		String saveSQL1 = "insert into t_role_priviledges(priviledgesID,roleID) values(:id,:roleId)";
		String saveSQL2 = "insert into t_priviledges_matrix(roleID,priviledgesID,iscreate,isdelete,ismodify,isselect,isprint,isimport,isexport,memo) values(:roleId,:id,1,1,1,1,1,1,1,'auto')";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", obj.getId());
		baseDao.delete(delHQL1, params);
		baseDao.delete(delHQL2, params);
		int[] ids = obj.getPriviledgesIds();
		if (ids != null) {
			for (int id : ids) {
				params.put("id", id);
				baseDao.save(saveSQL1, params);
				if (!priviledgesService.getPriviledgesVOById(id).getUrl().trim().equals("#")) {
					baseDao.save(saveSQL2, params);
				}

			}
		}
		return obj.getId();
	}

	@Override
	public List<PriviledgesVO> getPriviledgesVOByRoleId(int id) {
		List<PriviledgesVO> PriviledgesVO = new LinkedList<PriviledgesVO>();
		for (TPriviledges Priviledges : priviledgesService.getPriviledgesByRoleId(id)) {
			PriviledgesVO vo = new PriviledgesVO();
			BeanCopyUtils.copyProperties(Priviledges, vo);
			PriviledgesVO.add(vo);
		}
		return PriviledgesVO;
	}
}
