<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ page import="java.util.Date"%>
<!DOCTYPE HTML>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
	<title> Kobaco 한국방송광고진홍공사 </title>
</head>
<body>
	<table style="width:740px;border-collapse:collapse;border-spacing:0;table-layout: fixed;margin:0 auto">
		<tr>
			<td>
				<table style="width:100%;border-collapse:collapse;border-spacing:0;table-layout: fixed;margin:0 auto">
					<tr>
						<td rowspan="2" style="width:100px;vertical-align:top">
							<a href="#">
								<img src="https://www.kobaco.co.kr/assets/images/popup_img1.gif" alt="한국방송광고진흥공사 로고" style="width:100px;height:49px;border:0;padding-top:5px">
							</a>
						</td>
						<td style="color: #5588b4;font-size: 26px;font-weight: 600;line-height: 30px;text-align: center;">
							${rentalReservationForm.rental.rentTitle }
						</td>
					</tr>
					<tr>
						<td style="color: #333;font-size: 30px;font-weight: 900;line-height: 30px;padding-top: 5px;text-align: center;padding-bottom:20px">사용 신청서</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table style="width:100%;border-collapse:collapse;border-spacing:0;table-layout: fixed;margin:0 auto;border-top: 2px solid #5588b4;border-bottom: 1px solid #5588b4;">

					<tr>
						<th scope="row" style="width:148px; background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">행사명</th>
						<td colspan="3" style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">${rentalReservationForm.reservTitle }</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">행사 또는 전시내용</th>
						<td colspan="3" style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">${rentalReservationForm.reservContent }</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">사용일시</th>
						<td colspan="3" style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">${rentalReservationForm.reservDate }</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;;border-bottom: 1px solid #ddd;">사용인원</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">${rentalReservationForm.reservTotal } (명)</td>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">사용시간</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">
							<c:forEach items="${reservTimeArray }" var="reservTime" varStatus="vs">
								${reservTime }
								<c:if test="${!vs.last }">, </c:if>
							</c:forEach>
							<c:if test="${not empty rentalReservationForm.reservTimeAdd }">[추가 : ${rentalReservationForm.reservTimeAdd } 시간]</c:if>
						</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">신청기관</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">${rentalReservationForm.reservOrganization }</td>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">전&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;화</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">${rentalReservationForm.reservHp }</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</th>
						<td colspan="3" style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">(${rentalReservationForm.reservZipcode }) ${rentalReservationForm.reservAddress } ${rentalReservationForm.reservAddressDetail }</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">대&nbsp;관&nbsp;료</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">
							${reservPrice } 원(VAT별도)
						</td>
						<th class="te-cr" scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">	결제방법</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">
							${asapro:codeName(rentalReservationForm.reservPaidType, paidTypeCodeList) }
						</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">할&nbsp;인&nbsp;율</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">
							<c:if test="${rentalReservationForm.reservDiscountRate > 0 }">${rentalReservationForm.reservDiscountRate } %</c:if>
							<c:if test="${rentalReservationForm.reservDiscountRate == 0 }">없음</c:if>
						</td>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">구  분</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">${asapro:codeName(rentalReservationForm.reservOrgDivCode, orgDivCodeList) }</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">결제금액</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">
							${reservPaymentAmount } 원(VAT포함)
						</td>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">주차지원</th>
						<td style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">1일권 ${rentalReservationForm.reservParking }매</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">전자계산서<br>수신용 이메일 주소</th>
						<td colspan="3" style="padding: 12px;border-left: 1px solid #ddd;color: #4f80e4;border-bottom: 1px solid #ddd;">
							${rentalReservationForm.reservEmail }
							<p style="padding:0;color: #bb813d;margin:0">* 상기 이메일 주소로 세금계산서가 발행(발송)됩니다.</p>
						</td>
					</tr>
					
					<tr>
						<th scope="row" style="background: #f9f9f9;color: #333;font-weight: 600;padding: 12px;line-height: 18px;text-align: left;border-bottom: 1px solid #ddd;">비고</th>
						
						<td colspan="3" style="padding: 12px;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;line-height:24px">
							<pre style="font-size: large;"><c:out value="${rentalReservationForm.reservMemo }" escapeXml="true"/></pre>
						</td>
					</tr>
					<tr>
						<td colspan="4"  style="padding: 12px;color: #333;font-size: 18px;text-align: center;">
							대관 기준사항을 준수키로 하고 위와 같이 대관시설 사용을 신청합니다.<br><br>
							${now[0] } 년&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${now[1] }&nbsp;월&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${now[2] }&nbsp;일
						</td>
					</tr>
					
					<tr>
						<td colspan="4"  style="line-height:28px; padding: 30px 12px 20px 12px;color: #333;font-size: 18px;text-align: right;border-bottom: 1px solid #ddd;">
							신청자 성명 : ${rentalReservationForm.reservName }   (인, 서명)<br>
							휴 대 전 화 : ${rentalReservationForm.reservHp } 
						</td>
					</tr>
					
					<tr>
						<td colspan="4"  style="padding:12px;color:#555;font-size: 15px;">
							※ 사업자등록증(세금계산서 발급용) 사본 1부 첨부
						</td>
					</tr>

					<tr>
						<td colspan="4"  style="padding: 35px 12px 30px 12px;font-size: 15px;text-align: center;color: #333;font-size: 26px;font-weight: 600;">
							한국광고문화회관 귀중
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<table style="width:100%;border-collapse:collapse;border-spacing:0;table-layout: fixed;margin:0 auto;border-bottom: 1px solid #5588b4;">
					
					<tr>
						<td style="width:100px;height:94px;">
							<a href="#">
								<img src="https://www.kobaco.co.kr/assets/images/popup_img1.gif" alt="한국방송광고진흥공사 로고" style="width:100px;height:49px;border:0">
							</a>
						</td>
						<td style="color: #5588b4;font-size: 26px;font-weight: 600;line-height: 30px;text-align: left;padding: 0 0 0 180px;">
							대관시설 이용안내
						</td>
					</tr>
					<tr>
						<td colspan="2"  style="background: #fbfbfb;padding: 30px;border: 1px solid #ddd;line-height: 24px;font-size:15px;color:#555;letter-spacing:-1px">
							<p style="color: #333;font-size: 16px;font-weight: 600;margin: 30px 0 15px 0;">대관시설 현황</p>
							<p style="color: #333;font-size: 16px;font-weight: 600;margin: 30px 0 15px 0;">대관 요일 및 시간</p>
							- 오전 시간(09:00 ~ 13:00), 오후시간(14:00~18:00)<br>
							- 월요일 ～ 금요일(09:00～18:00) / 시간조정 가능함<br>
							- 주말, 공휴일은 웨딩행사 전용으로 사용(예약 : CJ ☏ 02-2144-0230)<br>
							- ※ 대관 제한 : 상품 또는 전시품 판매가 이뤄지는 상업성 행사는 대관하지 않습니다.<br>
							<p style="color: #333;font-size: 16px;font-weight: 600;margin: 30px 0 15px 0;">온라인 신청방법</p>
							
							① 광고회관방송괴화 홈페이지 <a href="http://www.kobaco.co.kr/site/adbro/home" target="_blank" style="color:#4f80e4;font-weight: normal;">http://www.kobaco.co.kr/site/adbro/home </a><br>
							&nbsp;&nbsp;&nbsp;&nbsp;광고회관방송회관(상단메뉴) → 대관시설예약 → 시설명 선택 후 온라인 대관신청<br>
							② 공사 홈페이지 www.kobaco.co.kr → Famaily site(상단 메뉴) → 광고회관방송회관 선택<br>
							&nbsp;&nbsp;&nbsp;&nbsp;대관 신청 담당자 : <strong style="color:#4f80e4;font-weight: normal;">광고인프라팀 ☎ 02-2144-0123, FAX 02-2144-0115</strong>
							
							
							<p style="color: #333;font-size: 16px;font-weight: 600;margin: 30px 0 15px 0;">대관료 정산방법</p>
							&middot;&nbsp; 현금결제는 사용 전일 18:00까지, 카드결제는 사용 당일 가능<br>
							&middot;&nbsp; 시설사용 전ㆍ후(현금영수증ㆍ세금계산서 발행 / 카드결제 가능)<br>
							&middot;&nbsp; 당일 정산 : 회관관리사무소(광고회관 7층) ※“세금계산서” 발행은 회관운영팀(8층)<br>
							&middot;&nbsp; 행사 전후 정산 : <strong style="color:#4f80e4;font-weight: normal;">우리은행 1005-903-066128 한국방송광고진흥공사</strong>


							<p style="color: #333;font-size: 16px;font-weight: 600;margin: 30px 0 15px 0;">대관신청 시 참고사항</p>
							&middot;&nbsp; 대관장소의 책상 배치도, 기자재 지원, 음향설비, 조명, 냉난방, 좌석배치 등 <strong style="color:#4f80e4;font-weight: normal;">모든 준비사항은 회관 시설지원<br>
							&nbsp;&nbsp;담당자</strong>와 사전에 반드시 협의하시기 바랍니다.<br>
							&nbsp;&nbsp;- 대관 시설지원 담당자 : 홍승표 주임 ☎ 010-4353-4161<br>
							&middot;&nbsp; 주차요금은 10분당 500원, 1시간 3,000원, 1일 20,000원이며, 행사주관자를 위한 1일 무료주차권 5매<br>
							&nbsp;&nbsp;(단, 소회의실은 3매)를 지원합니다.<br>
							&middot;&nbsp; 노트북은 준비되어 있으나 사용하시는 것을 가져오시면 더 편합니다.(예약자 사전점검 필)<br>
							&middot;&nbsp; 생수는 무상 지원해 드리지만 음료, 다과 등은 별도로 준비하여야 합니다.<br>
							&middot;&nbsp; 대관 취소 또는 변경 시에는 7일전까지 전화로 알려 주셔야 합니다.<br>
							
							<p style="margin-top: 35px;color: #333;font-size: 26px;font-weight: 600;text-align:center">한국광고문화회관 운영팀</p>
						</td>
					</tr>
				
				</table>
			
			</td>
		
		</tr>
	
	</table>

</body>
</html>