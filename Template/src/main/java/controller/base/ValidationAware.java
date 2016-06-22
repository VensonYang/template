package controller.base;

import java.util.Map;

/**
 * ValidationAware classes can accept Action (class level) or field level error
 * messages. Action level messages are kept in a Collection. Field level error
 * messages are kept in a Map from String field name to a List of field error
 * msgs.
 *
 * @author plightbo
 */
public interface ValidationAware {

	/**
	 * Get the field specific errors associated with this action. Error messages
	 * should not be added directly here, as implementations are free to return
	 * a new Collection or an Unmodifiable Collection.
	 *
	 * @return Map with errors mapped from fieldname (String) to Collection of
	 *         String error messages
	 */
	Map<String, String> getFieldError();

	/**
	 * Add an error message for a given field.
	 *
	 * @param fieldName
	 *            name of field
	 * @param errorMessage
	 *            the error message
	 */
	void addFieldError(String fieldName, String errorMessage);

	/**
	 * Check whether there are any field errors associated with this action.
	 *
	 * @return whether there are any field errors
	 */
	boolean hasFieldError();

}
