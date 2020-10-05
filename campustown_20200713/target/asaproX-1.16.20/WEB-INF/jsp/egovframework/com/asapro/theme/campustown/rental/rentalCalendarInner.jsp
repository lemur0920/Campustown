<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>


<script>
jQuery(function($){
	
});
</script>

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<div class="calendar-box mb">
		<div class="button-div mb38">
			<a href="#non" class="a1 pre" data-year="${rentalReservationSearch.reservYear - 1 }" data-month="${rentalReservationSearch.reservMonth}"><img src="/design/theme/campustown/images/sub/calendar_btn01.gif" alt="맨처음" /></a>
			<a href="#non" class="a2 pre" data-year="${rentalReservationSearch.preYear }" data-month="${rentalReservationSearch.preMonth}"><img src="/design/theme/campustown/images/sub/calendar_btn02.gif" alt="지난달" /></a>
			
			<select name="reservYear" title="년도선택" class="select1 select-year">
				<c:forEach begin="${0 }" end="${5 }" step="1" var="year">
					<option <c:if test="${(rentalReservationSearch.reservYear + 3 - year) eq rentalReservationSearch.reservYear }">selected</c:if>>${rentalReservationSearch.reservYear + 3 - year }</option>
				</c:forEach>
			</select>
			<select name="reservMonth" title="월선택" class="select2 select-month">
				<c:forEach begin="1" end="12" step="1" var="month">
					<fmt:formatNumber value="${month}" pattern="00"  var="calMonth"/>
					<option <c:if test="${rentalReservationSearch.reservMonth eq calMonth }">selected</c:if>>${calMonth }</option>
				</c:forEach>
			</select>
			<a href="#non" class="a3 next" data-year="${rentalReservationSearch.nextYear }" data-month="${rentalReservationSearch.nextMonth}"><img src="/design/theme/campustown/images/sub/calendar_btn03.gif" alt="다음달" /></a>
			<a href="#non" class="a4 next" data-year="${rentalReservationSearch.reservYear + 1 }" data-month="${rentalReservationSearch.reservMonth}"><img src="/design/theme/campustown/images/sub/calendar_btn04.gif" alt="맨마지막" /></a>
		</div>
		
		<table class="table-s mb35">
			<caption>달력</caption>
			<colgroup>
				<col style="width:auto" />
				<col style="width:14.3%" />
				<col style="width:14.3%" />
				<col style="width:14.3%" />
				<col style="width:14.3%" />
				<col style="width:14.3%" />
				<col style="width:14.3%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col" class="first">일</th>
					<th scope="col">월</th>
					<th scope="col">화</th>
					<th scope="col">수</th>
					<th scope="col">목</th>
					<th scope="col">금</th>
					<th scope="col">토</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rentalCalendar.days }" var="day" varStatus="status">
					<c:if test="${status.count % 7 == 1 }">
						<tr>
					</c:if>
					<fmt:formatNumber value="${day.year}" pattern="0000"  var="calYear"/>
					<fmt:formatNumber value="${day.month}" pattern="00"  var="calMonth"/>
					<fmt:formatNumber value="${day.day}" pattern="00"  var="calDay"/>
					<c:if test="${calMonth == rentalReservationSearch.reservMonth }">
						<td>
							<span class="span1 <c:if test="${status.count % 7 == 1 }">fs2</c:if>">${day.day }</span>
							<c:if test="${day.possibleChk }">
								<c:if test="${not empty day.possibleTime }">
									<a href="#popup_con" data-date="${calYear }-${calMonth }-${calDay }" data-possibletime="${day.possibleTime }" data-configtime="${day.configTermList }" class="span2 yycheck reservDateBtn">
										<p class="reservation-txt">예약가능</p>
									</a>
								</c:if>
								<c:if test="${empty day.possibleTime }">
									<p class="completion-txt">예약완료</p>
								</c:if>
							</c:if>
						</td>
					</c:if>
					<c:if test="${calMonth != rentalReservationSearch.reservMonth }">
						<td></td>
					</c:if>
					<c:if test="${status.count % 7 == 0 }">
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
