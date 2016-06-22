package controller.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;

import common.StaticsConstancts;
import common.utils.NetworkUtil;
import controller.result.ReturnResult;
import controller.result.StatusCode;

/**
 * controller助手，该类包含了controller类常用的一些常量以及方法
 * 
 * @author Venson Yang
 */
public class ControllerHelper {
	// 文件类型
	public static final String CONTENT_TYPE_HTML = "text/html";
	public static final String CONTENT_TYPE_IMAGE_JPG = "image/jpeg";
	public static final String CONTENT_TYPE_IMAGE_PNG = "image/png";
	public final static String CONTENT_TYPE_STREAM = "application/octet-stream";
	public final static String CONTENT_TYPE_JSON = "application/json";
	public final static String CONTENT_TYPE_PDF = "application/pdf";
	public final static String CONTENT_TYPE_XML = "text/xml";
	public final static String CONTENT_TYPE_EXCEL_93 = "application/vnd.ms-excel";
	public final static String CONTENT_TYPE_EXCEL = "application/excel";
	public final static String CONTENT_TYPE_EXCEL_97 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	// 附件下载格式
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String CONTENT_DISPOSITION_INLINE = "inline";
	public static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment";
	// 上传文件路径
	public static final String ATTACHMENT_UPLOAD_PATH = "/upload/attachment";
	public static final String ATTACHMENT_ROOT_PATH = "/";
	public static final String EXCEL_UPLOAD_PATH = "/upload/excel";
	public static final String HTML_UPLOAD_PATH = "/upload/html";
	public static final String IMAGE_UPLOAD_PATH = "/upload/image";
	public static final String HEAD_IMAGE_UPLOAD_PATH = "/upload/image/headImage";

	/**
	 * 在国际化资源文件中获取指定键值的value值
	 * 
	 * @param key
	 *            键值
	 * @return 键对应的值
	 */
	public static String getMessage(String key) {
		Locale locale = ControllerContext.getRequest().getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("message", locale);
		String msg = resourceBundle.getString(key);
		if (msg != null)
			return msg;
		return null;
	}

	public static String getUploadPath() {
		return getUploadPath(null);
	}

	/**
	 * 获取上传文件的根路径
	 * 
	 * @param path
	 *            相对路径
	 * @return 上传文件根路径
	 */
	public static String getUploadPath(String path) {
		HttpServletRequest request = ControllerContext.getRequest();
		if (path == null) {
			return request.getServletContext().getRealPath("/upload") + File.separator;
		}
		return request.getServletContext().getRealPath(path) + File.separator;

	}

	public static String getDownloadPath() {
		HttpServletRequest request = ControllerContext.getRequest();
		return request.getServletContext().getRealPath("/download") + File.separator;
	}

	/**
	 * 获取打印文件的根路径
	 * 
	 * @return 打印文件根路径
	 */
	public static String getPrintPath() {
		HttpServletRequest request = ControllerContext.getRequest();
		return request.getServletContext().getRealPath("/prints") + File.separator;
	}

	/**
	 * 输入下载文件
	 * 
	 * @param contentType
	 *            文件类型
	 * @param header
	 *            文件打开形式以及文件名
	 * @param data
	 *            文件数据
	 * @return 打印文件根路径
	 */
	public static void makeAttachment(String contentType, String header[], byte[] data) throws IOException {
		HttpServletResponse response = ControllerContext.getResponse();
		response.setContentType(contentType);
		response.setHeader(CONTENT_DISPOSITION,
				header[0] + ";fileName=" + new String(header[1].getBytes("UTF-8"), "ISO-8859-1"));
		ServletOutputStream stream = response.getOutputStream();
		stream.write(data);
		stream.flush();
		stream.close();
	}

	public static void makeAttachment(String contentType, String[] header, InputStream data) throws IOException {
		makeAttachment(contentType, header, IOUtils.toByteArray(data));
	}

	public static void makeAttachment(String contentType, String[] header, String filePath) throws IOException {
		makeAttachment(contentType, header, IOUtils.toByteArray(new FileInputStream(filePath)));
	}

	/**
	 * 渲染JSON数据
	 * 
	 * @param contentType
	 *            文件类型
	 * @param error
	 *            信息
	 * @param logger
	 *            日志
	 */
	public static boolean renderJSON(String contentType, String error, Logger logger) throws IOException {
		contentType = StringUtils.defaultString(contentType, CONTENT_TYPE_JSON);
		ReturnResult returnResult = ControllerContext.getResult();
		HttpServletResponse response = ControllerContext.getResponse();
		returnResult.setStatus(StatusCode.NO_ACCESS.setMessage(error));
		response.setContentType(contentType + "; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.toJSON(returnResult));
		out.close();
		if (logger != null)
			logger.info("IP[" + NetworkUtil.getIpAddress(ControllerContext.getRequest()) + "]" + error);

		return false;
	}

	public static boolean renderJSON(String error, Logger logger) throws IOException {
		return renderJSON(null, error, logger);
	}

	public static void renderJSON(String contentType) throws IOException {
		contentType = StringUtils.defaultString((String) contentType, CONTENT_TYPE_JSON);
		ReturnResult returnResult = ControllerContext.getResult();
		HttpServletResponse response = ControllerContext.getResponse();
		response.setContentType(contentType + "; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.toJSON(returnResult));
		out.close();
	}

	public static void renderJSON(String contentType, Object obj) throws IOException {
		contentType = StringUtils.defaultString((String) contentType, CONTENT_TYPE_JSON);
		HttpServletResponse response = ControllerContext.getResponse();
		response.setContentType(contentType + "; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.toJSON(obj));
		out.close();
	}

	/**
	 * 生成文件名
	 * 
	 * @param ext
	 *            文件扩展名
	 */
	public static String makeFileName(String ext) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(java.util.UUID.randomUUID().toString());
		buffer.append("-");
		buffer.append(System.currentTimeMillis());
		if (ext != null) {
			buffer.append(ext);
		}
		return buffer.toString();
	}

	public static String bulidFileName() {
		return makeFileName(null);
	}

	public static boolean checkError(Object obj, ValidationAware va, ReturnResult returnResult) {
		return checkError(obj, va, returnResult, null);
	}

	public static boolean checkError(Object obj, ValidationAware va, ReturnResult returnResult, Logger logger) {
		if (obj == null) {
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("无法获得请求参数，请检查参数格式"));
			return true;
		}
		if (va.hasFieldError()) {
			String errorMsg = va.getFieldError().values().iterator().next();
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage(errorMsg));
			if (logger != null) {
				logger.error(errorMsg);
			}
			return true;
		}

		return false;
	}

	public static String checkParam(ValidType validType, String para) {
		HttpServletRequest request = ControllerContext.getRequest();
		String param = null;
		if (para != null)
			param = request.getParameter(para);
		else
			param = request.getParameter(validType.getParam());
		if (validType.getVaildType().equals("num")) {
			if (StringUtils.isNumeric(param)) {
				return param;
			}

		} else if (validType.getVaildType().equals("notBlank")) {
			if (StringUtils.isNotBlank(param)) {
				return param;
			}

		}
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数错误，禁止访问！"));
		return null;
	}

	public static String checkParam(ValidType validType) {
		return checkParam(validType, null);
	}

	public static Integer getUserId() {
		HttpSession session = ControllerContext.getSession();
		Object userId = session.getAttribute(StaticsConstancts.USER_ID);
		if (userId != null) {
			return (Integer) userId;
		}
		return null;
	}

	public static String getUserAccount() {
		HttpSession session = ControllerContext.getSession();
		Object userAccount = session.getAttribute(StaticsConstancts.USER_ACCOUNT);
		if (userAccount != null) {
			return (String) userAccount;
		}
		return null;
	}

	public static String getUserName() {
		HttpSession session = ControllerContext.getSession();
		Object userId = session.getAttribute(StaticsConstancts.USER_NAME);
		if (userId != null) {
			return userId.toString();
		}
		return null;
	}

}
