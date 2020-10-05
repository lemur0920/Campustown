/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.support.annotation.PrivacyHistory;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 관리자 회원정보 조회,수정,삭제 이력관리 AOP Advice
 * @author yckim
 * @since 2019. 01. 03.
 *
 */
public class AdminInquireLogAspect {
	
	@Autowired
	private StatisticsService statisticsService;
	
	
	public void afterAdminInquire(JoinPoint joinPoint) throws IllegalAccessException, JsonGenerationException, JsonMappingException, IOException{
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		PrivacyHistory privacyHistory = method.getAnnotation(PrivacyHistory.class);
		
		//실행메소드에 히스토리 어노테이션이 있고 값이 있을경우만 로그 기록
		if(privacyHistory == null || StringUtils.isBlank(privacyHistory.moduleType()) || StringUtils.isBlank(privacyHistory.history()) ){
			return;
		} 
		
		//이렇게 사용하면 어플리케이션 아무대서나 현재 리퀘스트의 인스턴스를 획득 가능
		//response는 안되니까 뻘짓하지말자
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
		String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		StatisticsMemberInquireLog statisticsMemberInquireLog = new StatisticsMemberInquireLog();
		statisticsMemberInquireLog.setInqWorkerId(currentAdmin.getAdminId());
		statisticsMemberInquireLog.setInqWorkerName(currentAdmin.getAdminName());
		statisticsMemberInquireLog.setInqUrl(requestUri);
		statisticsMemberInquireLog.setInqRemoteIp(AsaproUtils.getRempoteIp(request));
		statisticsMemberInquireLog.setInqRegDate(new Date());
		statisticsMemberInquireLog.setInqWork(privacyHistory.history());
		
		
		//String className = joinPoint.getTarget().getClass().getName();
		//String methodName = joinPoint.getSignature().getName();
		
		
//		System.out.println("===================>>>>>>" + privacyHistory.moduleType());
//		System.out.println("===================>>>>>>" + privacyHistory.history());
		
		String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		Object[] parameterValues = joinPoint.getArgs();
		String deleteIds = null;
		
		int size = parameterNames.length;
		Map<String, Object> map =  null;
		for(int i = 0; i<size; i++){
			if( parameterValues[i] == null ){
				continue;
			}
			if( ExtendedModelMap.class.isAssignableFrom(parameterValues[i].getClass()) ){
				map = ((ExtendedModelMap)parameterValues[i]).asMap();
			}else if(parameterValues[i].getClass().isArray() ){
				deleteIds = Arrays.toString(((String[]) parameterValues[i]));
				statisticsMemberInquireLog.setInqTargetId(deleteIds);
			}
		}
		
		AdminMember targetMember = null;
		if(map.get("adminMemberForm") != null){
			targetMember = (AdminMember) map.get("adminMemberForm");
		}
		
		if(targetMember != null ){
			statisticsMemberInquireLog.setInqTargetId(targetMember.getAdminId());
			statisticsMemberInquireLog.setInqTargetName(targetMember.getAdminName());
		}
		
/*		if(requestUri.contains("/admin/list.do") ){
			//statisticsMemberInquireLog.setInqWork("회원 목록 조회");
			statisticsMemberInquireLog.setInqWork(privacyHistory.history());
		}else{
			if("POST".equalsIgnoreCase(request.getMethod()) ){
				//등록, 수정은 BindingResult 사용에 따라 처리 해줘야함
				//일단은 패스
				if(requestUri.contains("/admin/insert.do") ){
					statisticsMemberInquireLog.setInqWork("회원 추가");
				} else if(requestUri.contains("/admin/update.do") ){
					statisticsMemberInquireLog.setInqWork("회원 수정");
//					statisticsMemberInquireLog.setInqTargetId(inqTargetId);
//					statisticsMemberInquireLog.setInqTargetName(inqTargetId);
				} else if(requestUri.contains("/admin/delete.do") ){
					statisticsMemberInquireLog.setInqWork("회원 삭제");
					statisticsMemberInquireLog.setInqTargetId(deleteIds);
//					statisticsMemberInquireLog.setInqTargetName(inqTargetId);
				}
			}else if("GET".equalsIgnoreCase(request.getMethod()) ){
				if(requestUri.contains("/admin/update.do") ){
					statisticsMemberInquireLog.setInqWork("회원 조회");
//					statisticsMemberInquireLog.setInqTargetId(inqTargetId);
//					statisticsMemberInquireLog.setInqTargetName(inqTargetId);
				}
			}
			
		}*/
		
		//로그 저장
		//aop 설정에서 포인트컷으로 설정하려다 그냥 작업내역값이 있는경우만 처리하도록 함
		if(StringUtils.isNotBlank(statisticsMemberInquireLog.getInqWork()) ){
			statisticsService.insertStatisticsAdminInquireLog(statisticsMemberInquireLog);
		}
		
	}
	
	
}
