package service.news.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.StaticsConstancts;
import dao.BaseDao;
import dao.model.TNewsType;
import model.common.QueryVO;
import model.news.NewsTypeVO;
import service.news.NewsTypeService;
import utils.bean.BeanCopyUtils;
import utils.common.SQLBudlider;

@Service("newsTypeService")
public class NewsTypeServiceImpl implements NewsTypeService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> queryNewsType(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append(SQLBudlider.bulider(TNewsType.class));
		dataHQL.append(" WHERE 1=1 ");
		totalHQL.append("SELECT COUNT(*) FROM TNewsType a  WHERE 1=1 ");
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
	public Serializable addNewsType(NewsTypeVO obj) {
		// TODO Auto-generated method stub
		TNewsType tobj = new TNewsType();
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setCreateTime(new Date());
		return baseDao.save(tobj);
	}

	@Override
	public void modifyNewsType(NewsTypeVO obj) {
		// TODO Auto-generated method stub
		TNewsType tobj = getNewsTypeById(obj.getId());
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setModifyTime(new Date());
		baseDao.update(tobj);
	}

	@Override
	public void deleteNewsType(int id) {
		String hql = "DELETE TNewsType WHERE id=:id";

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		baseDao.delete(hql, param);
	}

	@Override
	public TNewsType getNewsTypeById(int id) {
		return baseDao.get(TNewsType.class, id);
	}

	@Override
	public NewsTypeVO getNewsTypeVOById(int id) {
		// TODO Auto-generated method stub
		NewsTypeVO NewsTypeVO = new NewsTypeVO();
		BeanCopyUtils.copyProperties(getNewsTypeById(id), NewsTypeVO);
		return NewsTypeVO;
	}

	@Override
	public List<Map<String, Object>> getNewsTypes() {
		String hql = "SELECT new Map(a.id as id,a.name as name) FROM  TNewsType a order by a.sort";
		return baseDao.findAll(hql);
	}

}
