<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	$("[data-srcdiv='"+$('#srcDiv').val()+"']").addClass('on');
	
	$('.srcL').on('click', function(e){
		e.preventDefault();
		
		//alert("click~!!");
		// hidden값 셋팅 (srcDiv)
		// alert("srcDiv: " + srcDiv);
		var srcDiv = $(this).data('srcdiv');
		$('#srcDiv').val(srcDiv);
		
		//alert("sHref: " + $('#sHref').val()+"?univId="+$('#univId').val()+"&srcDiv="+srcDiv);
		$("#spaceForm").attr("action", $('#sHref').val()+"?univId="+$('#univId').val()+"&srcDiv="+srcDiv).submit();
	});	
 	
	
	$('.a-but').on('click', function(e){
		e.preventDefault();
		$(this).prev().prev().trigger('click');
	});
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar3.jsp" charEncoding="UTF-8" />
 
	<div id="subl-center">
		<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
		<form:form id="spaceForm" modelAttribute="univHpMngrSearch" action="" method="get">
			<form:hidden path="univId" value="${univInfo.univId }" />
			<form:hidden path="srcDiv" />
			<%-- <form:input path="arcCategory" value="${paging1.result.arcCategory }"/> --%>
			<%-- <input type="text" id="sHref" value="${APP_PATH}/university/${univvId}?univId=${univInfo.univId}" style="width:400px;"> --%>
			<input type="hidden" id="sHref" value="${APP_PATH}/university/${univvId}" style="width:400px;">
			
			<div class="subl-tab2">
				<ul class="clearfix">
					<li class="srcL" data-srcdiv="move"><a href="#">입주공간</a></li>
					<li class="srcL" data-srcdiv="share"><a href="#">공유공간</a></li>
				</ul>
			</div>
		</form:form>
		
		<!-- 입주공간 조회 -->
		<c:if test="${spaceForm.srcDiv eq 'move' }">
			<c:if test="${empty paging1.result}">
				<tr>
					<td colspan="4" class="text-center">등록된 게시글이 없습니다.</td>
				</tr>
			</c:if>
		
			<div class="subl-photo-list2">
				<ul class="clearfix">
				<c:forEach items="${paging1.result }" var="item" varStatus="vs">
					<li>
						<a href="${APP_PATH}/archive/space/${archiveCategory.catId}/${item.arcId}" target="_blank">
							<div class="photo">
								<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }"
									onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
							</div>
							<ul>
								<li class="title">
									<%-- ${asapro:codeName(item.arcMetaCode1, arcMetaCode1List)} --%> 
									${asapro:depName(item.arcUnivId,departmentList)}
								</li>
								<li>${item.arcTitle}</li>
								<li class="a-but"><span>바로가기</span></li>
							</ul>
						</a>
					</li>
				</c:forEach>
				</ul>
			</div>
			
			<!-- paging -->
			<c:if test="${not empty paging1.result}">
				<div class="paging1">
					<c:out value="${paging1.userImageTag2}" escapeXml="false"/>
				</div>
			</c:if>
			
		</c:if>
		
		
		
		<!-- 공유공간 조회 -->
		<c:if test="${spaceForm.srcDiv eq 'share' }">
			<c:if test="${empty paging2.result}">
				<tr>
					<td colspan="4" class="text-center">등록된 게시글이 없습니다.</td>
				</tr>
			</c:if>
			
			<div class="subl-photo-list2">
				<ul class="clearfix">
				<c:forEach items="${paging2.result }" var="item" varStatus="vs">
					<li>
						<!-- /site/main/rental/view?rentId=1 -->
						<a href="${APP_PATH}/rental/view?rentId=${item.rentId}" target="_blank">
							<div class="photo">
								<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }"
									onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
							</div>
							<ul>
								<li class="title">
									<%-- ${asapro:codeName(item.arcMetaCode1, arcMetaCode1List)} --%> 
									${asapro:depName(item.rentCate1,departmentList)}
								</li>
								<li>${item.rentTitle}</li>
								<li class="a-but"><span>바로가기</span></li>
							</ul>
						</a>
					</li>
				</c:forEach>
				</ul>
			</div>
			
			<!-- paging -->
			<c:if test="${not empty paging2.result}">
				<div class="paging1">
					<c:out value="${paging2.userImageTag2}" escapeXml="false"/>
				</div>
			</c:if>
			
		</c:if>
		
	</div>
</div>   <!-- END #content -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />