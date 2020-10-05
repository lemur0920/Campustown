<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	$('.search-btn').on('click', function(e){
		$('#rentalSearch').submit();
	});
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<!-- content -->

	<!--  페이지별 내용 -->
	<div class="search-line2 inbtn clearfix">
		<form:form modelAttribute="rentalSearch" cssClass="form-inline" action="${APP_PATH}/rental/list" method="get">
			<p>
				<c:if test="${not empty rentCate2List}">
				<form:select path="rentCate2" title="공간유형">
					<option value="">지역</option>
					<form:options items="${rentCate2List}" itemLabel="codeName" itemValue="codeId"/>
				</form:select>
				</c:if>
			</p>
			<p>
				<c:if test="${not empty rentCate1List}">
				<form:select path="rentCate1" title="시설구분1">
					<option value="">캠퍼스타운</option>
					<form:options items="${rentCate1List}" itemLabel="univNm" itemValue="univId"/>
				</form:select>
				</c:if>
			</p>
			<p>
				<form:select path="sc" title="검색조건">
					<option value="">전체</option>
					<form:option value="RENT_TITLE" label="입주공간명" />
					<form:option value="RENT_DESCRIPTION" label="내용" />
				</form:select>	
			</p>
			<p class="clearfix">
				<form:input path="sv" title="검색어" placeholder=""/>
				<a href="#n" class="search-btn"><img src="${design.resource }/images/sub/t-searchstyle1-icon01_01.gif" alt="검색버튼"></a>
			</p>
		</form:form>
	</div>
	
	<div class="mgt25 total">
		Total : ${paging.rowTotal}
	</div>
	
	<div class="img-list">
		<ul class="clearfix">
			<c:if test="${empty paging.result}">
				<li>
					<p>등록된 대관시설이 없습니다.</p>
				</li>
			</c:if>
			<c:forEach items="${paging.result}" var="item" varStatus="vs">
				<li>
					<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
						<a href="${APP_PATH}/rental/view?rentId=${item.rentId}">
							<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText}" style="height:143px" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage300x300.jpg'" />
						</a>
					</c:if>
					<div>
						<div class="text-p">
							<p><span class="tit">
								<c:forEach items="${rentCate1List }" var="cate1">
									<c:if test="${cate1.univId eq item.rentCate1 }">
										${cate1.univNm }
									</c:if>
								</c:forEach>
								</span>
							</p>
							<p><span>${item.rentTitle }</span></p>
							<a href="${APP_PATH}/rental/view?rentId=${item.rentId}"><span>자세히보기</span></a>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
						
						
<%-- 						<p class="p1">${item.rentShortDescription }</p>
						<dl>
							<dt>수용인원 : </dt>
							<dd>최소 ${item.rentResMinNumber }명  ~ 최대 ${item.rentResMaxNumber }명</dd>
						</dl>
						<dl>
							<dt>면적 : </dt>
							<dd>-</dd>
						</dl>
						<dl>
							<dt>예약가능시간 : </dt>
							<dd>${item.rentAvailableTime }</dd>
						</dl>
						<dl>
							<dt>대관료 : </dt>
							<dd>
								<fmt:formatNumber value="${item.rentCharge}" pattern="#,###" />원 /
								<c:if test="${item.rentRentingMethod eq 'time' }">시간</c:if>
								<c:if test="${item.rentRentingMethod eq 'package' }">패키지</c:if>
							</dd>
						</dl> --%>
						<%-- <c:if test="${item.applyPossibleBtn }">
							<p class="p2"><a href="${APP_PATH}/rental/${item.rentCate1 }/calendar/${item.rentId}">예약신청</a></p>
						</c:if> --%>
	
	<!-- paging -->
	<%-- <c:out value="${paging.userTextTag}" escapeXml="false"/> --%>
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />