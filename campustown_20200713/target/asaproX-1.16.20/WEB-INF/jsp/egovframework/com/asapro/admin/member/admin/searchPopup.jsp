<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
== 사용법 예시 ==
1. 부모창의 javascript function을 openerCallback 파라메터로 전달.
2. extra1, extra2 는 필요한 경우 사용.
** 아래 소스를 부모창이될 jsp 에서 추가한다.
<script>
function testCallback(data){
	console.log(data);
}
</script>
<button class="btn btn-mini btn-info" type="button" onclick="window.open('${ADMIN_PATH}/member/searchPopup.do?openerCallback=testCallback&extra1=param1&extra2=param2', 'memberSearchPopup', 'width=800,height=700,scrollbars=auto,toolbar=no,menubar=no');">회원검색팝업</button>
--%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	//선택버튼
	$('.select-btn').click(function(e){
		e.preventDefault();
		if( confirm('선택하시겠습니까?') ){
			<c:if test="${not empty param.openerCallback}">
				var data = {};
				data.memberId = $(e.target).data('memberid');
				<c:if test="${not empty memberSearch.extra1}">
					data.extra1 = '${memberSearch.extra1}';
				</c:if>
				<c:if test="${not empty memberSearch.extra2}">
					data.extra2 = '${memberSearch.extra2}';
				</c:if>
				opener.<c:out value="${param.openerCallback }" escapeXml="true" />(data);
				self.close();
			</c:if>
			<c:if test="${empty param.openerCallback}">
				alert('회원검색 팝업창을 띄울때 선택버튼 클릭시 실행될 부모창의 javascript function을 openerCallback 파라메터로 전달해야 합니다.');
			</c:if>
		}
	});
});
</script>
<div class="page-header">
	<h1><i class="icon-globe"></i> ${currentSite.siteName} &gt; 회원검색</h1>
</div>
<%--
<div class="well well-small">
	<ul class="unstyled text-info" style="margin-bottom: 0;">
		<li><i class="icon-info-sign"></i> 프로필 사진이 갱신되지 않을 경우 새로고침을 해주세요.</li>
	</ul>
</div>
--%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/message.jsp" charEncoding="UTF-8" />
<div class="well well-small" style="text-align: right">
	<form:form modelAttribute="memberSearch" cssClass="form-search" action="${ADMIN_PATH}/member/searchPopup.do" method="get" style="margin-bottom: 0;">
		<form:hidden path="openerCallback"/>
		<form:hidden path="groupId"/>
		<form:hidden path="extra1"/>
		<form:hidden path="extra2"/>
		<label for="sc">검색조건</label>
		<form:select path="sc" cssClass="input-small" title="검색조건">
			<option value="">전체</option>
			<form:option value="memberId" label="회원아이디" />
			<form:option value="memberName" label="회원이름" />
			<form:option value="memberEmail" label="이메일" />
		</form:select>
		<%--
		<form:select path="roleCode" cssClass="input-small" title="역할">
			<option value="">역할</option>
			<c:forEach items="${roleList}" var="item" varStatus="vs">
				<c:if test="${item.roleCode != 'ROLE_GUEST'}">
					<form:option value="${item.roleCode}">${item.roleName}</form:option>
				</c:if>
			</c:forEach>
		</form:select>
		<form:select path="memberLevel" cssClass="input-small" title="${levelCodeCategory.catName}">
			<option value="">${levelCodeCategory.catName}</option>
			<form:options items="${levelCodeList}" itemValue="codeId" itemLabel="codeName" />
		</form:select>
		--%>			
		<%--
		<form:select path="memberActive" cssClass="input-small" title="상태">
			<option value="">상태</option>
			<form:option value="true" label="활성" />
			<form:option value="false" label="비활성" />
		</form:select>
		<form:select path="memberSignUpKeyChecked" cssClass="input-small" title="이메일확인">
			<option value="">이메일계정</option>
			<form:option value="true" label="확인됨" />
			<form:option value="false" label="미확인" />
		</form:select>
		--%>
		<form:select path="pageSize" cssClass="input-small pull-left" title="페이지크기" cssStyle="margin-right: 3px;">
			<form:option value="10">10개씩</form:option>
			<form:option value="20">20개씩</form:option>
			<form:option value="40">40개씩</form:option>
		</form:select>
		<form:select path="sortOrder" cssClass="input-small pull-left" title="정렬기준" cssStyle="margin-right: 3px;">
			<form:option value="MEM_REGDATE" label="등록일시" />
			<form:option value="MEM_ID" label="회원아이디" />
			<form:option value="MEM_NAME" label="회원이름" />
		</form:select>
		<form:select path="sortDirection" cssClass="input-small pull-left" title="정렬방향" cssStyle="margin-right: 3px;">
			<form:option value="DESC" label="내림차순" />
			<form:option value="ASC" label="오름차순" />
		</form:select>
		<form:input path="sv" cssClass="search-query input-medium" title="검색어" />
		<button class="btn btn-mini" type="submit">검색</button>
	</form:form>
</div>
<div class="row-fluid">
	<div class="span12">
		<table class="table table-striped table-condensed table-hover table-bordered">
			<colgroup>
				<col />
				<col />
				<%--
				<col style="width: 150px;" />
				--%>
				<col />
				<%--
				<col style="width: 150px;" />
				--%>
				<col />
				<col />
				<col style="width: 50px" />
			</colgroup>
			<thead>
				<tr>
					<th>회원이름</th>
					<th>회원아이디</th>
					<%--
					<th>전화번호</th>
					--%>
					<th>회원이메일</th>
					<%--
					<th>가입일시/최종방문일시</th>
					--%>
					<th>그룹</th>
					<th>등급/역할</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="6" class="center">표시할 데이터가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<tr <c:if test="${not item.memberActive}">class="strike"</c:if>>
						<%--
						<td class="center">
							<c:if test="${item.memberRole.roleAdmin}">
								-
							</c:if>
							<c:if test="${!item.memberRole.roleAdmin}">
								<input type="checkbox" name="memberIds" value="${item.memberId}" />
							</c:if>
						</td>
						--%>
						<td>
							${item.memberName}
						</td>
						<td>
							${item.memberId}
						</td>
						<%-- 
						<td>
							산불총회용 필드 예외 처리
							<c:if test="${not empty item.memberTel1 && not empty item.memberTel2 && not empty item.memberTel3}">
								<div>${item.memberTel1}-${item.memberTel2}-${item.memberTel3}</div>
							</c:if>
							<c:if test="${not empty item.memberMobile1 && not empty item.memberMobile2 && not empty item.memberMobile3}">
								<div>${item.memberMobile1}-${item.memberMobile2}-${item.memberMobile3}</div>
							</c:if>
							<div>${item.memberTel}</div>
							<div>${item.memberMobile}</div>
						</td>
						--%>
						<td>
							<c:if test="${!item.memberSignUpKeyChecked}">
								<div>
									<span class="label label-important">미확인 이메일계정</span> <a class="confirm-email" href="${ADMIN_PATH}/member/confirmEmail.do" data-confirmType="signUp" data-memberId="${item.memberId}">이메일승인</a>
								</div>
							</c:if>
							${item.memberEmail}
						</td>
						<%--
						<td>
							<fmt:formatDate value="${item.memberRegDate}" pattern="yyyy-MM-dd HH:mm:ss" /> / <fmt:formatDate value="${item.memberLastLoginDate}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						--%>
						<td>
							<c:forEach items="${item.groups}" var="group" varStatus="gvs">
							<c:if test="${gvs.index > 0}"><br /></c:if>
								${group.groupName}
							</c:forEach>
						</td>
						<td>
							<div style="font-weight: bold;">
								<c:forEach items="${levelCodeList}" var="level" varStatus="lvs">
									<c:if test="${item.memberLevel eq level.codeId}">${level.codeName}</c:if>
								</c:forEach>
							</div>
							${item.memberRole.roleName}
						</td>
						<td>
							<button class="btn btn-mini btn-info select-btn" type="button" data-memberid="${item.memberId}">선택</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
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