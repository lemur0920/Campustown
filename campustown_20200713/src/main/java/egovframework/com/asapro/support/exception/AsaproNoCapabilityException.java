/**
 * 
 */
package egovframework.com.asapro.support.exception;

/**
 * 권한없을때 던지는 예외
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@SuppressWarnings("serial")
public class AsaproNoCapabilityException extends AsaproException{

	public AsaproNoCapabilityException() {
		super();
	}

	public AsaproNoCapabilityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AsaproNoCapabilityException(String message) {
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
	public AsaproNoCapabilityException(String message, String locationHref) {
		super(message);
		this.locationHref = locationHref;
	}

	public AsaproNoCapabilityException(Throwable cause) {
		super(cause);
	}

}
