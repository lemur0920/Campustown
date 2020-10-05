/**
 * 
 */
package egovframework.com.asapro.comment.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.comment.service.Comment;
import egovframework.com.asapro.config.service.ConfigService;

/**
 * 댓글 데이터 검증
 * @author yckim
 * @since 2019. 7. 15.
 *
 */
@Component
public class CommentValidator implements Validator{
	
	@Autowired
	private ConfigService configService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Comment.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*
		Comment commentForm = (Comment) target;
		
		//비회원 댓글 필수정보 체크
		if( "guest".equals(commentForm.getCmBy()) ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cmGuestName", null, "이름은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cmGuestEmail", null, "이메일은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cmGuestPassword", null, "비밀번호는 필수입력입니다.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cmContent", null, "댓글 내용은 필수입력입니다.");
		if( commentForm.getCmContent().length() < 5 ){
			errors.rejectValue("cmContent", null, "댓글 내용은 5자 이상이어야 합니다.");
		}
		if( commentForm.getCmContent().length() > 1000 ){
			errors.rejectValue("cmContent", null, "댓글 내용은 1000자까지 입력할 수 있습니다.");
		}
	
		//비속어 검사
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
			if( commentForm.getCmContent().contains(slang) ){
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
			errors.rejectValue("cmContent", null, "사용금지어 또는 비속어가 포함되어 있습니다. [" + sb.toString() + "]");
		}*/
	}
	
}
