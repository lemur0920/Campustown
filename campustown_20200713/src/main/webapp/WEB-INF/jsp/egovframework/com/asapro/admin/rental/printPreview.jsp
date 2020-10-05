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
	<link href="${CONTEXT_PATH }/assets/plugins/jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet" />
	<link href="${CONTEXT_PATH }/assets/css/popup.css" rel="stylesheet" />
	
	
	<script src="${CONTEXT_PATH }/assets/js/jquery.min.js"></script>
	<script src="${CONTEXT_PATH }/assets/plugins/jquery-ui-1.12.1/jquery-ui.js"></script>
	<script type="text/javascript" src="${CONTEXT_PATH }/assets/js/printThis.js"></script>
</head>

<script>
jQuery(function($){
	//프린트
	$('.popup-header').on('click', '.printing', function(e){
		e.preventDefault();
		//window.print();
		
		$('#wrap').printThis({
			//canvas: false,  
			//importStyle: true, 
			importCSS: true,
			loadCSS: '${CONTEXT_PATH }/assets/css/print.css'
		});
		
	});
	
	//닫기버튼
	$('.closeBtn').on('click', function(e){
		e.preventDefault();
		window.close();
	});
	
	//최초 로드시 이용안내는 숨김
	$('.tab-2').hide();
	
	$('.popup-tab a').on('click', function(e){
		e.preventDefault();
		var id = $(this).attr('id');
		
		$('.popup-tab a').removeClass('on');
		$(this).addClass('on');
		
		//
		$('.tab-title').hide();
		$('.tab-content').hide();
		
		$('.'+id).fadeIn();
		
	});
	
});
</script>


<body>
<div id="wrap">
	<div class="popup-tab">
		<ul class="clear">
			<li><a id="tab-1" class="on" href="#n">사용신청서</a></li>
			<li><a id="tab-2" href="#n">이용안내</a></li>
		</ul>
	</div>
	<div class="popup-header">
		<%--<a class="logo" href="#"><img src="${currentSite.siteLogo.fileServletUrl}" alt="${currentSite.siteName} logo"></a> --%>
		<a class="logo" href="#"><img src="${CONTEXT_PATH }/assets/images/popup_img1.gif" alt="Kobaco 한국방송광고진홍공사"></a>
		<h2 class="tab-title tab-1">${rentalReservationForm.rental.rentTitle }<br><span>사용 신청서</span></h2>
		<h2 class="tab-title tab-2">대관시설 이용안내</h2>
		<a class="printing" href="#"><img src="${CONTEXT_PATH }/assets/images/popup_img2.gif" alt="출력"></a>
	</div>
	<div class="popup-table-bor tab-content tab-1">
		<table class="popup-table">
			<caption>컨퍼런스 룸 사용 신청서</caption>
			<colgroup>
				<col style="width:20%">
				<col style="width:*%">
				<col style="width:12%">
				<col style="width:28%">
			</colgroup>
			<tbody>
				<tr>
					<th class="no-ltbor" scope="row">행사명</th>
					<td colspan="3">${rentalReservationForm.reservTitle }</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">행사 또는 전시내용</th>
					<td colspan="3">${rentalReservationForm.reservContent }</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">사용일시</th>
					<td colspan="3">${rentalReservationForm.reservDate }</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">사용인원</th>
					<td>${rentalReservationForm.reservTotal } (명)</td>
					<th class="te-cr" scope="row">사용시간</th>
					<td>
						<c:forEach items="${reservTimeArray }" var="reservTime" varStatus="vs">
							${reservTime }
							<c:if test="${!vs.last }">, </c:if>
						</c:forEach>
						<c:if test="${not empty rentalReservationForm.reservTimeAdd }">[추가 : ${rentalReservationForm.reservTimeAdd } 시간]</c:if>
					</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">신청기관</th>
					<td>${rentalReservationForm.reservOrganization }</td>
					<th class="te-cr" scope="row">전     화</th>
					<td>${rentalReservationForm.reservHp }</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">주     소</th>
					<td colspan="3">(${rentalReservationForm.reservZipcode }) ${rentalReservationForm.reservAddress } ${rentalReservationForm.reservAddressDetail }</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">대 관 료</th>
					<td>
						${reservPrice } 원(VAT별도)
					</td>
					<th class="te-cr" scope="row">결제방법</th>
					<td>
						${asapro:codeName(rentalReservationForm.reservPaidType, paidTypeCodeList) }
					</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">할 인 율</th>
					<td>
						<c:if test="${rentalReservationForm.reservDiscountRate > 0 }">${rentalReservationForm.reservDiscountRate } %</c:if>
						<c:if test="${rentalReservationForm.reservDiscountRate == 0 }">없음</c:if>
					</td>
					<th class="te-cr" scope="row">구  분</th>
					<td>${asapro:codeName(rentalReservationForm.reservOrgDivCode, orgDivCodeList) }</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">결제금액</th>
					<td>${reservPaymentAmount } 원(VAT포함)</td>
					<th class="te-cr" scope="row">주차지원</th>
					<td>1일권 ${rentalReservationForm.reservParking }매</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">전자계산서<br>수신용 이메일 주소</th>
					<td colspan="3">
						<p class="color-blue">${rentalReservationForm.reservEmail }</p>
						<p class="color-gold">* 상기 이메일 주소로 세금계산서가 발행(발송)됩니다.</p>
					</td>
				</tr>
				<tr>
					<th class="no-ltbor" scope="row">비고</th>
					<td colspan="3">
						<ul class="popup-fonts">
							<li>
								<pre ><c:out value="${rentalReservationForm.reservMemo }" escapeXml="true"/></pre>
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td class="no-ltbor" colspan="4">
						<div class="tx-ct font18-color3">
							<p>대관 기준사항을 준수키로 하고 위와 같이 대관시설 사용을 신청합니다.</p>
							<p class="mt20">${now[0] } 년       ${now[1] } 월         ${now[2] } 일</p>
						</div>
						<div class="tx-rt mt35 font18-color3">
							<p>신청자 성명 : <span class="color-666">${rentalReservationForm.reservName }   (인, 서명)</span></p>
							<p class="mt10">휴 대 전 화 : <span class="color-666">${rentalReservationForm.reservHp } &nbsp; </span></p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="no-ltbor last-td" colspan="4">
						<p>※ 사업자등록증(세금계산서 발급용) 사본 1부 첨부</p>
						<div class="tx-ct mt35 font26-color3">한국광고문화회관 귀중</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<%-- 이용안내 --%>
	
	<div class="popup-border tab-content tab-2">
		<h3 class="popup-title mt0">대관시설 현황</h3>
		<h3 class="popup-title">대관 요일 및 시간</h3>
		<ul class="list-line">
			<li>오전 시간(09:00 ~ 13:00), 오후시간(14:00~18:00)</li>
			<li>월요일 ～ 금요일(09:00～18:00) / 시간조정 가능함</li>
			<li>주말, 공휴일은 웨딩행사 전용으로 사용(예약 : CJ ☏ 02-2144-0230)</li>
			<li>※ 대관 제한 : 상품 또는 전시품 판매가 이뤄지는 상업성 행사는 대관하지 않습니다.</li>
		</ul>
		<h3 class="popup-title">온라인 신청방법</h3>
		<ul class="list-numb">
			<li><span class="posi">① </span>광고회관방송괴화 홈페이지 
				<a class="color-blue" href="http://www.kobaco.co.kr/site/adbro/home">http://www.kobaco.co.kr/site/adbro/home</a>
				<br>광고회관방송회관(상단메뉴) → 대관시설예약 → 시설명 선택 후 온라인 대관신청
			</li>
			<li><span class="posi">② </span>공사 홈페이지 www.kobaco.co.kr → Famaily site(상단 메뉴) → 광고회관방송회관  선택
				<ul class="list-line">
					<li>대관 신청 담당자 : <span class="color-blue">광고인프라팀 ☎ 02-2144-0123, FAX 02-2144-0115</span></li>
				</ul>
			</li>
		</ul>
		<h3 class="popup-title">대관료 정산방법</h3>
		<ul class="list-spot">
			<li>현금결제는 사용 전일 18:00까지, 카드결제는 사용 당일 가능 </li>
			<li>시설사용 전ㆍ후(현금영수증ㆍ세금계산서 발행 / 카드결제 가능)</li>
			<li>당일 정산 : 회관관리사무소(광고회관 7층) ※“세금계산서” 발행은 회관운영팀(8층)</li>
			<li>행사 전후 정산 : <span class="color-blue">우리은행 1005-903-066128 한국방송광고진흥공사</span></li>
		</ul>
		<h3 class="popup-title">대관신청 시 참고사항</h3>
		<ul class="list-spot">
			<li>대관장소의 책상 배치도, 기자재 지원, 음향설비, 조명, 냉난방, 좌석배치 등 <span class="color-blue">모든 준비사항은 회관 시설지원 담당자</span>와 사전에 반드시 협의하시기 바랍니다.
				<ul class="list-line">
					<li>대관 시설지원 담당자 : 홍승표 주임 ☎ 010-4353-4161</li>
				</ul>
			</li>
			<li>주차요금은 10분당 500원, 1시간 3,000원, 1일 20,000원이며, 행사주관자를 위한 1일 무료주차권 5매<br>(단, 소회의실은 3매)를 지원합니다.</li>
			<li>노트북은 준비되어 있으나 사용하시는 것을 가져오시면 더 편합니다.(예약자 사전점검 필)</li>
			<li>생수는 무상 지원해 드리지만 음료, 다과 등은 별도로 준비하여야 합니다.</li>
			<li>대관 취소 또는 변경 시에는 7일전까지 전화로 알려 주셔야 합니다.</li>
		</ul>
		<div class="tx-ct mt35 font26-color3">한국광고문화회관 운영팀</div>
	</div>
	
	<div class="popup-button">
		<a href="#n" class="closeBtn">닫기</a>
	</div>
</div>
</body>
</html>