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
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	function saveData() {
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
<form name="makeCounselForm" id="makeCounselForm" action="${APP_PATH}/counsel/make_counsel/save" method="post" enctype="multipart/form-data">
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
								<input type="checkbox" id="idea_${vs.index}" name="idea" value="${item.codeValue}">
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
								<input type="checkbox" id="productDevelopment_${vs.index}" name="productDevelopment" value="${item.codeValue}">
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
								<input type="checkbox" id="prototypeProduct_${vs.index}" name="prototypeProduct" value="${item.codeValue}">
								<label for="${item}">${item.codeName}</label></span>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="title">개발사 및 아이템 명</label> <span class="fc-red">*</span></th>
					<td>
						<input type="text" title="이 항목은 필수 입니다." id="title" name="title" class="input-100p" maxlength="40" required>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="itemIntroduction">아이템 한줄 소개</label> <span class="fc-red">*</span></th>
					<td>
						<input type="text" title="이 항목은 필수 입니다." id="itemIntroduction" name="itemIntroduction" class="input-100p" maxlength="50" required>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="productOverview">제품개요 및 주요기능</label> <span class="fc-red">*</span></th>
					<td>
						<textarea cols="30" rows="10" title="이 항목은 필수 입니다." id="productOverview" name="productOverview" required></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="process">진행과정</label></th>
					<td>
						<textarea cols="30" rows="10" id="process" name="process"></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="productDetail">세부기능 및 제품 스펙</label></th>
					<td>
						<textarea cols="30" rows="10" id="productDetail" name="productDetail"></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th-txl"><label for="lb10">첨부파일</label></th>
					<td>
						<input type="file" id="file" name="file">
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="btn-area txc">
			<a href="<c:url value='${APP_PATH}/counsel/make_counsel/list'/>" class="btn02 fc-bg-gray">목록으로</a>
			<input type="button" onclick="saveData(); return false;" value="제품제작 의뢰하기" class="btn02 fc-bg-blue">
<%--			<a href="#" oㅌnclick="" class="btn02 fc-bg-blue">제품제작 의뢰하기</a>--%>
		</div>
	</div>
</div>
</form>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />