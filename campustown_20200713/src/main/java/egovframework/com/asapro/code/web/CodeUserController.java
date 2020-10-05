/**
 * 
 */
package egovframework.com.asapro.code.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.support.Constant;

/**
 * 코드 사용자 컨트롤러
 * 
 * @author yckim
 * @since 2018. 4. 17.
 */
@Controller
public class CodeUserController {
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 코드 목록을 json 으로 반환한다.
	 * @param model
	 * @param catId
	 * @return 코드 목록 json
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/code" + Constant.API_PATH + "/item/jsonList", method=RequestMethod.GET)
	@ResponseBody
	public List<Code> codeJsonListGet(Model model, @RequestParam(value="catId", required=true) String catId){
		List<Code> codeList = codeService.getCodeList(catId);
		return codeList;
	}
	
}
