<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
int nowYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()) );
%>
<c:set var="nowYear" value="<%= nowYear %>" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
});
</script>
	
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

		<!-- content -->
		<div class="slide-bg">
			<div class="sub-slide">
				<!-- <ul class="bx-sub"> -->
				<ul class="">
					<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<c:if test="${vs.count eq 1 }">
						<li>
							<div class="bxs-cont clearfix">
								<div class="bxs-left"><img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'"/></div>
								<div class="bxs-right">
									<div class="tips">
										<c:forEach items="${arcMetaCode2List }" var="metaCode2" varStatus="vs2">
											<c:set var="chk" value=""/>
											<c:forTokens items="${item.arcMetaCode2 }" delims="," var="selMeta2" varStatus="vs3">
												<c:if test="${metaCode2.codeId eq selMeta2 }">
													<c:set var="chk" value="on"/>
												</c:if>
											</c:forTokens>
											<a href="#n" class="${chk }">${metaCode2.codeName }</a>
										</c:forEach>
									</div>
									<p class="pro-name"><img src="${design.resource }/images/sub/slide_ico01.gif" alt="제목" /> ${item.arcTitle }</p>
									<p class="pro-explain">${item.poliShortDescription }</p>
									<a href="${item.permLink }" class="view-detail">자세히 보기</a>
								</div>
							</div>
						</li>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="gap40"></div>
		<div class="best-gal2">
			<ul class="clearfix">
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<c:if test="${vs.count > 1 }">
						<li>
							<a href="${item.permLink }">
								<div class="gal-img"><img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'"/></div>
								<div class="text">
									<p class="name">${item.arcTitle }</p>
									<p class="explain">${item.poliShortDescription }</p>
								</div>
								<div class="bg-ico">
									<div class="ico"><img src="${design.resource }/images/sub/go_bg.png" alt="" /></div>
								</div>
							</a>
						</li>
					</c:if>
				</c:forEach>
				
				<c:if test="${empty paging.result}">
					<!-- 게시물이 없을 때 -->
					<li class="nodata">게시물이 없습니다.</li>
				</c:if>
				
			</ul>
		</div>
		
	
		<!-- paging -->
		<%-- <c:out value="${paging.userImageTag}" escapeXml="false"/> --%>
		
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />