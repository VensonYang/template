package service.universal.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import service.universal.EchartsService;
import utils.common.EchartsUtil;

@Service("echartsService")
public class EchartsServiceImpl implements EchartsService {

	@Override
	public Object getPieData(Object... params) {
		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		for (int i = 1; i <= 5; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "某网站站点" + i);
			map.put("value", i * 300);
			data.add(map);
		}
		String title = "饼图示例";
		String name = "访问来源";
		return EchartsUtil.pie(title, name, data);
	}

	@Override
	public Object getLineData(Object... params) {
		// 建立模拟数据
		List<Object> data1 = new LinkedList<Object>();
		List<Object> data2 = new LinkedList<Object>();
		for (int i = 1; i <= 7; i++) {

			data1.add(i * Math.round(Math.random() * 5));
			data2.add(i * Math.round(Math.random() * 2));
		}
		// 设置数据
		List<List<Object>> data = new LinkedList<List<Object>>();
		data.add(data1);
		data.add(data2);
		String title = "线图示例";
		String[] legeng = new String[] { "最高气温", "最低气温" };
		String[] category = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
		String formatter = "{value} °C";
		return EchartsUtil.line(title, legeng, category, formatter, data);
	}

	@Override
	public Object getBarData(Object... params) {
		// 建立模拟数据
		List<Object> data1 = new LinkedList<Object>();
		List<Object> data2 = new LinkedList<Object>();
		for (int i = 1; i <= 6; i++) {
			data1.add(i * Math.round(Math.random() * 10));
			data2.add(i * Math.round(Math.random() * 5));
		}
		// 设置数据
		List<List<Object>> data = new LinkedList<List<Object>>();
		data.add(data1);
		data.add(data2);

		String title = "柱状图示例";
		String[] legeng = new String[] { "一月份", "二月份" };
		String[] category = new String[] { "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" };
		String formatter = "{value} 件";
		return EchartsUtil.bar(title, legeng, category, formatter, data);
	}

}
