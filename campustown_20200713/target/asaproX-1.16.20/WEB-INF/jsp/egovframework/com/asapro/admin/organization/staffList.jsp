<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	
	//삭제
	$('#deleteBtn').click(function(e){
		e.preventDefault();
		var stIdxs = [];
		$('[name=stIdxs]:checked').each(function(idx, el){
			stIdxs.push(el.value);
		});
		if(stIdxs.length == 0){
			alert('삭제할 항목을 선택해 주세요.');
			return;
		}
		if(confirm('삭제하시겠습니까?\n\n삭제한 항목은 복구할 수 없습니다.')){
			$.ajax({
				url : '${ADMIN_PATH}/jfac/staff${API_PATH}/delete.do'
				, type : 'post'
				, data : {
					stIdxs : stIdxs
				}
				, dataType: 'json'
			}).done(function(result){
				if(result.success){
					alert('삭제되었습니다.');
					location.reload();
				} else {
					if(result.text){
						alert(result.text);
					} else {
						alert('삭제하지 못하였습니다.[error]');
					}
				}
			}).fail(function(result){
				alert('삭제하지 못하였습니다.[fail]');
			});
		}
	});
	
	//순서저장
	$('#saveOrderBtn').click(function(e){
		e.preventDefault();
		var orderData = "";
		$('.order-field').each(function(idx, el){
			if( orderData ){
				orderData += ',';
			}
			orderData += $(el).data('stidx') + '@' + el.value; 
		});
		if(confirm('순서를 수정하시겠습니까? \n순서값이 작은 항목이 먼저 출력됩니다.')){
			$.ajax({
				url : '${ADMIN_PATH}/organization/staff${API_PATH}/order.do'
				, type : 'post'
				, data : {
					orderData : orderData
				}
				, dataType: 'json'
			}).done(function(result){
				if(result.success){
					alert('수정되었습니다..');
					location.reload();
				} else {
					if(result.text){
						alert(result.text);
					} else {
						alert('수정하지 못하였습니다.[error]');
					}
				}
			}).fail(function(result){
				alert('수정하지 못하였습니다.[fail]');
			});
		}
	});
	
	//datepicker
	//$('.datepicker').datepicker();
});
</script>
<div class="page-header">
	<h1><i class="icon-globe"></i> ${currentSite.siteName} &gt; 조직및업무 &gt; 직원</h1>
</div>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/message.jsp" charEncoding="UTF-8" />
<%--
<div class="well well-small">
	<ul class="unstyled text-info" style="margin-bottom: 0;">
		<li><i class="icon-info-sign"></i> xxx</li>
	</ul>
</div>
--%>
<div class="well well-small">
	<form:form modelAttribute="staffSearch" cssClass="form-search" action="" method="get" style="margin-bottom: 0;">
		<%--
		<div>
			<label for="sc">정렬조건</label>
			<form:select path="sortOrder" cssClass="input-small" title="정렬기준">
				<form:option value="PG_CREATED" label="등록일시" />
				<form:option value="PG_TITLE" label="제목" />
			</form:select>
			<form:select path="sortDirection" cssClass="input-small" title="정렬방향">
				<form:option value="DESC" label="내림차순" />
				<form:option value="ASC" label="오름차순" />
			</form:select>
		</div>
		--%>
		<div>
			<label for="sc">검색조건</label>
			<form:select path="stDepIdx">
				<option value="">부서</option>
				<form:options items="${departmentList}" itemLabel="depName" itemValue="depIdx"/>
			</form:select>
			<form:input path="sv" cssClass="search-query input-medium" title="검색어" />
			<button class="btn btn-mini" type="submit">검색</button>
		</div>
	</form:form>
</div>
<div class="row-fluid">
	<div class="span12">
		<table class="table table-striped table-condensed table-hover table-bordered">
			<colgroup>
				<col style="width: 30px;"/>
				<col style="width: 40px;"/>
				<%--
				<col />
				--%>
				<col />
				<col />
				<col />
				<col />
			</colgroup>
			<thead>
				<tr>
					<th class="center">
						<input type="checkbox" id="selectAll" onclick="jQuery('[name=stIdxs]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
					</th>
					<th>순서</th>
					<th>부서</th>
					<%--
					<th>직위</th>
					--%>
					<th>직원이름</th>
					<th>업무</th>
					<th>전화번호</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="6" class="center">표시할 데이터가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<tr>
						<td class="center">
							<input type="checkbox" name="stIdxs" value="${item.stIdx}" />
						</td>
						<td>
							<input type="text" name="stOrder" value="${item.stOrder}" data-stidx="${item.stIdx}" class="order-field input-small" style="width: 20px;" />
						</td>
						<td>
							<c:forEach items="${departmentList}" var="department" varStatus="dvs">
								<c:if test="${department.depIdx == item.stDepIdx}">${department.depName}</c:if>
							</c:forEach>
						</td>
						<%--
						<td>
							${asapro:codeName(item.stRankCode, staffRankCodeList)}
						</td>
						--%>
						<td>
							<a href="update.do?stIdx=${item.stIdx}">${item.stName}</a>
						</td>
						<td>
							${asapro:nl2br(item.stWork, false)}
						</td>
						<td>
							${asapro:nl2br(item.stTel, false)}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<a href="insert.do" class="btn btn-info btn-mini">직원추가</a>
		<button class="btn btn-mini btn-info" type="button" id="saveOrderBtn">순서저장</button>
		<a href="#" id="deleteBtn" class="btn btn-mini">삭제</a>
		<span class="pull-right">총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="pagination pagination-centered">
			<c:out value="${paging.adminTextTag}" escapeXml="false"/>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/footer.jsp" charEncoding="UTF-8" />