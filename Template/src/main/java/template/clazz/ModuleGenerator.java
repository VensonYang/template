package template.clazz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 * 模块生成器
 * 
 * <p>
 * 用于生成JEE模块。
 * <p>
 * 包括JAVA类：controller,service,service.impl；
 * 配置文件：ftl页面：list.html,add.html,edit.html。
 * <p>
 * 可设置的参数有：模块实体类名、java类包地址、配置文件地址、ftl页面地址。
 */
public class ModuleGenerator {
	public static final String SPT = File.separator;

	public static final String ENCODING = "UTF-8";

	private Properties prop = new Properties();

	private String packName;
	private String fileName;
	private File serviceFile;
	private File serviceImplFile;
	private File controllerFile;
	private File pageListFile;
	private File pageJSFile;
	private File pageAddFile;
	private File pageEditFile;

	private File serviceTpl;
	private File serviceImplTpl;
	private File controllerTpl;
	private File pageListTpl;
	private File pageAddTpl;
	private File pageEditTpl;
	private File pageJSTpl;

	public ModuleGenerator(String packName, String fileName) {
		this.packName = packName;
		this.fileName = fileName;
	}

	private void loadProperties() {
		try {
			System.out.println("packName=" + packName);
			System.out.println("fileName=" + fileName);
			FileInputStream fileInput = new FileInputStream(getFilePath(packName, fileName));
			prop.load(fileInput);
			String entityUp = prop.getProperty("Entity");
			System.out.println("entityUp:" + entityUp);
			if (entityUp == null || entityUp.trim().equals("")) {
				System.out.println("Entity not specified, exit!");
				return;
			}
			String entityLow = entityUp.substring(0, 1).toLowerCase() + entityUp.substring(1);
			System.out.println("entityLow:" + entityLow);
			prop.put("entity", entityLow);
			// if (log.isDebugEnabled()) {
			// Set ps = prop.keySet();
			// for (Object o : ps) {
			// System.out.println(o + "=" + prop.get(o));
			// }
			// }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepareFile() {
		String serviceFilePath = "src/main/java/service" + SPT + prop.getProperty("package") + SPT
				+ prop.getProperty("Entity") + "Service.java";
		serviceFile = new File(serviceFilePath);
		System.out.println("serviceFile:" + serviceFile.getAbsolutePath());

		String serviceImplFilePath = "src/main/java/service" + SPT + prop.getProperty("package") + SPT + "impl" + SPT
				+ prop.getProperty("Entity") + "ServiceImpl.java";
		serviceImplFile = new File(serviceImplFilePath);
		System.out.println("serviceImplFile:" + serviceImplFile.getAbsolutePath());

		String controllerFilePath = "src/main/java/controller" + SPT + prop.getProperty("package") + SPT
				+ prop.getProperty("Entity") + "Controller.java";
		controllerFile = new File(controllerFilePath);
		System.out.println("controllerFile:" + controllerFile.getAbsolutePath());

		String pageListFilePath = prop.getProperty("page_dir") + SPT + prop.getProperty("package") + SPT
				+ prop.getProperty("entity") + ".html";
		pageListFile = new File(pageListFilePath);
		System.out.println("pageListFile:" + pageListFile.getAbsolutePath());

		String pageAddFilePath = prop.getProperty("page_dir") + SPT + prop.getProperty("package") + SPT + "add-"
				+ prop.getProperty("entity") + ".html";
		pageAddFile = new File(pageAddFilePath);
		System.out.println("pageAddFile:" + pageAddFile.getAbsolutePath());

		String pageEditFilePath = prop.getProperty("page_dir") + SPT + prop.getProperty("package") + SPT + "edit-"
				+ prop.getProperty("entity") + ".html";
		pageEditFile = new File(pageEditFilePath);
		System.out.println("pageEditFile:" + pageEditFile.getAbsolutePath());

		String pageJSFilePath = prop.getProperty("page_dir") + SPT + "js" + SPT + "page" + SPT
				+ prop.getProperty("package") + SPT + prop.getProperty("entity") + ".js";
		pageJSFile = new File(pageJSFilePath);
		System.out.println("pageJSFile:" + pageJSFile.getAbsolutePath());

	}

	private void prepareTemplate() {
		String tplPack = prop.getProperty("template_dir");
		System.out.println("tplPack:" + tplPack);
		serviceImplTpl = new File(getFilePath(tplPack, "service_impl.txt"));
		serviceTpl = new File(getFilePath(tplPack, "service.txt"));
		controllerTpl = new File(getFilePath(tplPack, "controller.txt"));
		pageListTpl = new File(getFilePath(tplPack, "page_list.txt"));
		pageAddTpl = new File(getFilePath(tplPack, "page_add.txt"));
		pageEditTpl = new File(getFilePath(tplPack, "page_edit.txt"));
		pageJSTpl = new File(getFilePath(tplPack, "page_js.txt"));
	}

	private static void stringToFile(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, ENCODING);
	}

	private void writeFile() {
		try {
			if (Boolean.parseBoolean(prop.getProperty("is_service"))) {
				stringToFile(serviceImplFile, readTpl(serviceImplTpl));
				stringToFile(serviceFile, readTpl(serviceTpl));
			}
			if (Boolean.parseBoolean(prop.getProperty("is_controller"))) {
				stringToFile(controllerFile, readTpl(controllerTpl));
			}
			if (Boolean.parseBoolean(prop.getProperty("is_page"))) {
				stringToFile(pageListFile, readTpl(pageListTpl));
				stringToFile(pageAddFile, readTpl(pageAddTpl));
				stringToFile(pageEditFile, readTpl(pageEditTpl));
				stringToFile(pageJSFile, readTpl(pageJSTpl));
			}
		} catch (IOException e) {
			System.out.println("write file faild! " + e.getMessage());
		}
	}

	private String readTpl(File tpl) {
		String content = null;
		try {
			content = FileUtils.readFileToString(tpl, ENCODING);
			Set<Object> ps = prop.keySet();
			for (Object o : ps) {
				String key = (String) o;
				String value = prop.getProperty(key);
				content = content.replaceAll("\\#\\{" + key + "\\}", value);
			}
		} catch (IOException e) {
			System.out.println("read file faild. " + e.getMessage());
		}
		return content;

	}

	private String getFilePath(String templateDir, String name) {
		return "src/main/java/" + templateDir + "/" + name;
	}

	public void generate() {
		loadProperties();
		prepareFile();
		prepareTemplate();
		writeFile();
	}

	public static void main(String[] args) {
		String packName = "clazz/template";
		String fileName = "template.properties";
		new ModuleGenerator(packName, fileName).generate();
	}
}
