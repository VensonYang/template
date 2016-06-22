package common.parse;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Range;
import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFPanel;
import org.freehep.graphicsio.emf.EMFRenderer;
import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;
import org.w3c.dom.Document;

import net.arnx.wmf2svg.gdi.svg.SvgGdi;
import net.arnx.wmf2svg.gdi.wmf.WmfParser;

public class WordDocumentUtils {

	public static String[] DEFAULT_CATEGORIES;
	public static String[] DEFAULT_NUMBERS;

	static {
		DEFAULT_CATEGORIES = new String[] { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		DEFAULT_NUMBERS = new String[200];
		for (int i = 0; i < 200; i++) {
			DEFAULT_NUMBERS[i] = i + "";
		}
	}

	public static String convertToHTML(String inPath, String infileName, final String outPath, final String outfileName)
			throws Exception {
		STUnderline.Enum.forInt(1);
		WordToHtmlConverter converter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		HWPFDocument doc = new HWPFDocument(new FileInputStream(inPath + infileName));
		Range range = doc.getRange();
		// TODO此处将文档中的半角空格转为全角空格（为了保存文档中的空格和样式下划线）
		range.replaceText(" ", "　");
		converter.setPicturesManager(new PicturesManager() {

			public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
					float heightInches) {
				return savePic(content, pictureType, outPath, outfileName, widthInches * 100, heightInches * 100);
			}

		});
		System.out.print("word文档转化为html开始...");
		converter.processDocument(doc);

		Document htmlDocument = converter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		FileOutputStream fos = new FileOutputStream(outPath + outfileName);
		fos.write(out.toByteArray());
		fos.close();
		System.out.print("word文档转化为html完成...");
		return outfileName;
	}

	/**
	 * 保存并整理word文档中的图片
	 * 
	 * @param content
	 *            图片
	 * @param pictureType
	 *            图片类型
	 * @param outPath
	 *            输出路径
	 * @param outfileName
	 *            输出文件夹名
	 */
	public static String savePic(byte[] content, PictureType pictureType, String outPath, String outfileName,
			float width, float height) {
		String outFloder = outfileName.substring(0, outfileName.lastIndexOf("."));
		File outFile = new File(outPath + outFloder);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		try {
			String fileName = null;
			if (pictureType.getExtension() == PictureType.WMF.getExtension()) {
				fileName = outPath + outFloder + File.separator + java.util.UUID.randomUUID().toString() + ".png";
				wmf2Png(content, fileName, width, height);
			} else if (pictureType.getExtension() == PictureType.EMF.getExtension()) {
				fileName = outPath + outFloder + File.separator + java.util.UUID.randomUUID().toString() + ".png";
				emf2Png(content, fileName, width, height);
			} else {
				fileName = outPath + outFloder + File.separator + java.util.UUID.randomUUID().toString() + "."
						+ pictureType.getExtension();
				scaleImg(content, width, height, fileName, pictureType.getExtension());
			}
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void scaleImg(byte[] in, float width, float height, String path, String ext) throws IOException {
		InputStream buffin = new ByteArrayInputStream(in);
		BufferedImage bImage = ImageIO.read(buffin);
		Image img = bImage.getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
		BufferedImage bi = new BufferedImage((int) width, (int) height, BufferedImage.SCALE_SMOOTH);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		bi.flush();
		ImageIO.write(bi, ext, new File(path));
	}

	/**
	 * emf转png
	 * 
	 * @param content
	 *            文件
	 * @param fileName
	 *            输入文件名
	 */
	private static void emf2Png(byte[] content, String fileName, float width, float height) throws Exception {

		EMFInputStream sin = new EMFInputStream(new ByteArrayInputStream(content));
		// read the EMF file
		EMFRenderer emfRenderer = new EMFRenderer(sin);

		EMFPanel emfPanel = new EMFPanel();
		emfPanel.setRenderer(emfRenderer);

		// create SVG properties
		Properties p = new Properties();
		p.put(SVGGraphics2D.EMBED_FONTS, Boolean.toString(false));
		p.put(SVGGraphics2D.CLIP, Boolean.toString(true));
		p.put(SVGGraphics2D.COMPRESS, Boolean.toString(false));
		p.put(SVGGraphics2D.TEXT_AS_SHAPES, Boolean.toString(false));
		p.put(SVGGraphics2D.STYLABLE, Boolean.toString(false));
		ByteArrayOutputStream fOut = new ByteArrayOutputStream();
		// prepare Graphics2D
		SVGGraphics2D graphics2D = new SVGGraphics2D(fOut, emfPanel);
		graphics2D.setProperties(p);
		graphics2D.setDeviceIndependent(true);

		graphics2D.startExport();
		emfPanel.print(graphics2D);
		graphics2D.endExport();

		PNGTranscoder it = new PNGTranscoder();
		ByteArrayOutputStream png = new ByteArrayOutputStream();
		TranscoderOutput transcoderOutput = new TranscoderOutput(png);
		TranscoderInput transcoderInput = new TranscoderInput(new ByteArrayInputStream(fOut.toByteArray()));
		it.transcode(transcoderInput, transcoderOutput);
		// 设置大小和质量
		it.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1.0f));
		it.addTranscodingHint(ImageTranscoder.KEY_WIDTH, width);
		it.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, height);
		FileOutputStream pngfos = new FileOutputStream(fileName);
		pngfos.write(png.toByteArray());
		pngfos.close();
		transcoderInput.getInputStream().close();
		png.close();
		sin.close();
	}

	/**
	 * wmf转png
	 * 
	 * @param content
	 *            文件
	 * @param fileName
	 *            输入文件名
	 */
	private static void wmf2Png(byte[] content, String fileName, float width, float height) throws Exception {
		WmfParser parser = new WmfParser();
		SvgGdi gdi = new SvgGdi(false);
		parser.parse(new ByteArrayInputStream(content), gdi);

		Document doc = gdi.getDocument();
		scaleSVG(doc, (int) width, (int) height);
		ByteArrayOutputStream sout = new ByteArrayOutputStream();
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 1.0//EN");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "链接地址-20010904/DTD/svg10.dtd");
		transformer.transform(new DOMSource(doc), new StreamResult(sout));
		sout.flush();

		ByteArrayOutputStream pout = new ByteArrayOutputStream();
		PNGTranscoder it = new PNGTranscoder();
		// 设置大小和质量
		// it.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(0.9f));
		// it.addTranscodingHint(ImageTranscoder.KEY_WIDTH, width);
		// it.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, height);

		it.transcode(new TranscoderInput(new ByteArrayInputStream(sout.toByteArray())), new TranscoderOutput(pout));

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(pout.toByteArray());
		fos.close();
		pout.close();
		sout.close();
	}

	/**
	 * 重新设置图片大小
	 *
	 * @param doc
	 *            文档
	 */
	private static void scaleSVG(Document doc, int width, int height) {

		doc.getElementsByTagName("svg").item(0).getAttributes().getNamedItem("width").setNodeValue((int) width + "");
		doc.getElementsByTagName("svg").item(0).getAttributes().getNamedItem("height").setNodeValue((int) height + "");

	}

	public static boolean isStart(String text, String[] headers) {
		for (int i = 0; i < headers.length; i++) {
			if (text.matches("^" + headers[i] + "[.、．].*")) {
				return true;
			}
		}
		return false;
	}

	public static void parseHtmlTuQuestions(String fileName) throws Exception {
		System.out.print("解析html开始...");
		System.out.print("文件名称为：" + fileName);
		org.jsoup.nodes.Document doc = Jsoup.parse(new File(fileName), "utf-8");
		Element body = doc.body();
		Elements children = body.children();
		boolean categoryStart = false;
		boolean questionStart = false;
		for (int i = 0; i < children.size(); i++) {
			Element e = children.get(i);
			String text = e.text();
			String html = e.html();
			if (isStart(text, DEFAULT_CATEGORIES)) {
				// if (questionStart) {
				// System.out.print("\r\n}小题结束" + 111);
				// }
				if (categoryStart) {
					System.out.print("\r\n}大题结束" + 222);
				}
				System.out.print("\r\n大题开始 { " + html);
				categoryStart = true;
				questionStart = false;
			} else if (isStart(text, DEFAULT_NUMBERS)) {
				if (questionStart) {
					System.out.print("\r\n}小题结束" + 333);
				}
				System.out.print("\r\n小题开始{ " + html);
				questionStart = true;
				categoryStart = false;
			} else {
				if (categoryStart) {
					System.out.print("\r\n大题内容-" + html);
				} else if (questionStart) {
					System.out.print("\r\n小题内容-" + html);
				} else {
					System.out.print("其他内容" + html);
				}

			}
		}
		if (categoryStart) {
			System.out.print("\r\n}大题结束");
		}
		if (questionStart) {
			System.out.print("\r\n}小题结束");
		}
		System.out.print("解析html结束...");
	}

	public static void main(String[] args) {

		// ExecutorService service =
		// Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()
		// * 2);
		// for (int i = 0; i < 100; i++) {
		// service.execute(new Runnable() {
		// public void run() {
		// try {
		// long start = System.currentTimeMillis();
		// String inpath = "f://word/";
		// String infileName = "数学试卷.doc";
		// String outPath = "f://word/";
		// String outfileName = System.nanoTime() + ".html";
		// String fileName = WordDocumentUtils.convertToHTML(inpath, infileName,
		// outPath, outfileName);
		// // parseHtmlTuQuestions(inpath + fileName);
		// System.out.println("--------------");
		// System.out.print("耗时： " + (System.currentTimeMillis() - start) +
		// "毫秒");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });
		//
		// }
		// service.shutdown();

		try {
			parseHtmlTuQuestions("F:/word/试题解析/1461030856229.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
