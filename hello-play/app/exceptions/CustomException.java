package exceptions;

public class CustomException extends RuntimeException {
	private static final long serialVersionUID = -4012227895045616081L;
	int status;
	String type;
	Object entityClass;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String msg) {
		this.type = msg;
	}

	public Object getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Object entityClass) {
		this.entityClass = entityClass;
	}

	public CustomException(final String message) {
		super(message);
	}

	public CustomException(String message, final Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message, final Throwable cause, int status, String type, Object className) {
		super(message, cause);
		this.status = status;
		this.type = type;
		this.entityClass = className;
	}

	public CustomException(String message, final Throwable cause, int status, String type, Object className,
			String token) {
		super(message, cause);
		this.status = status;
		this.type = type;
		this.entityClass = className;
	}
}
