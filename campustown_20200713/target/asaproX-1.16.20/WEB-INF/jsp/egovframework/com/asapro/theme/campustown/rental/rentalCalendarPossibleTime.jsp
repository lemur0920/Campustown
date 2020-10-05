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

		
			
				<div class="popup" tabindex="0">
					<span class="pop_tit nsq">예약 가능 시간</span>
					
					<!-- 팝업 상세 -->
					<div class="con con724">
						<h4 class="h4-tit">시간</h4>
						<div class="radiocheck24">
							<span>
								<!-- <input type="radio" name="yyck" id="yyck1" class="yyck" /> -->
								<label for="yyck1" class="">예약불가</label>
							</span>
							<span>
								<!-- <input type="radio" name="yyck" id="yyck2" class="yyck" checked="checked" /> -->
								<label for="yyck2" class="able">예약가능</label>
							</span>
						</div>
						<div class="rdcktb">
							<table>
								<caption></caption>
								<colgroup>
									<col style="width:25%" />
									<col style="width:25%" />
									<col style="width:25%" />
									<col style="width:auto" />
								</colgroup>
								<tbody>
									<c:if test="${empty possibleTimeList }">
										<tr><td colspan="4">가능시간이 없습니다.</td></tr>
									</c:if>
									
									<c:forEach items="${possibleTimeList }" var="item" varStatus="vs">
										<c:if test="${vs.first or vs.count % 4 eq 1 }">
										<tr>
										</c:if>
										
											<td data-timegap="${item.timeGap }" data-time="${item.time }" class="<c:if test="${item.timeStatus eq 'possible' }">able</c:if> <c:if test="${vs.last or vs.count % 4 eq 0 }">last</c:if>">
												${item.time }
											</td>
										
										<c:if test="${vs.last or vs.count % 4 eq 0 }">
										</tr>
										</c:if>
										
									</c:forEach>
								
									<!-- <tr>
											<td>09:00 ~10:00</td>
											<td>10:00 ~11:00</td>
											<td class="on">11:00 ~12:00</td>
											<td class="last on">12:00 ~13:00</td>
										<td class="on">13:00 ~14:00</td>
										<td class="on">14:00 ~15:00</td>
										<td class="on">15:00 ~16:00</td>
										<td class="last">16:00 ~17:00</td>
									</tr>
									<tr>
										<td>17:00 ~18:00</td>
										<td>18:00 ~19:00</td>
										<td class="null24">-</td>
										<td class="last null24">-</td>
									</tr> -->
								</tbody>
							</table>
						</div>
						<p class="tbtbd">
							<a href="#non" class="a1 reservBtn">예약하기</a>
							<a href="#non" class="btn_cencle">취소</a>
						</p>
					</div>
					<!-- 팝업 상세 end  -->
					<button type="button" class="btn btn_close"><!--닫기 눌렀을 때 팝업창을 띄울 때 누른 button 이나 a 링크에 class="cfocus" 주세요. (접근성)-->닫기</button>
				</div>
