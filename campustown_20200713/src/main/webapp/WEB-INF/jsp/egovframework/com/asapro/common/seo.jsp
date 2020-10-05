<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%
/*
 - 검색엔진 최적화 관련 메타태그등을 모아서 출력
 - 로고를 일단 그냥 넣어놨는데 facebook share 하는 경우 200x200 사이즈 로고가 별도로 필요하다.
 - og.image와 image_src의 이미지rul은 별도 제작한 200x200x이미지의 url로 해 줄것.
*/
%>
<meta property="og:type" content="website">
<meta property="og:site_name" content="${currentSite.siteName}">
<meta property="og:title" content="${seo.title2}${currentSite.siteName}">
<meta property="og:url" content="<%= AsaproUtils.getCurrentUrl(request, true) %>">
<meta property="og:description" content="${currentSite.siteName},${seo.description}">
<meta property="og:image" content="<%= AsaproUtils.getSchemeDomainPort(request) %>${CONTEXT_PATH }/design/theme/${theme}/images/logo-200x200.jpg">
<meta property="og:image:height" content="200">
<meta property="og:image:width" content="200">
<meta name="name" content="${seo.title2}">
<meta name="keywords" content="${currentSite.siteName},${seo.title}${seo.keywords}" />
<meta name="description" content="${currentSite.siteName},${seo.description}" />
<meta name="robots" content="index,follow">
<meta name="googlebot" content="index,follow,snippet,archive">
<meta name="image_src" content="<%= AsaproUtils.getSchemeDomainPort(request) %>${CONTEXT_PATH }/design/theme/${theme}/images/logo-200x200.jpg">