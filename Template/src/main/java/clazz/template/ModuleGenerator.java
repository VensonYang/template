package clazz.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger log = LoggerFactory.getLogger(ModuleGenerator.class);
	public static final String SPT = File.separator;

	public static final String ENCODING = "UTF-8";

	private Properties prop = new Properties();

	private String packName;
	private String fileName;
	private File serviceFile;
	private File serviceImplFile;
	private File controllerFile;
	private File pageFile;

	private File serviceTpl;
	private File serviceImplTpl;
	private File controllerTpl;
	private File pageTpl;

	public ModuleGenerator(String packName, String fileName) {
		this.packName = packName;
		this.fileName = fileName;
	}

	@SuppressWarnings("rawtypes")
	private void loadProperties() {
		try {
			log.debug("packName=" + packName);
			log.debug("fileName=" + fileName);
			FileInputStream fileInput = new FileInputStream(getFilePath(packName, fileName));
			prop.load(fileInput);
			String entityUp = prop.getProperty("Entity");
			log.debug("entityUp:" + entityUp);
			if (entityUp == null || entityUp.trim().equals("")) {
				log.warn("Entity not specified, exit!");
				return;
			}
			String entityLow = entityUp.substring(0, 1).toLowerCase() + entityUp.substring(1);
			log.debug("entityLow:" + entityLow);
			prop.put("entity", entityLow);
			if (log.isDebugEnabled()) {
				Set ps = prop.keySet();
				for (Object o : ps) {
					log.debug(o + "=" + prop.get(o));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepareFile() {
		String serviceFilePath = getFilePath(prop.getProperty("service_p"),
				prop.getProperty("Entity") + "Service.java");
		serviceFile = new File(serviceFilePath);
		log.debug("serviceFile:" + serviceFile.getAbsolutePath());

		String serviceImplFilePath = getFilePath(prop.getProperty("service_impl_p"),
				prop.getProperty("Entity") + "ServiceImpl.java");
		serviceImplFile = new File(serviceImplFilePath);
		log.debug("serviceImplFile:" + serviceImplFile.getAbsolutePath());
		String controllerFilePath = getFilePath(prop.getProperty("controller_p"),
				prop.getProperty("Entity") + "Controller.java");
		controllerFile = new File(controllerFilePath);
		log.debug("controllerFile:" + controllerFile.getAbsolutePath());
		String pageFilePath = prop.getProperty("page_dir") + "/" + prop.getProperty("entity") + ".html";
		pageFile = new File(pageFilePath);
		log.debug("pageFilePath:" + pageFile.getAbsolutePath());

	}

	private void prepareTemplate() {
		String tplPack = prop.getProperty("template_dir");
		log.debug("tplPack:" + tplPack);
		serviceImplTpl = new File(getFilePath(tplPack, "service_impl.txt"));
		serviceTpl = new File(getFilePath(tplPack, "service.txt"));
		controllerTpl = new File(getFilePath(tplPack, "controller.txt"));
		pageTpl = new File(getFilePath(tplPack, "page.txt"));
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
				stringToFile(pageFile, readTpl(pageTpl));
			}
		} catch (IOException e) {
			log.warn("write file faild! " + e.getMessage());
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
			log.warn("read file faild. " + e.getMessage());
		}
		return content;

	}

	private String getFilePath(String packageName, String name) {
		log.debug("replace:" + packageName);
		String path = packageName.replaceAll("\\.", "/");
		log.debug("after relpace:" + path);
		return "src/main/java/" + path + "/" + name;
	}

	public void generate() {
		loadProperties();
		prepareFile();
		prepareTemplate();
		writeFile();
	}

	public static void main(String[] args) {
		String packName = "clazz.template";
		String fileName = "template.properties";
		new ModuleGenerator(packName, fileName).generate();
	}
}
