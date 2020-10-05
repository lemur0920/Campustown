<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%
//댓글목록 출력용

%>
<script src="${CONTEXT_PATH }/design/common/js/jquery.timeago.js"></script>
<script src="${CONTEXT_PATH }/design/common/js/comment.js"></script>
<script>
jQuery(function($){
	
	//페이징 버튼
	/* $('.commentWrapper .bbs_paging a').on('click', function(e){
		e.preventDefault();
		$('.commentWrapper').load($(this).attr('href'));	
	}); */
	
	//페이징 버튼
	$('.commentWrapper .paging a').on('click', function(e){
		e.preventDefault();
		$('.commentWrapper').load($(this).attr('href'));	
	});
	
	//정렬 버튼
	$('.order_wrap a').on('click', function(e){
		e.preventDefault();
		$('.commentWrapper').load($(this).attr('href') + '&direction=' + $(this).data('direction'));	
	});

});
</script>

<ul class="sub03-list-table">
	<li class="bg">댓글 <span class="color-red">${paging.rowTotal }</span></li>
	
	<c:if test="${not empty paging.result }">
		<c:forEach items="${paging.result }" var="item" varStatus="vs">
			<li>
				<ul class="bg-enter">
					<li>
						${item.cmtSnsUserName}
						<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.cmtUserId}&reply=true" class="noteWrite" title="쪽지쓰기"><img src="${design.resource }/images/sub/sub01_limg33.gif" alt="쪽지"></a>
						<span class="and">｜</span><fmt:formatDate value="${item.cmtRegDate}" pattern="yyyy-MM-dd HH:mm:ss" /> 
						
						<c:set var="cookiename1" value="comm_reco_${currentSite.siteId}_${item.cmtId}" />
						<c:set var="className" value="heartWhite" />
						<c:if test="${not empty cookie[cookiename1]['value'] }"><c:set var="className" value="heartYellow" /></c:if>
						<p class="btnA fr comm-recommend" id="comm_recommend_${item.cmtId}" data-cmtid="${item.cmtId}" style="cursor: pointer;">
							<span class="${className }">${item.cmtRecommend}</span>
						</p>
					</li>
					<li>${asapro:nl2br(item.cmtContent, false)}</li>
				</ul>
			</li>
		</c:forEach>
	</c:if>
	
	<li class="bg">
		<form id="comment_form" action="${APP_PATH}/comment${API_PATH}/new" method="post">
			${SECURITY_TOKEN_TAG}
			<%-- <input type="hidden" name="cmtPageUrl" value="<%= AsaproUtils.getCurrentUrl(request, false, false) %>" /> --%>
			<input type="hidden" name="cmtMenuId" value="${currentMenu.menuId}" />
			<input type="hidden" name="cmtModule" value="${param.cmtModule}" />
			<input type="hidden" name="cmtModuleSub" value="${param.cmtModuleSub}" />
			<input type="hidden" name="cmtModCategory" value="${param.cmtModCategory}" />
			<input type="hidden" name="cmtModItemId" value="${param.cmtModItemId}" />
			<input type="hidden" name="cmtTitle" value="${param.cmtTitle}" />
			<input type="hidden" name="cmtParentId" value="" />
			<input type="hidden" name="cmtLoginType" value="${currentUser.userLoginType }" />
			
			<div class="write-review">
				<label for="sb-in1">댓글 달기</label>
				<textarea name="cmtContent" id="sb-in1" cols="30" rows="10" maxlength="1000"></textarea>
				<a href="#non" class="comment-submit"><img src="${design.resource }/images/sub/sub03_img1.gif" alt="등록" /></a>
			</div>
			<div class="write-review">
				<span class="txt_cnt" id="counter">0 / 1000</span>
				<div class="arr"></div>
				</div>
		</form>
		
		
	</li>
</ul>
<!-- paging -->
<c:out value="${paging.userImageTag}" escapeXml="false"/>








<%-- 
<div class="reply_wrap">
	<div class="reply_inner">
		<div class="top">
			<div class="tit"><strong>의견작성</strong><span>로그인 후 작성이 가능합니다.</span></div>
			<div class="input_wrap">
				<form id="comment_form" action="${APP_PATH}/comment${API_PATH}/new" method="post">
					${SECURITY_TOKEN_TAG}
					<input type="hidden" name="cmtPageUrl" value="<%= AsaproUtils.getCurrentUrl(request, false, false) %>" />
					<input type="hidden" name="cmtMenuId" value="${currentMenu.menuId}" />
					<input type="hidden" name="cmtModule" value="${param.cmtModule}" />
					<input type="hidden" name="cmtModuleSub" value="${param.cmtModuleSub}" />
					<input type="hidden" name="cmtModCategory" value="${param.cmtModCategory}" />
					<input type="hidden" name="cmtModItemId" value="${param.cmtModItemId}" />
					<input type="hidden" name="cmtTitle" value="${param.cmtTitle}" />
					<input type="hidden" name="cmtParentId" value="" />
					<input type="hidden" name="cmtLoginType" value="${currentUser.userLoginType }" />
					
					<div class="txt_box">
						<textarea name="cmtContent" id="" cols="30" rows="10" maxlength="1000"></textarea>
					</div>
					<a href="#non" class="comment-submit">저장하기</a>
					<span class="txt_cnt" id="counter">1 / 1000</span>
					<div class="arr"></div>
				</form>
			</div>
		</div>
		<div class="bot">
			<div class="tit">
				<span>총 <strong>${paging.rowTotal }</strong>개<span class="pc">의 의견이 있습니다.</span></span>
				<div class="order_wrap">
					<a href="${APP_PATH}/comment/list?${commentSearch.queryString }" data-direction="DESC" <c:if test="${commentSearch.sortDirection eq 'DESC'}">class="on"</c:if>>최신순</a>
					<a href="${APP_PATH}/comment/list?${commentSearch.queryString }" data-direction="ASC" <c:if test="${commentSearch.sortDirection eq 'ASC'}">class="on"</c:if>>과거순</a>
				</div>
			</div>
			<ul>
				<c:if test="${not empty paging.result }">
					<c:forEach items="${paging.result }" var="item" varStatus="vs">
						<li>
							<div class="idpart">
								<div class="l">
									<div class="sns_circle">
										<c:if test="${not empty item.cmtProfileImage }"><img alt="${item.cmtSnsUserName}의 이미지" src="${item.cmtProfileImage}"></c:if>
										
									</div>
									<c:set var="loginType" value=""/>
									<c:choose>
										<c:when test="${item.cmtLoginType eq '2'}"><c:set var="loginType" value="facebook"/></c:when>
										<c:when test="${item.cmtLoginType eq '4'}"><c:set var="loginType" value="naver"/></c:when>
										<c:when test="${item.cmtLoginType eq '8'}"><c:set var="loginType" value="cacao"/></c:when>
										<c:when test="${item.cmtLoginType eq '16'}"><c:set var="loginType" value="phone"/></c:when>
										<c:when test="${item.cmtLoginType eq '32'}"><c:set var="loginType" value="ipin"/></c:when>
									</c:choose>
									<!-- facebook, naver, cacao, phone, ipin -->
									<div class="snslogo sl_${fn:substring(item.cmtLoginType,0,2) }">${item.cmtLoginType}</div>
								</div>
								<div class="r">
									<p class="name">${asapro:maskingNameRight(item.cmtSnsUserName) }</p>
									<span class="date"><fmt:formatDate value="${item.cmtRegDate}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
								</div>
							</div>
							<div class="conpart">
								<p>${asapro:nl2br(item.cmtContent, false)}</p>
							</div>
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</div><!-- //.bot -->
	</div>
	
	<!-- paging -->
	<c:out value="${paging.userTextTag}" escapeXml="false"/>
 --%>	
</div><!-- //.reply_wrap -->