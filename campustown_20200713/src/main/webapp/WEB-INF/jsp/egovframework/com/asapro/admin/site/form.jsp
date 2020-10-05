<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
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
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/site/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/site/update.do" /></c:if>
						            <form:form modelAttribute="siteForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										
										<div class="form-group row">
											<form:label path="siteId" cssClass="col-sm-2 col-form-label">사이트 아이디 <span class="text-danger">*</span></form:label>
											<div class="col-sm">	
												<c:if test="${formMode == 'INSERT'}">
													<form:input path="siteId" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="10" id="thresholdconfig" placeholder="- 영문, 숫자  3자 ~ 10자 이하사용" />
												</c:if>
												<c:if test="${formMode == 'UPDATE'}">
													<form:input path="siteId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="10" id="thresholdconfig" />
												</c:if>
												<form:errors path="siteId" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="siteName" cssClass="col-sm-2 col-form-label">사이트명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="siteName" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="siteName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="siteDomain" cssClass="col-sm-2 col-form-label">사이트 도메인 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="siteDomain" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<div class="mt-2 bg-light font-weight-bold text-info">- 메인사이트에서만 필수항목 입니다.</div>
												<div class="mt-2 bg-light font-weight-bold text-info">- 도메인과 포트번호만 입력해 주세요. 예) aaa.test.com, aaa.test.com:8080</div>
												<form:errors path="siteDomain" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="siteDescription" cssClass="col-sm-2 col-form-label">사이트 설명 </form:label>
											<div class="col-sm">
												<form:textarea path="siteDescription" cssClass="form-control col-sm-8" rows="3" placeholder="설명" />
												<form:errors path="siteDescription" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="siteType" cssClass="col-sm-2 col-form-label">사이트 유형 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="siteType" class="custom-control-input" value="domain"/>
													<label for="siteType1" class="custom-control-label"> 도메인 구분</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="siteType" class="custom-control-input" value="dir"/>
													<label for="siteType2" class="custom-control-label"> 디렉토리 구분</label>
												</div>
												<div class="mt-2 bg-light font-weight-bold text-info">- 도메인 구분은 별도 도메인 또는 서브도메인을 소유하여 새로운 사이트를 생성할 경우 사용</div>
												<div class="mt-2 bg-light font-weight-bold text-info">- 디렉토리 구분은 동일한 도메인을 사용하여 사이트 아이디를 구분자로 url을 생성하여 서브사이트를 생성할 경우 사용</div>
												<form:errors path="siteType" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="siteLogo" cssClass="col-sm-2 col-form-label">로고 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<div>
													<c:if test="${not empty siteForm.siteLogo && siteForm.siteLogo.fileId > 0}">
														<%--<img class="img-thumbnail" style="max-width: 300px; max-height: 200px;" src="${siteForm.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" /> --%>
														<img class="img-thumbnail" style="max-width: 300px; max-height: 200px;" src="${siteForm.siteLogo.fileServletUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
													</c:if>
												</div>
												<div>
													<input type="file" name="siteLogoImage" class="col-form-label" />
													<ul>
														<li>
															<span class="bg-light font-weight-bold text-info"> jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
														</li>
													</ul>	
													<form:errors path="siteLogoImage" element="div" cssClass="text-danger col-form-label ml-2" />
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
											<form:label path="siteTheme" cssClass="col-sm-2 col-form-label">사이트 테마 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="siteTheme" items="${themeList}" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" />
												<div class="mt-2 bg-light font-weight-bold text-info">- 테마 관리,등록 기능이 개발될때까지 design, theme 를 직접 폴더 생성하여 사용</div>
												<form:errors path="siteTheme" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="siteLocale" cssClass="col-sm-2 col-form-label">사이트 언어 </form:label>
											<div class="col-sm form-inline">
												<form:select path="siteLocale" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
													<form:option value="ko_KR">한국</form:option>
													<form:option value="en_US">미국</form:option>
													<form:option value="ja_JP">일본</form:option>
													<form:option value="zh_CN">중국</form:option>
												</form:select>
											</div>
										</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/site/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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