/**
 * 
 */
package egovframework.com.asapro.support.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 입력폼인지, 수정폼인지(혹은 기타 구분자) 구분해서 처리할 수 있도록 오버로딩 메서드를 추가한 Validator 인터페이스를 상속한 인터페이스
 * <pre>
 * - 데이터의 ID값이 자동 증가되는 seq값이 아니라 문자열값인 경우 insert 인지 update인지 구분하기 위한 용도로 사용됨.
 * </pre>
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
public interface AsaproValidator extends Validator{
	
	/**
	 * 입력폼인지, 수정폼인지 구분해서 처리할 수 있도록 추가한 메서드
	 * @param target
	 * @param errors
	 * @param formMode
	 */
	public void validate(Object target, Errors errors, String formMode);
	
}
