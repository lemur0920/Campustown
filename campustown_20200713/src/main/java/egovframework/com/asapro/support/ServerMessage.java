/**
 * 
 */
package egovframework.com.asapro.support;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * 메세지
 * @author yckim
 * @since 2018. 4. 16.
 *
 */
public class ServerMessage {
	
	private boolean success;
	private String text;
	private int successCnt = 0;
	private String resultCode = "";
	
	/**
	 * 디폴트 생성자
	 */
	@JsonIgnore
	public ServerMessage(){
		
	}
	
	/**
	 * 서버에서 전송되는 메세지
	 * @param type
	 * @param text
	 */
	@JsonIgnore
	public ServerMessage(boolean success, String text){
		this.success = success;
		this.text = text;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the successCnt
	 */
	public int getSuccessCnt() {
		return successCnt;
	}

	/**
	 * @param successCnt the successCnt to set
	 */
	public void setSuccessCnt(int successCnt) {
		this.successCnt = successCnt;
	}

	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

}
