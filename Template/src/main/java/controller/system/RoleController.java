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
import model.system.RoleVO;
import model.system.RoleVO.IAddRole;
import model.system.RoleVO.IAddRolePrivileges;
import model.system.RoleVO.IModifyRole;
import service.system.RoleService;
import utils.bean.BeanDirectorFactory;

@ResponseBody
@Controller
@RequestMapping("/role")
public class RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;

	@RequestMapping("showQueryRole")
	public ReturnResult showQueryRole() {
		ReturnResult returnResult = ControllerContext.getResult();
		QueryVO queryVO = BeanDirectorFactory.getBeanDirector().getDataVO(QueryVO.class);
		Map<String, Object> result = roleService.queryRole(queryVO);
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result.get(StaticsConstancts.DATA))
				.setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("showQueryRole success");
		return returnResult;
	}

	@RequestMapping(value = "addRole")
	public ReturnResult addRole() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		RoleVO roleVO = BeanDirectorFactory.getBeanDirector().getDataVO(RoleVO.class, va, IAddRole.class);
		logger.debug(roleVO.getRoleName());
		if (ControllerHelper.checkError(roleVO, va, returnResult, logger)) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setRows(roleService.addRole(roleVO));
		logger.debug("save Rolesuccess");
		return returnResult;
	}

	@RequestMapping(value = "modifyRole")
	public ReturnResult modifyRole() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		RoleVO RoleVO = BeanDirectorFactory.getBeanDirector().getDataVO(RoleVO.class, va, IModifyRole.class);
		if (ControllerHelper.checkError(RoleVO, va, returnResult, logger)) {
			return returnResult;
		}
		roleService.modifyRole(RoleVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("modify Rolesuccess");
		return returnResult;
	}

	@RequestMapping(value = "deleteRole")
	public ReturnResult deleteRole() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		try {
			roleService.deleteRole(Integer.parseInt(param));
			returnResult.setStatus(StatusCode.SUCCESS);
		} catch (Exception e) {
			returnResult.setStatus(StatusCode.FAIL.setMessage("已被引用，无法删除"));
		}
		logger.debug("deleteRole success");
		return returnResult;
	}

	@RequestMapping(value = "getRole")
	public ReturnResult getRole() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setRows(roleService.getRoleVOById(Integer.parseInt(param)));
		logger.debug("getRole success");
		return returnResult;
	}

	@RequestMapping("showRole")
	public ReturnResult showRole() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(roleService.getRoles());
		logger.debug("showRole success");
		return returnResult;
	}

	@RequestMapping(value = "getRolePrivileges")
	public ReturnResult getRolePrivileges() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS)
				.setRows(roleService.getPrivilegesVOByRoleId(Integer.parseInt(param)));
		logger.debug("getRolePrivileges success");
		return returnResult;
	}

	@RequestMapping(value = "addRolePrivileges")
	public ReturnResult addRolePrivileges() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		RoleVO roleVO = BeanDirectorFactory.getBeanDirector().getDataVO(RoleVO.class, va, IAddRolePrivileges.class);
		if (ControllerHelper.checkError(roleVO, va, returnResult, logger)) {
			return returnResult;
		}
		roleService.addRolePrivileges(roleVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("save RolePrivileges");
		return returnResult;
	}

}
