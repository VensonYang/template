package common.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Emphasis;

@SuppressWarnings("rawtypes")
public class EchartsUtil {

	public static Option bar(String title, String[] legends, String[] category, String formatter,
			List<List<Object>> data) {
		Option option = lineOrPie(title, legends, category, formatter, data, true);
		List<Series> seriess = new LinkedList<Series>();
		for (int i = 0; i < legends.length; i++) {
			Bar bar = new Bar(legends[i]);
			bar.setData(data.get(i));
			seriess.add(bar);
		}
		option.series(seriess);
		return option;

	}

	public static Option line(String title, String[] legends, String[] category, String formatter,
			List<List<Object>> data) {
		Option option = lineOrPie(title, legends, category, formatter, data, false);
		List<Series> seriess = new LinkedList<Series>();
		for (int i = 0; i < legends.length; i++) {
			Line line = new Line(legends[i]);
			line.setData(data.get(i));
			seriess.add(line);
		}
		option.series(seriess);
		return option;

	}

	public static Option lineOrPie(String title, String[] legends, String[] category, String formatter,
			List<List<Object>> data, boolean isToobox) {
		// 创建option对象,并设置属性配置
		Option option = new Option();
		// 设置标题
		option.title().text(title);
		// 创建提示工具
		option.tooltip().trigger(Trigger.axis);
		// 设置图例说明
		Legend legend = new Legend();
		legend.setData(Arrays.asList(legends));
		option.legend(legend);
		if (isToobox) {
			// 设置工具条，数据类型，还原，保存为图片
			option.toolbox().show(true).feature(new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);
		}
		// 设置x轴为类目轴
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setData(Arrays.asList(category));
		option.xAxis(categoryAxis);
		// 设置y轴为值轴
		option.yAxis(new ValueAxis().axisLabel(new AxisLabel().formatter(formatter)));
		return option;

	}

	public static Option pie(String title, String name, List<Map<String, Object>> data) {
		// 创建option对象,并设置属性配置
		Option option = new Option();
		// 设置标题
		option.title().text(title).x("center");
		// 创建提示工具
		option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
		// 从数据中获取图例数据
		List<Object> legends = new LinkedList<Object>();
		for (Map<String, Object> map : data) {
			legends.add(map.get("name"));
		}
		// 设置图例说明
		option.legend().orient(Orient.vertical).data(legends).setLeft("left");
		// 设置并创建Bar数据
		Pie pie = new Pie(name);
		pie.setData(data);
		// pie.setRadius("55%");
		option.series(pie.itemStyle(new ItemStyle()
				.emphasis(new Emphasis().shadowBlur(10).shadowOffsetX(0).shadowColor("rgba(0, 0, 0, 0.5)"))));
		return option;
	}

}
