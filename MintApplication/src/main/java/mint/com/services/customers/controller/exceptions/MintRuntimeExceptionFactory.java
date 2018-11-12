package mint.com.services.customers.controller.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component()
public class MintRuntimeExceptionFactory {

	@Value("${controllers.HttpStatusResponse.404}")
	private String NOT_FOUND;
	@Value("${controllers.HttpStatusResponse.404.default}")
	private String DEFAULT_NOT_FOUND;
	@Value("${controllers.HttpStatusResponse.500.default}")
	private String SERVER_INTERNAL_ERROR;

	/**
	 * It creates an exception that has to be thrown by a rest controller an then
	 * the response will return a HTTP 404 code and as body a customized message with the
	 * type of resource and the not found message
	 * 
	 * @param resourceClass the type of the resource Eg. Customer. If null a generic
	 *                      message will be used for the 404 code
	 * @return the exception that once is thrown from a controller will be caught by
	 *         the RestResponseEntityExceptionHandler and will return the http
	 *         status code and body as defined in the MintRuntimeException
	 */
	public MintRuntimeException generateNotFoundResponse(Class resourceClass) {
		String reason = null != resourceClass ? String.format(NOT_FOUND, resourceClass.getSimpleName())
				: DEFAULT_NOT_FOUND;
		return new MintRuntimeException(HttpStatus.NOT_FOUND, reason);
	}

	/**
	 * It creates an exception that has to be thrown by a rest controller an then
	 * the response will return a HTTP 500 code and as body a customized message with the
	 * internal server error
	 * @param e The original exception 
	 * @param message The message corresponding to the body of the http response
	 *                with error code 500
	 * @return the exception that once is thrown from a controller will be caught by
	 *         the RestResponseEntityExceptionHandler and will return the http
	 *         status code and body as defined in the MintRuntimeException
	 */
	public MintRuntimeException generateServerInternalErrorResponse(Exception e, String message) {
		String reason = null != message? message : SERVER_INTERNAL_ERROR;
		return new MintRuntimeException(HttpStatus.NOT_FOUND, reason, e);
	}

	/**
	 * It creates an exception that has to be thrown by a rest controller an then
	 * the response will return a HTTP 500 code and as body a customized message with the
	 * default internal server error
	 * @param the original exception
	 * @return the exception that once is thrown from a controller will be caught by
	 *         the RestResponseEntityExceptionHandler and will return the http
	 *         status code and body as defined in the MintRuntimeException
	 */
	public MintRuntimeException generateServerInternalErrorResponse(Exception e) {
		String reason = SERVER_INTERNAL_ERROR;
		return new MintRuntimeException(HttpStatus.NOT_FOUND, reason, e);
	}
	
	/**
	 * It creates an exception that has to be thrown by a rest controller an then
	 * the response will return the code and the body as specified in the parameters
	 * @param status the HTTPStatus of the response
	 * @param reason the body of the response
	 * @return the exception that once is thrown from a controller will be caught by
	 *         the RestResponseEntityExceptionHandler and will return the http
	 *         status code and body as defined in the MintRuntimeException
	 */
	public MintRuntimeException generateCustomStatus(HttpStatus status, String reason) {
		return new MintRuntimeException(status, reason);
	}
	
}
