package common.parse;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
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
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFPanel;
import org.freehep.graphicsio.emf.EMFRenderer;
import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import net.arnx.wmf2svg.gdi.svg.SvgGdi;
import net.arnx.wmf2svg.gdi.wmf.WmfParser;

public class WordToHtml {

	private static final Logger logger = LoggerFactory.getLogger(WordToHtml.class);

	private WordToHtml() {
	}

	private static WordToHtml instance = new WordToHtml();

	public static WordToHtml getInstance() {
		return instance;
	}

	public static void main(String args[]) throws Exception {
		// toHtml("f:/", "test.doc", "f:/", "sd.html");
		instance.toHtml("f://word/", "数学试卷.doc", "f://word/", "数学试卷.html");

	}

	public void convertTxtToHtml(String inPath, String inFileName, String outPath, String outFileName) {
		try {
			String content = getStringFormTxt(inPath + inFileName);
			writeHtml(content, outPath, outFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeHtml(String content, String outPath, String outFileName) throws IOException {
		File writefile = new File(outPath + outFileName);
		writefile.createNewFile();
		writefile = new File(outPath + outFileName); // 重新实例化
		FileOutputStream fw = new FileOutputStream(writefile);
		fw.write(content.getBytes());
		fw.flush();
		fw.close();
	}

	/**
	 * 
	 * @param inPath
	 * @param inFileName
	 * @param outPath
	 * @param outFileName
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public void wordToHtml03(String inPath, String inFileName, final String outPath, final String outFileName)
			throws IOException, ParserConfigurationException, TransformerException {
		STUnderline.Enum.forInt(1);
		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(inPath + inFileName));
		Range range = wordDocument.getRange();
		// TODO此处将文档中的半角空格转为全角空格（为了保存文档中的空格和样式下划线）
		range.replaceText(" ", "　");
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
					float heightInches) {
				return savePic(content, pictureType, outPath, outFileName, widthInches * 100, heightInches * 100);
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		Document htmlDocument = wordToHtmlConverter.getDocument();
		OutputStream outStream = new FileOutputStream(new File(outPath + outFileName));
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		outStream.close();
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
	public String savePic(byte[] content, PictureType pictureType, String outPath, String outfileName, float width,
			float height) {
		String outFloder = outfileName.substring(0, outfileName.lastIndexOf("."));
		File outFile = new File(outPath + outFloder);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		try {
			String fileName = outPath + outFloder + File.separator + java.util.UUID.randomUUID().toString() + "."
					+ pictureType.getExtension();
			if (pictureType.getExtension() == PictureType.WMF.getExtension()) {
				fileName = outPath + outFloder + File.separator + java.util.UUID.randomUUID().toString() + ".png";
				wmf2Png(content, fileName, width, height);
				return fileName;
			} else if (pictureType.getExtension() == PictureType.EMF.getExtension()) {
				fileName = outPath + outFloder + File.separator + java.util.UUID.randomUUID().toString() + ".png";
				emf2Png(content, fileName, width, height);
				return fileName;
			}
			scaleImg(content, width, height, fileName, pictureType.getExtension());
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void scaleImg(byte[] in, float width, float height, String path, String ext) throws IOException {
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
	private void emf2Png(byte[] content, String fileName, float width, float height) throws Exception {

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
	private void wmf2Png(byte[] content, String fileName, float width, float height) throws Exception {
		WmfParser parser = new WmfParser();
		SvgGdi gdi = new SvgGdi(false);
		parser.parse(new ByteArrayInputStream(content), gdi);

		Document doc = gdi.getDocument();
		// scaleSVG(doc, (int) width, (int) height);
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
		it.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(0.9f));
		it.addTranscodingHint(ImageTranscoder.KEY_WIDTH, width);
		it.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, height);

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
	// private static void scaleSVG(Document doc, int width, int height) {
	//
	// doc.getElementsByTagName("svg").item(0).getAttributes().getNamedItem("width").setNodeValue((int)
	// width + "");
	// doc.getElementsByTagName("svg").item(0).getAttributes().getNamedItem("height").setNodeValue((int)
	// height + "");
	//
	// }

	public boolean doGenerateSysOut(String inPath, String inFileName, String outPath, String outFileName)
			throws IOException {
		boolean flag = true;
		try {
			// 例：outFileName = aa.html,outFileNameNoable = aa
			String outFileNameNoable = outFileName.split(".htm")[0];
			String fileOutName = outPath + outFileName;
			XWPFDocument document = new XWPFDocument(new FileInputStream(inPath + inFileName));
			XHTMLOptions options = XHTMLOptions.create().indent(4);
			// Extract image 创建存储图片文件夹
			File imageFolder = new File(outPath + outFileNameNoable);
			options.setExtractor(new FileImageExtractor(imageFolder));
			// URI resolver
			options.URIResolver(new FileURIResolver(imageFolder));
			File outFile = new File(fileOutName);
			outFile.getParentFile().mkdirs();
			OutputStream out = new FileOutputStream(outFile);
			XHTMLConverter.getInstance().convert(document, out, options);
			String imgPath = outPath + outFileNameNoable + File.separator + "word" + File.separator + "media";
			List<XWPFPictureData> pits = document.getAllPictures();
			for (XWPFPictureData pit : pits) {
				if (pit.suggestFileExtension().equalsIgnoreCase(PictureType.WMF.getExtension())) {
					String fileName = imgPath + File.separator
							+ pit.getFileName().substring(0, pit.getFileName().lastIndexOf(".")) + ".png";
					wmf2Png(pit.getData(), fileName, 100, 100);
				}
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public void imgBitchConvert(String path) throws Exception {
		File file = new File(path);
		File[] files = file.listFiles();
		for (File f : files) {
			FileInputStream fis = new FileInputStream(f);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			wmf2Png(bos.toByteArray(),
					path + File.separator + f.getName().substring(0, f.getName().lastIndexOf(".")) + ".png", 100, 100);
			f.delete();
		}
	}

	/**
	 * 读取html文件到字符串
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String readFile(String filename) throws Exception {
		StringBuffer buffer = new StringBuffer("");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			buffer = new StringBuffer();
			while (br.ready())
				buffer.append((char) br.read());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();
		}
		return buffer.toString();
	}

	private String convertToChinese(String dataStr) {
		if (dataStr == null || dataStr.length() == 0) {
			return dataStr;
		}
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			int system = 10;// 进制
			if (start == 0) {
				int t = dataStr.indexOf("&#");
				if (start != t)
					start = t;
				if (start > 0) {
					buffer.append(dataStr.substring(0, start));
				}
				if (start == -1) {
					return dataStr;
				}
			}
			end = dataStr.indexOf(";", start + 2);
			String charStr = "";
			if (end != -1) {
				charStr = dataStr.substring(start + 2, end);
				// 判断进制
				char s = charStr.charAt(0);
				if (s == 'x' || s == 'X') {
					system = 16;
					charStr = charStr.substring(1);
				}
				// 转换
				try {
					char letter = (char) Integer.parseInt(charStr, system);
					buffer.append(new Character(letter).toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

			// 处理当前unicode字符到下一个unicode字符之间的非unicode字符
			start = dataStr.indexOf("&#", end);
			if (start - end > 1) {
				buffer.append(dataStr.substring(end + 1, start));
			}
			// 处理最后面的非 unicode字符
			if (start == -1) {
				int length = dataStr.length();
				if (end + 1 != length) {
					buffer.append(dataStr.substring(end + 1, length));
				}
			}
		}
		return buffer.toString();
	}

	public void writeToFile2(String path, String str) throws IOException {
		PrintStream ps = null;
		try {
			File file = new File(path);
			ps = new PrintStream(new FileOutputStream(file));
			ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");// 往文件里写入字符串,解决编码问题，语法不正规
			ps.append(str);// 在已有的基础上添加字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.flush();
				ps.close();
			}
		}
	}

	/**
	 * replace image src
	 * 
	 * @param htmlStr
	 * @return String
	 * @throws Exception
	 */

	public void convertDocxToHtml(String inPath, String inFileName, String outPath, String outFileName)
			throws Exception {
		if (doGenerateSysOut(inPath, inFileName, outPath, outFileName)) {
			writeToFile2(outPath + outFileName, convertToChinese(readFile(outPath + outFileName)));
		}
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 读出的txt的内容
	 */
	public String getStringFormTxt(String path) throws Exception {

		InputStreamReader in = new InputStreamReader(new FileInputStream(path), "gb2312");
		BufferedReader br = new BufferedReader(in);
		StringBuffer buff = new StringBuffer();
		buff.append("<!DOCTYPE html>\r\n");
		buff.append("<head>\r\n");
		buff.append("<meta charset=\"utf-8\" />\r\n");
		buff.append("</head>\r\n");
		buff.append("<body>\r\n");
		buff.append("<div>\r\n");
		String temp = null;
		while ((temp = br.readLine()) != null) {
			buff.append("<br>");
			buff.append(temp.replaceAll("\\s", "　").replaceAll("\\>", "&gt;").replaceAll("\\<", "&lt;"));
			buff.append("\r\n");
		}
		buff.append("\r\n</div>");
		buff.append("\r\n</body>");
		buff.append("\r\n</html>");
		br.close();
		return buff.toString();
	}

	/**
	 * 将word文档转换为html文件;
	 * 
	 * @param inPath
	 * @param inFileName
	 * @param outPath
	 * @param outFileName
	 * @throws Exception
	 */
	public void toHtml(String inPath, String inFileName, String outPath, String outFileName) throws Exception {
		logger.debug("文件:" + inFileName + "转换开始....");
		if (!new File(inPath + inFileName).exists()) {
			System.out.println(inPath + inFileName + " 文件不存在");
			return;
		}
		if (!new File(outPath).exists()) {
			new File(outPath).mkdirs();
		}
		boolean isDoc = false;
		if (inFileName.endsWith(".docx") || inFileName.endsWith(".DOCX")) {
			// 07
			convertDocxToHtml(inPath, inFileName, outPath, outFileName);
			isDoc = true;
		} else if (inFileName.endsWith(".doc") || inFileName.endsWith(".DOC")) {
			// 03
			wordToHtml03(inPath, inFileName, outPath, outFileName);
			// WordDocumentUtils.convertToHTML(inPath, inFileName, outPath,
			// outFileName);
			isDoc = true;

		} else {
			convertTxtToHtml(inPath, inFileName, outPath, outFileName);
		}
		if (isDoc) {
			updateImgSrc(outPath, outFileName);
		}
		logger.debug("文件:" + outFileName + "转换结束....");
	}

	private void updateImgSrc(String outPath, String outFileName) throws IOException, FileNotFoundException {
		org.jsoup.nodes.Document doc = Jsoup.parse(new File(outPath + outFileName), "utf-8");
		Element body = doc.body();
		Elements links = body.getElementsByTag("img");
		if (links != null && links.size() > 0) {
			for (Element link : links) {
				String oldSrc = link.attr("src");
				oldSrc = oldSrc.substring(oldSrc.lastIndexOf("\\") + 1);
				String folder = outFileName.substring(0, outFileName.lastIndexOf("."));
				String newSrc = null;
				if (oldSrc.contains(folder)) {
					newSrc = oldSrc;
				} else {
					newSrc = folder + File.separator + oldSrc;
				}
				newSrc = newSrc.replaceAll("\\\\", "/");
				// String baseUrl =
				// "http://192.168.0.200:8080/DITemplate/upload/html/";
				// link.attr("src", baseUrl + newSrc);
				if (newSrc.toLowerCase().contains("wmf")) {
					newSrc = newSrc.substring(0, newSrc.lastIndexOf(".")) + ".png";
				}
				link.attr("src", newSrc);
			}
			FileOutputStream fos = new FileOutputStream(outPath + outFileName);
			fos.write(doc.toString().getBytes());
			fos.flush();
			fos.close();
		}
	}

}