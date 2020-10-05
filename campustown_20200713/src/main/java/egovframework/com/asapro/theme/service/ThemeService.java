/**
 * 
 */
package egovframework.com.asapro.theme.service;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 테마 서비스
 * @author yckim
 * @since 2018. 9. 13.
 *
 */
public interface ThemeService {
	
	/**
	 * 테마목록을 조회한다.
	 * @param webRoot
	 * @return 테마목록
	 * @throws FileNotFoundException 
	 */
	public List<String> getThemeList() throws FileNotFoundException;
	
}
