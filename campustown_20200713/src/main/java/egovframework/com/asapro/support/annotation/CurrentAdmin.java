/**
 * 
 */
package egovframework.com.asapro.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨트롤러 메서드의 파라메터 중 에서 이 어노테이션이 붙은 AdminMember 객체는
 * 현재 관리자의 정보를 담고 있다.
 * <pre>
 * -컨트롤러의 파라메터에만 적용된다 
 * -CustomHandlerMethodArgumentResolver 에서 세션에 저장되어 있는 
 * 현재 로그인관리자 정보를 AdminMember 인스턴스에 집어넣은 후에 컨트롤러로 넘김 
 * </pre> 
 * @author yckim
 * @since 2018. 5. 14.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface CurrentAdmin {

}
