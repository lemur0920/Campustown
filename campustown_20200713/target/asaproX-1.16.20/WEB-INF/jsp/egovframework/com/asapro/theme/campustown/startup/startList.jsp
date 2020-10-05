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
<!-- 
<style>
.spanBsns {
	float: left;
	display: inline-block;
	width: 800px;
	height:50px;
	margin-left: 100px;
	background-color: blue;
}

.spanTot {
	float: right;
	width: 200px;
	height:50px;
	background-color: red;
}
</style>
 -->
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
	
	<form:form id="startupForm" modelAttribute="startHpMngrSearch" action="${APP_PATH}/startup/list" method="get">
	<form:hidden path="bsnsRealm" />
	<!-- <div class="search-line inbtn mgt25 clearfix"> -->
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
				<form:option value="compNm">창업팀명</form:option>
				<form:option value="empNm">대표명</form:option>
				<form:option value="univNm">캠퍼스타운명</form:option>
			</form:select>
		</p>
		<p class="clearfix">
			<form:input path="sv" title="검색어 입력" />
			<a href="javascript:{}"
			   onclick="document.getElementById('startupForm').submit(); return false;" id="srcBtn">
				<img src="${design.resource}/images/sub/t-searchstyle1-icon01_01.gif" alt="검색">
			</a>
		</p>
		<a href="${APP_PATH}/startup/empList"><span>창업팀 사람찾기</span></a>
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
	<div class="imhtext-list-s1 twoBox">
		
		<c:if test="${empty paging.result}">
			<p style="text-align: center; margin-top: 20px;">등록된 게시글이 없습니다.</p>
			<!-- <tr>
				<td colspan="4" class="text-center">등록된 게시글이 없습니다.</td>
			</tr> -->
		</c:if>
		
		<c:forEach items="${paging.result}" var="item" varStatus="vs">
			<c:if test="${(vs.count % 2) == 1}">
				<ul class="clearfix">
			</c:if>
			<li>
				<p class="top-text">${item.univNm }</p>
				<dl class="text1">
					<dt>
							<a href="${APP_PATH}/startup/intro?compId=${item.compId}" target="_blank">
								<c:if test="${not empty item.file1Info && item.file1Info.fileId > 0 }">
									<img src="${item.file1Info.fileServletUrl}" alt="${item.file1AltText}" 
										onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
								</c:if>
								<c:if test="${empty item.file1Info}">
									<img src="${design.resource}/images/sub/imhtext-list_img02.jpg" alt="대체 이미지">
								</c:if>
							</a>
					</dt>
					<dd>
						<span class="tit">
							<a href="${APP_PATH}/startup/intro?compId=${item.compId}" target="_blank">
								${item.compNm } <%-- (대표: ${item.empNm }) - ${item.univNm } --%>
							</a>
						</span>
						<p class="name-text">
							<span class="bold">대표:</span>${item.empNm }
						</p>
						<p>
							<c:set var="ment" value="${asapro:nl2br(item.ment, false)}" />
							<c:choose>
								<c:when test="${fn:length(ment) > 129}">
									<c:out value="${fn:substring(ment,0, 130)}" escapeXml="false"/>...
	   							</c:when>
								<c:otherwise>
									<c:out value="${ment}" escapeXml="false"/>
								</c:otherwise>
							</c:choose>
						</p>
					</dd>
				</dl>
				<div class="text2">
					<p>
						<span><b>투자액</b>(<fmt:formatNumber value="${item.invtAmt }" pattern="#,###" />만원)</span>
						<span><b>매출액</b>(<fmt:formatNumber value="${item.saleAmt }" pattern="#,###" />만원)</span>
						<span><b>고용인수</b>(<fmt:formatNumber value="${item.emplyCnt }" pattern="#,###" />명)</span>
						<span><b>지적재산</b>(<fmt:formatNumber value="${item.intellProp }" pattern="#,###" />건) </span>
						<%--  
						<b>투자액</b>(${item.invtAmt}만원)<b>&nbsp;|&nbsp;</b> 
						<b>매출액</b>(${item.saleAmt}만원)<b>&nbsp;|&nbsp;</b> 
						<b>고용인수</b>(${item.emplyCnt}명)<b>&nbsp;|&nbsp;</b>
						<b>지적재산</b>(${item.intellProp}건)
						 --%>
					</p>
					<!-- 추가한 부분 2-->
					<%-- 
					<p>
						사업분야 : ${asapro:codeName(item.bsnsRealm, bsnsRealmCodeList) } 
					</p>
					 --%>
					<!-- 추가한 부분 1-->
					<%--  
					<p>
						관심분야 :
						<c:set var="doneLoop" value="false"/>
						<c:forEach items="${item.intrstRealm}" var="itemArr" varStatus="vs">
							<c:if test="${not doneLoop}">
								<c:set var="intrstRealmList" value="${asapro:codeName(itemArr,intrstRealmCodeList) }"/>
								${fn:trim(intrstRealmList)}<c:if test="${vs.count > 0}">,</c:if>
								<c:if test="${vs.count == 3}"> 
									<c:set var="doneLoop" value="true"/> 
								</c:if> 
							</c:if>
						</c:forEach> ... 
					</p>
					 --%>
				</div>
				<p class="updata"><b>Update </b>
					<fmt:parseDate value="${item.updDt }" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
					<fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd"/>
				</p>
			</li>	
			<c:if test="${(vs.count % 2) == 0 || vs.last}">
				</ul>
			</c:if>
				
		</c:forEach>
	</div>
	
		<!-- paging -->
		<div class="paging">
			<c:out value="${paging.userImageTag}" escapeXml="false"/>
		</div>
		
	<!-- </div> -->  <!-- END .area -->
<!-- </div> -->   <!-- END #content -->


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />