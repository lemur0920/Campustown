/**
 * 
 */
package egovframework.com.asapro.support.crypto;

/**
 * 
 * 암호화와 base64를 한번에 처리할 수 있도록 기능을 한곳에 모은 인터페이스
 * @author cwsong
 * @since 2013. 7. 2.
 *
 */
public interface CryptoWorker {
	
	/**
     * 암호화된 바이트배열을 base64문자열로 인코딩해서 반환한다.
     * @param string
     * @return base64문자열
     */
	public String encrypt(String string);
	/**
     * 암호화된 base64문자열을 바이트배열로 환 후 문자열로 복구해서 반환한다.
     * @param string
     * @return 복호화된 문자열
     */
	public String decrypt(String string);
}
