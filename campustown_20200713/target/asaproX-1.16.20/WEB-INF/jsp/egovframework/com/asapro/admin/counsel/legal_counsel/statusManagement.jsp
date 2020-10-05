<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>

    function fn_lawyerDel(id){
        var statusCode = 2; //삭제시 서울시 승인 대기로
        var url = "${ADMIN_PATH}/counsel/legal_counsel/lawyer/del.do";
        $.ajax({
            url : url,
            data : {
                id : id, statusCode : statusCode
            }
        }).done(function(result){
            alert("변호사 삭제 완료");
            location.reload();
        }).fail(function(result){
            alert('통신실패 [fail]');
        });
    }
</script>

<c:choose>
    <c:when test="${param.status eq 0}">
        <c:choose>
        	<%-- ${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_LAWYER')} --%>
            <%-- <c:when test="${sessionScope.currentAdmin.adminRole eq 'ROLE_UNIVERSITY_MNGR'}"> --%>
            <c:when test="${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_UNIVERSITY_MNGR')}">
                <button type="button" class="btn btn-sm btn-primary waves-effect waves-light" onclick="fn_statusUpdate(${param.id},2,'승인되었습니다.')">승인</button>
                <button type="button" class="btn btn-sm btn-primary waves-effect waves-light" onclick="fn_statusUpdate(${param.id},1,'반려되었습니다.')">반려</button>
            </c:when>
            <c:otherwise>캠퍼스타운승인전</c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${param.status eq 2}">
        <c:choose>
            <%-- <c:when test="${sessionScope.currentAdmin.adminRole eq 'ROLE_SCT_ADMIN'}"> --%>
            <c:when test="${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_SCT_ADMIN')}">
                <button type="button" class="btn btn-sm btn-primary waves-effect waves-light" data-toggle="modal" data-target=".bs-example-modal-center" id="lawyerSaveId" value="${param.id}" onclick="fn_paramId(${param.id},0)">등록</button> <%--       상태값 4번--%>
                <button type="button" class="btn btn-sm btn-primary waves-effect waves-light" onclick="fn_statusUpdate(${param.id},3,'반려되었습니다.')">반려</button>
            </c:when>
            <c:otherwise>서울시승인전</c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${param.status eq 4}">
        <c:choose>
            <%-- <c:when test="${sessionScope.currentAdmin.adminRole eq 'ROLE_SCT_ADMIN'}"> --%>
            <c:when test="${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_SCT_ADMIN')}">
                <button type="button" class="btn btn-sm btn-primary waves-effect waves-light" data-toggle="modal" data-target=".bs-example-modal-center" onclick="fn_paramId(${param.id},1)">수정</button> <%--       상태값 4번--%>
                <button type="button" class="btn btn-sm btn-primary waves-effect waves-light" onclick="fn_lawyerDel(${param.id},6)">삭제</button>
            </c:when>
            <%-- <c:when test="${sessionScope.currentAdmin.adminRole eq 'ROLE_LAWYER'}"> --%>
            <c:when test="${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_LAWYER')}">
                <button type="button" class="btn btn-sm btn-primary waves-effect waves-light" onclick="fn_lawyerDel(${param.id},6)">취소</button>
            </c:when>
        </c:choose>
    </c:when>
</c:choose>



