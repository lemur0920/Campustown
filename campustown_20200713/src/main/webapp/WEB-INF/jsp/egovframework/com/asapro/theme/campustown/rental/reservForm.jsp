<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//주소의 파라메터 제거
	//history.replaceState({}, null, location.pathname);
	
	var tempDiscountRate = '${rentalReservationForm.rental.rentMemberDiscount}';	//할인률
	var discountRate = 0;	//할인률
	var paymentAmount = ${rentalReservationForm.reservPaymentAmount};	//결제금액
	
	//멤버십여부 변경시
 	$('[name=reservMembership]').on('change', function(){
 		if($(this).val() == 'true' ){
			discountRate = tempDiscountRate;
 		}else{
			discountRate = 0;
 		}
 		$('.paymentAmount').html(numberWithCommas(paymentAmount - parseInt(paymentAmount * discountRate / 100)) + ' 원');
	});
	
	//예약확인 버튼
	$('.confirmBtn').on('click', function(){
		/* var chkagree = true;
		$('.agree').each(function(event, el){

			if(el.checked == true  ){
				return true;
			}else{
				chkagree = false;
				$('#agreeChk').val(chkagree);
				return false;
			}
		});
		
		if(!chkagree ){
			alert('서비스 이용약관 동의는 필수 입니다.');
			return false;
		} */
		
		$('.yycheck-show').show();
	});
	
	//예약하기
	$('.insertBtn').on('click', function(){
		/* var chkagree = true;
		$('.agree').each(function(event, el){

			if(el.checked == true  ){
				return true;
			}else{
				chkagree = false;
				return false;
			}
		});
		
		$('#agreeChk').val(chkagree); */
		//alert($('#agreeChk').val());
		$('#rentalReservationForm').submit();
	});
	
	//이용약관 전체동의버튼
	$('.allcheckyg').on('click', function(event, el1){
		var chkValue = $(this).data('check');
		if(chkValue == ''){
			$(this).data('check','checked');
		}else{
			$(this).data('check','');
		}
		//alert($(this).data('check'));
		$('.agree').each(function(idx, el2){
			el2.checked = chkValue;
		});
	});
	
	
	//레이어 닫기
	$('.closeBtn, .btn_close').click(function(){
		$('#popup_con').hide();
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<!-- content -->
	

	<!--  페이지별 내용 -->
	<h4 class="title2 mb45">신청서 작성</h4>
	
	<div class="step-box01 mb73">
		<ul class="c4">
			<li>
				<div>
					<div class="con">
						<p class="txt01">Step #1</p>
						<p class="txt02"><span>예약 정보 확인</span></p>
					</div>
				</div>
			</li>
			<li>
				<div>
					<div class="con">
						<p class="txt01">Step #2</p>
						<p class="txt02"><span>개인정보<br>제공동의</span></p>
					</div>
				</div>
			</li>
			<li class="on">
				<div>
					<div class="con">
						<p class="txt01">Step #3</p>
						<p class="txt02"><span>신청서 작성</span></p>
					</div>
				</div>
			</li>
			<li>
				<div>
					<div class="con">
						<p class="txt01">Step #4</p>
						<p class="txt02"><span>담당자 승인</span></p>
					</div>
				</div>
			</li>
		</ul>
	</div>
	
	<!-- alert maeeage -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/message.jsp"/>
	
	<div class="table-con style2 mb30">
		<table>
			<caption></caption>
			<colgroup>
				<col style="width:20%" />
				<col style="width:auto" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">
						<label for="lb01">공간명</label>
					</th>
					<td>
						<c:forEach items="${rentCate1List}" var="cate1" varStatus="vs">
							<c:if test="${cate1.univId eq rentalReservationForm.rental.rentCate1 }">${cate1.univNm }</c:if>
						</c:forEach>
						<%-- ${asapro:codeName(rentalReservationForm.rental.rentCate1, rentCate1List) }  --%>
						[${rentalReservationForm.rental.rentTitle }]
					</td>
				</tr>
				<tr>
					<th scope="row">
						<label>예약날짜</label>
					</th>
					<td>${rentalReservationForm.reservDate } (${asapro:getDayOfWeekKorText(rentalReservationForm.reservDate) }) ${fn:replace(rentalReservationForm.reservTime, ',', ' ,')}</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	
	
	
	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${APP_PATH}/rental/${rentalReservationForm.rental.rentCate1 }/reserv/${rentalReservationForm.rental.rentId }/insert" /></c:if>
	<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${APP_PATH}/rental/${rentalReservationForm.rental.rentCate1 }/reserv/${rentalReservationForm.rental.rentId }/update" /></c:if>
	<form:form modelAttribute="rentalReservationForm" action="${actionUrl}" method="post" enctype="multipart/form-data">
		${SECURITY_TOKEN_TAG}
		<form:hidden path="reservId" />
		<form:hidden path="agreeChk" />
		<form:hidden path="reservMemo" />
		<form:hidden path="reservDate" />
		<form:hidden path="reservTime" />
		
		<div class="table-con style2 mb30">
			<table>
				<caption>신청서 작성</caption>
				<colgroup>
					<col style="width:20%" />
					<col style="width:auto" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<label for="ipt1" class="on">신청자 성명</label> <span class="fc-red">*</span>
						</th>
						<td>
							<c:if test="${formMode eq 'INSERT'}"><c:set var="reservName" value="${currentUser.userName }" /></c:if>
							<c:if test="${formMode eq 'UPDATE'}"><c:set var="reservName" value="${rentalReservationForm.reservName }" /></c:if>
							<input name="reservName" type="text" id="ipt1" class="input-320px" value="${reservName }" readonly="readonly"/>
							<form:errors path="reservName" id="ipt1" element="div" cssClass="text-error" />
						</td>
					</tr>
					
					<c:if test="${rentalReservationForm.rental.rentMembership }">
						<tr>
							<th scope="row">
								<label>맴버쉽 여부</label>
							</th>
							<td>
								<form:radiobutton path="reservMembership" value="true"/>
								<label for="reservMembership1" class="label1">멤버쉽 회원입니다.</label>
								<form:radiobutton path="reservMembership" value="false"/>
								<label for="reservMembership2" class="label1">멤버십 회원이 아닙니다.</label>
								<div class="mt-2"> 멤버십 할인 <b class="text-error">${rentalReservationForm.rental.rentMemberDiscount }</b> % </div>
							</td>
						</tr>
					</c:if>
					
					<tr>
						<th scope="row">
							<label for="reservHp1" class="on">연락처</label> <span class="fc-red">*</span>
						</th>
						<td>
							<form:select path="reservHp1" cssClass="input-180px" cssErrorClass="input-180px text-error" title="연락처 앞자리 선택">
								<option value="" label="선택">
								<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
							</form:select>
							 - <form:input path="reservHp2" cssClass="input-180px" cssErrorClass="input-180px text-error" maxlength="4" title="연락처 가운데 자리 입력" />
							 - <form:input path="reservHp3" cssClass="input-180px" cssErrorClass="input-180px text-error" maxlength="4" title="연락처 뒷 자리 입력" />
							<form:errors path="reservHp1" element="div" cssClass="text-error" />
							<form:errors path="reservHp2" element="div" cssClass="text-error" />
							<form:errors path="reservHp3" element="div" cssClass="text-error" />
						</td>
					</tr>
					<tr>
						<th scope="row">
							<label for="reservEmail" class="on">E-mail</label> <span class="fc-red">*</span>
						</th>
						<td>
							<form:input path="reservEmail" cssClass="input-320px" cssErrorClass="input-320px text-error" title="이메일 입력" />
							<form:errors path="reservEmail" element="div" cssClass="text-error" />
							
							<!-- <input type="text" id="ipt2" class="input3" />
							@
							<select title="이메일 뒷자리 선택" class="slt2">
								<option>직접입력</option>
							</select>
							<input type="text" title="이메일 직접 입력" class="input3" /> -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<label for="reservTotal" class="on">사용 인원</label> <span class="fc-red">*</span>
						</th>
						<td>
							<form:input path="reservTotal" cssClass="input-150px" cssErrorClass="input-150px text-error" title="사용 인원 입력" />
							<span class=""> 명</span> 
							<form:errors path="reservTotal" element="div" cssClass="text-error" />
							<p class="mt10">※ 수용인원은 최소 <b class="text-error">${rentalReservationForm.rental.rentResMinNumber }</b> 명, 최대 <b class="text-error">${rentalReservationForm.rental.rentResMaxNumber }</b> 명 입니다.</p>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<label for="reservOrganization" class="on">단체명</label> <span class="fc-red">*</span>
						</th>
						<td>
							<form:input path="reservOrganization" cssClass="input-100p" cssErrorClass="input-100p text-error" title="단체명 입력" />
							<form:errors path="reservOrganization" element="div" cssClass="text-error" />
						</td>
					</tr>
					<tr>
						<th scope="row">
							<label for="reservTitle" class="on">행사명</label> <span class="fc-red">*</span>
						</th>
						<td>
							<form:input path="reservTitle" cssClass="input-100p" cssErrorClass="input-100p text-error" title="행사명 입력" />
							<form:errors path="reservTitle" element="div" cssClass="text-error" />
						</td>
					</tr>
					<tr>
						<th scope="row">
							<label for="reservContent" class="on">대관목적/내용</label> <span class="fc-red">*</span>
						</th>
						<td>
							<form:textarea path="reservContent" cssClass="" rows="5" cssErrorClass="" maxlength="200" placeholder="사용목적을 200자 이내로 간단하게 적어주세요." />
							<form:errors path="reservContent" element="div" cssClass="text-error" />
						</td>
					</tr>
					<tr>
						<th scope="row">
							<label for="reservRequests">요청사항</label>
						</th>
						<td>
							<form:input path="reservRequests" cssClass="input-100p" cssErrorClass="input-100p text-error" title="요청사항 입력" />
							<form:errors path="reservRequests" element="div" cssClass="text-error" />
						</td>
					</tr>
					<tr>
						<th scope="row">
							<label for="reservRequests">결제방식 선택</label> <span class="fc-red">*</span>
						</th>
						<td>
						<c:forTokens items="${rentalReservationForm.rental.rentPaymentType }" delims="," var="type" varStatus="vs">
							<span>
								<form:radiobutton path="reservPaidType" class="" value="${type }"/>
								<label for="reservPaidType${vs.count }" class="label2"> ${asapro:codeName(type, rentPaymentTypeCodeList) }</label>
							</span>
						</c:forTokens>
						<form:errors path="reservPaidType" element="div" cssClass="text-error" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="btn-area txc">
			<a href="${APP_PATH }/rental/list" class="btn03">취소</a>
			<a href="#non" class="btn05 confirmBtn">신청하기</a>
		</div>
	</form:form>






	<!--컨텐츠팝업-->
	<div id="popup_con" class="yycheck-show">
		<div class="popup popup2" tabindex="0">
			<span class="pop_tit nsq">예약신청</span>
			
			<!-- 팝업 상세 -->
			<div class="con con724">
				<p class="p7251">예약 정보 확인</p>
				<div class="d7251">
					<div>
						<p>
							<span class="span1">예약공간</span>
							<span>
								<c:forEach items="${rentCate1List}" var="cate1" varStatus="vs">
									<c:if test="${cate1.univId eq rentalReservationForm.rental.rentCate1 }">${cate1.univNm }</c:if>
								</c:forEach>
								<%-- ${asapro:codeName(rentalReservationForm.rental.rentCate1, rentCate1List) }  --%>
								[${rentalReservationForm.rental.rentTitle }]
							</span>
						</p>
						<p> 
							<span class="span1">예약날짜</span>
							<span>${rentalReservationForm.reservDate } (${asapro:getDayOfWeekKorText(rentalReservationForm.reservDate) })</span>
						</p>
						<p>
							<span class="span1">예약시간</span>
							<span>${fn:replace(rentalReservationForm.reservTime, ',', '<br>')}</span>
						</p>
						<p>
							<span class="span1">결제예정금액</span>
							<span class="paymentAmount"><fmt:formatNumber value="${rentalReservationForm.reservPaymentAmount}" pattern="#,###" /> 원</span>
						</p>
					</div>
				</div>
				<p class="org7251">예약정보를 확인하고, 예약하기를 클릭하세요</p>
				<p class="btbt7252">
					<a href="#non" class="a1 insertBtn">예약하기</a>
					<!-- <a href="#non" class="a2">수정하기</a> -->
					<a href="javascript:void(0)" class="a3 closeBtn">닫기</a>
				</p>

			</div>
			<!-- 팝업 상세 end  -->
			<button type="button" class="btn btn_close"><!--닫기 눌렀을 때 팝업창을 띄울 때 누른 button 이나 a 링크에 class="cfocus" 주세요. (접근성)-->닫기</button>
		</div>
	</div>
	<!--컨텐츠팝업-->
	
	<!-- content end -->


<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />