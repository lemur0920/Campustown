/**
 * 
 */
package egovframework.com.asapro.support.crypto;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cryptography.EgovCryptoService;

/**
 * PBEWithSHA1AndDESede 알고리즘을 암호화/복호화에 적용하는 CryptoWorker 구현 클래스
 * 다른 알고리즘을 구현할 경우 다른 worker를 구현해서 사용하자
 * @author cwsong
 * @since 
 * @date 2013. 7. 2.
 */
@Component("generalCryptoWorker")
public class GeneralCryptoWorker implements CryptoWorker{
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralCryptoWorker.class);
	
	//globalConfig의 값을 받아놓기 위한 변수이므로 여기서 고쳐봤자 페이지 로딩시 바뀌므로 여기서 변수를 직접 수정해봤자 의미 없음.
	//config_option 테이블의 use_db_encryption 값을 바꿔 주어야 함. 
	public static boolean USE_DB_ENCRYPTION = true; 
	
	//17글자를 암호화하면 병신되는 버그같은게 있는데 이게 나왔다 안나왔다함?!?
	@Resource(name="ARIACryptoService")
    private EgovCryptoService cryptoService;
	
	//암호화할때마다 다른 값이 나와서(복호는 같은게 나옴) 검색을 못함
	//@Resource(name="generalCryptoService")
    //private EgovGeneralCryptoService cryptoService; 
	
	//암호화
    public String encrypt(String string){
    	if(StringUtils.isBlank(string)){
    		return string;
    	}
    	String result = string;
    	try {
			result = Base64.encodeBase64String( cryptoService.encrypt(string.getBytes("UTF-8"), EgovProperties.getProperty("crypto.password")) );
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
		}
    	return result.trim();
    }
    
    //복호화
    public String decrypt(String string){
    	if(StringUtils.isBlank(string)){
    		return string;
    	}
    	String result = string;
		try {
			result = new String( cryptoService.decrypt(Base64.decodeBase64(string), EgovProperties.getProperty("crypto.password")), "UTF-8" );
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
		}  
    	return result.trim();
    }
}
