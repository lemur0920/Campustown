<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.Date"%>
<%
Date now = new Date();
%>
<fmt:formatDate value="<%= now %>" pattern="yyyyMMdd" var="now"/>
<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){

});
$(document).ready(function(){ //DOM이 준비되고
	$("#file").click(function(){
		if($("#file_delete_img").length > 0) {
			alert("기존파일을 삭제후 추가해주세요.");
			return false;
		}
	});

	$("#file_delete_img").click(function() {
		$.ajax({
			type : 'POST'
			, url : '${APP_PATH}/counsel/legal_counsel/deleteFile/${viewFile.fileId}'
			, dataType: 'json'
			, data: $('#legalCounselForm').serialize()
			, success : function(data) {
				$("#attachFileDiv").remove();
			}
		});
	});

	// $(legalCounselForm).submit(function(){ //문서의 모든 form이 submit될때
	// });
});

function go_list() {
	var referrer =  document.referrer;

	if(referrer != null && referrer.match("/mypage")) {
		location.href = '${APP_PATH}/counsel/legal_counsel/mypage/list';
	} else {
		location.href = '${APP_PATH}/counsel/legal_counsel/list';
	}

}
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
<form name="legalCounselForm" id="legalCounselForm" action="${APP_PATH}/counsel/legal_counsel/save" method="post">
	${SECURITY_TOKEN_TAG}
	<div class="area">
		<p class="fc-blue mb20">법률자문은 캠퍼스타운에서 검토 후, 지정된 변호사와 상담이 진행됩니다.</p>
		<div class="table-con style2 mb30">
			<table>
				<caption>법률자문</caption>
				<colgroup>
					<col style="width: 20%">
					<col style="width: auto">
				</colgroup>
				<tbody>
				<tr>
					<th scope="row" class="th-txl"><label for="lb01">분야</label> <span class="fc-red">*</span></th>
					<td>
						<span class="mob-s  mr15"><label for="lab04">${view.realm}</label></span>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb05">제목</label> <span class="fc-red">*</span></th>
					<td>
						<span class="mob-s  mr15"><label for="lab05">${view.title}</label></span>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb06">법률 자문 신청 내용</label> <span class="fc-red">*</span></th>
					<td>
						<textarea cols="30" rows="10" id="contents" name="contents" readonly>${view.contents}</textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb10">첨부파일</label></th>
					<td>
						<c:if test="${!empty viewFile}">
							<div id="attachFileDiv">
								<c:if test="${cu.userName == view.regName}">
								<a href="${APP_PATH}/file/download/uu/${viewFile.fileUUID}" title="다운로드" class="dl_file">${viewFile.fileOriginalName} (${viewFile.fileSizeString})</a>
								&nbsp;
								<a href="#none"><img src="<c:url value='/design/common/images/asset/btn_del.png' />" id="file_delete_img" class="cursor" alt="<spring:message code="title.attachedFileDelete" />"></a>
								</c:if>
								<c:if test="${cu.userName != view.regName}">
									<div>${viewFile.fileOriginalName} (${viewFile.fileSizeString})</div>
								</c:if>
								<br/><br/>
							</div>
						</c:if>
						<input type="file" id="file" name="file">
					</td>
				</tr>
				
				</tbody>
			</table>
		</div>
		<!-- 
		<div class="btn-area txc">
			<a href="#n" class="btn02 fc-bg-gray" onclick="go_list(); return false;">목록으로</a>
		</div>
		 -->
	</div>
</form>

<br/><br/>

<c:if test="${not empty view.lawyerId }">
	<div class="table-con style2 mb30">
		<table>
			<caption>답글</caption>
			<colgroup>
				<col style="width: 20%">
				<col style="width: auto">
			</colgroup>
			<tbody>
				<tr>
					<td scope="row" class="th-txl" colspan="2"><h4 class="title2 mb25" style="margin-bottom:0px;">답글</h4></td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb10">담당자</label></th>
					<td>
							${view.lawyerId }			
					</td>
				</tr>
				
				<tr>
					<th scope="row" class="th-txl"><label for="lb10">답변</label></th>
					<td>
							${view.lawyerCounsel }				
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl">답변 첨부파일</th>
					<td>
						<c:if test="${!empty lawyerFile}">
							<div id="attachFileDiv">
								<a href="${APP_PATH}/file/download/uu/${lawyerFile.fileUUID}" title="다운로드" class="dl_file">${lawyerFile.fileOriginalName} (${lawyerFile.fileSizeString})</a>
							</div>
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</c:if>

<div class="btn-area txc">
	<a href="#n" class="btn02 fc-bg-gray" onclick="go_list(); return false;">목록으로</a>
</div>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />