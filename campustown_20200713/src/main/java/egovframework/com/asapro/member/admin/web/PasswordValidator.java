/**
 * 
 */
package egovframework.com.asapro.member.admin.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.member.admin.service.AdminPassword;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 비밀번호 변경폼 검증
 * @author yckim
 * @since 2018. 5. 8.
 *
 */
@Component
public class PasswordValidator implements AsaproValidator{

	@Autowired
	private AdminMemberService adminMemberService;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AdminPassword.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		AdminPassword adminPasswordForm = (AdminPassword)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "validation.member.passwordUpdate.newPassword.blank", "새 비밀번호를 입력해 주세요.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswordCheck", "validation.member.passwordUpdate.confirmPassword.blank", "새 비밀번호 확인을 입력해 주세요.");
		
		//비밀번호 확인값 체크
		if( StringUtils.isNotBlank(adminPasswordForm.getNewPassword()) ){
			if( !StringUtils.isAsciiPrintable(adminPasswordForm.getNewPassword())  ){
				errors.rejectValue("newPassword", "validation.member.adminPassword.char", "비밀번호는 영문, 숫자, 특수문자만 사용할 수 있습니다.");
			} 
			if( adminPasswordForm.getNewPassword().length() < 9 || adminPasswordForm.getNewPassword().length() > 20 ){
				errors.rejectValue("newPassword", "validation.member.adminPassword.length", "비밀번호 길이는 최소 9자 이상, 최대 20자 이하여야 합니다.");
			} 
			if( StringUtils.containsWhitespace(adminPasswordForm.getNewPassword()) ){
				errors.rejectValue("newPassword", null, "공백은 포함할 수 없습니다.");
			}
			if( StringUtils.containsNone(adminPasswordForm.getNewPassword(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ") 
					|| StringUtils.containsNone(adminPasswordForm.getNewPassword(), "abcdefghijklmnopqrstuvwxyz") 
					|| StringUtils.containsNone(adminPasswordForm.getNewPassword(), "0123456789") 
					|| StringUtils.containsNone(adminPasswordForm.getNewPassword(), "`~!@#$%^&*()_+-=[]{}\\;:,<.>/?|'\"")
					){
				errors.rejectValue("newPassword", null, "비밀번호는 영문소문자, 영문대문자, 숫자, 특수문자가 1개이상씩 포함되어야 합니다.");
			}
			//반복된 3자리 이상의 문자나 숫자
			Pattern p1 = Pattern.compile("(\\w)\\1\\1");
			Matcher m1 = p1.matcher(adminPasswordForm.getNewPassword());
			Pattern p2 = Pattern.compile("([\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"])\\1\\1");
			Matcher m2 = p2.matcher(adminPasswordForm.getNewPassword());
			
			if(m1.find() || m2.find()){
				errors.rejectValue("newPassword", "validation.member.adminPassword.length", "비밀번호에 3자리 이상의 같은 문자나 숫자가 포함되어 있습니다.");
			}
			
			 // 연속된 3자리 이상의 문자나 숫자
	        String listThreeChar = "abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz|012|123|234|345|456|567|678|789|890";
	        String[] arrThreeChar = listThreeChar.split("\\|");
	        for (int i=0; i<arrThreeChar.length; i++) {
	            if(adminPasswordForm.getNewPassword().toLowerCase().matches(".*" + arrThreeChar[i] + ".*")) {
	            	errors.rejectValue("newPassword", "validation.member.adminPassword.length", "비밀번호에 연속된 3자리 이상의 문자나 숫자가 포함되어 있습니다.");
	            }
	        }

	        // 연속된 3자리 이상의 키보드 문자
	        String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
	        String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
	        for (int j=0; j<arrKeyboardThreeChar.length; j++) {
	            if(adminPasswordForm.getNewPassword().toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
	            	errors.rejectValue("newPassword", "validation.member.adminPassword.length", "비밀번호에 연속된 3자리 이상의 키보드 문자가 포함되어 있습니다.");
	            }
	        }
		}
		
		if( StringUtils.isNotBlank(adminPasswordForm.getNewPassword()) && StringUtils.isNotBlank(adminPasswordForm.getNewPasswordCheck()) ){
			if( !adminPasswordForm.getNewPassword().equals(adminPasswordForm.getNewPasswordCheck()) ){
				errors.rejectValue("newPasswordCheck", "validation.member.adminPasswordCheck.notMatch", "새 비밀번호와 새 비밀번호 확인이 서로 일치 하지 않습니다.");
			}
		}
		
	}
}
