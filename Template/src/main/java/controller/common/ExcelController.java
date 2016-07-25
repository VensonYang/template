package controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import controller.base.ControllerContext;
import controller.base.ControllerHelper;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import utils.common.ExcelUtil;

@RequestMapping("/excel")
@Controller
@ResponseBody
@SuppressWarnings("rawtypes")
public class ExcelController {
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

	@RequestMapping("exportExcelByTemplate")
	public void exportExcelByTemplate(HttpServletRequest request) throws IOException {
		// 获取模板文件路径
		// File file = new File(builderPath("export_teacher.xls"));
		String template = request.getParameter("template");
		String json = request.getParameter("json");
		File file = new File(builderPath(template));
		if (file.exists()) {
			if (StringUtils.isNotBlank(json)) {
				// 获取数据
				List<Map> data = JSON.parseArray(json, Map.class);
				// 第一种:根据模板进行写入
				ExcelUtil.exportExcelByTemplate(data, new FileInputStream(file), template);
			} else {
				ControllerHelper.renderJSON("未找到可以导出的数据", null);
			}

		} else {
			ControllerHelper.renderJSON("导出excel 模板文件不存在", null);
		}

	}

	@RequestMapping("exportExcelByCreate")
	public void exportExcelByCreate(HttpServletRequest request) throws IOException {
		String j = request.getParameter("json");
		String c = request.getParameter("cloums");
		String fileName = request.getParameter("fileName");
		if (StringUtils.isNotBlank(j)) {
			// 获取数据
			List<Map> data = JSON.parseArray(j, Map.class);
			List<Map> cloums = JSON.parseArray(c, Map.class);
			// 第一种:根据模板进行写入
			ExcelUtil.exportExcelByCreate(cloums, data, fileName);
		} else {
			ControllerHelper.renderJSON("未找到可以导出的数据", null);
		}

	}

	private String builderPath(String fileName) {
		StringBuilder dirBuilder = new StringBuilder(ControllerHelper.getDownloadPath());
		dirBuilder.append(File.separator);
		dirBuilder.append("template");
		dirBuilder.append(File.separator);
		dirBuilder.append(fileName);
		return dirBuilder.toString();
	}

	@RequestMapping("importExcel")
	public ReturnResult importExcel() throws IOException {
		HttpServletRequest request = ControllerContext.getRequest();
		ReturnResult returnResult = ControllerContext.getResult();
		String templateName = request.getParameter("templateName");
		// 获取文件名
		String url = request.getParameter("url");
		StringBuffer path = new StringBuffer(ControllerHelper.getUploadPath(ControllerHelper.ATTACHMENT_ROOT_PATH));
		StringBuffer tmpPath = new StringBuffer(ControllerHelper.getDownloadPath());
		path.append(File.separator + url);
		String usrPath = path.toString();
		logger.debug(usrPath);
		File file = new File(usrPath);
		// 判断文件是否存在
		if (!file.exists()) {
			returnResult.setStatus(StatusCode.FAIL.setMessage("文件未找到，请从新上传"));
			return returnResult;
		}
		InputStream usrIn = new FileInputStream(file);
		tmpPath.append(File.separator);
		tmpPath.append("template");
		tmpPath.append(File.separator);
		tmpPath.append(templateName);
		File tmpFile = new File(tmpPath.toString());
		FileInputStream tmpIn = new FileInputStream(tmpFile);
		String ext = file.getName().endsWith("xls") ? "xls" : "xlsx";
		// 校验导入格式
		String msg = ExcelUtil.matchExcelData(usrIn, ext, tmpIn);
		if (msg != null) {
			returnResult.setStatus(StatusCode.FAIL.setMessage(msg));
			return returnResult;
		}
		tmpIn.close();
		usrIn = new FileInputStream(new File(usrPath));
		// 解析excel文件中的数据
		// 将数据存入数据库
		// newsService.saveBatchData(list);
		returnResult.setStatus(StatusCode.SUCCESS).setRows(ExcelUtil.getDataFromExcel(usrIn, ext));
		usrIn.close();
		return returnResult;
	}

}
