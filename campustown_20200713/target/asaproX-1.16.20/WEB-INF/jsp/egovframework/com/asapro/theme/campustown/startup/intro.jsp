<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	/* 
	$('.aMenu').on('click', function(e){
		e.preventDefault();
		//alert("click~!!");
		//var compId = $(this).data('compid');
		//$('#compId').val(compId);
		//alert("compId: "+$('#compId').val());
		//alert("href: "+$(this).attr("href"));
		var href= $(this).attr("href");
		
		//$("#carinvalForm").attr("method","post");
		//$("#carinvalForm").attr("action","${APP_PATH}/apply/applyCarnival/userFinal").submit();
		$("#startupForm").attr("action",$(this).attr("href")).submit();
		//$('#startIntroForm').href(href);
		//$('#startIntroForm').submit();
		
	});
	 */
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar2.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

<%-- <form:form id="startIntroForm" modelAttribute="startHpMngrSearch" action="${APP_PATH}/startup/${startId}?compId=${compId }" method="get"> --%>
<%-- 
<form:form id="startupForm" modelAttribute="startHpMngrSearch" action="" method="get">
	<form:hidden path="compId" />
</form:form>
 --%>	
	<div class="founding-team-content">
		<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
		<%-- 
		<div class="subl-tab">
			<ul class="clearfix">
				<li class="on"><a href="${APP_PATH }/startup/intro?compId=${startCompInfo.compId}">회사소개</a></li>
				<li><a href="#none">새소식</a></li>
			</ul>
		</div>
		 --%>
			
			<h3 class="subl-title1" id="startup-1">회사소개</h3>
			<div class="subl-two-table clearfix">
				<div class="subl-table">
					<table>
						<caption>회사소개 관심 분야</caption>
						<colgroup>
							<col style="width:31%;">
							<col style="width:auto;">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">회사명</th>
								<td>${startCompInfo.compNm }</td>
							</tr>
							<tr>
								<th scope="row">업종</th>
								<td>${asapro:codeName(startCompInfo.induty, indutyCodeList)}</td>
							</tr>
							<tr>
								<th scope="row">설립년도</th>
								<td>${fn:replace(startCompInfo.fondDt, '-', '. ' )}</td>
							</tr>
							<tr>
								<th scope="row">임직원수</th>
								<td>${startCompInfo.empCnt }</td>
							</tr>
							
						</tbody>
					</table>
				</div>
				<div class="subl-table">
					<table>
						<caption>회사소개 관심 분야</caption>
						<colgroup>
							<col style="width:31%;">
							<col style="width:auto;">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">사업분야</th>
								<td>
									<%-- <span class="art">${startCompInfo.bsnsRealm}</span> --%>
									<c:choose>
										<c:when test="${fn:length(startCompInfo.bsnsRealm) > 43}">
											<c:out value="${fn:substring(startCompInfo.bsnsRealm,0,44)}" />...
		          							</c:when>
										<c:otherwise>
											<c:out value="${startCompInfo.bsnsRealm}" />
										</c:otherwise>
									</c:choose>
									<%-- 
									<c:set var="doneLoop" value="false"/>
									<c:forEach items="${startCompInfo.bsnsRealm}" var="itemArr" varStatus="vs">
										<c:if test="${not doneLoop}">
											<c:set var="bsnsRealmList" value="${asapro:codeName(itemArr,bsnsRealmCodeList) }"/>
											${fn:trim(bsnsRealmList)}<c:if test="${vs.count > 0}">,</c:if>
											<c:if test="${vs.count == 3}"> 
												<c:set var="doneLoop" value="true"/> 
											</c:if> 
										</c:if>
									</c:forEach> ...
									 --%>
									
								</td>
							</tr>
							<%-- 
							<tr>
								<th scope="row">설립년도</th>
								<td>${startCompInfo.fondDt }</td>
							</tr>
							<tr>
								<th scope="row">임직원수</th>
								<td>${total }명</td>
							</tr>
							 --%>
							<tr>
								<th scope="row">위치</th>
								<td>${asapro:nl2br(startCompInfo.address, false)}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="subl-table">
				<table>
					<caption>회사소개 관심 분야</caption>
					<colgroup>
						<col style="width:15%;">
						<col style="width:auto;">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">Vision</th>
							<td>${startCompInfo.vsn }</td>
						</tr>
						<tr>
							<th scope="row">소개</th>
							<td>${startCompInfo.intrcn }
							<!--
							<br /><a href="#" class="ablue-next mt20">에디터로 편집 가능</a>
							-->
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<h3 class="subl-title1" id="startup-2">주요 서비스 및 홍보</h3>
			<div class="subl-table">
				<table>
					<caption>주요 서비스 및 홍보</caption>
					<colgroup>
						<col style="width:15%;">
						<col style="width:auto;">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">주요 서비스</th>
							<td>
								${startCompInfo.mainSvc }
							</td>
						</tr>
						<tr>
							<th scope="row">홍보</th>
							<td>
								${startCompInfo.prMssage }
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<h3 class="subl-title1" id="startup-3">창업활동정보</h3>
			<c:if test="${not empty startComp.invtAmt or not empty  startComp.emplyCnt or not empty startComp.saleAmt or not empty startComp.intellProp}">
			<div class="subl-table">
				<table>
					<caption>창업활동정보</caption>
					<colgroup>
						<col style="width:15%;">
						<col style="width:*%;">
					</colgroup>
					<tbody>
						<tr>
							
							<th scope="row">투자액</th>
							<td><fmt:formatNumber value="${startComp.invtAmt }" pattern="#,###" />만원</td>
						</tr>
						<tr>
							<th scope="row">고용인수</th>
							<td><fmt:formatNumber value="${startComp.emplyCnt }" pattern="#,###" />명</td>
						</tr>
						<tr>
							<th scope="row">매출액</th>
							<td><fmt:formatNumber value="${startComp.saleAmt }" pattern="#,###" />만원</td>
						</tr>
						<tr>
							<th scope="row">지적재산</th>
							<td><fmt:formatNumber value="${startComp.intellProp }" pattern="#,###" />건</td>
						</tr>
					</tbody>
				</table>
			</div>
			</c:if>
			
			<c:if test="${not empty startCompInfo.snsFace || not empty startCompInfo.snsTwit || not empty startCompInfo.snsBlog || not empty startCompInfo.snsHp || not empty startCompInfo.snsInsta || not empty startCompInfo.snsKakao}">
			<h3 class="subl-title1" id="startup-3">SNS</h3>
			<div class="subl-table">
				<table>
					<caption>SNS</caption>
					<colgroup>
						<col style="width:15%;">
						<col style="width:auto;">
					</colgroup>
					<tbody>
						<c:if test="${not empty startCompInfo.snsFace }">
							<tr>
								<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/face.gif" alt="페이스북 아이콘" />페이스북</th>
								<td><a href="${startCompInfo.snsFace }" target="_blank">${startCompInfo.snsFace }</a></td>
							</tr>
						</c:if>
						<c:if test="${not empty startCompInfo.snsTwit }">
							<tr>
								<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/twitor.gif" alt="트위터 아이콘" />트위터</th>
								<td><a href="${startCompInfo.snsTwit }" target="_blank">${startCompInfo.snsTwit }</a></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty startCompInfo.snsBlog }">
							<tr>
								<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/blog.gif" alt="블로그 아이콘" />블로그</th>
								<td><a href="${startCompInfo.snsBlog }" target="_blank">${startCompInfo.snsBlog }</a></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty startCompInfo.snsHp }">
							<tr>
								<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/homepage.gif" alt="홈페이지 아이콘" />홈페이지</th>
								<td><a href="${startCompInfo.snsHp }" target="_blank">${startCompInfo.snsHp }</a></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty startCompInfo.snsInsta }">
							<tr>
								<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/insta.png" alt="인스타 아이콘" />인스타</th>
								<td><a href="${startCompInfo.snsInsta }" target="_blank">${startCompInfo.snsInsta }</a></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty startCompInfo.snsKakao }">
							<tr>
								<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/kakao.png" alt="카카오 아이콘" />카카오</th>
								<td><a href="${startCompInfo.snsKakao }" target="_blank">${startCompInfo.snsKakao }</a></td>
							</tr>
						</c:if>
						
					</tbody>
				</table>
			</div>
			</c:if>
			
			
			
			
			<h3 class="subl-title1" id="startup-4">임직원 현황</h3>
			<c:if test="${not empty startEmpInfo }">
				<div class="employee-box clearfix">
					<div class="employee-txt">
						<div class="txt-box">
							<i class="img">
								<c:if test="${not empty startEmpInfo.file3Info && startEmpInfo.file3Info.fileId > 0 }">
									<img src="${startEmpInfo.file3Info.fileServletUrl }" alt="${startEmpInfo.file3Info.fileAltText }"
										onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
								</c:if>
								<c:if test="${empty startEmpInfo.file3Info}">
									<img src="${CONTEXT_PATH }/assets/images/users/profile.jpg" alt="대체 이미지">
								</c:if>
							</i>
								
							<div class="txt">
								<h3 class="tit">대표 ${startEmpInfo.empNm }</h3>
								<dl>
									<dt><img src="${design.resource }/images/sub/tel-icon.png" alt="연락처 아이콘"></dt>
									<dd>
										${startCompInfo.compTel }
										<%-- 
										<c:if test="${startEmpInfo.telExpsrYn eq 'Y'}">${startEmpInfo.tel }</c:if>
										<c:if test="${startEmpInfo.telExpsrYn eq 'N'}">비공개</c:if>
										 --%>
										<c:if test="${not empty startCompInfo.compTel and startEmpInfo.phoneExpsrYn eq 'Y'}"> / </c:if>
										
										<c:if test="${startEmpInfo.phoneExpsrYn eq 'Y'}">${startEmpInfo.phone }</c:if> 
										<c:if test="${startEmpInfo.phoneExpsrYn eq 'N'}">비공개</c:if> 
									</dd>	
								</dl>
								<dl>
									<dt><img src="${design.resource }/images/sub/mail-icon.png" alt="메일 아이콘"></dt>
									<dd>
										<c:if test="${startEmpInfo.emailExpsrYn eq 'Y'}">${startEmpInfo.email }</c:if>
										<c:if test="${startEmpInfo.emailExpsrYn eq 'N'}">비공개</c:if>
									</dd>
								</dl>
								<dl>
									<dt><img src="${design.resource }/images/sub/message-icon.png" alt="쪽지 아이콘"></dt>
									<dd>
									<c:if test="${startEmpInfo.seoulId != null and startEmpInfo.seoulId != '' and startEmpInfo.seoulIdMsgExpsrYn eq 'Y'}">
										<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${startEmpInfo.seoulId}&reply=true" class="noteWrite" title="쪽지쓰기">쪽지보내기</a>
									</c:if>
									</dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="employee-logo">
						<c:if test="${not empty startComp.file2Info && startComp.file2Info.fileId > 0 }">
							<img src="${startComp.file2Info.fileServletUrl }" alt="${startComp.file2Info.fileAltText }"
						 	     onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
				 	    </c:if> 
						<c:if test="${empty startComp.file2Info}">
							<img src="${design.resource}/images/sub/imhtext-list_img02.jpg" alt="대체 이미지">
				 	    </c:if> 
					</div>
				</div>
			
				<div class="board-table no-bor-new mt30">
					<table>
						<caption>임직원 현황</caption>
						<colgroup>
							<col style="width: 15%">
							<col style="width: 20%">
							<col style="width: 20%">
							<col style="width: auto">
							<col style="width: 20%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">직위</th>
								<th scope="col">이름</th>
								<th scope="col">담당업무</th>
								<th scope="col">연락처</th>
								<th scope="col">이메일</th>
							</tr>
						</thead>
						<c:if test="${empty startEmpList}">
							<tbody>
								<tr>
									<td colspan="5" class="text-center">등록된 임직원이 없습니다.</td>
								</tr>
							</tbody>
						</c:if>
						<tbody>
							<c:forEach items="${startEmpList}" var="item" varStatus="vs">
								<tr>
									<td>${item.ofcps }</td>
									<td>${item.empNm }
										<c:if test="${item.seoulId != null and item.seoulId != '' and item.seoulIdMsgExpsrYn eq 'Y'}"> 
											<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.seoulId}&reply=true" class="noteWrite" title="쪽지쓰기"><img src="${design.resource }/images/sub/sub01_limg33.gif" alt="쪽지"></a>
										</c:if>
									</td>
									<td>${item.chrgJob }</td>
									<td>
										<c:if test="${item.phoneExpsrYn eq 'Y'}">
											${item.phone }
										</c:if>
										<c:if test="${item.phoneExpsrYn eq 'N'}">
											비공개
										</c:if>
									</td>
									<td>
										<c:if test="${item.emailExpsrYn eq 'Y'}">	
											<a href="#" class="art col-blue">${item.email }</a>
										</c:if>
										<c:if test="${item.emailExpsrYn eq 'N'}">	
											<a href="#" class="art col-blue">비공개</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		<!--</div> -->
	
<!-- </div> --> <!--  End content -->



<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />