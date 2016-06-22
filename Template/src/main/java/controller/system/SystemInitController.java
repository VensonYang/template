package controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.ControllerContext;
import controller.result.ReturnResult;
import controller.result.StatusCode;
import service.system.SystemInitService;

@Controller
@RequestMapping("/systemInit")
@ResponseBody
public class SystemInitController {
	private static final Logger logger = LoggerFactory.getLogger(SystemInitController.class);
	@Autowired
	private SystemInitService systemInitService;

	@RequestMapping("initMoralStudent")
	public ReturnResult initMoralStudent() {
		ReturnResult result = ControllerContext.getResult();
		try {
			systemInitService.initMoralStudent();
			result.setStatus(StatusCode.SUCCESS);
			logger.debug("initMoralStudent success");
		} catch (Exception e) {
			result.setStatus(StatusCode.FAIL.setMessage("初始化德育记录失败"));
			logger.debug("initMoralStudent fail");
		}
		return result;
	}

	@RequestMapping("initStudentAppraisal")
	public ReturnResult initStudentAppraisal() {
		ReturnResult result = ControllerContext.getResult();
		try {
			systemInitService.initStudentAppraisal();
			result.setStatus(StatusCode.SUCCESS);
			logger.debug("initStudentAppraisal success");
		} catch (Exception e) {
			result.setStatus(StatusCode.FAIL.setMessage("初始化德育记录失败"));
			logger.debug("initStudentAppraisal fail");
		}
		return result;
	}

	@RequestMapping("initStudent")
	public ReturnResult initStudent() {
		ReturnResult result = ControllerContext.getResult();
		try {
			systemInitService.initStudent();
			result.setStatus(StatusCode.SUCCESS);
			logger.debug("initStudent success");
		} catch (Exception e) {
			result.setStatus(StatusCode.FAIL.setMessage("初始化德育记录失败"));
			logger.debug("initStudent fail");
		}
		return result;
	}

	@RequestMapping("initTeacher")
	public ReturnResult initTeacher() {
		ReturnResult result = ControllerContext.getResult();
		try {
			systemInitService.initTeacher();
			result.setStatus(StatusCode.SUCCESS);
			logger.debug("initTeacher success");
		} catch (Exception e) {
			result.setStatus(StatusCode.FAIL.setMessage("初始化德育记录失败"));
			logger.debug("initTeacher fail");
		}
		return result;
	}

	@RequestMapping("initClass")
	public ReturnResult initClass() {
		ReturnResult result = ControllerContext.getResult();
		try {
			systemInitService.initClass();
			result.setStatus(StatusCode.SUCCESS);
			logger.debug("initClass success");
		} catch (Exception e) {
			result.setStatus(StatusCode.FAIL.setMessage("初始化德育记录失败"));
			logger.debug("initClass fail");
		}
		return result;
	}

	@RequestMapping("initCourse")
	public ReturnResult initCourse() {
		ReturnResult result = ControllerContext.getResult();
		try {
			systemInitService.initCourse();
			result.setStatus(StatusCode.SUCCESS);
			logger.debug("initCourse success");
		} catch (Exception e) {
			result.setStatus(StatusCode.FAIL.setMessage("初始化德育记录失败"));
			logger.debug("initCourse fail");
		}
		return result;
	}

	@RequestMapping("initMoral")
	public ReturnResult initMoral() {
		ReturnResult result = ControllerContext.getResult();
		try {
			systemInitService.initMoral();
			result.setStatus(StatusCode.SUCCESS);
			logger.debug("initMoral success");
		} catch (Exception e) {
			result.setStatus(StatusCode.FAIL.setMessage("初始化德育记录失败"));
			logger.debug("initMoral fail");
		}
		return result;
	}

	@RequestMapping("updateSystem")
	public ReturnResult updateSystem() {
		ReturnResult result = ControllerContext.getResult();
		systemInitService.updateSystem();
		result.setStatus(StatusCode.SUCCESS);
		logger.debug("updateSystem success");
		return result;
	}
}
