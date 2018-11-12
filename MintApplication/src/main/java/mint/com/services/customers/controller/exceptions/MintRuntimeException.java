package mint.com.services.customers.controller.exceptions;

import org.springframework.http.HttpStatus;

public class MintRuntimeException extends RuntimeException {
	private Exception originalException;
	HttpStatus status;
	String reason;

	public MintRuntimeException(HttpStatus status, String reason) {
		super();
		this.status = status;
		this.reason = reason;
	}
	public MintRuntimeException(HttpStatus status, String reason, Exception e) {
		super(e);
		this.status = status;
		this.reason = reason;
		this.originalException = e;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
