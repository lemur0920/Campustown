/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.archive.service.ArchiveSearch;

/**
 *  광고자료 검색
 * @author yckim
 * @since 2019.9. 30.
 *
 */
public class AdvertisingSearch extends ArchiveSearch{
	private String adtManufactureYear;	//제작년도
	
	private Boolean adtDefaultYear = false;	//연도 기본값 셋팅여부

	/**
	 * @return the adtManufactureYear
	 */
	public String getAdtManufactureYear() {
		return adtManufactureYear;
	}

	/**
	 * @param adtManufactureYear the adtManufactureYear to set
	 */
	public void setAdtManufactureYear(String adtManufactureYear) {
		this.adtManufactureYear = adtManufactureYear;
	}

	/**
	 * @return the adtDefaultYear
	 */
	public Boolean getAdtDefaultYear() {
		return adtDefaultYear;
	}

	/**
	 * @param adtDefaultYear the adtDefaultYear to set
	 */
	public void setAdtDefaultYear(Boolean adtDefaultYear) {
		this.adtDefaultYear = adtDefaultYear;
	}

	
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getAdtManufactureYear() != null ){
			sb.append("&amp;adtManufactureYear=");
			sb.append(this.getAdtManufactureYear());
		}
		if( this.getAdtDefaultYear() != null ){
			sb.append("&amp;adtDefaultYear=");
			sb.append(this.getAdtDefaultYear());
		}
		
		
		return sb.toString();
	}
}
