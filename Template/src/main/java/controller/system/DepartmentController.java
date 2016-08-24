package controller.system;

import java.util.Date;
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
import model.system.DepartmentVO;
import model.system.DepartmentVO.IAddDepartment;
import model.system.DepartmentVO.IModifyDepartment;
import service.system.DepartmentService;
import utils.bean.BeanDirectorFactory;

@RequestMapping("/department")
@ResponseBody
@Controller
public class DepartmentController {
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("query")
	public ReturnResult query() {
		ReturnResult returnResult = ControllerContext.getResult();
		QueryVO queryVO = BeanDirectorFactory.getBeanDirector().getDataVO(QueryVO.class);
		Map<String, Object> result = departmentService.queryDepartment(queryVO);
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result.get(StaticsConstancts.DATA))
				.setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("showQueryDepartment success");
		return returnResult;
	}

	@RequestMapping(value = "save")
	public ReturnResult save() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		DepartmentVO departmentVO = BeanDirectorFactory.getBeanDirector().getDataVO(DepartmentVO.class, va,
				IAddDepartment.class);
		if (ControllerHelper.checkError(departmentVO, va, returnResult, logger)) {
			return returnResult;
		}
		departmentVO.setCreateTime(new Date());
		departmentVO.setCreator(ControllerHelper.getUserName());
		departmentService.addDepartment(departmentVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("addDepartment success");
		return returnResult;
	}

	@RequestMapping(value = "update")
	public ReturnResult update() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		DepartmentVO departmentVO = BeanDirectorFactory.getBeanDirector().getDataVO(DepartmentVO.class, va,
				IModifyDepartment.class);
		if (ControllerHelper.checkError(departmentVO, va, returnResult, logger)) {
			return returnResult;
		}
		departmentService.modifyDepartment(departmentVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("modifyDepartment success");
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
			departmentService.deleteDepartment(Integer.parseInt(param));
			returnResult.setStatus(StatusCode.SUCCESS);
			logger.debug("deleteDepartment success");
		} catch (Exception e) {
			returnResult.setStatus(StatusCode.FAIL.setMessage("已被引用，无法删除"));
			logger.debug("deleteDepartment fail");
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
		returnResult.setStatus(StatusCode.SUCCESS)
				.setRows(departmentService.getDepartmentVOById(Integer.parseInt(param)));
		logger.debug("getDepartment success");
		return returnResult;
	}

	@RequestMapping(value = "getAllDepartment")
	public ReturnResult getAllDepartment() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(departmentService.getAllDepartment());
		logger.debug("getAllDepartment success");
		return returnResult;
	}

}
