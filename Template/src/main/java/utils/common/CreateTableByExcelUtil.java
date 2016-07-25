package utils.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateTableByExcelUtil {

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
	public static String getDataFromExcel(InputStream excelFile, String ext) {
		StringBuilder builder = new StringBuilder();
		// builder.append("BEGIN \n");
		Workbook workbook = null;
		try {
			if (ext.equals("xls")) {
				workbook = new HSSFWorkbook(excelFile);
			} else {
				workbook = new XSSFWorkbook(excelFile);
			}
			int sheetNum = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNum; i++) {
				Sheet sheet = workbook.getSheetAt(i); // sheet 从0开始
				builder.append(createTable(sheet.getSheetName(), getSheetData(sheet)));
				builder.append(";\n\n");
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
		// builder.append(" \nEND;");
		return builder.toString();
	}

	private static String createTable(String name, List<Map<String, String>> data) {
		StringBuilder SQL = new StringBuilder();
		SQL.append("CREATE TABLE ");
		SQL.append(name);
		SQL.append("(\n");
		// 去除第一条数据
		data.remove(0);
		int size = data.size();
		int i = 0;
		for (Map<String, String> map : data) {
			SQL.append("`");
			SQL.append(map.get("key_0"));
			SQL.append("` ");
			SQL.append(map.get("key_1"));
			String key_2 = map.get("key_2");
			String key_3 = map.get("key_3");

			String key_4 = map.get("key_4");
			String key_5 = map.get("key_5");
			String key_7 = map.get("key_7");
			if (null != key_2 && key_2.trim().length() > 0) {
				SQL.append("(");
				SQL.append(key_2);
				SQL.append(")");
			} else {
				SQL.append(" ");
				SQL.append(key_2);
			}
			if (null != key_3 && key_3.equals("是")) {
				SQL.append(" PRIMARY KEY ");
			}
			if (null != key_4 && key_4.equals("是")) {
				SQL.append(" AUTO_INCREMENT ");
			}
			if (null != key_5 && key_5.equals("是")) {
				SQL.append(" NOT NULL ");
			}
			if (null != key_7 && key_7.trim().length() > 0) {
				SQL.append(" COMMENT '");
				SQL.append(key_7);
				SQL.append("'");
			}

			if (size > 1 && i < size - 1) {
				SQL.append(",\n");
			}
			i++;
		}
		SQL.append(" \n)");
		return SQL.toString();
	}

	private static List<Map<String, String>> getSheetData(Sheet sheet) {
		List<Map<String, String>> varList = new ArrayList<Map<String, String>>();
		int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号
		for (int i = 0; i < rowNum; i++) { // 行循环开始
			Map<String, String> rowData = new HashMap<String, String>();
			Row row = sheet.getRow(i); // 行
			int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置
			for (int j = 0; j < cellNum; j++) { // 列循环开始
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
				rowData.put("key_" + j, cellValue);
			}
			varList.add(rowData);
		}
		return varList;
	}

	public static void main(String[] args) {
		try {
			String result = getDataFromExcel(new FileInputStream("f:/题库系统表结构.xlsx"), "xlsx");
			System.out.println(result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// public static void main(String[] args) {
	// String result = createTable("t_test", d);
	// System.out.println(result);
	// }
	//
	// public static final List<Map<String, String>> d = new
	// LinkedList<Map<String, String>>();
	// static {
	// Map<String, String> m = new HashMap<String, String>();
	// m.put("key_0", "id");
	// m.put("key_1", "int");
	// m.put("key_2", "10");
	// m.put("key_3", "是");
	// m.put("key_4", "是");
	// m.put("key_6", "id");
	// m.put("key_7", "ID");
	// Map<String, String> m1 = new HashMap<String, String>();
	// m1.put("key_0", "code");
	// m1.put("key_1", "varchar");
	// m1.put("key_2", "30");
	// m1.put("key_7", "代码");
	// Map<String, String> m2 = new HashMap<String, String>();
	// m2.put("key_0", "kemu");
	// m2.put("key_1", "varchar");
	// m2.put("key_2", "20");
	// m2.put("key_7", "科目");
	// d.add(m);
	// d.add(m1);
	// d.add(m2);
	// }
}
