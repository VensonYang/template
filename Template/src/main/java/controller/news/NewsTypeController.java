package controller.news;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.utils.BeanDirectorFactory;
import common.StaticsConstancts;
import controller.base.ControllerContext;
import controller.base.ControllerHelper;
import controller.base.ValidParam;
import controller.base.ValidationAware;
import controller.base.ValidationAwareSupport;
import controller.result.ReturnResult;
import controller.result.StatusCode;
import model.common.QueryVO;
import model.news.NewsTypeVO;
import model.news.NewsTypeVO.IAddNewsType;
import model.news.NewsTypeVO.IModifyNewsType;
import service.news.NewsTypeService;

@RequestMapping("/newsType")
@ResponseBody
@Controller
public class NewsTypeController {
	private static final Logger logger = LoggerFactory.getLogger(NewsTypeController.class);
	@Autowired
	private NewsTypeService newsTypeService;

	@RequestMapping("showQueryNewsType")
	public ReturnResult showQueryNewsType() {
		ReturnResult returnResult = ControllerContext.getResult();
		QueryVO queryVO = BeanDirectorFactory.getBeanDirector().getDataVO(QueryVO.class);
		Map<String, Object> result = newsTypeService.queryNewsType(queryVO);
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result.get(StaticsConstancts.DATA))
				.setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("showQueryFiles success");
		return returnResult;
	}

	@RequestMapping(value = "addNewsType")
	public ReturnResult addNewsType() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		NewsTypeVO NewsTypeVO = BeanDirectorFactory.getBeanDirector().getDataVO(NewsTypeVO.class, va,
				IAddNewsType.class);
		if (ControllerHelper.checkError(NewsTypeVO, va, returnResult, logger)) {
			return returnResult;
		}
		newsTypeService.addNewsType(NewsTypeVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("save NewsTypesuccess");
		return returnResult;
	}

	@RequestMapping(value = "modifyNewsType")
	public ReturnResult modifyNewsType() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		NewsTypeVO NewsTypeVO = BeanDirectorFactory.getBeanDirector().getDataVO(NewsTypeVO.class, va,
				IModifyNewsType.class);
		if (ControllerHelper.checkError(NewsTypeVO, va, returnResult, logger)) {
			return returnResult;
		}
		newsTypeService.modifyNewsType(NewsTypeVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("modify NewsTypesuccess");
		return returnResult;
	}

	@RequestMapping(value = "deleteNewsType")
	public ReturnResult deleteNewsType() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		try {
			newsTypeService.deleteNewsType(Integer.parseInt(param));
			returnResult.setStatus(StatusCode.SUCCESS);
		} catch (Exception e) {
			returnResult.setStatus(StatusCode.FAIL.setMessage("已被引用，无法删除"));
		}
		logger.debug("deleteNewsType success");
		return returnResult;
	}

	@RequestMapping(value = "getNewsType")
	public ReturnResult getNewsType() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setRows(newsTypeService.getNewsTypeVOById(Integer.parseInt(param)));
		logger.debug("get NewsTypesuccess");
		return returnResult;
	}

	@RequestMapping("getNewTypes")
	public ReturnResult getNewTypes() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(newsTypeService.getNewsTypes());
		logger.debug("getNewTypes success");
		return returnResult;
	}
}
