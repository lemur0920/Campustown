<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	$(legalCounselForm).submit(function(){ //문서의 모든 form이 submit될때
		if($('#realm').val() == ""){
			alert("분야를 작성해 주세요 ")
			return false;
		}else if($('#title').val() == ""){
			alert("제목을 작성해 주세요 ")
			return false;
		}else if($('#contents').val() == ""){
			alert("내용을 작성해 주세요 ")
			return false;
		}
	alert("등록되었습니다.")

		// if(!$('#id').attr('value') || !$('#password').attr('value')){ // id가 id인 요소의 vlue 또는 id가 password인 value가 없다면
		// 	alert('password'); // password라고 경고
		// 	return false; // 폼은 전송시키지않고 false반환
		// }

		//alert($('#idea').checkbox.val());
	});
});

</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
<form name="legalCounselForm" id="legalCounselForm" action="${APP_PATH}/counsel/legal_counsel/save" method="post" enctype="multipart/form-data">
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
<%--						<span class="mob-s  mr15">--%>
							<input type="text" id="realm" name="realm" class="input-100p">
<%--						</span>--%>
<%--						<label for="lab04">아이디어</label>--%>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb04">제목</label> <span class="fc-red">*</span></th>
					<td>
						<input type="text" title="제목" id="title" name="title" class="input-100p">
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb06">법률 자문 신청 내용</label> <span class="fc-red">*</span></th>
					<td>
						<textarea cols="30" rows="10" id="contents" name="contents"></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb10">첨부파일</label></th>
					<td>
						<input type="file" id="file" name="file">
						<img src="${CONTEXT_PATH }/design/common/images/asset/download_icon.png" style="width: 20px;margin-right: 10px;"><a href="${APP_PATH}/file/download/uu/8a6cf24192cc465693737a76ec783abc" class="btn btn-outline-primary waves-effect waves-light btn-sm">자문서 양식 다운로드</a>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="btn-area txc">
			<a href="${APP_PATH}/counsel/legal_counsel/list" class="btn02 fc-bg-gray">목록으로</a>
			<input type="submit" class="btn02 fc-bg-blue" value="법률자문 신청하기">
		</div>
	</div>
	</div>
</form>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />