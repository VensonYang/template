package service.universal.impl;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.utils.BeanCopyUtils;
import common.StaticsConstancts;
import common.utils.DateFormaterUtil;
import common.utils.DateUtil;
import dao.BaseDao;
import dao.model.TFile;
import model.common.QueryVO;
import model.universal.BatchFileVO;
import model.universal.FileVO;
import service.universal.NetdiskService;

/**
 * 网盘服务类 <br>
 * 类说明:该类实现了网盘接口的所有业务逻辑
 */
@Service("netdiskService")
public class NetdiskServiceImpl implements NetdiskService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> queryFiles(QueryVO queryVO) {
		StringBuilder dataHQL = new StringBuilder();
		StringBuilder totalHQL = new StringBuilder();
		dataHQL.append("SELECT new map(a.id as id, a.fileDesc as fileDesc,a.fileName as fileName,"
				+ "a.fileSize as fileSize, b.userName as uploadName,a.createTime as createTime) "
				+ "FROM TFile a,TUser b WHERE a.createor=b.id ");
		totalHQL.append("SELECT COUNT(*) FROM TFile a  WHERE 1=1");
		Map<String, Object> params = new HashMap<String, Object>();
		buildHQL(queryVO, dataHQL, totalHQL, params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(StaticsConstancts.DATA,
				baseDao.findByPage(dataHQL.toString(), params, queryVO.getOffset(), queryVO.getLimit()));
		result.put(StaticsConstancts.TOTAL, baseDao.get(totalHQL.toString(), params));
		return result;
	}

	private void buildHQL(QueryVO queryVO, StringBuilder dataHQL, StringBuilder totalHQL, Map<String, Object> params) {
		if (StringUtils.isNumeric(queryVO.getId())) {
			dataHQL.append("AND a.id = :fileId ");
			totalHQL.append("AND a.id = :fileId ");
			params.put("fileId", Integer.parseInt(queryVO.getId()));
		}
		if (StringUtils.isNotBlank(queryVO.getName())) {
			dataHQL.append("AND a.fileName LIKE :fileName ");
			totalHQL.append("AND a.fileName LIKE :fileName ");
			params.put("fileName", "%" + queryVO.getName() + "%");
		}
		if (queryVO.getStaTime() != null && queryVO.getEndTime() != null) {
			dataHQL.append("AND (a.createTime BETWEEN :staTime AND :endTime)");
			totalHQL.append("AND (a.createTime BETWEEN :staTime AND :endTime)");
			params.put("staTime", DateUtil.fomatDate(queryVO.getStaTime()));
			params.put("endTime", DateFormaterUtil.formatEndTime(queryVO.getEndTime()));
		}
	}

	@Override
	public Serializable addFile(FileVO obj) {
		TFile tobj = new TFile();
		BeanCopyUtils.copyProperties(obj, tobj);
		return baseDao.save(tobj);
	}

	@Override
	public void deleteFile(int id) {
		FileVO file = getFileByFileId(id);
		baseDao.delete(TFile.class, id);
		new File(file.getFilePath()).delete();
	}

	@Override
	public FileVO getFileByFileId(int id) {
		// TODO Auto-generated method stub
		TFile file = baseDao.get(TFile.class, id);
		FileVO fileVO = new FileVO();
		BeanCopyUtils.copyProperties(file, fileVO);
		return fileVO;
	}

	@Override
	public void addBatchFile(BatchFileVO fileVO, Integer userId) {
		for (int i = 0; i < fileVO.getFileNames().length; i++) {
			FileVO saveFileVO = new FileVO();
			String fileName = fileVO.getFileNames()[i];
			String builderName = fileVO.getBuilderNames()[i];
			String url = fileVO.getUrl()[i];
			int fileSize = fileVO.getFileSizes()[i];
			String fileType = fileVO.getFileTypes()[i];
			saveFileVO.setFileName(fileName);
			saveFileVO.setFileType(fileType);
			saveFileVO.setBuilderName(builderName);
			saveFileVO.setFileSize(fileSize);
			saveFileVO.setFilePath(url);
			saveFileVO.setCreateor(userId);
			saveFileVO.setCreateTime(new Date());
			addFile(saveFileVO);
		}
	}

}
