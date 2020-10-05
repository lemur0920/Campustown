<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<!-- Magnific popup -->
        <link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
        <script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
        <script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>
<script>
jQuery(function($){
	
	//아이디 사용가능 체크
	<c:if test="${formMode eq 'INSERT'}">
	$('#adminId').keyup(function(e){
		$('#adminId\\.errors').remove();
		var adminId = $.trim($(this).val());
		if( adminId.length >= 5 ){
			$.ajax({
				url : '${ADMIN_PATH}/member/admin/checkAdminId.do'
				, type: 'get'
				, data : {
					adminId : adminId
				}
				, dataType : 'json'
				, context : this
			}).done(function(data){
				if( data.success ){
					$('#adminIdCheckResult').html('<span class="text-success">사용가능한 아이디입니다.</span>');
				} else {
					if( data.text ){
						$('#adminIdCheckResult').html('<span class="text-danger">' + data.text + '</span>');
					} else {
						$('#adminIdCheckResult').html('<span class="text-danger">사용할 수 없는 아이디입니다.</span>');
					}
				}
			}).fail(function(){
				alert('중복확인을 실패하였습니다.[fail]');
			});
		} else {
			if( adminId.length > 0 ){
				$('#adminIdCheckResult').html('<span class="text-danger">아이디는 5자 이상이어야 합니다.</span>');
			} else {
				$('#adminIdCheckResult').html('');
			}
		}
	});
	</c:if>
	
	
	
	//기관선택시 부서목록 가져오기
	$('#adminOrganization').on('change',function(e){
		e.preventDefault();
		var orgId = $(this).val();
		
		if(orgId == null || orgId == ''){
			$('#adminDepartment').empty();
			$('#adminDepartment').append("<option value=''>선택</option>");
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
			$('#adminDepartment').empty();
			$('#adminDepartment').append("<option value=''>선택</option>");
			if(result.length > 0){
				$.each(result, function(index, item){
					//alert(item.depName);
					$('#adminDepartment').append("<option value='"+ item.depId +"' >"+ item.depName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
/* 	
	//부서선택시 팀목록 가져오기
	$('#adminDepartment').on('change',function(e){
		e.preventDefault();
		var depId = $(this).val();
		
		if(depId == null || depId == ''){
			$('#adminTeam').empty();
			$('#adminTeam').append("<option value=''>선택</option>");
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
			if(result.length > 0){
				$('#adminTeam').empty();
				$('#adminTeam').append("<option value=''>선택</option>");
				$.each(result, function(index, item){
					//alert(item.teamName);
					$('#adminTeam').append("<option value='"+ item.teamId +"' >"+ item.teamName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
 */
	//부서선택시 창업팀목록 가져오기
	$('#adminDepartment').on('change',function(e){
		e.preventDefault();
		var depId = $(this).val();
		
		if(depId == null || depId == ''){
			$('#adminTeam').empty();
			$('#adminTeam').append("<option value=''>선택</option>");
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
			$('#adminTeam').empty();
			$('#adminTeam').append("<option value=''>선택</option>");
			if(result.length > 0){
				$.each(result, function(index, item){
					//alert(item.teamName);
					$('#adminTeam').append("<option value='"+ item.compId +"' >"+ item.compNm +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
	
	
	
	//달력
	$('.datepicker').datepicker({
		yearRange : '-100:+0'
	});
	
	
	<c:if test="${formMode eq 'UPDATE'}">
	//====  switch ========
	//====  switch ========
		
	//개정 상태변경 - 활성, 비활성
	$('#adminActiveAc').on('change', function(){
		//$('#adminActive').val($(this).prop('checked'));
		
		var adminId = $('#adminId').val();
		var adminActive = $(this).prop('checked');
		if(adminId == null || adminId == ''){
			return;
		}

		$.ajax({
			url : '${ADMIN_PATH}/member/admin/status/update.do'
			, type : 'post'
			, data : {
				adminId : adminId,
				adminActive : adminActive
			}
			, dataType: 'json'
		}).done(function(result){
			if(result.success ){
				$('.adminActiveResult').html("상태가 변경 되었습니다.");
			}else{
				$('.adminActiveResult').html(result.text);
			}
			
		}).fail(function(result){
			$('.adminActiveResult').html("상태 변경 실패(fail)");
		});
	});
	
	//로그인 실패로인한 잠김 상태변경 - 잠김,해제
	$('#adminLockAc').on('change', function(){
		//$('#adminActive').val($(this).prop('checked'));
		
		var adminId = $('#adminId').val();
		var adminLock = $(this).prop('checked');
		if(adminId == null || adminId == ''){
			return;
		}

		$.ajax({
			url : '${ADMIN_PATH}/member/admin/status/update.do'
			, type : 'post'
			, data : {
				adminId : adminId,
				adminLock : adminLock
			}
			, dataType: 'json'
		}).done(function(result){
			if(result.success ){
				$('.adminLockResult').html("상태가 변경 되었습니다.");
			}else{
				$('.adminLockResult').html(result.text);
			}
			
		}).fail(function(result){alert(JSON.stringify(result));
			$('.adminLockResult').html("상태 변경 실패(fail)");
		});
	});
	
	</c:if>
	
	//롤 선택시 시스템 롤은 다중 선택할 수 없다
	$('.rolechk input:checkbox').on('change', function(e){
		var role = $(this).val();
		if(role == 'ROLE_SUPER_ADMIN' || role == 'ROLE_GUEST' || role == 'ROLE_NORMAL_ADMIN' ){
			$(this).closest('.modal-body').find('input:checkbox').prop('checked', false);
			$(this).prop('checked', true);
		} else {
			$(this).closest('.modal-body').find('.system').prop('checked', false);
		}
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
					            	
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/member/admin/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/member/admin/update.do" /></c:if>
						            <form:form modelAttribute="adminMemberForm" cssClass="" action="${actionUrl}" method="post">
										<div class="form-group row">
											<form:label path="adminId" cssClass="col-sm-2 col-form-label">관리자 아이디 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:if test="${formMode == 'INSERT'}">
													<form:input path="adminId" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="영문, 숫자만 5~20자" maxlength="20"/>
												</c:if>
												<c:if test="${formMode == 'UPDATE'}">
													<form:input path="adminId" readonly="true" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="영문, 숫자만 5~20자" maxlength="20"/>
												</c:if>
												<form:errors path="adminId" element="div" cssClass="text-danger col-form-label ml-2" />
												<div id="adminIdCheckResult" class="col-form-label"></div>
											</div>
										</div>
										<c:if test="${formMode == 'INSERT'}">
										<div class="form-group row">
											<form:label path="adminPassword" cssClass="col-sm-2 col-form-label">관리자 비밀번호 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:password path="adminPassword" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="영문, 숫자, 특수문자만  9~20자 " />
												<form:errors path="adminPassword" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminPasswordCheck" cssClass="col-sm-2 col-form-label">관리자 비밀번호 확인 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:password path="adminPasswordCheck" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="영문, 숫자, 특수문자만  9~20자 " />
												<form:errors path="adminPasswordCheck" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										</c:if>
										<div class="form-group row">
											<form:label path="adminName" cssClass="col-sm-2 col-form-label">관리자 이름 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="adminName" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" />
												<form:errors path="adminName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminSex" cssClass="col-sm-2 col-form-label">성별 </form:label>
											<div class="col-sm">
												<div class="col-form-label">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="adminSex" class="custom-control-input" value="M"/>
														<label for="adminSex1" class="custom-control-label"> 남</label>
													</div>
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="adminSex" class="custom-control-input" value="F"/>
														<label for="adminSex2" class="custom-control-label"> 여</label>
													</div>
												</div>
												<form:errors path="adminSex" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminEmail" cssClass="col-sm-2 col-form-label">관리자 이메일 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="adminEmail" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="ex)admin@cms.com" />
												<form:errors path="adminEmail" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminTel1" cssClass="col-sm-2 col-form-label">관리자 전화번호 </form:label>
											<div class="col-sm form-inline">
												<form:select path="adminTel1" cssClass="form-control col-sm-1">
													<option value="" label="선택">
													<form:options items="${areaCodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												 - <form:input path="adminTel2" cssClass="form-control col-sm-1" maxlength="4" />
												 - <form:input path="adminTel3" cssClass="form-control col-sm-1" maxlength="4" />
												<form:errors path="adminTel1" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminMobile1" cssClass="col-sm-2 col-form-label">관리자 휴대폰번호 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:select path="adminMobile1" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid">
													<option value="" label="선택">
													<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												 - <form:input path="adminMobile2" cssClass="form-control col-sm-1" maxlength="4" />
												 - <form:input path="adminMobile3" cssClass="form-control col-sm-1" maxlength="4" />
												<form:errors path="adminMobile1" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminFax" cssClass="col-sm-2 col-form-label">관리자 팩스번호 </form:label>
											<div class="col-sm">
												<form:input path="adminFax" cssClass="form-control col-sm-2" />
												<form:errors path="adminFax" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminZipcode" cssClass="col-sm-2 col-form-label">우편번호 </form:label>
											<div class="col-sm form-inline">
												<form:input path="adminZipcode" id="zipcode" cssClass="form-control col-sm-1 mr-sm-2" placeholder="우편번호" />
												<button id="zipSearchBtn" class="btn btn-primary waves-effect waves-light" >우편번호 찾기</button>
												<form:errors path="adminZipcode" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminAddress" cssClass="col-sm-2 col-form-label">주소 </form:label>
											<div class="col-sm">
												<form:input path="adminAddress" id="address" cssClass="form-control col-sm-4" placeholder="주소" />
												<form:errors path="adminAddress" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminAddressDetail" cssClass="col-sm-2 col-form-label">상세주소 </form:label>
											<div class="col-sm">
												<form:input path="adminAddressDetail" id="addressDetail" cssClass="form-control col-sm-3" placeholder="상세주소" />
												<form:errors path="adminAddressDetail" element="div" cssClass="text-danger" />
											</div>
										</div>
										<!-- Daum 우편번호 서비스 스크립트  -->
										<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/zipSearch.jsp" charEncoding="UTF-8" />
										<%--
										<div class="form-group row">
											<form:label path="adminActive" cssClass="col-sm-2 col-form-label">관리자 상태 </form:label>
											<div class="col-sm form-inline">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="adminActive" class="custom-control-input" value="true"/>
													<label for="adminActive1" class="custom-control-label"> 활성</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="adminActive" class="custom-control-input" value="false"/>
													<label for="adminActive2" class="custom-control-label"> 비활성</label>
												</div>
												<form:errors path="adminActive" element="div" cssClass="text-danger" />
											</div>
										</div>
										 --%>
										
										<hr>
										
										<div class="form-group row">
											<form:label path="adminOrganization" cssClass="col-sm-2 col-form-label">소속 기관 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:choose>
													<c:when test="${!currentAdmin.superAdmin and not empty organizationList and currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel >= 1 }">
														<c:forEach items="${organizationList}" var="organization" varStatus="vs">
															<c:if test="${organization.orgId eq currentAdmin.adminOrganization }">
																<input name="org" value="${organization.orgName }" class="form-control col-sm-3" title="소속 기관" disabled="disabled"/>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<form:select path="adminOrganization" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" >
															<option value="">선택</option>
															<%--<form:options items="${organizationList}" itemLabel="orgName" itemValue="orgId" /> --%>
															<c:forEach items="${organizationList }" var="item" varStatus="vs">
																<form:option value="${item.orgId }" label="" >${item.orgName }<c:if test="${!item.orgUse }"> (미사용)</c:if></form:option>
															</c:forEach>
														</form:select>
													</c:otherwise>
												</c:choose>
												<form:errors path="adminOrganization" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="adminDepartment" cssClass="col-sm-2 col-form-label">소속 부서 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:choose>
												 	<c:when test="${!currentAdmin.superAdmin and not empty departmentList and currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel >= 2 }">
												 		<c:forEach items="${departmentList}" var="department" varStatus="vs">
												 			<c:if test="${department.depId eq currentAdmin.adminDepartment }">
												 				<input name="dep" value="${department.depName }" class="form-control col-sm-3" title="소속 부서" disabled="disabled"/>
												 			</c:if>
												 		</c:forEach>
												 	</c:when>
												 	<c:otherwise>
														<form:select path="adminDepartment" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" >
															<option value="">선택</option>
															<form:options items="${departmentList}" itemLabel="depName" itemValue="depId" />
														</form:select>
												 	</c:otherwise>
												 </c:choose>
												<form:errors path="adminDepartment" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="adminTeam" cssClass="col-sm-2 col-form-label">소속 팀 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:choose>
												 	<c:when test="${!currentAdmin.superAdmin and not empty teamList and currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel >= 3 }">
												 		<c:forEach items="${teamList}" var="team" varStatus="vs">
												 			<c:if test="${team.compId eq currentAdmin.adminTeam }">
												 				<input name="team" value="${team.compNm }" class="form-control col-sm-3" title="팀" disabled="disabled"/>
												 			</c:if>
												 		</c:forEach>
												 	</c:when>
												 	<c:otherwise>
														<form:select path="adminTeam" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" >
															<option value="">선택</option>
															<form:options items="${teamList}" itemLabel="compNm" itemValue="compId" />
														</form:select>
													<%-- 
														<form:select path="adminTeam" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" >
															<option value="">선택</option>
															<form:options items="${teamList}" itemLabel="teamName" itemValue="teamId" />
														</form:select> --%>
												 	</c:otherwise>
												 </c:choose>
												<form:errors path="adminTeam" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="adminPosition" cssClass="col-sm-2 col-form-label">직급/직책 </form:label>
											<div class="col-sm">
												<form:select path="adminPosition" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<option value="">선택</option>
													<form:options items="${positionList}" itemLabel="codeName" itemValue="codeId" />
												</form:select>
												<form:errors path="adminPosition" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<label id="adminSiteRoleRelList" class="col-sm-2 col-form-label">관리 사이트 및 역할 <span class="text-danger">*</span></label>
											<div class="col-sm col-form-label">
												<c:forEach items="${allSiteList}" var="siteItem" varStatus="vs">
													<c:set var="checkSite" value="false"/>
													<c:set var="selRoleCode" value=""/>
													<c:forEach items="${adminMemberForm.adminSiteRoleRelList }" var="selRel">
														<c:if test="${ selRel.sitePrefix eq siteItem.sitePrefix}">
															<c:set var="checkSite" value="true"/>
															<c:set var="selRoleCode" value="${selRel.roleCode}"/>
														</c:if>
													</c:forEach>

													<div class="form-inline mb-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" name="adminSiteRoleRelList[${vs.index}].sitePrefix" id="siteIds${vs.count }" class="custom-control-input" value="${siteItem.sitePrefix}"
															<c:if test="${checkSite}">checked="checked"</c:if>	/>
															<label class="custom-control-label" for="siteIds${vs.count }">${siteItem.siteName }</label>
														</div>
														
														<div class="col-sm">
															<button type="button" class="btn btn-sm btn-primary waves-effect waves-light" data-toggle="modal" data-target="#myModal${vs.count }">역할선택 </button>
														</div>
														
														<div id="myModal${vs.count }" class="modal fade rolechk" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="display: none;" aria-hidden="true">
		                                                    <div class="modal-dialog modal-lg">
		                                                        <div class="modal-content">
		                                                            <div class="modal-header">
		                                                                <h5 class="modal-title mt-0" id="myModalLabel">역할목록</h5>
		                                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		                                                            </div>
		                                                            <div class="modal-body">
			                                                            <div><span class="text-danger">*</span><span class="mx-2 bg-light font-weight-bold text-info">는 다중 부여 할 수 없습니다.</span></div>
		                                                                <!-- <h4>역할 선택</h4> -->
		                                                               <%--  
		                                                                <select name="adminSiteRoleRelList[${vs.index}].roleCode" class="form-control col-sm-3 ml-4" multiple >
																			<option value="" >선택</option>
																			<c:forEach items="${roleList}" var="role" varStatus="rvs">
																				<c:set var="isChkRole" value="false" />
																				<c:forTokens items="${selRoleCode }" delims="," var="chkRole">
																					<c:if test="${role.roleCode eq chkRole }">
																						<c:set var="isChkRole" value="true" />
																					</c:if>
																				</c:forTokens>
																				<option value="${role.roleCode }" label="${role.roleName }" <c:if test="${isChkRole }">selected="selected"</c:if> />
																			</c:forEach>
																		</select> --%>
																		
																		
																		<c:forEach items="${roleList}" var="role" varStatus="rvs">
																			<c:set var="isChkRole" value="false" />
																			<c:set var="isSystem" value="false" />
																			<c:forTokens items="${selRoleCode }" delims="," var="chkRole">
																				<c:if test="${role.roleCode eq chkRole }">
																					<c:set var="isChkRole" value="true" />
																				</c:if>
																			</c:forTokens>
																			<c:if test="${role.roleCode eq 'ROLE_SUPER_ADMIN' or role.roleCode eq 'ROLE_GUEST' or role.roleCode eq 'ROLE_NORMAL_ADMIN'}">
																				<c:set var="isSystem" value="true" />
																			</c:if>
																			<div class="custom-control custom-checkbox">
																				<input type="checkbox" name="adminSiteRoleRelList[${vs.index}].roleCode" id="roleCode${vs.count }${rvs.count }" class="custom-control-input<c:if test="${isSystem }"> system</c:if>" value="${role.roleCode }"
																				<c:if test="${isChkRole }">checked="checked"</c:if>	/>
																				<label class="custom-control-label" style="display: block;" for="roleCode${vs.count }${rvs.count }">${role.roleName } <c:if test="${isSystem }"> <span class="text-danger">*</span></c:if></label>
																			</div>
																		</c:forEach>
														
		                                                            </div>
		                                                            <div class="modal-footer">
		                                                                <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal">닫기</button>
		                                                                <!-- <button type="button" class="btn btn-primary waves-effect waves-light">Save changes</button> -->
		                                                            </div>
		                                                        </div><!-- /.modal-content -->
		                                                    </div><!-- /.modal-dialog -->
		                                                </div>
														
													</div>
												</c:forEach>
												<form:errors path="adminSiteRoleRelList" element="div" cssClass="text-danger" />
											</div>
										</div>
										 
										
										<div class="form-group row level">
											<form:label path="adminSex" cssClass="col-sm-2 col-form-label">관리자 관리레벨 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<div class="col-form-label">
													<c:choose>
														<c:when test="${!currentAdmin.superAdmin }">
															<c:choose>
																<c:when test="${currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel <= 1 }">
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="adminManagementLevel" id="adminManagementLevel1" class="custom-control-input" value="1"/>
																		<label for="adminManagementLevel1" class="custom-control-label"> 기관 이하</label>
																	</div>
																</c:when>
															</c:choose>
															<c:choose>
																<c:when test="${currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel <= 2 }">
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="adminManagementLevel" id="adminManagementLevel2" class="custom-control-input" value="2"/>
																		<label for="adminManagementLevel2" class="custom-control-label"> 부서 이하</label>
																	</div>
																</c:when>
															</c:choose>
															<c:choose>
																<c:when test="${currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel <= 3 }">	
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="adminManagementLevel" id="adminManagementLevel3" class="custom-control-input" value="3"/>
																		<label for="adminManagementLevel3" class="custom-control-label"> 팀 이하</label>
																	</div>
																</c:when>
															</c:choose>		
														</c:when>
													
														<c:otherwise>
															<div class="custom-control custom-radio custom-control-inline">
																<form:radiobutton path="adminManagementLevel" class="custom-control-input" value="1"/>
																<label for="adminManagementLevel1" class="custom-control-label"> 기관 이하</label>
															</div>
															<div class="custom-control custom-radio custom-control-inline">
																<form:radiobutton path="adminManagementLevel" class="custom-control-input" value="2"/>
																<label for="adminManagementLevel2" class="custom-control-label"> 부서 이하</label>
															</div>
															<div class="custom-control custom-radio custom-control-inline">
																<form:radiobutton path="adminManagementLevel" class="custom-control-input" value="3"/>
																<label for="adminManagementLevel3" class="custom-control-label"> 팀 이하</label>
															</div>
														</c:otherwise>
													</c:choose>
												</div>
												<span class="mx-2 bg-light font-weight-bold text-info">- 슈퍼관리자를 제외한 관리자관리 권한이 있는 경우만 레벨에 따른 관리가 가능합니다</span>
												<form:errors path="adminManagementLevel" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										
										 
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/member/admin/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
											<c:if test="${formMode == 'UPDATE'}">
											<a href="${ADMIN_PATH}/member/admin/password/update.do?adminId=${adminMemberForm.adminId}" class="btn btn-warning waves-effect waves-light">비밀번호 변경</a>
											</c:if>
										</div>
										
										<c:if test="${formMode == 'UPDATE'}">
										<hr>
										<%--관리자 계정 상태 --%>
										<div class="form-group row">
											<form:label path="adminActive" cssClass="col-sm-2 col-form-label">계정 상태 </form:label>
											<div class="col-sm">
				                            	<form:hidden path="adminActive" />
				                            	<input name="adminActiveAc" value="${adminMemberForm.adminActive }" type="checkbox" <c:if test="${adminMemberForm.adminActive }">checked</c:if> data-on="활성" data-off="비활성" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" data-width="85" id="adminActiveAc">
					                            <span class="adminActiveResult text-warning mx-2"></span>
				                            </div>
											<%--<form:errors path="adminActive" element="div" cssClass="text-danger" /> --%>
										</div>
										
										<div class="form-group row">
											<form:label path="adminLock" cssClass="col-sm-2 col-form-label">로그인 제한(잠김) 상태 </form:label>
											<div class="col-sm">
				                            	<form:hidden path="adminLock" />
				                            	<input name="adminLockAc" value="${adminMemberForm.adminLock }" type="checkbox" <c:if test="${adminMemberForm.adminLock }">checked</c:if> data-on="잠김" data-off="해제" data-toggle="toggle" data-onstyle="danger" data-offstyle="success" data-width="85" id="adminLockAc">
					                            <span class="adminLockResult text-warning mx-2"></span>
				                            </div>
											<%--<form:errors path="adminLock" element="div" cssClass="text-danger" /> --%>
										</div>
										</c:if>
										
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