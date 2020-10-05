<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>
jQuery(function($){
	//내용 글자 카운트
	$('textarea[name=noteContent]').keyup( $.proxy(function(e){
		var content = $('textarea[name=noteContent]').val();
	    $('#counter').html(content.length + " / 1000자");    //글자수 실시간 카운팅

	}, this) );
	
	
	//보내기
	$('#sendBtn').on('click', function(e){
		e.preventDefault();
		var url = $('#noteForm').attr('action');
		var params = $('#noteForm').serialize();
		
		$.ajax({
			url : url
			, type : 'post'
			, dataType: 'json'
			, data: params
		}).done(function(result){
			$('.popup-box').hide();
			
			if(result.success){
				
				swal({
	                type: 'success',
	                title: '쪽지가 발송되었습니다.',
	                html: '발송결과 : ' + result.successCnt + '건',
	                //showCancelButton: true,
                    //confirmButtonClass: 'btn btn-success',
                    //cancelButtonClass: 'btn btn-danger m-l-10'
	            }).then(function(){
					location.reload();
	            });
			} else {
				if(result.text){
					swal('발송실패',result.text,'error');
		               
					/* swal({
		                icon: 'error',
		                title: '발송실패',
		                text: result.text
		            }); */
				} else {
					swal({
		                type: 'error',
		                title: '발송하지 못하였습니다.[error]'
		            });
				}
			}
		}).fail(function(result){
			$('.popup-box').hide();
			
			swal({
                type: 'error',
                title: '발송하지 못하였습니다.[fail]'
            });
		});
		
	});
	
});
</script>


<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<!-- content -->
	<div class="popup-content">
		<div class="popup-header">
			<p class="close"><a href="#n"><img src="${design.resource }/images/sub/popup-close.gif" alt="닫기"></a></p>
		</div>
		<form:form modelAttribute="noteForm" action="${APP_PATH}/mypage/note/insert" method="post">
			${SECURITY_TOKEN_TAG}
			<div class="popup-body">
				<div class="view-table table-con">
					<table>
						<caption>쪽지쓰기</caption>
						<colgroup>
							<col style="width: 15%">
							<col style="width: *">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">받는사람 </th>
								<td>
									<form:hidden path="noteReceiverId" />
									${noteForm.receiver.adminName } (${noteForm.receiver.adminId })
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<form:textarea path="noteContent" cols="30" rows="10" id="lb01"  maxlength="1000"/>
									<span class="txt_cnt" id="counter">0 / 1000자</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="btn-area txc clearfix">
					<a href="#none" class="btn02 fc-bg-gray fl close">취소</a>
					<a href="#none" id="sendBtn" class="btn02 fc-bg-blue fr">보내기</a>
				</div>
			</div>
		</form:form>
	</div>
	

	<!-- content end -->

