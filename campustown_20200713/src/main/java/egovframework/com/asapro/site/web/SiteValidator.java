/**
 * 
 */
package egovframework.com.asapro.site.web;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.cmm.service.EgovProperties;

/**
 * 사이트 폼 검사
 * @author yckim
 * @since 2018.09.11
 *
 */
@Component
public class SiteValidator implements AsaproValidator{
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Site.class);
	}

	@Override
	public void validate(Object object, Errors error) {
		throw new AsaproException("not this!");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		Site site = (Site) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siteId", null, "사이트 아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siteName", null, "사이트 이름은 필수입력입니다.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siteTheme", null, "사이트 테마는 필수선택입니다.");
		
		if( "INSERT".equals(formMode) ){
			if( StringUtils.isNotBlank(site.getSiteId()) ){
				if( !site.getSiteId().matches("[a-zA-Z0-9]+")  ){
					errors.rejectValue("siteId", null, "사이트 아이디는 영문과 숫자만 사용할 수 있습니다.");
				} 
				if( site.getSiteId().length() < 3 || site.getSiteId().length() > 10 ){
					errors.rejectValue("siteId", null, "사이트 아이디는 최소 3자 이상, 최대 10자 이하여야 합니다.");
				}
				
				String webRoot = AsaproUtils.getWebRoot(request);
				String uploadFolderPath = "";
				
				//EgovProperties.getProperty("Globals.fileStorePath")
				//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
				String fileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
				if(StringUtils.isNotBlank(fileStorePath)){
					uploadFolderPath = StringUtils.removeEnd(fileStorePath, "/") + Constant.UPLOAD_PATH;
				}else{
					uploadFolderPath = StringUtils.removeEnd(webRoot, "/") + Constant.UPLOAD_PATH;
				}
				
				File uploadDir = new File(uploadFolderPath);
				File[] uploadDirs = uploadDir.listFiles(new FileFilter() {
					@Override
					public boolean accept(File file) {
						return file.isDirectory() && !file.getName().startsWith(".");
					}
				});
				if(uploadDirs != null && uploadDirs.length > 0){
					List<String> dirList = new ArrayList<String>();
					for( File f : uploadDirs ){
						dirList.add(f.getName());
					}
					for( String dir : dirList ){
						if( dir.equals(site.getSiteId()) ){
							errors.rejectValue("siteId", null, "업로드 폴더에 동일한 이름의 폴더가 존재할 경우 충돌 방지를 위해 아이디 사용을 제한합니다. 폴더이름을 변경하거나 다른 아이디를 사용해 주세요.");
						}
					}
				}
			}
			//Site fromDB = siteService.getSite(site);
			Site fromDB = siteService.getSite(site.getSiteId());
			if( fromDB != null ){
				errors.rejectValue("siteId", null, "이미 사용중인 사이트 아이디입니다.");
			}
			
			if( site.getSiteLogoImage() == null || site.getSiteLogoImage().isEmpty() ){
				errors.rejectValue("siteLogoImage", null, "로고이미지는 필수입력입니다.");
			}
		}
		
		if( "UPDATE".equals(formMode) ){
			//Site fromDB = siteService.getSite(site);
			Site fromDB = siteService.getSite(site.getSiteId());
			if(fromDB.isSiteMain() ){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siteDomain", null, "메인 사이트 도메인은 필수입력입니다.");
			}
		}
	}

}