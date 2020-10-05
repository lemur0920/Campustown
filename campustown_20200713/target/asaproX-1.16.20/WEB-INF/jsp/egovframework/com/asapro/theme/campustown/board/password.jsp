<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
<style>
.btn-primary {
	background: #15b993;
    color: #fff;
    font-size: 16px;
    line-height: 35px;
    width: 115px;
    /*height: 35px;*/
    display: inline-block;
    border-radius: 4px;
}    
a.btn-default {
    background: #888888;
    color: #fff;
    font-size: 16px;
    line-height: 35px;
    width: 115px;
    /*height: 35px;*/
    display: inline-block;
    border-radius: 4px;
    text-align: center;
   
}
input.password {
    width: 176px;
    height: 31px;
}
</style>
<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
<div class="RightCWarp">


	<div class="width-auto">
		<%-- 게시판 관리자일 경우 --%>
		<c:if test="${isBoardManager}">
			<h2 class="s-tit s6-tit">
				삭제 확인
			</h2>
			
			<div class="alert alert-danger">
				<ul class="list-unstyled">
					<li>삭제 하시겠습니까?</li>
					<li>삭제된 글은 복구할 수 없습니다.</li>
				</ul>
			</div>
			<form action="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/${formMode}/password" method="post">
				${SECURITY_TOKEN_TAG}
				<button class="btn btn-primary" type="submit">확인</button>
				<c:if test="${formMode eq 'edit' or formMode eq 'delete'}">
					<a class="btn btn-default" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}">취소</a>
				</c:if>
				<c:if test="${formMode eq 'secret'}">
					<a class="btn btn-default" href="${APP_PATH}/board/${boardConfig.bcId}/list">취소</a>
				</c:if>
			</form>
		</c:if>
			
		<%-- 게시판 관리자가 아닐 경우 --%>
		<c:if test="${!isBoardManager}">
			<h2 class="s-tit s6-tit">
				비밀번호 확인
			</h2>
			<br/>
			<div class="alert red">
				<c:if test="${formMode eq 'edit'}">글을 수정하기위해서는 본인확인용 비밀번호가 필요합니다.</c:if>
				<c:if test="${formMode eq 'delete'}">
					<c:if test="${sessionScope.currentUser.userRole.roleCode eq 'ROLE_GUEST'}">
						<ul class="list-unstyled">
							<li>글을 삭제하기 위해서는 본인확인용 비밀번호가 필요합니다.</li>
							<li>글작성시 입력하신 비밀번호를 입력하신 후 확인버튼을 클릭해주세요.</li>
							<li>삭제된 글은 복구할 수 없습니다.</li>
						</ul>
					</c:if>
					<c:if test="${sessionScope.currentUser.userRole.roleCode != 'ROLE_GUEST'}">
						<ul class="list-unstyled">
							<li>글을 삭제하기 위해서는 본인확인이 필요합니다.</li>
							<li>계정 비밀번호를 입력하신 후 확인버튼을 클릭해주세요.</li>
							<li>삭제된 글은 복구할 수 없습니다.</li>
						</ul>
					</c:if>
				</c:if>
				<c:if test="${formMode eq 'secret'}">글을 조회하기 위해서는 비밀번호가 필요합니다.</c:if>
			</div>
			<form name="boardPassword" class="form-inline" action="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/${formMode}/password" method="post">
				${SECURITY_TOKEN_TAG}
				<div class="form-group">
					<input class="password" type="password" name="password" />
					<button class="btn btn-primary" type="submit">확인</button>
					<c:if test="${formMode eq 'edit' or formMode eq 'delete'}">
						<a class="btn btn-default" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}">취소</a>
					</c:if>
					<c:if test="${formMode eq 'secret'}">
						<a class="btn btn-default" href="${APP_PATH}/board/${boardConfig.bcId}/list">취소</a>
					</c:if>
				</div>
				<c:if test="${not empty passwordNotMatched}">
					<div class="red">
						${passwordNotMatched}
					</div>
				</c:if>
			</form>
		</c:if>
	</div>
				
</div>
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>		
<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />