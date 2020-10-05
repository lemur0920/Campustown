<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	//메타코드 1 선택에 따라 메타코드 2 목록 변경
	$('#catCustomType').on('change',function(e){
		e.preventDefault();
		var customType = $(this).val();
		
		$('#catUserListTemplate').empty();
		$('#catUserListTemplate').append("<option value=''>선택</option>");
		
		$('#catUserViewTemplate').empty();
		$('#catUserViewTemplate').append("<option value=''>선택</option>");
		
		$('#catAdminListTemplate').empty();
		$('#catAdminListTemplate').append("<option value=''>선택</option>");
		
		$('#catAdminFormTemplate').empty();
		$('#catAdminFormTemplate').append("<option value=''>선택</option>");

		if(customType == null || customType == ''){
			//alert('선택된 타입이 없습니다.');
			return;
		}

		//관리자 템플릿
		$.ajax({
			url : '${ADMIN_PATH}/archive${API_PATH}/admin/template/jsonList.do'
			, type : 'get'
			, data : {
				customType : customType
			}
			, dataType: 'json'
		}).done(function(result){
			
			if(result.length > 0){

				$.each(result, function(index, item){
					//alert(item.codeName);
					$('#catAdminListTemplate').append("<option value='"+ item +"'>"+ item +"</option>");
					$('#catAdminFormTemplate').append("<option value='"+ item +"'>"+ item +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('관리자 템플릿 목록을 불러오지 못하였습니다.[fail]');
		});
		
		//사용자 템플릿
		$.ajax({
			url : '${ADMIN_PATH}/archive${API_PATH}/user/template/jsonList.do'
			, type : 'get'
			, data : {
				customType : customType
			}
			, dataType: 'json'
		}).done(function(result){
			
			if(result.length > 0){

				$.each(result, function(index, item){
					//alert(item.codeName);
					$('#catUserListTemplate').append("<option value='"+ item +"'>"+ item +"</option>");
					$('#catUserViewTemplate').append("<option value='"+ item +"'>"+ item +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('사용자 템플릿 목록을 불러오지 못하였습니다.[fail]');
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
					            	
					            	<!-- info -->
									<div class="p-3 mb-3 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 아카이브 카테고리 등록 </div>
										<div><i class="mdi mdi-information"></i> 메타코드는 GRADE용 코드의 카테고리 선택입니다. </div>
									</div>
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/category/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/archive/category/update.do" /></c:if>
						            <form:form modelAttribute="archiveCategoryForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<%-- <form:hidden path="catId" /> --%>
										
										<div class="form-group row">
											<form:label path="catId" cssClass="col-sm-2 col-form-label">카테고리 아이디 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:if test="${formMode == 'INSERT'}">
													<form:input path="catId" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
												</c:if>
												<c:if test="${formMode == 'UPDATE'}">
													<form:input path="catId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
												</c:if>
												<form:errors path="catId" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catName" cssClass="col-sm-2 col-form-label">카테고리 명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="catName" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="catName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catCustomType" cssClass="col-sm-2 col-form-label">아카이브 타입 <span class="text-danger">*</span></form:label>
											<div class="col-sm ">
												<form:select path="catCustomType" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<c:forEach items="${archiveItemList}" var="archiveItem" varStatus="vs">
														<form:option value="${archiveItem.customType}" label="${archiveItem.label}"/>
													</c:forEach>
												</form:select>
												<form:errors path="catCustomType" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catUserListTemplate" cssClass="col-sm-2 col-form-label">사용자 목록 템플릿 <span class="text-danger">*</span></form:label>
											<div class="col-sm ">
												<form:select path="catUserListTemplate" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<c:forEach items="${arcUserTemplateList}" var="template" varStatus="vs">
														<form:option value="${template}" label="${template}"/>
													</c:forEach>
												</form:select>
												<form:errors path="catUserListTemplate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catUserViewTemplate" cssClass="col-sm-2 col-form-label">사용자 뷰 템플릿 <span class="text-danger">*</span></form:label>
											<div class="col-sm ">
												<form:select path="catUserViewTemplate" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<c:forEach items="${arcUserTemplateList}" var="template" varStatus="vs">
														<form:option value="${template}" label="${template}"/>
													</c:forEach>
												</form:select>
												<form:errors path="catUserViewTemplate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catAdminListTemplate" cssClass="col-sm-2 col-form-label">관리자 목록 템플릿 <span class="text-danger">*</span></form:label>
											<div class="col-sm ">
												<form:select path="catAdminListTemplate" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<c:forEach items="${arcAdminTemplateList}" var="template" varStatus="vs">
														<form:option value="${template}" label="${template}"/>
													</c:forEach>
												</form:select>
												<form:errors path="catAdminListTemplate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catAdminFormTemplate" cssClass="col-sm-2 col-form-label">관리자 폼 템플릿 <span class="text-danger">*</span></form:label>
											<div class="col-sm ">
												<form:select path="catAdminFormTemplate" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<c:forEach items="${arcAdminTemplateList}" var="template" varStatus="vs">
														<form:option value="${template}" label="${template}"/>
													</c:forEach>
												</form:select>
												<form:errors path="catAdminFormTemplate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<hr>
										<div class="form-group row">
											<form:label path="catMetaCode1" cssClass="col-sm-2 col-form-label">메타코드 1 </form:label>
											<div class="col-sm ">
												<form:select path="catMetaCode1" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<form:options items="${codeCatList }" itemLabel="catName" itemValue="catId"/>
												</form:select>
												<form:errors path="catMetaCode1" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catMetaCode2" cssClass="col-sm-2 col-form-label">메타코드 2 </form:label>
											<div class="col-sm ">
												<form:select path="catMetaCode2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<form:options items="${codeCatList }" itemLabel="catName" itemValue="catId"/>
												</form:select>
												<form:errors path="catMetaCode2" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catMetaCode3" cssClass="col-sm-2 col-form-label">메타코드 3 </form:label>
											<div class="col-sm ">
												<form:select path="catMetaCode3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<form:options items="${codeCatList }" itemLabel="catName" itemValue="catId"/>
												</form:select>
												<form:errors path="catMetaCode3" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<!-- ==================================================== 옵션  ==================================================== -->
										<hr>
										<div class="form-group row">
											<form:label path="catSupportSelect1" cssClass="col-sm-2 col-form-label">추출 기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportSelect1" class="custom-control-input" value="true"/>
													<label for="catSupportSelect11" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportSelect1" class="custom-control-input" value="false"/>
													<label for="catSupportSelect12" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="catSupportSelect1" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catSupportFixing" cssClass="col-sm-2 col-form-label">상단 별도분리 출력 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportFixing" class="custom-control-input" value="true"/>
													<label for="catSupportFixing1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportFixing" class="custom-control-input" value="false"/>
													<label for="catSupportFixing2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="catSupportFixing" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catFixingNum" cssClass="col-sm-2 col-form-label">상단 별도분리 출력 갯수 </form:label>
											<div class="col-sm form-inline">
												<form:input path="catFixingNum" maxlength="2" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="form-text text-muted text-info mx-2"> - 상단 설정 갯수만큼 별도 출력하고 나머지 목록 출력할 경우 사용.</span>
												<form:errors path="catFixingNum" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catSupportNuri" cssClass="col-sm-2 col-form-label">공공누리 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportNuri" class="custom-control-input" value="true"/>
													<label for="catSupportNuri1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportNuri" class="custom-control-input" value="false"/>
													<label for="catSupportNuri2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="catSupportNuri" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catSupportRecommend" cssClass="col-sm-2 col-form-label">추천 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportRecommend" class="custom-control-input" value="true"/>
													<label for="catSupportRecommend1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportRecommend" class="custom-control-input" value="false"/>
													<label for="catSupportRecommend2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="catSupportRecommend" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<!-- ==================================================== 이미지 및 기타  ==================================================== -->
										<hr>
										<div class="form-group row">
											<form:label path="catSupportImage" cssClass="col-sm-2 col-form-label">대표이미지 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportImage" class="custom-control-input" value="true"/>
													<label for="catSupportImage1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportImage" class="custom-control-input" value="false"/>
													<label for="catSupportImage2" class="custom-control-label"> 사용안함</label>
												</div>
												<span class="bg-light font-weight-bold text-info">- 대표이미지 첨부기능 여부.</span>
												<form:errors path="catSupportImage" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catSupportThumbnail" cssClass="col-sm-2 col-form-label">썸네일기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportThumbnail" class="custom-control-input" value="true"/>
													<label for="catSupportThumbnail1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catSupportThumbnail" class="custom-control-input" value="false"/>
													<label for="catSupportThumbnail2" class="custom-control-label"> 사용안함</label>
												</div>
												<span class="bg-light font-weight-bold text-info">- 이미지파일 첨부시 자동으로 썸네일을 생성할 것인지 여부.</span>
												<form:errors path="catSupportThumbnail" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catThumbnailCrop" cssClass="col-sm-2 col-form-label">썸네일자르기 비율 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catThumbnailCrop" class="custom-control-input" value="true"/>
													<label for="catThumbnailCrop1" class="custom-control-label"> 지정된 수치사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="catThumbnailCrop" class="custom-control-input" value="false"/>
													<label for="catThumbnailCrop2" class="custom-control-label"> 원본 비율 사용</label>
												</div>
												<span class="bg-light font-weight-bold text-info">- 썸네일 생성시 원본비율을 유지할 것인지여부.</span>
												<form:errors path="catThumbnailCrop" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catThumbnailWidth" cssClass="col-sm-2 col-form-label">썸네일 크기 </form:label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> 가로 : </span>
												<form:input path="catThumbnailWidth" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px , 세로 : </span> 
												<form:input path="catThumbnailHeight" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px</span> 
												<span class="bg-light font-weight-bold text-info">- 설정변경 이후에 업로드되는 파일에만 적용됩니다.</span>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catUploadFileNum" cssClass="col-sm-2 col-form-label">업로드파일개수 </form:label>
											<div class="col-sm form-inline">
												<form:select path="catUploadFileNum" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
													<form:option value="0">0</form:option>
													<form:option value="1">1</form:option>
													<form:option value="2">2</form:option>
													<form:option value="3">3</form:option>
													<form:option value="4">4</form:option>
													<form:option value="5">5</form:option>
													<form:option value="6">6</form:option>
													<form:option value="7">7</form:option>
													<form:option value="8">8</form:option>
													<form:option value="9">9</form:option>
													<form:option value="10">10</form:option>
												</form:select>
												<span class="col-form-label mx-2"> 개</span> 
											</div>
										</div>
										<div class="form-group row">
											<form:label path="catUploadSizeMax" cssClass="col-sm-2 col-form-label">업로드파일용량 제한 </form:label>
											<div class="col-sm form-inline">
												<form:input path="catUploadSizeMax" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" />
												<span class="col-form-label mx-2"> MB</span> 
												<span class="bg-light font-weight-bold text-info">- 서버설정에서 지원하는 최대값까지만 입력 가능합니다.</span>
												<span class="mx-2 bg-light font-weight-bold text-info">- 서버설정 최대값 확인은 서버관리자에게 문의해주세요.</span>
												<form:errors path="catUploadSizeMax" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<!-- ==================================================== 페이지설정  ==================================================== -->
										<hr>
										<div class="form-group row">
											<form:label path="catPageSize" cssClass="col-sm-2 col-form-label">페이지 크기 </form:label>
											<div class="col-sm form-inline">
												<form:input path="catPageSize" maxlength="3" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="form-text text-muted text-info mx-2"> - 카테고리별 페이징 될 크기를 지정할 수 있습니다.</span>
												<form:errors path="catPageSize" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catSortOrder" cssClass="col-sm-2 col-form-label">정렬필드명 </form:label>
											<div class="col-sm form-inline">
												<form:input path="catSortOrder" maxlength="100" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
												<span class="form-text text-muted text-info mx-2"> - DB 테이블의 컬럼명</span>
												<form:errors path="catSortOrder" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="catSortDirection" cssClass="col-sm-2 col-form-label">정령방향 </form:label>
											<div class="col-sm form-inline">
												<form:select path="catSortDirection" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<form:option value="ASC" label="ASC"/>
													<form:option value="DESC" label="DESC"/>
												</form:select>
												<form:errors path="catSortDirection" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										

										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/archive/category/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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