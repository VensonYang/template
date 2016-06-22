package controller.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a default implementation of ValidationAware. Returns new collections
 * for errors and messages (defensive copy).
 *
 * @author Jason Carreira
 * @author tm_jee
 * @version $Date$ $Id$
 */
@SuppressWarnings("serial")
public class ValidationAwareSupport implements ValidationAware, Serializable {

	private Map<String, String> fieldErrors;

	@Override
	public Map<String, String> getFieldError() {
		// TODO Auto-generated method stub
		return this.fieldErrors;
	}

	@Override
	public void addFieldError(String fieldName, String errorMessage) {
		// TODO Auto-generated method stub
		if (fieldErrors == null) {
			fieldErrors = new HashMap<String, String>();
		}
		fieldErrors.put(fieldName, errorMessage);
	}

	@Override
	public boolean hasFieldError() {
		// TODO Auto-generated method stub
		return this.fieldErrors == null || this.fieldErrors.isEmpty() ? false : true;
	}

}
