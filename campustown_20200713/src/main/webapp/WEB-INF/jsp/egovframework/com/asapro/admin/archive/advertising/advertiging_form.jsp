<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ page import="java.util.Date"%>
<jsp:useBean id="toDay" class="java.util.Date" />

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>

<script>
jQuery(function($){
	
	// 메타데이터 광고종류에 따라 show, hide
	var adtType = '${advertisingForm.arcMetaCode1}';
	
	if( adtType == 'broadcast' || adtType == '' ){//방송광고
		$('.broadcast').show();
		$('.nobroadcast,.webtoon').hide();
		
	} else {
		$('.broadcast,.webtoon').hide();
		$('.nobroadcast').show();
		
		if(adtType == 'webtoon'){
			$('.webtoon').show();
		}
	}
	
	$('input[name=arcMetaCode1]').on('change', function(){
		if($('input[name=arcMetaCode1]:checked').val() == 'broadcast' ){
			$('input','.nobroadcast,.webtoon').val('');
			$('.broadcast').show();
			$('.nobroadcast,.webtoon').hide();
		}else{
			$('input','.broadcast,.webtoon').val('');
			$('textarea','.broadcast,.webtoon').val('');
			$('.broadcast,.webtoon').hide();
			$('.nobroadcast').show();
			if($('input[name=arcMetaCode1]:checked').val() == 'webtoon'){
				$('.webtoon').show();
			}
		}
	});
	
	
	//메타코드 2 선택에 따라 메타코드 3 목록 변경
	$('[name=arcMetaCode2]').on('change',function(e){
		e.preventDefault();
		var catId = $(this).val();
		
		if(catId == null || catId == ''){
			//alert('선택된 코드가 없습니다.');
			$('#arcMetaCode3').empty();
			$('#arcMetaCode3').append("<option value=''>선택</option>");
			return;
		}

		$.ajax({
			url : '${ADMIN_PATH}/code${API_PATH}/item/jsonList.do'
			, type : 'get'
			, data : {
				catId : catId
			}
			, dataType: 'json'
		}).done(function(result){
			$('#arcMetaCode3').empty();
			$('#arcMetaCode3').append("<option value=''>선택</option>");
			if(result.length > 0){

				$.each(result, function(index, item){
					//alert(item.codeName);
					$('#arcMetaCode3').append("<option value='"+ item.codeId +"'>"+ item.codeName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
	
		
	//첨부파일
	//------------------------------------------
	//------------------------- 첨부파일 등록 Start
	//-------------------------------------------
	<c:if test="${archiveCategory.catUploadFileNum > 0}">
	$('.selectFile').multiSelector({
		list_target: 'fileList'
		,fileItemCssSelector: 'file_add'
		,list_delete_fileid: 'deleteFileList'
		,max: ${archiveCategory.catUploadFileNum} //설정기능 개발 후 재 적용
	});
	</c:if>
	
	//------------------------- 첨부파일 등록 End
	
	//비다오 첨부파일 제거
	$('.deleteVideoFile').on('click', function(e){
		e.preventDefault();
		$('#deleteVideo').val($('#videoFileFileId').val());
		$(this).closest('div').remove();
		$('#videoFileFileId').val(0) ;
	});
	//라디오 첨부파일 제거
	$('.deleteRadioFile').on('click', function(e){
		e.preventDefault();
		$('#deleteRadio').val($('#radioFileFileId').val());
		$(this).closest('div').remove();
		$('#radioFileFileId').val(0) ;
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
										<div><i class="mdi mdi-information"></i> ${archiveCategory.catName }  </div>
									</div>
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/advertising/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/advertising/update.do" /></c:if>
						            <form:form modelAttribute="advertisingForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="arcId" />
										<form:hidden path="arcCategory" />
										<form:hidden path="thumb.fileId" />
										<form:hidden path="videoFileInfo.fileId" id="videoFileFileId"/>
										<form:hidden path="radioFileInfo.fileId" id="radioFileFileId"/>
										<form:hidden path="deleteVideo" />
										<form:hidden path="deleteRadio" />
										
										<c:if test="${not empty archiveCategory.catMetaCode1 }">
											
											<%-- <div class="form-group row">
												<form:label path="arcMetaCode1" cssClass="col-sm-2 col-form-label">${arcMetaCode1Info.catName } <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:select path="arcMetaCode1" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
														<option value="" label="선택">
														<form:options items="${arcMetaCode1List }" itemLabel="codeName" itemValue="codeId"/>
													</form:select>
													<form:errors path="arcMetaCode1" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>	
											</div> --%>
											
											 
											<div class="form-group row">
												<form:label path="arcMetaCode1" cssClass="col-sm-2 col-form-label">${arcMetaCode1Info.catName } <span class="text-danger">*</span></form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${arcMetaCode1List }" var="metaCode1" varStatus="vs">
														<div class="custom-control custom-radio custom-control-inline">
															<form:radiobutton path="arcMetaCode1" class="custom-control-input" value="${metaCode1.codeId }"/>
															<label for="arcMetaCode1${vs.count}" class="custom-control-label"> ${metaCode1.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="arcMetaCode1" element="div" cssClass="text-danger" />
												</div>
											</div>
										</c:if>
										
										<c:if test="${not empty archiveCategory.catMetaCode2 }">
											<div class="form-group row arcMetaCode2">
												<form:label path="arcMetaCode2" cssClass="col-sm-2 col-form-label">${arcMetaCode2Info.catName } <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:select path="arcMetaCode2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
														<option value="" label="선택">
														<form:options items="${arcMetaCode2List }" itemLabel="codeName" itemValue="codeId"/>
													</form:select>
													<form:errors path="arcMetaCode2" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>	
											</div>
										</c:if>
										
										<%-- 
										<div class="form-group row">
											<form:label path="arcMetaCode3" cssClass="col-sm-2 col-form-label">공익광고 소분류 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:select path="arcMetaCode3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
									 				<c:if test="${not empty arcMetaCode3List }">
														<form:options items="${arcMetaCode3List }" itemLabel="codeName" itemValue="codeId"/>
													</c:if>
												</form:select>
												<form:errors path="arcMetaCode3" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>	
										</div> --%>
										<c:if test="${not empty archiveCategory.catMetaCode3 }">
											<div class="form-group row arcMetaCode3">
												<form:label path="arcMetaCode3" cssClass="col-sm-2 col-form-label">${arcMetaCode3Info.catName } <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:select path="arcMetaCode3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
														<option value="" label="선택">
														<form:options items="${arcMetaCode3List }" itemLabel="codeName" itemValue="codeId"/>
													</form:select>
													<form:errors path="arcMetaCode3" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>	
											</div>
										</c:if>
										
										
										<div class="form-group row">
											<form:label path="arcTitle" cssClass="col-sm-2 col-form-label">제목 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="arcTitle" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid"  />
												<form:errors path="arcTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="adtManufactureYear" cssClass="col-sm-2 col-form-label">제작년도 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<fmt:formatDate value="${toDay }" pattern="yyyy" var="thisYear" />
												<form:select path="adtManufactureYear" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<c:forEach begin="0" end="${thisYear - 1980 }" step="1" var="idx" >
														<form:option value="${thisYear - idx }" label="${thisYear - idx } 년" />
													</c:forEach>
												</form:select>
												<form:errors path="adtManufactureYear" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>	
										</div>
										
										<div class="form-group row nobroadcast">
											<form:label path="adtMedia" cssClass="col-sm-2 col-form-label">매체 </form:label>
											<div class="col-sm">
												<form:input path="adtMedia" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"  />
												<form:errors path="adtMedia" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="adtProducer" cssClass="col-sm-2 col-form-label">광고회사/제작사 </form:label>
											<div class="col-sm">
												<form:input path="adtProducer" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="adtProducer" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<%-- <div class="form-group row">
											<form:label path="arcTag" cssClass="col-sm-2 col-form-label">키워드 </form:label>
											<div class="col-sm">
												<form:input path="arcTag" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid"  />
												<form:errors path="arcTag" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div> --%>
										
										
										
										<%-- <div class="form-group row">
											<form:label path="arcContent" cssClass="col-sm-2 col-form-label">내용 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="arcContent" cssClass="form-control col-sm-8" rows="3"  />
												<form:errors path="arcContent" element="div" cssClass="text-danger" />
											</div>
										</div> --%>
										
										<div class="form-group row broadcast">
											<form:label path="adtDirector" cssClass="col-sm-2 col-form-label">감독 </form:label>
											<div class="col-sm">
												<form:input path="adtDirector" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"  />
												<form:errors path="adtDirector" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="adtBackMusic" cssClass="col-sm-2 col-form-label">배경음악 </form:label>
											<div class="col-sm">
												<form:input path="adtBackMusic" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" />
												<form:errors path="adtBackMusic" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="adtPlanIntertion" cssClass="col-sm-2 col-form-label">기획의도 </form:label>
											<div class="col-sm">
												<form:textarea path="adtPlanIntertion" cssClass="form-control col-sm-8" rows="3" maxlength="660"  />
												<form:errors path="adtPlanIntertion" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="adtComposition" cssClass="col-sm-2 col-form-label">구성 및 표현 </form:label>
											<div class="col-sm">
												<form:textarea path="adtComposition" cssClass="form-control col-sm-8" rows="3" maxlength="660"  />
												<form:errors path="adtComposition" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="adtProductionReview" cssClass="col-sm-2 col-form-label">제작후기 </form:label>
											<div class="col-sm">
												<form:textarea path="adtProductionReview" cssClass="form-control col-sm-8" rows="3" maxlength="660"  />
												<form:errors path="adtProductionReview" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="adtTvCf" cssClass="col-sm-2 col-form-label">TV CF Copy </form:label>
											<div class="col-sm">
												<form:textarea path="adtTvCf" cssClass="form-control col-sm-8" rows="4" maxlength="1300"  />
												<form:errors path="adtTvCf" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="adtRadioCf" cssClass="col-sm-2 col-form-label">Radio CF Copy </form:label>
											<div class="col-sm">
												<form:textarea path="adtRadioCf" cssClass="form-control col-sm-8" rows="4" maxlength="1300"  />
												<form:errors path="adtRadioCf" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										
										
										<c:if test="${archiveCategory.catSupportNuri }">
											<div class="form-group row">
												<form:label path="arcNuri" cssClass="col-sm-2 col-form-label">공공누리 </form:label>
												<div class="col-sm">
													<form:select path="arcNuri" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${nuriCodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
													<form:errors path="arcNuri" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										
										<div class="form-group row">
											<form:label path="arcUse" cssClass="col-sm-2 col-form-label">게시여부 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="arcUse" class="custom-control-input" value="true"/>
													<label for="arcUse1" class="custom-control-label"> 게시</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="arcUse" class="custom-control-input" value="false"/>
													<label for="arcUse2" class="custom-control-label"> 게시안함</label>
												</div>
												<form:errors path="arcUse" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<c:if test="${archiveCategory.catSupportImage }">
											<div class="form-group row">
												<form:label path="arcThumbFile" cssClass="col-sm-2 col-form-label">대표이미지 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<div>
														<c:if test="${not empty advertisingForm.thumb && advertisingForm.thumb.fileId > 0}">
															<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${advertisingForm.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
														</c:if>
													</div>
													<div>
														<input type="file" name="arcThumbFile" class="col-form-label" />
														<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png 파일만 업로드할 수 있습니다.</span>
														<form:errors path="arcThumbFile" element="div" cssClass="text-danger col-form-label ml-2" />
													</div>
													<div class="form-inline">
														<div class="col-form-label mr-2"><span class="text-danger">*</span> 대체텍스트 : </div>
														<form:input path="arcThumbText" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-4 is-invalid"  />
														<form:errors path="arcThumbText" element="div" cssClass="text-danger col-form-label ml-2" />
													</div>
													<div>
														<c:if test="${not empty thumbFileInfoUploadResult}">
															<div class="text-danger">
																<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
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
										</c:if>
										
										<div class="form-group row broadcast">
											<form:label path="videoFile" cssClass="col-sm-2 col-form-label">동영상 </form:label>
											<div class="col-sm">
												<div>
													<input type="file" name="videoFile" class="col-form-label" />
													<form:errors path="videoFile" element="div" cssClass="text-danger col-form-label ml-2" />
													<span class="bg-light font-weight-bold text-info">- wmv, mp4, flv, ogv 파일만 업로드할 수 있습니다.</span>
													<span class="bg-light font-weight-bold text-info">- (파일크기 ${archiveCategory.catUploadSizeMax} MB제한)</span>
												</div>
												<c:if test="${not empty advertisingForm.videoFileInfo && advertisingForm.videoFileInfo.fileId > 0}">
													<div class="aaa">
														<a href="${APP_PATH}/file/download/uu/${advertisingForm.videoFileInfo.fileUUID}" title="다운로드" >${advertisingForm.videoFileInfo.fileOriginalName} (${advertisingForm.videoFileInfo.fileSizeString})
															<i class="dripicons-download"></i>
														</a>
														<i class="deleteVideoFile ml-2 fa fa-times text-danger" style="cursor: pointer;"></i>
													</div>
													<%-- <div class="">
														<span>${advertisingForm.videoFileInfo.fileOriginalName}(${advertisingForm.videoFileInfo.fileSizeString})</span>
													</div> --%>
												</c:if>
												<div>
													<c:if test="${not empty videoFileInfoUploadResult}">
														<div class="text-danger">
															<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
															<ul>
																<c:forEach items="${videoFileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
																<c:forEach items="${videoFileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
																<c:forEach items="${videoFileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
															</ul>
														</div>
													</c:if>
												</div>

											</div>
										</div>
										
										<div class="form-group row broadcast">
											<form:label path="radioFile" cssClass="col-sm-2 col-form-label">라디오 </form:label>
											<div class="col-sm">
												<div>
													<input type="file" name="radioFile" class="col-form-label" />
													<form:errors path="radioFile" element="div" cssClass="text-danger col-form-label ml-2" />
													<span class="bg-light font-weight-bold text-info">- ogg, mp3, m4a, wav, wma 파일만 업로드할 수 있습니다.</span>
													<span class="bg-light font-weight-bold text-info">- (파일크기 ${archiveCategory.catUploadSizeMax} MB제한)</span>
												</div>
												<c:if test="${not empty advertisingForm.radioFileInfo && advertisingForm.radioFileInfo.fileId > 0}">
													<div>
														<a href="${APP_PATH}/file/download/uu/${advertisingForm.radioFileInfo.fileUUID}" title="다운로드" >${advertisingForm.radioFileInfo.fileOriginalName} (${advertisingForm.radioFileInfo.fileSizeString})
															<i class="dripicons-download"></i>
														</a>
														<i class="deleteRadioFile ml-2 fa fa-times text-danger" style="cursor: pointer;"></i>
													</div>
													<%-- <div class="">
														<span>${advertisingForm.videoFileInfo.fileOriginalName}(${advertisingForm.videoFileInfo.fileSizeString})</span>
													</div> --%>
												</c:if>
												<div>
													<c:if test="${not empty radioFileInfoUploadResult}">
														<div class="text-danger">
															<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
															<ul>
																<c:forEach items="${radioFileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
																<c:forEach items="${radioFileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
																<c:forEach items="${radioFileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
															</ul>
														</div>
													</c:if>
												</div>

											</div>
										</div>
										
										
										<%-- 첨부된 파일 --%>
										<c:if test="${archiveCategory.catUploadFileNum > 0}">
											<div class="form-group row webtoon">
												<label for="arcMultipartFiles" id="file_label" class="col-sm-2 col-form-label">첨부파일 </label>
												<div class="col-sm">
													<input type="file" name="arcMultipartFiles[0]" id="arcMultipartFiles0" class="selectFile col-form-label"/>
													<form:errors path="arcMultipartFiles" element="div" cssClass="text-danger col-form-label ml-2" />
													<span class="bg-light font-weight-bold text-info">- 파일첨부는 총${archiveCategory.catUploadFileNum}개까지 가능합니다.(파일당${archiveCategory.catUploadSizeMax}MB제한)</span>
													<div class="mt-2" id="fileList">
														<c:forEach items="${advertisingForm.arcFileInfos}" var="arcFileInfo" varStatus="vs">
															<div class="file_add">
																<span>${arcFileInfo.fileOriginalName}(${arcFileInfo.fileSizeString})</span>
																<i class="deleteBtn ml-2 fa fa-times text-danger" style="cursor: pointer;" data-fileid="${arcFileInfo.fileId}"></i>
															</div>
														</c:forEach>
													</div>
													<div id="deleteFileList"></div>
													
													<c:if test="${not empty fileInfoUploadResult}">
													<div class="text-danger">
														<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
														<ul>
															<c:forEach items="${fileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
															<c:forEach items="${fileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
															<c:forEach items="${fileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
														</ul>
													</div>
													</c:if>
												</div>
											</div>
										</c:if>
										
										<c:if test="${archiveCategory.catSupportSelect1 }">
											<hr>
											<div class="form-group row">
												<label class="col-sm-2 col-form-label">광고자료 추출 </label>
												<div class="col-sm">
													<div class="col-form-label">
														<div class="custom-control custom-checkbox custom-control-inline">
															<form:checkbox path="arcSelected1" class="custom-control-input" value="true"/>
															<label for="arcSelected11" class="custom-control-label"> 광고자료 추출</label>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/archive/advertising/list.do?arcCategory=${advertisingForm.arcCategory}" class="btn btn-secondary waves-effect m-l-5">목록</a>
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
	
<script>
//CKEDITOR
/* CKEDITOR.replace( 'arcContent',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=archive&moduleId=${archiveCategory.catCustomType}&moduleSub=${archiveCategory.catId}&moduleSubId=${advertisingForm.arcId}'
} ); */
</script>	

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />