package utils.bean;

public class BeanDirectorFactory {
	private static IBeanDirector beanDirector;
	static {
		beanDirector = new BeanDirectorImpl();
	}

	public static IBeanDirector getBeanDirector() {
		return beanDirector;
	};
}
