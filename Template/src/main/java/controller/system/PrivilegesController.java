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
import model.system.PrivilegesVO;
import model.system.PrivilegesVO.IAddPrivileges;
import model.system.PrivilegesVO.IModifyPrivileges;
import service.system.PrivilegesService;
import utils.bean.BeanDirectorFactory;

@ResponseBody
@RequestMapping("/privileges")
@Controller
public class PrivilegesController {
	private static final Logger logger = LoggerFactory.getLogger(PrivilegesController.class);
	@Autowired
	private PrivilegesService privilegesService;

	@RequestMapping("query")
	public ReturnResult query() {
		ReturnResult returnResult = ControllerContext.getResult();
		QueryVO queryVO = BeanDirectorFactory.getBeanDirector().getDataVO(QueryVO.class);
		Map<String, Object> result = privilegesService.queryPrivileges(queryVO);
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result.get(StaticsConstancts.DATA))
				.setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("showPrivileges success");
		return returnResult;
	}

	@RequestMapping(value = "save")
	public ReturnResult save() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		PrivilegesVO privilegesVO = BeanDirectorFactory.getBeanDirector().getDataVO(PrivilegesVO.class, va,
				IAddPrivileges.class);
		if (ControllerHelper.checkError(privilegesVO, va, returnResult, logger)) {
			return returnResult;
		}
		privilegesVO.setCreator(ControllerHelper.getUserId());
		privilegesService.addPrivileges(privilegesVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("save Privilegessuccess");
		return returnResult;
	}

	@RequestMapping(value = "update")
	public ReturnResult update() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		PrivilegesVO privilegesVO = BeanDirectorFactory.getBeanDirector().getDataVO(PrivilegesVO.class, va,
				IModifyPrivileges.class);
		if (ControllerHelper.checkError(privilegesVO, va, returnResult, logger)) {
			return returnResult;
		}
		privilegesService.modifyPrivileges(privilegesVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("modify Privilegessuccess");
		return returnResult;
	}

	@RequestMapping(value = "delete")
	public ReturnResult delete() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		privilegesService.deletePrivileges(Integer.parseInt(param));
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("deletePrivileges success");
		return returnResult;
	}

	@RequestMapping(value = "get")
	public ReturnResult get() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS)
				.setRows(privilegesService.getPrivilegesVOById(Integer.parseInt(param)));
		logger.debug("get Privilegessuccess");
		return returnResult;
	}

	@RequestMapping(value = "getMenu")
	public ReturnResult getMenu() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(privilegesService.getMenu());
		logger.debug("get Menusuccess");
		return returnResult;
	}

}
