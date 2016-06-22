package service.universal;

public interface EchartsService {
	/**
	 * 模拟获取饼图数据
	 * 
	 * @param params参数
	 */
	Object getPieData(Object... params);

	/**
	 * 模拟获取折线图数据
	 * 
	 * @param params参数
	 */
	Object getLineData(Object... params);

	/**
	 * 模拟获取柱状图数据
	 * 
	 * @param params参数
	 */
	Object getBarData(Object... params);
}
