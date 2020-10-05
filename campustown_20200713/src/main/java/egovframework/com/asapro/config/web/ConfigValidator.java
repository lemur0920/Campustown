/**
 * 
 */
package egovframework.com.asapro.config.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigOption;

/**
 * 설정폼 값검증
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
@Component
public class ConfigValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Config.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Config configForm = (Config) target;
		if( configForm.getOptions() == null || configForm.getOptions().size() == 0 ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "options", null, "옵션값이 없습니다.");
		} else {
			int idx = 0;
			for( ConfigOption configOption : configForm.getOptions() ){
				if( StringUtils.isNotBlank(configOption.getOptValue()) ){
					if( configOption.getOptValue().length() > 4000 ){
						errors.rejectValue("options[" + idx + "].optValue", null, "옵션값은 최대 4000자까지 입력할 수 있습니다.");
					}
				}
				idx++;
			}
		}
		
	}

}
