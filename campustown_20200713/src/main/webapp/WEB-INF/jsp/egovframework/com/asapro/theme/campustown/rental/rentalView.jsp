<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	<h4 class="title2 mb25">${rentalForm.rentTitle }</h4>
	<div class="img-text-box mb50 clearfix">
		<div class="left-p">
			<img src="${rentalForm.thumb.fileServletUrl}" alt="${rentalForm.thumb.fileAltText}" />
		</div>
		<div class="right-p">
			<p class="text-title">${rentalForm.rentShortDescription }</p>
			<div class="text-con">
				<ul>
					<li>이메일 문의 ${rentalForm.rentManagerEmail }</li>
					<li>전화문의 ${rentalForm.rentManagerTel }</li>
				</ul>
			</div>
			<div class="btn">
				<c:if test="${rentalForm.applyPossibleBtn }">
					<a href="${APP_PATH}/rental/${rentalForm.rentCate1 }/calendar/${rentalForm.rentId}">예약하기</a>
				</c:if>
			</div>
		</div>
		<!--<a href="#none" class="btn-bottom">시설공간정보 신청서 다운로드</a>-->
	</div>		
			
	${rentalForm.rentDescription }	
					
						
						
	<!-- button -->
	<div class="btn-area txc clearfix">
		<a href="${APP_PATH}/rental/list" class="btn02 fc-bg-blue fr">목록</a>
	</div>
						
						
<%-- 						
						<p class="p1">${item.rentShortDescription }</p>
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
	
<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />