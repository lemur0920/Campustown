<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>


<script>
jQuery(function($){

	
		
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
	<c:forEach items="${impossibleDayArray}" var="item">
	impossibleDayByReserv.push("${item}");
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
				//시간닫기 레이어에서는 모든 시간대가 완료,닫기 처리되어 불가한 날짜를 보여주기위해 체크 패스
				/* if($.inArray(year + '-' + month + '-' + day, impossibleDayByReserv) >= 0 ){	//불가능 일 체크
					return [false];
				} */
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
	$('#possibleTimeWrapper').on('change', '#reservDate', function(e){
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
					html += " <span class=\"badge badge-danger waves-effect waves-light mx-2 deleteBtn\" data-reservdate=\"" + reservDate + "\" data-rentid=\"" + rentId + "\" data-closetime=\"" + item.time + "\">열기</span>";
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
	
	
	//========================================================
	//예약시간 닫기 추가
	// Ajax
    $('#insertBtn').on('click', function () {
    	var reservTimes = [];
    	var reservDate = $('#reservDate').val();
		$('[name=reservTime]:checked').each(function(idx, el){
			reservTimes.push(el.value);
		});
		//alert(reservTimes);
        swal({
            title: '닫힘처리 하시겠습니까?',
            html: '닫힘처리한 항목은 다시 열림 처리할 수 있습니다.',
            showCancelButton: true,
            confirmButtonText: '닫힘',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
						if(reservDate == ''){
                            reject('닫힘처리할 날짜를 선택해 주세요.');
                        } else {
	                        if (reservTimes.length === 0) {
	                            reject('닫힘처리할 시간을 선택해 주세요.');
                   			} else {
	                            resolve();
                   			}
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/rental/reservation/closeTime/insert.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					rentId : rentId
					, reservDate : reservDate
					, reservTimes : reservTimes
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '닫힘처리가 완료되었습니다.',
		                html: '닫힘결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
		            	$('#possibleTimeList').load('${ADMIN_PATH}/rental/reservation/closeTime/Layer.do?rental.rentId=' + rentId + '&reservDate=' + reservDate);
						//location.reload();
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
			                title: '닫힘처리하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '닫힘처리하지 못하였습니다.[fail]'
	            });
			});
        });
    });
	
	//========================================================
	//시간닫음 재열기
	// Ajax
    $('#rentalReservationForm').on('click', '.deleteBtn', function () {
    	var rentId = $(this).data('rentid');
    	var reservDate = $(this).data('reservdate');
    	var reservCloseTime = $(this).data('closetime');
		
		//alert(reservCloseTime);
        swal({
            title: '다시 열림처리를 하시겠습니까?',
            html: '다시 열림처리한 항목은 다시 닫기 처리할 수 있습니다.',
            showCancelButton: true,
            confirmButtonText: '열림',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                   //      if (reservCloseTime.length === 0) {
                   //         reject('다시 열림처리할 항목을 선택해 주세요.');
                   //     } else {
                            resolve();
                   //     } 
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/rental/reservation/closeTime/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					rentId : rentId
					, reservDate : reservDate
					, reservTime : reservCloseTime
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '열림처리가 완료되었습니다.',
		                html: '열림결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
		            	$('#possibleTimeList').load('${ADMIN_PATH}/rental/reservation/closeTime/Layer.do?rental.rentId=' + rentId + '&reservDate=' + reservDate);
						//location.reload();
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
			                title: '열림처리하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '열림처리하지 못하였습니다.[fail]'
	            });
			});
        });
    });

});
</script>

					
<!-- ============================= 메뉴별 컨텐츠 ============================ -->
<!-- 입력폼 -->
<div class="row">
    <div class="col-sm-12">
        <div class="card m-b-15">
            <div class="card-body">
            
				<!-- alert maeeage -->
				<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
				
	            <form:form modelAttribute="rentalReservationForm" cssClass="" action="${ADMIN_PATH}/rental/reservation/closeTime/insert.do" method="post">
					<form:hidden path="rental.rentId" />
					
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
									<c:if test="${possibleTime.timeStatus eq 'close' }"><span class="badge badge-success badge-pill">닫</span><span class="badge badge-danger waves-effect waves-light mx-2 deleteBtn" data-reservdate="${rentalReservationForm.reservDate }" data-rentid="${rentalReservationForm.rental.rentId }" data-closetime="${possibleTime.time }">열기</span></c:if>
									<%-- <c:if test="${possibleTime.timeStatus eq 'possible' }"><span class="badge badge-info">닫기</span></c:if> --%>
								</div>
							</c:forEach>
							</div>
							<form:errors path="reservTime" element="div" cssClass="text-danger" />
						</div>
					</div>
					
					<div class="form-group">
						<button type="button" class="btn btn-primary waves-effect waves-light" id="insertBtn">시간닫음처리</button>
					</div>
					
				</form:form>
            </div>
        </div>
    </div>
</div>
<!-- //입력폼 -->

<!-- ============================= //메뉴별 컨텐츠 ============================ -->