/**
 * 
 */
package egovframework.com.asapro.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 컨트롤러에서 404 던질때.
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
public class AsaproNotFoundException extends AsaproException{

	public AsaproNotFoundException() {
		super();
	}

	public AsaproNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AsaproNotFoundException(String message) {
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
	public AsaproNotFoundException(String message, String locationHref) {
		super(message);
		this.locationHref = locationHref;
	}

	public AsaproNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
