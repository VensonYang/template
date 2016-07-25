package utils.bean;

import controller.base.ValidationAware;

public interface IBeanBuilder {
	<T> T buildVO(Class<T> vo, ValidationAware va);

	<T> T buildVO(Class<T> vo);

	<T, K> T buildVO(Class<T> vo, ValidationAware va, Class<K> validGroup);
}
