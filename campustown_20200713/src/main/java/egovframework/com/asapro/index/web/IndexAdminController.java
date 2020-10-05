/**
 * 
 */
package egovframework.com.asapro.index.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.Constant;







/**
 * 관리자 메인, 로그인 컨트롤러
 * @author yckim
 * @since 2018. 3. 30.
 *
 */
@Controller
public class IndexAdminController {
	
	/**
	 * 관리자 주소
	 * @param model
	 * @param loginForm
	 * @return 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + ".do", method=RequestMethod.GET)
	public String adminShortGet(@CurrentSite Site currentSite){
		return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/login.do";
	}
}
