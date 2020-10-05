<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<!-- Magnific popup -->
        <link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
        <script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
        <script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>
<script>
jQuery(function($){
	
	//기관선택시 부서목록 가져오기
	/*
	$('#bcOrganization').on('change',function(e){
		e.preventDefault();
		var orgId = $(this).val();

		if(orgId == null || orgId == ''){
			$('#bcDepartment').empty();
			$('#bcDepartment').append("<option value=''>선택</option>");
			return;
		}
	
		$.ajax({
			url : '${ADMIN_PATH}/organization/department${API_PATH}/jsonList.do'
			, type : 'get'
			, data : {
				orgId : orgId
			}
			, dataType: 'json'
		}).done(function(result){
			if(result.length > 0){
				$('#bcDepartment').empty();
				$('#bcDepartment').append("<option value=''>선택</option>");
				$.each(result, function(index, item){
					//alert(item.depName);
					$('#bcDepartment').append("<option value='"+ item.depId +"' >"+ item.depName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
	*/
	
	
	//기관선택시 부서목록 가져오기
	$('#bcOrganization').on('change',function(e){
		e.preventDefault();
		var orgId = $(this).val();

		if(orgId == null || orgId == ''){
			$('#department').empty();
			
			var html = "<h5 class='modal-title mt-0'>부서목록</h5>";
			html += "<hr>";
			$('#department').append(html);
			return;
		}
	
		$.ajax({
			url : '${ADMIN_PATH}/organization/department${API_PATH}/jsonList.do'
			, type : 'get'
			, data : {
				orgId : orgId
			}
			, dataType: 'json'
		}).done(function(result){
			if(result.length > 0){
				$('#department').empty();
				
				var html = "<h5 class='modal-title mt-0'>부서목록</h5>";
				html += "<hr>";
				$.each(result, function(index, item){
					//alert(item.depName);
					html += "<div class='custom-control custom-checkbox custom-control-inline ml-2'>";
					html += "<input type='checkbox' name='bcDepartment' id='bcDepartment" + (index+1) + "' class='custom-control-input' value='" + item.depId + "'/>";
					html += "<label for='bcDepartment" + (index+1) + "' class='custom-control-label'> " + item.depName + "</label>";
					html += "</div>";
				});
				$('#department').append(html);
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
	
	// 기관,부서,팀 선택 트리
	$hiddenInput = $('#bstree-data');
	$('#mytree').bstree({
		dataSource: $hiddenInput,
		initValues: $hiddenInput.data('ancestors'),
		dataSeparator: ',', 
		onDataPush: function (values) {
			var def = '<strong class="pull-left">Values:&nbsp;</strong>';
			for (var i in values) {
				def += '<span class="pull-left">' + values[i] + '&nbsp;</span>';
			}
			$('#status').html(def);
		},
		updateNodeTitle: function (node, title) {
			return '[' + node.attr('data-id') + '] ' + title + ' (' + node.attr('data-level') + ')';
		}
	});
	
	
	//필드추가
	$('.addFieldBtn').on('click', function(){
		var limit = 10;
		var fieldCnt = $('.customField').length;
		if(fieldCnt >= limit){
			alert('더이상 사용자 필드를 추가할 수 없습니다.');
			return;
		}
		
		var html = '<div class="form-group form-inline">';
			html += '<span class="col-form-label mr-2"> 필드[' + (fieldCnt+1) + '] 명 : </span>';
			html += '<input name="bcCustomField' + (fieldCnt+1) + '" class="form-control col-sm-3 mr-4 customField" />';
			html += '<form:errors path="bcCustomField' + (fieldCnt+1) + '" element="div" cssClass="text-danger" />';
			html += '<div class="custom-control custom-radio custom-control-inline">';
			html += '<input type="radio" name="bcCustomFieldUseArray[' + fieldCnt + ']" id="bcCustomFieldUse1_' + fieldCnt + '" class="custom-control-input" value="true" checked="checked" />';
			html += '<label for="bcCustomFieldUse1_' + fieldCnt + '" class="custom-control-label"> 사용</label>';
			html += '</div>';
			html += '<div class="custom-control custom-radio custom-control-inline">';
			html += '<input type="radio" name="bcCustomFieldUseArray[' + fieldCnt + ']" id="bcCustomFieldUse2_' + fieldCnt + '" class="custom-control-input" value="false" />';
			html += '<label for="bcCustomFieldUse2_' + fieldCnt + '" class="custom-control-label"> 사용안함</label>';
			html += '</div>';
			html += '</div>';
		$('#customfield-list').append(html);
		
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
					        	<div class="card-header">
									<h4 class="mt-0 header-title">게시판
										<c:if test="${formMode == 'INSERT'}"> 추가</c:if>
										<c:if test="${formMode == 'UPDATE'}"> 수정</c:if>
									</h4>
								</div>
								
					            <div class="card-body">
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/board/config/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/board/config/update.do" /></c:if>
						            <form:form modelAttribute="boardConfigForm" cssClass="" action="${actionUrl}" method="post">
						            
										<!-- ==================================================== 기본설정  ==================================================== -->
										<div class="form-group row">
											<form:label path="bcId" cssClass="col-sm-2 col-form-label">게시판아이디 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:if test="${formMode == 'INSERT'}">
													<form:input path="bcId" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
												</c:if>
												<c:if test="${formMode == 'UPDATE'}">
													<form:input path="bcId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
												</c:if>
												<form:errors path="bcId" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcName" cssClass="col-sm-2 col-form-label">게시판이름 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="bcName" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="게시판이름" />
												<form:errors path="bcName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcType" cssClass="col-sm-2 col-form-label">게시판유형 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="bcType" items="${bcTypeList}" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" />
												<form:errors path="bcType" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcListFile" cssClass="col-sm-2 col-form-label">게시판목록 템플릿 </form:label>
											<div class="col-sm">
												<form:input path="bcListFile" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" />
												<form:errors path="bcListFile" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcViewFile" cssClass="col-sm-2 col-form-label">게시판조회 템플릿 </form:label>
											<div class="col-sm">
												<form:input path="bcViewFile" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" />
												<form:errors path="bcViewFile" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcFormFile" cssClass="col-sm-2 col-form-label">게시판 글쓰기/수정 템플릿 </form:label>
											<div class="col-sm">
												<form:input path="bcFormFile" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" />
												<form:errors path="bcFormFile" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<label class="col-sm-2 col-form-label"></label>
											<div class="col-sm">
												<div class="p-3 bg-light font-weight-bold text-info">
													<div><i class="mdi mdi-information"></i> 특정 jsp파일을 기본 뷰파일 대신 사용할 필요가 있을 경우 파일 경로를 입력합니다.(<strong>/WEB-INF/jsp/egovframework/com/asapro/theme/${theme}/board/</strong> 디렉토리 아래만 지정가능)</div>
													<div><i class="mdi mdi-information"></i> 예) template.jsp 또는 template 또는 custom/template.jsp 또는 custom/template</div>
												</div>
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcUse" cssClass="col-sm-2 col-form-label">사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcUse" class="custom-control-input" value="true"/>
													<label for="bcUse1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcUse" class="custom-control-input" value="false"/>
													<label for="bcUse2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcUse" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<!-- ========================================================  메타데이터  ============================================= -->
										<hr>
										<div class="form-group row">
											<form:label path="bcCategory1" cssClass="col-sm-2 col-form-label">게시판분류 </form:label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> 분류명1 : </span> 
												<form:input path="bcCategory1Name" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
												<span class="col-form-label mx-2"> 코드1 : </span> 
												<form:select path="bcCategory1" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<form:option value="">사용안함</form:option>
													<form:options items="${codeCategoryList}" itemLabel="catName" itemValue="catId" />
												</form:select>
											</div>
										</div>
										<%-- //창업 제품 소개 및 장터 게시판 예외처리 --%>
										<c:if test="${boardConfigForm.bcId ne 'product' }">
										
										<div class="form-group row">
											<label class="col-sm-2 col-form-label"></label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> 분류명2 : </span> <form:input path="bcCategory2Name" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
												<span class="col-form-label mx-2"> 코드2 : </span> 
												<form:select path="bcCategory2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<form:option value="">사용안함</form:option>
													<form:options items="${codeCategoryList}" itemLabel="catName" itemValue="catId" />
												</form:select>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-sm-2 col-form-label"></label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> 분류명3 : </span> <form:input path="bcCategory3Name" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
												<span class="col-form-label mx-2"> 코드3 : </span> 
												<form:select path="bcCategory3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<form:option value="">사용안함</form:option>
													<form:options items="${codeCategoryList}" itemLabel="catName" itemValue="catId" />
												</form:select>
											</div>
										</div>
										</c:if>
										
										<div class="form-group row">
											<form:label path="bcStatusCode" cssClass="col-sm-2 col-form-label">진행상태코드 </form:label>
											<div class="col-sm">
												<form:select path="bcStatusCode" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<form:option value="">사용안함</form:option>
													<form:options items="${codeCategoryList}" itemLabel="catName" itemValue="catId" />
												</form:select>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-sm-2 col-form-label">추가정보 </label>
											<div class="col-sm">
												<div class="form-group">
													<button type="button" class="btn btn-sm btn-primary waves-effect waves-light addFieldBtn">
														<i class="ion-plus"> 필드추가</i> 
													</button>
													<span class="bg-light font-weight-bold text-info ml-3">- 추가 저장된 필드는 데이터 보존을 위해 삭제 할 수 없습니다. 필드명 변경시 기저장된 데이터에 유의하세요.</span>
												</div>
												
												<div id="customfield-list">
													<form:hidden path="bcCustomFieldUse"/>
													<c:if test="${not empty boardConfigForm.bcCustomFieldUseToArray}">
														<c:set var="displayCnt" value="${fn:length(boardConfigForm.bcCustomFieldUseToArray)}"/>
														
														<c:forEach begin="1" end="${displayCnt }" var="i" step="1">
															<div class="form-group form-inline">
																<span class="col-form-label mr-2"> 필드[${i }] 명 : </span> 
																<form:input path="bcCustomField${i }" cssClass="form-control col-sm-3 mr-4 customField" cssErrorClass="form-control col-sm-3 is-invalid customField" />
																<form:errors path="bcCustomField${i }" element="div" cssClass="text-danger" />
																
																<div class="custom-control custom-radio custom-control-inline">
																	<input type="radio" name="bcCustomFieldUseArray[${i-1 }]" id="bcCustomFieldUse1_${i-1 }" class="custom-control-input" value="true" <c:if test="${boardConfigForm.bcCustomFieldUseToArray[i-1] eq 'true' }">checked="checked"</c:if> />
																	<label for="bcCustomFieldUse1_${i-1 }" class="custom-control-label"> 사용</label>
																</div>
																<div class="custom-control custom-radio custom-control-inline">
																	<input type="radio" name="bcCustomFieldUseArray[${i-1 }]" id="bcCustomFieldUse2_${i-1 }" class="custom-control-input" value="false" <c:if test="${boardConfigForm.bcCustomFieldUseToArray[i-1] eq 'false' }">checked="checked"</c:if> />
																	<label for="bcCustomFieldUse2_${i-1 }" class="custom-control-label"> 사용안함</label>
																</div>
												
															</div>
														</c:forEach>
													</c:if>

												</div>
											</div>
										</div>
										
										<!-- ======================================================  게시판 권한  ================================================== -->
										<hr>
										<div class="form-group row">
											<form:label path="bcOrganization" cssClass="col-sm-2 col-form-label">게시판 관리부서 </form:label>
											<div class=" form-inline">
												<%--
												<span class="col-form-label mr-2"> 기관 : </span>
												<form:select path="bcOrganization" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<option value="">선택</option>
													<form:options items="${organizationList}" itemLabel="orgName" itemValue="orgId" />
												</form:select>
												
												<span class="col-form-label mx-2"> 부서 : </span> 
												 --%>
												<div class="col-sm">
													<button type="button" class="btn btn-sm btn-primary waves-effect waves-light" data-toggle="modal" data-target="#myModal">관리부서선택 </button>
													<span class="bg-light font-weight-bold text-info ml-3">- 부서선택을 하지않을 경우 모든 부서가 관리권한을 가집니다.</span>
												</div>
												<%--
												<div class="col-sm">
													<button type="button" class="btn btn-sm btn-primary waves-effect waves-light depBtn">관리부서선택 </button>
													<span class="bg-light font-weight-bold text-info ml-3">- 부서선택을 하지않을 경우 모든 부서가 관리권한을 가집니다.</span>
												</div>
												<div  id="department" class="mfp-hide">
													<h5 class="modal-title mt-0">부서목록</h5>
													<hr>
													<c:forEach items="${departmentList}" var="departmen" varStatus="vs">
														<div class="custom-control custom-checkbox custom-control-inline ml-2">
															<input type="checkbox" name="bcDepartment" id="bcDepartment${vs.count }" class="custom-control-input" value="${departmen.depId }"
															<c:forEach items="${boardConfigForm.bcDepartmentArray }" var="selDep"><c:if test="${ selDep eq departmen.depId}">checked="checked"</c:if></c:forEach>
															/>
															<label for="bcDepartment${vs.count }" class="custom-control-label"> ${departmen.depName }</label>
														</div>
													</c:forEach>
												</div> --%>
												<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="display: none;" aria-hidden="true">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title mt-0" id="myModalLabel">부서목록</h5>
                                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <!-- <h4>관리부서 선택</h4> -->
                                                                  
                                                                 <input id="bstree-data" type="hidden" name="bcDepartment" data-ancestors="${boardConfigForm.bcDepartment }," >
                                                                 <div id="mytree" class="bstree">
                                                                 	<c:set var="chkLevel" value="1"/>
                                                                 	<c:forEach items="${departmentList}" var="departmen" varStatus="vs">
                                                                 		<c:if test="${vs.first }"><ul></c:if>
                                                                 		<c:if test="${departmen.depLevel > chkLevel }"><ul></c:if>
                                                                 		<c:if test="${departmen.depLevel < chkLevel }">
                                                                 			<c:forEach var="i" begin="1" end="${chkLevel - departmen.depLevel }" step="1">
                                                                 				</li></ul>
                                                                 			</c:forEach>
                                                                 		</c:if>
                                                                 		<c:if test="${!vs.first and departmen.depLevel eq chkLevel }"></li></c:if>
                                                                 		<li data-id="${departmen.depId }" data-level="${departmen.depLevel }"><span>${departmen.depName }</span>
                                                                 		<c:if test="${vs.last }"></li></ul></c:if>
                                                                 		<c:set var="chkLevel" value="${departmen.depLevel }"/>
																	</c:forEach>
																</div>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal">닫기</button>
                                                                <!-- <button type="button" class="btn btn-primary waves-effect waves-light">Save changes</button> -->
                                                            </div>
                                                        </div><!-- /.modal-content -->
                                                    </div><!-- /.modal-dialog -->
                                                </div>
												
												<%--
												<form:select path="bcDepartment" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<option value="">선택</option>
													<form:options items="${departmentList}" itemLabel="depName" itemValue="depId" />
												</form:select>
												 --%>
												 
											</div>
										</div>
										<script>
											jQuery(function($){
												
												// From an element with ID #popup
												$('.depBtn').magnificPopup({
												  items: {
												      src: '#department',
												      type: 'inline',
												     // mainClass: 'mfp-with-zoom', // class to remove default margin from left and right side
												     // zoom: {
													//		enabled: true,
													//		duration: 300 // don't foget to change the duration also in CSS
													//	}
												  }
												});
											});
										
										</script>
										
										<div class="form-group row">
											<form:label path="bcGroup" cssClass="col-sm-2 col-form-label">게시판 관리그룹 </form:label>
											<div class="col-sm">
												<form:select path="bcGroup" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<option value="">선택</option>
													<form:options items="${groupList}" itemLabel="groupName" itemValue="groupId" />
												</form:select>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-sm-2 col-form-label">일반회원 권한 </label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowMemberList" class="custom-control-input" value="true"/>
													<label for="bcAllowMemberList1" class="custom-control-label"> 목록조회</label>
												</div>
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowMemberView" class="custom-control-input" value="true"/>
													<label for="bcAllowMemberView1" class="custom-control-label"> 내용조회</label>
												</div>
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowMemberForm" class="custom-control-input" value="true"/>
													<label for="bcAllowMemberForm1" class="custom-control-label"> 글쓰기, 수정, 삭제(본인 작성글만)</label>
												</div>
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowMemberDownload" class="custom-control-input" value="true"/>
													<label for="bcAllowMemberDownload1" class="custom-control-label"> 첨부파일다운로드</label>
												</div>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-sm-2 col-form-label">비회원 권한 </label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowGuestList" class="custom-control-input" value="true"/>
													<label for="bcAllowGuestList1" class="custom-control-label"> 목록조회</label>
												</div>
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowGuestView" class="custom-control-input" value="true"/>
													<label for="bcAllowGuestView1" class="custom-control-label"> 내용조회</label>
												</div>
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowGuestForm" class="custom-control-input" value="true"/>
													<label for="bcAllowGuestForm1" class="custom-control-label"> 글쓰기, 수정, 삭제(본인 작성글만)</label>
												</div>
												<div class="custom-control custom-checkbox custom-control-inline">
													<form:checkbox path="bcAllowGuestDownload" class="custom-control-input" value="true"/>
													<label for="bcAllowGuestDownload1" class="custom-control-label"> 첨부파일다운로드</label>
												</div>
											</div>
										</div>
										
										<!-- ==================================================== 옵션  ==================================================== -->
										<hr>
										<div class="form-group row">
											<form:label path="bcSupportNotice" cssClass="col-sm-2 col-form-label">공지글기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportNotice" class="custom-control-input" value="true"/>
													<label for="bcSupportNotice1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportNotice" class="custom-control-input" value="false"/>
													<label for="bcSupportNotice2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportNotice" element="div" cssClass="text-danger" />
												<span class="bg-light font-weight-bold text-info">- 공지글은 관리자와 게시판관리자만 게시할 수 있습니다.</span>
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcNoticeEveryPage" cssClass="col-sm-2 col-form-label">공지글 표시유형 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcNoticeEveryPage" class="custom-control-input" value="true"/>
													<label for="bcNoticeEveryPage1" class="custom-control-label"> 모든페이지</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcNoticeEveryPage" class="custom-control-input" value="false"/>
													<label for="bcNoticeEveryPage2" class="custom-control-label"> 첫페이지만</label>
												</div>
												<form:errors path="bcNoticeEveryPage" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportSecret" cssClass="col-sm-2 col-form-label">비밀글기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportSecret" class="custom-control-input" value="true"/>
													<label for="bcSupportSecret1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportSecret" class="custom-control-input" value="false"/>
													<label for="bcSupportSecret2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportSecret" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportComment" cssClass="col-sm-2 col-form-label">댓글기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportComment" class="custom-control-input" value="true"/>
													<label for="bcSupportComment1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportComment" class="custom-control-input" value="false"/>
													<label for="bcSupportComment2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportComment" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportAnswer" cssClass="col-sm-2 col-form-label">답변기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportAnswer" class="custom-control-input" value="true"/>
													<label for="bcSupportAnswer1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportAnswer" class="custom-control-input" value="false"/>
													<label for="bcSupportAnswer2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportAnswer" element="div" cssClass="text-danger" />
												<span class="bg-light font-weight-bold text-info">- QnA타입의 답변이 필요한 게시판에 사용</span>
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportNuri" cssClass="col-sm-2 col-form-label">공공누리 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportNuri" class="custom-control-input" value="true"/>
													<label for="bcSupportNuri1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportNuri" class="custom-control-input" value="false"/>
													<label for="bcSupportNuri2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportNuri" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportHitDay" cssClass="col-sm-2 col-form-label">조회수 증가유형 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportHitDay" class="custom-control-input" value="true"/>
													<label for="bcSupportHitDay1" class="custom-control-label"> 하루한번</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportHitDay" class="custom-control-input" value="false"/>
													<label for="bcSupportHitDay2" class="custom-control-label"> 조회시마다</label>
												</div>
												<form:errors path="bcSupportHitDay" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportRecommend" cssClass="col-sm-2 col-form-label">추천기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportRecommend" class="custom-control-input" value="true"/>
													<label for="bcSupportRecommend1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportRecommend" class="custom-control-input" value="false"/>
													<label for="bcSupportRecommend2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportRecommend" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportMainSelec" cssClass="col-sm-2 col-form-label">메인페이지 추출 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportMainSelec" class="custom-control-input" value="true"/>
													<label for="bcSupportMainSelec1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportMainSelec" class="custom-control-input" value="false"/>
													<label for="bcSupportMainSelec2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportMainSelec" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportCommSelec" cssClass="col-sm-2 col-form-label">공통게시판 추출 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportCommSelec" class="custom-control-input" value="true"/>
													<label for="bcSupportCommSelec1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportCommSelec" class="custom-control-input" value="false"/>
													<label for="bcSupportCommSelec2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportCommSelec" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportOpenDay" cssClass="col-sm-2 col-form-label">게시날짜 설정 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportOpenDay" class="custom-control-input" value="true"/>
													<label for="bcSupportOpenDay1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportOpenDay" class="custom-control-input" value="false"/>
													<label for="bcSupportOpenDay2" class="custom-control-label"> 사용안함</label>
												</div>
												<form:errors path="bcSupportOpenDay" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<!-- ==================================================== 이미지 및 기타  ==================================================== -->
										<hr>
										<div class="form-group row">
											<form:label path="bcSupportImage" cssClass="col-sm-2 col-form-label">대표이미지 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportImage" class="custom-control-input" value="true"/>
													<label for="bcSupportImage1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportImage" class="custom-control-input" value="false"/>
													<label for="bcSupportImage2" class="custom-control-label"> 사용안함</label>
												</div>
												<span class="bg-light font-weight-bold text-info">- 대표이미지 첨부기능 여부.</span>
												<form:errors path="bcSupportImage" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcSupportThumbnail" cssClass="col-sm-2 col-form-label">썸네일기능 사용여부 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportThumbnail" class="custom-control-input" value="true"/>
													<label for="bcSupportThumbnail1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcSupportThumbnail" class="custom-control-input" value="false"/>
													<label for="bcSupportThumbnail2" class="custom-control-label"> 사용안함</label>
												</div>
												<span class="bg-light font-weight-bold text-info">- 이미지파일 첨부시 자동으로 썸네일을 생성할 것인지 여부.</span>
												<form:errors path="bcSupportThumbnail" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcThumbnailCrop" cssClass="col-sm-2 col-form-label">썸네일자르기 비율 </form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcThumbnailCrop" class="custom-control-input" value="true"/>
													<label for="bcThumbnailCrop1" class="custom-control-label"> 지정된 수치사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="bcThumbnailCrop" class="custom-control-input" value="false"/>
													<label for="bcThumbnailCrop2" class="custom-control-label"> 원본 비율 사용</label>
												</div>
												<span class="bg-light font-weight-bold text-info">- 썸네일 생성시 원본비율을 유지할 것인지여부.</span>
												<form:errors path="bcThumbnailCrop" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcThumbnailWidth" cssClass="col-sm-2 col-form-label">썸네일 크기 </form:label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> 가로 : </span>
												<form:input path="bcThumbnailWidth" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px , 세로 : </span> 
												<form:input path="bcThumbnailHeight" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> px</span> 
												<span class="bg-light font-weight-bold text-info">- 설정변경 이후에 업로드되는 파일에만 적용됩니다.</span>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="bcUploadFileNum" cssClass="col-sm-2 col-form-label">업로드파일개수 </form:label>
											<div class="col-sm form-inline">
												<form:select path="bcUploadFileNum" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
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
											<form:label path="bcUploadSizeMax" cssClass="col-sm-2 col-form-label">업로드파일용량 제한 </form:label>
											<div class="col-sm form-inline">
												<form:input path="bcUploadSizeMax" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" />
												<span class="col-form-label mx-2"> MB</span> 
												<span class="bg-light font-weight-bold text-info">- 서버설정에서 지원하는 최대값까지만 입력 가능합니다.</span>
												<span class="mx-2 bg-light font-weight-bold text-info">- 서버설정 최대값 확인은 서버관리자에게 문의해주세요.</span>
												<form:errors path="bcUploadSizeMax" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="bcPageSize" cssClass="col-sm-2 col-form-label">페이지 크기 </form:label>
											<div class="col-sm form-inline">
												<span class="col-form-label mr-2"> 페이지당 </span> 
												<form:input path="bcPageSize" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> 개의 글을 표시</span> 
												<form:errors path="bcPageSize" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/board/config/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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