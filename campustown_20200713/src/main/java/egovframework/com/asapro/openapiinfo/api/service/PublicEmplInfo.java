package egovframework.com.asapro.openapiinfo.api.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

public class PublicEmplInfo extends MultiSiteVO {
	private Integer seq; // 번호
	private Integer readnum; // 조회수
	private String regdate; // 등록일
	private String title; // 제목
	private String type01; // 공고유형1
	private String type02; // 공고유형2
	private String typeinfo01; // 공고유형추가정보1
	private String typeinfo02; // 공고유형추가정보2
	private String typeinfo03; // 공고유형추가정보3
	private String userid; // 사용자 아이디
	private String username; // 사용자 이름
	private String empmnsn; // 일련번호
	private String contents; // 내용
	private String deptCode; // 기관코드
	private String deptName; // 기관명
	private String enddate; // 마감일
	private String link01; // 링크01
	private String link02; // 링크02
	private String link03; // 링크03
	private String moddate; // 수정일

	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * @return the readnum
	 */
	public Integer getReadnum() {
		return readnum;
	}

	/**
	 * @param readnum
	 *            the readnum to set
	 */
	public void setReadnum(Integer readnum) {
		this.readnum = readnum;
	}

	/**
	 * @return the regdate
	 */
	public String getRegdate() {
		return regdate;
	}

	/**
	 * @param regdate
	 *            the regdate to set
	 */
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type01
	 */
	public String getType01() {
		return type01;
	}

	/**
	 * @param type01
	 *            the type01 to set
	 */
	public void setType01(String type01) {
		this.type01 = type01;
	}

	/**
	 * @return the type02
	 */
	public String getType02() {
		return type02;
	}

	/**
	 * @param type02
	 *            the type02 to set
	 */
	public void setType02(String type02) {
		this.type02 = type02;
	}

	/**
	 * @return the typeinfo01
	 */
	public String getTypeinfo01() {
		return typeinfo01;
	}

	/**
	 * @param typeinfo01
	 *            the typeinfo01 to set
	 */
	public void setTypeinfo01(String typeinfo01) {
		this.typeinfo01 = typeinfo01;
	}

	/**
	 * @return the typeinfo02
	 */
	public String getTypeinfo02() {
		return typeinfo02;
	}

	/**
	 * @param typeinfo02
	 *            the typeinfo02 to set
	 */
	public void setTypeinfo02(String typeinfo02) {
		this.typeinfo02 = typeinfo02;
	}

	/**
	 * @return the typeinfo03
	 */
	public String getTypeinfo03() {
		return typeinfo03;
	}

	/**
	 * @param typeinfo03
	 *            the typeinfo03 to set
	 */
	public void setTypeinfo03(String typeinfo03) {
		this.typeinfo03 = typeinfo03;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the empmnsn
	 */
	public String getEmpmnsn() {
		return empmnsn;
	}

	/**
	 * @param empmnsn
	 *            the empmnsn to set
	 */
	public void setEmpmnsn(String empmnsn) {
		this.empmnsn = empmnsn;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents
	 *            the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode
	 *            the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate
	 *            the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @return the link01
	 */
	public String getLink01() {
		return link01;
	}

	/**
	 * @param link01
	 *            the link01 to set
	 */
	public void setLink01(String link01) {
		this.link01 = link01;
	}

	/**
	 * @return the link02
	 */
	public String getLink02() {
		return link02;
	}

	/**
	 * @param link02
	 *            the link02 to set
	 */
	public void setLink02(String link02) {
		this.link02 = link02;
	}

	/**
	 * @return the link03
	 */
	public String getLink03() {
		return link03;
	}

	/**
	 * @param link03
	 *            the link03 to set
	 */
	public void setLink03(String link03) {
		this.link03 = link03;
	}

	/**
	 * @return the moddate
	 */
	public String getModdate() {
		return moddate;
	}

	/**
	 * @param moddate
	 *            the moddate to set
	 */
	public void setModdate(String moddate) {
		this.moddate = moddate;
	}

}
