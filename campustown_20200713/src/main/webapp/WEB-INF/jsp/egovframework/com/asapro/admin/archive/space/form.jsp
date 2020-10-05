<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>

<script>
jQuery(function($){
	
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
	
	
	//메타코드 1 선택에 따라 메타코드 2 목록 변경
	$('[name=arcMetaCode1]').on('change',function(e){
		e.preventDefault();
		var catId = $(this).val();
		
		if(catId == null || catId == ''){
			//alert('선택된 코드가 없습니다.');
			$('#arcMetaCode2').empty();
			$('#arcMetaCode2').append("<option value=''>선택</option>");
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
			$('#arcMetaCode2').empty();
			$('#arcMetaCode2').append("<option value=''>선택</option>");
			if(result.length > 0){

				$.each(result, function(index, item){
					//alert(item.codeName);
					$('#arcMetaCode2').append("<option value='"+ item.codeId +"'>"+ item.codeName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
	
	// 정규식
	// 숫자랑 -
	$("#spaTel").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9-~]/g,""));
	});
	
	// 이메일 정규식(임직원 정보)
	$("#spaEmail").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^a-zA-z0-9-_\.@]/g,""))
    	       .css("text-transform", "lowercase");
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
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/space/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/space/update.do" /></c:if>
						            <form:form modelAttribute="spaceForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="arcId" />
										<form:hidden path="arcCategory" />
										<form:hidden path="thumb.fileId" />
										
										<c:if test="${not empty archiveCategory.catMetaCode1 }">
											
											<div class="form-group row">
												<form:label path="arcMetaCode1" cssClass="col-sm-2 col-form-label">${arcMetaCode1Info.catName } <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:select path="arcMetaCode1" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
														<option value="" label="선택">
														<form:options items="${arcMetaCode1List }" itemLabel="codeName" itemValue="codeId"/>
													</form:select>
													<form:errors path="arcMetaCode1" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>	
											</div>
											
											<%--
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
											 --%>
										</c:if>
										
										
										<c:if test="${not empty archiveCategory.catMetaCode2 }">
											<%--
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
											--%>
										 
											<div class="form-group row arcMetaCode2">
												<form:label path="arcMetaCode2" cssClass="col-sm-2 col-form-label">지역 <span class="text-danger">*</span></form:label>
												<div class="col-sm form-inline">
													<form:select path="arcMetaCode2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
														<option value="" label="선택">
										 				<c:if test="${not empty arcMetaCode2List }">
															<form:options items="${arcMetaCode2List }" itemLabel="codeName" itemValue="codeId"/>
														</c:if>
													</form:select>
													<form:errors path="arcMetaCode2" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>	
											</div>
										
										</c:if>
										
										<div class="form-group row arcUnivId">
											<form:label path="arcUnivId" cssClass="col-sm-2 col-form-label">캠퍼스타운 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:select path="arcUnivId" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
									 				<c:if test="${not empty departmentList }">
														<form:options items="${departmentList }" itemLabel="depName" itemValue="depId"/>
													</c:if>
												</form:select>
												<form:errors path="arcUnivId" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>	
										</div>
										
										<c:if test="${not empty archiveCategory.catMetaCode3 }">
											<div class="form-group row">
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
											<form:label path="arcTitle" cssClass="col-sm-2 col-form-label">입주공간명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="arcTitle" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" max-length="150" />
												<form:errors path="arcTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="spaEmail" cssClass="col-sm-2 col-form-label">이메일</form:label>
											<div class="col-sm">
												<form:input path="spaEmail" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid"  max-length="150"/>
												<form:errors path="spaEmail" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="spaTel" cssClass="col-sm-2 col-form-label">문의전화</form:label>
											<div class="col-sm">
												<form:input path="spaTel" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  max-length="20" />
												<form:errors path="spaTel" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="spaLocation" cssClass="col-sm-2 col-form-label">위치</form:label>
											<div class="col-sm">
												<form:input path="spaLocation" id="address" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" max-length="150" />
												<form:errors path="spaLocation" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<!-- Daum 우편번호 서비스 스크립트  -->
										<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/zipSearch.jsp" charEncoding="UTF-8" />
										
										<c:if test="${archiveCategory.catSupportImage }">
											<div class="form-group row">
												<form:label path="arcThumbFile" cssClass="col-sm-2 col-form-label">대표이미지 </form:label>
												<div class="col-sm">
													<div>
														<c:if test="${not empty spaceForm.thumb && spaceForm.thumb.fileId > 0}">
															<%--<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${boardArticleForm.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" /> --%>
															<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${spaceForm.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
														</c:if>
													</div>
													<div>
														<input type="file" name="arcThumbFile" class="col-form-label" />
														<form:errors path="arcThumbFile" element="div" cssClass="text-danger col-form-label ml-2" />
														<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png 파일만 업로드할 수 있습니다.</span>
													</div>
													<div class="form-inline">
														<div class="col-form-label mr-2"> 대체텍스트 : </div>
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
										
										<%-- 
										<div class="form-group row">
											<form:label path="spaUseHours" cssClass="col-sm-2 col-form-label">이용시간 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="spaUseHours" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="spaUseHours" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="spaBusinessHours" cssClass="col-sm-2 col-form-label">업무시간 </form:label>
											<div class="col-sm">
												<form:input path="spaBusinessHours" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid" />
												<form:errors path="spaBusinessHours" element="div" cssClass="text-danger" />
											</div>
										</div>
										

										<div class="form-group row">
											<form:label path="spaAgency" cssClass="col-sm-2 col-form-label">운영기관 </form:label>
											<div class="col-sm">
												<form:input path="spaAgency" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="spaAgency" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="spaSiteUrl" cssClass="col-sm-2 col-form-label">홈페이지 url </form:label>
											<div class="col-sm">
												<form:input path="spaSiteUrl" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid"  />
												<form:errors path="spaSiteUrl" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										 --%>
										
										<div class="form-group row">
											<form:label path="spaComment" cssClass="col-sm-2 col-form-label">코멘트</form:label>
											<div class="col-sm">
												<form:textarea path="spaComment" cssClass="form-control col-sm-8" rows="3" max-length="150" />
												<form:errors path="spaComment" element="div" cssClass="text-danger" />
											</div>
										</div>
										 
										<div class="form-group row">
											<form:label path="arcContent" cssClass="col-sm-2 col-form-label">입주공간 정보</form:label>
											<div class="col-sm">
												<form:textarea path="arcContent" cssClass="form-control col-sm-8" rows="3"  />
												<form:errors path="arcContent" element="div" cssClass="text-danger" />
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
											<form:label path="arcUse" cssClass="col-sm-2 col-form-label">게시여부</form:label>
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
										
										<%-- 첨부된 파일 --%>
										<c:if test="${archiveCategory.catUploadFileNum > 0}">
											<div class="form-group row uploadFile">
												<label for="arcMultipartFiles" id="file_label" class="col-sm-2 col-form-label">첨부파일 </label>
												<div class="col-sm">
													<input type="file" name="arcMultipartFiles[0]" id="arcMultipartFiles0" class="selectFile col-form-label"/>
													<form:errors path="arcMultipartFiles" element="div" cssClass="text-danger col-form-label ml-2" />
													<span class="bg-light font-weight-bold text-info">- 파일첨부는 총${archiveCategory.catUploadFileNum}개까지 가능합니다.(파일당${archiveCategory.catUploadSizeMax}MB제한)</span>
													<div class="mt-2" id="fileList">
														<c:forEach items="${spaceForm.arcFileInfos}" var="arcFileInfo" varStatus="vs">
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
												<label class="col-sm-2 col-form-label">일자리카페 추출 </label>
												<div class="col-sm">
													<div class="col-form-label">
														<div class="custom-control custom-checkbox custom-control-inline">
															<form:checkbox path="arcSelected1" class="custom-control-input" value="true"/>
															<label for="arcSelected11" class="custom-control-label"> 일자리카페 추출</label>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/archive/space/list.do?arcCategory=${spaceForm.arcCategory}" class="btn btn-secondary waves-effect m-l-5">목록</a>
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
CKEDITOR.replace( 'arcContent',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=archive&moduleId=${archiveCategory.catCustomType}&moduleSub=${archiveCategory.catId}&moduleSubId=${spaceForm.arcId}'
} );
</script>	

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />