package egovframework.com.campustown.univHpMngr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngr;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 대학 홈페이지 관리
 * @author jaseo
 * @since 2020. 02. 12.
 */
@Component
public class UnivHpMngrValidator implements AsaproValidator{
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(UnivHpMngr.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
		
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
	}
	
}
