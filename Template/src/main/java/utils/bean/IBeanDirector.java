package utils.bean;

import controller.base.ValidationAware;

public interface IBeanDirector {

	<T> T getDataVO(Class<T> paramClass, ValidationAware paramValidationAware);

	<T> T getDataVO(Class<T> paramClass);

	<T, K> T getDataVO(Class<T> paramClass, ValidationAware paramValidationAware, Class<K> paramClass1);
}