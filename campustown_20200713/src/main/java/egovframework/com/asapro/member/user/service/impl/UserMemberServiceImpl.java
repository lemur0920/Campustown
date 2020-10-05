/**
 * 
 */
package egovframework.com.asapro.member.user.service.impl;

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
import egovframework.com.asapro.member.admin.service.Recover;
import egovframework.com.asapro.member.user.service.UserMemberMapper;
import egovframework.com.asapro.member.user.service.UserMemberService;
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
 * 사용자 서비스
 * @author yckim
 * @since 2020. 2. 24.
 *
 */
@Service
public class UserMemberServiceImpl extends EgovAbstractServiceImpl implements UserMemberService{
	
	@Autowired
	private UserMemberMapper userMemberMapper;
	
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
	





	

}