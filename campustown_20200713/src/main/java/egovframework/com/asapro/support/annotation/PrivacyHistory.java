/**
 * 
 */
package egovframework.com.asapro.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 개인정보 조회로그 해당 메소드에 추가해준다.
 * <br> 기입한 모듈종류와 내역을 기입하기위해 사용.
 * @author yckim
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivacyHistory {
	
	String moduleType();
	String history();
}
