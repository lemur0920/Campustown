<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<script>
jQuery(function($){
	
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
	
	//datepicker
	$('.datepicker').datepicker();

	
	//회원검색 - 검색버튼
	$('.searchBtnLayer').on('click', function(){
		var params = $('#adminMemberSearch').serialize();
		$('#memberList').load('${ADMIN_PATH}/member/admin/searchLayer.do',params);	
	});
	
	//sv 엔터 이벤트 막기
	$('input[type="text"]').keydown(function() {
		if (event.keyCode === 13) {
			event.preventDefault();
		}
	});
	
	//회원검색 - 페이징 버튼
	$('.page-link').on('click', function(e){
		e.preventDefault();
		$('#memberList').load($(this).attr('href'));	
	});
	
	//회원선택시 input 셋팅
	$('.memberSelect').on('click', function(e){
		e.preventDefault();
		$('#menuManager').val($(this).data('name'));
		$('#menuDepartment').val($(this).data('department'));
		$('#menuPhone').val($(this).data('tel'));
		$('#menuEmail').val($(this).data('email'));
		$.magnificPopup.close();
	});

});
</script>

<!-- ============================= 메뉴별 컨텐츠 ============================ -->

<!-- 검색폼 -->
<div class="row">
    <div class="col-sm-12">
        <div class="card m-b-15">
            <div class="card-body">
	            <form:form modelAttribute="adminMemberSearch" cssClass="form-inline" action="${ADMIN_PATH}/member/admin/searchLayer.do" method="get">
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
						<form:select path="orgId" cssClass="form-control mr-sm-2 mb-2" title="기관">
							<option value="">기관</option>
							<form:options items="${organizationList}" itemLabel="orgName" itemValue="orgId"/>
						</form:select>	
						<form:select path="depId" cssClass="form-control mr-sm-2 mb-2" title="부서">
							<option value="">부서</option>
							<form:options items="${departmentList}" itemLabel="depName" itemValue="depId"/>
						</form:select>
						
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
						<button class="btn btn-outline-success waves-effect mb-2 searchBtnLayer" type="button">검색</button>
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
							<col style="width: 5%;" />
							<!--<col style="width: 70px;" /> -->
							<col />
							<col />
							<!--<col /> -->
							<col />
							<col />
							<col />
							<!--<col /> -->
							<col />
							<!--<col style="width: 8%;" />-->
						</colgroup>
						<thead>
							<tr>
								<th>상태</th>
								<!-- <th>프로필</th> -->
								<th>아이디</th>
								<th>이름</th>
								<!-- <th>전화번호</th> -->
								<th>기관</th>
								<th>부서</th>
								<th>직급</th>
								<!-- <th>그룹</th> -->
								<th>역할</th>
								<!-- <th>등록일시</th> -->
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty paging.result}">
								<tr>
									<td colspan="10" class="text-center">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${paging.result}" var="item" varStatus="vs">
								<tr>
									<td>
										<c:if test="${item.adminActive}"><span class="badge badge-success">활성</span></c:if>
										<c:if test="${not item.adminActive}"><span class="badge badge-warning">비활성</span></c:if>
									</td>
									<%--
									<td>
										<a href="${ADMIN_PATH}/member/admin/update.do?adminId=${item.adminId}">
											<img class="rounded-circle" src="${UPLOAD_PATH}/member/${item.adminId}/profile.jpg" alt="${item.adminName}" style="width: 50px; height: 50px;" onerror="this.src='${CONTEXT_PATH }/assets/images/users/profile.jpg'" />
										</a>
									</td>
									 --%>
									<td><strong>${item.adminId}</strong></td>
									<td><a href="#non" class="memberSelect" data-name="${item.adminName}" data-tel="${item.adminTelJoin}" data-department="${item.department.depName}" data-email="${item.adminEmail}"><strong>${item.adminName}</strong></a></td>
									<%-- <td><i class="mdi mdi-phone-in-talk"> ${item.adminTelJoin}</i><br><i class="mdi mdi-cellphone-iphone"> ${item.adminMobileJoin}</i></td> --%>
									<%--<td><i class="mdi mdi-phone-in-talk"> ${item.adminTelJoin}</i><br><i class="mdi mdi-cellphone-iphone"> ${item.adminMobileJoin}</i></td> --%>
									<td>${item.organization.orgName }</td>
									<td>${item.department.depName}</td>
									<%--<td>${item.department.depName}</td> --%>
									<td>${asapro:codeName(item.adminPosition, positionList)}</td>
									<%--<td></td> --%>
									<td>
										<c:forEach items="${item.adminSiteRoleRelList }" var="selRel" varStatus="vs">
											<c:forEach items="${allSiteList}" var="siteItem">
												<c:if test="${selRel.sitePrefix eq siteItem.sitePrefix}">
													${siteItem.siteName } : 
												</c:if>
											</c:forEach>
											<c:forEach items="${roleList}" var="role">
												<c:if test="${selRel.roleCode eq role.roleCode }">
													${role.roleName }
												</c:if>
											</c:forEach>
											<c:if test="${!vs.last }"><br></c:if>
										</c:forEach>
								
									</td>
									<%--
									<td>
										<fmt:formatDate value="${item.adminRegdate}" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									 --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="form-inline">
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