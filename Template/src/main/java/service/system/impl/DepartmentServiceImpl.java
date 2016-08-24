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
import model.common.QueryVO;
import model.system.DepartmentVO;
import service.system.DepartmentService;
import utils.bean.BeanCopyUtils;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> queryDepartment(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append("SELECT new map(id as id,name as name,deptNo as deptNo,"
				+ "(CASE state WHEN '1' THEN '启用' ELSE '停用' END) as state,DATE_FORMAT(createTime,'%Y-%m-%d') as createTime)"
				+ " FROM TDepartment a WHERE  1=1  ");
		totalHQL.append("SELECT COUNT(*) FROM TDepartment a WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		buildHQL(queryVO, dataHQL, totalHQL, params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(StaticsConstancts.DATA,
				baseDao.findByPage(dataHQL.toString(), params, queryVO.getOffset(), queryVO.getLimit()));
		result.put(StaticsConstancts.TOTAL, baseDao.get(totalHQL.toString(), params));
		return result;
	}

	private void buildHQL(QueryVO queryVO, StringBuilder dataHQL, StringBuilder totalHQL, Map<String, Object> params) {
		if (StringUtils.isNotBlank(queryVO.getState())) {
			dataHQL.append("AND a.state=:state  ");
			totalHQL.append("AND a.state=:state  ");
			params.put("state", queryVO.getState());
		}
		if (StringUtils.isNotBlank(queryVO.getName())) {
			String keyword = "%" + queryVO.getName() + "%";
			dataHQL.append("AND name LIKE :keyword ");
			totalHQL.append("AND name LIKE :keyword ");
			params.put("keyword", keyword);
		}
		if (StringUtils.isNotBlank(queryVO.getAccount())) {
			dataHQL.append("AND deptNo = :account ");
			totalHQL.append("AND deptNo = :account ");
			params.put("account", queryVO.getAccount());
		}
		if (StringUtils.isNotBlank(queryVO.getSort())) {
			dataHQL.append("Order By " + queryVO.getSort() + " " + queryVO.getOrder());
		}
	}

	@Override
	public Serializable addDepartment(DepartmentVO departmentVO) {
		TDepartment department = new TDepartment();
		BeanCopyUtils.copyProperties(departmentVO, department);
		return baseDao.save(department);

	}

	@Override
	public void modifyDepartment(DepartmentVO departmentVO) {
		TDepartment department = getDepartmentById(departmentVO.getId());
		BeanCopyUtils.copyProperties(departmentVO, department);
		baseDao.update(department);
	}

	@Override
	public void deleteDepartment(Object departmentId) {
		String hql = "DELETE TDepartment WHERE id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", departmentId);
		baseDao.delete(hql, params);
	}

	@Override
	public TDepartment getDepartmentById(Object departmentId) {
		String hql = "FROM TDepartment WHERE id=?";
		return (TDepartment) baseDao.get(hql, departmentId);
	}

	@Override
	public DepartmentVO getDepartmentVOById(Object departmentId) {
		DepartmentVO departmentVO = new DepartmentVO();
		BeanCopyUtils.copyProperties(getDepartmentById(departmentId), departmentVO);
		return departmentVO;
	}

	@Override
	public List<Map<String, Object>> getAllDepartment() {
		String hql = "SELECT new Map(a.id as id,a.name as text) FROM TDepartment a ORDER BY deptNo ASC";
		return baseDao.findAll(hql);
	}
}
