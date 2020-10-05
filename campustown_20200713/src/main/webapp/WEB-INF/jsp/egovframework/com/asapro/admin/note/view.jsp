<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
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
				url : '${ADMIN_PATH}/note/${noteDiv}/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteIdsByType : noteIdsByType
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
						location.href = '${ADMIN_PATH}/note/${noteDiv}/list.do';
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
				url : '${ADMIN_PATH}/note/cancel.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteReceptionId : cancelId
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
		            	location.href = '${ADMIN_PATH}/note/${noteDiv}/list.do';
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
					<%-- <c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" /> --%>
					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 입력폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					        
					        	<div class="card-header">
					        	<h5>쪽지보기</h5>
								</div>
								
					            <div class="card-body">
									<!-- 탭메뉴 -->
									<ul class="nav nav-tabs mb-2" role="tablist">
										<li class="nav-item">
											<a class="nav-link text-info<c:if test="${noteDiv eq 'reception'}"> active show</c:if>" href="${ADMIN_PATH}/note/reception/list.do" role="tab" aria-selected="false">
												<img src="${CONTEXT_PATH }/assets/images/note_reception.png" alt="받은쪽지함" class="img-fluid m-r-5" style="height: 20px;" />받은쪽지함</a>
										</li>
										<li class="nav-item">
											<a class="nav-link text-info<c:if test="${noteDiv eq 'transmit'}"> active show</c:if>" href="${ADMIN_PATH}/note/transmit/list.do" role="tab" aria-selected="true">
												<img src="${CONTEXT_PATH }/assets/images/note_transmit.png" alt="보낸쪽지함" class="img-fluid m-r-5" style="height: 20px;" />보낸쪽지함</a>
										</li>
									</ul>
					            	<!-- 탭메뉴 -->
									<%-- <ul class="nav nav-pills mb-2" role="tablist" style="display: none">
										<li class="nav-item">
											<a class="nav-link btn-outline-success active show" href="${ADMIN_PATH}/note/${noteDiv}/view.do" role="tab" aria-selected="true">쪽지상세보기</a>
										</li>
									</ul> --%>
									<hr>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					            	
					            	<!-- 받은쪽지 -->
					            	<!-- 받은쪽지 -->
					            	<!-- 받은쪽지 -->
					            	<c:if test="${noteDiv eq 'reception' }">
					            		<div class="noteIdsByType" data-delid="${noteModel.noteReceptionId }"></div>
					            		<div class="form-group row">
											<label class="col-sm-1 col-form-label">
												<b>보낸사람</b> 
											</label>
											<div class="col-sm form-inline">
												${noteModel.transmiter.adminName } (${noteModel.noteTransmiterId })
											</div>
										</div>
										
										<div class="form-group row">
											<label class="col-sm-1 col-form-label"><b>받은시간</b></label>
											<div class="col-sm form-inline">
												<fmt:formatDate value="${noteModel.noteSendDate}" pattern="yyyy-MM-dd [HH:mm]" />
											</div>
										</div>
										<hr>
										<div class="form-group row">
											<label class="col-sm-1 col-form-label">내용 </label>
											<div class="col-sm">
												${asapro:nl2br(noteModel.noteContent,false) }
												
											</div>
										</div>
					            	</c:if>
					            	
					            	<!-- 보낸쪽지 -->
					            	<!-- 보낸쪽지 -->
					            	<!-- 보낸쪽지 -->
					            	<c:if test="${noteDiv eq 'transmit' }">
					            		<div class="noteIdsByType" data-delid="${noteModel.noteTransmitId }"></div>
					            		<div class="form-group row">
											<label class="col-sm-1 col-form-label">
												<b>받는사람</b> 
											</label>
											<div class="col-sm form-inline">
												${noteModel.receiver.adminName }(${noteModel.noteReceiverId })
											</div>
										</div>
										
										<div class="form-group row">
											<label class="col-sm-1 col-form-label"><b>보낸시간</b></label>
											<div class="col-sm form-inline">
												<fmt:formatDate value="${noteModel.noteSendDate}" pattern="yyyy-MM-dd [HH:mm]" />
											</div>
										</div>
										<hr>
										<div class="form-group row">
											<label class="col-sm-1 col-form-label">내용 </label>
											<div class="col-sm">
												${asapro:nl2br(noteModel.noteContent,false) }
												
											</div>
										</div>
					            	</c:if>
					            	
									
										
					            </div>
					            <div class="card-footer">
									<button id="deleteBtn" class="btn btn-danger waves-effect waves-light"><i class="mdi mdi-delete-forever"></i> 삭제</button>
									<c:if test="${noteDiv eq 'reception' }">
										<a href="${ADMIN_PATH}/note/insert.do?noteReceiverId=${noteModel.noteTransmiterId}&reply=true" id="replyBtn" class="btn btn-primary waves-effect waves-light"> 답장</a>
									</c:if>
									<c:if test="${noteDiv eq 'transmit' and !noteModel.noteOpen and noteModel.noteCancelYn eq 'N'}">
										<button id="cancelBtn" class="btn btn-warning waves-effect waves-light" data-cancelid="${noteModel.noteReceptionId}"> 발송취소</button>
									</c:if>
									<a href="${ADMIN_PATH}/note/${noteDiv }/list.do?cp=${backListSearch.cp}${backListSearch.queryString}" class="btn btn-secondary">목록</a>

									<%-- <a href="${ADMIN_PATH}/note/reception/list.do" class="btn btn-outline-info waves-effect waves-light">
										<img src="${CONTEXT_PATH }/assets/images/note_reception.png" alt="받은쪽지함" class="img-fluid m-r-5" style="height: 20px;" /> 받은쪽지함</a>
									<a href="${ADMIN_PATH}/note/transmit/list.do" class="btn btn-outline-info waves-effect waves-light">
										<img src="${CONTEXT_PATH }/assets/images/note_transmit.png" alt="보낸쪽지함" class="img-fluid m-r-5" style="height: 20px;" /> 보낸쪽지함</a> --%>
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