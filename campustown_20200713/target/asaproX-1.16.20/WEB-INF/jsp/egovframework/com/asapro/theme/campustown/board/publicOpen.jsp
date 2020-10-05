<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- 공공누리 -->
<c:if test="${boardConfig.bcSupportNuri }">
	<c:if test="${boardArticle.baNuri != null && boardArticle.baNuri != '' }">
		<!--1024-->
		<div class="codeView04" style="position:relative;margin:0;padding:0;margin-top:100px;background:#fff;border:1px solid #dbdbdb;padding:30px 15px 30px 250px;font-size:12px;color:#292929;font-weight:bold;">
			<img src="${CONTEXT_PATH }/design/common/images/publicOpen/${boardArticle.baNuri }.jpg" style="position:absolute;left:20px;top:12%;vertical-align:middle;max-width:219px;height:54px;" alt="${asapro:codeDescription(boardArticle.baNuri, bcNuriCodeList) }"/>
			<span>${currentSite.siteName }</span> 에서 창작한 본 저작물은 "공공누리"&nbsp;<a href="http://www.kogl.or.kr/info/licenseType${fn:substringAfter(boardArticle.baNuri, 'NURI_TYPE_0')}.do" target="_blank">${asapro:codeDescription(boardArticle.baNuri, bcNuriCodeList) }</a>&nbsp;조건에 따라 이용할 수 있습니다.
		</div>
		<!--1024-->
	</c:if>
</c:if>
<!-- 공공누리 끝-->