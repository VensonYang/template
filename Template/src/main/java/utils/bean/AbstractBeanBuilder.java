package utils.bean;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import controller.base.ControllerContext;
import controller.base.ValidationAware;

public abstract class AbstractBeanBuilder implements IBeanBuilder {

	private static final Logger logger = LoggerFactory.getLogger(AbstractBeanBuilder.class);

	private static Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public <T> T buildVO(Class<T> vo, ValidationAware va) {
		return process(vo, va, null);
	}

	public <T> T buildVO(Class<T> vo) {
		return process(vo, null, null);
	}

	@Override
	public <T, K> T buildVO(Class<T> vo, ValidationAware va, Class<K> validGroup) {
		return process(vo, va, validGroup);
	}

	private <T, K> T process(Class<T> vo, ValidationAware va, Class<K> validGroup) {
		try {
			T dataResult = vo.newInstance();
			Field[] fields = vo.getDeclaredFields();
			HttpServletRequest request = ControllerContext.getRequest();
			for (Field field : fields) {
				ReflectionUtils.makeAccessible(field);
				// String
				// paramValue=ServletActionContext.getRequest().getParameter(field.getName());
				String[] paramValueArray = request.getParameterValues(field.getName());
				if (paramValueArray != null) {
					if (field.getType().equals(String[].class)) {
						ReflectionUtils.setField(field, dataResult, paramValueArray);
					} else if (field.getType().equals(Character[].class)) {
						Character[] data = new Character[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							data[i] = paramValueArray[i].toCharArray()[0];
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Boolean[].class)) {
						boolean[] data = new boolean[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNotBlank(paramValueArray[i])) {
								data[i] = Boolean.parseBoolean(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(boolean[].class)) {
						boolean[] data = new boolean[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNotBlank(paramValueArray[i])) {
								data[i] = Boolean.parseBoolean(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(int[].class)) {
						int[] data = new int[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Integer.parseInt(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(float[].class)) {
						float[] data = new float[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Float.parseFloat(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Date[].class)) {
						Date[] data = new Date[paramValueArray.length];
						SimpleDateFormat sdf;
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.trim(paramValueArray[i]).length() > 10) {
								sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							} else {
								sdf = new SimpleDateFormat("yyyy-MM-dd");
							}
							try {
								data[i] = sdf.parse(paramValueArray[i]);
							} catch (ParseException e) {
								logger.debug(e.toString());
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Integer[].class)) {
						Integer[] data = new Integer[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Integer(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Float[].class)) {
						Float[] data = new Float[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Float(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Byte[].class)) {
						Byte[] data = new Byte[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Byte(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(byte[].class)) {
						byte[] data = new byte[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Byte.parseByte(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Short[].class)) {
						Short[] data = new Short[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Short(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(short[].class)) {
						short[] data = new short[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Short.parseShort(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Long[].class)) {
						Long[] data = new Long[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Long(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(long[].class)) {
						long[] data = new long[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Long.parseLong(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Double[].class)) {
						Double[] data = new Double[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Double(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(double[].class)) {
						double[] data = new double[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Double.parseDouble(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(String.class)) {
						if (null != paramValueArray[0]) {
							ReflectionUtils.setField(field, dataResult, paramValueArray[0]);
						}
					} else if (field.getType().equals(Character.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							char[] data = paramValueArray[0].toCharArray();
							ReflectionUtils.setField(field, dataResult, data[0]);
						}
					} else if (field.getType().equals(int.class)) {
						if (StringUtils.isNumeric(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Integer.parseInt(paramValueArray[0]));
						}
					} else if (field.getType().equals(Integer.class)) {
						if (StringUtils.isNumeric(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Integer.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(float.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Float.parseFloat(paramValueArray[0]));
						}
					} else if (field.getType().equals(Float.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Float.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(Date.class)) {
						SimpleDateFormat sdf;
						if (StringUtils.trim(paramValueArray[0]).length() > 10) {
							sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						} else {
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						}
						try {
							ReflectionUtils.setField(field, dataResult, sdf.parse(paramValueArray[0]));
						} catch (ParseException e) {
							logger.debug(e.toString());
						}
					} else if (field.getType().equals(Byte.class)) {
						if (StringUtils.isNumeric(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Byte.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(byte.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Byte.parseByte(paramValueArray[0]));
						}
					} else if (field.getType().equals(Short.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Short.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(short.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Short.parseShort(paramValueArray[0]));
						}
					} else if (field.getType().equals(Long.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Long.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(long.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Long.parseLong(paramValueArray[0]));
						}
					} else if (field.getType().equals(Double.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Double.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(double.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Double.parseDouble(paramValueArray[0]));
						}
					} else if (field.getType().equals(Boolean.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Boolean.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(boolean.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Boolean.parseBoolean(paramValueArray[0]));
						}
					}

				}
			}
			if (va != null) {
				validateData(dataResult, va, validGroup);
			}
			return dataResult;
		} catch (InstantiationException e) {
			logger.debug(e.toString());
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			logger.debug(e1.toString());
		}
		return null;
	}

	private <T> void validateData(Object dataObj, ValidationAware va, Class<T> validGroup) {
		Set<ConstraintViolation<Object>> constraintViolations = null;
		if (validGroup != null) {
			constraintViolations = validator.validate(dataObj, validGroup);
		} else {
			constraintViolations = validator.validate(dataObj);
		}
		for (ConstraintViolation<Object> cv : constraintViolations) {
			for (Node node : cv.getPropertyPath()) {
				va.addFieldError(node.getName(), cv.getMessage());
				return;
			}
		}
		// Iterator<ConstraintViolation<Object>> iterator =
		// constraintViolations.iterator();
		// if (iterator.hasNext()) {
		// ConstraintViolation<Object> cv = iterator.next();
		// Iterator<Node> nodeIterator = cv.getPropertyPath().iterator();
		// if (nodeIterator.hasNext()) {
		// Node node = nodeIterator.next();
		// va.addFieldError(node.getName(), cv.getMessage());
		// }
		// }
	}
}
