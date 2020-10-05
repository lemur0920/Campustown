<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${param.status eq 0}"><span class="apply">법률자문 신청</span></c:when>
    <c:when test="${param.status eq 1}"><span>캠퍼스타운 반려</span></c:when>
    <c:when test="${param.status eq 2}"><span>서울시 승인대기</span></c:when>
    <c:when test="${param.status eq 3}"><span>서울시 승인반려</span></c:when>
    <c:when test="${param.status eq 4}"><span>담당변호사 배정</span></c:when>
    <c:when test="${param.status eq 5}"><span>자문완료</span></c:when>
    <c:when test="${param.status eq 6}"><span>취소</span></c:when>
</c:choose>



