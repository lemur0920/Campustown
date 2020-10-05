/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.service;


import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.support.annotation.ArchiveItem;


/**
 * 정책자료 VO
 * 아카이브 커스텀타입
 * @author yckim
 * @since 2019. 12. 27.
 *
 */
@ArchiveItem(customType="policy", label="정책자료")
public class Policy extends Archive{
	
	private String poliShortDescription;	//간단설명

	/**
	 * @return the poliShortDescription
	 */
	public String getPoliShortDescription() {
		return poliShortDescription;
	}

	/**
	 * @param poliShortDescription the poliShortDescription to set
	 */
	public void setPoliShortDescription(String poliShortDescription) {
		this.poliShortDescription = poliShortDescription;
	}

	
}
