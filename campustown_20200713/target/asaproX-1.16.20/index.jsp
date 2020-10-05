<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="egovframework.com.asapro.support.Constant"%>
<%
//response.sendRedirect(Constant.APP_PATH + "/" + "adbro" + "/home");
//response.sendRedirect(Constant.APP_PATH + "/" + Constant.MAIN_SITE_DISPLAY_ID + "/home");
pageContext.forward(Constant.APP_PATH + "/" + Constant.MAIN_SITE_DISPLAY_ID + "/home");
%>