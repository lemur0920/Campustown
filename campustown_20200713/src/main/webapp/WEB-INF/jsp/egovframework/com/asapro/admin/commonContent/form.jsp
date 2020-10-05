<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge" /> -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>
<!-- <script src="${CONTEXT_PATH }/assets/crosseditor/js/namo_scripteditor.js"></script> -->
<script>
jQuery(function($){
	/* //submit 시 에디터의 내용을 content에 담는다
	$('#editval').on('click', function(){
	    document.getElementById("comContContent").value = CrossEditor.GetBodyValue();
	});
 */
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
					            	
					            	<!-- info -->
									<div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 카테고리는 코드관리(moduleCode)에서 등록하세요.</div>
										<div><i class="mdi mdi-information"></i> 공통콘텐츠 카테고리1,	공통콘텐츠 카테고리2,	공통콘텐츠 카테고리3</div>
									</div>	
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
									<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/common/content/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/common/content/update.do" /></c:if>
						            <form:form modelAttribute="commonContentForm" cssClass="" action="${actionUrl}" method="post">
						            	<form:hidden path="comContId" />
										
										<c:if test="${not empty moduleCodeList }">
											<div class="form-group row">
												<form:label path="comContModule" cssClass="col-sm-2 col-form-label">적용프로그램 <span class="text-danger">*</span></form:label>
												<div class="col-sm">
													<form:select path="comContModule" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${moduleCodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
													<form:errors path="comContModule" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										
										
										<div class="form-group row">
											<form:label path="comContTitle" cssClass="col-sm-2 col-form-label">제목 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="comContTitle" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid" />
												<form:errors path="comContTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="comContSubTitle" cssClass="col-sm-2 col-form-label">간단설명 </form:label>
											<div class="col-sm">
												<form:textarea path="comContSubTitle" cssClass="form-control col-sm-8" rows="3" placeholder="" />
												<form:errors path="comContSubTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<c:if test="${not empty cate1CodeList }">
											<div class="form-group row">
												<form:label path="comContCate1" cssClass="col-sm-2 col-form-label">카테고리1</form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${cate1CodeList }" var="cate1Code" varStatus="vs"> 
														<c:set var="chkday" value="" />
														<c:forEach items="${selectComContCate1Array }" var="cate1" >
															<c:if test="${cate1 eq cate1Code.codeId }">
																<c:set var="chkday" value="checked" />
															</c:if>
														</c:forEach>
														<div class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" name="comContCate1" data-dayidx="${vs.index }" id="comContCate1${vs.count }" class="custom-control-input" value="${cate1Code.codeId }" ${chkday } />
															<label for="comContCate1${vs.count }" class="custom-control-label"> ${cate1Code.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="comContCate1" element="div" cssClass="text-danger" />
												</div>
											</div>
										</c:if>
										<%--
										<c:if test="${not empty cate1CodeList }">
											<div class="form-group row">
												<form:label path="comContCate1" cssClass="col-sm-2 col-form-label">카테고리1 </form:label>
												<div class="col-sm">
													<form:select path="comContCate1" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${cate1CodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
													<form:errors path="comContCate1" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										 --%>
										
										<c:if test="${not empty cate2CodeList }">
											<div class="form-group row">
												<form:label path="comContCate2" cssClass="col-sm-2 col-form-label">카테고리2</form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${cate2CodeList }" var="cate2Code" varStatus="vs"> 
														<c:set var="chkday" value="" />
														<c:forEach items="${selectComContCate2Array }" var="cate2" >
															<c:if test="${cate2 eq cate2Code.codeId }">
																<c:set var="chkday" value="checked" />
															</c:if>
														</c:forEach>
														<div class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" name="comContCate2" data-dayidx="${vs.index }" id="comContCate2${vs.count }" class="custom-control-input" value="${cate2Code.codeId }" ${chkday } />
															<label for="comContCate2${vs.count }" class="custom-control-label"> ${cate2Code.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="comContCate2" element="div" cssClass="text-danger" />
												</div>
											</div>
										</c:if>
										<%--
										<c:if test="${not empty cate2CodeList }">
											<div class="form-group row">
												<form:label path="comContCate2" cssClass="col-sm-2 col-form-label">카테고리2 </form:label>
												<div class="col-sm">
													<form:select path="comContCate2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${cate2CodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
													<form:errors path="comContCate2" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										 --%>
										
										<c:if test="${not empty cate3CodeList }">
											<div class="form-group row">
												<form:label path="comContCate3" cssClass="col-sm-2 col-form-label">카테고리3</form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${cate3CodeList }" var="cate3Code" varStatus="vs"> 
														<c:set var="chkday" value="" />
														<c:forEach items="${selectComContCate3Array }" var="cate3" >
															<c:if test="${cate3 eq cate3Code.codeId }">
																<c:set var="chkday" value="checked" />
															</c:if>
														</c:forEach>
														<div class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" name="comContCate3" data-dayidx="${vs.index }" id="comContCate3${vs.count }" class="custom-control-input" value="${cate3Code.codeId }" ${chkday } />
															<label for="comContCate3${vs.count }" class="custom-control-label"> ${cate3Code.codeName }</label>
														</div>
													</c:forEach>
													<form:errors path="comContCate3" element="div" cssClass="text-danger" />
												</div>
											</div>
										</c:if>
										<%--
										<c:if test="${not empty cate3CodeList }">
											<div class="form-group row">
												<form:label path="comContCate3" cssClass="col-sm-2 col-form-label">카테고리3 </form:label>
												<div class="col-sm">
													<form:select path="comContCate3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
														<form:option value="">선택</form:option>
														<form:options items="${cate3CodeList}" itemLabel="codeName" itemValue="codeId" />
													</form:select>
													<form:errors path="comContCate3" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</div>
										</c:if>
										 --%>
										
										<div class="form-group row">
											<form:label path="comContStatus" cssClass="col-sm-2 col-form-label">게시여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="comContStatus" class="custom-control-input" value="public"/>
													<label for="comContStatus1" class="custom-control-label"> 게시</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="comContStatus" class="custom-control-input" value="draft"/>
													<label for="comContStatus2" class="custom-control-label"> 게시안함</label>
												</div>
											</div>
											<form:errors path="comContStatus" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										
										<div class="form-group row">
											<form:label path="comContContent" cssClass="col-sm-2 col-form-label">콘텐츠 <span class="text-danger">*</span></form:label>
											&nbsp;&nbsp;&nbsp;
											<div class="col-sm">
											
										
												<form:textarea style="display: none"  path="comContContent" id="comContContent" cssClass="form-control col-sm-8" rows="10" placeholder="" />
												<form:errors path="comContContent" element="div" cssClass="text-danger col-form-label ml-2" /> 
												<!-- <script>
													var CrossEditor = new NamoSE('namoeditor1');
													CrossEditor.params.Width = '100%'; 
													CrossEditor.params.Height = 600;
													CrossEditor.EditorStart();
													
													// Body안의 내용만 에디터 삽입
													var content = document.getElementById("comContContent").value;
													function OnInitCompleted(e){
														   e.editorTarget.SetBodyValue(content);
													} 
												</script>
											 -->
											</div>
										</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light" id="editval">저장</button>
											<a href="${ADMIN_PATH}/common/content/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
										<!-- 	<button onclick="fnEditval();return false;" class="btn btn-secondary waves-effect m-l-5">에디트내용확인</button> -->
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

CKEDITOR.replace( 'comContContent',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=commonContent'
} );

</script>


<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />