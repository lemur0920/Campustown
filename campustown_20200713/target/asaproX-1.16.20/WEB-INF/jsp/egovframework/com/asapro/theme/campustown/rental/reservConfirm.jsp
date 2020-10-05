<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){

	//수정하기 
	$('#editBtn').on('click', function(e){
		e.preventDefault();
		$('#rentalReservationForm').attr('action','${APP_PATH}/rental/reservation/updateForm');
		$('#rentalReservationForm').attr('method', 'post');
		$('#rentalReservationForm').submit();
	});
	
	//신청완료
	$('#reservBtn').on('click', function(e){
		e.preventDefault();
		$('#rentalReservationForm').attr('action','${APP_PATH}/rental/reservation/result');
		$('#rentalReservationForm').submit();
	});

});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
<div class="RightCWarp">

	<h4 class="title10_1">${rentalReservationForm.rental.rentTitle } <span>(${asapro:codeName(rentalReservationForm.rental.rentCate1, rentCate1List) })</span></h4>

	<div class="sub91_11">
		<h5 class="titleOr1">사용신청서</h5>
		<p class="p12 pdLeft0">대관 기준사항을 준수키로 하고 아래와 같이 대관신청 사용을 신청합니다.</p>
	</div>

	<div class="s1marg30">
		<!-- alert maeeage -->
		<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/message.jsp"/>
	
		<form:form modelAttribute="rentalReservationForm" action="${APP_PATH}/rental/reservation/insert" method="post" enctype="multipart/form-data">
			${SECURITY_TOKEN_TAG}
			<form:hidden path="reservId" />
			<table class="tablest112">
				<caption>사용신청서표</caption>
				<colgroup>
					<col style="width:25%;" />
					<col />
				</colgroup>
				<tbody>
	
					<tr>
						<th scope="row">행사명</th>
						<td class="txtLf">${rentalReservationForm.reservTitle }</td>
					</tr>
	
					<tr>
						<th scope="row">행사 또는 전시내용</th>
						<td class="txtLf">${rentalReservationForm.reservContent }</td>
					</tr>
	
					<tr>
						<th scope="row">사용날짜 및 시간</th>
						<td class="txtLf">${rentalReservationForm.reservDate } (${rentalReservationForm.reservTime }) 
							<c:if test="${not empty rentalReservationForm.reservTimeAdd }"><span> + 추가 : ${rentalReservationForm.reservTimeAdd } 시간</span></c:if>
						</td>
					</tr>
	
					<tr>
						<th scope="row">사용인원</th>
						<td class="txtLf">${rentalReservationForm.reservTotal }명</td>
					</tr>
	
					<tr>
						<th scope="row">결제방법</th>
						<td class="txtLf">${asapro:codeName(rentalReservationForm.reservPaidType, paidTypeCodeList ) }</td>
					</tr>
	
					<tr>
						<th scope="row">구분</th>
						<td class="txtLf">${asapro:codeName(rentalReservationForm.reservOrgDivCode, orgDivCodeList ) }</td>
					</tr>
	
					<tr>
						<th scope="row">신청기관명</th>
						<td class="txtLf">${rentalReservationForm.reservOrganization }</td>
					</tr>
	
					<tr>
						<th scope="row">연락처</th>
						<td class="txtLf">${rentalReservationForm.reservHp}</td>
					</tr>
	
					<tr>
						<th scope="row">주소</th>
						<td class="txtLf">${rentalReservationForm.reservZipcode } ${rentalReservationForm.reservAddress } ${rentalReservationForm.reservAddressDetail }</td>
					</tr>
	
					<tr>
						<th scope="row">신청자 성명</th>
						<td class="txtLf">${rentalReservationForm.reservName }</td>
					</tr>
	
					<tr>
						<th scope="row">직위</th>
						<td class="txtLf">${rentalReservationForm.reservPosition }</td>
					</tr>
	
					<tr>
						<th scope="row">e-mail</th>
						<td class="txtLf">${rentalReservationForm.reservEmail }</td>
					</tr>
	
					<tr>
						<th scope="row">사업자등록증<br/>(세금계산서 발급용)</th>
						<td class="txtLf">
							${rentalReservationForm.reservAppendingFileInfo.fileOriginalName }&nbsp;
							<a href="javascript:vodi(0)"><img src="${design.resource }/images/sub/dlz_dw.gif" alt="" class="vtca_m"/></a>
						</td>
					</tr>
	
				</tbody>
			</table>
		</form:form>
	</div>


	<div class="blgg1 blgg112">
		<a href="javascript:vodi(0)" class="grbLink" id="editBtn">수정하기</a>
		<a href="javascript:vodi(0)" id="reservBtn">신청완료</a>
	</div>
</div>
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />