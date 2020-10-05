<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.js"></script>
<script src="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.css">

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
			, url : '${ADMIN_PATH}/counsel/make_counsel/deleteFile/${fileInfo.fileId}.do'
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

<!-- Left Sidebar -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

<form name="makeCounselForm" id="makeCounselForm" action="${ADMIN_PATH}/counsel/make_counsel/update.do" method="post" enctype="multipart/form-data">
	<input type="hidden" name="makeCounselSeq" value="${makeCounselSeq}"/>

	<!-- Start right Content here -->
	<div class="content-page">
		<!-- Start content -->
		<div class="content">

			<!-- Top Bar -->
			<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />

			<div class="page-content-wrapper ">

				<div class="container-fluid">

					<!-- location -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />

					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 입력폼 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-15">
								<div class="card-body">

									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />

									<!-- info -->
									<div class="p-3 mb-3 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 시제품 제작 및 자문 의뢰 상세 </div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 col-form-label">아이디어<span class="text-danger">*</span></label>
										<div class="col-sm">
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
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 col-form-label">제품개발<span class="text-danger">*</span></label>
										<div class="col-sm">
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
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 col-form-label">시제품<span class="text-danger">*</span></label>
										<div class="col-sm">
											<c:forEach items="${Prot}" var="item" varStatus="vs">
												<span class="mob-s  mr15">
													<input type="checkbox" id="prototypeProduct_${vs.index}" name="prototypeProduct" value="${item.codeValue}"
														<c:forEach var="detail" items="${detailList}">
															<c:if test="${detail.type == 'prot' && item.codeValue == detail.makeCounselRealm}">
																   checked
															</c:if>
														</c:forEach>
													>
													<label for="${item}">${item.codeName}</label>
												</span>
											</c:forEach>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 col-form-label">개발사 및 아이템 명<span class="text-danger">*</span></label>
										<div class="col-sm">
											<input type="text" title="개발사 및 아이템 명" id="title" name="title" class="form-control col-sm-6" maxlength="40" value="${view.title}" required>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 col-form-label">아이템 한줄 소개<span class="text-danger">*</span></label>
										<div class="col-sm">
											<input type="text" title="아이템 한줄 소개 입력" id="itemIntroduction" name="itemIntroduction" class="form-control col-sm-6" maxlength="50" value="${view.itemIntroduction}" required>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 col-form-label">제품개요 및 주요기능<span class="text-danger">*</span></label>
										<div class="col-sm">
											<textarea cols="30" rows="10" id="productOverview" name="productOverview" class="form-control col-sm-8" required><c:out value="${view.productOverview}"/></textarea>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 col-form-label">진행과정</label>
										<div class="col-sm">
											<textarea cols="30" rows="10" id="process" name="process" class="form-control col-sm-8"><c:out value="${view.process}"/></textarea>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 col-form-label">세부기능 및 제품 스펙</label>
										<div class="col-sm">
											<textarea cols="30" rows="10" id="productDetail" name="productDetail" class="form-control col-sm-8"><c:out value="${view.productDetail}"/></textarea>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 col-form-label">첨부파일</label>
										<div class="col-sm">
											<c:if test="${fileCount > 0}">
												<div id="attachFileDiv">
													<a href="${ADMIN_PATH}/file/download/uu/${fileInfo.fileUUID}" title="다운로드" class="dl_file">${fileInfo.fileOriginalName} (${fileInfo.fileSizeString})</a>
													&nbsp;
													<a href="#none"><img src="<c:url value='/design/common/images/asset/btn_del.png' />" id="file_delete_img" class="cursor" alt="파일다운로드"></a>
													<br/><br/>
												</div>
											</c:if>
											<input type="file" id="file" name="file">
										</div>
									</div>

									<div class="form-group">
										<button onclick="modifyData(); return false;" class="btn btn-primary waves-effect waves-light">수정</button>
										<a href="${ADMIN_PATH}/counsel/make_counsel/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- //입력폼 -->

					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
				</div><!-- container -->


			</div> <!-- Page content Wrapper -->

		</div> <!-- content -->

	</div>
	<!-- End Right content here -->

	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />


</form>