/**
 * 
 */
package egovframework.com.asapro.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 스프링 빈을 DI할 수 없는 상황 일 경우
 * 이 클래스의 static 메서드를 이용해 직접 application context를 구해서 사용한다.
 * <pre>
 * - 1)java단에서의 사용예
 * - CryptoWorker worker = ApplicationContextProvider.getApplicationContext().getBean(CryptoWorker.class);
 * - 2) jsp 에서 빈이 필요할때
 * - ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
 * - MenuService menuService = applicationContext.getBean(MenuService.class);
 * </pre>
 * @author yckim
 * @since 2013. 7. 2.
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	/**
	 * @return the applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;	
	}

}
