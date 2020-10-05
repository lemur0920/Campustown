<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${param.type eq 'idea'}">
        <c:forEach items="${param.Idea}" var="idea" varStatus="ideaVs">
            <c:if test="${idea.codeValue eq param.makeCounselRealm}">
                ${idea.codeName}
            </c:if>
        </c:forEach>
        <%--												<c:if test="${!ideaVs.last}">,</c:if> --%>
    </c:when>
    <c:when test="${param.type eq 'prod'}">
        <c:forEach items="${param.Prod}" var="prod" varStatus="prodVs">
            <c:if test="${prod.codeValue eq param.makeCounselRealm}">
                ${prod.codeName}
            </c:if>
        </c:forEach>
        <%--												<c:if test="${!prodVs.last}">,</c:if> --%>
    </c:when>
    <c:when test="${param.type eq 'prot'}">
        <c:forEach items="${param.Prot}" var="prot" varStatus="protVs">
            <c:if test="${prot.codeValue eq param.makeCounselRealm}">
                ${prot.codeName}
            </c:if>
        </c:forEach>
        <%--												<c:if test="${!protVs.last}">,</c:if> --%>
    </c:when>
</c:choose>