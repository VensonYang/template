package service.news;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TNews;
import model.common.QueryVO;
import model.news.NewsVO;

public interface NewsService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryNews(QueryVO queryVO);

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	Serializable addNews(NewsVO obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	void modifyNews(NewsVO obj);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	void deleteNews(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	TNews getNewsById(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	Map<String, Object> getNewsVOById(int id);

	/**
	 * 批量导入新闻
	 * 
	 * @param data
	 *            数据源
	 */
	void saveBatchData(final List<Map<String, String>> data);
}
