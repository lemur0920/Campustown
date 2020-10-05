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
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
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
			, url : '${APP_PATH}/counsel/make_counsel/deleteFile/${fileInfo.fileId}'
			, dataType: 'json'
			, data: $('#makeCounselForm').serialize()
			, success : function(data) {
				$("#attachFileDiv").remove();
			}
		});
	});
});

function modifyData() {
	var objWrite1 = document.getElementsByName("idea");
	var objWrite1Cnt = 0;
	for(var i=0;i<objWrite1.length;i++){
		if(objWrite1[i].checked == false){
			objWrite1Cnt++;
		}
	}
	if(objWrite1.length == objWrite1Cnt) {
		alert("아이디어를 선택 해 주세요.");
		return false;
	}

	var objWrite2 = document.getElementsByName("productDevelopment");
	var objWrite2Cnt = 0;
	for(var i=0;i<objWrite2.length;i++){
		if(objWrite2[i].checked == false){
			objWrite2Cnt++;
		}
	}
	if(objWrite2.length == objWrite2Cnt) {
		alert("제품개발을 선택 해 주세요.");
		return false;
	}

	var objWrite3 = document.getElementsByName("prototypeProduct");
	var objWrite3Cnt = 0;
	for(var i=0;i<objWrite3.length;i++){
		if(objWrite3[i].checked == false){
			objWrite3Cnt++;
		}
	}
	if(objWrite3.length == objWrite3Cnt) {
		alert("시제품을 선택 해 주세요.");
		return false;
	}

	$("#makeCounselForm").validate();

	$("#makeCounselForm").submit();
}


</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
<form name="makeCounselForm" id="makeCounselForm" action="${APP_PATH}/counsel/make_counsel/update" method="post" enctype="multipart/form-data">
	<input type="hidden" name="makeCounselSeq" value="${makeCounselSeq}"/>
	${SECURITY_TOKEN_TAG}
	<div class="area">
		<p class="fc-blue mb20">스타트업을 위한 맞춤형 시제품 제작을 의뢰해보세요</p>
		<div class="table-con style2 mb30">
			<table>
				<caption>시제품 제작 및 자문홍보</caption>
				<colgroup>
					<col style="width: 20%">
					<col style="width: auto">
				</colgroup>
				<tbody>
				<tr>
					<th scope="row" class="th-txl"><label for="idea">아이디어</label> <span class="fc-red">*</span></th>
					<td>
						<c:forEach items="${Idea}" var="item" varStatus="vs">
							<span class="mob-s  mr15">
								<input type="checkbox" id="idea_${vs.index}" name="idea" value="${item.codeValue}"
									<c:forEach var="detail" items="${detailList}">
										<c:if test="${detail.type == 'idea' && item.codeValue == detail.makeCounselRealm}">
											checked
										</c:if>
									</c:forEach>
								>
								<label for="${item}">${item.codeName}</label>
							</span>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="productDevelopment">제품개발</label> <span class="fc-red">*</span></th>
					<td>
						<c:forEach items="${Prod}" var="item" varStatus="vs">
							<span class="mob-s  mr15">
								<input type="checkbox" id="productDevelopment_${vs.index}" name="productDevelopment" value="${item.codeValue}"
									<c:forEach var="detail" items="${detailList}">
										<c:if test="${detail.type == 'prod' && item.codeValue == detail.makeCounselRealm}">
									   		checked
										</c:if>
									</c:forEach>
								>
								<label for="${item}">${item.codeName}</label>
							</span>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="prototypeProduct">시제품</label> <span class="fc-red">*</span></th>
					<td>
						<c:forEach items="${Prot}" var="item" varStatus="vs">
							<span class="mob-s  mr15">
								<input type="checkbox" id="prototypeProduct_${vs.index}" name="prototypeProduct" value="${item.codeValue}"
									<c:forEach var="detail" items="${detailList}">
										<c:if test="${detail.type == 'prot' && item.codeValue == detail.makeCounselRealm}">
											checked
										</c:if>
									</c:forEach>
								>
								<label for="${item}">${item.codeName}</label></span>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="title">개발사 및 아이템 명</label> <span class="fc-red">*</span></th>
					<td>
						<input type="text" title="이 항목은 필수 입니다." id="title" name="title" class="input-100p" maxlength="40" value="${view.title}" required>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="itemIntroduction">아이템 한줄 소개</label> <span class="fc-red">*</span></th>
					<td>
						<input type="text" title="이 항목은 필수 입니다." id="itemIntroduction" name="itemIntroduction" class="input-100p" maxlength="50" value="${view.itemIntroduction}" required>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="productOverview">제품개요 및 주요기능</label> <span class="fc-red">*</span></th>
					<td>
						<textarea cols="30" rows="10" title="이 항목은 필수 입니다." id="productOverview" name="productOverview" required><c:out value="${view.productOverview}"/></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="process">진행과정</label></th>
					<td>
						<textarea cols="30" rows="10" id="process" name="process"><c:out value="${view.process}"/></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="productDetail">세부기능 및 제품 스펙</label></th>
					<td>
						<textarea cols="30" rows="10" id="productDetail" name="productDetail"><c:out value="${view.productDetail}"/></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb10">첨부파일</label></th>
					<td>
						<c:if test="${fileCount > 0}">
							<div id="attachFileDiv">
							<a href="${APP_PATH}/file/download/uu/${fileInfo.fileUUID}" title="다운로드" class="dl_file">${fileInfo.fileOriginalName} (${fileInfo.fileSizeString})</a>
							&nbsp;
							<a href="#none"><img src="<c:url value='/design/common/images/asset/btn_del.png' />" id="file_delete_img" class="cursor" alt="<spring:message code="title.attachedFileDelete" />"></a>
							<br/><br/>
							</div>
						</c:if>
						<input type="file" id="file" name="file">
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="btn-area txc">
			<a href="<c:url value='${APP_PATH}/counsel/make_counsel/list'/>" class="btn02 fc-bg-gray">목록으로</a>
			<input type="button" onclick="modifyData(); return false;" value="제품제작 의뢰수정" class="btn02 fc-bg-blue">
		</div>
	</div>
</div>
</form>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />