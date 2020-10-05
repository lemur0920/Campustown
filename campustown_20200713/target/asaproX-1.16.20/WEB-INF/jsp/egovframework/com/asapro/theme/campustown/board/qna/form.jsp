<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<c:if test="${not empty fileInfoUploadResult}"><script>alert('<spring:message code="board.article.fileInfoUploadResult"></spring:message>');</script></c:if>

<script>
jQuery(function($){
	
	
	//달력
	$('.datepicker-ui').datepicker();
	
	//첨부파일
	//------------------------------------------
	//------------------------- 첨부파일 등록 Start
	//-------------------------------------------
	<c:if test="${boardConfig.bcUploadFileNum > 0}">
	$('.selectFile').multiSelector({
		list_target: 'fileList'
		,fileItemCssSelector: 'file_add'
		,list_delete_fileid: 'deleteFileList'
		,max: ${boardConfig.bcUploadFileNum}
	});
	</c:if>
	//------------------------- 첨부파일 등록 End
	
	//등록
	$('.insertBtn').on('click', function(e){
		e.preventDefault();
		
		$('#boardArticleForm').submit();
	});
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<c:if test="${not empty message}">
		<div class="message">
			${message}
		</div><!-- //.message -->
	</c:if>
	<form:form modelAttribute="boardArticleForm" action="${actionUrl}" method="post" enctype="multipart/form-data">
		${SECURITY_TOKEN_TAG}
		<form:hidden path="bcId" />
			
		<div class="table-con style2 mb30">
			<table>
				<caption>
				${boardConfig.bcName}
				<c:if test="${formMode eq 'INSERT'}">글쓰기</c:if>
				<c:if test="${formMode eq 'UPDATE'}">글수정</c:if>
				</caption>
				
				<colgroup>
					<col style="width: 20%">
					<col style="width: auto">
				</colgroup>
				
				<tbody>
					<tr>
						<th scope="row"><label for="baTitle">제목</label> <span class="text-error">*</span></th>
						<td>
							<form:input path="baTitle" title="제목 입력" class="input-100p" htmlEscape="false"/>
							<form:errors path="baTitle" element="div" cssClass="text-error" />
						</td>
					</tr>
					
					<c:choose>
						<c:when test="${sessionScope.currentUser.userRole.roleCode eq 'ROLE_GUEST'}">
							<tr>
								<th scope="row"><label for="baGuestName">이름</label> <span class="text-error">*</span></th>
								<td colspan="">
									<form:input path="baGuestName" maxlength="30" title="이름 입력" class="input-100p"/> 
									<form:errors path="baGuestName" element="div" cssClass="text-error" />
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="baEmail">이메일</label></th>
								<td>
									<form:input path="baEmail" title="이메일 입력" class="input-100p"/>
									<form:errors path="baEmail" element="div" cssClass="text-error" />
								</td>
							</tr>
							<tr>
								<th scope="row">
									<label for="baGuestPassword">본인확인비밀번호</label> <c:if test="${formMode eq 'INSERT'}"><span class="text-error">*</span></c:if>
								</th>
								<td>
									<form:password path="baGuestPassword" maxlength="20" />
									<form:errors path="baGuestPassword" element="div" cssClass="text-error" />
									<c:if test="${formMode eq 'UPDATE'}">
										<div>- 본인확인용 비밀번호 변경시 입력해 주세요.(최대20자. 공란시 기존비밀번호가 유지됩니다.)</div>
									</c:if>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th scope="row"><label for="baGuestName">이름</label></th>
								<td>
									<c:if test="${formMode eq 'INSERT'}">
										<span id="baGuestName">${sessionScope.currentUser.userName}</span>
									</c:if>
									<c:if test="${formMode eq 'UPDATE'}">
										<c:if test="${empty boardArticleForm.member}"><span id="baGuestName">${boardArticleForm.baGuestName}</span></c:if>
										<c:if test="${not empty boardArticleForm.member}"><span id="baGuestName">${boardArticleForm.member.memberName}</span></c:if>
									</c:if>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="baEmail">이메일</label></th>
								<td>
									<c:if test="${formMode eq 'INSERT'}">
										<form:input path="baEmail" value="${sessionScope.currentUser.userEmail}" class="input-100p"/>
									</c:if>
									<c:if test="${formMode eq 'UPDATE'}">
										<form:input path="baEmail"/>
									</c:if>
									<form:errors path="baEmail" element="div" cssClass="text-error" />
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
						<tr>
							<th scope="row">
								<label for="baCategory1">${boardConfig.bcCategory1Name}</label> <span class="text-error">*</span>
							</th>
							<td>
								<form:select path="baCategory1" class="sel-w150">
									<form:option value="">선택</form:option>
									<form:options items="${bcCategory1CodeList}" itemLabel="codeName" itemValue="codeId"/>
								</form:select>
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
						<tr>
							<th scope="row">
								<label for="baCategory2">${boardConfig.bcCategory2Name}</label> 
							</th>
							<td>
								<form:select path="baCategory2">
									<form:option value="">선택</form:option>
									<form:options items="${bcCategory2CodeList}" itemLabel="codeName" itemValue="codeId"/>
								</form:select>
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
						<tr>
							<th scope="row">
								<label for="baCategory3">${boardConfig.bcCategory3Name}</label> 
							</th>
							<td>
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
						<th scope="row"><label for="baContentHtml">내용</label> <span class="text-error">*</span></th>
						<td>
							<form:textarea path="baContentHtml" title="내용 입력" cols="30" rows="10" htmlEscape="false"/>
							<form:errors path="baContentHtml" element="div" cssClass="text-error" />
						</td>
					</tr>
					<c:if test="${boardConfig.bcSupportSecret}">
						<tr>
							<th scope="row"><label for="baSecret">비밀글</label> <span class="text-error">*</span></th>
							<td>
								<div class="isb-tail cick_back">
									<span>
										<input type="checkbox" name="baSecret" id="cb02" class="agreeChk" value="true" <c:if test="${boardArticleForm.baSecret }">checked="checked"</c:if>>
										<label for="cb02">비밀</label>
									</span>	
								</div>
								<form:label path="baSecretPassword">비밀번호 </form:label>
								<form:password path="baSecretPassword" />
								<form:errors path="baSecretPassword" element="div" cssClass="text-error" />
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
								
					<c:if test="${boardConfig.bcUploadFileNum > 0}">
						<!-- 현재첨부된 파일 -->
						<tr>
							<th scope="row"><label for="lb04">첨부파일</label> </th>
							<td>
								
									<input type="file" name="baMultipartFiles[0]" id="baMultipartFiles0" class="selectFile" />
									<form:errors path="baMultipartFiles" element="div" cssClass="text-error" />
									<span class="">- 파일첨부는 총${boardConfig.bcUploadFileNum}개까지 가능합니다.(파일당${boardConfig.bcUploadSizeMax}MB제한)</span>
									
									<div class="" id="fileList">
										<c:forEach items="${boardArticleForm.baFileInfos}" var="baFileInfo">
											<div class="file_add">
												<span>${baFileInfo.fileOriginalName}(${baFileInfo.fileSizeString})</span>
												<img src="${CONTEXT_PATH }/design/common/images/asset/btn_del.png" class="deleteBtn" style="cursor: pointer;" data-fileid="${baFileInfo.fileId}">
											</div>
										</c:forEach>
									</div>
									<div id="deleteFileList"></div>
									
									<c:if test="${not empty fileInfoUploadResult}">
										<div class="text-error">
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
					
					
				</tbody>
			</table>
		</div>
		
		<div class="btn-area txr clearfix">
			<a class="btn05 insertBtn" href="#n">저장</a>
			<a class="btn01" href="${APP_PATH}/board/${boardConfig.bcId}/list">목록</a>
		</div>
	</form:form>
	
	
	
	
	
	
	
		<%-- <h2 class="s-tit s6-tit">
			${boardConfig.bcName}
			<c:if test="${formMode eq 'INSERT'}">글쓰기</c:if>
			<c:if test="${formMode eq 'UPDATE'}">글수정</c:if>
		</h2>
	
		<c:if test="${not empty message}">
			<div class="message">
				${message}
			</div><!-- //.message -->
		</c:if>
		<form:form modelAttribute="boardArticleForm" action="${actionUrl}" method="post" enctype="multipart/form-data">
			${SECURITY_TOKEN_TAG}
			<form:hidden path="bcId" />
			
			<div class="board-form">
			
				<table>
					<colgroup>
						<col style="width: 20%"/>
						<col style="width: 25%"/>
						<col style="width: 20%" />
						<col />
					</colgroup>
					<tbody>
						<tr>
							<th><form:label path="baTitle">제목</form:label> <span class="required">*</span></th>
							<td colspan="3">
								<form:input path="baTitle" cssStyle="width: 95%"/>
								<form:errors path="baTitle" element="div" cssClass="form-error" />
							</td>
						</tr>
						<c:choose>
							<c:when test="${sessionScope.currentUser.userRole.roleCode eq 'ROLE_GUEST'}">
								<tr>
									<th><form:label path="baGuestName">작성자</form:label> <span class="required">*</span></th>
									<td colspan="">
										<form:input path="baGuestName" maxlength="30" /> 
										<form:errors path="baGuestName" element="div" cssClass="form-error" />
									</td>
									<th><form:label path="baEmail">이메일</form:label></th>
									<td>
										<form:input path="baEmail" cssStyle="width: 85%"/>
										<form:errors path="baEmail" element="div" cssClass="form-error" />
									</td>
								</tr>
								<tr>
									<th>
										<form:label path="baGuestPassword">본인확인비밀번호</form:label> <c:if test="${formMode eq 'INSERT'}"><span class="required">*</span></c:if>
									</th>
									<td colspan="3">
										<form:password path="baGuestPassword" maxlength="20" />
										<form:errors path="baGuestPassword" element="div" cssClass="form-error" />
										<c:if test="${formMode eq 'UPDATE'}">
											<div>- 본인확인용 비밀번호 변경시 입력해 주세요.(최대20자. 공란시 기존비밀번호가 유지됩니다.)</div>
										</c:if>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th>
										<label for="baGuestName">작성자</label>
									</th>
									<td>
										<c:if test="${formMode eq 'INSERT'}">
											<span id="baGuestName">${sessionScope.currentUser.userName}</span>
										</c:if>
										<c:if test="${formMode eq 'UPDATE'}">
											<c:if test="${empty boardArticleForm.member}"><span id="baGuestName">${boardArticleForm.baGuestName}</span></c:if>
											<c:if test="${not empty boardArticleForm.member}"><span id="baGuestName">${boardArticleForm.member.memberName}</span></c:if>
										</c:if>
									</td>
									<th><form:label path="baEmail">이메일</form:label></th>
									<td>
										<c:if test="${formMode eq 'INSERT'}">
											<form:input path="baEmail" value="${sessionScope.currentUser.userEmail}" cssStyle="width: 85%"/>
										</c:if>
										<c:if test="${formMode eq 'UPDATE'}">
											<form:input path="baEmail" cssStyle="width: 85%"/>
										</c:if>
										<form:errors path="baEmail" element="div" cssClass="form-error" />
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						
						<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
							<tr>
								<th>
									<form:label path="baCategory1">${boardConfig.bcCategory1Name}</form:label>
								</th>
								<td colspan="3">
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
								<td colspan="3">
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
								<td colspan="3">
									<form:select path="baCategory3">
										<form:option value="">선택</form:option>
										<form:options items="${bcCategory3CodeList}" itemLabel="codeName" itemValue="codeId"/>
									</form:select>
								</td>
							</tr>
						</c:if>
						
						글상태 필드는 관리자권한인 사용자에게만 표시
						<c:if test="${isBoardManager and not empty boardConfig.bcStatusCode}">
							<tr>
								<th><form:label path="baStatus">진행상태</form:label></th>
								<td colspan="3">
									<form:select path="baStatus">
										<form:option value="">선택</form:option>
										<form:options items="${bcStatusCodeList}" itemLabel="codeName" itemValue="codeId"/>
									</form:select>
								</td>
							</tr>
						</c:if>
						
						<tr>
							<th><form:label path="baContentHtml">내용</form:label></th>
							<td colspan="3">
								<form:errors path="baContentHtml" element="div" cssClass="form-error" />
								<form:textarea path="baContentHtml" style="width: 95%; height: 200px;" />
							</td>
						</tr>
						
						
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
						
						<c:if test="${boardConfig.bcSupportSecret}">
							<tr>
								<th><form:label path="baSecret">비밀글 </form:label></th>
								<td colspan="3">
									<form:checkbox path="baSecret" />
									<form:label path="baSecretPassword">비밀번호 </form:label>
									<form:password path="baSecretPassword" />
									<form:errors path="baSecretPassword" element="div" cssClass="form-error" />
									<c:if test="${formMode eq 'UPDATE' and boardArticleForm.baSecret}">
										<ul>
											<li>- 비밀글 비밀번호를 변경시 입력해 주세요.(공란시 기존비밀번호가 유지됩니다.)</li>
										</ul>
									</c:if>
								</td>
							</tr>
						</c:if>
						
						
						<c:if test="${boardConfig.bcSupportNuri}">
							<tr>
								<th><form:label path="baNuri">공공누리</form:label></th>
								<td colspan="3">
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
								<td colspan="3">
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
						
						
						<c:if test="${boardConfig.bcUploadFileNum > 0}">
							현재첨부된 파일
							<tr>
								<th class="no-lb" scope="row"><label id="file_label" class="">첨부파일 </label></th>
								<td colspan="">
									<input type="file" name="baMultipartFiles[0]" id="baMultipartFiles0" class="selectFile" />
									<form:errors path="baMultipartFiles" element="div" cssClass="text-error" />
									<span class="">- 파일첨부는 총${boardConfig.bcUploadFileNum}개까지 가능합니다.(파일당${boardConfig.bcUploadSizeMax}MB제한)</span>
									
									<div class="" id="fileList">
										<c:forEach items="${boardArticleForm.baFileInfos}" var="baFileInfo">
											<div class="file_add">
												<span>${baFileInfo.fileOriginalName}(${baFileInfo.fileSizeString})</span>
												<img src="${CONTEXT_PATH }/design/common/images/asset/btn_del.png" class="deleteBtn" style="cursor: pointer;" data-fileid="${baFileInfo.fileId}">
											</div>
										</c:forEach>
									</div>
									<div id="deleteFileList"></div>
									
									<c:if test="${not empty fileInfoUploadResult}">
										<div class="text-error">
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
						
						관리자권한인 사용자에게만 표시
						<c:if test="${isBoardManager and boardConfig.bcSupportAnswer}">
							<tr>
								<th><form:label path="baAnswer">답글</form:label></th>
								<td colspan="3">
									<form:errors path="baAnswer" element="div" cssClass="form-error" />
									<form:textarea path="baAnswer" style="width: 95%; height: 200px;" />
								</td>
							</tr>
						</c:if>
						<c:if test="${!isBoardManager and boardConfig.bcSupportAnswer}">
							<form:hidden path="baAnswer" />
						</c:if>
					</tbody>
				</table>
			
				<div class="board-form">
					<button class="btn" type="submit">저장</button>
					<a class="btn btn-white" href="${APP_PATH}/board/${boardConfig.bcId}/list">목록</a>
				</div>
			</div><!-- //.board-form -->
		</form:form> --%>
				
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />