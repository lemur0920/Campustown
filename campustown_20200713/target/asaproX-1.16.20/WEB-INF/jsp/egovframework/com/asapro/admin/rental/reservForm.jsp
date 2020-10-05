<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.js"></script>
<script src="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.css">
<script>
jQuery(function($){

	//대관/대여 기간 달력
	//$('.datepicker-ui').datepicker();
	
	//======  대관료 금액산정  ==========
	var rentCate1Name = '';
	<c:forEach items="${rentCate1List }" var="cate1" varStatus="vs">
		<c:if test="${rentalReservationForm.rental.rentCate1 eq cate1.univId }">
			rentCate1Name = '${cate1.univNm }';
		</c:if>
	</c:forEach>
	var pricePerHour = parseInt('${rentalReservationForm.rental.rentCharge }');	// 기준 대여료
	var rentingMethod = '${rentalReservationForm.rental.rentRentingMethod}';//대관료책정방식
	var hour = 0;	//대여시간
//	var hour = $('[name=selectPrice] option:selected').data("hour");	//대여시간
//	var addTime = $('#reservTimeAdd').val();	//추가 대여시간
	var rentName = '${rentalReservationForm.rental.rentTitle}';	//대관시설명
//	var price = $('#reservPrice').val();	//대관료
	var tempDiscountRate = '${rentalReservationForm.rental.rentMemberDiscount}';	//할인률
	var discountRate = 0;	//할인률
	var paymentAmount = $('#reservPaymentAmount').val();	//결제금액
	var tempReservPaymentAmount = 0;	//추가시간,할인률 적용 금액
	
	if(${rentalReservationForm.reservMembership} ){
		discountRate = tempDiscountRate;
	}
	
	<c:if test="${formMode eq 'UPDATE'}">
		hour = ${rentalReservationForm.reservUsageTime};
	</c:if>
	
	//대관료 선택시 금액 변경
/* 	$('[name=selectPrice]').on('change', function(){
		price = $(this).val();	
		$('#reservPrice').prop('value', price);
		hour = $('[name=selectPrice] option:selected').data("hour");
		//$('#reservPaymentAmount').prop('value', paymentAmount);
		memoCreate();
	}); */
	
	//대관료 직접 변경시
/* 	$('#reservPrice').on('change keydown keypress keyup click ', function(){
		price = $(this).val();
		hour = 0;
		memoCreate();
	}); */

	//할인률 변경시
/* 	$('#reservDiscountRate').on('change', function(){
		discountRate = $(this).val();
		memoCreate();
	}); */
	
	//추가시간 변경시
/* 	$('#reservTimeAdd').on('change', function(){
		addTime = $(this).val();
		memoCreate();
	}); */
	
	//예약시간 선택시
 	$('#rentalReservationForm').on('change','[name=reservTime]', function(){
 		hour = 0;
 		$('input[name=reservTime]:checked').each(function(){
			hour += $(this).data('timegap');
 		});
 		//alert(hour);
 		$('#reservUsageTime').val(hour);
 		
		memoCreate();
	}); 
	
	//멤버십여부 변경시
 	$('[name=reservMembership]').on('change', function(){
 		if($(this).is(':checked') ){
			discountRate = tempDiscountRate;
 		}else{
			discountRate = 0;
 		}
		memoCreate();
	});
	
	//메모 - 대관료 산정 텍스트
	function memoCreate(){
		var tempPriceSum = 0;
		//대관료
		tempPriceSum = pricePerHour * hour;
/* 		//추가시간 금액 합산 - 시간당 20% 할증
		tempPriceSum = tempPriceSum + parseInt(addTime * (pricePerHour + (pricePerHour * 0.2)) );
 */		
		//할인률적용 금액
		tempReservPaymentAmount = tempPriceSum - parseInt(tempPriceSum * (discountRate / 100));

		//세금10%적용 금액
//		paymentAmount = tempReservPaymentAmount + parseInt(tempReservPaymentAmount * 0.1);

		paymentAmount = tempReservPaymentAmount;
		$('#reservPrice').prop('value', tempPriceSum);
		$('#reservDiscountRate').prop('value', discountRate);
		$('#reservPaymentAmount').prop('value', paymentAmount);
		
		var text = '';
		text += '대관료 산정 : \n';
		text += '\t\t' + rentCate1Name + ' [' + rentName + '] ';
		if(rentingMethod == 'time'){
			text += hour + '시간 ';
		}
		text += '사용료 : ';
		text += numberWithCommas(tempPriceSum) + '원\n';
/* 		if(addTime > 0){
			text += '\t\t' + '추가 사용료 (20% 할증) : ';
			text += addTime + '시간 * ' + numberWithCommas(pricePerHour + (pricePerHour * 0.2)) + ' = ';
			text += numberWithCommas(addTime * (pricePerHour + (pricePerHour * 0.2))) + '원\n';
		} */
		if(discountRate > 0){
			text += '\t\t' + '할인 ' + discountRate + '% : -' + numberWithCommas(parseInt(tempPriceSum * discountRate / 100))  + ' 원\n';
		}
//		text += '\t\t' + '부가세 : ' + numberWithCommas(parseInt(tempReservPaymentAmount * 0.1)) + '원\n';
		text += '\t\t' + ' = ' + numberWithCommas(paymentAmount) + '원';
		$('#reservMemo').text(text);
	}
	//======  //대관료 금액산정  ==========
	
		
	//============= 가능일 달력 =======
	
	/*
		예약가능 한 나짜 시작, 종료 일 이내이며
		가능요일 이어야 하고
		불가능 지정한 날짜는 재외
		기예약된 날짜 재외
	*/
	
	var week = new Array('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
	var possibleDayOfWeek = '${rentalReservationForm.rental.rentPossibleDayOfWeek }'.split(',');	//가능요일 배열
	var minDate = '${rentalReservationForm.rental.rentReservSdateAfter }';	//최소 출력일
	var maxDate = '${rentalReservationForm.rental.rentReservEdateAfter }';	//최대 출력일
	var impossibleDay = '${rentalReservationForm.rental.rentImpossibleDate }'.split(', ');	//불가능일 배열
	var impossibleDayByReserv = new Array();
	<c:forEach items="${impossibleDayIncludeCloseArray}" var="item">
	impossibleDayByReserv.push('${item}');
	</c:forEach>
	//var impossibleDayByReserv = ${impossibleDayArray};
	
//	var impossibleDayByReserv = ['2018-12-13'];
	
	//가능, 불가능 리턴 
	function impossibleDays(date){
		var year = date.getFullYear().toString();
		var month = (date.getMonth() + 1).toString();
		if(month.length == 1 ){
			month = '0' + month;
		}
		var day = date.getDate().toString();
		if(day.length == 1 ){
			day = '0' + day;
		}
		
		if($.inArray(week[date.getDay()], possibleDayOfWeek) >= 0 ){	//가능요일 체크
			if($.inArray(year + '-' + month + '-' + day, impossibleDay) >= 0 ){	//불가능 일 체크
					return [false];
			}else{
				if($.inArray(year + '-' + month + '-' + day, impossibleDayByReserv) >= 0 ){	//불가능 일 체크
					return [false];
				}
				return [true];
			}
		}else{
			return [false];
		}
	}
	
	//예약불가일 달력
	$('#reservDate').datepicker({
		numberOfMonths: [1,3]	//출력 월 수
		//, beforeShowDay: $.datepicker.noWeekends	//주말선택불가
		, beforeShowDay: impossibleDays
		, minDate: minDate	//출력할 최소 날짜 
		, maxDate : maxDate	//출력할 최대 날짜
	});
	
	var rentId = '${rentalReservationForm.rental.rentId}';
	//선택날짜에대한 가능 시간
	$('#reservDate').on('change',function(e){
		e.preventDefault();

		var reservDate = $(this).val();
		var reservId = $('#reservId').val();

		if(reservDate == null || reservDate == ''){
			$('.reservTimes').empty();
			return;
		}
	
		$.ajax({
			url : '${ADMIN_PATH}/rental/reserv/possibleTimeList${API_PATH}/json.do'
			, type : 'get'
			, data : {
				reservDate : reservDate
				, rentId : rentId
				, reservId : reservId
			}
			, dataType: 'json'
		}).done(function(result){
			$('.reservTimes').empty();
			if(result.length > 0){
				
				var html = "";
				$.each(result, function(index, item){
				html += "<div class=\"custom-control custom-checkbox \">";
				html += "<input type=\"checkbox\" name=\"reservTime\" id=\"reservTime" + (index+1) + "\" class=\"custom-control-input\" ";
				html += "data-timegap=\"" + item.timeGap + "\" value=\"" + item.time + "\" ";
				if(item.timeStatus == 'complete' || item.timeStatus == 'close'){
					html += "disabled=\"disabled\"";
				}
				html += "/>";
				html += "<label for=\"reservTime" + (index+1) + "\" class=\"custom-control-label\"> " + item.time + "</label>";
				if(item.timeStatus == 'complete'){
					html += " <span class=\"badge badge-primary badge-pill\">완</span>";
				}
				if(item.timeStatus == 'close'){
					html += " <span class=\"badge badge-success badge-pill\">닫</span>";
				}
				html += "</div>";
				});
				html += '<form:errors path="reservTime" element="div" cssClass="text-danger" />';
				$('.reservTimes').append(html);
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
	
	//===========================================================
	//===========================================================
	//예약승인처리 - 알림메일발송
	$('#admissionBtn').on('click',function(e){
		//e.preventDefault();

		var reservId = $('#reservId').val();

		swal({
            title: '승인처리 하시겠습니까?',
            html: '예약 승인처리가 되고 알림 메일이 발송됩니다.',
            showCancelButton: true,
            confirmButtonText: '승인',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (reservId.length === 0) {
                            reject('승인할 항목이 없습니다.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/rental/reservation/admission.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservId : reservId
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '승인처리완료',
		                html: result.text
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
						//location.href = 'list.do';
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '승인처리를 하지지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '승인처리를 하지 못하였습니다.[fail]'
	            });
			});
        });
	});
	
	
	//예약취소처리 - 알림메일발송
	$('#cancelBtn').on('click',function(e){
		//e.preventDefault();

		var reservId = $('#reservId').val();

		swal({
            title: '취소처리 하시겠습니까?',
            html: '예약 취소처리가 되고 알림 메일이 발송됩니다.',
            showCancelButton: true,
            confirmButtonText: '예약취소',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (reservId.length === 0) {
                            reject('취소할 항목이 없습니다.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/rental/reservation/cancel.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservId : reservId
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '취소처리완료',
		                html: result.text
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
						//location.href = 'list.do';
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '취소처리를 하지지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '취소처리를 하지 못하였습니다.[fail]'
	            });
			});
        });
	});
	
	//결제처리 
	$('#patmentBtn').on('click',function(e){
		//e.preventDefault();

		var reservId = $('#reservId').val();

		swal({
            title: '결제처리 하시겠습니까?',
            html: '현장결제시 결제처리입니다 . 결제금액과 수령금액을 확인하세요.',
            showCancelButton: true,
            confirmButtonText: '결제처리',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (reservId.length === 0) {
                            reject('결제처리할 항목이 없습니다.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/rental/reservation/payment.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservId : reservId
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '결제처리완료',
		                html: result.text
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
						//location.href = 'list.do';
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '결제처리를 하지지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '결제처리를 하지 못하였습니다.[fail]'
	            });
			});
        });
	});
	
	//결제취소처리 
	$('#payCancelBtn').on('click',function(e){
		//e.preventDefault();

		var reservId = $('#reservId').val();

		swal({
            title: '결제취소처리 하시겠습니까?',
            html: '현장결제시 결제취소처리입니다 . 현장에서 처리한 금액을 확인후 환불처리하세요.',
            showCancelButton: true,
            confirmButtonText: '결제취소처리',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (reservId.length === 0) {
                            reject('결제취소처리할 항목이 없습니다.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/rental/reservation/payCancel.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservId : reservId
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '결제취소처리완료',
		                html: result.text
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
						//location.href = 'list.do';
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '결제취소처리를 하지지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '결제취소처리를 하지 못하였습니다.[fail]'
	            });
			});
        });
	});

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
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					            	
					            	<!-- info -->
									<div class="p-3 mb-3 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 대관/대여 신청 </div>
									</div>
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/rental/reservation/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/rental/reservation/update.do" /></c:if>
						            <form:form modelAttribute="rentalReservationForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="reservId" />
										<form:hidden path="reservUsageTime" />
										
										<div class="form-group row">
											<form:label path="rental.rentId" cssClass="col-sm-2 col-form-label">대관/대여 공간 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:hidden path="rental.rentId" />
												<form:input path="rental.rentTitle" cssClass="form-control col-sm-6" readonly="true" />
												<form:errors path="rental.rentId" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservName" cssClass="col-sm-2 col-form-label">신청자 명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="reservName" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
												<form:errors path="reservName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservPosition" cssClass="col-sm-2 col-form-label">신청자 직위/직책 </form:label>
											<div class="col-sm">
												<form:input path="reservPosition" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
												<form:errors path="reservPosition" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservTel1" cssClass="col-sm-2 col-form-label">신청자 전화번호 </form:label>
											<div class="col-sm form-inline">
												<form:select path="reservTel1" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid">
													<option value="" label="선택">
													<form:options items="${areaCodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												 - <form:input path="reservTel2" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
												 - <form:input path="reservTel3" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
												<form:errors path="reservTel1" element="div" cssClass="text-danger col-form-label ml-2" />
												<form:errors path="reservTel2" element="div" cssClass="text-danger col-form-label ml-2" />
												<form:errors path="reservTel3" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservHp1" cssClass="col-sm-2 col-form-label">신청자 휴대폰번호 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:select path="reservHp1" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid">
													<option value="" label="선택">
													<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												 - <form:input path="reservHp2" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
												 - <form:input path="reservHp3" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
												<form:errors path="reservHp1" element="div" cssClass="text-danger col-form-label ml-2" />
												<form:errors path="reservHp2" element="div" cssClass="text-danger col-form-label ml-2" />
												<form:errors path="reservHp3" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservEmail" cssClass="col-sm-2 col-form-label">신청자 이메일주소 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="reservEmail" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="reservEmail" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservOrganization" cssClass="col-sm-2 col-form-label">신청기관/단체명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="reservOrganization" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="reservOrganization" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
									<%-- 	<div class="form-group row">
											<form:label path="reservOrgDivCode" cssClass="col-sm-2 col-form-label">신청기관 구분 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label">
												<c:forEach items="${orgDivCodeList }" var="orgDivCode" varStatus="vs">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="reservOrgDivCode" class="custom-control-input" value="${orgDivCode.codeId }"/>
														<label for="reservOrgDivCode${vs.count }" class="custom-control-label"> ${orgDivCode.codeName }</label>
													</div>
												</c:forEach>
												<form:errors path="reservOrgDivCode" element="div" cssClass="text-danger" />
											</div>
										</div> --%>
										<div class="form-group row">
											<form:label path="reservZipcode" cssClass="col-sm-2 col-form-label">우편번호 </form:label>
											<div class="col-sm form-inline">
												<form:input path="reservZipcode" id="zipcode" cssClass="form-control col-sm-1 mr-sm-2" placeholder="우편번호" />
												<button id="zipSearchBtn" class="btn btn-primary waves-effect waves-light" >우편번호 찾기</button>
												<form:errors path="reservZipcode" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservAddress" cssClass="col-sm-2 col-form-label">주소 </form:label>
											<div class="col-sm">
												<form:input path="reservAddress" id="address" cssClass="form-control col-sm-6" placeholder="주소" />
												<form:errors path="reservAddress" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservAddressDetail" cssClass="col-sm-2 col-form-label">상세주소 </form:label>
											<div class="col-sm">
												<form:input path="reservAddressDetail" id="addressDetail" cssClass="form-control col-sm-6" placeholder="상세주소" />
												<form:errors path="reservAddressDetail" element="div" cssClass="text-danger" />
											</div>
										</div>
										<!-- Daum 우편번호 서비스 스크립트  -->
										<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/zipSearch.jsp" charEncoding="UTF-8" />
										
										<div class="form-group row">
											<form:label path="reservTotal" cssClass="col-sm-2 col-form-label">사용 인원 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<div class="form-inline">
													<form:input path="reservTotal" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
													<span class="col-form-label mx-2"> 명</span> 
													<form:errors path="reservTotal" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
												<span class="mt-2 bg-light font-weight-bold text-info"> 수용인원은 최소 <b class="text-warning">${rentalReservationForm.rental.rentResMinNumber }</b> 명, 최대 <b class="text-warning">${rentalReservationForm.rental.rentResMaxNumber }</b> 명 입니다.</span>
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservDate" cssClass="col-sm-2 col-form-label">예약일 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="reservDate" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 is-invalid datepicker-ui" autocomplete="off" readonly="true"/>
												<form:errors path="reservDate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservTime" cssClass="col-sm-2 col-form-label">예약시간 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label ">
												<div class="reservTimes">
												<c:forEach items="${possibleTimeList }" var="possibleTime" varStatus="vs"> 
													<c:set var="chkday" value="" />
													<c:forEach items="${reservTimeArray }" var="reservTime" >
														<c:if test="${reservTime eq possibleTime.time }">
															<c:set var="chkday" value="checked" />
														</c:if>
													</c:forEach>
													<div class="custom-control custom-checkbox ">
														<input type="checkbox" name="reservTime" id="reservTime${vs.count }" class="custom-control-input" data-timegap="${possibleTime.timeGap }" value="${possibleTime.time }" ${chkday } <c:if test="${possibleTime.timeStatus eq 'complete' or possibleTime.timeStatus eq 'close' }">disabled="disabled"</c:if> />
														
														<label for="reservTime${vs.count }" class="custom-control-label"> ${possibleTime.time } </label>
														<c:if test="${possibleTime.timeStatus eq 'complete' }"><span class="badge badge-primary badge-pill">완</span></c:if>
														<c:if test="${possibleTime.timeStatus eq 'close' }"><span class="badge badge-success badge-pill">닫</span></c:if>
														<%-- <c:if test="${possibleTime.timeStatus eq 'possible' }"><span class="badge badge-info">닫기</span></c:if> --%>
													</div>
												</c:forEach>
												</div>
												<%-- <span class="col-form-label mx-2"> 추가시간 : </span> 
												<form:select path="reservTimeAdd" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<form:option value="" label="추가시간"/>
													<form:option value="1" label="~ 19:00(1시간)"/>
													<form:option value="2" label="~ 20:00(2시간)"/>
													<form:option value="3" label="~ 21:00(3시간)"/>
													<form:option value="4" label="~ 22:00(4시간)"/>
												</form:select> --%>
												<form:errors path="reservTime" element="div" cssClass="text-danger" />
											</div>
											<%--
											<div class="col-sm">
												<form:select path="reservTime" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<form:option value="">선택</form:option>
													<form:options items="${termArray}"  />
												</form:select>
												<form:errors path="reservTime" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
											 --%>
										</div>
										<div class="form-group row">
											<form:label path="reservPaidType" cssClass="col-sm-2 col-form-label">결제방법 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label">
												<c:forTokens items="${rentalReservationForm.rental.rentPaymentType }" delims="," var="type" varStatus="vs">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="reservPaidType" class="custom-control-input" value="${type }"/>
														<label for="reservPaidType${vs.count }" class="custom-control-label"> ${asapro:codeName(type, rentPaymentTypeCodeList) }</label>
													</div>
												</c:forTokens>
											
												<%-- <c:forEach items="${rentPaymentTypeCodeList }" var="paidTypeCode" varStatus="vs">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="reservPaidType" class="custom-control-input" value="${paidTypeCode.codeId }"/>
														<label for="reservPaidType${vs.count }" class="custom-control-label"> ${paidTypeCode.codeName }</label>
													</div>
												</c:forEach> --%>
												<form:errors path="reservPaidType" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<c:if test="${rentalReservationForm.rental.rentMembership }">
										<div class="form-group row">
											<label class="col-sm-2 col-form-label">멤버십 여부 </label>
											<div class="col-sm col-form-label">
												<div>
													<div class="custom-control custom-checkbox custom-control-inline">
														<form:checkbox path="reservMembership" class="custom-control-input" value="true"/>
														<label for="reservMembership1" class="custom-control-label"> 멤버십</label>
													</div>
												</div>
												<span class="mt-2 bg-light font-weight-bold text-info"> 멤버십 할인 <b class="text-warning">${rentalReservationForm.rental.rentMemberDiscount }</b> % </span>
											</div>
										</div>
										</c:if>
										
										<div class="form-group row">
											<form:label path="reservTitle" cssClass="col-sm-2 col-form-label">행사명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="reservTitle"  cssClass="form-control col-sm-6" />
												<form:errors path="reservTitle" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservContent" cssClass="col-sm-2 col-form-label">행사 목적/내용 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="reservContent" cssClass="form-control col-sm-8" rows="5"  />
												<form:errors path="reservContent" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservRequests" cssClass="col-sm-2 col-form-label">요청사항 </form:label>
											<div class="col-sm">
												<form:textarea path="reservRequests" cssClass="form-control col-sm-8" rows="3"  />
												<form:errors path="reservRequests" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										
										<div class="form-group row">
											<form:label path="reservAppendingFile" cssClass="col-sm-2 col-form-label">첨부파일 </form:label>
											<div class="col-sm">
												<div>
													<c:if test="${not empty rentalReservationForm.reservAppendingFileInfo && rentalReservationForm.reservAppendingFileInfo.fileId > 0}">
														<%--<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${rentalForm.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" /> 
														<img class="reservAppendingFile" style="max-width: 200px; max-height: 200px;" src="${rentalForm.thumb.fileServletUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />--%>
														<a href="${APP_PATH}/file/download/uu/${rentalReservationForm.reservAppendingFileInfo.fileUUID}">
															<span>${rentalReservationForm.reservAppendingFileInfo.fileOriginalName} (${rentalReservationForm.reservAppendingFileInfo.fileSizeString})</span>
															<i class="ti-download"></i>
														</a>
													</c:if>
												</div>
												<div>
													<input type="file" name="reservAppendingFile" class="col-form-label" />
													<form:errors path="reservAppendingFile" element="div" cssClass="text-danger col-form-label ml-2" />
													<ul>
														<li>
															<span class="bg-light font-weight-bold text-info"> hwp, doc, docx, txt, rtf, ppt, pptx, xls, xlsx, pdf, jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
														</li>
													</ul>	
												</div>
												<div>
													<c:if test="${not empty fileInfoUploadResult}">
														<div class="text-danger">
															<div>다음의 파일을 업로드 하지 못하였습니다.</div>
															<ul>
																<c:forEach items="${fileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
																<c:forEach items="${fileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
																<c:forEach items="${fileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
															</ul>
														</div>
													</c:if>
												</div>

											</div>
										</div>
										
										<%--======================================================================================================= --%>
										<%--=======================================   신청내용 확인시, 수정시에만 표출  ================================================ --%>
										<%--======================================================================================================= --%>
										<hr>
										<c:if test="${formMode eq 'UPDATE' }">
										<div class="form-group row">
											<form:label path="reservStatus" cssClass="col-sm-2 col-form-label">신청상태 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:hidden path="reservStatus" />
												<input name="reservStatus2" class="form-control col-sm-2" readonly="readonly" value="${asapro:codeName(rentalReservationForm.reservStatus, statusCodeList) }"/>
												<%--
												<form:select path="reservStatus" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
													<form:options items="${statusCodeList}" itemLabel="codeName" itemValue="codeId" />
												</form:select>
												<form:errors path="reservStatus" element="div" cssClass="text-danger col-form-label ml-2" />
												 --%>
											</div>
										</div>
										</c:if>

<%-- 										<c:if test="${rentalReservationForm.reservStatus eq 'RS10' }">
										<!-- info -->
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 금액을 산정 후 저장 하시면 승인버튼이 노출 됩니다. </div>
										</div>
										</c:if> --%>
										
										<div class="form-group row">
											<form:label path="reservPrice" cssClass="col-sm-2 col-form-label">대관료 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<%-- <fmt:parseNumber integerOnly="true" var="pricePerHour" value="${rentalReservationForm.rental.rentCharge }" />
												<select name="selectPrice" class="form-control col-sm-1 mr-2" >
													<option value=""  data-hour="0" label="직접입력">
													<option value="${pricePerHour * 4 }" data-hour="4" label="<fmt:formatNumber pattern="#,###" value="${pricePerHour * 4 }"/>" <c:if test="${pricePerHour * 4 eq rentalReservationForm.reservPrice}"> selected="true"</c:if>>
													<option value="${pricePerHour * 5 }" data-hour="5" label="<fmt:formatNumber pattern="#,###" value="${pricePerHour * 5 }"/>" <c:if test="${pricePerHour * 5 eq rentalReservationForm.reservPrice}"> selected="true"</c:if>>
													<option value="${pricePerHour * 6 }" data-hour="6" label="<fmt:formatNumber pattern="#,###" value="${pricePerHour * 6 }"/>" <c:if test="${pricePerHour * 6 eq rentalReservationForm.reservPrice}"> selected="true"</c:if>>
													<option value="${pricePerHour * 7 }" data-hour="7" label="<fmt:formatNumber pattern="#,###" value="${pricePerHour * 7 }"/>" <c:if test="${pricePerHour * 7 eq rentalReservationForm.reservPrice}"> selected="true"</c:if>>
													<option value="${pricePerHour * 8 }" data-hour="8" label="<fmt:formatNumber pattern="#,###" value="${pricePerHour * 8 }"/>" <c:if test="${pricePerHour * 8 eq rentalReservationForm.reservPrice}"> selected="true"</c:if>>
												</select> --%>
												<form:input path="reservPrice" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" readonly="true" />
												<span class="col-form-label mx-2"> 원</span> 
												(
												<span>
													<c:if test="${rentalReservationForm.rental.rentRentingMethod eq 'time' }">시간단위</c:if>
													<c:if test="${rentalReservationForm.rental.rentRentingMethod eq 'package' }">패키지단위</c:if>
												</span>
													)
												<form:errors path="reservPrice" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<%-- <form:hidden path="reservPrice"/> --%>
										
										<div class="form-group row">
											<form:label path="reservDiscountRate" cssClass="col-sm-2 col-form-label">할인률 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<%-- <form:select path="reservDiscountRate" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
													<form:options items="${discountRateCodeList}" itemLabel="codeName" itemValue="codeId" />
													<form:option value="0" label="할인없음"/>
													<form:option value="10" label="10%"/>
													<form:option value="20" label="20%"/>
													<form:option value="30" label="30%"/>
													<form:option value="40" label="40%"/>
													<form:option value="50" label="50%"/>
													<form:option value="60" label="60%"/>
													<form:option value="70" label="70%"/>
													<form:option value="80" label="80%"/>
													<form:option value="90" label="90%"/>
													<form:option value="100" label="100%"/>
												</form:select> --%>
												<form:input path="reservDiscountRate" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" readonly="true" />
												<span class="col-form-label mx-2"> %</span> 
												<form:errors path="reservDiscountRate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<%-- <form:hidden path="reservDiscountRate"/> --%>
										
										<div class="form-group row">
											<form:label path="reservPaymentAmount" cssClass="col-sm-2 col-form-label">결제금액 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="reservPaymentAmount" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" readonly="true" />
												<span class="col-form-label mx-2"> 원</span> 
												<form:errors path="reservPaymentAmount" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservMemo" cssClass="col-sm-2 col-form-label">비고 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="reservMemo" cssClass="form-control col-sm-8" rows="8"  />
												<form:errors path="reservMemo" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<c:if test="${formMode eq 'UPDATE' }">
										
										<%-- <div class="form-group row">
											<form:label path="reservParking" cssClass="col-sm-2 col-form-label">주차지원 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="reservParking" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> 대</span> 
												<form:errors path="reservParking" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div> --%>
										<%-- <form:hidden path="reservParking"/> --%>
										

										</c:if>

										<div class="form-group">
											<c:if test="${empty rentalReservationForm.reservStatus || rentalReservationForm.reservStatus < 'RS40' }">
												<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											</c:if>
											<a href="${ADMIN_PATH}/rental/reservation/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
											<c:if test="${rentalReservationForm.reservStatus eq 'RS10' }">
												<button type="button" id="admissionBtn" class="btn btn-outline-warning waves-effect waves-light">승인</button>
											</c:if>
											<c:if test="${rentalReservationForm.reservStatus < 'RS40' }">
												<button type="button" id="cancelBtn" class="btn btn-outline-danger waves-effect waves-light">예약취소</button>
											</c:if>
											<c:if test="${rentalReservationForm.reservStatus eq 'RS31' }">
												<button type="button" id="patmentBtn" class="btn btn-outline-success waves-effect waves-light">결제처리(현)</button>
											</c:if>
											<c:if test="${rentalReservationForm.reservStatus eq 'RS40' and fn:startsWith(rentalReservationForm.reservPaidType, 'local') }">
												<button type="button" id="payCancelBtn" class="btn btn-outline-danger waves-effect waves-light">결제취소(현)</button>
											</c:if>
											
											
											<%-- <c:if test="${rentalReservationForm.reservStatus eq 'RS20' }">
												<a href="${ADMIN_PATH}/rental/reservation/printPreview.do?reservId=${rentalReservationForm.reservId }" target="_blank" class="btn btn-info waves-effect m-l-5">신청서 미리보기</a>
											</c:if> --%>
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
	
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />