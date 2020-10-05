<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.support.Constant"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	
<script>
jQuery(function($){
	
	//페이지 타이틀 처리
	var page_title = '';
	var total = $('a.active').length;

	$('a.active').each(function(index){
		if(total == 1){
			if(index == total-1 ){
				page_title += $(this).find('span').eq(0).text();
			}
		}else{
			if(index == total-1 ){
				page_title += $(this).text(); 
			}
		}
	});
	$('h3.page-title').text(page_title);
	
	//메뉴 location 처리
	var seo = '';
	//seo = '<li class="breadcrumb-item"><a href="#" >${currentSite.siteName}</a></li>';
	$('a.active').each(function(index){
		seo += '<li class="breadcrumb-item';
		if(index == total - 1 ){
			seo += ' active';
		}
		seo += '">';
		if(index < total - 1 ){
			seo += '<a href="#">';
		}
		if(index == 0 ){
			seo += $(this).find('span').eq(0).text();
		} else {
			seo += $(this).text(); 
		}
		if(index < total - 1 ){
			seo += '</a>';
		}
		seo += '</li>';
	});
	$('.breadcrumb').html(seo);
	
	//로그아웃
	$('#logout').on('click', function(e){
		e.preventDefault();
		$.ajax({
			url : '${ADMIN_PATH}/logout.do'
			, type : 'get'
			,dataType : 'json'
		}).then(function(result){
			if(result.success){
				swal({
	                type: 'success',
	                title: result.text,
	            }).then(function(){
	            	location.href = '${ADMIN_PATH}/login.do';
	            });
				//alert(data.text);
			} else {
				//...
				swal("로그아웃 실패!", "서버문제로 로그아웃에 실패하였습니다.", "danger")
				//alert('서버문제로 로그아웃에 실패하였습니다.');
			}
		}).fail(function(result){
			swal("로그아웃 실패!", "로그아웃에 실패하였습니다.[fail]", "danger")
		});
		
	});
	
	
	getNonOpenNoteCnt();
	//미확인 쪽지 수량
	function getNonOpenNoteCnt(){
		$.ajax({
			url : '${ADMIN_PATH}/note/nonOpenCnt.do'
			, type : 'get'
			,dataType : 'json'
		}).then(function(result){
			if(result.success){
				if(result.successCnt > 0){
					$('.nonOpenCnt').text(result.successCnt);
				} else {
					$('.nonOpenCnt').text('');
				}
			} else {
				//...
				console.error('미확인 쪽지 수량을 불러오지 못했습니다.');
			}
		}).fail(function(result){
			console.error('미확인 쪽지 수량확인 실패!![fail]');
		});
	}
		
});
</script>
	
	<!-- Top Bar Start -->
	<div class="topbar">
	
	    <nav class="navbar-custom">
	
	        <ul class="list-inline float-right mb-0 mr-3">
	        
	            <%-- <li class="list-inline-item dropdown notification-list">
	                <a class="nav-link dropdown-toggle arrow-none waves-effect" data-toggle="dropdown" href="#" role="button"
	                   aria-haspopup="false" aria-expanded="false">
	                    <i class="mdi mdi-email-outline noti-icon"></i>
	                    <span class="badge badge-danger noti-icon-badge">5</span>
	                </a>
	                <div class="dropdown-menu dropdown-menu-right dropdown-arrow dropdown-menu-lg">
	                    <!-- item-->
	                    <div class="dropdown-item noti-title">
	                        <h5><span class="badge badge-danger float-right">745</span>Messages</h5>
	                    </div>
	
	                    <!-- item-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        <div class="notify-icon"><img src="${CONTEXT_PATH }/assets/images/users/avatar-2.jpg" alt="user-img" class="img-fluid rounded-circle" /> </div>
	                        <p class="notify-details"><b>Charles M. Jones</b><small class="text-muted">Dummy text of the printing and typesetting industry.</small></p>
	                    </a>
	
	                    <!-- item-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        <div class="notify-icon"><img src="${CONTEXT_PATH }/assets/images/users/avatar-3.jpg" alt="user-img" class="img-fluid rounded-circle" /> </div>
	                        <p class="notify-details"><b>Thomas J. Mimms</b><small class="text-muted">You have 87 unread messages</small></p>
	                    </a>
	
	                    <!-- item-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        <div class="notify-icon"><img src="${CONTEXT_PATH }/assets/images/users/avatar-4.jpg" alt="user-img" class="img-fluid rounded-circle" /> </div>
	                        <p class="notify-details"><b>Luis M. Konrad</b><small class="text-muted">It is a long established fact that a reader will</small></p>
	                    </a>
	
	                    <!-- All-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        View All
	                    </a>
	
	                </div>
	            </li> --%>
	            
	            <li class="list-inline-item dropdown notification-list">
	                <a class="nav-link dropdown-toggle arrow-none waves-effect" data-toggle="dropdown" href="#" role="button"
	                   aria-haspopup="false" aria-expanded="false">
	                    <i class="mdi mdi-email-outline noti-icon"></i>
	                    <span class="badge badge-danger noti-icon-badge nonOpenCnt"></span>
	                </a>
	                <div class="dropdown-menu dropdown-menu-right dropdown-arrow dropdown-menu-lg">
	                    <!-- item-->
	                    <div class="dropdown-item noti-title">
	                        <h5><!-- <span class="badge badge-danger float-right">745</span> -->Messages</h5>
	                    </div>
	
	                    <!-- item-->
	                    <a href="${ADMIN_PATH}/note/reception/list.do" class="dropdown-item notify-item">
	                        <img src="${CONTEXT_PATH }/assets/images/note_reception.png" alt="받은쪽지함" class="img-fluid m-r-5" style="height: 20px;" /> 
	                        <span>받은쪽지함</span><span class="badge badge-danger float-right nonOpenCnt">5</span>
	                    </a>
	
	                    <!-- item-->
	                    <a href="${ADMIN_PATH}/note/transmit/list.do" class="dropdown-item notify-item">
	                        <img src="${CONTEXT_PATH }/assets/images/note_transmit.png" alt="보낸쪽지함" class="img-fluid m-r-5" style="height: 20px;" /> 
	                       	<span>보낸쪽지함</span>
	                    </a>
	
	                </div>
	            </li>
	
				<c:if test="${fn:length(authSiteList) > 1}">
	            <li class="list-inline-item dropdown notification-list">
	                <a class="nav-link dropdown-toggle arrow-none waves-effect" data-toggle="dropdown" href="#" role="button"
	                   aria-haspopup="false" aria-expanded="false">
	                    <i class="mdi mdi-monitor-multiple noti-icon"></i>
	                    <span class="badge badge-success noti-icon-badge">${fn:length(authSiteList)}</span>
	                </a>
	                <div class="dropdown-menu dropdown-menu-right dropdown-arrow dropdown-menu-lg">
	                    <!-- item-->
	                    <div class="dropdown-item noti-title">
	                        <h5><span class="badge badge-danger float-right"></span>Multi Site</h5>
	                    </div>
	                    
	                    <c:forEach items="${authSiteList}" var="item">
							<c:if test="${item.siteMain}">
								<!-- item-->
			                    <a href="/<%= Constant.MAIN_SITE_DISPLAY_ID %><%= Constant.ADMIN_PATH %>/login.do" class="dropdown-item notify-item">
			                        <div class="notify-icon bg-primary"><img src="${item.siteLogo.fileServletUrl}" alt="${item.siteName}" class="img-fluid " /></div>
			                        <p class="notify-details"><b>${item.siteName}</b><small class="text-muted">${item.siteDescription}</small></p>
			                    </a>
							</c:if>
							<c:if test="${!item.siteMain}">
								<!-- item-->
			                    <a href="/${item.siteId}<%= Constant.ADMIN_PATH %>/login.do" class="dropdown-item notify-item">
			                        <div class="notify-icon bg-primary"><img src="${item.siteLogo.fileServletUrl}" alt="${item.siteName}" class="img-fluid " /></div>
			                        <p class="notify-details"><b>${item.siteName}</b><small class="text-muted">${item.siteDescription}</small></p>
			                    </a>
							</c:if>
						
						</c:forEach>
	
						<%--
						<c:forEach items="${authSiteList}" var="item">
							<c:if test="${item.siteMain}">
								<c:if test="${not empty item.seperatedSiteDomains}">
									<!-- item-->
				                    <a href="http://${item.seperatedSiteDomains[0]}/<%= Constant.MAIN_SITE_DISPLAY_ID %><%= Constant.ADMIN_PATH %>.do" class="dropdown-item notify-item">
				                        <div class="notify-icon bg-primary"><img src="${item.siteLogo.fileServletUrl}" alt="${item.siteName}" class="img-fluid rounded-circle" /></div>
				                        <p class="notify-details"><b>${item.siteName}</b><small class="text-muted">${item.siteDescription}</small></p>
				                    </a>
								</c:if>
								<c:if test="${empty item.seperatedSiteDomains}">
									<!-- item-->
				                    <a href="/<%= Constant.MAIN_SITE_DISPLAY_ID %><%= Constant.ADMIN_PATH %>.do" class="dropdown-item notify-item">
				                        <div class="notify-icon bg-primary"><img src="${item.siteLogo.fileServletUrl}" alt="${item.siteName}" class="img-fluid rounded-circle" /></div>
				                        <p class="notify-details"><b>${item.siteName}</b><small class="text-muted">${item.siteDescription}</small></p>
				                    </a>
								</c:if>
							</c:if>
							<c:if test="${!item.siteMain}">
								<c:if test="${not empty item.seperatedSiteDomains}">
									<!-- item-->
				                    <a href="http://${item.seperatedSiteDomains[0]}/${item.siteId}<%= Constant.ADMIN_PATH %>.do" class="dropdown-item notify-item">
				                        <div class="notify-icon bg-primary"><img src="${item.siteLogo.fileServletUrl}" alt="${item.siteName}" class="img-fluid rounded-circle" /></div>
				                        <p class="notify-details"><b>${item.siteName}</b><small class="text-muted">${item.siteDescription}</small></p>
				                    </a>
								</c:if>
								<c:if test="${empty item.seperatedSiteDomains}">
									<!-- item-->
				                    <a href="/${item.siteId}<%= Constant.ADMIN_PATH %>.do" class="dropdown-item notify-item">
				                        <div class="notify-icon bg-primary"><img src="${item.siteLogo.fileServletUrl}" alt="${item.siteName}" class="img-fluid rounded-circle" /></div>
				                        <p class="notify-details"><b>${item.siteName}</b><small class="text-muted">${item.siteDescription}</small></p>
				                    </a>
								</c:if>
							</c:if>
						
						</c:forEach>
						 --%>
						
						
						
						<%--
	                    <!-- item-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        <div class="notify-icon bg-primary"><i class="mdi mdi-cart-outline"></i></div>
	                        <p class="notify-details"><b>Your order is placed</b><small class="text-muted">Dummy text of the printing and typesetting industry.</small></p>
	                    </a>
	
	                    <!-- item-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        <div class="notify-icon bg-success"><i class="mdi mdi-message"></i></div>
	                        <p class="notify-details"><b>New Message received</b><small class="text-muted">You have 87 unread messages</small></p>
	                    </a>
	
	                    <!-- item-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        <div class="notify-icon bg-warning"><i class="mdi mdi-martini"></i></div>
	                        <p class="notify-details"><b>Your item is shipped</b><small class="text-muted">It is a long established fact that a reader will</small></p>
	                    </a>
	
	                    <!-- All-->
	                    <a href="javascript:void(0);" class="dropdown-item notify-item">
	                        View All
	                    </a>
	                     --%>
	
	                </div>
	            </li>
				</c:if>
	
	            <li class="list-inline-item dropdown notification-list">
	            	<a class="nav-link dropdown-toggle arrow-none waves-effect" data-toggle="dropdown" href="#" role="button"
	                   aria-haspopup="false" aria-expanded="false">
	                    <i class="fa fa-power-off noti-icon"></i>
	                </a>
	                <%--
	                <a class="nav-link dropdown-toggle arrow-none waves-effect nav-user" data-toggle="dropdown" href="#" role="button"
	                   aria-haspopup="false" aria-expanded="false">
	                    <img src="${CONTEXT_PATH }/assets/images/users/avatar-1.jpg" alt="user" class="rounded-circle"> 
	                </a>--%>
	                <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
	                    <a class="dropdown-item" href="${ADMIN_PATH}/member/admin/profile/update.do"><i class="mdi mdi-account-circle m-r-5 text-muted"></i> Profile</a>
	                    <c:if test="${empty currentSite.seperatedSiteDomains}">
		                    <a class="dropdown-item" href="${APP_PATH}/home" target="_blank"><i class="mdi mdi-arrow-right-bold-hexagon-outline m-r-5 text-muted"></i> Site Go</a>
	                    </c:if>
	                    <c:if test="${not empty currentSite.seperatedSiteDomains}">
		                    <a class="dropdown-item" href="//${currentSite.seperatedSiteDomains[0]}${APP_PATH}/home" target="_blank"><i class="mdi mdi-arrow-right-bold-hexagon-outline m-r-5 text-muted"></i> Site Go</a>
	                    </c:if>
	                    <%--<a class="dropdown-item" href="#"><span class="badge badge-success pull-right">5</span><i class="mdi mdi-settings m-r-5 text-muted"></i> Settings</a>
	                    <a class="dropdown-item" href="#"><i class="mdi mdi-lock-open-outline m-r-5 text-muted"></i> Lock screen</a>--%>
	                    <a class="dropdown-item" href="${ADMIN_PATH}/logout.do" id="logout"><i class="mdi mdi-logout m-r-5 text-muted"></i> Logout</a>
	                </div>
	            </li>
	
	        </ul>
	
	        <ul class="list-inline menu-left mb-0">
	            <li class="list-inline-item">
	                <button type="button" class="button-menu-mobile open-left waves-effect">
	                    <i class="ion-navicon"></i>
	                </button>
	            </li>
	            <li class="hide-phone list-inline-item app-search">
	                <h3 class="page-title">${currentSite.siteName} &gt; Dashboard</h3>
	            </li>
	        </ul>
	
	        <div class="clearfix"></div>
	
	    </nav>
	
	</div>
	<!-- Top Bar End -->
