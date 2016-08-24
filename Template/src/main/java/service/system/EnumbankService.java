package service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TEnumbank;
import model.common.QueryVO;
import model.system.EnumbankVO;

public interface EnumbankService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryEnumbank(QueryVO queryVO);

	/**
	 * 增加对象
	 * 
	 * @param enumbankVO
	 *            值模型对象
	 */
	Serializable addEnumbank(EnumbankVO enumbankVO);

	/**
	 * 修改对象
	 * 
	 * @param enumbankVO
	 *            值模型对象
	 */
	void modifyEnumbank(EnumbankVO enumbankVO);

	/**
	 * 根据Id删除对象
	 * 
	 * @param enumbankId
	 */
	void deleteEnumbank(Object enumbankId);

	/**
	 * 根据Id获取表对象
	 * 
	 * @param enumbankId
	 */
	TEnumbank getEnumbankById(Object enumbankId);

	/**
	 * 根据Id获取值模型对象
	 * 
	 * @param enumbankId
	 */
	EnumbankVO getEnumbankVOById(Object enumbankId);

	/** 根据枚举类型获取枚举值 */
	List<Map<String, Object>> getEnumByEnumTypeId(String typeId);

	/** 根据枚举类型获取枚举值 */
	List<Map<String, Object>> getSelectByTypeId(String typeId);

}
