<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${param.status eq 0}">법률자문신청</c:when>
    <c:when test="${param.status eq 1}">캠퍼스타운반려</c:when>
    <c:when test="${param.status eq 2}">서울시승인대기</c:when>
    <c:when test="${param.status eq 3}">서울시승인반려</c:when>
    <c:when test="${param.status eq 4}">담당변호사배정</c:when>
    <c:when test="${param.status eq 5}">자문완료</c:when>
    <c:when test="${param.status eq 6}">취소</c:when>
</c:choose>



