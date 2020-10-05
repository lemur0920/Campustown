/**
 * 
 */
package egovframework.com.asapro.menu.web;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 메뉴 폼 검증
 * @author yckim
 * @since 2018. 7. 6.
 *
 */
@Component
public class MenuValidator implements AsaproValidator{

	@Autowired
	private MenuService menuService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Menu.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	//사용금지 메뉴 아이디
	private static final String[] RESERVED_MENU_IDS = {
		"index"
		, "home"
		, "board"
		, "boards"
		, "user"
		, "users"
		, "member"
		, "members"
		, "admin"
		, "administrator"
		, "manager"
		, "content"
		, "contents"
		, "page"
		, "pages"
		, "new"
		, "edit"
		, "form"
		, "list"
		, "view"
		, "insert"
		, "update"
		, "delete"
		//, "test"
		, "download"
		, "upload"
		, "file"
		, "files"
		//, "login"
		//, "logout"
		//, "join"
	};
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		Menu menuForm = (Menu)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "menuId", null, "메뉴아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "menuName", null, "메뉴이름은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "menuLink", null, "메뉴링크 Url은 필수입력입니다.");
		
		if( "INSERT".equals(formMode) ){
			if( StringUtils.isNotBlank(menuForm.getMenuId()) ){
				if( !menuForm.getMenuId().matches("[a-zA-Z0-9_-]*") ){
					errors.rejectValue("menuId", null, "영문, 숫자, _, -만 사용할 수 있습니다.");
				}
				if( menuForm.getMenuId().length() < 3 || menuForm.getMenuId().length() > 50 ){
					errors.rejectValue("menuId", null, "3자 이상, 50자 이하여야 합니다.");
				}
				if( ArrayUtils.contains(RESERVED_MENU_IDS, menuForm.getMenuId().trim()) ){
					errors.rejectValue("menuId", null, "[" + menuForm.getMenuId().trim() + "] 는 정책상 사용할 수 없는 메뉴아이디입니다.");
				}
				Menu fromDB = menuService.getMenu(menuForm);
				if( fromDB != null ){
					errors.rejectValue("menuId", null, "현재 사용중인 메뉴 아이디입니다.");
				}
			}
		}
	}

}
