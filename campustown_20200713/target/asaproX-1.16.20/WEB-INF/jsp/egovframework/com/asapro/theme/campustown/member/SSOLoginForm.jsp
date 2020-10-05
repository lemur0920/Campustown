<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>

<script type="text/javascript" src="${CONTEXT_PATH }/design/common/js/jquery.min.js"></script>
<script>

jQuery(function($){
	$('[name=userForm]').submit();
});

</script>

<form name="userForm" method="post" action="https://www.seoul.go.kr/member/userlogin/loginCheck.do" >
	<input type="hidden" name="a" value="UserLoginCheckApp">
	<input type="hidden" name="div" value="mem">
	<input type="hidden" name="SITE_GB" value="GB103">
	<input type="hidden" name="refresh_url" value="<%=AsaproUtils.getSchemeDomainPort(request) %>${APP_PATH }/member/SSOLoginProc?returnUrl=${userForm.returnUrl }"> 
	<input type="hidden" name="isExternal" value="Y">
</form>
