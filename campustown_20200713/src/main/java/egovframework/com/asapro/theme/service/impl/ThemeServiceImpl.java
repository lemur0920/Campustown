/**
 * 
 */
package egovframework.com.asapro.theme.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.theme.service.ThemeService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 테마서비스 구현
 * @author yckim
 * @since 2018. 9. 13.
 *
 */
@Service
public class ThemeServiceImpl extends EgovAbstractServiceImpl implements ThemeService{

	@Autowired
	private HttpServletRequest request;
	
	//테마 목록 => /design/theme/{테마목록}
	@Override
	public List<String> getThemeList() {
		
		String webRoot = AsaproUtils.getWebRoot(request);
		
		File themeDir = new File(webRoot + "/design/theme");
		
		File[] themeDirs = themeDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() && !file.getName().startsWith(".");
			}
		});
		
		List<String> themeList = new ArrayList<String>();
		for( File f : themeDirs ){
			themeList.add(f.getName());
		}
		
		Collections.sort(themeList, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if(s1 != null && s2 != null){
					if( "default".equals(s1) ){
						return -1;
					} else {
						return 1; 
					}
				} else {
					return -1;
				}
			}
		});
		
		return themeList;
	}

}
