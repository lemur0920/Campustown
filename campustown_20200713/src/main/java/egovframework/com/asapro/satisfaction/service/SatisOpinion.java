/**
 * 
 */
package egovframework.com.asapro.satisfaction.service;

import java.util.Date;
import java.util.List;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 메뉴 평가의견 VO
 * @author yckim
 * @since 2019. 4. 15.
 *
 */
public class SatisOpinion extends MultiSiteVO{
	
	private String menuId;	//메뉴 아이디
	private String satisOpinion;	//평가의견
	private Date satisOpiDate;	//평가의견 일시
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * @return the satisOpinion
	 */
	public String getSatisOpinion() {
		return satisOpinion;
	}
	/**
	 * @param satisOpinion the satisOpinion to set
	 */
	public void setSatisOpinion(String satisOpinion) {
		this.satisOpinion = satisOpinion;
	}
	/**
	 * @return the satisOpiDate
	 */
	public Date getSatisOpiDate() {
		return satisOpiDate;
	}
	/**
	 * @param satisOpiDate the satisOpiDate to set
	 */
	public void setSatisOpiDate(Date satisOpiDate) {
		this.satisOpiDate = satisOpiDate;
	}
	
}
