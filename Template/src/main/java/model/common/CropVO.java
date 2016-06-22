package model.common;

public class CropVO {
	private double x;
	private double y;
	private double w;
	private double h;
	private String fileName;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "CropVO [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", fileName=" + fileName + "]";
	}

}
