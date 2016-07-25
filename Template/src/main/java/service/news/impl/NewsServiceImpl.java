package service.news.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.StaticsConstancts;
import dao.BaseDao;
import dao.model.TNews;
import dao.model.TNewsType;
import model.common.QueryVO;
import model.news.NewsVO;
import service.news.NewsService;
import utils.bean.BeanCopyUtils;
import utils.common.DBUtil;
import utils.common.SQLBudlider;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> queryNews(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		Set<String> filterField = new HashSet<String>();
		filterField.add("content");
		dataHQL.append(SQLBudlider.bulider(TNews.class, filterField));
		dataHQL.append(" WHERE 1=1 ");
		totalHQL.append("SELECT COUNT(*) FROM TNews a  WHERE 1=1 ");
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
			dataHQL.append("AND a.title LIKE :keyword  ");
			totalHQL.append("AND a.title LIKE :keyword  ");
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
	public Serializable addNews(NewsVO obj) {
		// TODO Auto-generated method stub
		TNews tobj = new TNews();
		BeanCopyUtils.copyProperties(obj, tobj);
		TNewsType newsType = new TNewsType();
		newsType.setId(obj.getNewsTypeId());
		tobj.setTNewsType(newsType);
		tobj.setCreateTime(new Date());
		return baseDao.save(tobj);
	}

	@Override
	public void modifyNews(NewsVO obj) {
		// TODO Auto-generated method stub
		TNews tobj = getNewsById(obj.getId());
		BeanCopyUtils.copyProperties(obj, tobj);
		tobj.setModifyTime(new Date());
		baseDao.update(tobj);
	}

	@Override
	public void deleteNews(int id) {
		String hql = "DELETE TNews WHERE id=:id";

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		baseDao.delete(hql, param);
	}

	@Override
	public TNews getNewsById(int id) {
		return baseDao.get(TNews.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getNewsVOById(int id) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) baseDao.get(
				"SELECT new Map(a.title as title,a.summary as summary, a.publisher as publisher,a.content as content,a.source as source,DATE_FORMAT(a.createTime,'%Y-%m-%d %H:%i:%s') as createTime,b.name as newsTypeName) FROM TNews a LEFT JOIN a.TNewsType b WHERE a.id=?",
				id);
	}

	@Override
	public void saveBatchData(final List<Map<String, String>> data) {
		try {
			DBUtil.beginTransaction();
			Connection conn = DBUtil.getConnection();
			String sql = "insert into template.t_news(title,publisher,newtypeID,content,source,status,createTime) values(?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Map<String, String> map : data) {
				pstmt.setString(1, (String) map.get("title"));
				pstmt.setString(2, (String) map.get("publisher"));
				pstmt.setInt(3, 2);
				pstmt.setString(4, (String) map.get("title"));
				pstmt.setString(5, (String) map.get("source"));
				pstmt.setBoolean(6, true);
				pstmt.setTimestamp(7, Timestamp.valueOf((String) map.get("createTime")));
				pstmt.execute();
				DBUtil.commit();
			}
		} catch (Exception e) {
			DBUtil.rollback();
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}

	}

}
