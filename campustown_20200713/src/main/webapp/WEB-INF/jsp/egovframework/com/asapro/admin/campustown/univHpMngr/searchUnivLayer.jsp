<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>
jQuery(function($){
	
	// 기관값 셋팅
	$("#orgId").val($("#srcOrgId").val());
	
	// 대학목록검색 - 검색버튼
	$('.univSearchBtnLayer').on('click', function(){
		var params = $('#departmentSearch').serialize();
		$('#univList').load('${ADMIN_PATH}/univHpMngr/searchUnivLayer.do', params);	
	});
	
	//sv 엔터 이벤트 막기
	$('input[type="text"]').keydown(function() {
		if (event.keyCode === 13) {
			event.preventDefault();
		}
	});
	
	//대학목록검색 - 페이징 버튼
	$('.contentPasing .page-link').on('click', function(e){
		e.preventDefault();
		$('#univList').load($(this).attr('href'));	
	});
	
	//콘텐츠 선택시 input 셋팅
	$('.depSelect').on('click', function(e){
		e.preventDefault();
		
		$('#univId').val($(this).data('depid'));
		$('#univNm').val($(this).data('depname'));
		
		$('#tel').val($(this).data('deptel'));
		if($('#tel').val != null || $('#tel').val != ''){
			var telArr = $('#tel').val().split("-");
			$('#selOpt').val(telArr[0]);
			$('#tel2').val(telArr[1]);
			$('#tel3').val(telArr[2]);
		}
		
		$('#ment').val($(this).data('depdescription'));
		$('#depUse').val($(this).data('depuse'));	
		
		/* 
		$('#depId').val($(this).data('depid'));
		$('#depName').val($(this).data('depname'));
		$('#depTel').val($(this).data('deptel'));
		$('#depDescription').val($(this).data('depdescription'));
		 */
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
	            <form:form modelAttribute="departmentSearch" cssClass="form-inline" action="${ADMIN_PATH}/univHpMngr/searchUnivLayer.do" method="get">
					<div class="form-row mr-auto mt-2">
						<label class="mr-sm-2 mb-2">정렬조건</label>
						<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
							<form:option value="10">10개씩</form:option>
							<form:option value="20">20개씩</form:option>
							<form:option value="40">40개씩</form:option>
						</form:select>
						<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
							<form:option value="DEP_ORDER" label="순서" />
							<form:option value="DEP_ID" label="부서아이디" />
							<form:option value="DEP_NAME" label="부서이름" />
							<form:option value="DEP_NAME_EN" label="부서이름영문" />
							<form:option value="DEP_REGDATE" label="등록일" />
						</form:select>
						<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
							<form:option value="ASC" label="오름차순" />
							<form:option value="DESC" label="내림차순" />
						</form:select>
					</div>
					<div class="form-row mt-2" >
						<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
						<form:select path="orgId" readonly="true" onFocus="this.initialSelect = this.selectedIndex;"
									 onChange="this.selectedIndex = this.initialSelect;"
									 cssClass="form-control mr-sm-2 mb-2" title="기관목록">
							<option value="">기관전체</option>
							<form:options items="${organizationList}" itemLabel="orgName" itemValue="orgId"/>
							<input type="hidden" id="srcOrgId" value="${srcOrgId}" />
						</form:select>
						<form:select path="depUse" cssClass="form-control mr-sm-2 mb-2" title="사용여부">
							<option value="">사용여부</option>
							<form:option value="1" label="사용중" />
							<form:option value="0" label="미사용" />
						</form:select>
						<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
							<option value="">전체</option>
							<form:option value="DEP_NAME" label="부서이름" />
							<form:option value="DEP_NAME_EN" label="부서이름영문" />
							<form:option value="DEP_DESCRIPTION" label="부서소개" />
						</form:select>	
						<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
						<button class="btn btn-outline-success waves-effect mb-2 univSearchBtnLayer" type="button">검색</button>
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
				<!-- info -->
				<c:if test="${not empty departmentSearch.orgId }">
				<div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 순서변경은 드래그하여 이동 후 순서저장버튼</div>
				</c:if>
			
				<!-- alert maeeage -->
				<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
				<div class="table-responsive">
					<table class="table table-striped table-hover ">
						<colgroup>
							<col style="width: 30px;" />
							<col style="width: 70px;" />
							<col style="width: 20%;" />
							<col />
							<col />
							<col />
							<col style="width: 8%;" />
							<col style="width: 8%;" />
						</colgroup>
						<thead>
							<tr>
								<th class="text-center">
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=orgIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
										<label class="custom-control-label" for="selectAll"></label>
									</div>
								</th>
								<c:if test="${not empty departmentSearch.orgId }">
									<th>순서</th>
								</c:if>
								<c:if test="${empty departmentSearch.orgId }">
									<th>번호</th>
								</c:if>
								<th>소속기관</th>
								<th>부서아이디</th>
								<th>부서이름</th>
								<th>부서소개</th>
								<th>사용유무</th>
								<th>등록일시</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty paging.result}">
								<tr>
									<td colspan="8" class="text-center">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${paging.result}" var="item" varStatus="vs">
								<tr>
									<td class="text-center">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" name="depIds" value="${item.depId}" class="custom-control-input" id="depIds${vs.count }" />
											<label class="custom-control-label" for="depIds${vs.count }"></label>
										</div>
									</td>
									<c:if test="${not empty departmentSearch.orgId }">
									<td>
										<input type="text" name="depOrders" data-depid="${item.depId}" value="${item.depOrder}" readonly class="form-control form-control-sm " />
									</td>
									</c:if>
									<c:if test="${empty departmentSearch.orgId }">
										<td>${paging.rowTop - vs.index}</td>
									</c:if>
									<td>${item.organization.orgName }</td>
									<td>${item.depId}</td>
									<td><a href="#non" class="depSelect" data-depid="${item.depId}" data-depname="${item.depName}" data-deptel="${item.depTel}" data-depdescription="${item.depDescription}" data-depuse="${item.depUse}"><strong>${item.depName}</strong></a></td>
									<td>${item.depDescription}</td>
									<!-- <td>
										<a href="${ADMIN_PATH}/organization/staff/list.do?orgId=${item.orgId}" class="text-warning">직원목록</a>
									</td> -->
									<td>
										<c:if test="${item.depUse }"><span class="badge badge-success">사용</span></c:if>														
										<c:if test="${!item.depUse }"><span class="badge badge-warning">미사용</span></c:if>														
									</td>
									<td>
										<fmt:formatDate value="${item.depRegdate}" pattern="yyyy-MM-dd" />
									</td>
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
				<div class="contentPasing">
					<c:out value="${paging.adminTextTag}" escapeXml="false"/>
				</div>
				
			</div>
		</div>
	</div>
</div><!-- 목록 -->

<!-- ============================= //메뉴별 컨텐츠 ============================ -->