<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="egovframework.com.asapro.member.admin.service.AdminMember"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="egovframework.com.asapro.support.ApplicationContextProvider"%>
<%@ page import="egovframework.com.asapro.archive.service.ArchiveCategorySearch"%>
<%@ page import="egovframework.com.asapro.archive.service.ArchiveService"%>
<%@ page import="egovframework.com.asapro.archive.service.ArchiveCategory"%>
<%@ page import="egovframework.com.asapro.site.service.Site"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%
ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
ArchiveService archiveService = applicationContext.getBean(ArchiveService.class);
Site currentSite = (Site)request.getAttribute("currentSite");
//========== 시작 : Archive ==========
ArchiveCategorySearch archiveCategorySearch = new ArchiveCategorySearch();
archiveCategorySearch.setPaging(false);
archiveCategorySearch.setSitePrefix(currentSite.getSitePrefix());
List<ArchiveCategory> archiveCategoryList = archiveService.getArchiveCategoryList(archiveCategorySearch);
//========== 끝 : Archive ==========
//AdminMember currentAdmin = (AdminMember)session.getAttribute("currentAdmin");
%>
<c:set var="archiveCategoryList" value="<%=archiveCategoryList %>"/>
<script>
jQuery(function($){
	
	//자식메뉴 없는 부모 엘리먼트 제거
   	$('.has_third').each(function(idx, el){
   		if( !$('.list-unstyled > li', el).size() ){
   			$(el).remove();
   		}
   	});
	//자식메뉴 없는 부모 엘리먼트 제거
	$('.has_sub').each(function(idx, el){
		if( !$('.list-unstyled > li', el).size() ){
			$(el).remove();
		}
	});
});

</script>

	<!-- ========== Left Sidebar Start ========== -->
	<div class="left side-menu">
		<button type="button" class="button-menu-mobile button-menu-mobile-topbar open-left waves-effect">
			<i class="ion-close"></i>
		</button>

		<!-- LOGO -->
		<div class="topbar-left">
			<div class="text-center">
				<!--<a href="index.html" class="logo">Admiry</a>-->
				<a href="${ADMIN_PATH}/dashboard.do" class="logo"><img src="${currentSite.siteLogo.fileServletUrl}" <%-- onerror="this.src='${CONTEXT_PATH }/assets/images/logo.png'"--%> height="50" alt="${currentSite.siteName} logo"></a>
			</div>
		</div>

		<div class="sidebar-inner slimscrollleft">
		
		    <div class="user-details">
		        <div class="text-center">
		           <%--  <img src="${UPLOAD_PATH}/member/${sessionScope.currentAdmin.adminId}/profile.jpg" alt="${sessionScope.currentAdmin.adminName}" onerror="this.src='${CONTEXT_PATH }/assets/images/users/profile.jpg'" class="rounded-circle"> --%>
		            <img src="${CONTEXT_PATH }/assets/images/users/profile.jpg" alt="${sessionScope.currentAdmin.adminName}" onerror="this.src='${CONTEXT_PATH }/assets/images/users/profile.jpg'" class="rounded-circle">
		        </div>
		        <div class="user-info">
		            <h4 class="font-16">${sessionScope.currentAdmin.adminName}</h4>
		            <h6 class="font-12 font-weight-light">(Role : <c:forTokens items="${sessionScope.currentAdmin.adminRole.roleName}" delims="," var="item" varStatus="vs"><c:if test="${vs.index > 0 }"><br/>,</c:if>${item }</c:forTokens>)</h6>
		            <span class="text-muted user-status"><i class="fa fa-dot-circle-o text-success"></i> Online</span>
		        </div>
		    </div>
		
		    <div id="sidebar-menu">
		        <ul>
					<li class="menu-title">Main</li>
					
					${asapro:printAdminMenuLink(currentSite, currentAdmin, '대시보드', '/dashboard.do', 'GET', 'waves-effect', '1', 'ti-dashboard')}
					
					<c:if test="${currentSite.siteMain}">
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-layers"></i> <span> 사이트 관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '사이트 목록', '/site/list.do', 'GET', '', '', '')}
						</ul>
					</li>
					</c:if>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-user"></i> <span> 회원관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '관리자 목록', '/member/admin/list.do', 'GET', '', '', '')}
							<%--${asapro:printAdminMenuLink(currentSite, currentAdmin, '사용자 목록', '/member/user/list.do', 'GET', '', '', '')} --%>
						</ul>
					</li>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="dripicons-store"></i> <span> 조직관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '기관 목록', '/organization/organization/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '부서 목록', '/organization/department/list.do', 'GET', '', '', '')}
							<%--${asapro:printAdminMenuLink(currentSite, currentAdmin, '직원 목록', '/organization/staff/list.do', 'GET', '', '', '')} --%>
						</ul>
					</li>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-clipboard"></i> <span> 게시판 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '게시판목록', '/board/config/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '댓글', '/comment/board/list.do', 'GET', '', '', '')}
						</ul>
					</li>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-shield"></i> <span> 보안관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '권한목록', '/capability/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '롤목록', '/role/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '접속가능 IP', '/allowip/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '임시허용 IP목록', '/allowipTemp/list.do', 'GET', '', '', '')}
							<%-- ${asapro:printAdminMenuLink(currentSite, currentAdmin, '접속가능 MAC', '/allowmac/list.do', 'GET', '', '', '')} --%>
						</ul>
					</li>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-layout"></i> <span> 홈페이지 관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '메뉴', '/menu/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '콘텐츠관리', '/content/list.do', 'GET', '', '', '')}
<%-- 							${asapro:printAdminMenuLink(currentSite, currentAdmin, '메인비주얼', '/banner/list.do?bnType=mainVisual', 'GET', '', '', '')} --%>
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '배너', '/banner/list.do?bnType=banner', 'GET', '', '', '')}
<%-- 							${asapro:printAdminMenuLink(currentSite, currentAdmin, '팝업존', '/banner/list.do?bnType=popupzone', 'GET', '', '', '')} --%>
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '레이어팝업', '/banner/list.do?bnType=layer', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '창업팀홍보배너', '/banner/list.do?bnType=static', 'GET', '', '', '')}
						</ul>
					</li>
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-files"></i> <span> 파일관리시스템 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '파일정보목록', '/fileinfo/list.do', 'GET', '', '', '')}
						</ul>
					</li>
					
					${asapro:printAdminMenuLink(currentSite, currentAdmin, ' 캠퍼스타운 관리', '/univHpMngr/list.do', 'GET', 'waves-effect', '1', 'ti-cup')}
					${asapro:printAdminMenuLink(currentSite, currentAdmin, ' 창업팀 관리', '/startHpMngr/list.do', 'GET', 'waves-effect', '1', 'ti-crown')}
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-stamp"></i> <span> 정책협의회 관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '정책협의회 통계', '/policyCfrnc/stat.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '정책협의회 목록', '/policyCfrnc/list.do', 'GET', '', '', '')}
						</ul>
					</li>

					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-headphone-alt"></i> <span> 상담 및 자문 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '법률 자문', '/counsel/legal_counsel/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '시제품 제작 및 자문', '/counsel/make_counsel/list.do', 'GET', '', '', '')}
						</ul>
					</li>

					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-zip"></i> <span> <!-- 정보 및 자료관리 -->입주공간 관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							<c:forEach items="${archiveCategoryList}" var="archiveCategory">
								<c:set var="archiveCategoryLink" value="/archive/${archiveCategory.catCustomType }/list.do?arcCategory=${archiveCategory.catId}" />
								<c:set var="archiveCatName" value="${archiveCategory.catName}"/>
								${asapro:printAdminMenuLink(currentSite, currentAdmin, archiveCatName, archiveCategoryLink, 'GET', '', '', '')}
							</c:forEach>
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '정보자료실 설정', '/archive/category/list.do', 'GET', '', '', '')}
						</ul>
					</li>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-pin-alt"></i> <span> <!-- 신청 및 예약 -->공유공간 관리</span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							<li class="has_third">
								<a href="javascript:void(0);" class="waves-effect"><span> 대관/대여 관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
								<ul class="list-unstyled">
									${asapro:printAdminMenuLink(currentSite, currentAdmin, '등록/설정', '/rental/list.do', 'GET', '', '', '')}
									${asapro:printAdminMenuLink(currentSite, currentAdmin, '신청현황', '/rental/reservation/list.do', 'GET', '', '', '')}
								</ul>
							</li>
							<%--
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '단체관람', '/viewing/reservation/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '교육프로그램', '/education/reservation/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '자원봉사', '/volunteer/reservation/list.do', 'GET', '', '', '')}
							 --%>
						</ul>
					</li>
<%--					<li class="has_sub">--%>
<%--					<a href="javascript:void(0);" class="waves-effect"><i class="ti-zip"></i> <span>신청서 관리</span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>--%>
<%--						<ul class="list-unstyled">--%>
<%--						${asapro:printAdminMenuLink(currentSite, currentAdmin, '2020 X-TECH창업경진대회', '/application/form/list.do', 'GET', '', '', '')}--%>
<%--						</ul>--%>
<%--					</li>--%>
					
<%-- 					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-check-box"></i> <span> 온라인투표 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '투표목록', '/poll/list.do', 'GET', '', '', '')}
						</ul>
					</li> --%>
<%-- 					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-headphone-alt"></i> <span> 민원 및 신고 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '사이버신문고', '/cyberComplaint/report/list.do', 'GET', '', '', '')}
						</ul>
					</li> --%>
					 
<%-- 					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-direction-alt"></i> <span> 오픈API관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '오픈API정보 설정', '/openapiinfo/list.do', 'GET', '', '', '')}
						</ul>
					</li> --%>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-package"></i> <span> 공통관리 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '코드분류', '/code/category/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '상세코드', '/code/item/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '메일대상', '/emailTarget/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '공통콘텐츠', '/common/content/list.do', 'GET', '', '', '')}
						</ul>
					</li>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-bar-chart"></i> <span> 통계 / 로그 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							<li class="has_third">
								<a href="javascript:void(0);" class="waves-effect"><span> 사이트 접속통계 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
								<ul class="list-unstyled">
									${asapro:printAdminMenuLink(currentSite, currentAdmin, '접속자', '/statistics/session.do', 'GET', '', '', '')}
									${asapro:printAdminMenuLink(currentSite, currentAdmin, 'OS', '/statistics/os.do', 'GET', '', '', '')}
									${asapro:printAdminMenuLink(currentSite, currentAdmin, '브라우저', '/statistics/browser.do', 'GET', '', '', '')}
									${asapro:printAdminMenuLink(currentSite, currentAdmin, '국가', '/statistics/country.do', 'GET', '', '', '')}
									${asapro:printAdminMenuLink(currentSite, currentAdmin, '메뉴', '/statistics/menu.do', 'GET', '', '', '')}
								</ul>
							</li>
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '만족도 조사', '/satisfaction/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '사용자정보 조회로그', '/statistics/user/inquireLog/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '관리자 접속로그', '/statistics/admin/accessLog/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '관리자정보 조회로그', '/statistics/admin/inquireLog/list.do', 'GET', '', '', '')}
						</ul>
					</li>
					
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-settings"></i> <span> 설정 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '사이트설정', '/config/site/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '파일설정', '/config/media/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '메뉴설정', '/config/design/list.do', 'GET', '', '', '')}
							<c:if test="${currentSite.siteMain}">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '보안설정', '/config/security/list.do', 'GET', '', '', '')}
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '전역설정', '/config/global/list.do', 'GET', '', '', '')}
							</c:if>
							<%--${asapro:printAdminMenuLink(currentSite, currentAdmin, '워터마크', '/config/watermark/list.do', 'GET', '', '', '')} --%>
						</ul>
					</li>
					<!-- 
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="ti-calendar"></i><span> 일정 </span></a>
						<ul class="list-unstyled">
							${asapro:printAdminMenuLink(currentSite, currentAdmin, '일정관리', '/schedule/calendar.do', 'GET', '', '', '')}
						</ul>
					</li>
					 -->
					
					<!-- //3댑스 매뉴처리 마크업
					<li class="has_sub">
						<a href="javascript:void(0);" class="waves-effect"><i class="dripicons-store"></i> <span> 3뎁스 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
						<ul class="list-unstyled">
							<li class="has_third">
								<a href="javascript:void(0);" class="waves-effect"><span> 2-1 </span> <span class="pull-right"><i class="mdi mdi-chevron-right"></i></span></a>
								<ul class="list-unstyled">
									<li><a href="${ADMIN_PATH}/member/admin/list.do">2-1-1</a></li>
									<li><a href="${ADMIN_PATH}/member/admin/list2.do">2-1-2</a></li>
								</ul>
							</li>
							<li><a href="#dddd">2-2</a></li>
						</ul>
					</li>
					 -->
				</ul>
			</div>
			<div class="clearfix"></div>
		</div> <!-- end sidebarinner -->
	</div>
	<!-- Left Sidebar End -->
