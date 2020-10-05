/**
 * 
 */
package egovframework.com.asapro.support.handler;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import egovframework.com.asapro.support.exception.AsaproException;

/**
 * RestTemplate 으로 요청했을 경우 에러처리
 * @author cwsong
 *
 */
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler{

	private static final ResponseErrorHandler RESPONSE_ERROR_HANDLER = new DefaultResponseErrorHandler();
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return RESPONSE_ERROR_HANDLER.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		String body = IOUtils.toString(response.getBody());
		throw new AsaproException(body);
	}

}
