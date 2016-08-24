package service.system.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.StaticsConstancts;
import dao.BaseDao;
import dao.model.TPrivileges;
import model.common.QueryVO;
import model.system.NodeVO;
import model.system.PrivilegesVO;
import model.system.PrivilegesVectorVO;
import service.system.LoginService;
import service.system.PrivilegesService;
import utils.bean.BeanCopyUtils;

@Service("privilegesService")
public class PrivilegesServiceImpl implements PrivilegesService {
	@Autowired
	private LoginService loginService;
	@Autowired
	private BaseDao baseDao;
	private Map<Integer, PrivilegesVectorVO> privilegesVectors;

	private boolean initPrivilegesVectors(int userId) {
		List<Map<String, Object>> privilegesResult = loginService.getPrivilegesByUserId(userId);
		if (privilegesResult != null) {
			List<Map<String, Object>> privilegeMatrixResult = loginService.getPrivilegesMatrixByUserId(userId);
			for (Map<String, Object> privilege : privilegesResult) {
				for (Map<String, Object> priviledgeMatrix : privilegeMatrixResult) {
					if ((Integer) privilege.get("id") == (Integer) priviledgeMatrix
							.get(StaticsConstancts.PRIVILEGES_ID)) {
						PrivilegesVectorVO privilegesVector = new PrivilegesVectorVO();
						privilegesVector.setPrivilege(privilege);
						Map<String, Boolean> privilegeMatrixMap = new HashMap<String, Boolean>();
						for (Entry<String, Object> set : priviledgeMatrix.entrySet()) {
							String key = set.getKey();
							if (!key.equals(StaticsConstancts.PRIVILEGES_ID)) {
								privilegeMatrixMap.put(key, (Boolean) set.getValue());
							}
						}
						privilegesVector.setPrivilegeMatrix(privilegeMatrixMap);
						privilegesVectors.put((Integer) privilege.get("id"), privilegesVector);
					}
				}

			}
			return true;
		}
		return false;
	}

	@Override
	public Map<Integer, PrivilegesVectorVO> getPrivilegesVectors(int userId) {
		privilegesVectors = new HashMap<Integer, PrivilegesVectorVO>();
		if (initPrivilegesVectors(userId)) {
			return privilegesVectors;
		}
		return null;
	}

	@Override
	public List<TPrivileges> getPrivilegesByUserId(int userId) {
		String hql = "SELECT DISTINCT a FROM TPrivileges a,TRolePrivileges b,TUserRole c "
				+ "WHERE a.id=b.TPrivileges.id AND b.TRole.id=c.TRole.id AND c.TUser.id=:userId AND a.state=1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return baseDao.findAll(hql, TPrivileges.class, params);
	}

	private void iteratorPrivilege(NodeVO parentNode, Set<Integer> pids, List<TPrivileges> privileges) {
		pids.remove(parentNode.getId()); // 删除当前父ID
		for (TPrivileges privilege : privileges) {
			NodeVO node = new NodeVO(privilege.getId(), privilege.getPrivilegesName(), privilege.getUrl(),
					privilege.getPid(), privilege.getIcon());
			node.setRemark(privilege.getRemark());
			node.setTarget(privilege.getTarget());
			if (node.getPid() == parentNode.getId()) {
				parentNode.add(node); // 当前节点添加子节点
				if (pids.contains(node.getId())) { // 查询当前节点是否属于父节点
					iteratorPrivilege(node, pids, privileges);
				}
			}
		}
	}

	@Override
	public NodeVO getMenuByUserId(int userId) {
		List<TPrivileges> privileges = getPrivilegesByUserId(userId);
		Set<Integer> pids = new LinkedHashSet<Integer>();
		for (TPrivileges priviledge : privileges) {
			pids.add(priviledge.getPid());
		}
		if (pids.size() > 0) {
			NodeVO menu = new NodeVO(0, "root", null, -1, null);
			iteratorPrivilege(menu, pids, privileges);
			return menu;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> queryPrivileges(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append(
				" SELECT new Map(  a.id as id,a.privilegesName as name,a.url as url,(CASE a.state WHEN '1' THEN '启用' ELSE '禁用' END) as "
						+ "state,a.pid as pid,a.icon as icon,a.target as target) " + " FROM TPrivileges a  WHERE 1=1 ");
		totalHQL.append("SELECT COUNT(*) FROM TPrivileges a  WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		buildHQL(queryVO, dataHQL, totalHQL, params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(StaticsConstancts.DATA,
				baseDao.findByPage(dataHQL.toString(), params, queryVO.getOffset(), queryVO.getLimit()));
		result.put(StaticsConstancts.TOTAL, baseDao.get(totalHQL.toString(), params));
		return result;
	}

	private void buildHQL(QueryVO queryVO, StringBuilder dataHQL, StringBuilder totalHQL, Map<String, Object> params) {
		if (queryVO != null) {
			if (StringUtils.isNotBlank(queryVO.getName())) {
				String keyword = "%" + queryVO.getName() + "%";
				dataHQL.append("AND a.privileges_name LIKE :keyword  ");
				totalHQL.append("AND a.privileges_name LIKE :keyword  ");
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
	}

	@Override
	public Serializable addPrivileges(PrivilegesVO obj) {
		TPrivileges tobj = new TPrivileges();
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setCreateTime(new Date());
		return baseDao.save(tobj);
	}

	@Override
	public void modifyPrivileges(PrivilegesVO obj) {
		TPrivileges tobj = getPrivilegesById(obj.getId());
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setModifyTime(new Date());
		baseDao.update(tobj);
	}

	@Override
	public void deletePrivileges(int id) {
		String hql1 = "DELETE TPrivilegesMatrix a WHERE a.TPrivileges.id=:id";
		String hql2 = "DELETE TRolePrivileges a WHERE a.TPrivileges.id=:id";
		String hql3 = "DELETE TPrivileges WHERE id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		baseDao.delete(hql1, params);
		baseDao.delete(hql2, params);
		baseDao.delete(hql3, params);
	}

	@Override
	public TPrivileges getPrivilegesById(int id) {
		return baseDao.get(TPrivileges.class, id);
	}

	@Override
	public PrivilegesVO getPrivilegesVOById(int id) {
		PrivilegesVO privilegesVO = new PrivilegesVO();
		BeanCopyUtils.copyProperties(getPrivilegesById(id), privilegesVO);
		return privilegesVO;
	}

	@Override
	public List<TPrivileges> getPrivilegesByRoleId(int roleId) {
		String hql = "SELECT DISTINCT a FROM TPrivileges a LEFT JOIN a.TRolePrivilegeses b WHERE b.TRole.id=:roleId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		return baseDao.findAll(hql, TPrivileges.class, params);
	}

	@Override
	public NodeVO getMenu() {
		List<TPrivileges> privileges = getPrivileges();
		Set<Integer> pids = new LinkedHashSet<Integer>();
		for (TPrivileges privilege : privileges) {
			pids.add(privilege.getPid());
		}
		if (pids.size() > 0) {
			NodeVO menu = new NodeVO(0, "root", null, -1, null);
			iteratorPrivilege(menu, pids, privileges);
			return menu;
		} else {
			return null;
		}
	}

	private List<TPrivileges> getPrivileges() {
		String hql = "FROM TPrivileges";
		return baseDao.findAll(hql, TPrivileges.class);
	};

}
