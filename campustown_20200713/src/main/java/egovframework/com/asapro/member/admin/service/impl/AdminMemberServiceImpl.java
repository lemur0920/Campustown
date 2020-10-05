/**
 * 
 */
package egovframework.com.asapro.member.admin.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

//import egovframework.com.asapro.config.service.Config;
//import egovframework.com.asapro.config.service.ConfigService;
//import egovframework.com.asapro.config.service.TextData;
//import egovframework.com.asapro.fileinfo.service.FileInfoService;
//import egovframework.com.asapro.group.service.GroupMapper;
//import egovframework.com.asapro.mail.service.MailService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberDelete;
import egovframework.com.asapro.member.admin.service.AdminMemberDeleteSearch;
import egovframework.com.asapro.member.admin.service.AdminMemberMapper;
import egovframework.com.asapro.member.admin.service.AdminMemberSearch;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.member.admin.service.AdminPassword;
import egovframework.com.asapro.member.admin.service.AdminSiteRoleRel;
import egovframework.com.asapro.member.admin.service.Recover;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 관리자 서비스
 * @author yckim
 * @since 2018. 4. 27.
 *
 */
@Service
public class AdminMemberServiceImpl extends EgovAbstractServiceImpl implements AdminMemberService{
	
	@Autowired
	private AdminMemberMapper adminMemberMapper;
	
//	@Autowired
//	private GroupMapper groupMapper;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	
//	@Autowired 
//	private FileInfoService fileInfoService;
	
//	@Autowired
//	private ConfigService configService;
	
	@Autowired
	private HttpServletRequest request;
	
//	@Autowired
//	private MailService mailService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 관리자 추가
	 */
	@Override
	public String insertAdminMember(AdminMember adminMemberForm) {
		adminMemberForm.setAdminActive(true);//활성
		adminMemberForm.setAdminLock(false);//잠김아님
		adminMemberForm.setAdminRegdate(new Date());
		adminMemberForm.setAdminPassword(egovPasswordEncoder.encryptPassword(adminMemberForm.getAdminPassword()));
		adminMemberForm.setAdminLoginLastDate(new Date());
		adminMemberForm.setAdminPWLastUpdated(new Date());
		int insertedCnt = adminMemberMapper.insertAdminMember(adminMemberForm);
		
		//관리자 사이트별 역할 릴레이션 추가
		for(AdminSiteRoleRel adminSiteRoleRel : adminMemberForm.getAdminSiteRoleRelList() ){
			if(StringUtils.isNotBlank(adminSiteRoleRel.getSitePrefix()) && StringUtils.isNotBlank(adminSiteRoleRel.getRoleCode()) ){
				adminSiteRoleRel.setAdminId(adminMemberForm.getAdminId());
				adminMemberMapper.insertAdminSiteRoleRel(adminSiteRoleRel);
			}
		}
		
		//프로필이미지 처리
		if( insertedCnt > 0 ){
			//관리자 추가될때 프로필 파일 입력되면 처리
			if( adminMemberForm.getAdminProfileImage() != null && !adminMemberForm.getAdminProfileImage().isEmpty() ){
//				this.updateAdminMemberProfileImage(adminMemberForm);
			} 
			//관리자이 추가될때 프로필 파일이 없으면 기본 파일로 넣는다.
			else {
				adminMemberForm.setAdminProfileImageRemove(true);
//				this.updateAdminMemberProfileImage(adminMemberForm);
			}
			
//			Config memberConfig = configService.getConfig("global");
			
		}
		
		return adminMemberForm.getAdminId();
	}
	
	/**
	 * 관리자조회
	 */
	@Override
	public AdminMember getAdminMember(AdminMember adminMember) {
		//if(StringUtils.isBlank(adminMember.getSitePrefix()) ){
		//	Site currentSite = (Site)request.getAttribute("currentSite");
		//	adminMember.setSitePrefix(currentSite.getSitePrefix());
		//}
		return adminMemberMapper.selectAdminMember(adminMember);
	}

	/**
	 * 관리자조회
	 */
	@Override
	public AdminMember getAdminMember(String adminId) {
		AdminMember adminMemberDB = adminMemberMapper.selectAdminMemberById(adminId);
		return adminMemberDB;
	}

	/**
	 * 관리자목록 조회
	 */
	@Override
	public List<AdminMember> getAdminMemberList(AdminMemberSearch adminMemberSearch) {
		List<AdminMember> list = adminMemberMapper.selectAdminMemberList(adminMemberSearch); 
		return list;
	}
	
	/**
	 * 관리자목록 토탈
	 */
	@Override
	public int getAdminMemberListTotal(AdminMemberSearch adminMemberSearch) {
		return adminMemberMapper.selectAdminMemberListTotal(adminMemberSearch);
	}

	/**
	 * 관리자정보수정
	 */
	@Override
	public int updateAdminMember(AdminMember adminMemberForm) {
		int updatedCnt = adminMemberMapper.updateAdminMember(adminMemberForm);
		
		//관리자 사이트별 역할 릴레이션 삭제
		adminMemberMapper.deleteAdminSiteRoleRel(adminMemberForm.getAdminId());
		
		//관리자 사이트별 역할 릴레이션 추가
		for(AdminSiteRoleRel adminSiteRoleRel : adminMemberForm.getAdminSiteRoleRelList() ){
			if(StringUtils.isNotBlank(adminSiteRoleRel.getSitePrefix()) && StringUtils.isNotBlank(adminSiteRoleRel.getRoleCode()) ){
				adminSiteRoleRel.setAdminId(adminMemberForm.getAdminId());
				adminMemberMapper.insertAdminSiteRoleRel(adminSiteRoleRel);
			}
		}
				
		//프로필이미지 처리
		if( updatedCnt > 0 ){
			if( adminMemberForm.isAdminProfileImageRemove() ){
				//프로필 이미지 삭제 -> 기본이미지로 대체
//				this.updateAdminMemberProfileImage(adminMemberForm);
			} else {
				//관리자 수정될때 프로필 파일 입력되면 처리
				if( adminMemberForm.getAdminProfileImage() != null && !adminMemberForm.getAdminProfileImage().isEmpty() ){
//					this.updateAdminMemberProfileImage(adminMemberForm);
				} 
				//관리자이 수정될때 프로필 파일이 없으면 현재파일 유지
				//else {
					//do nothing
				//}
			}
		}
		return updatedCnt;
	}

	/**
	 * 관리자삭제
	 */
	@Override
	public int deleteAdminMember(String adminId) {
		//관리자가 속해있는 그룹 연결 정보도 삭제
//		groupMapper.deleteGroupMemberByMemberId(adminId);
//		FileUtils.deleteQuietly(new File(request.getSession().getServletContext().getRealPath("/") + Constant.UPLOAD_PATH + "/member/admin/" + adminId));
		
		//관리자 사이트별 역할 릴레이션 삭제
		adminMemberMapper.deleteAdminSiteRoleRel(adminId);
		
		return adminMemberMapper.deleteAdminMember(adminId);
	}
	
	/**
	 * 복수관리자삭제
	 */
	@Override
	public int deleteAdminMember(String[] adminIds) {
		int result = 0;
		for(String adminId : adminIds){
			result += deleteAdminMember(adminId);
		}
		return result;
	}

	/**
	 * 비밀번호 변경
	 */
	@Override
	public int updateAdminMemberPassword(AdminPassword adminPasswordForm) {
		adminPasswordForm.setNewPassword(egovPasswordEncoder.encryptPassword(adminPasswordForm.getNewPassword()));
		return adminMemberMapper.updateAdminMemberPassword(adminPasswordForm);
	}

	/**
	 * 마지막 로그인 일시 수정
	 */
	@Override
	public int updateAdminMemberLastLoginDate(AdminMember adminMember) {
		adminMember.setAdminLoginLastDate(new Date());
		return adminMemberMapper.updateAdminMemberLastLoginDate(adminMember);
	}
	
	/**
	 * 계정의 잠김여부를 반환한다.
	 */
	@Override
	public boolean isLock(String loginId) {
		return adminMemberMapper.selectAdminMemberLock(loginId);
	}

	/**
	 * 로그인 실패 누적데이터 리셋
	 */
	@Override
	public int resetLoginFailInfo(AdminMember adminMember) {
		//로그인 실패 관련 데이터 리셋
		adminMember.setAdminLock(false);
		adminMember.setAdminLoginFailCount(0);
		adminMember.setAdminLoginFailDate(null);
		return adminMemberMapper.resetLoginFailInfo(adminMember);
	}

	/**
	 * 로그인 실패 횟수 증가
	 */
	@Override
	public int addLoginFailCount(String loginId) {
		return adminMemberMapper.addLoginFailCount(loginId);
	}

	/**
	 * 로그인 실패 회수 조회
	 */
	@Override
	public int getLoginFailCount(String loginId) {
		return adminMemberMapper.selectLoginFailCount(loginId);
	}
	
	/**
	 * 계정 잠김 처리, 잠김 일시 등록
	 */
	@Override
	public int updateLockAndFailDate(String loginId) {
		AdminMember adminMember = new AdminMember();
		adminMember.setAdminId(loginId);
		adminMember.setAdminLock(true);
		adminMember.setAdminLoginFailDate(new Date());
		
		return adminMemberMapper.updateLockAndFailDate(adminMember);
	}
	
	/**
	 * 로그인 마지막 실패 일시를 조회한다.
	 */
	@Override
	public Date getLoginFailDate(String loginId) {
		return adminMemberMapper.selectLoginFailDate(loginId);
	}
	
	/**
	 * 관리자 상태를 수정한다.
	 */
	@Override
	public int updateAdminMemberStatus(AdminMember adminMemberForm) {
		return adminMemberMapper.updateAdminMemberStatus(adminMemberForm);
	}

	
	//==============================================================================================================
	//=====================================  관리자 개인정보 수정  =================================================
	//==============================================================================================================
	
	/**
	 * 관리자 프로파일을 수정한다.
	 */
	@Override
	public int updateAdminMemberProfile(AdminMember adminMemberForm) {
		int updatedCnt = adminMemberMapper.updateAdminMemberProfile(adminMemberForm);
				
		//프로필이미지 처리
		if( updatedCnt > 0 ){
			if( adminMemberForm.isAdminProfileImageRemove() ){
				//프로필 이미지 삭제 -> 기본이미지로 대체
//				this.updateAdminMemberProfileImage(adminMemberForm);
			} else {
				//관리자 수정될때 프로필 파일 입력되면 처리
				if( adminMemberForm.getAdminProfileImage() != null && !adminMemberForm.getAdminProfileImage().isEmpty() ){
//					this.updateAdminMemberProfileImage(adminMemberForm);
				} 
				//관리자이 수정될때 프로필 파일이 없으면 현재파일 유지
				//else {
					//do nothing
				//}
			}
		}
		return updatedCnt;
		
	}





	

}