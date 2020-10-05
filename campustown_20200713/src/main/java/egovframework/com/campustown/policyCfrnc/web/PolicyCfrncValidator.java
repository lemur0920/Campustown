package egovframework.com.campustown.policyCfrnc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrnc;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrncService;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;

/**
 * 정책협의회 관리
 * @author jaseo
 * @since 2020. 04. 26.
 *
 */
@Component
public class PolicyCfrncValidator implements AsaproValidator{
	
	@Autowired
	private PolicyCfrncService policyCfrncService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(PolicyCfrnc.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
		
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
	}
	
	
	
	

}
