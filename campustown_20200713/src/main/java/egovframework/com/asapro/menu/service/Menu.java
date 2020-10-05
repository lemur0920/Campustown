/**
 * 
 */
package egovframework.com.asapro.menu.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.support.Constant;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 메뉴 VO
 * @author yckim
 * @since 2018. 7. 6.
 *
 */
@SuppressWarnings("serial")
public class Menu extends MultiSiteVO implements Serializable{
	
//	@WatchDog
	private String menuId;
	private String menuName = "";
	private String menuPlainName;//메뉴이름에 포함된 특수문자 제거 - 디비컬럼은 없음
	private String menuType = "content";//content|program|link
	
	private String menuGnbType = "text";//image/text
	private String menuGnbExtOn = "";
	private String menuGnbExtOff = "";
	private String menuSnbType = "text";//image/text
	private String menuSnbExtOn = "";
	private String menuSnbExtOff = "";
	private String menuTitleType = "text";//image/text
	private String menuTitleExt = "";
	private String menuTitleSubText = "";//메뉴타이틀 옆에 작게 표시되는 설명글 있을 경우 사용
	
	private String menuLink = "";	//메뉴 링크
	private boolean menuNewWin = false;	//세창으로 열지여부
	private Menu menuParent;	//부모메뉴
	private List<Menu> menuChildren;	//자식메뉴 목록
	private Integer menuDepth = 1;	//메뉴 뎁스
	private Integer menuOrder = 999;	//메뉴 순서
	private String menuHeader = "header.jsp";	//헤더파일
	private String menuTemplate;	//특정 jsp 파일을 뷰로 지정해서 사용
	private String menuFooter = "footer.jsp";	//푸터파일
	private String menuStatus = "public";	//public(공개)|hidden(접근은 가능하지만 메뉴트리에는 안나옴)|locked(접근도 안되고 메뉴에도 안나옴)
	private Date menuRegDate;	//등록일시
	private Date menuLastModified;	//마지막 수정일시
	
	private boolean menuUseManagerInfo = false;	//메뉴 관리자정보 노출 여부
	private String menuManager = "";	//메뉴 관리자
	private String menuDepartment = "";	//메뉴 관리부서
	private String menuPhone = "";	//메뉴 관리자 전화번호
	private String menuEmail = "";	//메뉴 관리자 메일주소
	private String menuEtc = "";	//메뉴 관리자 기타정보
	
	private boolean menuUseSatisfaction;	//메뉴 만족도조사 사용여부
	private String menuLocation = "";	//메뉴위치
	
	private Content content;	//콘텐츠
	
	//메뉴 해시태그  
	private String tagCode = "";
	
	//--컬럼 없음
	private MultipartFile menuGnbImageOnFile;
	private MultipartFile menuGnbImageOffFile;
	private MultipartFile menuSnbTopFile;
	private MultipartFile menuSnbImageOnFile;
	private MultipartFile menuSnbImageOffFile;
	private MultipartFile menuTitleImageFile;
	
	//이미지 지울때--컬럼 없음
	private boolean menuGnbImageOnDelete;
	private boolean menuGnbImageOffDelete;
	private boolean menuSnbImageOnDelete;
	private boolean menuSnbImageOffDelete;
	private boolean menuTitleImageDelete;
	
	//이미지url--컬럼 없음
	private String menuTitleImageUrl = "";
	private String menuGnbImageOnUrl = "";
	private String menuGnbImageOffUrl = "";
	private String menuSnbImageOnUrl = "";
	private String menuSnbImageOffUrl = "";

	/**
	 * Default Constructor
	 */
	public Menu(){
		
	}
	
	/**
	 * Constructor
	 * @param menuId
	 * @param menuLink
	 */
	public Menu(String menuId, String menuLink){
		this.menuId = menuId;
		this.menuLink = menuLink;
	}
	
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
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}
	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
		this.menuPlainName = Jsoup.clean(this.menuName, Whitelist.none());
	}
	/**
	 * @return the menuGnbType
	 */
	public String getMenuGnbType() {
		return menuGnbType;
	}
	/**
	 * @param menuGnbType the menuGnbType to set
	 */
	public void setMenuGnbType(String menuGnbType) {
		this.menuGnbType = menuGnbType;
	}
	/**
	 * @return the menuSnbType
	 */
	public String getMenuSnbType() {
		return menuSnbType;
	}
	/**
	 * @param menuSnbType the menuSnbType to set
	 */
	public void setMenuSnbType(String menuSnbType) {
		this.menuSnbType = menuSnbType;
	}
	/**
	 * @return the menuTitleType
	 */
	public String getMenuTitleType() {
		return menuTitleType;
	}
	/**
	 * @param menuTitleType the menuTitleType to set
	 */
	public void setMenuTitleType(String menuTitleType) {
		this.menuTitleType = menuTitleType;
	}
	/**
	 * @return the menuTitleSubText
	 */
	public String getMenuTitleSubText() {
		return menuTitleSubText;
	}
	/**
	 * @param menuTitleSubText the menuTitleSubText to set
	 */
	public void setMenuTitleSubText(String menuTitleSubText) {
		this.menuTitleSubText = menuTitleSubText;
	}
	/**
	 * @return the menuLink
	 */
	public String getMenuLink() {
		/*if(StringUtils.isNotBlank(menuLink) ){
			return Constant.CONTEXT_PATH + menuLink;
		}else{
			return menuLink;
		}*/
		return menuLink;
	}
	/**
	 * @param menuLink the menuLink to set
	 */
	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}
	/**
	 * @return the menuNewWin
	 */
	public boolean isMenuNewWin() {
		return menuNewWin;
	}
	/**
	 * @param menuNewWin the menuNewWin to set
	 */
	public void setMenuNewWin(boolean menuNewWin) {
		this.menuNewWin = menuNewWin;
	}
	/**
	 * @return the menuParent
	 */
	public Menu getMenuParent() {
		return menuParent;
	}
	/**
	 * @param menuParent the menuParent to set
	 */
	public void setMenuParent(Menu menuParent) {
		this.menuParent = menuParent;
	}
	/**
	 * @return the menuChildren
	 */
	public List<Menu> getMenuChildren() {
		return menuChildren;
	}
	/**
	 * @param menuChildren the menuChildren to set
	 */
	public void setMenuChildren(List<Menu> menuChildren) {
		this.menuChildren = menuChildren;
	}
	/**
	 * @return the menuDepth
	 */
	public Integer getMenuDepth() {
		return menuDepth;
	}
	/**
	 * @param menuDepth the menuDepth to set
	 */
	public void setMenuDepth(Integer menuDepth) {
		this.menuDepth = menuDepth;
	}
	/**
	 * @return the menuOrder
	 */
	public Integer getMenuOrder() {
		return menuOrder;
	}
	/**
	 * @param menuOrder the menuOrder to set
	 */
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	/**
	 * @return the menuHeader
	 */
	public String getMenuHeader() {
		return menuHeader;
	}
	/**
	 * @param menuHeader the menuHeader to set
	 */
	public void setMenuHeader(String menuHeader) {
		this.menuHeader = menuHeader;
	}
	/**
	 * @return the menuFooter
	 */
	public String getMenuFooter() {
		return menuFooter;
	}
	/**
	 * @param menuFooter the menuFooter to set
	 */
	public void setMenuFooter(String menuFooter) {
		this.menuFooter = menuFooter;
	}
	/**
	 * @return the menuRegDate
	 */
	public Date getMenuRegDate() {
		return menuRegDate;
	}
	/**
	 * @param menuRegDate the menuRegDate to set
	 */
	public void setMenuRegDate(Date menuRegDate) {
		this.menuRegDate = menuRegDate;
	}
	/**
	 * @return the menuLastModified
	 */
	public Date getMenuLastModified() {
		return menuLastModified;
	}
	/**
	 * @param menuLastModified the menuLastModified to set
	 */
	public void setMenuLastModified(Date menuLastModified) {
		this.menuLastModified = menuLastModified;
	}

	/**
	 * @return the menuUseManagerInfo
	 */
	public boolean isMenuUseManagerInfo() {
		return menuUseManagerInfo;
	}

	/**
	 * @param menuUseManagerInfo the menuUseManagerInfo to set
	 */
	public void setMenuUseManagerInfo(boolean menuUseManagerInfo) {
		this.menuUseManagerInfo = menuUseManagerInfo;
	}

	/**
	 * @return the menuManager
	 */
	public String getMenuManager() {
		return menuManager;
	}
	/**
	 * @param menuManager the menuManager to set
	 */
	public void setMenuManager(String menuManager) {
		this.menuManager = menuManager;
	}
	/**
	 * @return the menuDepartment
	 */
	public String getMenuDepartment() {
		return menuDepartment;
	}
	/**
	 * @param menuDepartment the menuDepartment to set
	 */
	public void setMenuDepartment(String menuDepartment) {
		this.menuDepartment = menuDepartment;
	}
	/**
	 * @return the menuPhone
	 */
	public String getMenuPhone() {
		return menuPhone;
	}
	/**
	 * @param menuPhone the menuPhone to set
	 */
	public void setMenuPhone(String menuPhone) {
		this.menuPhone = menuPhone;
	}
	/**
	 * @return the menuEmail
	 */
	public String getMenuEmail() {
		return menuEmail;
	}
	/**
	 * @param menuEmail the menuEmail to set
	 */
	public void setMenuEmail(String menuEmail) {
		this.menuEmail = menuEmail;
	}
	/**
	 * @return the menuEtc
	 */
	public String getMenuEtc() {
		return menuEtc;
	}
	/**
	 * @param menuEtc the menuEtc to set
	 */
	public void setMenuEtc(String menuEtc) {
		this.menuEtc = menuEtc;
	}
	/**
	 * @return the menuGnbImageOnFile
	 */
	public MultipartFile getMenuGnbImageOnFile() {
		return menuGnbImageOnFile;
	}
	/**
	 * @param menuGnbImageOnFile the menuGnbImageOnFile to set
	 */
	public void setMenuGnbImageOnFile(MultipartFile menuGnbImageOnFile) {
		this.menuGnbImageOnFile = menuGnbImageOnFile;
	}
	/**
	 * @return the menuGnbImageOffFile
	 */
	public MultipartFile getMenuGnbImageOffFile() {
		return menuGnbImageOffFile;
	}
	/**
	 * @param menuGnbImageOffFile the menuGnbImageOffFile to set
	 */
	public void setMenuGnbImageOffFile(MultipartFile menuGnbImageOffFile) {
		this.menuGnbImageOffFile = menuGnbImageOffFile;
	}
	/**
	 * @return the menuSnbImageOnFile
	 */
	public MultipartFile getMenuSnbImageOnFile() {
		return menuSnbImageOnFile;
	}
	/**
	 * @param menuSnbImageOnFile the menuSnbImageOnFile to set
	 */
	public void setMenuSnbImageOnFile(MultipartFile menuSnbImageOnFile) {
		this.menuSnbImageOnFile = menuSnbImageOnFile;
	}
	/**
	 * @return the menuSnbImageOffFile
	 */
	public MultipartFile getMenuSnbImageOffFile() {
		return menuSnbImageOffFile;
	}
	/**
	 * @param menuSnbImageOffFile the menuSnbImageOffFile to set
	 */
	public void setMenuSnbImageOffFile(MultipartFile menuSnbImageOffFile) {
		this.menuSnbImageOffFile = menuSnbImageOffFile;
	}
	/**
	 * @return the menuTitleImageFile
	 */
	public MultipartFile getMenuTitleImageFile() {
		return menuTitleImageFile;
	}
	/**
	 * @param menuTitleImageFile the menuTitleImageFile to set
	 */
	public void setMenuTitleImageFile(MultipartFile menuTitleImageFile) {
		this.menuTitleImageFile = menuTitleImageFile;
	}
	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}
	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	/**
	 * @return the menuStatus
	 */
	public String getMenuStatus() {
		return menuStatus;
	}
	/**
	 * @param menuStatus the menuStatus to set
	 */
	public void setMenuStatus(String menuStatus) {
		this.menuStatus = menuStatus;
	}
	/**
	 * @return the menuTemplate
	 */
	public String getMenuTemplate() {
		return menuTemplate;
	}
	/**
	 * @param menuTemplate the menuTemplate to set
	 */
	public void setMenuTemplate(String menuTemplate) {
		this.menuTemplate = menuTemplate;
	}
	/**
	 * @return the menuPlainName
	 */
	public String getMenuPlainName() {
		return menuPlainName;
	}
	/**
	 * @param menuPlainName the menuPlainName to set
	 */
	public void setMenuPlainName(String menuPlainName) {
		this.menuPlainName = menuPlainName;
	}

	/**
	 * @return the menuSnbTopFile
	 */
	public MultipartFile getMenuSnbTopFile() {
		return menuSnbTopFile;
	}

	/**
	 * @param menuSnbTopFile the menuSnbTopFile to set
	 */
	public void setMenuSnbTopFile(MultipartFile menuSnbTopFile) {
		this.menuSnbTopFile = menuSnbTopFile;
	}

	public static final int ONLY_CHILD = -1;
	public static final int FIRST_CHILD = 0;
	public static final int MIDDLE_CHILD = 5;
	public static final int LAST_CHILD = 9;

	/**
	 * 형제메뉴들 중에서 메뉴의 위치를 구해서 반환한다.
	 * <pre>
	 * Menu.FIRST = 0;
	 * Menu.MIDDLE = 5;
	 * Menu.LAST = 9;
	 * </pre>
	 * @param menuList
	 * @return 메뉴위치
	 */
	public int getMenuPosition(List<Menu> menuList){
		
		int orderMin = 999;
		int orderMax = 0;
		
		for( Menu menu : menuList ){
			if( !"public".equals(menu.getMenuStatus()) ){
				continue;
			}
			//1차메뉴
			if( (this.getMenuParent() == null || StringUtils.isBlank(this.getMenuParent().getMenuId())) && (menu.getMenuParent() == null || StringUtils.isBlank(menu.getMenuId())) ){
				if( menu.getMenuOrder() < orderMin ){
					orderMin = menu.getMenuOrder();
				}
				if( menu.getMenuOrder() > orderMax ){
					orderMax = menu.getMenuOrder(); 
				}
			} else {
				if( this.getMenuParent() != null && menu.getMenuParent() != null ){
					if( menu.getMenuParent().getMenuId().equals(this.getMenuParent().getMenuId()) ){
						if( menu.getMenuOrder() < orderMin ){
							orderMin = menu.getMenuOrder();
						}
						if( menu.getMenuOrder() > orderMax ){
							orderMax = menu.getMenuOrder(); 
						}
					}
				}
			}
		}
		if(this.getMenuOrder() == orderMin && this.getMenuOrder() == orderMax){
			return Menu.ONLY_CHILD;
		} else if(this.getMenuOrder() == orderMin && this.getMenuOrder() != orderMax){
			return Menu.FIRST_CHILD;
		} else if(this.getMenuOrder() != orderMin && this.getMenuOrder() == orderMax){
			return Menu.LAST_CHILD;
		} else {
			return Menu.MIDDLE_CHILD;
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		return true;
	}

	/**
	 * @return the menuGnbExtOn
	 */
	public String getMenuGnbExtOn() {
		return menuGnbExtOn;
	}

	/**
	 * @param menuGnbExtOn the menuGnbExtOn to set
	 */
	public void setMenuGnbExtOn(String menuGnbExtOn) {
		this.menuGnbExtOn = menuGnbExtOn;
	}

	/**
	 * @return the menuGnbExtOff
	 */
	public String getMenuGnbExtOff() {
		return menuGnbExtOff;
	}

	/**
	 * @param menuGnbExtOff the menuGnbExtOff to set
	 */
	public void setMenuGnbExtOff(String menuGnbExtOff) {
		this.menuGnbExtOff = menuGnbExtOff;
	}

	/**
	 * @return the menuSnbExtOn
	 */
	public String getMenuSnbExtOn() {
		return menuSnbExtOn;
	}

	/**
	 * @param menuSnbExtOn the menuSnbExtOn to set
	 */
	public void setMenuSnbExtOn(String menuSnbExtOn) {
		this.menuSnbExtOn = menuSnbExtOn;
	}

	/**
	 * @return the menuSnbExtOff
	 */
	public String getMenuSnbExtOff() {
		return menuSnbExtOff;
	}

	/**
	 * @param menuSnbExtOff the menuSnbExtOff to set
	 */
	public void setMenuSnbExtOff(String menuSnbExtOff) {
		this.menuSnbExtOff = menuSnbExtOff;
	}

	/**
	 * @return the menuTitleExt
	 */
	public String getMenuTitleExt() {
		return menuTitleExt;
	}

	/**
	 * @param menuTitleExt the menuTitleExt to set
	 */
	public void setMenuTitleExt(String menuTitleExt) {
		this.menuTitleExt = menuTitleExt;
	}
	
	/**
	 * @return the menuGnbImageOnDelete
	 */
	public boolean isMenuGnbImageOnDelete() {
		return menuGnbImageOnDelete;
	}

	/**
	 * @param menuGnbImageOnDelete the menuGnbImageOnDelete to set
	 */
	public void setMenuGnbImageOnDelete(boolean menuGnbImageOnDelete) {
		this.menuGnbImageOnDelete = menuGnbImageOnDelete;
	}

	/**
	 * @return the menuGnbImageOffDelete
	 */
	public boolean isMenuGnbImageOffDelete() {
		return menuGnbImageOffDelete;
	}

	/**
	 * @param menuGnbImageOffDelete the menuGnbImageOffDelete to set
	 */
	public void setMenuGnbImageOffDelete(boolean menuGnbImageOffDelete) {
		this.menuGnbImageOffDelete = menuGnbImageOffDelete;
	}

	/**
	 * @return the menuSnbImageOnDelete
	 */
	public boolean isMenuSnbImageOnDelete() {
		return menuSnbImageOnDelete;
	}

	/**
	 * @param menuSnbImageOnDelete the menuSnbImageOnDelete to set
	 */
	public void setMenuSnbImageOnDelete(boolean menuSnbImageOnDelete) {
		this.menuSnbImageOnDelete = menuSnbImageOnDelete;
	}

	/**
	 * @return the menuSnbImageOffDelete
	 */
	public boolean isMenuSnbImageOffDelete() {
		return menuSnbImageOffDelete;
	}

	/**
	 * @param menuSnbImageOffDelete the menuSnbImageOffDelete to set
	 */
	public void setMenuSnbImageOffDelete(boolean menuSnbImageOffDelete) {
		this.menuSnbImageOffDelete = menuSnbImageOffDelete;
	}

	/**
	 * @return the menuTitleImageDelete
	 */
	public boolean isMenuTitleImageDelete() {
		return menuTitleImageDelete;
	}

	/**
	 * @param menuTitleImageDelete the menuTitleImageDelete to set
	 */
	public void setMenuTitleImageDelete(boolean menuTitleImageDelete) {
		this.menuTitleImageDelete = menuTitleImageDelete;
	}

	/**
	 * @return the menuTitleImageUrl
	 */
	public String getMenuTitleImageUrl() {
		return menuTitleImageUrl;
	}

	/**
	 * @param menuTitleImageUrl the menuTitleImageUrl to set
	 */
	public void setMenuTitleImageUrl(String menuTitleImageUrl) {
		this.menuTitleImageUrl = menuTitleImageUrl;
	}

	/**
	 * @return the menuGnbImageOnUrl
	 */
	public String getMenuGnbImageOnUrl() {
		return menuGnbImageOnUrl;
	}

	/**
	 * @param menuGnbImageOnUrl the menuGnbImageOnUrl to set
	 */
	public void setMenuGnbImageOnUrl(String menuGnbImageOnUrl) {
		this.menuGnbImageOnUrl = menuGnbImageOnUrl;
	}

	/**
	 * @return the menuGnbImageOffUrl
	 */
	public String getMenuGnbImageOffUrl() {
		return menuGnbImageOffUrl;
	}

	/**
	 * @param menuGnbImageOffUrl the menuGnbImageOffUrl to set
	 */
	public void setMenuGnbImageOffUrl(String menuGnbImageOffUrl) {
		this.menuGnbImageOffUrl = menuGnbImageOffUrl;
	}

	/**
	 * @return the menuSnbImageOnUrl
	 */
	public String getMenuSnbImageOnUrl() {
		return menuSnbImageOnUrl;
	}

	/**
	 * @param menuSnbImageOnUrl the menuSnbImageOnUrl to set
	 */
	public void setMenuSnbImageOnUrl(String menuSnbImageOnUrl) {
		this.menuSnbImageOnUrl = menuSnbImageOnUrl;
	}

	/**
	 * @return the menuSnbImageOffUrl
	 */
	public String getMenuSnbImageOffUrl() {
		return menuSnbImageOffUrl;
	}

	/**
	 * @param menuSnbImageOffUrl the menuSnbImageOffUrl to set
	 */
	public void setMenuSnbImageOffUrl(String menuSnbImageOffUrl) {
		this.menuSnbImageOffUrl = menuSnbImageOffUrl;
	}

	/**
	 * @return the menuUseSatisfaction
	 */
	public boolean isMenuUseSatisfaction() {
		return menuUseSatisfaction;
	}

	/**
	 * @param menuUseSatisfaction the menuUseSatisfaction to set
	 */
	public void setMenuUseSatisfaction(boolean menuUseSatisfaction) {
		this.menuUseSatisfaction = menuUseSatisfaction;
	}
	
	/**
	 * @return the menuLocation
	 */
	public String getMenuLocation() {
		return menuLocation;
	}

	/**
	 * @param menuLocation the menuLocation to set
	 */
	public void setMenuLocation(String menuLocation) {
		this.menuLocation = menuLocation;
	}

	/**
	 * @return the tagCode
	 */
	public String getTagCode() {
		return tagCode;
	}

	/**
	 * @param tagCode the tagCode to set
	 */
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}


	/**
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}

	/**
	 * 업로드 테마폴더안에 있는 메뉴이미지 주소를 반환한다.
	 * @param siteId
	 * @param theme
	 * @param suffix
	 * @return
	 */
	public String getMenuImageUrl(String siteId, String theme, String suffix){
		
		StringBuilder sb = new StringBuilder();
		sb.append(Constant.UPLOAD_PATH);
		sb.append("/");
		sb.append(siteId);
		sb.append("/theme/");
		sb.append(theme);
		sb.append("/menu/");
		sb.append(this.getMenuId());
		sb.append(suffix);
		return sb.toString();
	}

	/**
	 * 메인페이지 인지 여부를 반환한다.
	 * @return
	 */
	public boolean isHome() {
		return this.getMenuOrder() == 0 && this.getMenuDepth() == 1;
	}
	
	/**
	 * 메뉴 이미지 url을 세팅한다.
	 * @param theme
	 * @param forceTextMenu 텍스트 메뉴를 강제할지 여부
	 * @return
	 */
	public Menu populateMenuImageUrl(String siteId, String theme, boolean forceTextMenu){
		
		if( forceTextMenu ){
			this.setMenuGnbType("text");
			this.setMenuSnbType("text");
			this.setMenuTitleType("text");
		}
		
		if( "image".equals(this.getMenuGnbType()) ){
			this.setMenuGnbImageOnUrl(this.getMenuImageUrl(siteId, theme, this.getMenuGnbExtOn()));
			this.setMenuGnbImageOffUrl(this.getMenuImageUrl(siteId, theme, this.getMenuGnbExtOff()));
		}
		if( "image".equals(this.getMenuSnbType()) ){
			this.setMenuSnbImageOnUrl(this.getMenuImageUrl(siteId, theme, this.getMenuSnbExtOn()));
			this.setMenuSnbImageOffUrl(this.getMenuImageUrl(siteId, theme, this.getMenuSnbExtOff()));
		}
		if( "image".equals(this.getMenuTitleType()) ){
			this.setMenuTitleImageUrl(this.getMenuImageUrl(siteId, theme, this.getMenuTitleExt()));
		}
		return this;
	}
	
}
