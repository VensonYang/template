package utils.bean;

import controller.base.ValidationAware;

public class BeanDirectorImpl implements IBeanDirector {

	private IBeanBuilder beanBuilder = new BeanBuilderImpl();

	@Override
	public <T> T getDataVO(Class<T> dataVO, ValidationAware va) {
		return beanBuilder.buildVO(dataVO, va);
	}

	@Override
	public <T, K> T getDataVO(Class<T> dataVO, ValidationAware va, Class<K> validGroup) {
		return beanBuilder.buildVO(dataVO, va, validGroup);
	}

	@Override
	public <T> T getDataVO(Class<T> dataVO) {
		// TODO Auto-generated method stub
		return beanBuilder.buildVO(dataVO);
	}

}
