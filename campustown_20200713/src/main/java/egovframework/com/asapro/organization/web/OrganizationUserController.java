/**
 * 
 */
package egovframework.com.asapro.organization.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.organization.service.StaffSearch;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 조직관리 사용자 컨트롤러
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
@Controller
public class OrganizationUserController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private CodeService codeService;
	

}
