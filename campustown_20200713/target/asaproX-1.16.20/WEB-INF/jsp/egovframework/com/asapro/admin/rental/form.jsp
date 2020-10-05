<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>
<script src="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.js"></script>
<script src="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.css">
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.css">
<script>
jQuery(function($){
	
	<c:if test="${formMode eq 'UPDATE' and rentalForm.step eq 'applySet'}">
		//대관/대여 가능일 구분 show, hide
		var rentPossiblelDayType = '${rentalForm.rentPossiblelDayType}';
		
		if( rentPossiblelDayType == 'period' ){
			//$('#bnStartDate, #bnEndDate').val('');
			$('.date-prop').show();
		} else {
			//$('#bnStartDate, #bnEndDate').val('');
			$('.date-prop').hide();
		}
		
		$('input[name=rentPossiblelDayType]').on('change', function(){
			if($('input[name=rentPossiblelDayType]:checked').val() == 'period' ){
				$('.date-prop').show();
			}else{
				$('.date-prop').hide();
				$('.datepicker-ui').val('');
			}
		});
		
		
		//대관/대여 기간 달력
		$('.datepicker-ui').datepicker();
		
		
		//가능요일 배열
		var possibleDays = [];
		
		//체크박스 체크된 요일 인덱스 배열에 담는다.
		function possibleDaysCheck(){
			$('[name=rentPossibleDayOfWeek]:checked').each(function(index, item){
				possibleDays.push($(this).data('dayidx'));
			});
			//$.each(possibleDays, function(idx, item){
			//	alert(item);
			//});
		}
		
		//페이지 로드시 체크된 요일 배열에 담는다.
		possibleDaysCheck();
		
		//요일 체크 박스가 클릭 될 때마나다 가능요일 배열 새로 담는다.
		$('[name=rentPossibleDayOfWeek]').on('click', function(){
			possibleDays = [];
			possibleDaysCheck();
		});
		
		//불가능 요일 
		function impossibleDays(date){
			if($.inArray(date.getDay(), possibleDays) < 0 ){
				//alert($.inArray(date.getDay(), possibleDays));
				return [false];
			}else{
				return [true];
			}
		}
		
		//예약불가일 달력
		$('#rentImpossibleDate').multiDatesPicker({
			numberOfMonths: [1,3]
			//, beforeShowDay: $.datepicker.noWeekends
			, beforeShowDay: impossibleDays
			, minDate: 0	//today
			
		});
		
		//시간
		$(document).on('focus','.startTime, .endTime', function() {
			$(this).timepicker({
				timeFormat: 'HH:mm',
				interval: 60,
				minTime: '0',
				maxTime: '23:00',
				//defaultTime: '11',
				startTime: '00:00',
				dynamic: false,
				dropdown: true,
				scrollbar: true
			});
		});
		
		//예약가능시간 생성 
		$('.rentReservTermList').on('click', '.btnAddTime', function(e){
			//alert($('#eduConstructorRoundTimes0\\.eduConstructorRoundStime').attr('id'));
			var index = $('.rentReservTerm', $(this).closest('.termList')).length;
			var name = $(this).closest('.termList').data('name');
			//alert(index);
			var html = '<div class="form-inline mb-2 rentReservTerm">';
			html += '<input type="text" name="' + name + '[' + index+'].rentReservStartTime" id="' + name + index + '.rentReservStartTime" class="startTime form-control col-sm-3" maxlength="5" autocomplete="off" />';
			html += '<span class="col-form-label mx-2">~</span> ';
			html += '<input type="text" name="' + name + '[' + index + '].rentReservEndTime" id="' + name + index + '.rentReservEndTime" class="endTime form-control col-sm-3" maxlength="5" autocomplete="off" />';
			html += '<div class="custom-control custom-checkbox custom-control-inline mx-2">';
			html += '<input type="checkbox" name="' + name + '[' + index + '].rentIsNextday" id="' + name + index + '.rentIsNextday" class="custom-control-input" value="true" />';
			html += '<label for="' + name + index + '.rentIsNextday" class="custom-control-label"> 익일</label>';
			html += '</div>';
			html += '<button type="button" class="btnDeleteTime btn btn-outline-danger btn-sm waves-effect waves-light ml-2">삭제</button>';
			html += '<form:errors path="' + name + '[' + index + '].rentReservStartTime" element="div" cssClass="errorStartTime text-danger col-form-label ml-2" />';
			html += '<form:errors path="' + name + '[' + index + '].rentReservEneTime" element="div" cssClass="errorEndTime text-danger col-form-label ml-2" />';
			html += '</div>';
			$(this).closest('.termList').append($(html).fadeIn(600));
		});
		
		//예약가능시간 인덱스정리
		function resetIndex(listWrapper){
			var name = listWrapper.data('name');
			$('.rentReservTerm', listWrapper).each(function(idx, el){
				$(this).find('.startTime').attr('name', name + '[' + idx + '].rentReservStartTime');
				$(this).find('.startTime').attr('id', name + idx + '.rentReservStartTime');
				$(this).find('.endTime').attr('name', name + '[' + idx + '].rentReservEndTime');
				$(this).find('.endTime').attr('id', name + idx + '.rentReservEndTime');
				$(this).find('.errorStartTime').attr('path', name + '[' + idx + '].rentReservStartTime');
				$(this).find('.errorEndTime').attr('path', name + '[' + idx + '].rentReservEndTime');
			});
		}
		
		//예약가능시간 제거
		$('.rentReservTermList').on('click', '.btnDeleteTime', function(){
			var listWrapper = $(this).closest('.termList');
			$(this).closest('div').remove();
			resetIndex(listWrapper);
		});
	</c:if>
	
	<c:if test="${empty rentalForm.step or rentalForm.step eq 'basic'}">
		//회원할인률 show, hide
		var rentMembership = '${rentalForm.rentMembership}';
		//alert(rentMembership);
		if( rentMembership == 'true' ){
			$('.discount').show();
		} else {
			$('.discount').hide();
		}
		//멤버십 할인률 노출여부
		$('input[name=rentMembership]').on('change', function(){
			if($('input[name=rentMembership]:checked').val() == 'true' ){
				$('.discount').show();
			}else{
				$('.discount').hide();
				$('#rentMemberDiscount').val(0);
			}
		});
		
		//대관료책정 방식에 따라 비용방식 노출
		$('input[name=rentRentingMethod]').on('change', function(){
			if($('input[name=rentRentingMethod]:checked').val() == 'time' ){
				$('.chargeLabel').html('시간단위');
			}else{
				$('.chargeLabel').html('패키지단위');
			}
		});
		
		
		//시설구분1 선택에 따라 시설구분2 변경
		$('[name=rentCate1]').on('change',function(e){
			e.preventDefault();
			var sigugun = $(this).children('option:selected').data('sigugun');
			
			if(sigugun == null || sigugun == ''){
				//alert('선택된 코드가 없습니다.');
				$('[name=rentCate2]').val('');
				$('[name=rentCate2Name]').val('');
				return;
			}

			$.ajax({
				url : '${ADMIN_PATH}/code${API_PATH}/item/jsonList.do'
				, type : 'get'
				, data : {
					catId : 'SIGUNGU_CODE'
				}
				, dataType: 'json'
			}).done(function(result){
				$('[name=rentCate2]').val('');
				$('[name=rentCate2Name]').val('');
				if(result.length > 0){

					$.each(result, function(index, item){
						//alert(item.codeName);
						if(item.codeId == sigugun){
							$('[name=rentCate2]').val(item.codeId);
							$('[name=rentCate2Name]').val(item.codeName);
						}
					});
				}
				
			}).fail(function(result){
				alert('코드를 불러오지 못하였습니다.[fail]');
			});
		});
	</c:if>
	
	
});
</script>

	<!-- Left Sidebar -->	
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

	<!-- Start right Content here -->
	<div class="content-page">
	    <!-- Start content -->
	    <div class="content">
			
			<!-- Top Bar -->
			<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />
			
	        <div class="page-content-wrapper ">

				<div class="container-fluid">
			
					<!-- location -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />
					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 입력폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
					            
									<!-- 탭메뉴 -->
									<ul class="nav nav-tabs mb-2" role="tablist">
										<c:if test="${formMode eq 'INSERT'}">
											<li class="nav-item">
												<a class="nav-link active show disabled" href="#n" role="tab" aria-selected="false">기본정보</a>
											</li>
										</c:if>
										
										<c:if test="${formMode eq 'UPDATE'}">
											<li class="nav-item">
												<a class="nav-link<c:if test="${rentalForm.step eq 'basic'}"> active show</c:if>" href="${ADMIN_PATH}/rental/update.do?step=basic&rentId=${rentalForm.rentId}" role="tab" aria-selected="false">기본정보</a>
											</li>
											<li class="nav-item">
												<a class="nav-link<c:if test="${rentalForm.step eq 'applySet'}"> active show</c:if>" href="${ADMIN_PATH}/rental/update.do?step=applySet&rentId=${rentalForm.rentId}" role="tab" aria-selected="true">신청설정정보</a>
											</li>
										</c:if>
									</ul>
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					            	
					            	<!-- info -->
					            	<c:if test="${formMode eq 'INSERT'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 기본정보 등록 후 신청설정정보 등록 </div>
										</div>
									</c:if>
									
					            	<!-- info -->
					            	<c:if test="${formMode eq 'UPDATE' and rentalForm.step eq 'applySet'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div>제목 : ${rentalForm.rentTitle }  </div>
										</div>
									</c:if>
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/rental/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/rental/update.do" /></c:if>
						            <form:form modelAttribute="rentalForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="rentId" />
										<form:hidden path="step" />
										
										<%--==================================== --%>
										<%--============  기본정보 폼 ===============--%>
										<%--==================================== --%>
										<c:if test="${formMode eq 'INSERT' or (formMode eq 'UPDATE' and rentalForm.step eq 'basic')}">
											
											<div class="form-group row">
												<form:label path="rentCate1" cssClass="col-sm-2 col-form-label">캠퍼스타운 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:select path="rentCate1" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" >
														<form:option value="">선택</form:option>
														<c:forEach items="${rentCate1List}" var="item" varStatus="vs">
															<form:option value="${item.univId }" label="${item.univNm }" data-sigugun="${item.areaGuCd }" />
														</c:forEach>
													</form:select>
													<form:errors path="rentCate1" element="div" cssClass="text-danger col-form-label ml-2" />
													<!-- <span class="mt-2 bg-light font-weight-bold text-info"> code category : rent_cate1 - 대관 시설 구분코드1</span> -->
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentCate2" cssClass="col-sm-2 col-form-label">지역 </form:label>
												<%-- <div class="col-sm col-form-label">
													<div>
														<c:forEach items="${rentCate2List }" var="rentCate2Code" varStatus="vs"> 
															<c:set var="chkType" value="" />
															<c:forTokens items="${rentalForm.rentCate2 }" delims="," var="typeItem">
																<c:if test="${typeItem eq rentCate2Code.codeId }">
																	<c:set var="chkType" value="checked" />
																</c:if>
															</c:forTokens>
															<div class="custom-control custom-checkbox custom-control-inline">
																<input type="checkbox" name="rentCate2" id="rentCate2${vs.count }" class="custom-control-input" value="${rentCate2Code.codeId }" ${chkType } />
																<label for="rentCate2${vs.count }" class="custom-control-label"> ${rentCate2Code.codeName }</label>
															</div>
														</c:forEach>
														<form:errors path="rentReservType" element="div" cssClass="text-danger" />
													</div>
													<!-- <span class="mt-2 bg-light font-weight-bold text-info"> code category : rent_cate2 - 대관 시설 공간유형</span> -->
												</div> --%>
												
												<%-- <div class="col-sm col-form-label">
													<c:forEach items="${rentCate2List }" var="rentCate2Code" varStatus="vs">
														<div class="custom-control custom-radio custom-control-inline">
															<form:radiobutton path="rentCate2" class="custom-control-input" value="${rentCate2Code.codeId }"/>
															<label for="rentCate2${vs.count}" class="custom-control-label"> ${rentCate2Code.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="rentCate2" element="div" cssClass="text-danger" />
												</div> --%>
												<div class="col-sm col-form-label">
													<c:set var="tempCate2Name" value="" />
													<c:choose>
														<c:when test="${formMode eq 'INSERT' or (formMode eq 'UPDATE' and rentalForm.step eq 'basic') }">
															<c:forEach items="${rentCate2List }" var="rentCate2Code" varStatus="vs">
																<c:if test="${rentalForm.rentCate2 eq rentCate2Code.codeId}">
																<c:set var="tempCate2Name" value="${rentCate2Code.codeName}" />
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<c:set var="tempCate2Name" value="" />
														</c:otherwise>
													</c:choose>
													<form:hidden path="rentCate2" />
													<input type="text" name="rentCate2Name" class="form-control col-sm-3" readonly="readonly" value="${tempCate2Name}" />
													<form:errors path="rentCate2" element="div" cssClass="text-danger" />
												</div>
												
												<%-- <div class="col-sm">
													<form:select path="rentCate2" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${rentCate2List}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
													<form:errors path="rentCate2" element="div" cssClass="text-danger col-form-label ml-2" />
													<!-- <span class="mt-2 bg-light font-weight-bold text-info"> code category : rent_cate2 - 대관 시설 공간유형</span> -->
												</div> --%>
											</div>
											
											<div class="form-group row">
												<form:label path="rentTitle" cssClass="col-sm-2 col-form-label">공간명 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:input path="rentTitle" cssClass="form-control col-sm-10" cssErrorClass="form-control col-sm-10 is-invalid" maxlength="66" />
													<form:errors path="rentTitle" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentShortDescription" cssClass="col-sm-2 col-form-label">한줄설명 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:input path="rentShortDescription" cssClass="form-control col-sm-12" cssErrorClass="form-control col-sm-12 is-invalid"  />
													<form:errors path="rentShortDescription" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentDiv" cssClass="col-sm-2 col-form-label">대관/대여 구분 <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentDiv" class="custom-control-input" value="facility"/>
														<label for="rentDiv1" class="custom-control-label"> 대관</label>
													</div>
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentDiv" class="custom-control-input" value="item" disabled="true"/>
														<label for="rentDiv2" class="custom-control-label"> 대여</label>
													</div>
													<form:errors path="rentDiv" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentRentingMethod" cssClass="col-sm-2 col-form-label">대관료 책정 방식 <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentRentingMethod" class="custom-control-input" value="time"/>
														<label for="rentRentingMethod1" class="custom-control-label"> 시간단위</label>
													</div>
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentRentingMethod" class="custom-control-input" value="package" />
														<label for="rentRentingMethod2" class="custom-control-label"> 패키지 단위</label>
													</div>
													<form:errors path="rentRentingMethod" element="div" cssClass="text-danger" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentCharge" cssClass="col-sm-2 col-form-label">대관/대여 비용 
													(
												<span class="chargeLabel">
													<c:if test="${rentalForm.rentRentingMethod eq 'time' }">시간단위</c:if>
													<c:if test="${rentalForm.rentRentingMethod eq 'package' }">패키지단위</c:if>
												</span>
													)
												<span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:input path="rentCharge" cssClass="form-control col-sm-2 number" cssErrorClass="form-control col-sm-2 is-invalid number"  />
													<span class="col-form-label mx-2"> 원</span> 
													<form:errors path="rentCharge" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentParking" cssClass="col-sm-2 col-form-label">주차지원 대수 </form:label>
												<div class="col-sm form-inline">
													<form:input path="rentParking" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2"> 대</span> 
													<form:errors path="rentParking" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentReservType" cssClass="col-sm-2 col-form-label">예약 접수방법 <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${rentReservTypeCodeList }" var="reservTypeCode" varStatus="vs"> 
														<c:set var="chkType" value="" />
														<c:forTokens items="${rentalForm.rentReservType }" delims="," var="typeItem">
															<c:if test="${typeItem eq reservTypeCode.codeId }">
																<c:set var="chkType" value="checked" />
															</c:if>
														</c:forTokens>
														<div class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" name="rentReservType" id="rentReservType${vs.count }" class="custom-control-input" value="${reservTypeCode.codeId }" ${chkType } />
															<label for="rentReservType${vs.count }" class="custom-control-label"> ${reservTypeCode.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="rentReservType" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentPaymentType" cssClass="col-sm-2 col-form-label">결제방법 <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${rentPaymentTypeCodeList }" var="paymentTypeCode" varStatus="vs"> 
														<c:set var="chkType" value="" />
														<c:forTokens items="${rentalForm.rentPaymentType }" delims="," var="typeItem">
															<c:if test="${typeItem eq paymentTypeCode.codeId }">
																<c:set var="chkType" value="checked" />
															</c:if>
														</c:forTokens>
														<div class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" name="rentPaymentType" id="rentPaymentType${vs.count }" class="custom-control-input" value="${paymentTypeCode.codeId }" ${chkType } />
															<label for="rentPaymentType${vs.count }" class="custom-control-label"> ${paymentTypeCode.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="rentPaymentType" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<%-- 											
											<div class="form-group row">
												<form:label path="rentVR" cssClass="col-sm-2 col-form-label">홍보영상폴더 </form:label>
												<div class="col-sm">
													<form:input path="rentVR" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
													<form:errors path="rentVR" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div> --%>
											<hr>
											<div class="form-group row">
												<form:label path="rentApprove" cssClass="col-sm-2 col-form-label">승인기능사용여부 <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentApprove" class="custom-control-input" value="true"/>
														<label for="rentApprove1" class="custom-control-label"> 사용</label>
													</div>
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentApprove" class="custom-control-input" value="false"/>
														<label for="rentApprove2" class="custom-control-label"> 사용안함</label>
													</div>
													<form:errors path="rentApprove" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentMembership" cssClass="col-sm-2 col-form-label">멤버십할인 사용여부 <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentMembership" class="custom-control-input" value="true"/>
														<label for="rentMembership1" class="custom-control-label"> 사용</label>
													</div>
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentMembership" class="custom-control-input" value="false"/>
														<label for="rentMembership2" class="custom-control-label"> 사용안함</label>
													</div>
													<form:errors path="rentMembership" element="div" cssClass="text-danger" />
												</div>
											</div>
											<div class="form-group row discount">
												<form:label path="rentMemberDiscount" cssClass="col-sm-2 col-form-label">멤버십할인률 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:input path="rentMemberDiscount" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2"> %</span> 
													<form:errors path="rentMemberDiscount" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<%--사용여부를 신청설정정보를 등록할때까지 보이지 않게 --%>
											<c:if test="${formMode eq 'UPDATE' and rentalForm.step eq 'basic' and rentalForm.rentStep eq '2'}">	
												<div class="form-group row">
													<form:label path="rentUse" cssClass="col-sm-2 col-form-label">게시여부 <span class="text-danger">*</span></form:label>
													<div class="col-sm col-form-label">
														<div class="custom-control custom-radio custom-control-inline">
															<form:radiobutton path="rentUse" class="custom-control-input" value="true"/>
															<label for="rentUse1" class="custom-control-label"> 게시</label>
														</div>
														<div class="custom-control custom-radio custom-control-inline">
															<form:radiobutton path="rentUse" class="custom-control-input" value="false"/>
															<label for="rentUse2" class="custom-control-label"> 게시안함</label>
														</div>
														<form:errors path="rentUse" element="div" cssClass="text-danger" />
													</div>
												</div>
											</c:if>
											<hr>	

											
											<div class="form-group row">
												<form:label path="rentDescription" cssClass="col-sm-2 col-form-label">설명 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:textarea path="rentDescription" cssClass="form-control col-sm-8" rows="10" placeholder="설명" />
													<form:errors path="rentDescription" element="div" cssClass="text-danger" />
												</div>
											</div>
<%-- 											
											<div class="form-group row">
												<form:label path="rentFacilityInfo" cssClass="col-sm-2 col-form-label">시설안내 </form:label>
												<div class="col-sm">
													<form:textarea path="rentFacilityInfo" cssClass="form-control col-sm-8" rows="4" placeholder="시설안내" />
													<form:errors path="rentFacilityInfo" element="div" cssClass="text-danger" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentPrecautions" cssClass="col-sm-2 col-form-label">주의사항 </form:label>
												<div class="col-sm">
													<form:textarea path="rentPrecautions" cssClass="form-control col-sm-8" rows="4" placeholder="주의사항" />
													<form:errors path="rentPrecautions" element="div" cssClass="text-danger" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentRefundPokicy" cssClass="col-sm-2 col-form-label">환불규정 </form:label>
												<div class="col-sm">
													<form:textarea path="rentRefundPokicy" cssClass="form-control col-sm-8" rows="4" placeholder="환불규정" />
													<form:errors path="rentRefundPokicy" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentEtc" cssClass="col-sm-2 col-form-label">기타정보 </form:label>
												<div class="col-sm">
													<form:textarea path="rentEtc" cssClass="form-control col-sm-8" rows="4" placeholder="기타정보" />
													<form:errors path="rentEtc" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentAvailableTime" cssClass="col-sm-2 col-form-label">예약가능시간 </form:label>
												<div class="col-sm">
													<form:input path="rentAvailableTime" cssClass="form-control col-sm-10" cssErrorClass="form-control col-sm-10 is-invalid"  />
													<form:errors path="rentAvailableTime" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
 --%>											
											<div class="form-group row">
												<form:label path="rentImage" cssClass="col-sm-2 col-form-label">이미지 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<div>
														<c:if test="${not empty rentalForm.thumb && rentalForm.thumb.fileId > 0}">
															<%--<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${rentalForm.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" /> --%>
															<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${rentalForm.thumb.fileServletUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
														</c:if>
													</div>
													<div>
														<input type="file" name="rentImage" class="col-form-label" />
														<form:errors path="rentImage" element="div" cssClass="text-danger col-form-label ml-2" />
														<ul>
															<li>
																<span class="bg-light font-weight-bold text-info"> jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
															</li>
															<li><span class="bg-light font-weight-bold text-info"> 권장 사이즈 : 가로 XXX px, 세로 XX px</span></li>
															
														</ul>	
													</div>
													<div>
														<c:if test="${not empty thumbFileInfoUploadResult}">
															<div class="text-danger">
																<div>다음의 파일을 업로드 하지 못하였습니다.</div>
																<ul>
																	<c:forEach items="${thumbFileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
																	<c:forEach items="${thumbFileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
																	<c:forEach items="${thumbFileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
																</ul>
															</div>
														</c:if>
													</div>
	
												</div>
											</div>
											
											<hr>
											<div class="menu-title">담당자정보</div>
											<div class="form-group row">
												<form:label path="rentManager" cssClass="col-sm-2 col-form-label">담당자 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:input path="rentManager" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"  />
													<form:errors path="rentManager" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentManagerTel" cssClass="col-sm-2 col-form-label">담당자 연락처 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:input path="rentManagerTel" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"  />
													<form:errors path="rentManagerTel" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentManagerEmail" cssClass="col-sm-2 col-form-label">담당자 이메일 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:input path="rentManagerEmail" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
													<form:errors path="rentManagerEmail" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
										</c:if>
										
										<%--==================================== --%>
										<%--===========   신청설정정보 폼 =============--%>
										<%--==================================== --%>
										<c:if test="${formMode eq 'UPDATE' and rentalForm.step eq 'applySet'}">
											<div class="form-group row">
												<form:label path="rentPossiblelDayType" cssClass="col-sm-2 col-form-label">가능일 구분 <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentPossiblelDayType" class="custom-control-input" value="always"/>
														<label for="rentPossiblelDayType1" class="custom-control-label"> 상시</label>
													</div>
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="rentPossiblelDayType" class="custom-control-input" value="period"/>
														<label for="rentPossiblelDayType2" class="custom-control-label"> 기간</label>
													</div>
													<form:errors path="rentPossiblelDayType" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<div class="form-group row date-prop">
												<form:label path="rentPeriodSdate" cssClass="col-sm-2 col-form-label">대관/대여 기간 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2">시작일</span> 
													<form:input path="rentPeriodSdate" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 datepicker-ui is-invalid" autocomplete="off"/>
													<form:errors path="rentPeriodSdate" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row date-prop">
												<form:label path="rentPeriodEdate" cssClass="col-sm-2 col-form-label"></form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2">종료일</span> 
													<form:input path="rentPeriodEdate" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 datepicker-ui is-invalid" autocomplete="off"/>
													<form:errors path="rentPeriodEdate" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentReservSdateAfter" cssClass="col-sm-2 col-form-label">예약 가능일 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2">금일로 부터</span> 
													<form:input path="rentReservSdateAfter" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2">일 후 시작</span> 
													<form:errors path="rentReservSdateAfter" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentReservEdateAfter" cssClass="col-sm-2 col-form-label"></form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2">금일로 부터</span> 
													<form:input path="rentReservEdateAfter" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2">일 후 까지</span> 
													<form:errors path="rentReservEdateAfter" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentPossibleDayOfWeek" cssClass="col-sm-2 col-form-label">가능 요일</form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${dayOfWeekCodeList }" var="dayOfWeek" varStatus="vs"> 
														<c:set var="chkday" value="" />
														<c:forEach items="${possibleDayOfWeekArray }" var="possibleDay" >
															<c:if test="${possibleDay eq dayOfWeek.codeId }">
																<c:set var="chkday" value="checked" />
															</c:if>
														</c:forEach>
														<div class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" name="rentPossibleDayOfWeek" data-dayidx="${vs.index }" id="rentPossibleDayOfWeek${vs.count }" class="custom-control-input" value="${dayOfWeek.codeId }" ${chkday } />
															<label for="rentPossibleDayOfWeek${vs.count }" class="custom-control-label"> ${dayOfWeek.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="rentPossibleDayOfWeek" element="div" cssClass="text-danger" />
												</div>
											</div>
											
											<div class="form-group row">
												<form:label path="rentImpossibleDate" cssClass="col-sm-2 col-form-label">예약불가일 </form:label>
												<div class="col-sm form-inline">
													<form:input path="rentImpossibleDate" cssClass="form-control col-sm-10" cssErrorClass="form-control col-sm-10 is-invalid" autocomplete="off"/>
													<form:errors path="rentImpossibleDate" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
											<div class="form-group row rentReservTermList">
												<form:label path="rentReservTime" cssClass="col-sm-2 col-form-label">예약 가능 시간 <span class="text-danger">*</span></form:label>
												<!-- 평일 -->
												<div class="col-sm termList" data-name="rentReservTimeList">
													<div class="alert alert-primary"><strong>평일</strong></div>
													<div><button type="button" id="btnAddTime" class="btnAddTime btn btn-info btn-sm waves-effect waves-light mb-2">시간추가</button></div>
													<c:forEach items="${rentalForm.rentReservTimeList }" var="reservTime" varStatus="vs">
														<div class="form-inline mb-2 rentReservTerm">
															<input type="text" name="rentReservTimeList[${vs.index}].rentReservStartTime" id="rentReservTimeList${vs.index}.rentReservStartTime" value="${reservTime.rentReservStartTime}" class="startTime form-control col-sm-3" maxlength="5" autocomplete="off" />
															<span class="col-form-label mx-2">~</span> 
															<input type="text" name="rentReservTimeList[${vs.index}].rentReservEndTime" id="rentReservTimeList${vs.index}.rentReservEndTime" value="${reservTime.rentReservEndTime}" class="endTime form-control col-sm-3" maxlength="5" autocomplete="off" />
															
															<div class="custom-control custom-checkbox custom-control-inline mx-2">
																<input type="checkbox" name="rentReservTimeList[${vs.index}].rentIsNextday" id="rentReservTimeList${vs.index }.rentIsNextday" class="custom-control-input" value="true" <c:if test="${reservTime.rentIsNextday }">checked</c:if> />
																<label for="rentReservTimeList${vs.index }.rentIsNextday" class="custom-control-label"> 익일</label>
															</div>
															
															<c:if test="${vs.index != 0}">
															<button type="button" class="btnDeleteTime btn btn-outline-danger btn-sm waves-effect waves-light ml-2">삭제</button>
															</c:if>
															<form:errors path="rentReservTimeList[${vs.index}].rentReservStartTime" element="div" cssClass="errorStartTime text-danger col-form-label ml-2" />
															<form:errors path="rentReservTimeList[${vs.index}].rentReservEndTime" element="div" cssClass="errorEndTime text-danger col-form-label ml-2" />
														</div>
													</c:forEach>
												</div>
												<!-- 토요일 -->
												<div class="col-sm termList" data-name="rentReservTimeSatList">
													<div class="alert alert-success"><strong>토요일</strong></div>
													<div><button type="button" id="btnAddTimeSat" class="btnAddTime btn btn-info btn-sm waves-effect waves-light mb-2">시간추가</button></div>
													<c:forEach items="${rentalForm.rentReservTimeSatList }" var="reservTime" varStatus="vs">
														<div class="form-inline mb-2 rentReservTerm">
															<input type="text" name="rentReservTimeSatList[${vs.index}].rentReservStartTime" id="rentReservTimeSatList${vs.index}.rentReservStartTime" value="${reservTime.rentReservStartTime}" class="startTime form-control col-sm-3" maxlength="5" autocomplete="off" />
															<span class="col-form-label mx-2">~</span> 
															<input type="text" name="rentReservTimeSatList[${vs.index}].rentReservEndTime" id="rentReservTimeSatList${vs.index}.rentReservEndTime" value="${reservTime.rentReservEndTime}" class="endTime form-control col-sm-3" maxlength="5" autocomplete="off" />
															
															<div class="custom-control custom-checkbox custom-control-inline mx-2">
																<input type="checkbox" name="rentReservTimeSatList[${vs.index}].rentIsNextday" id="rentReservTimeSatList${vs.index }" class="custom-control-input" value="true" <c:if test="${reservTime.rentIsNextday }">checked</c:if> />
																<label for="rentReservTimeSatList${vs.index }" class="custom-control-label"> 익일</label>
															</div>
															
															<c:if test="${vs.index != 0}">
															<button type="button" class="btnDeleteTime btn btn-outline-danger btn-sm waves-effect waves-light ml-2">삭제</button>
															</c:if>
															<form:errors path="rentReservTimeSatList[${vs.index}].rentReservStartTime" element="div" cssClass="errorStartTime text-danger col-form-label ml-2" />
															<form:errors path="rentReservTimeSatList[${vs.index}].rentReservEndTime" element="div" cssClass="errorEndTime text-danger col-form-label ml-2" />
														</div>
													</c:forEach>
												</div>
												<!-- 일요일 -->
												<div class="col-sm termList" data-name="rentReservTimeSunList">
													<div class="alert alert-danger"><strong>일요일</strong></div>
													<div><button type="button" id="btnAddTimeSun" class="btnAddTime btn btn-info btn-sm waves-effect waves-light mb-2">시간추가</button></div>
													<c:forEach items="${rentalForm.rentReservTimeSunList }" var="reservTime" varStatus="vs">
														<div class="form-inline mb-2 rentReservTerm">
															<input type="text" name="rentReservTimeSunList[${vs.index}].rentReservStartTime" id="rentReservTimeSunList${vs.index}.rentReservStartTime" value="${reservTime.rentReservStartTime}" class="startTime form-control col-sm-3" maxlength="5" autocomplete="off" />
															<span class="col-form-label mx-2">~</span> 
															<input type="text" name="rentReservTimeSunList[${vs.index}].rentReservEndTime" id="rentReservTimeSunList${vs.index}.rentReservEndTime" value="${reservTime.rentReservEndTime}" class="endTime form-control col-sm-3" maxlength="5" autocomplete="off" />
															
															<div class="custom-control custom-checkbox custom-control-inline mx-2">
																<input type="checkbox" name="rentReservTimeSunList[${vs.index}].rentIsNextday" id="rentReservTimeSunList${vs.index }" class="custom-control-input" value="true" <c:if test="${reservTime.rentIsNextday }">checked</c:if> />
																<label for="rentReservTimeSunList${vs.index }" class="custom-control-label"> 익일</label>
															</div>
															
															<c:if test="${vs.index != 0}">
															<button type="button" class="btnDeleteTime btn btn-outline-danger btn-sm waves-effect waves-light ml-2">삭제</button>
															</c:if>
															<form:errors path="rentReservTimeSunList[${vs.index}].rentReservStartTime" element="div" cssClass="errorStartTime text-danger col-form-label ml-2" />
															<form:errors path="rentReservTimeSunList[${vs.index}].rentReservEndTime" element="div" cssClass="errorEndTime text-danger col-form-label ml-2" />
														</div>
													</c:forEach>
													
												</div>
											</div>
											
											<hr>
											<div class="form-group row">
												<form:label path="rentRefundDateBefore" cssClass="col-sm-2 col-form-label">환불 가능일 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2">예약일로 부터</span> 
													<form:input path="rentRefundDateBefore" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2">일 전 까지</span> 
													<form:errors path="rentRefundDateBefore" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentRefundRate" cssClass="col-sm-2 col-form-label">환불률 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:input path="rentRefundRate" readonly="true" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2"> %</span> 
													<form:errors path="rentRefundRate" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentPaymentTimeLimit" cssClass="col-sm-2 col-form-label">온라인결제 제한시간 </form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2">신청 후</span> 
													<form:input path="rentPaymentTimeLimit" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2">시간 내 미결제시 자동 취소</span> 
													<form:errors path="rentPaymentTimeLimit" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentResMinTime" cssClass="col-sm-2 col-form-label">최소예약 시간 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:input path="rentResMinTime" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2"> 시간</span> 
													<form:errors path="rentResMinTime" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentResMinNumber" cssClass="col-sm-2 col-form-label">최소예약 인원 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:input path="rentResMinNumber" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2"> 명</span> 
													<form:errors path="rentResMinNumber" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="rentResMaxNumber" cssClass="col-sm-2 col-form-label">최대수용 인원 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:input path="rentResMaxNumber" cssClass="form-control col-sm-1 number" cssErrorClass="form-control col-sm-1 is-invalid number"  />
													<span class="col-form-label mx-2"> 명</span> 
													<form:errors path="rentResMaxNumber" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											
										</c:if>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/rental/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
										</div>
									</form:form>
					            </div>
					        </div>
					    </div>
					</div>
					<!-- //입력폼 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->
	
<c:if test="${formMode eq 'INSERT' or (formMode eq 'UPDATE' and rentalForm.step eq 'basic')}">
<script>
//CKEDITOR
CKEDITOR.replace( 'rentDescription',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=rental&moduleId=&moduleSub=&moduleSubId='
} );
</script>
</c:if>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />