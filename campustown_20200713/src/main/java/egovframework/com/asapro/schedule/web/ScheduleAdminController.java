/**
 * 
 */
package egovframework.com.asapro.schedule.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;


/**
 * 일정 관리자 컨트롤러
 * @author yckim
 * @since 2018. 5. 17.
 *
 */
@Controller
public class ScheduleAdminController {
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 달력을 반환한다.
	 * @param model
	 * @param calendarSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/schedule/calendar.do", method=RequestMethod.GET)
	public String monthCalendarGet(Model model, @CurrentSite Site currentSite){
		
		
		return "asapro/admin/schedule/calendar";
	};
	

}