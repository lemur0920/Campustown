<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><script>
var GLOBAL = {
		APP_PATH:'${APP_PATH}',
		CONTEXT_PATH:'${CONTEXT_PATH}',
		<%--ADMIN_PATH:'${ADMIN_PATH}',--%>
		API_PATH:'${API_PATH}',
		siteId:'${currentSite.siteId}',
		menuId:'${currentMenu.menuId}',
		SECURITY_TOKEN_NAME:'${SECURITY_TOKEN_NAME}',
		SECURITY_TOKEN:'${SECURITY_TOKEN}',
		SECURITY_ASYNC_TOKEN_NAME:'${SECURITY_ASYNC_TOKEN_NAME}',
		SECURITY_ASYNC_COOKIE_NAME:'${SECURITY_ASYNC_COOKIE_NAME}',
		<%--COMMENT_USE_MODERATE:${commentConfigMap.use_moderate},--%>
		SITE_LOCALE:'${currentSite.siteLocale}',
		MOBILE:${userAgent.getOperatingSystem().getDeviceType() == 'MOBILE'}
		};
</script>
