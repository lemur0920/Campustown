<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>

<!-- Magnific popup -->
<link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
<script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
<script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.css">
<script src="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.js"></script>

<script>
	$(document).ready(function(){ //DOM이 준비되고
		$("#file").click(function(){
			if($("#file_delete_img").length > 0) {
				alert("기존파일을 삭제후 추가해주세요.");
				return false;
			}
		});

		$("#file_delete_img").click(function() {
			if(${lawyerAssingId.lawyerId ne sessionScope.currentAdmin.adminId}){
				alert("해당 자문에 배정 되지 않았습니다.");
				return false;
			}

			$.ajax({
				type : 'POST'
				, url : '${ADMIN_PATH}/counsel/legal_counsel/deleteFile/${lawyerFile.fileId}.do'
				, dataType: 'json'
				, data: $('#lawyerCounselFrom').serialize()
				, success : function(data) {
					$("#attachFileDiv").remove();
				}
			});
		});

		<%--$(lawyerCounselFrom).submit(function(){ //문서의 모든 form이 submit될때--%>
		<%--	if(${lawyerAssingId.lawyerId ne sessionScope.currentAdmin.adminId}){--%>
		<%--		alert("해당 자문에 배정 되지 않았습니다.");--%>
		<%--		return false;--%>
		<%--	}--%>
		<%--	alert("등록되었습니다.")--%>
		<%--});--%>

		CKEDITOR.replace( 'contents',{
			filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=content&moduleId=${contentForm.contentId}&moduleSub=content'
		} );
	});

	function fn_downFile(fileId, uuId){
		alert("파일 다운::"+fileId+"dddd"+uuId);
		window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+fileId+"&fileSn="+uuId+"'/>");
	}

	function fn_submit(index) {
		if(${lawyerAssingId.lawyerId ne sessionScope.currentAdmin.adminId}){
			alert("해당 자문에 배정 되지 않았습니다.");
			return false;
		}
		$('#statusCodeValue').val(index);
		alert("등록되었습니다.")
	}

</script>

<!-- Left Sidebar -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

<!-- Start right Content here -->
<div class="content-page">
	<!-- Start content -->
	<div class="content">

		<!-- Top Bar -->
		<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />

		<div class="page-content-wrapper ">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<div class="card m-b-30 search-area">
							<div class="card-body">
								<div class="edit-table table">
									<table>
										<colgroup>
											<col style="width: 10%;">
											<col style="width: 60%;">
											<col style="width: 10%;">
											<col style="width: 10%;">
										</colgroup>
										<tbody>
										<tr>
											<th scope="row">분야</th>
											<td colspan="3">${view.realm}</td>
										</tr>
										<tr>
											<th scope="row">제목</th>
											<td colspan="3">${view.title}</td>
										</tr>
										<tr>
											<th scope="row">등록일</th>
											<td><fmt:formatDate value="${view.regDate}" pattern="yyyy-MM-dd"/></td>
										</tr>
										<tr>
											<th scope="row">작성자</th>
											<td>${view.regName}</td>
										</tr>
<%--										<tr>--%>
<%--											<th scope="row">신청자</th>--%>
<%--											<td>${view.regName} (${view.teamName})</td>--%>
<%--											<th scope="row">연락처</th>--%>
<%--											<td>${view.}</td>--%>
<%--										</tr>--%>
										<tr>
<%--										<th scope="row">내용</th>--%>
<%--											<td colspan="4">--%>
<%--												<div class="ed-table-con">${view.contents}</div>--%>
<%--											</td>--%>
<%--										</tr>--%>
										<th scope="row" class="">
											<label for="contents">내용</label>
										</th>
										<td>
											<textarea style="width:100%;" rows="5" id="contents" name="contents">${view.contents}</textarea>
										</td>
										</tbody>
									</table>

									<table>
										<colgroup>
											<col style="width: 15%;">
											<col style="width: 75%;">
										</colgroup>
										<tbody>
										<tr>
											<th scope="row">자문 첨부파일</th>
											<td>
												<a href="${APP_PATH}/file/download/uu/${viewFile.fileUUID} ">${viewFile.fileOriginalName}</a>
											</td>
										</tr>
										<tr>
											<th scope="row">상태</th>
											<td>
												<c:import url="statusName.jsp" charEncoding="UTF-8">
													<c:param name="status" value="${view.status}"></c:param>
												</c:import>
											</td>
										</tr>
										</tbody>
									</table>

					<form id="lawyerCounselFrom" name="lawyerCounselFrom" action="${ADMIN_PATH}/counsel/legal_counsel/lawyer/lcsave/${lawyerAssingId.seq}.do" method="post" enctype="multipart/form-data">
						<input type="hidden" name="legalCounseSeq" id="legalCounseSeq" value="${view.id}">
						<input type="hidden" name="statusCodeValue" id="statusCodeValue">
									<table>
										<colgroup>
											<col style="width: 10%;">
											<col style="width: 75%;">
										</colgroup>
										<tbody>
										<tr>
											<th scope="row">답변</th>
											<td>
												<form method="post">
													<textarea style="width:100%;" rows="5" id="lawyerCounsel" name="lawyerCounsel">${lawyerAssingId.lawyerCounsel}</textarea>
												</form>
											</td>
										</tr>
										<tr>
											<th scope="row">답변 첨부파일</th>
											<td>
												<c:if test="${!empty lawyerFile}">
												<div id="attachFileDiv">
													<a href="${APP_PATH}/file/download/uu/${lawyerFile.fileUUID}" title="다운로드" class="dl_file">${lawyerFile.fileOriginalName} (${lawyerFile.fileSizeString})</a>
													&nbsp;
													<a href="#none"><img src="<c:url value='/design/common/images/asset/btn_del.png' />" id="file_delete_img" class="cursor" alt="파일다운로드"></a>
													<br/><br/>
												</div>
												</c:if>
												<input type="file" id="file" name="file">
											</td>
										</tr>
										</tbody>
									</table>

								<div class="button-items text-right">
									<input type="submit" class="btn btn-secondary" value="답변저장" onclick="fn_submit(0)">
									<input type="submit" class="btn btn-secondary" value="답변취소" onclick="fn_submit(1)">
									<a href="${ADMIN_PATH}/counsel/legal_counsel/list.do" class="btn btn-secondary " role="button">목록</a>
								</div>
					</form>
							</div>
						</div>
					</div> <!-- end col -->

				</div> <!-- end row -->

			</div><!-- container -->


		</div> <!-- Page content Wrapper -->

	</div> <!-- content -->

	<footer class="footer">
		© 2018 Upcube - Crafted with <i class="mdi mdi-heart text-danger"></i> by Themesdesign.
	</footer>

</div>
<!-- End Right content here -->

</div>



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />