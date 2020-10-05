/**
 * 
 */
package egovframework.com.asapro.support.exception;

/**
 * ASAPRO 공통예외
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@SuppressWarnings("serial")
public class AsaproException extends RuntimeException {
	
	// back : history.back();
	// url : 해당 주소로 이동
	protected String locationHref = null;
	
	public AsaproException() {
		super();
	}

	public AsaproException(String message, Throwable cause) {
		super(message, cause);
	}

	public AsaproException(String message) {
		super(message);
	}
	
	/**
	 * alert 메세지를 출력하고 스크립트로 location을 이동시킨다.
	 * <pre>
	 * - location.href 가 'back' 이면 history.back() 이 실행된다.
	 * </pre>
	 * @param message
	 * @param locationHref
	 */
	public AsaproException(String message, String locationHref) {
		super(message);
		this.locationHref = locationHref;
	}

	public AsaproException(Throwable cause) {
		super(cause);
	}

	/**
	 * @return the locationHref
	 */
	public String getLocationHref() {
		return locationHref;
	}

	/**
	 * @param locationHref the locationHref to set
	 */
	public void setLocationHref(String locationHref) {
		this.locationHref = locationHref;
	}

}
