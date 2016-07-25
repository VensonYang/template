package controller.universal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.ControllerContext;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import service.universal.EchartsService;

@RequestMapping("/echart")
@ResponseBody
@Controller
public class EchartsController {

	Logger logger = LoggerFactory.getLogger(EchartsController.class);
	@Autowired
	private EchartsService echartsService;

	@RequestMapping(value = "lineOption")
	public ReturnResult lineOption() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(echartsService.getLineData());
		return returnResult;
	}

	@RequestMapping(value = "barOption")
	public ReturnResult barOption() {
		ReturnResult returnResult = ControllerContext.getResult();
		// 获取option
		returnResult.setStatus(StatusCode.SUCCESS).setRows(echartsService.getBarData());
		return returnResult;
	}

	@RequestMapping(value = "pieOption")
	public ReturnResult pieOption() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(echartsService.getPieData());
		return returnResult;
	}
}
