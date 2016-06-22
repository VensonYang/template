package service.universal;

import java.io.Serializable;
import java.util.Map;

import model.common.QueryVO;
import model.universal.BatchFileVO;
import model.universal.FileVO;

public interface NetdiskService {

	/**
	 * 分页获取所有对象
	 * 
	 * @param queryVO
	 *            查询条件
	 */
	Map<String, Object> queryFiles(QueryVO queryVO);

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            值模型对象
	 * @param tobj
	 *            数据库模型对象
	 */
	Serializable addFile(FileVO obj);

	/**
	 * 批量增加对象
	 */
	void addBatchFile(String rootPath, BatchFileVO fileVO, Integer userId);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	void deleteFile(int id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param id
	 */
	FileVO getFileByFileId(int id);

}
