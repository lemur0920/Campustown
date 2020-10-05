<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%Date now = new Date();%>
<fmt:formatDate value="<%= now %>" pattern="yyyy-MM-dd" var="now"/>

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
		$(legalCounselFrom).submit(function(){ //문서의 모든 form이 submit될때
			if($('#realm').val() == ""){
				alert("분야를 작성해 주세요 ")
				return false;
			}
			alert("등록되었습니다.")
		});

		CKEDITOR.replace( 'contents',{
			filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=content&moduleId=${contentForm.contentId}&moduleSub=content'
		} );

	});
</script>

<!-- Left Sidebar -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

<!-- Start right Content here -->
<div class="content-page">
	<!-- Start content -->
	<div class="content">

		<!-- Top Bar -->
		<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />

		<form name="legalCounselFrom" id="legalCounselFrom" action="${ADMIN_PATH}/counsel/legal_counsel/save.do" method="post" enctype="multipart/form-data">


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
											<col style="width: 75%;">
											<col style="width: 10%;">
											<col style="width: 10%;">
										</colgroup>
										<tbody>
										<tr>
											<th scope="row">분야</th>
											<td ><input style="width:100%;" type="text" id="realm" name="realm" maxlength="10"></td>
										</tr>
										<tr>
											<th scope="row">제목</th>
											<td ><input style="width:100%;" type="text" id="title" name="title" maxlength="20"></td>
										</tr>
										<tr>
											<th scope="row">등록일</th>
											<td>${now}</td>
										</tr>
										<tr>
											<th scope="row">작성자</th>
											<td>${sessionScope.currentAdmin.adminName}</td>
										</tr>
										<tr>
<%--										<th scope="row">내용</th>--%>
<%--											<td colspan="4">--%>
<%--												<div class="ed-table-con" id="contents" name="contents"></div>--%>
<%--											</td>--%>
<%--										</tr>--%>
											<th scope="row">
												<label for="contents">내용</label>
											</th>
											<td>
												<textarea style="width:100%;" rows="5" id="contents" name="contents"></textarea>
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
												<input type="file" id="file" name="file" />
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


								<div class="button-items text-right">
									<input type="submit" class="btn btn-secondary" value="답변저장">
									<a href="${ADMIN_PATH}/counsel/legal_counsel/list.do" class="btn btn-secondary " role="button">목록</a>
								</div>
							</div>


					</div> <!-- end col -->

				</div> <!-- end row -->
					</form>
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