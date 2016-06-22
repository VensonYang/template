package common.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;

import controller.base.ControllerContext;
import controller.base.ControllerHelper;

/**
 * 利用开源组件POI导出,导入，校验EXCEL文档！
 * 
 * @author YWX
 */
@SuppressWarnings("rawtypes")
public class ExcelUtil {

	/**
	 * 根据模板导出excel
	 *
	 * @param data
	 *            数据源
	 * @param file
	 *            模板文件
	 * @param keys
	 *            单元格的键值
	 * @throws IOException
	 */
	public static void exportExcelByTemplate(List<Map> data, FileInputStream file, String fileName) {

		HSSFWorkbook workbook = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(file);
			workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum() + 1;
			for (Map map : data) {
				// 创建行
				HSSFRow row = sheet.createRow(rowNum);
				int i = 0;
				for (Object value : map.values()) {
					// 创建单元格
					HSSFCell cell = row.createCell(i);
					// 判断值是否为空
					if (value != null) {
						// 判断值的类型后进行强制类型转换
						if (value instanceof Boolean) {
							cell.setCellValue((Boolean) value);
						} else if (value instanceof Date) {
							cell.setCellValue((Date) value);
						} else if (value instanceof Double) {
							cell.setCellValue((Double) value);
						} else if (value instanceof Calendar) {
							cell.setCellValue((Calendar) value);
						} else {
							cell.setCellValue(value.toString());
						}
					} else {
						cell.setCellValue("");
					}
					i++;
				}
				rowNum++;
			}
			// 创建输出流
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);
			HttpServletResponse response = ControllerContext.getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName);
			ServletOutputStream stream = response.getOutputStream();
			stream.write(os.toByteArray());
			stream.flush();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建excel并写入数据
	 * 
	 * @param workbook
	 *            要写入的工作簿
	 * @param headStyle
	 *            头部标题样式
	 * @param contentStyle
	 *            内容样式
	 * @param headers
	 *            表格属性列名数组
	 * @param data
	 *            数据源
	 * @param keys
	 *            单元格的键值
	 */
	public static void exportExcelByCreate(List<Map> cloums, List<Map> data, String fileName) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		// 产生表格标题行
		HSSFRow header = sheet.createRow(0);
		HSSFCellStyle headStyle = getBasicStyle(workbook);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 16); // 字体高度
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font.setFontName("宋体"); // 字体
		headStyle.setFont(font);
		List<String> fields = new ArrayList<String>();
		List<String> headers = new ArrayList<String>();
		for (Map head : cloums) {
			if (!head.get("field").equals("state")) {
				headers.add(head.get("title").toString());
				fields.add(head.get("field").toString());
			}
		}
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = header.createCell(i);
			cell.setCellStyle(headStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
			cell.setCellValue(text);
		}
		HSSFCellStyle cloumnStyle = getBasicStyle(workbook);
		for (int i = 0; i < data.size(); i++) {
			Map map = data.get(i);
			// 创建行
			HSSFRow row = sheet.createRow(i + 1);
			// int i = 0;
			for (int j = 0; j < fields.size(); j++) {
				Object value = map.get(fields.get(j));
				// 创建单元格
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(cloumnStyle);
				// 判断值是否为空
				if (value != null) {
					// 判断值的类型后进行强制类型转换
					if (value instanceof Boolean) {
						cell.setCellValue((Boolean) value);
					} else if (value instanceof Date) {
						cell.setCellValue((Date) value);
					} else if (value instanceof Double) {
						cell.setCellValue((Double) value);
					} else if (value instanceof Calendar) {
						cell.setCellValue((Calendar) value);
					} else {
						cell.setCellValue(value.toString());
					}
				} else {
					cell.setCellValue("");
				}
			}
		}
		// 自动适应列宽
		for (int i = 0; i < headers.size(); i++) {
			sheet.autoSizeColumn(i);
		}
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);
			ControllerHelper.makeAttachment(ControllerHelper.CONTENT_TYPE_EXCEL_93,
					new String[] { "attachment", fileName }, os.toByteArray());
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// 设置基本样式
	public static HSSFCellStyle getBasicStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14); // 字体号数
		font.setFontName("宋体"); // 字体
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		// font.setItalic(true); // 是否使用斜体
		// font.setStrikeout(true); //是否使用划线

		// 设置单元格类型
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
		style.setWrapText(true);
		return style;
	}

	/**
	 * 从excel中获取数据
	 * 
	 * @param excelFile
	 *            输入文件流
	 * @param fileName
	 *            文件名称，包含后缀名
	 * @param startRow
	 *            开始行号
	 * @param startCol
	 *            开始列号
	 * @param sheetNum
	 *            哪一页
	 * @throws IOException
	 */
	public static List<Map<String, String>> getDataFromExcel(InputStream excelFile, String ext, int startRow,
			int startCol, int sheetNum) {
		List<Map<String, String>> varList = new ArrayList<Map<String, String>>();
		Workbook workbook = null;
		try {
			if (ext.equals("xls")) {

				workbook = new HSSFWorkbook(excelFile);

			} else {
				workbook = new XSSFWorkbook(excelFile);
			}
			Sheet sheet = workbook.getSheetAt(sheetNum); // sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号

			for (int i = startRow; i < rowNum; i++) { // 行循环开始
				Map<String, String> rowData = new HashMap<String, String>();
				Row row = sheet.getRow(i); // 行
				Row firstRow = sheet.getRow(0);
				int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置
				for (int j = startCol; j < cellNum; j++) { // 列循环开始
					Cell cell = row.getCell(j);
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						case Cell.CELL_TYPE_NUMERIC:
							cellValue = String.valueOf((int) cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							cellValue = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_FORMULA:
							cellValue = cell.getNumericCellValue() + "";
							break;
						case Cell.CELL_TYPE_BLANK:
							cellValue = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_ERROR:
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}

					rowData.put(firstRow.getCell(j).toString(), cellValue);

				}
				varList.add(rowData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return varList;
	}

	/**
	 * 从excel中获取数据
	 * 
	 * @param excelFile
	 *            输入文件流
	 * @param fileName
	 *            文件名称，包含后缀名
	 * @throws IOException
	 */
	public static List<Map<String, String>> getDataFromExcel(InputStream excelFile, String ext) throws IOException {
		return ExcelUtil.getDataFromExcel(excelFile, ext, 1, 0, 0);
	}

	/**
	 * 校验导入excel文件的数据
	 * 
	 * @param userFile
	 *            导入文件
	 * @param templateFile
	 *            模板文件
	 * @param startRow
	 *            起始行
	 * @param startCol
	 *            起始列
	 * @param sheetNum
	 *            哪一页
	 * @param isNotNull
	 *            是否允许为空
	 * 
	 */
	public static String matchExcelData(InputStream userFile, String usrExt, InputStream templateFile, int startRow,
			int startCol, int sheetNum, boolean isNotNull) {
		String result = null;
		Workbook usrbook = null;
		Workbook tmpbook = null;
		try {
			// 获取文件，并创建工作簿
			if (usrExt.equalsIgnoreCase("xls")) {
				usrbook = new HSSFWorkbook(userFile);
			} else {
				usrbook = new XSSFWorkbook(userFile);
			}
			tmpbook = new HSSFWorkbook(templateFile);
			// 获取指定页
			Sheet usrSheet = usrbook.getSheetAt(sheetNum);
			Sheet tmpSheet = tmpbook.getSheetAt(sheetNum);
			if (usrSheet.getLastRowNum() < 1 || tmpSheet.getLastRowNum() < 1) {
				return "导入文件中并未找到可导入的数据";
			}

			// 获取指定行
			Row usrRow = usrSheet.getRow(startRow);
			Row tmpRow = tmpSheet.getRow(startRow);
			if (usrRow == null || tmpRow == null) {
				return "导入文件的指定行列值为空";
			}

			// 匹配指定列值
			for (int i = startCol; i < tmpRow.getLastCellNum(); i++) {
				Cell usrCell = usrRow.getCell(i);
				Cell tmpCell = tmpRow.getCell(i);
				if (usrCell == null || tmpCell == null) {
					return "导入文件的指定行列值为空";
				}
				String usrValue = usrCell.getStringCellValue();
				String tmpValue = tmpCell.getStringCellValue();
				if (!usrValue.equals(tmpValue)) {
					return "导入文件和模板文件的指定行列值不匹配,模板指定行值为:" + tmpValue + ",导入文件指定行值为:" + usrValue;
				}
			}
			// 是否允许单元格为空
			if (isNotNull) {
				int usrRowNum = usrSheet.getLastRowNum();
				for (int i = 0; i < usrRowNum; i++) {
					usrRow = usrSheet.getRow(i);
					int cellNum = 0;
					for (int j = 0; j < usrRow.getLastCellNum(); j++) {
						Cell usrCell = usrRow.getCell(cellNum);
						if (usrCell == null) {
							return "导入文件的单元格值为空！";
						} else if (StringUtils.isBlank(usrCell.getStringCellValue())) {
							return "导入文件的单元格值为空！";
						}
						cellNum++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (usrbook != null && tmpbook != null) {
				try {
					usrbook.close();
					tmpbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return result;
	}

	/**
	 * 校验导入excel文件的数据
	 * 
	 * @param userFile
	 *            导入文件
	 * @param templateFile
	 *            模板文件
	 * @param isNotNull
	 *            是否允许为空
	 * 
	 */
	public static String matchExcelData(InputStream userFile, String usrExt, InputStream templateFile,
			boolean isNotNull) throws IOException {
		return matchExcelData(userFile, usrExt, templateFile, 0, 0, 0, isNotNull);
	};

	/**
	 * 校验导入excel文件的数据
	 * 
	 * @param userFile
	 *            导入文件
	 * @param templateFile
	 *            模板文件
	 * 
	 */
	public static String matchExcelData(InputStream userFile, String usrExt, InputStream templateFile)
			throws IOException {
		return matchExcelData(userFile, usrExt, templateFile, false);
	};
}