<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-and-player.js"></script>
<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelementplayer.css">
<script>
jQuery(function($){
	
	$('video, audio').mediaelementplayer({
		// Do not forget to put a final slash (/)
		//pluginPath: 'https://cdnjs.com/libraries/mediaelement/',
		// this will allow the CDN to use Flash without restrictions
		// (by default, this is set as `sameDomain`)
		//shimScriptAccess: 'always'
		// more configuration
	});
	
	//대체텍스트 수정
	$('.altUpdateBtn').on('click', function(e){
		e.preventDefault();
		var fileId = $(this).data('fileid');;
		var fileAltText = $('#fileAltText').val();

        swal({
            title: '수정하시겠습니까?',
            html: '수정 후 복구할 수 없습니다.',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '수정',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (fileId <= 0) {
                            reject('수정할 항목이 없습니다.');
                        } else {
                            if(fileAltText == null || fileAltText == ''){
                            	reject('대체텍스트가 없습니다.');
                            }
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/fileinfo/altText/update.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					fileId : fileId
					, fileAltText : fileAltText
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '수정이 완료되었습니다.',
		                //실제 수정건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '수정결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
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
			                title: '수정하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '수정하지 못하였습니다.[fail]'
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
									
						            <form:form modelAttribute="fileInfoForm" cssClass="" action="${ADMIN_PATH}/fileinfo/update.do" method="post" enctype="multipart/form-data">
										<form:hidden path="fileId" />
										
										<div class="form-group row">
											<label class="col-sm-2 col-form-label">파일보기 </label>
											<div class="col-sm">
											
												<div>
													<c:choose>
														<c:when test="${fileInfoForm.fileMediaType == 'IMAGE'}">
															<img class="img-thumbnail" src="${fileInfoForm.fileServletUrl}" alt="${fileInfoForm.fileOriginalName}" style="max-width: 80%; max-height: 900px;"/>
														</c:when>
														
														<c:when test="${fileInfoForm.fileMediaType == 'DOCUMENT'}">
															<asapro:fileIcon fileInfo="${fileInfoForm}" />
														</c:when>
														<c:when test="${fileInfoForm.fileMediaType == 'VIDEO'}">
															<c:if test="${fileInfoForm.fileExt eq 'mp4'}">
																<video class="" width="640" height="360" controls="controls" preload="none" >
																	<source type="video/mp4" src="${APP_PATH}/file/stream/uu/${fileInfoForm.fileUUID}" />
																	<object width="640" height="360" type="application/x-shockwave-flash" data="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-flash-video.swf">
																		<param name="movie" value="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-flash-video.swf" />
																		<param name="flashvars" value="controls=true&amp;poster=&amp;file=${APP_PATH}/file/stream/uu/${fileInfoForm.fileUUID}" />
																		<img src="myvideo.jpg" width="640" height="360" title="No video playback capabilities" />
																	</object>
																</video>
															</c:if>
															<c:if test="${fileInfoForm.fileExt ne 'mp4'}">
																<asapro:fileIcon fileInfo="${fileInfoForm}" />
															</c:if>
														</c:when>
														<c:when test="${fileInfoForm.fileMediaType == 'AUDIO'}">
															<c:if test="${fileInfoForm.fileExt eq 'mp3'}">
																<audio controls="controls" preload="none">
																     <source src="${APP_PATH}/file/stream/uu/${fileInfoForm.fileUUID}" type="audio/mpeg">
																</audio>
															</c:if>
															<c:if test="${fileInfoForm.fileExt ne 'mp3'}">
																<asapro:fileIcon fileInfo="${fileInfoForm}" />
															</c:if>
														</c:when>
														<c:when test="${fileInfoForm.fileMediaType == 'ETC'}">
															<asapro:fileIcon fileInfo="${fileInfoForm}" />
														</c:when>
													</c:choose>
												</div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="fileId" cssClass="col-sm-2 col-form-label">파일정보 </form:label>
											<div class="col-sm">
												<div class="table-responsive-lg">
													<table class="table table-bordered">
														<colgroup>
															<col style="width: 14%;" />
															<col style="width: 12%;" />
															<col style="width: 30%;"/>
															<col style="width: 12%;"/>
															<col style="width: 30%;" />
														</colgroup>
														<tbody>
															<tr>
																<th class="text-center bg-light align-middle" scope="row">파일명</th>
																<td class=" align-middle" colspan="4"><strong>${fileInfoForm.fileOriginalName }</strong></strong></td>
															</tr>
															<tr>
																<th class="text-center bg-light align-middle" rowspan="4" scope="row">기본정보</th>
																<th class="text-center bg-light align-middle" scope="row">파일종류</th>
																<td class="text-center align-middle">${fileInfoForm.fileMimeType }</td>
																<th class="text-center bg-light align-middle" scope="row">파일유형</th>
																<td class="text-center align-middle">${fileInfoForm.fileMediaType }</td>
															</tr>
															
															<tr>
																<th class="text-center bg-light align-middle" scope="row">등록일</th>
																<td class="text-center align-middle"><fmt:formatDate value="${fileInfoForm.fileRegDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
																<th class="text-center bg-light align-middle" scope="row">크기</th>
																<td class="text-center align-middle">${fileInfoForm.fileSizeString }</td>
															</tr>
															<tr>
																<th class="text-center bg-light align-middle" scope="row">UUID</th>
																<td class="text-center align-middle">${fileInfoForm.fileUUID }</td>
																<th class="text-center bg-light align-middle" scope="row">다운로드수</th>
																<td class="text-center align-middle">${fileInfoForm.fileDownloadCount }</td>
															</tr>
															
															<tr>
																<th class="text-center bg-light align-middle" scope="row">저장경로</th>
																<td class=" align-middle" colspan="3">${fileStorePath }${fileInfoForm.filePath }</td>
															</tr>
															<tr>
																<th class="text-center bg-light align-middle" scope="row">다운로드 URL</th>
																<td class=" align-middle" colspan="4">
																	${APP_PATH}/file/download/uu/${fileInfoForm.fileUUID }
																	<span class="ml-2"></span><a href="${APP_PATH}/file/download/uu/${fileInfoForm.fileUUID }" class="btn btn-outline-primary waves-effect waves-light btn-sm">다운로드</a>
																</td>
															</tr>

															<c:if test="${(fileInfoForm.fileMediaType eq 'VIDEO' and fileInfoForm.fileExt eq 'mp4') or (fileInfoForm.fileMediaType eq 'AUDIO' and fileInfoForm.fileExt eq 'mp3') }">
															<tr>
																<th class="text-center bg-light align-middle" scope="row">스트리밍 URL</th>
																<td class=" align-middle" colspan="4">
																		${APP_PATH}/file/stream/uu/${fileInfoForm.fileUUID }
																</td>
															</tr>
															</c:if>

															<c:if test="${fileInfoForm.fileMediaType == 'IMAGE'}">
															<tr>
																<th class="text-center bg-light align-middle" scope="row">대체텍스트</th>
																<td class=" align-middle" colspan="4">
																<div class="form-inline">
																	<form:input path="fileAltText" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" />
																	<span class="ml-2"></span><a href="#n" class="altUpdateBtn btn btn-outline-primary waves-effect waves-light btn-sm" data-fileid="${fileInfoForm.fileId }">수정</a>
																</div>
																</td>
															</tr>
															<tr>
																<th class="text-center bg-light align-middle" scope="row">썸네일 URL</th>
																<td class=" align-middle" colspan="4">${fileInfoForm.fileServletThumbnailUrl }</td>
															</tr>
															<tr>
																<th class="text-center bg-light align-middle" scope="row">원본 이미지 URL</th>
																<td class=" align-middle" colspan="4">${fileInfoForm.fileServletUrl }</td>
															</tr>
															</c:if>
														</tbody>
													</table>
												
												</div>
											</div>
										</div>
										
										<div class="form-group">
											<!-- <button type="submit" class="btn btn-primary waves-effect waves-light">수정</button> -->
											<a href="${ADMIN_PATH}/fileinfo/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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