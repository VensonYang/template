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
import dao.model.TEnumbank;
import model.common.QueryVO;
import model.system.EnumbankVO;
import service.system.EnumbankService;
import utils.bean.BeanCopyUtils;

@Service("enumbankService")
public class EnumbankServiceImpl implements EnumbankService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> queryEnumbank(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append("SELECT new map(id as id,sex as sex,"
				+ "(CASE state WHEN '1' THEN '启用' ELSE '停用' END) as state,DATE_FORMAT(createTime,'%Y-%m-%d') as createTime,"
				+ "  DATE_FORMAT(modifyTime,'%Y-%m-%d') as modifyTime,remark as remark) FROM TEnumbank a WHERE  1=1  ");
		totalHQL.append("SELECT COUNT(*) FROM TEnumbank a WHERE 1=1 ");
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
		if (StringUtils.isNotBlank(queryVO.getSort())) {
			dataHQL.append("Order By " + queryVO.getSort() + " " + queryVO.getOrder());
		}
	}

	@Override
	public Serializable addEnumbank(EnumbankVO enumbankVO) {
		TEnumbank enumbank = new TEnumbank();
		BeanCopyUtils.copyProperties(enumbankVO, enumbank);
		return baseDao.save(enumbank);

	}

	@Override
	public void modifyEnumbank(EnumbankVO enumbankVO) {
		TEnumbank enumbank = getEnumbankById(enumbankVO.getId());
		BeanCopyUtils.copyProperties(enumbankVO, enumbank);
		baseDao.update(enumbank);
	}

	@Override
	public void deleteEnumbank(Object enumbankId) {
		String hql = "DELETE TEnumbank WHERE id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", enumbankId);
		baseDao.delete(hql, params);
	}

	@Override
	public TEnumbank getEnumbankById(Object enumbankId) {
		String hql = "FROM TEnumbank WHERE id=?";
		return (TEnumbank) baseDao.get(hql, enumbankId);
	}

	@Override
	public EnumbankVO getEnumbankVOById(Object enumbankId) {
		EnumbankVO enumbankVO = new EnumbankVO();
		BeanCopyUtils.copyProperties(getEnumbankById(enumbankId), enumbankVO);
		return enumbankVO;
	}

	@Override
	public List<Map<String, Object>> getEnumByEnumTypeId(String typeId) {
		String hql = "SELECT new Map(a.id as id,a.enumValue as text) FROM TEnumbank a  WHERE a.typeId=:typeId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeId", typeId);
		return baseDao.findAll(hql, params);
	}

	@Override
	public List<Map<String, Object>> getSelectByTypeId(String typeId) {
		String hql = "SELECT new Map(a.enumValue as id,a.enumValue as text) FROM TEnumbank a  WHERE a.typeId=:typeId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeId", typeId);
		return baseDao.findAll(hql, params);
	}
}
