<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<c:if test="${not empty fileInfoUploadResult}"><script>alert('<spring:message code="board.article.fileInfoUploadResult"></spring:message>');</script></c:if>

<!-- 관리자쪽 파일첨부 jquery를 사용하는걸로 바꿔요~ -->
<%--<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/fms/EgovMultiFile.js'/>" ></script> --%>

<script>
jQuery(function($){
	//저장
	$('#content').on('click','.insertBtn', function(e){
		e.preventDefault();
		
		//이메일 체크
		if($("#baEmail").val() != '' && $("#baEmail").val() != null){
			var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
			if(!emailRule.test($("#baEmail").val())) {
				//alert("이메일 형태가 아닙니다.");
				$('#baEmailerrors').remove();
				$('#baEmail').closest('td').append('<div id="baEmailerrors" style="color: #ff0000;">이메일 형태가 아닙니다.</div>');
				$("#baEmail").focus();
	            return false;
			}else{
				$('#baEmailerrors').remove();
			}
		}else{
			//alert("이메일은 필수 값입니다.");
			$('#baEmailerrors').remove();
			$('#baEmail').closest('td').append('<div id="baEmailerrors" style="color: #ff0000;">이메일입력은 필수 입니다.</div>');
			$("#baEmail").focus();
			return false;
		}
		
		//휴대폰번호 체크
		var hp = $('#baHp').val();
		if(hp == null || hp == ''){
			$('#baHperrors').remove();
			$('#baHp').closest('td').append('<div id="baHperrors" style="color: #ff0000;">휴대폰번호 입력은 필수 입니다.</div>');
			$("#baHp").focus();
			return false;
		}else{
			$('#baHperrors').remove();
		}
		
		//카테고리 선택체크
		var cat1 = $('#baCategory1 option:selected').val();
		if(cat1 == null || cat1 == '' ){
			//alert('카테고리 선택은 필수 입니다.');
			$('#baCategory1errors').remove();
			$('#baCategory1').closest('td').append('<div id="baCategory1errors" style="color: #ff0000;">카테고리 선택은 필수 입니다.</div>');
			$("#baCategory1").focus();
			return false;
		} else{
			$('#baCategory1errors').remove();
		}
		
		$('#boardArticleForm').submit();
	});
});
</script>
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- content -->
			<div id="content">
				<c:if test="${not empty message}">
					<div class="message">
						${message}
					</div><!-- //.message -->
				</c:if>
				
				<!-- board form -->
				<div class="table tb_1">
				<!-- alert maeeage -->
				<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					<form:form modelAttribute="boardArticleForm" action="${actionUrl}" method="post" enctype="multipart/form-data">
						${SECURITY_TOKEN_TAG}
						<form:hidden path="bcId" />
						
						<table>
							<caption>${boardConfig.bcName }</caption>
							<colgroup>
								<col class="col_1">
								<col style="width:*;">
								<col class="col_1">
								<col class="col_2">
							</colgroup>
							<tbody>
								<tr>
									<th><form:label path="baTitle">제목</form:label> <span class="required">*</span></th>
									<td colspan="3" class="tlt">
										<form:input path="baTitle" title="제목을 입력하세요"/>
										<form:errors path="baTitle" element="div" cssClass="form-error" style="color: #ff0000;"/>
									</td>
								</tr>
								<tr>
									<c:choose>
										<c:when test="${sessionScope.currentUser.userRole.roleCode eq 'ROLE_GUEST'}">
											<tr>
												<th><form:label path="baGuestName">작성자</form:label> <span class="required">*</span></th>
												<td class="tlt">
													<form:input path="baGuestName" maxlength="30" /> 
													<form:errors path="baGuestName" element="div" cssClass="form-error" style="color: #ff0000;" />
												</td>
												
												<th><form:label path="baEmail">이메일</form:label> <span class="required">*</span></th>
												<td class="tlt">
													<form:input path="baEmail" />
													<form:errors path="baEmail" element="div" cssClass="form-error" style="color: #ff0000;" />
												</td>
											</tr>
											<tr>
												<th>
													<form:label path="baGuestPassword">본인확인비밀번호</form:label> <c:if test="${formMode eq 'INSERT'}"><span class="required">*</span></c:if>
												</th>
												<td class="tlt">
													<form:password path="baGuestPassword" maxlength="20" />
													<form:errors path="baGuestPassword" element="div" cssClass="form-error" style="color: #ff0000;" />
													<c:if test="${formMode eq 'UPDATE'}">
														<div>- 본인확인용 비밀번호 변경시 입력해 주세요.(최대20자. 공란시 기존비밀번호가 유지됩니다.)</div>
													</c:if>
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<th>
													<label for="GuestName">작성자</label>
												</th>
												<td class="tlt">
													<c:if test="${formMode eq 'INSERT'}">
														<span id="GuestName">${sessionScope.currentUser.userName}</span>
														<input type="hidden" name="baGuestName" value="${sessionScope.currentUser.userName}" />
													</c:if>
													<c:if test="${formMode eq 'UPDATE'}">
														<c:if test="${empty boardArticleForm.member}">
															<span id="baGuestName">${boardArticleForm.baGuestName}</span>
															<input type="hidden" name="baGuestName" value="${boardArticleForm.baGuestName}" />
														</c:if>
														<c:if test="${not empty boardArticleForm.member}"><span id="baGuestName">${boardArticleForm.member.memberName}</span></c:if>
													</c:if>
												</td>
												<th class="tlt"><form:label path="baEmail">이메일</form:label> <span class="required">*</span></th>
												<td>
													<c:if test="${formMode eq 'INSERT'}">
														<form:input path="baEmail" value="${sessionScope.currentUser.userEmail}" />
													</c:if>
													<c:if test="${formMode eq 'UPDATE'}">
														<form:input path="baEmail" />
													</c:if>
													<form:errors path="baEmail" element="div" cssClass="form-error" />
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tr>
								<tr>
									<th>
										<form:label path="baHp">휴대폰 번호</form:label> <span class="required">*</span>
									</th>
									<td class="tlt">
										<form:input path="baHp" />
										<form:errors path="baHp" element="div" cssClass="form-error" style="color: #ff0000;" />
									</td>
								</tr>
								<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
									<tr>
										<th>
											<form:label path="baCategory1">${boardConfig.bcCategory1Name}</form:label> <span class="required">*</span>
										</th>
										<td class="tlt">
											<form:select path="baCategory1">
												<form:option value="">선택</form:option>
												<form:options items="${bcCategory1CodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
									<tr>
										<th>
											<form:label path="baCategory2">${boardConfig.bcCategory2Name}</form:label>
										</th>
										<td class="tlt">
											<form:select path="baCategory2">
												<form:option value="">선택</form:option>
												<form:options items="${bcCategory2CodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
									<tr>
										<th><form:label path="baCategory3">${boardConfig.bcCategory3Name}</form:label></th>
										<td class="tlt">
											<form:select path="baCategory3">
												<form:option value="">선택</form:option>
												<form:options items="${bcCategory3CodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
										</td>
									</tr>
								</c:if>
								
								<%-- 글상태 필드는 관리자권한인 사용자에게만 표시 --%>
								<c:if test="${isBoardManager and not empty boardConfig.bcStatusCode}">
									<tr>
										<th><form:label path="baStatus">진행상태</form:label></th>
										<td class="tlt">
											<form:select path="baStatus">
												<form:option value="">선택</form:option>
												<form:options items="${bcStatusCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
										</td>
									</tr>
								</c:if>
								
								<tr>
									<td colspan="4" class="img_resize tlt">
										<form:textarea path="baContentHtml" cols="30" rows="10" htmlEscape="false" class="textarea h300 w0" title="내용을 입력하세요" placeholder="내용을 입력하세요" />
										<form:errors path="baContentHtml" element="div" cssClass="form-error" style="color: #ff0000;" />
									</td>
								</tr>
								
								<c:if test="${boardConfig.bcSupportSecret}">
									<tr>
										<th>비밀글 </th>
										<td class="tlt">
											<p class="chk_t3">
												<input type="checkbox" name="baSecret" id="id01" value="true" checked="checked" onclick="return false;">
												<label for="id01">비밀글</label>
											</p>
										</td>
										
										<th><form:label path="baSecretPassword">비밀번호 </form:label> <span class="required">*</span></th>
										<td class="tlt">
											<form:password path="baSecretPassword" class="wm130"/>
											<form:errors path="baSecretPassword" element="div" cssClass="form-error" style="color: #ff0000;" />
											<span class="t_bl77 f13 ib">※비밀번호를 꼭 기억해 주세요.</span>
											<c:if test="${formMode eq 'UPDATE' and boardArticleForm.baSecret}">
												<ul>
													<li>- 비밀글 비밀번호를 변경시 입력해 주세요.(공란시 기존비밀번호가 유지됩니다.)</li>
												</ul>
											</c:if>
										</td>
									</tr>
								</c:if>
								
								<c:if test="${boardConfig.bcSupportNotice and isBoardManager}">
									<tr>
										<th>
											<form:label path="baNotice">공지글</form:label>
										</th>
										<td colspan="3">
											<form:checkbox path="baNotice" />
											<form:errors path="baNotice" element="div" cssClass="form-error" />
											<form:label path="baNoticeEndDate">공지종료일</form:label>
											<form:input path="baNoticeEndDate" class="datepicker-ui"/>
											<form:errors path="baNoticeEndDate" element="div" cssClass="form-error" />
										</td>
									</tr>
								</c:if>
								
								<c:if test="${boardConfig.bcSupportNuri}">
									<tr>
										<th><form:label path="baNuri">공공누리</form:label></th>
										<td class="feisd">
											<form:select path="baNuri" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
												<form:option value="">선택</form:option>
												<form:options items="${bcNuriCodeList}" itemLabel="codeName" itemValue="codeId" />
											</form:select>
											<form:errors path="baNuri" element="div" cssClass="text-danger col-form-label ml-2" />
										</td>
									</tr>
								</c:if>
								
								
								<c:if test="${boardConfig.bcSupportImage}">
									<tr>
										<th><form:label path="baThumbFile">대표이미지</form:label></th>
										<td class="feisd">
											<div>
												<c:if test="${not empty boardArticleForm.thumb && boardArticleForm.thumb.fileId > 0}">
													<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${boardArticleForm.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/design/common/images/noimage.jpg'" />
												</c:if>
											</div>
											<div>
												<input type="file" name="baThumbFile" class="col-form-label" />
												<form:errors path="baThumbFile" element="div" cssClass="text-danger col-form-label ml-2" />
												<span class="mt-2 bg-light font-weight-bold text-info">- jpg, jpeg, gif, png 파일만 업로드할 수 있습니다.</span>
											</div>
											<div class="form-inline">
												<span class="col-form-label mr-2"> 대체텍스트 : </span>
												<form:input path="baThumbText" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="baThumbText" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
											<div>
												<c:if test="${not empty thumbFileInfoUploadResult}">
													<div class="text-danger">
														<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
														<ul>
															<c:forEach items="${thumbFileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
															<c:forEach items="${thumbFileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
															<c:forEach items="${thumbFileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
														</ul>
													</div>
												</c:if>
											</div>
										</td>	
									</tr>
								</c:if>
								
								<%--
								<c:if test="${boardConfig.bcUploadFileNum > 0}">
									<!-- 현재첨부된 파일 --!>
									<tr>
										<th><label for="baMultipartFiles[0]" id="file_label" class="col-sm-2 col-form-label">첨부파일 </label></th>
										<td class="feisd">
											<input type="file" name="baMultipartFiles[0]" id="baMultipartFiles0" class="col-form-label" />
											<form:errors path="baMultipartFiles" element="div" cssClass="text-danger col-form-label ml-2" />
											<div class="mt-2 bg-light font-weight-bold text-danger">- 임시처리 script, 마크업, 첨부파일변경등 수정해야함</div>
											<span class="mt-2 bg-light font-weight-bold text-info">- 파일첨부는 총${boardConfig.bcUploadFileNum}개까지 가능합니다.(파일당${boardConfig.bcUploadSizeMax}MB제한)</span>
											
											<div class="mt-2" id="egovComFileList">
												<c:forEach items="${boardArticleForm.baFileInfos}" var="baFileInfo">
													<div class="file_add">
														<span class="input2">${baFileInfo.fileOriginalName}(${baFileInfo.fileSizeString})</span>
														<img src="/images/egovframework/com/cmm/btn/btn_del.png" class="cursor" style="margin: 0px; padding: 0px;">
													</div>
												</c:forEach>
											</div>
											
											<c:if test="${not empty fileInfoUploadResult}">
												<div class="text-danger">
													<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
													<ul>
														<c:forEach items="${fileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
														<c:forEach items="${fileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
														<c:forEach items="${fileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
													</ul>
												</div>
											</c:if>
										</td>
									</tr>
								</c:if>
								 --%>
								
								<%-- 관리자권한인 사용자에게만 표시 --%>
								<%--
								<c:if test="${isBoardManager and boardConfig.bcSupportAnswer}">
									<tr>
										<th><form:label path="baAnswer">답글</form:label></th>
										<td class="tlt">
											<form:errors path="baAnswer" element="div" cssClass="form-error" />
											<form:textarea path="baAnswer" style="width: 95%; height: 200px;" />
										</td>
									</tr>
								</c:if>
								<c:if test="${!isBoardManager and boardConfig.bcSupportAnswer}">
									<form:hidden path="baAnswer" />
								</c:if>
								 --%>
								
							</tbody>
						</table>
					</form:form>
				</div>

				<!-- button -->
				<div class="btn_wrap trt">
					<c:if test="${isBoardManager || bcAllowForm}">
						<a href="#n" class="btn t_br insertBtn">저장</a>
					</c:if>
					<a class="btn t_wg" href="${APP_PATH}/board/${boardConfig.bcId}/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a>
				</div>
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />