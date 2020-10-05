package egovframework.com.campustown.startHpMngr.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;


/**
 * 창업팀 홈페이지 관리
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Component
public class StartHpMngrValidator implements AsaproValidator{
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(StartHpMngr.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		StartHpMngr activeIdxForm = (StartHpMngr)target;
		
		//startHpMngrValidator.validate(startHpMngrEmpssForm, bindingResult, "EMP_INSERT");
		//startHpMngrValidator.validate(activeIdxForm, bindingResult, "ACT_IDX_INSERT");
		// 창업활동지수 등록 폼
		/*
		 SALE_AMT : 매출 금액
		INVT_AMT  : 투자 금액
		EMPLY_CNT : 고용인 수
		INTELL_PROP: 지적 재산
		*/
		if("ACT_IDX_INSERT".equalsIgnoreCase(formMode)){
			
			// 매출금액
			if( !StringUtils.isNumeric(activeIdxForm.getSaleAmt()) ){
				errors.rejectValue("saleAmt", null, "매출금액은 숫자만 입력가능합니다.");
			}
			// 투자금액
			if( !StringUtils.isNumeric(activeIdxForm.getInvtAmt()) ){
				errors.rejectValue("invtAmt", null, "투자금액은 숫자만 입력가능합니다.");
			}
			// 고용인수
			if( !StringUtils.isNumeric(activeIdxForm.getEmplyCnt()) ){
				errors.rejectValue("emplyCnt", null, "고용인수는 숫자만 입력가능합니다.");
			}
			// 지적재산
			if( !StringUtils.isNumeric(activeIdxForm.getIntellProp()) ){
				errors.rejectValue("intellProp", null, "지적재산은 숫자만 입력가능합니다.");
			}
			
			
			
			
			
			
			
		}
		
		
		
	}
	

}
