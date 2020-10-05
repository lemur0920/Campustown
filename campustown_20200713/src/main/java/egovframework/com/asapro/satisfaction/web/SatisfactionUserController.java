/**
 * 
 */
package egovframework.com.asapro.satisfaction.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.asapro.satisfaction.service.Satisfaction;
import egovframework.com.asapro.satisfaction.service.SatisfactionService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 만족도 조사 사용자 컨트롤러
 * @author yckim	
 * @since 2019. 4. 15.
 *
 */
@Controller
public class SatisfactionUserController {
	
	@Autowired
	private SatisfactionValidator satisfactionValidator;
	
	@Autowired
	private SatisfactionService satisfactionService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 만족도 평가를 입력한다.
	 * (평가당 하나의 컬럼으로 관리하지 않고 해당 메뉴의 만족도를 카운트를 증가하는 방식이다.
	 * 	단, 주관식 평가의견은 별도 레코드를 추가한다.  
	 * 	기능대비 데이터의 증가이슈를 고려한 방식이다.)
	 * @param model
	 * @param satisfaction
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/satisfaction" + Constant.API_PATH + "/evaluate", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage evaluateSatisfactionPost(HttpServletResponse response, Model model, @CurrentSite Site currentSite, @ModelAttribute Satisfaction satisfaction, BindingResult bindingResult){
		
		ServerMessage serverMessage = new ServerMessage();
		satisfaction.setSitePrefix(currentSite.getSitePrefix());
		Cookie satisCookie = AsaproUtils.getCookie(request.getCookies(), "satis_" + currentSite.getSiteId() + "_" + satisfaction.getMenuId());
		//쿠키 있으면 이미 평가 한 메뉴로 본다
		if( satisCookie != null ){
			serverMessage.setSuccess(false);
			serverMessage.setText("이미 평가하셨습니다.");
		} else {
			satisfactionValidator.validate(satisfaction, bindingResult, "UPDATE");
			if( bindingResult.hasErrors() ){
				serverMessage.setSuccess(false);
				serverMessage.setText(AsaproUtils.getAllFieldErrorMessage(bindingResult,", "));
			} else {
				if( satisfactionService.updateSatisfaction(satisfaction) > 0 ){
					serverMessage.setSuccess(true);
					
					//쿠키에 저장
					Cookie newCookie = new Cookie("satis_" + currentSite.getSiteId() + "_" + satisfaction.getMenuId(), System.currentTimeMillis() + "");
					//newCookie.setDomain(configService.getConfig("global").getOption("cookie_domain"));
					//newCookie.setDomain(currentSite.getSiteDomain());
					//newCookie.setDomain(request.getServerName());
					newCookie.setPath("/");
					newCookie.setHttpOnly(true);
					int t = 60 * 60 * 24 * 365;//1년
					if(t > 31536000) {
						t = 31536000;
					}
					newCookie.setMaxAge(t);
					response.addCookie(newCookie);
				} else {
					serverMessage.setSuccess(false);
					serverMessage.setText("server error occured.");
				}
			}
		}
		
		return serverMessage;
	}
}
