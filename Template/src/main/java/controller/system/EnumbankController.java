package controller.system;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.StaticsConstancts;
import controller.base.ControllerContext;
import controller.base.ControllerHelper;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import controller.base.ValidParam;
import controller.base.ValidationAware;
import controller.base.ValidationAwareSupport;
import model.common.QueryVO;
import model.system.EnumbankVO;
import model.system.EnumbankVO.IAddEnumbank;
import model.system.EnumbankVO.IModifyEnumbank;
import service.system.EnumbankService;
import utils.bean.BeanDirectorFactory;

@RequestMapping("/enumbank")
@ResponseBody
@Controller
public class EnumbankController {
	private static final Logger logger = LoggerFactory.getLogger(EnumbankController.class);
	@Autowired
	private EnumbankService enumbankService;

	@RequestMapping("query")
	public ReturnResult query() {
		ReturnResult returnResult = ControllerContext.getResult();
		QueryVO queryVO = BeanDirectorFactory.getBeanDirector().getDataVO(QueryVO.class);
		Map<String, Object> result = enumbankService.queryEnumbank(queryVO);
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result.get(StaticsConstancts.DATA))
				.setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("showQueryEnumbank success");
		return returnResult;
	}

	@RequestMapping(value = "save")
	public ReturnResult save() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		EnumbankVO enumbankVO = BeanDirectorFactory.getBeanDirector().getDataVO(EnumbankVO.class, va,
				IAddEnumbank.class);
		if (ControllerHelper.checkError(enumbankVO, va, returnResult, logger)) {
			return returnResult;
		}
		enumbankService.addEnumbank(enumbankVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("addEnumbank success");
		return returnResult;
	}

	@RequestMapping(value = "update")
	public ReturnResult update() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		EnumbankVO enumbankVO = BeanDirectorFactory.getBeanDirector().getDataVO(EnumbankVO.class, va,
				IModifyEnumbank.class);
		if (ControllerHelper.checkError(enumbankVO, va, returnResult, logger)) {
			return returnResult;
		}
		enumbankService.modifyEnumbank(enumbankVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("modifyEnumbank success");
		return returnResult;
	}

	@RequestMapping(value = "delete")
	public ReturnResult delete() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		try {
			enumbankService.deleteEnumbank(Integer.parseInt(param));
			returnResult.setStatus(StatusCode.SUCCESS);
			logger.debug("deleteEnumbank success");
		} catch (Exception e) {
			returnResult.setStatus(StatusCode.FAIL.setMessage("已被引用，无法删除"));
			logger.debug("deleteEnumbank fail");
		}
		return returnResult;
	}

	@RequestMapping(value = "get")
	public ReturnResult get() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setRows(enumbankService.getEnumbankVOById(Integer.parseInt(param)));
		logger.debug("getEnumbank success");
		return returnResult;
	}

	@RequestMapping(value = "getEnumByEnumTypeId")
	public ReturnResult getEnumByEnumTypeId() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NOT_BLANK);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setRows(enumbankService.getEnumByEnumTypeId(param));
		logger.debug("getEnumByEnumTypeId success");
		return returnResult;
	}

	@RequestMapping(value = "getSelectByTypeId")
	public ReturnResult getSelectByTypeId() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NOT_BLANK);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setRows(enumbankService.getSelectByTypeId(param));
		logger.debug("getEnumByEnumTypeId success");
		return returnResult;
	}

}
