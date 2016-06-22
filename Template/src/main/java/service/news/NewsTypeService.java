package service.news;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TNewsType;
import model.common.QueryVO;
import model.news.NewsTypeVO;

public interface NewsTypeService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryNewsType(QueryVO queryVO);

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	Serializable addNewsType(NewsTypeVO obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            值模型对象
	 */
	void modifyNewsType(NewsTypeVO obj);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	void deleteNewsType(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	TNewsType getNewsTypeById(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	NewsTypeVO getNewsTypeVOById(int id);

	List<Map<String, Object>> getNewsTypes();

}
