/**
 * 
 */
package egovframework.com.asapro.menu.service;

import java.io.Serializable;
import java.util.List;


/**
 * 메뉴 아이디를 Json에서 파싱하기 위한 VO
 * @author yckim
 * @since 2018. 7. 19.
 *
 */
@SuppressWarnings("serial")
public class MenuIdJsonVO implements Serializable{
	
	private String id;
	private List<MenuIdJsonVO> children;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the children
	 */
	public List<MenuIdJsonVO> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<MenuIdJsonVO> children) {
		this.children = children;
	}
	
	
	
}
