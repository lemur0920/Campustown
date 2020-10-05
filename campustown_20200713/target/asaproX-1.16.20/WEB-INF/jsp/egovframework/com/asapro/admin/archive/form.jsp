<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){

	// 메타데이터 구분, 매체 광고유형에 따라 show, hide
	var adType = '${archiveForm.arcMetaCode1}';
	
	if( adType == 'print' ){//인쇄광고
		//$('#bnStartDate, #bnEndDate').val('');
		$('.arcMetaCode2, .arcMedia').show();
	} else {
		//$('#bnStartDate, #bnEndDate').val('');
		$('.arcMetaCode2, .arcMedia').hide();
	}
	
	$('input[name=arcMetaCode1]').on('change', function(){
		if($('input[name=arcMetaCode1]:checked').val() == 'print' ){
			$('.arcMetaCode2, .arcMedia').show();
		}else{
			$('#arcMetaCode2, #arcMedia').val('');
			$('.arcMetaCode2, .arcMedia').hide();
		}
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
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/update.do" /></c:if>
						            <form:form modelAttribute="archiveForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="arcId" />
										<form:hidden path="arcCategory" />
										<form:hidden path="thumb.fileId" />
										
										<c:if test="${not empty archiveCategory.catMetaCode1 }">
											<%--
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
											 --%>
											 
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
										
										<div class="form-group row arcMedia">
											<form:label path="arcMedia" cssClass="col-sm-2 col-form-label">매체 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="arcMedia" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="arcMedia" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcTitle" cssClass="col-sm-2 col-form-label">제목 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="arcTitle" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="arcTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcContent" cssClass="col-sm-2 col-form-label">내용 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="arcContent" cssClass="form-control col-sm-8" rows="3"  />
												<form:errors path="arcContent" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcYear" cssClass="col-sm-2 col-form-label">제작년도 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="arcYear" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<form:errors path="arcYear" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcAdvertiser" cssClass="col-sm-2 col-form-label">광고주 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="arcAdvertiser" id="address" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" />
												<form:errors path="arcAdvertiser" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcProduct" cssClass="col-sm-2 col-form-label">제품명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="arcProduct" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="arcProduct" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcProductDiv" cssClass="col-sm-2 col-form-label">제품분류 </form:label>
											<div class="col-sm">
												<form:input path="arcProductDiv" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="arcProductDiv" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcActor" cssClass="col-sm-2 col-form-label">성우 </form:label>
											<div class="col-sm">
												<form:input path="arcActor" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<form:errors path="arcActor" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="arcPeriod" cssClass="col-sm-2 col-form-label">기간 </form:label>
											<div class="col-sm form-inline">
												<form:input path="arcPeriod" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid "/>
												<form:errors path="arcPeriod" element="div" cssClass="text-danger col-form-label ml-2" />
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
												<form:label path="arcThumbFile" cssClass="col-sm-2 col-form-label">대표이미지 </form:label>
												<div class="col-sm">
													<div>
														<c:if test="${not empty archiveForm.thumb && archiveForm.thumb.fileId > 0}">
															<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${archiveForm.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
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
										
										<%-- 첨부된 파일 --%>
										<c:if test="${archiveCategory.catUploadFileNum > 0}">
											<div class="form-group row">
												<label for="arcMultipartFiles" id="file_label" class="col-sm-2 col-form-label">첨부파일 </label>
												<div class="col-sm">
													<input type="file" name="arcMultipartFiles[0]" id="arcMultipartFiles0" class="selectFile col-form-label"/>
													<form:errors path="arcMultipartFiles" element="div" cssClass="text-danger col-form-label ml-2" />
													<span class="bg-light font-weight-bold text-info">- 파일첨부는 총${archiveCategory.catUploadFileNum}개까지 가능합니다.(파일당${archiveCategory.catUploadSizeMax}MB제한)</span>
													<div class="mt-2" id="fileList">
														<c:forEach items="${archiveForm.arcFileInfos}" var="arcFileInfo" varStatus="vs">
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
												<label class="col-sm-2 col-form-label">광고자료관 추출 </label>
												<div class="col-sm">
													<div class="col-form-label">
														<div class="custom-control custom-checkbox custom-control-inline">
															<form:checkbox path="arcSelected1" class="custom-control-input" value="true"/>
															<label for="arcSelected11" class="custom-control-label"> 광고자료관 추출</label>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/archive/list.do?arcCategory=${archiveForm.arcCategory}" class="btn btn-secondary waves-effect m-l-5">목록</a>
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