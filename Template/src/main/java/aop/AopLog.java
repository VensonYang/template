package aop;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import common.StaticsConstancts;
import controller.base.ControllerContext;
import utils.bean.FieldUtil;

@Component("aopLog")
@Aspect
public class AopLog {
	private static final Logger logger = LoggerFactory.getLogger(AopLog.class);

	@AfterReturning(pointcut = "execution(* service..*.add*(..))", returning = "returnValue")
	public void afterAdd(JoinPoint point, Object returnValue) {
		processPrint(point, "增加", returnValue);
	}

	@After("execution(* service..*.delete*(..))")
	public void afterDelete(JoinPoint point) {
		processPrint(point, "删除", null);
	}

	@After("execution(* service..*.modify*(..))")
	public void afterModify(JoinPoint point) {
		processPrint(point, "修改", null);
	}

	@SuppressWarnings("unchecked")
	private void processPrint(JoinPoint point, String Operation, Object returnValue) {
		HttpSession session = ControllerContext.getSession();
		String methodName = point.getSignature().getName();
		Object[] args = point.getArgs();
		Object obj = null;
		if (args != null && args.length >= 1) {
			obj = args[0];
		}
		LogVO logVO = new LogVO();
		logVO.setUserName((String) session.getAttribute(StaticsConstancts.USER_NAME));
		logVO.setUserId((Integer) session.getAttribute(StaticsConstancts.USER_ID));
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(StaticsConstancts.USER_INFO);
		logVO.setUserIP((String) userInfo.get(StaticsConstancts.USER_IP_ADDRESS));
		logVO.setCreateDate(new Date());
		logVO.setOperation(Operation);
		// logVO.setContent("在类[" + target.getClass().getName() + "]中执行了[" +
		// methodName + "]方法");
		if (Operation.equals("增加")) {
			logVO.setContent("一个" + methodName.substring(3) + "对象，该对象的ID为" + returnValue);
		} else if (Operation.equals("修改")) {
			logVO.setContent("一个" + methodName.substring(6) + "对象，该对象的ID为" + FieldUtil.getFieldValue(obj, "id"));
		} else {
			logVO.setContent("一个" + methodName.substring(6) + "对象，该对象的ID为" + obj);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("用户[");
		buffer.append(logVO.getUserName());
		buffer.append("]IP为[");
		buffer.append(logVO.getUserIP());
		buffer.append("]操作类型[");
		buffer.append(logVO.operation);
		buffer.append("]内容[");
		buffer.append(logVO.getContent());
		buffer.append("]");
		logger.info(buffer.toString());
	}

	public class LogVO {
		private Integer userId;// 操作人id
		private String userName;// 操作人id
		private String userIP;// 操作人id
		private Date createDate;// 日期
		private String content;// 日志内容
		private String operation;// 操作(主要是"添加"、"修改"、"删除")

		public LogVO() {
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserIP() {
			return userIP;
		}

		public void setUserIP(String userIP) {
			this.userIP = userIP;
		}

	}

}
