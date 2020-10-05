<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<!DOCTYPE HTML>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
	<title> Kobaco 한국방송광고진홍공사 </title>
	<link href="${CONTEXT_PATH }/design/theme/admuseum/css/base.css" rel="stylesheet" type="text/css" />
	<link href="${CONTEXT_PATH }/design/theme/admuseum/css/print/style_print.css" rel="stylesheet" type="text/css" />
</head>

 <body>
	<div id="pr_content">
		<div class="pr_title"><h1>박물관 · 도서관 자원봉사 신청서</h1></div>
		<table class="pr_tableType01">
			<colgroup>
			        <col width="20%" />				
			        <col width="*" />			
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><label for="name">이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름</label></th>
					<td><input type="text" id="name"  class="pr_inputbox" value="${volunteerReservationForm.reservName }" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="school">학&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;교</label></th>
					<td><input type="text" id="school" style="width:400px;" class="pr_inputbox" value="${volunteerReservationForm.reservGroup }" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="address">주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</label></th>
					<td><input type="text" id="address" style="width:100%;" class="pr_inputbox" value="${volunteerReservationForm.reservAddress }" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="obj">목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;적</label></th>
					<td><input type="text" id="address" style="width:100%;" class="pr_inputbox" value="${volunteerReservationForm.reservPurpose }" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="date">봉사일<br />및 시간</label></th>
					<td>
						<ul class="print_bl ">
							<li>
								<span class="fl mr5">○ 날짜 :</span><input type="text" id="date" style="width:50px;" class="pr_inputbox fl txtr mr5" value="${reservDate[0] }" /><span class="fl mr5">년</span>
								<input type="text" id="" style="width:40px;" class="pr_inputbox fl txtr mr5" value="${reservDate[1] }" /><span class="fl mr5">월</span>
								<input type="text" id="" style="width:45px;" class="pr_inputbox fl txtr mr5" value="${reservDate[2] }" /><span class="fl mr5">일 (</span>
								<input type="text" id="" style="width:40px;" class="pr_inputbox fl txtr mr5" value="${reservDayOfWeek }" /><span class="fl">요일)</span>
							</li>
							<li>
								<span class="fl mr5">○ 시간 :</span><input type="text" id="date" style="width:50px;" class="pr_inputbox fl txtr mr5" value="${sTime[0] }" /><span class="fl mr5">시</span>
								<input type="text" id="" style="width:40px;" class="pr_inputbox fl txtr mr5" value="${sTime[1] }" /><span class="fl mr5">분&nbsp;&nbsp&nbsp;&nbsp;~ </span>
								<input type="text" id="" style="width:20px;" class="pr_inputbox fl txtr mr5" value="${eTime[0] }"  /><span class="fl mr5">시</span>
								<input type="text" id="" style="width:40px;" class="pr_inputbox fl txtr mr5" value="${eTime[1] }" /><span class="fl mr5">분 (</span>								
								<input type="text" id="" style="width:40px;" class="pr_inputbox fl txtr mr5" value="${termTime }" /><span class="fl">시간)</span>
							</li>
						</ul>					
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="tell">연&nbsp;&nbsp;락&nbsp;&nbsp;처</label></th>
					<td>
						<ul class="print_bl ">
							<li>
								<span class="fl">○ 전화 :</span><input type="text" id="tell" style="width:200px" class="pr_inputbox fl" value="${volunteerReservationForm.reservHp }" />								
							</li>
							<li>
								<span class="fl">○ 이메일 :</span><input type="text" id="" style="width:200px;" class="pr_inputbox fl" value="${volunteerReservationForm.reservEmail }" />								
							</li>
							
						</ul>
					
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="etc">기&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;타<br />건의 사항</label></th>
					<td>
						<textarea id="etc" name="" cols="" rows="3" class="pr_textarea" style="width:100%;height:100%;">${volunteerReservationForm.reservSuggest }</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="mt100">
			<ul class="print_bl ">
				<li>
					<span class="fl mr5">○ 신&nbsp;&nbsp;청&nbsp;&nbsp;일 :</span><input type="text" id="date" style="width:50px;" class="pr_inputbox fl txtr mr5" value="${regDate[0] }" /><span class="fl mr5">년</span>
					<input type="text" id="" style="width:50px;" class="pr_inputbox fl txtr mr5" value="${regDate[1] }" /><span class="fl mr5">월</span>
					<input type="text" id="" style="width:55px;" class="pr_inputbox fl txtr mr5" value="${regDate[2] }" /><span class="fl mr5">일</span>
				</li>
				<li>
					<span class="fl mr5">○ 신&nbsp;&nbsp;청&nbsp;&nbsp;자 :</span><input type="text" id="date" style="width:150px;" class="pr_inputbox fl txtr mr5" value="${volunteerReservationForm.reservName }" /><span class="fl mr5">(인, 서명)</span>
				</li>
			</ul>
		</div>

	</div>  
 </body>
</html>