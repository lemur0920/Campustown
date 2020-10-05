<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
	
	//유형별 옵션 show, hide
	var bnType = '${bannerForm.bnType}';
	if( bnType == 'popupzone' || bnType == 'layer' ){
		//$('#bnStartDate, #bnEndDate').val('');
		$('.date-prop').show();
	} else {
		//$('#bnStartDate, #bnEndDate').val('');
		$('.date-prop').hide();
	}
	
	if( bnType == 'layer' ){
		//$('#bnWidth, #bnHeight, #bnTop, #bnLeft').val('');
		$('.size-prop, .position-prop').show();
	} else {
		//$('#bnWidth, #bnHeight, #bnTop, #bnLeft').val('');
		$('.size-prop, .position-prop').hide();
	}
	
	//달력
	$('.datepicker-ui').datepicker();
	
	
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
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/banner/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/banner/update.do" /></c:if>
						            <form:form modelAttribute="bannerForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="bnId" />
										<form:hidden path="bnExt" />
										
										<div class="form-group row">
											<form:label path="bnType" cssClass="col-sm-2 col-form-label">유형 <span class="text-danger">*</span></form:label>
											<form:hidden path="bnType" />
											<div class="col-sm col-form-label">
												<span class="font-weight-bold ">
												<c:choose>
													<c:when test="${bannerForm.bnType eq 'mainVisual'}">메인비쥬얼</c:when>
													<c:when test="${bannerForm.bnType eq 'popupzone'}">팝업존</c:when>
													<c:when test="${bannerForm.bnType eq 'layer'}">레이어팝업</c:when>
													<c:when test="${bannerForm.bnType eq 'banner'}">배너</c:when>
													<c:when test="${bannerForm.bnType eq 'static'}">창업팀홍보배너</c:when>
													<c:otherwise>${bannerForm.bnType}</c:otherwise>
												</c:choose>
												</span>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="bnImage" cssClass="col-sm-2 col-form-label">이미지 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<div>
													<c:if test="${not empty bannerForm.thumb && bannerForm.thumb.fileId > 0}">
														<%--<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${bannerForm.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" /> --%>
														<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${bannerForm.thumb.fileServletUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
													</c:if>
												</div>
												<div>
													<input type="file" name="bnImage" class="col-form-label" />
													<form:errors path="bnImage" element="div" cssClass="text-danger col-form-label ml-2" />
													<ul>
														<li>
															<span class="bg-light font-weight-bold text-info"> jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
														</li>
														
														<c:choose>
															<c:when test="${bannerForm.bnType eq 'mainVisual'}"><li><span class="bg-light font-weight-bold text-info"> 권장 사이즈 :  가로 XXX px, 세로 XXX px</span></li></c:when>
															<c:when test="${bannerForm.bnType eq 'popupzone'}"><li><span class="bg-light font-weight-bold text-info"> 권장 사이즈 : 가로 XXX px, 세로 XXX px</span></li></c:when>
															<c:when test="${bannerForm.bnType eq 'banner'}"><li><span class="bg-light font-weight-bold text-info"> 권장 사이즈 : 가로 XXX px, 세로 XX px</span></li></c:when>
															<c:when test="${bannerForm.bnType eq 'static'}"><li><span class="bg-light font-weight-bold text-info"> 권장 사이즈 : 가로 XXX px, 세로 XX px</span></li></c:when>
														</c:choose>
														
													</ul>	
												</div>
												<div>
													<c:if test="${not empty thumbFileInfoUploadResult}">
														<div class="text-danger">
															<div>다음의 파일을 업로드 하지 못하였습니다.</div>
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
										
										<div class="form-group row">
											<form:label path="bnName" cssClass="col-sm-2 col-form-label">제목 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="bnName" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="bnName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="bnDescription" cssClass="col-sm-2 col-form-label">설명 </form:label>
											<div class="col-sm">
												<form:textarea path="bnDescription" cssClass="form-control col-sm-8" rows="3" placeholder="설명" />
												<form:errors path="bnDescription" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="bnLink" cssClass="col-sm-2 col-form-label">링크 </form:label>
											<div class="col-sm">
												<form:input path="bnLink" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="bnLink" element="div" cssClass="text-danger col-form-label ml-2" />
												<span class="mt-2 bg-light font-weight-bold text-info">- http(s)://를 포함한 전체 url을 입력해 주세요.(링크가 없을 경우 공백유지)</span>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="bnNewWin" cssClass="col-sm-2 col-form-label">링크대상(taget) </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bnNewWin" class="custom-control-input" value="true"/>
													<label for="bnNewWin1" class="custom-control-label"> 새창</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bnNewWin" class="custom-control-input" value="false"/>
													<label for="bnNewWin2" class="custom-control-label"> 현재창</label>
												</div>
												<form:errors path="bnNewWin" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row date-prop">
											<form:label path="bnStartDate" cssClass="col-sm-2 col-form-label">게시 시작일 </form:label>
											<div class="col-sm form-inline">
												<form:input path="bnStartDate" cssClass="form-control col-sm-1 datepicker-ui" />
												<form:errors path="bnStartDate" element="div" cssClass="text-danger col-form-label ml-2" />
												<span class="ml-3 bg-light font-weight-bold text-info">- 팝업존, 레이어팝업에만 적용됩니다.</span>
											</div>
										</div>
										
										<div class="form-group row date-prop">
											<form:label path="bnEndDate" cssClass="col-sm-2 col-form-label">게시 종료일 </form:label>
											<div class="col-sm form-inline">
												<form:input path="bnEndDate" cssClass="form-control col-sm-1 datepicker-ui" />
												<form:errors path="bnEndDate" element="div" cssClass="text-danger col-form-label ml-2" />
												<span class="ml-3 bg-light font-weight-bold text-info">- 팝업존, 레이어팝업에만 적용됩니다.</span>
											</div>
										</div>
										
										<div class="form-group row size-prop">
											<form:label path="bnWidth" cssClass="col-sm-2 col-form-label">크기 </form:label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> 가로(width) : </span>
												<form:input path="bnWidth" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px , 세로(height) : </span> 
												<form:input path="bnHeight" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px</span> 
												<div class="form-text bg-light font-weight-bold text-info">
													<div> - 레이어팝업에만 적용됩니다.</div>
													<div> - 미 입력시 원본크기로 적용됩니다.</div>
													<div> - 하나만 입력 시 나머지값은 비율에 맞춰 자동조정 됩니다.</div>
												</div>
											</div>
										</div>
										
										<div class="form-group row position-prop">
											<form:label path="bnTop" cssClass="col-sm-2 col-form-label">위치 </form:label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> Top : </span>
												<form:input path="bnTop" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px ; Left : </span> 
												<form:input path="bnLeft" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px</span> 
												<span class="ml-3 bg-light font-weight-bold text-info">- 레이어팝업의 모니터 화면 좌측과 상단에서부터의 거리를 나타냅니다.</span>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="bnUse" cssClass="col-sm-2 col-form-label">게시여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bnUse" class="custom-control-input" value="true"/>
													<label for="bnUse1" class="custom-control-label"> 게시</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bnUse" class="custom-control-input" value="false"/>
													<label for="bnUse2" class="custom-control-label"> 게시안함</label>
												</div>
												<form:errors path="bnUse" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<c:if test="${empty bannerForm.bnType}">
												<a href="${ADMIN_PATH}/banner/list.do?bnType=banner" class="btn btn-secondary waves-effect m-l-5">목록</a>
											</c:if>
											<c:if test="${not empty bannerForm.bnType}">
												<a href="${ADMIN_PATH}/banner/list.do?bnType=${bannerForm.bnType}" class="btn btn-secondary waves-effect m-l-5">목록</a>
											</c:if>
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