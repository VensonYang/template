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

import bean.utils.BeanCopyUtils;
import common.StaticsConstancts;
import dao.BaseDao;
import dao.model.TPriviledges;
import model.common.QueryVO;
import model.system.PriviledgesVO;
import model.user.NodeVO;
import model.user.PriviledgesVectorVO;
import service.system.PriviledgesService;
import service.user.LoginService;

@Service("priviledgesService")
public class PriviledgesServiceImpl implements PriviledgesService {
	@Autowired
	private LoginService loginService;
	@Autowired
	private BaseDao baseDao;
	private Map<Integer, PriviledgesVectorVO> priviledgesVectors;

	private boolean initPriviledgesVectors(int userId) {
		List<Map<String, Object>> priviledgesResult = loginService.getPriviledgesByUserId(userId);
		if (priviledgesResult != null) {
			List<Map<String, Object>> priviledgeMatrixResult = loginService.getPriviledgesMatrixByUserId(userId);
			for (Map<String, Object> priviledge : priviledgesResult) {
				for (Map<String, Object> priviledgeMatrix : priviledgeMatrixResult) {
					if ((Integer) priviledge.get("id") == (Integer) priviledgeMatrix
							.get(StaticsConstancts.PRIVILEDGES_ID)) {
						PriviledgesVectorVO priviledgesVector = new PriviledgesVectorVO();
						priviledgesVector.setPriviledge(priviledge);
						Map<String, Boolean> priviledgeMatrixMap = new HashMap<String, Boolean>();
						for (Entry<String, Object> set : priviledgeMatrix.entrySet()) {
							String key = set.getKey();
							if (!key.equals("priviledgesID")) {
								priviledgeMatrixMap.put(key, (Boolean) set.getValue());
							}
						}
						priviledgesVector.setPriviledgesMatrix(priviledgeMatrixMap);
						priviledgesVectors.put((Integer) priviledge.get("id"), priviledgesVector);
					}
				}

			}
			return true;
		}
		return false;
	}

	@Override
	public Map<Integer, PriviledgesVectorVO> getPriviledgesVectors(int userId) {
		priviledgesVectors = new HashMap<Integer, PriviledgesVectorVO>();
		if (initPriviledgesVectors(userId)) {
			return priviledgesVectors;
		}
		return null;
	}

	@Override
	public List<TPriviledges> getPriviledgesByUserId(int userId) {
		String hql = "SELECT DISTINCT a FROM TPriviledges a,TRolePriviledges b,TUserRole c "
				+ "WHERE a.id=b.TPriviledges.id AND b.TRole.id=c.TRole.id AND c.TUser.id=:userId AND a.status=1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return baseDao.findAll(hql, TPriviledges.class, params);
	}

	private void iteratorPriviledge(NodeVO parentNode, Set<Integer> pids, List<TPriviledges> priviledges) {
		pids.remove(parentNode.getId()); // 删除当前父ID
		for (TPriviledges priviledge : priviledges) {
			NodeVO node = new NodeVO(priviledge.getId(), priviledge.getName(), priviledge.getUrl(), priviledge.getPid(),
					priviledge.getIcon());
			if (node.getPid() == parentNode.getId()) {
				parentNode.add(node); // 当前节点添加子节点
				if (pids.contains(node.getId())) { // 查询当前节点是否属于父节点
					iteratorPriviledge(node, pids, priviledges);
				}
			}
		}
	}

	@Override
	public NodeVO getMenuByUserId(int userId) {
		List<TPriviledges> priviledges = getPriviledgesByUserId(userId);
		Set<Integer> pids = new LinkedHashSet<Integer>();
		for (TPriviledges priviledge : priviledges) {
			pids.add(priviledge.getPid());
		}
		if (pids.size() > 0) {
			NodeVO menu = new NodeVO(0, "root", null, -1, null);
			iteratorPriviledge(menu, pids, priviledges);
			return menu;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> queryPriviledges(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append(
				" SELECT new Map(  a.id as id,a.name as name,a.url as url,(CASE a.status WHEN '1' THEN '启用' ELSE '禁用' END) as "
						+ "status,a.pid as pid,CONCAT('<i class=\"fa ',CONCAT(a.icon,'\"></i>')) as icon,a.isParent as isParent) "
						+ " FROM TPriviledges a  WHERE 1=1 ");
		totalHQL.append("SELECT COUNT(*) FROM TPriviledges a  WHERE 1=1 ");
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
	}

	@Override
	public Serializable addPriviledges(PriviledgesVO obj) {
		TPriviledges tobj = new TPriviledges();
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setCreateTime(new Date());
		return baseDao.save(tobj);
	}

	@Override
	public void modifyPriviledges(PriviledgesVO obj) {
		TPriviledges tobj = getPriviledgesById(obj.getId());
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setModifyTime(new Date());
		baseDao.update(tobj);
	}

	@Override
	public void deletePriviledges(int id) {
		String hql1 = "DELETE TPriviledgesMatrix WHERE a.TRole.id=:id";
		String hql2 = "DELETE TRolePriviledges a WHERE a.TRole.id=:id";
		String hql3 = "DELETE TPriviledges WHERE id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		baseDao.delete(hql1, params);
		baseDao.delete(hql2, params);
		baseDao.delete(hql3, params);
	}

	@Override
	public TPriviledges getPriviledgesById(int id) {
		return baseDao.get(TPriviledges.class, id);
	}

	@Override
	public PriviledgesVO getPriviledgesVOById(int id) {
		PriviledgesVO priviledgesVO = new PriviledgesVO();
		BeanCopyUtils.copyProperties(getPriviledgesById(id), priviledgesVO);
		return priviledgesVO;
	}

	@Override
	public List<TPriviledges> getPriviledgesByRoleId(int roleId) {
		String hql = "SELECT DISTINCT a FROM TPriviledges a LEFT JOIN a.TRolePriviledgeses b WHERE b.TRole.id=:roleId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		return baseDao.findAll(hql, TPriviledges.class, params);
	}

	@Override
	public NodeVO getMenu() {
		List<TPriviledges> priviledges = getPriviledges();
		Set<Integer> pids = new LinkedHashSet<Integer>();
		for (TPriviledges priviledge : priviledges) {
			pids.add(priviledge.getPid());
		}
		if (pids.size() > 0) {
			NodeVO menu = new NodeVO(0, "root", null, -1, null);
			iteratorPriviledge(menu, pids, priviledges);
			return menu;
		} else {
			return null;
		}
	}

	private List<TPriviledges> getPriviledges() {
		String hql = "FROM TPriviledges";
		return baseDao.findAll(hql, TPriviledges.class);
	};

}
