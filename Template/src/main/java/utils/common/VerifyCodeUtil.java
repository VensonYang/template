package utils.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * 生成验证码工具类
 */
public final class VerifyCodeUtil {
	private static final String[] chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	/*
	 * , "A", "B", "C", "D", "E", "F", "G", "H", "I", "发", "东", "南", "西", "北",
	 * "中", "白"
	 */
	private static final int SIZE = 4;
	private static final int LINES = 2;
	private static final int WIDTH = 44;
	private static final int HEIGHT = 30;
	private static final int FONT_SIZE = 15;

	private VerifyCodeUtil() {
	}

	public static Map<String, BufferedImage> createImage() {
		StringBuffer sb = new StringBuffer();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();
		// 画随机字符
		for (int i = 1; i <= SIZE; i++) {

			int r = ran.nextInt(chars.length);
			graphic.setColor(Color.BLACK);
			graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
			// 验证码 坐标
			graphic.drawString(chars[r], (i - 1) * WIDTH / SIZE, HEIGHT / 2);
			sb.append(chars[r]);// 将字符保存，以便存入Session
		}
		// 画干扰线
		for (int i = 1; i <= LINES; i++) {
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
		}
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.put(sb.toString(), image);
		return map;
	}

	public static InputStream getInputStream(BufferedImage image) throws IOException {
		// 将图片给imageStream赋值
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
		ImageIO.write(image, "jpg", imOut);
		return new ByteArrayInputStream(bs.toByteArray());
	}

	public static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
		return color;
	}

}
