<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Date"%>
<%
Date now = new Date();
%>
<fmt:formatDate value="<%= now %>" pattern="yyyyMMdd" var="now"/>
<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	$('#bsnsRealm').val("");
	
	$('a[id^=bsns]').on('click', function(){
		//alert('ㅎㅎㅎ');
		//alert('codeName: '+$(this).text());
		var codeId = $(this).data('codeid');
		//alert('codeId: ' + codeId);
		$('#bsnsRealm').val(codeId);
		$('#srcBtn').trigger('click');
	});
});
</script>

<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	
	<div class="blue-title-box2">
		<dl class="clearfix">
			<c:set var="now" value="<%=new java.util.Date()%>" />							
			<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
			<fmt:formatDate var="nowMonth" value="${now}" pattern="MM" />
			
			<dt><p>서울 캠퍼스 타운<br><span>창업팀 현황</span></p><span>${nowYear }. ${nowMonth } 기준</span></dt>
			<dd>
				<p>서울소재&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><img src="${design.resource}/images/sub/blue-title-box_icon2.png" alt="">&nbsp;54</span>개 대학에서&nbsp;&nbsp;<span><img src="${design.resource}/images/sub/blue-title-box_icon3.png" alt="">&nbsp;${univListTot }</span>개 캠퍼스타운, <span>&nbsp;&nbsp;<img src="${design.resource}/images/sub/blue-title-box_icon2.png" alt="">&nbsp;<img src="${design.resource}/images/sub/blue-title-box_icon1.png" alt="">&nbsp;${startListTot }</span>개 창업팀이 활동중이며,<br>
					2020년내&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><img src="${design.resource}/images/sub/blue-title-box_icon4.png" alt="">&nbsp;500</span>개 창업팀 유치를 목표로 하고 있습니다.
				</p>
			</dd>
		</dl>
	</div>
	
	<form:form id="startupForm2" modelAttribute="startHpMngrSearch" action="${APP_PATH}/startup/empList" method="get">
	<form:hidden path="bsnsRealm" />
	<div class="search-line2 inbtn mgt25 clearfix">
		<p>
			<form:select path="areaId" title="검색조건 선택">
				<option value="">지역</option>
				<form:options items="${sigunguCodeList}" itemLabel="codeName" itemValue="codeId"/>
			</form:select>
		</p>
		<p>
			<form:select path="sc" title="검색조건 선택">
				<option value="">구분</option>
				<form:option value="univNm">소속대학교</form:option>
				<form:option value="empNm">이름</form:option>
				<form:option value="compNm">회사명</form:option>
				<form:option value="tel">연락처(회사)</form:option>
				<form:option value="phone">연락처(직원)</form:option>
				<form:option value="email">이메일</form:option>
			</form:select>
		</p>
		<p class="clearfix">
			<form:input path="sv" title="검색어 입력" />
			<a href="javascript:{}"
			   onclick="document.getElementById('startupForm2').submit(); return false;" id="srcBtn">
				<img src="${design.resource}/images/sub/t-searchstyle1-icon01_01.gif" alt="검색">
			</a>
		</p>
		<a href="${APP_PATH}/startup/list"><span>창업팀 조회로 이동</span></a>
	</div>
	<%-- 
	<div class="mgt25 cate">
		<span class="cate">
			<c:forEach items="${bsnsRealmCodeList}" var="item" varStatus="vs">
				<a href="#none" id="bsns${vs.index }" data-codeid="${item.codeId }"><b>${item.codeName }</b></a>
				<c:if test="${!vs.last }">&nbsp;&nbsp;|&nbsp;&nbsp;</c:if>
			</c:forEach>
		</span>
	</div>
	 --%>
	</form:form>
	<div class="mgt25 total">
		Total : ${paging.rowTotal}
	</div>


	<!-- startup list -->
	<div class="board-table table-warp style2">
		<table>
			<caption>창업 참여자 정보 조회</caption>
			<colgroup>
				<col style="width: 80px;">
				<col style="width: 120px;">
				<col style="width: 100px;">
				<col style="width: auto;">
				<col style="width: auto;">
				<col style="width: 200px;">
				<col style="width: 170px;">
				<col style="width: auto;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">업종</th>
					<th scope="col">지역</th>
					<th scope="col">소속 대학교</th>
					<th scope="col">회사명</th>
					<th scope="col">이름</th>
					<th scope="col">연락처</th>
					<th scope="col">이메일</th>
				</tr>
			</thead>
			<tbody>
				
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="8" class="text-center">등록된 창업팀 참여자가 없습니다.</td>
					</tr>
				</c:if>
				
				
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<tr>
						<td>${paging.rowTop - vs.index}</td>
						<td>
							${asapro:codeName(item.induty, indutyCodeList)}
						</td>
						<td>${asapro:codeName(item.areaGuCd, sigunguCodeList)}</td>
						<td>${item.univNm }</td>
						<td>
							${item.compNm }
							<%-- <c:if test="${not empty item.snsHp }"> --%>
							<a href="${APP_PATH}/startup/intro?compId=${item.compId}" target="_blank">
								<img class="app-icon" src="${design.resource}/images/sub/homepage.gif" alt="">
							</a>
							<%-- </c:if> --%>
						</td>
						<td>${item.empNm }
						<c:if test="${item.rprsntvYn eq 'Y'}"><b>(대표)</b></c:if>
						<c:if test="${item.seoulId != null and item.seoulId != '' and item.seoulIdMsgExpsrYn eq 'Y'}">
							<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.seoulId}&reply=true" class="noteWrite" title="쪽지쓰기"><img src="${design.resource }/images/sub/sub01_limg33.gif" alt="쪽지"></a>
						</c:if>
						</td>
						<td>
							<!-- 2020.07.01 jaseo - 기존에 임직원 테이블에 있던 회사 전화번호 컬럼을 회사 기본정보 테이블로 옮김 -->
							<%-- <c:if test="${item.compTel ne '000' and item.telExpsrYn eq 'Y'}">${item.tel }</c:if> --%>
							<c:if test="${item.compTel ne '--'}">${item.compTel }</c:if>
							<c:if test="${empty item.compTel or item.compTel eq '--'}">-</c:if>
							
							<%-- <c:if test="${item.telExpsrYn eq 'Y' and item.phoneExpsrYn eq 'Y'}"> / </c:if> --%>
							<c:if test="${not empty item.phone}"> / </c:if>
							
							<%-- <c:if test="${item.tel eq '000' and (empty item.phone or item.phoneExpsrYn eq 'N')}"> 비공개 </c:if> --%>
							<c:if test="${not empty item.phone and item.phoneExpsrYn eq 'Y'}">${item.phone }</c:if>
							<c:if test="${not empty item.phone and item.phoneExpsrYn eq 'N'}">비공개</c:if>
						</td>
						<td>
							<c:if test="${item.emailExpsrYn eq 'Y'}">${item.email }</c:if>
							<c:if test="${item.emailExpsrYn eq 'N'}">비공개</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
		<!-- paging -->
		<div class="paging">
			<c:out value="${paging.userImageTag}" escapeXml="false"/>
		</div>
		
	</div>  <!-- END .area -->
</div>   <!-- END #content -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />