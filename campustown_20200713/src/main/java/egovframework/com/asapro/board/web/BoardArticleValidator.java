/**
 * 
 */
package egovframework.com.asapro.board.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.support.exception.AsaproException;
//import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 게시물 검증
 * @author yckim
 * @since 2018. 6. 7.
 *
 */
@Component
public class BoardArticleValidator implements AsaproValidator{
	
	@Autowired
	private BoardService boardService;
	
//	@Autowired
//	private ConfigService configService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(BoardArticle.class);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		BoardArticle boardArticle = (BoardArticle) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baTitle", null, "제목은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baContentHtml", null, "내용은 필수입력입니다.");
		
		//비회원 입력시 이름, 비밀번호 체크
		if( "ROLE_GUEST".equals(boardArticle.getMember().getMemberRole().getRoleCode()) ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baGuestName", null, "비회원 작성시 작성자 이름은 필수입력입니다.");
			//if( boardArticle.getBaId() == null || boardArticle.getBaId() == 0 ){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baGuestPassword", null, "비회원 글 작성시 본인확인용 비밀번호는 필수입력입니다.");
			//}
			//비밀번호 길이체크
			if( StringUtils.isNotBlank(boardArticle.getBaGuestPassword()) ){
				if( boardArticle.getBaGuestPassword().length() < 4 || boardArticle.getBaGuestPassword().length() > 20 ){
					errors.rejectValue("baGuestPassword", null, "비밀번호는 4자 이상, 20자 이하여야 합니다.");
				}
			}
		}
		
		//비밀글 작성시 비밀번호 입력 체크
		if( boardArticle.getBoardConfig().isBcSupportSecret() ){
			if( boardArticle.getBaSecret() ){
				//비밀번호 입력체크
				if( boardArticle.getBaId() == null || boardArticle.getBaId() == 0 ){
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baSecretPassword", null, "비밀글 작성시 비밀글조회용 비밀번호는 필수입력입니다.");
				}
				//비밀번호 길이체크
				if( StringUtils.isNotBlank(boardArticle.getBaSecretPassword()) ){
					if( boardArticle.getBaSecretPassword().length() < 4 || boardArticle.getBaSecretPassword().length() > 20 ){
						errors.rejectValue("baSecretPassword", null, "비밀번호는 4자 이상, 20자 이하여야 합니다.");
					}
				}
			}
		}
		
		//이메일 형식 체크
		if( StringUtils.isNotBlank(boardArticle.getBaEmail()) ){
			if( !AsaproUtils.isEmail(boardArticle.getBaEmail()) ){
				errors.rejectValue("baEmail", null, "이메일을 형식에 맞게 입력해 주세요.");
			}
		}
		
		//관리자, 게시판 관리자만 공지글 작성 가능
		if( !boardService.isBoardManager(boardArticle.getBoardConfig(), boardArticle.getMember()) ){
			if( boardArticle.getBaNotice() ){
				errors.rejectValue("baNotice", null, "공지글은 관리자만 작성할 수 있습니다.");
			}
		}

		
/*
 //종료일만 입력으로 변경
		if( boardArticle.getBaNoticeStartDate() != null && boardArticle.getBaNoticeEndDate() != null ){
			Date startDate = AsaproUtils.getTimeChangedDate(boardArticle.getBaNoticeStartDate(), 0, 0, 0);
			Date endDate = AsaproUtils.getTimeChangedDate(boardArticle.getBaNoticeEndDate(), 0, 0, 0);
			if( startDate.after(endDate) ){
				errors.rejectValue("baNoticeStartDate", null, "시작일은 종료일 이전이어야 합니다.");
				errors.rejectValue("baNoticeEndDate", null, "종료일은 시작일 이후여야 합니다.");
			}
		}
*/
		if(boardArticle.getBaNotice() ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baNoticeEndDate", null, "종료일은 필수입력입니다.");
		}
		
		//비속어 검사
/*
		List<String> slangList = new ArrayList<String>(); 
		List<String> catchList = new ArrayList<String>();
		String wordFilter = configService.getTextData("word_filter", "word_filter").getTextContent();
		if( StringUtils.isNotEmpty( wordFilter ) ){
			slangList.addAll(Arrays.asList(wordFilter.split(";")));
		}
		for( String slang : slangList  ){
			if( StringUtils.isBlank(slang) ){
				continue;
			}
			if( boardArticle.getBaContentHtml().contains(slang) ){
				catchList.add(slang);
			}
		}
		if( !catchList.isEmpty() ){
			StringBuilder sb = new StringBuilder(20);
			int idx = 0;
			for( String str : catchList ){
				if(idx > 0){
					sb.append(", ");
				}
				sb.append(str);
				idx ++;
			}
			errors.rejectValue("baContentHtml", null, "사용금지어 또는 비속어가 포함되어 있습니다. [" + sb.toString() + "]");
		}
*/
		
		//대표이미지 대체텍스트 체크
//		if( boardArticle.getBoardConfig().isBcSupportThumb() ){
			if( boardArticle.getBaThumbFile() != null && boardArticle.getBaThumbFile().getSize() > 0 ){
				//대체텍스트 입력체크
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baThumbText", null, "대체텍스트는 필수입력입니다.");
			}
//		}
		
	}

}
