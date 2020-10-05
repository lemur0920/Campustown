<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar"%>
<%@ page import="egovframework.com.asapro.member.admin.service.AdminMember"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<% 
AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
int thisYear = Calendar.getInstance().get(Calendar.YEAR);
%>
<c:set var="thisYear" value="<%= thisYear %>" />
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	//삭제 Ajax
	$('#deleteBtn').on('click', function(e){
		var adminIds = [];
		$('[name=adminIds]:checked').each(function(idx, el){
			adminIds.push(el.value);
		});
		
		swal({
            title: '삭제한 항목은 복구할 수 없습니다.\n삭제하시겠습니까?',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (adminIds.length === 0) {
                            reject('삭제할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/member/admin/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					adminIds : adminIds
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '삭제가 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '삭제결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '삭제하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '삭제하지 못하였습니다.[fail]'
	            });
			});
        }).catch(swal.noop);
	});
	
	
	//기관선택시 부서목록 가져오기
	$('#orgId').on('change',function(e){
		e.preventDefault();
		var orgId = $(this).val();
		
		if(orgId == null || orgId == ''){
			$('#depId').empty();
			$('#depId').append("<option value=''>부서</option>");
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
				$('#depId').empty();
				$('#depId').append("<option value=''>부서</option>");
				$.each(result, function(index, item){
					//alert(item.depName);
					$('#depId').append("<option value='"+ item.depId +"' >"+ item.depName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});

	//부서선택시 팀목록 가져오기
	$('#depId').on('change',function(e){
		e.preventDefault();
		var depId = $(this).val();
		
		if(depId == null || depId == ''){
			$('#teamId').empty();
			$('#teamId').append("<option value=''>팀</option>");
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
				$('#teamId').empty();
				$('#teamId').append("<option value=''>팀</option>");
				$.each(result, function(index, item){
					//alert(item.depName);
					/* $('#teamId').append("<option value='"+ item.teamId +"' >"+ item.teamName +"</option>"); */
					$('#teamId').append("<option value='"+ item.compId +"' >"+ item.compNm +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});

	
	//datepicker
	$('.datepicker').datepicker();
	
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
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
						            <form:form modelAttribute="adminMemberSearch" cssClass="form-inline" action="${ADMIN_PATH}/member/admin/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="ADMIN_REGDATE" label="등록일시" />
												<form:option value="ADMIN_ID" label="관리자아이디" />
												<!--<form:option value="ADMIN_NAME" label="관리자이름" />-->
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="adminRoleCode" cssClass="form-control mr-sm-2 mb-2" title="역할">
												<option value="">역할</option>
												<c:forEach items="${roleList}" var="item" varStatus="vs">
													<c:if test="${item.roleCode != 'ROLE_GUEST'}">
														<form:option value="${item.roleCode}">${item.roleName}</form:option>
													</c:if>
												</c:forEach>
											</form:select>	
											<%--
											<form:select path="groupId" cssClass="form-control mr-sm-2 mb-2" title="그룹">
												<option value="">그룹</option>
												<form:options items="${groupList}" itemLabel="groupName" itemValue="groupId"/>
											</form:select>	
											 --%>
											 <c:choose>
											 	<c:when test="${!currentAdmin.superAdmin and not empty organizationList and currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel >= 1 }">
											 		<c:forEach items="${organizationList}" var="organization" varStatus="vs">
											 			<c:if test="${organization.orgId eq currentAdmin.adminOrganization }">
											 				<input name="org" value="${organization.orgName }" class="form-control mr-sm-2 mb-2" title="기관" disabled="disabled"/>
											 			</c:if>
											 		</c:forEach>
											 	</c:when>
											 	<c:otherwise>
													<form:select path="orgId" cssClass="form-control mr-sm-2 mb-2" title="기관">
														<option value="">기관</option>
														<form:options items="${organizationList}" itemLabel="orgName" itemValue="orgId"/>
													</form:select>
											 	</c:otherwise>
											 </c:choose>
											 
											 <c:choose>
											 	<c:when test="${!currentAdmin.superAdmin and not empty departmentList and currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel >= 2 }">
											 		<c:forEach items="${departmentList}" var="department" varStatus="vs">
											 			<c:if test="${department.depId eq currentAdmin.adminDepartment }">
											 				<input name="dep" value="${department.depName }" class="form-control mr-sm-2 mb-2" title="부서" disabled="disabled"/>
											 			</c:if>
											 		</c:forEach>
											 	</c:when>
											 	<c:otherwise>
													<form:select path="depId" cssClass="form-control mr-sm-2 mb-2" title="부서">
														<option value="">부서</option>
														<form:options items="${departmentList}" itemLabel="depName" itemValue="depId"/>
													</form:select>
											 	</c:otherwise>
											 </c:choose>
											 
											 <c:choose>
											 	<c:when test="${!currentAdmin.superAdmin and not empty teamList and currentAdmin.adminManagementLevel != null and currentAdmin.adminManagementLevel >= 3 }">
											 		<c:forEach items="${teamList}" var="team" varStatus="vs">
											 			<c:if test="${team.compId eq currentAdmin.adminTeam }">
											 				<input name="team" value="${team.compNm }" class="form-control mr-sm-2 mb-2" title="팀" disabled="disabled"/>
											 			</c:if>
											 		</c:forEach>
											 	</c:when>
											 	<c:otherwise>
													<form:select path="teamId" cssClass="form-control mr-sm-2 mb-2" title="팀">
														<option value="">팀</option>
														<form:options items="${teamList}" itemLabel="compNm" itemValue="compId"/>
													</form:select>
												<%-- <form:select path="teamId" cssClass="form-control mr-sm-2 mb-2" title="팀">
													<option value="">팀</option>
													<form:options items="${teamList}" itemLabel="teamName" itemValue="teamId"/>
												</form:select> --%>
											 	</c:otherwise>
											 </c:choose>
											
											
											<form:select path="adminActive" cssClass="form-control mr-sm-2 mb-2" title="상태">
												<option value="">상태</option>
												<form:option value="true" label="활성" />
												<form:option value="false" label="비활성" />
											</form:select>	
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="adminId" label="관리자아이디" />
												<form:option value="adminName" label="관리자이름" />
												<form:option value="adminEmail" label="관리자이메일" />
											</form:select>	
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											<button class="btn btn-outline-success waves-effect mb-2 " type="submit">검색</button>
										</div>
									</form:form>
					            </div>
					        </div>
					    </div>
					</div>
					<!-- //검색폼 -->
					
					<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
								
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
									<div class="table-responsive">
										<table class="table table-striped table-hover ">
											<colgroup>
												<col style="width: 30px;" />
												<%-- <col style="width: 5%;" /> --%>
												<col style="width: 70px;" />
												<col style="width: 8%;" />
												<col style="width: 8%;" />
												<col style="width: 10%;" />
												<col />
												<col />
												<col />
												<col style="width: 20%;" />
												<col style="width: 8%;" />
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=adminIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>상태</th>
													<!-- <th>프로필</th> -->
													<th>아이디</th>
													<th>이름</th>
													<th>전화번호</th>
													<th>기관</th>
													<th>부서</th>
													<th>팀</th>
													<th>역할</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="11" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="adminIds" value="${item.adminId}" class="custom-control-input" id="adminIds${vs.count }" />
																<label class="custom-control-label" for="adminIds${vs.count }"></label>
															</div>
														</td>
														<td>
															<c:if test="${item.adminActive}"><span class="badge badge-success">활성</span></c:if>
															<c:if test="${not item.adminActive}"><span class="badge badge-warning">비활성</span></c:if>
														</td>
														<%-- <td>
															<a href="${ADMIN_PATH}/member/admin/update.do?adminId=${item.adminId}">
																<img class="rounded-circle" src="${UPLOAD_PATH}/member/${item.adminId}/profile.jpg" alt="${item.adminName}" style="width: 50px; height: 50px;" onerror="this.src='${CONTEXT_PATH }/assets/images/users/profile.jpg'" />
															</a>
														</td> --%>
														<td><a href="${ADMIN_PATH}/member/admin/update.do?adminId=${item.adminId}"><strong>${item.adminId}</strong></a></td>
														<td><a href="${ADMIN_PATH}/member/admin/update.do?adminId=${item.adminId}"><strong>${item.adminName}</strong></a></td>
														<td><i class="mdi mdi-phone-in-talk"> ${item.adminTelJoin}</i><br><i class="mdi mdi-cellphone-iphone"> ${item.adminMobileJoin}</i></td>
														<td>${item.organization.orgName }</td>
														<td>${item.department.depName}</td>
														<%-- <td>${item.team.teamName}</td> --%>
														<td>
															<c:forEach items="${allTeamList }" var="comp" varStatus="vs">
																<c:if test="${comp.compId eq item.adminTeam }">
																	${comp.compNm}
																</c:if>
															</c:forEach>
														</td>
														<td>
															<c:forEach items="${item.adminSiteRoleRelList }" var="selRel" varStatus="vs">
																<c:forEach items="${allSiteList}" var="siteItem">
																	<c:if test="${selRel.sitePrefix eq siteItem.sitePrefix}">
																		<b>${siteItem.siteName }</b> : 
																	</c:if>
																</c:forEach>
																<c:forEach items="${roleList}" var="role">
																	<c:forTokens items="${selRel.roleCode }" delims="," var="selRelItems" varStatus="vsrel">
																		<c:if test="${selRelItems eq role.roleCode }">
																		<c:if test="${vsrel.index > 0 }">, </c:if>
																			${role.roleName }
																		</c:if>
																	</c:forTokens>
																</c:forEach>
																<c:if test="${!vs.last }"><br></c:if>
															</c:forEach>
													
														</td>
														<td>
															<fmt:formatDate value="${item.adminRegdate}" pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/member/admin/insert.do" class="btn btn-primary waves-effect waves-light">관리자추가</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
										</div>
										<div>
											<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
										</div>
									</div>

									<!-- paging -->
									<div>
										<c:out value="${paging.adminTextTag}" escapeXml="false"/>
									</div>
									
								</div>
							</div>
						</div>
					</div><!-- 목록 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />