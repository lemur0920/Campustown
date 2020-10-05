/**
 * 
 */
package egovframework.com.asapro.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 아카이브를 상속한 커스텀 타입을 만들때 모델에 추가해주어야 한다.
 * <br>관리자 > 분류체계 기능에 자동으로 아카이브 타입이 추가된다.
 * @author yckim
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArchiveItem {
	
	//String customType() default "archive";
	String customType();
	String label();
}
