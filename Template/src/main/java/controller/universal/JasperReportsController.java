package controller.universal;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.base.ControllerHelper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXml4SwfExporter;

@SuppressWarnings("serial")
@Controller
@RequestMapping("jasper")
public class JasperReportsController {

	Logger logger = LoggerFactory.getLogger(JasperReportsController.class);

	public List<Student> getStudent() {
		ArrayList<Student> student = new ArrayList<Student>();
		for (int i = 0; i < 50; i++) {
			Student stu = new Student("name" + i, "男", i, "广东广州", "15919533" + i);
			student.add(stu);
		}
		return student;
	}

	@RequestMapping(value = "pdf")
	public void pdf() throws IOException, JRException {
		Map<String, Object> param = new HashMap<String, Object>();
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(getStudent(), false);
		byte[] content = JasperRunManager
				.runReportToPdf(new FileInputStream(ControllerHelper.getPrintPath() + "student.jasper"), param, data);
		ControllerHelper.makeAttachment(ControllerHelper.CONTENT_TYPE_PDF,
				new String[] { ControllerHelper.CONTENT_DISPOSITION_INLINE, "student.pdf" }, content);
	}

	@RequestMapping(value = "xls")
	public void xls() throws JRException, IOException {
		JRXlsExporter exporter = new JRXlsExporter();
		Map<String, Object> param = new HashMap<String, Object>();
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(getStudent(), false);
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		setParameterProcess(exporter, param, oStream, data);
		ControllerHelper.makeAttachment(ControllerHelper.CONTENT_TYPE_EXCEL_93,
				new String[] { ControllerHelper.CONTENT_DISPOSITION_ATTACHMENT, "student.xls" }, oStream.toByteArray());
	}

	@RequestMapping(value = "html")
	public void html() throws IOException, JRException {
		Map<String, Object> param = new HashMap<String, Object>();
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(getStudent(), false);
		String path = JasperRunManager.runReportToHtmlFile(ControllerHelper.getPrintPath() + "student.jasper", param,
				data);
		ControllerHelper.makeAttachment(ControllerHelper.CONTENT_TYPE_HTML,
				new String[] { ControllerHelper.CONTENT_DISPOSITION_INLINE, "student.html" }, path);
	}

	@RequestMapping(value = "xml")
	public void xml() throws JRException, IOException {
		JRExporter exporter = new JRXml4SwfExporter();
		Map<String, Object> param = new HashMap<String, Object>();
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		JRRewindableDataSource data = new JRBeanCollectionDataSource(getStudent(), false);
		setParameterProcess(exporter, param, oStream, data);
		ControllerHelper.makeAttachment(ControllerHelper.CONTENT_TYPE_XML,
				new String[] { ControllerHelper.CONTENT_DISPOSITION_INLINE, "student.xml" }, oStream.toByteArray());
	}

	private void setParameterProcess(JRExporter exporter, Map<String, Object> param, OutputStream oStream,
			JRRewindableDataSource data) throws JRException, FileNotFoundException {
		JasperPrint jasperPrint = JasperFillManager
				.fillReport(new FileInputStream(ControllerHelper.getPrintPath() + "student.jasper"), param, data);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.exportReport();
	}

	public class Student implements java.io.Serializable {

		private String name;
		private String sex;
		private Integer age;
		private String hometown;
		private String phone;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String getHometown() {
			return hometown;
		}

		public void setHometown(String hometown) {
			this.hometown = hometown;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Student(String name, String sex, Integer age, String hometown, String phone) {
			this.name = name;
			this.sex = sex;
			this.age = age;
			this.hometown = hometown;
			this.phone = phone;
		}

	}
}
