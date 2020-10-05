<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<c:if test="${!boardConfig.bcSupportAnswer && boardConfig.bcType ne 'qna' && boardConfig.bcType ne 'faq' && !fn:startsWith(boardArticleForm.memId, 'member_') && !fn:startsWith(boardArticleForm.memId, 'guest_')}">
<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>
</c:if>
<script src="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.css">
<script>
jQuery(function($){
	
	//공지종료일 노출
	$('#baNotice1').on('click', function(){
		if($(this).is(':checked') == true ){
			$('.baNoticeEndDate').show();
		}else{
			$('.baNoticeEndDate').hide();
			$('input[name=baNoticeEndDate]').val('');
		}
	});
	
	
	//공통게시 카테고리선택 노출
	<c:if test="${!boardArticleForm.baCommSelec }">
		$('#baComSelCat').hide();
	</c:if>
	$('#baCommSelec1').on('click', function(){
		if($(this).is(':checked') == true ){
			$('#baComSelCat').show();
		}else{
			$('#baComSelCat').hide();
			$('#baComSelCat').val('');
		}
	});
	
	//비밀글 비밀번호 노출
	$('#baSecret1').on('click', function(){
		if($(this).is(':checked') == true ){
			$('.baSecretPassword').show();
		}else{
			$('.baSecretPassword').hide();
			$('#baSecretPassword').val('');
		}
	});
	
	//달력
	$('.datepicker-ui').datepicker();
	
	//첨부파일
	//------------------------------------------
	//------------------------- 첨부파일 등록 Start
	//-------------------------------------------
	<c:if test="${boardConfig.bcUploadFileNum > 0}">
	$('.selectFile').multiSelector({
		list_target: 'fileList'
		,fileItemCssSelector: 'file_add'
		,list_delete_fileid: 'deleteFileList'
		,max: ${boardConfig.bcUploadFileNum}
	});
	</c:if>
	//------------------------- 첨부파일 등록 End


	//시간
	$(document).on('focus','.startTime', function() {
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
	
	<c:if test="${boardConfig.bcId eq 'product' }">
	//부서선택시 창업팀목록 가져오기
	$('#baCategory2').on('change',function(e){
		e.preventDefault();
		var depId = $(this).val();
		
		if(depId == null || depId == ''){
			$('#baCategory3').empty();
			$('#baCategory3').append("<option value=''>선택</option>");
			return;
		}

		$.ajax({
			url : '${ADMIN_PATH}/organization/team${API_PATH}/jsonList.do'
			, type : 'get'
			, data : {
				depId : depId
			}
			, dataType: 'json'
		}).done(function(result){
			$('#baCategory3').empty();
			$('#baCategory3').append("<option value=''>선택</option>");
			if(result.length > 0){
				$.each(result, function(index, item){
					//alert(item.teamName);
					$('#baCategory3').append("<option value='"+ item.compId +"' >"+ item.compNm +"</option>");
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
					        	<div class="card-header">
									<h4 class="mt-0 header-title">${boardConfig.bcName }
										<c:if test="${formMode == 'INSERT'}">- 글등록</c:if>
										<c:if test="${formMode == 'UPDATE'}">- 글수정</c:if>
									</h4>
								</div>
					        
					            <div class="card-body">
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/board/article/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/board/article/update.do" /></c:if>
						            <form:form modelAttribute="boardArticleForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="bcId" />
										<form:hidden path="baId" />
										<form:hidden path="thumb.fileId" />
										<div class="form-group row">
											<form:label path="baTitle" cssClass="col-sm-2 col-form-label">제목 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="baTitle" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" htmlEscape="false" />
												<form:errors path="baTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="baGuestName" cssClass="col-sm-2 col-form-label">작성자 </form:label>
											<div class="col-sm">
												<c:if test="${formMode eq 'INSERT'}">
												<input name="baGuestName" value="${sessionScope.currentAdmin.adminName}" readonly="readonly" class="form-control col-sm-2" />
												</c:if>
												<c:if test="${formMode eq 'UPDATE'}">
													<c:if test="${boardArticleForm.member == null }"><input name="baGuestName" value="${boardArticleForm.baGuestName}" readonly="readonly" class="form-control col-sm-2" /></c:if>
													<c:if test="${boardArticleForm.member != null and not empty boardArticleForm.member.memberName}"><input name="baGuestName" value="${boardArticleForm.member.memberName}" readonly="readonly" class="form-control col-sm-2" /></c:if>
												</c:if>
												<form:errors path="baGuestName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="baEmail" cssClass="col-sm-2 col-form-label">이메일 </form:label>
											<div class="col-sm">
												<c:if test="${formMode eq 'INSERT'}">
													<form:input path="baEmail" value="${sessionScope.currentAdmin.adminEmail}" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"  />
												</c:if>
												<c:if test="${formMode eq 'UPDATE'}">
													<form:input path="baEmail" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"  />
												</c:if>
												<form:errors path="baEmail" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
											<div class="form-group row">
												<form:label path="baCategory1" cssClass="col-sm-2 col-form-label">${boardConfig.bcCategory1Name} </form:label>
												<div class="col-sm">
													<form:select path="baCategory1" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${bcCategory1CodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
												</div>
											</div>
										</c:if>
										
										<%-- //창업 제품 소개 및 장터 게시판 예외처리 --%>
										<c:if test="${boardConfig.bcId ne 'product' }">
										
											<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
												<div class="form-group row">
													<form:label path="baCategory2" cssClass="col-sm-2 col-form-label">${boardConfig.bcCategory2Name} </form:label>
													<div class="col-sm">
														<form:select path="baCategory2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
															<form:option value="">선택</form:option>
															<form:options items="${bcCategory2CodeList}" itemLabel="codeName" itemValue="codeId" />
														</form:select>
													</div>
												</div>
											</c:if>
											<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
												<div class="form-group row">
													<form:label path="baCategory3" cssClass="col-sm-2 col-form-label">${boardConfig.bcCategory3Name} </form:label>
													<div class="col-sm">
														<form:select path="baCategory3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
															<form:option value="">선택</form:option>
															<form:options items="${bcCategory3CodeList}" itemLabel="codeName" itemValue="codeId" />
														</form:select>
													</div>
												</div>
											</c:if>
										</c:if>
										<c:if test="${boardConfig.bcId eq 'product' }">
											<div class="form-group row">
												<form:label path="baCategory2" cssClass="col-sm-2 col-form-label">캠퍼스타운 </form:label>
												<div class="col-sm">
													<form:select path="baCategory2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${departmentList}" itemLabel="depName" itemValue="depId" />
													</form:select>
												</div>
											</div>
											<div class="form-group row">
												<form:label path="baCategory3" cssClass="col-sm-2 col-form-label">창업팀 </form:label>
												<div class="col-sm">
													<form:select path="baCategory3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${teamList}" itemLabel="compNm" itemValue="compId" />
													</form:select>
												</div>
											</div>
										</c:if>
										
										
										<c:if test="${not empty boardConfig.bcStatusCode}">
											<div class="form-group row">
												<form:label path="baStatus" cssClass="col-sm-2 col-form-label">진행상태 </form:label>
												<div class="col-sm">
													<form:select path="baStatus" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${bcStatusCodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
												</div>
											</div>
										</c:if>
										
										<!-- 사용자 정의 필드 출력 -->
										<c:if test="${not empty boardConfig.bcCustomFieldUseToArray}">
											<c:set var="displayCnt" value="${fn:length(boardConfig.bcCustomFieldUseToArray)}"/>
											
											<c:forEach begin="1" end="${displayCnt }" var="i" step="1">
												<c:if test="${boardConfig.bcCustomFieldUseToArray[i-1] eq 'true'}">
													<c:set var="fieldName" value="bcCustomField${i }"/>
													<div class="form-group row">
														<form:label path="baCustomField${i }" cssClass="col-sm-2 col-form-label">${boardConfig[fieldName] } </form:label>
														<div class="col-sm">
															<form:input path="baCustomField${i }" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid"  />
															<form:errors path="baCustomField${i }" element="div" cssClass="text-danger col-form-label ml-2" />
														</div>
													</div>
												</c:if>
											</c:forEach>
										</c:if>
										
										<div class="form-group row">
											<form:label path="baContentHtml" cssClass="col-sm-2 col-form-label">내용 </form:label>
											<div class="col-sm">
												<form:textarea path="baContentHtml" cssClass="form-control col-sm-8" rows="16" placeholder="내용" htmlEscape="false"/>
												<form:errors path="baContentHtml" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="baUse" cssClass="col-sm-2 col-form-label">게시여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="baUse" class="custom-control-input" value="true"/>
													<label for="baUse1" class="custom-control-label"> 게시</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="baUse" class="custom-control-input" value="false"/>
													<label for="baUse2" class="custom-control-label"> 게시안함</label>
												</div>
												<form:errors path="baUse" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<c:if test="${boardConfig.bcSupportNotice}">
											<div class="form-group row">
												<label class="col-sm-2 col-form-label">공지글 </label>
												<div class="col-sm">
													<div class="col-form-label">
														<div class="custom-control custom-checkbox custom-control-inline">
															<form:checkbox path="baNotice" class="custom-control-input" value="true"/>
															<label for="baNotice1" class="custom-control-label"> 공지</label>
														</div>
													</div>
												</div>
											</div>
											
											<div class="form-group row baNoticeEndDate " style="display: <c:if test='${!boardArticleForm.baNotice}'>none;</c:if>">
												<form:label path="baNoticeEndDate" cssClass="col-sm-2 col-form-label"> </form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2"> 공지종료일 : </span>
													<input name="baNoticeEndDate" class="form-control mr-sm-1 datepicker-ui<form:errors path="baNoticeEndDate"> is-invalid</form:errors>" value="${asapro:formatStringDate(boardArticleForm.baNoticeEndDate, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd') }" autocomplete="off" />
													<form:errors path="baNoticeEndDate" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										
										<c:if test="${boardConfig.bcSupportMainSelec}">
											<div class="form-group row">
												<label class="col-sm-2 col-form-label">메인페이지 추출 </label>
												<div class="col-sm">
													<div class="col-form-label">
														<div class="custom-control custom-checkbox custom-control-inline">
															<form:checkbox path="baMainSelec" class="custom-control-input" value="true"/>
															<label for="baMainSelec1" class="custom-control-label"> 메인추출</label>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										
										<c:if test="${boardConfig.bcSupportCommSelec}">
											<div class="form-group row">
												<label class="col-sm-2 col-form-label">공통게시판 노출 </label>
												<div class="col-sm form-inline">
													<div class="col-form-label">
														<div class="custom-control custom-checkbox custom-control-inline">
															<form:checkbox path="baCommSelec" class="custom-control-input" value="true"/>
															<label for="baCommSelec1" class="custom-control-label"> 공통노출</label>
														</div>
													</div>
												<c:if test="${not empty bcCategory1CodeList}">
														<form:select path="baComSelCat" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
															<form:option value="">선택</form:option>
															<form:options items="${bcCategory1CodeList}" itemLabel="codeName" itemValue="codeId" />
														</form:select>
												</c:if>
												</div>
											</div>
										</c:if>
										
										<c:if test="${boardConfig.bcSupportOpenDay}">
											<div class="form-group row">
												<form:label path="baStartDate" cssClass="col-sm-2 col-form-label">게시날짜 설정 </form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2">게시일시</span>
													<form:input path="baStartDate" cssClass="form-control mr-sm-1 datepicker-ui" cssErrorClass="form-control mr-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
													<form:input path="baStartTime" cssClass="form-control ml-2 col-sm-1 startTime" cssErrorClass="form-control ml-2 col-sm-1 startTime is-invalid" maxlength="5" autocomplete="off"/>
													<form:errors path="baStartDate" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="baEndDate" cssClass="col-sm-2 col-form-label"></form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-4">종료일</span> 
													<form:input path="baEndDate" cssClass="form-control mr-sm-1 datepicker-ui" cssErrorClass="form-control mr-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
													<form:errors path="baEndDate" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										
										<c:if test="${boardConfig.bcSupportSecret}">
											<div class="form-group row">
												<label class="col-sm-2 col-form-label">비밀글 </label>
												<div class="col-sm col-form-label">
													<div class="custom-control custom-checkbox custom-control-inline">
														<form:checkbox path="baSecret" class="custom-control-input" value="true"/>
														<label for="baSecret1" class="custom-control-label"> 비밀</label>
													</div>
												</div>
											</div>
											
											<div class="form-group row baSecretPassword " style="display: <c:if test='${!boardArticleForm.baSecret}'>none;</c:if>">
												<form:label path="baSecretPassword" cssClass="col-sm-2 col-form-label"> </form:label>
												<div class="col-sm form-inline">
													<span class="col-form-label mr-2"> 비밀번호 : </span>
													<form:password path="baSecretPassword" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" />
													<form:errors path="baSecretPassword" element="div" cssClass="text-danger col-form-label ml-2" />
													<c:if test="${formMode eq 'UPDATE' and boardArticleForm.baSecret}">
														<span class="ml-2 mt-2 bg-light font-weight-bold text-info">- 비밀글 비밀번호를 변경시 입력해 주세요.(공란시 기존비밀번호가 유지됩니다.)</span>
													</c:if>
												</div>
											</div>
										</c:if>
										
										<c:if test="${boardConfig.bcSupportNuri}">
											<div class="form-group row">
												<form:label path="baNuri" cssClass="col-sm-2 col-form-label">공공누리 </form:label>
												<div class="col-sm">
													<form:select path="baNuri" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${bcNuriCodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
													<form:errors path="baNuri" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										
										<c:if test="${boardConfig.bcSupportImage}">
											<div class="form-group row">
												<form:label path="baThumbFile" cssClass="col-sm-2 col-form-label">대표이미지 </form:label>
												<div class="col-sm">
													<div>
														<c:if test="${not empty boardArticleForm.thumb && boardArticleForm.thumb.fileId > 0}">
															<%--<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${boardArticleForm.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" /> --%>
															<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${boardArticleForm.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
														</c:if>
													</div>
													<div>
														<input type="file" name="baThumbFile" class="col-form-label" />
														<form:errors path="baThumbFile" element="div" cssClass="text-danger col-form-label ml-2" />
														<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png 파일만 업로드할 수 있습니다.</span><br>
														<span class="bg-light font-weight-bold text-info">- 이미지는 16:9 비율로 480 x 270 이상 사이즈로 업로드하면 최적화되어 표출됩니다. </span>
													</div>
													<div class="form-inline">
														<div class="col-form-label mr-2"> 대체텍스트 : </div>
														<form:input path="baThumbText" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-4 is-invalid"  />
														<form:errors path="baThumbText" element="div" cssClass="text-danger col-form-label ml-2" />
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
										
										<%-- 첨부된 파일 --%>
										<c:if test="${boardConfig.bcUploadFileNum > 0}">
											<div class="form-group row">
												<label for="baMultipartFiles" id="file_label" class="col-sm-2 col-form-label">첨부파일 </label>
												<div class="col-sm">
													<input type="file" name="baMultipartFiles[0]" id="baMultipartFiles0" class="selectFile col-form-label"/>
													<form:errors path="baMultipartFiles" element="div" cssClass="text-danger col-form-label ml-2" />
													<span class="bg-light font-weight-bold text-info">- 파일첨부는 총${boardConfig.bcUploadFileNum}개까지 가능합니다.(파일당${boardConfig.bcUploadSizeMax}MB제한)</span>
													<div class="mt-2" id="fileList">
														<c:forEach items="${boardArticleForm.baFileInfos}" var="baFileInfo" varStatus="vs">
															<div class="file_add">
																<span><a href="${APP_PATH}/file/download/uu/${baFileInfo.fileUUID}" title="<c:out value="${fileItem.fileOriginalName}" escapeXml="true" />">${baFileInfo.fileOriginalName}(${baFileInfo.fileSizeString})<i class="mdi mdi-download text-warning"></i></a></span>
																<i class="deleteBtn ml-2 fa fa-times text-danger" style="cursor: pointer;" data-fileid="${baFileInfo.fileId}"></i>
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
										
										<c:if test="${boardConfig.bcSupportAnswer and formMode eq 'UPDATE'}">
										<hr>
											<div class="form-group row">
												<form:label path="baAnswer" cssClass="col-sm-2 col-form-label">답글 </form:label>
												<div class="col-sm">
													<form:textarea path="baAnswer" cssClass="form-control col-sm-8" rows="8" placeholder="답글" />
													<form:errors path="baAnswer" element="div" cssClass="text-danger" />
												</div>
											</div>
										</c:if>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/board/article/list.do?bcId=${boardArticleForm.bcId}" class="btn btn-secondary waves-effect m-l-5">목록</a>
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
<c:if test="${!boardConfig.bcSupportAnswer && boardConfig.bcType ne 'qna' && boardConfig.bcType ne 'faq' && !fn:startsWith(boardArticleForm.memId, 'member_') && !fn:startsWith(boardArticleForm.memId, 'guest_')}">
CKEDITOR.replace( 'baContentHtml',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=board&moduleId=${boardArticleForm.bcId}&moduleSub=article&moduleSubId=${boardArticleForm.baId}'
} );
</c:if>
</script>


<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />