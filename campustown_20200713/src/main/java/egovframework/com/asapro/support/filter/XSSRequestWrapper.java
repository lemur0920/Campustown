/**
 * 
 */
package egovframework.com.asapro.support.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author yckim
 * @since 2018. 8. 31.
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {

		Map<String, String[]> map = super.getParameterMap();
		if (map == null) {
			return null;
		}
		
		Map<String, String[]> encodedMap = new HashMap<String, String[]>();
		
		for( String key : map.keySet() ){
			if(map.get(key) != null){
				String[] values = map.get(key);
				int count = values.length;
				String[] encodedValues = new String[count];
				for (int i = 0; i < count; i++) {
					encodedValues[i] = cleanXSS(values[i]);
				}
				encodedMap.put(key, encodedValues);
			}
        }
		return encodedMap;
	}
	
	@Override
	public String[] getParameterValues(String parameter) {
		
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}
		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return cleanXSS(value);

	}

	@Override
    public String getQueryString() {
		String value = super.getQueryString();
		if (value == null)
			return null;
		return cleanXSS(value);
    }
	
	private String cleanXSS(String value) {
		// You'll need to remove the spaces from the html entities below
		value = value.replaceAll("&", "&amp;");
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("%3C", "&lt;"); // <
		value = value.replaceAll("%3E", "&gt;");	// >
		//value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		//value = value.replaceAll("\\(", "[").replaceAll("\\)", "]");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("\"", "&quot;");
		//value = value.replaceAll("\\.", "&#46;");
		value = value.replaceAll("%2E", "&#46;");	// .
		value = value.replaceAll("%2F", "&#47;");	// /
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		//파일패스 상대경로 접근방지
		value = value.replaceAll("\\.\\./", ""); // ../
		value = value.replaceAll("\\.\\.\\\\", ""); // ..\
		return value;
	}
}
