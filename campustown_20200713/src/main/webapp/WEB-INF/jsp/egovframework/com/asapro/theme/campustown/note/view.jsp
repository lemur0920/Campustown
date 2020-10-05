<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//삭제 Ajax
	$('#deleteBtn').on('click', function(e){
		var noteIdsByType = [];
		noteIdsByType.push($('.noteIdsByType').data('delid'));
		
		swal({
            title: '삭제한 항목은 복구할 수 없습니다.\n삭제하시겠습니까?',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (noteIdsByType.length === 0) {
                            reject('삭제할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${APP_PATH}/mypage/note/${noteDiv}/delete'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteIdsByType : noteIdsByType
					,${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '삭제가 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '삭제결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.href = '${APP_PATH}/mypage/note/${noteDiv}/list';
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
			                title: '삭제하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '삭제하지 못하였습니다.[fail]'
	            });
			});
        });
	});
	
	
	//발송취소 Ajax
	$('#cancelBtn').on('click', function(e){
		var cancelId = $(this).data('cancelid');
		
		swal({
            title: '발송취소를 하시겠습니까?',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (!cancelId ) {
                            reject('취소할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${APP_PATH}/mypage/note/cancel'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteReceptionId : cancelId
					,${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '발송취소가 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '취소결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
		            	location.href = '${APP_PATH}/mypage/note/${noteDiv}/list';
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
			                title: '취소하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '취소하지 못하였습니다.[fail]'
	            });
			});
        });
	});

});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />


<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<!-- content -->
	<!-- 탭메뉴 -->
	<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
	
	<!-- 받은쪽지 -->
    <!-- 받은쪽지 -->
    <!-- 받은쪽지 -->
    <c:if test="${noteDiv eq 'reception' }">
    	<div class="noteIdsByType" data-delid="${noteModel.noteReceptionId }"></div>
		<div class="view-table table-con">
			<table>
				<caption>받은쪽지 상세보기</caption>
				<colgroup>
					<col style="width: 15%">
					<col style="width: *">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">보낸사람 </th>
						<td>${noteModel.transmiter.adminName } (${noteModel.noteTransmiterId })</td>
					</tr>
					<tr>
						<th scope="row">받은시간</th>
						<td><fmt:formatDate value="${noteModel.noteSendDate}" pattern="yyyy-MM-dd [HH:mm]" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="lb01">
								${asapro:nl2br(noteModel.noteContent,false) }
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:if>
	
	<!-- 보낸쪽지 -->
    <!-- 보낸쪽지 -->
    <!-- 보낸쪽지 -->
    <c:if test="${noteDiv eq 'transmit' }">
    	<div class="noteIdsByType" data-delid="${noteModel.noteTransmitId }"></div>
    	<div class="view-table table-con">
			<table>
				<caption>받은쪽지 상세보기</caption>
				<colgroup>
					<col style="width: 15%">
					<col style="width: *">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">받는사람 </th>
						<td>${noteModel.receiver.adminName } (${noteModel.noteReceiverId })</td>
					</tr>
					<tr>
						<th scope="row">보낸시간</th>
						<td><fmt:formatDate value="${noteModel.noteSendDate}" pattern="yyyy-MM-dd [HH:mm]" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="lb01">
								${asapro:nl2br(noteModel.noteContent,false) }
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
    </c:if>
	
	<div class="btn-area txl clearfix">
		<a id="deleteBtn" class="btn04" href="#n">삭제</a>
		<c:if test="${noteDiv eq 'reception' }">
			<a id="replyBtn" class="btn05 noteWrite" href="${APP_PATH}/mypage/note/insert?noteReceiverId=${noteModel.noteTransmiterId}&reply=true"> 답장</a>
		</c:if>
		<c:if test="${noteDiv eq 'transmit' and !noteModel.noteOpen and noteModel.noteCancelYn eq 'N'}">
			<a id="cancelBtn" class="btn03" href="#n" data-cancelid="${noteModel.noteReceptionId}"> 발송취소</a>
		</c:if>
		<a class="btn01" href="${APP_PATH}/mypage/note/${noteDiv }/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a>
	</div>
	
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>


	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />