/**
 * 
 */
package egovframework.com.asapro.member.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.client.RestTemplate;

//import egovframework.com.asapro.config.service.Config;
//import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.member.user.service.UserMemberService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 사용자 폼 검증
 * @author yckim
 * @since 2020. 2. 24.
 *
 */
@Component
public class UserMemberValidator implements AsaproValidator{
	
	@Autowired
	private HttpServletRequest request;
	
//	@Autowired
//	private ConfigService configService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserMemberService userMemberService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(UserMember.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		UserMember userMemberForm = (UserMember)target;
		
			
		//사용자 아이디
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", null, "아이디는 필수입력입니다.");
		
		
		//사용자 아이디 형태
		if( StringUtils.isNotBlank(userMemberForm.getUserId()) ){
			//if( !StringUtils.isAlphanumeric(userMemberForm.getUserId()) || !StringUtils.isAsciiPrintable(userMemberForm.getUserId()) ){
			if( !userMemberForm.getUserId().matches("[a-zA-Z0-9_]+") ){
				errors.rejectValue("userId", null, "아이디는 영문, 숫자, '_'만 사용할 수 있습니다.");
			} 
			if( userMemberForm.getUserId().length() < 5 || userMemberForm.getUserId().length() > 20 ){
				errors.rejectValue("userId", null, "아이디 길이는 5 ~ 20자 여야 합니다.");
			}
		}
		
		//사용자 추가시는 비밀번호 체크
		if("INSERT".equalsIgnoreCase(formMode)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", null, "비밀번호는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPasswordCheck", null, "비밀번호 확인은 필수입력입니다.");
		
			//비밀번호 확인값 체크
			if( StringUtils.isNotBlank(userMemberForm.getUserPassword()) ){
				if( !StringUtils.isAsciiPrintable(userMemberForm.getUserPassword())  ){
					errors.rejectValue("userPassword", null, "비밀번호는 영문, 숫자, 특수문자만 사용할 수 있습니다.");
				} 
				if( userMemberForm.getUserPassword().length() < 9 || userMemberForm.getUserPassword().length() > 20 ){
					errors.rejectValue("userPassword", null, "비밀번호 길이는 9 ~ 20자 여야 합니다.");
				}
				if( StringUtils.containsWhitespace(userMemberForm.getUserPassword()) ){
					errors.rejectValue("userPassword", null, "공백은 포함할 수 없습니다.");
				}
				if( StringUtils.containsNone(userMemberForm.getUserPassword(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ") 
						|| StringUtils.containsNone(userMemberForm.getUserPassword(), "abcdefghijklmnopqrstuvwxyz") 
						|| StringUtils.containsNone(userMemberForm.getUserPassword(), "0123456789") 
						|| StringUtils.containsNone(userMemberForm.getUserPassword(), "`~!@#$%^&*()_+-=[]{}\\;:,<.>/?|'\"")
						){
					errors.rejectValue("userPassword", null, "비밀번호는 영문소문자, 영문대문자, 숫자, 특수문자가 1개이상씩 포함되어야 합니다.");
				}
				
				//반복된 3자리 이상의 문자나 숫자
				Pattern p1 = Pattern.compile("(\\w)\\1\\1");
				Matcher m1 = p1.matcher(userMemberForm.getUserPassword());
				Pattern p2 = Pattern.compile("([\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"])\\1\\1");
				Matcher m2 = p2.matcher(userMemberForm.getUserPassword());
				
				if(m1.find() || m2.find()){
					errors.rejectValue("userPassword", "validation.member.userPassword.length", "비밀번호에 3자리 이상의 같은 문자나 숫자가 포함되어 있습니다.");
				}

				 // 연속된 3자리 이상의 문자나 숫자
		        String listThreeChar = "abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz|012|123|234|345|456|567|678|789|890";
		        String[] arrThreeChar = listThreeChar.split("\\|");
		        for (int i=0; i<arrThreeChar.length; i++) {
		            if(userMemberForm.getUserPassword().toLowerCase().matches(".*" + arrThreeChar[i] + ".*")) {
		            	errors.rejectValue("userPassword", "validation.member.userPassword.length", "비밀번호에 연속된 3자리 이상의 문자나 숫자가 포함되어 있습니다.");
		            }
		        }

		        // 연속된 3자리 이상의 키보드 문자
		        String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
		        String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
		        for (int j=0; j<arrKeyboardThreeChar.length; j++) {
		            if(userMemberForm.getUserPassword().toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
		            	errors.rejectValue("userPassword", "validation.member.userPassword.length", "비밀번호에 연속된 3자리 이상의 키보드 문자가 포함되어 있습니다.");
		            }
		        }
			}
			
			if( StringUtils.isNotBlank(userMemberForm.getUserPassword()) && StringUtils.isNotBlank(userMemberForm.getUserPasswordCheck()) ){
				if( !userMemberForm.getUserPassword().equals(userMemberForm.getUserPasswordCheck()) ){
					errors.rejectValue("userPasswordCheck", null, "비밀번호 확인값이 일치하지 않습니다.");
				}
			}
		}
		
		//사용자이름
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", null, "이름은 필수입력입니다.");

		//전화번호
		if( StringUtils.isNotBlank(userMemberForm.getUserTel2()) || StringUtils.isNotBlank(userMemberForm.getUserTel3()) ){
			if( !StringUtils.isNumeric(userMemberForm.getUserTel2()) || !StringUtils.isNumeric(userMemberForm.getUserTel3()) ){
				errors.rejectValue("userTel1", null, "전화번호는 숫자만 입력가능합니다.");
			}
		}
		
		//휴대폰번호
		if( StringUtils.isBlank(userMemberForm.getUserMobile1()) || StringUtils.isBlank(userMemberForm.getUserMobile2()) || StringUtils.isBlank(userMemberForm.getUserMobile3()) ){
			errors.rejectValue("userMobile1", null, "휴대전화번호는 필수입력입니다.");
		} else {
			if( !StringUtils.isNumeric(userMemberForm.getUserMobile1()) || !StringUtils.isNumeric(userMemberForm.getUserMobile2()) || !StringUtils.isNumeric(userMemberForm.getUserMobile3()) ){
				errors.rejectValue("userMobile1", null, "휴대전화번호는 숫자만 입력가능합니다.");
			}
		}
			
		//이메일 체크
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", null, "이메일 주소는 필수입력입니다.");
		if( StringUtils.isNotBlank(userMemberForm.getUserEmail()) ){
			if( !AsaproUtils.isEmail(userMemberForm.getUserEmail()) ){
				errors.rejectValue("userEmail", null, "이메일을 형식에 맞게 입력해 주세요.");
			}
		}
		
		
		
		//회원추가시 아이디 중복 불가
/*		if("INSERT".equalsIgnoreCase(formMode)){
			UserMember fromDB = userMemberService.getUserMember(userMemberForm.getUserId());
			if(fromDB != null){
				errors.rejectValue("userId", null, "사용중인 아이디 입니다.");
			}
		}
		
*/	
/*		
		//프로파일 수정시는 제외
		if(!"PROFILE".equalsIgnoreCase(formMode)){
			
			//소속기관
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userOrganization", null, "소속기관은 필수선택입니다.");
			
			//소속부서
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userDepartment", null, "소속부서는 필수선택입니다.");
			
			//관리 사이트 및 역할
			if( userMemberForm.getUserSiteRoleRelList() != null && userMemberForm.getUserSiteRoleRelList().size() > 0){
				boolean checkSiteRole = false;
				for (UserSiteRoleRel userSiteRoleRel : userMemberForm.getUserSiteRoleRelList() ) {
					if(StringUtils.isNotBlank(userSiteRoleRel.getSitePrefix()) ){
						if(StringUtils.isNotBlank(userSiteRoleRel.getRoleCode()) ){
							checkSiteRole = true;
							break;
						}
					}
				}
				
				if( !checkSiteRole ){
					errors.rejectValue("userSiteRoleRelList", null, "관리 사이트 및 역할 선택은 필수입니다.");
				}
			}else{
				errors.rejectValue("userSiteRoleRelList", null, "관리 사이트 및 역할 선택은 필수입니다.");
			}
		}
		
*/		
		/*	
		//회원추가시 아이디 중복 불가
		if("INSERT".equalsIgnoreCase(formMode)){
			Member fromDB = memberService.getMember(memberForm.getMemberId());
			if(fromDB != null){
				errors.rejectValue("memberId", "validation.member.memberId.duplicated", "사용중인 아이디 입니다.");
			}
			//회원추가시 탈퇴회원 아이디 사용 불가
			MemberDelete memberDeleteDB = memberService.getMemberDelete(memberForm.getMemberId());
			if( memberDeleteDB != null ){
				errors.rejectValue("memberId", "validation.member.memberId.unavailable", "사용할 수 없는 아이디 입니다.");
			}
			//회원추가/수정시 이메일, 전화번호, 이름 3가지가 같은 회원이 있는지 확인 - 본인의 중복가입일 가능성이 높음
			if( StringUtils.isNotBlank(memberForm.getMemberName())
					&& StringUtils.isNotBlank(memberForm.getMemberEmail())
					&& StringUtils.isNotBlank(memberForm.getMemberMobile1())
					&& StringUtils.isNotBlank(memberForm.getMemberMobile2())
					&& StringUtils.isNotBlank(memberForm.getMemberMobile3())
					){
				//회원아이디찾기 메소드 활용함
				Recover duplicateMemberSearch = new Recover();
				duplicateMemberSearch.setRecoverMode("ID");
				duplicateMemberSearch.setMemberName(memberForm.getMemberName());
				duplicateMemberSearch.setMemberMobile1(memberForm.getMemberMobile1());
				duplicateMemberSearch.setMemberMobile2(memberForm.getMemberMobile2());
				duplicateMemberSearch.setMemberMobile3(memberForm.getMemberMobile3());
				duplicateMemberSearch.setMemberEmail(memberForm.getMemberEmail());
				Member duplicateMember = memberService.getRecoverMember(duplicateMemberSearch);
				
				//중복데이터가 있다.
				if( duplicateMember != null ){
					errors.rejectValue("memberName", "validation.member.memberName.duplicateMember", "중복사용자 정보(성명, 전화번호, 이메일 중복)가 존재합니다.");
				}
			}
		}
		
		//프로필 이미지 체크
		if( memberForm.getMemberProfileImage() != null && !memberForm.getMemberProfileImage().isEmpty() ){
			String fileExtension = FilenameUtils.getExtension(memberForm.getMemberProfileImage().getOriginalFilename()).toLowerCase();
			//png 는 가끔 처리가 안되는 서버가 있음...이유는 나도 몰라-_- 서버 라이브러리 문젠가 암튼.
			if( ArrayUtils.indexOf(new String[]{"jpeg", "jpg", "gif"}, fileExtension) == -1 ){
				errors.rejectValue("memberProfileImage", "validation.member.memberProfileImage.extFilter", "프로필 이미지는 jpeg, jpg, gif 파일만 업로드할 수 있습니다.");
			}
			if( memberForm.getMemberProfileImage().getSize() >= Constant.MEGA ){
				errors.rejectValue("memberProfileImage", "validation.member.memberProfileImage.fileSize", "프로필 이미지는 1MB 미만의 파일만 업로드할 수 있습니다.");
			}
		}
		
		if( !AsaproUtils.isUserPath(request.getRequestURI()) ){
		*/
			/*
			 * 구글 리캡차 사용
			 * - 참고 : https://www.google.com/recaptcha/intro/index.html
			 */
			//회원가입시 캡차 사용 확인
	/*		Config globalConfig = configService.getConfig("global");
			if( globalConfig != null && "true".equals(globalConfig.getOption("use_captcha_signup")) ){
				//1.폼에서 넘어온 응답키값
				//캡차사용하기로 했으면 아래 파라메터가 무조건 있어야 함. 
				//multipart/form-data 폼에서 넘어오면 파라메터로 받을 수 없어서 UserInterceptor에서 attribute에 옮겨놓은걸 사용
				String gRecaptchaResponse = StringUtils.defaultString((String)request.getAttribute("g-recaptcha-response"));
				if( StringUtils.isNotBlank(gRecaptchaResponse) ){
					//2.인증비밀키
					String captchaSecretKey = globalConfig.getOption("use_captcha_secret_key");
					//3.엔드유저 아이피
					String remoteIp = AsaproUtils.getRempoteIp(request);
					//구글 리캡차 서비스 이용
					String captchaVerification = restTemplate.getForObject("https://www.google.com/recaptcha/api/siteverify?secret=" + captchaSecretKey + "&response=" + gRecaptchaResponse + "&remoteip=" + remoteIp, String.class);
					if( StringUtils.isNotBlank(captchaVerification) ){
						try {
							ObjectMapper objectMapper = new ObjectMapper();
							TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
							Map<String, Object> verifyResultMap = objectMapper.readValue(captchaVerification, typeRef);
							if( verifyResultMap != null ){
								if( !"true".equals((String)verifyResultMap.get("success")) ){
									errors.rejectValue("memberCaptcha", "validation.member.captcha.failure", "자동가입이 의심되는 요청입니다.");
								}
							} else {
								errors.rejectValue("memberCaptcha", "validation.member.captcha.failure", "자동가입이 의심되는 요청입니다.");
							}
						} catch (JsonParseException e) {
							e.printStackTrace();
						} catch (JsonMappingException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (Exception e){
							e.printStackTrace();
						}
					} else {
						errors.rejectValue("memberCaptcha", "validation.member.captcha.failure", "자동가입이 의심되는 요청입니다.");
					}
				} else {
					errors.rejectValue("memberCaptcha", "validation.member.captcha.failure", "자동가입이 의심되는 요청입니다.");
				}
			}
		}*/
	}

}
