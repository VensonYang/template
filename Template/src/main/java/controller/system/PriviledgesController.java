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
import controller.base.ValidParam;
import controller.base.ValidationAware;
import controller.base.ValidationAwareSupport;
import controller.result.ReturnResult;
import controller.result.StatusCode;
import model.common.QueryVO;
import model.system.PriviledgesVO;
import model.system.PriviledgesVO.IAddPriviledges;
import model.system.PriviledgesVO.IModifyPriviledges;
import service.system.PriviledgesService;
import utils.bean.BeanDirectorFactory;

@ResponseBody
@RequestMapping("/priviledges")
@Controller
public class PriviledgesController {
	private static final Logger logger = LoggerFactory.getLogger(PriviledgesController.class);
	@Autowired
	private PriviledgesService priviledgesService;

	@RequestMapping("showQueryPriviledges")
	public ReturnResult showQueryPriviledges() {
		ReturnResult returnResult = ControllerContext.getResult();
		QueryVO queryVO = BeanDirectorFactory.getBeanDirector().getDataVO(QueryVO.class);
		Map<String, Object> result = priviledgesService.queryPriviledges(queryVO);
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result.get(StaticsConstancts.DATA))
				.setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("showPriviledges success");
		return returnResult;
	}

	@RequestMapping(value = "addPriviledges")
	public ReturnResult addPriviledges() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		PriviledgesVO priviledgesVO = BeanDirectorFactory.getBeanDirector().getDataVO(PriviledgesVO.class, va,
				IAddPriviledges.class);
		if (ControllerHelper.checkError(priviledgesVO, va, returnResult, logger)) {
			return returnResult;
		}
		priviledgesVO.setCreator(ControllerHelper.getUserId());
		priviledgesService.addPriviledges(priviledgesVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("save Priviledgessuccess");
		return returnResult;
	}

	@RequestMapping(value = "modifyPriviledges")
	public ReturnResult modifyPriviledges() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		PriviledgesVO priviledgesVO = BeanDirectorFactory.getBeanDirector().getDataVO(PriviledgesVO.class, va,
				IModifyPriviledges.class);
		if (ControllerHelper.checkError(priviledgesVO, va, returnResult, logger)) {
			return returnResult;
		}
		priviledgesService.modifyPriviledges(priviledgesVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("modify Priviledgessuccess");
		return returnResult;
	}

	@RequestMapping(value = "deletePriviledges")
	public ReturnResult deletePriviledges() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		priviledgesService.deletePriviledges(Integer.parseInt(param));
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("deletePriviledges success");
		return returnResult;
	}

	@RequestMapping(value = "getPriviledges")
	public ReturnResult getPriviledges() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS)
				.setRows(priviledgesService.getPriviledgesVOById(Integer.parseInt(param)));
		logger.debug("get Priviledgessuccess");
		return returnResult;
	}

	@RequestMapping(value = "getMenu")
	public ReturnResult getMenu() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(priviledgesService.getMenu());
		logger.debug("get Menusuccess");
		return returnResult;
	}

}
