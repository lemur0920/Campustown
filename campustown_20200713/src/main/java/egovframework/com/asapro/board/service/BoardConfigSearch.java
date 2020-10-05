/**
 * 
 */
package egovframework.com.asapro.board.service;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 게시판 설정 검색 VO
 * @author yckim
 * @since 2018. 5. 31.
 *
 */
public class BoardConfigSearch extends PagingSearch{
	
	private Boolean bcUse;	//사용유무
	private Boolean bcAdmin = false;	//admin 슈퍼 관리자여부
	private String bcDepartment;	//관리부서 코드

	/**
	 * @return the bcUse
	 */
	public Boolean getBcUse() {
		return bcUse;
	}

	/**
	 * @param bcUse the bcUse to set
	 */
	public void setBcUse(Boolean bcUse) {
		this.bcUse = bcUse;
	}
	/**
	 * @return the bcDepartment
	 */
	public String getBcDepartment() {
		return bcDepartment;
	}

	/**
	 * @param bcDepartment the bcDepartment to set
	 */
	public void setBcDepartment(String bcDepartment) {
		this.bcDepartment = bcDepartment;
	}

	/**
	 * @return the bcAdmin
	 */
	public Boolean getBcAdmin() {
		return bcAdmin;
	}

	/**
	 * @param bcAdmin the bcAdmin to set
	 */
	public void setBcAdmin(Boolean bcAdmin) {
		this.bcAdmin = bcAdmin;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getBcUse() != null ){
			sb.append("&amp;bcUse=");
			sb.append(this.getBcUse());
		}
		
		return sb.toString();
	}
}
