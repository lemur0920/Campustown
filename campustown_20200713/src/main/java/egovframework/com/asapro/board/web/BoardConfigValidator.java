/**
 * 
 */
package egovframework.com.asapro.board.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 게시판 설정VO 검증
 * @author yckim
 * @since 2018. 5. 31.
 *
 */
@Component
public class BoardConfigValidator implements AsaproValidator {

	@Autowired
	private BoardService boardService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(BoardConfig.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		BoardConfig boardConfigForm = (BoardConfig)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bcId", null, "게시판 아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bcName", null, "게시판 이름은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bcType", null, "게시판 유형은 필수선택입니다.");
		
		if(boardConfigForm.getBcCustomFieldUseArray() != null && boardConfigForm.getBcCustomFieldUseArray().size() > 0){
			int arraySize = boardConfigForm.getBcCustomFieldUseArray().size();
			for (int i = 1; i <= arraySize; i++) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bcCustomField"+i, null, "필드명은 필수입력 입니다.");
			}
		}
		
		if( "INSERT".equals(formMode) ){
			//게시판 아이디 중복 체크
			if( StringUtils.isNotBlank(boardConfigForm.getBcId()) ){
				BoardConfig fromDB = boardService.getBoardConfig(boardConfigForm);
				if( fromDB != null ){
					errors.rejectValue("bcId", null, "사용중인 게시판 아이디입니다.");
				}
				if( !boardConfigForm.getBcId().matches("[0-9a-z_]*") ){
					errors.rejectValue("bcId", null, "게시판아이디는 영어소문자, 숫자 그리고 언더바(_)만 사용가능 합니다.");
				}
				if( boardConfigForm.getBcId().length() < 4 || boardConfigForm.getBcId().length() > 25 ){
					errors.rejectValue("bcId", null, "길이는 4자 이상, 25자 이하여야 합니다");
				}
			}
		}
	}

}
