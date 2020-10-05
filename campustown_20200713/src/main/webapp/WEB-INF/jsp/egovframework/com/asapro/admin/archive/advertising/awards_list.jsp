<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ page import="java.util.Date"%>
<jsp:useBean id="toDay" class="java.util.Date" />

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var arcIds = [];
		$('[name=arcIds]:checked').each(function(idx, el){
			arcIds.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '삭제한 항목은 복구할 수 없습니다. 첨부되었던 파일도 함께 삭제됩니다.',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (arcIds.length === 0) {
                            reject('삭제할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/archive/advertising/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					arcIds : arcIds
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '삭제가 완료되었습니다.',
		                html: '삭제결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
						//location.href = 'list.do';
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '삭제하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '삭제하지 못하였습니다.[fail]'
	            });
			});
        });
    });
	
<%-- 	
  //메타코드 2 선택에 따라 메타코드 3 목록 변경
	$('#metaCode2').on('change',function(e){
		e.preventDefault();
		var catId = $('#metaCode2').find(':selected').val();
		
		if(catId == null || catId == ''){
			//alert('선택된 코드가 없습니다.');
			$('#metaCode3').empty();
			$('#metaCode3').append("<option value=''>공익광고 소분류</option>");
			return;
		}

		$.ajax({
			url : '${ADMIN_PATH}/code${API_PATH}/item/jsonList.do'
			, type : 'get'
			, data : {
				catId : catId
			}
			, dataType: 'json'
		}).done(function(result){
			$('#metaCode3').empty();
			$('#metaCode3').append("<option value=''>공익광고 소분류</option>");
			if(result.length > 0){

				$.each(result, function(index, item){
					//alert(item.codeName);
					$('#metaCode3').append("<option value='"+ item.codeId +"'>"+ item.codeName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
--%>	
	
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
			
	        <div class="page-content-wrapper ">

				<div class="container-fluid">
			
					<!-- location -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />

					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
						            <form:form modelAttribute="advertisingSearch" cssClass="" action="${ADMIN_PATH}/archive/advertising/list.do" method="get">
						            	<form:hidden path="arcCategory"/>
						            	
										<div class="form-row mr-auto mt-2 form-inline">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="${archiveCategory.catPageSize}">${archiveCategory.catPageSize}개씩</form:option>
												<form:option value="${archiveCategory.catPageSize * 2}">${archiveCategory.catPageSize * 2}개씩</form:option>
												<form:option value="${archiveCategory.catPageSize * 4}">${archiveCategory.catPageSize * 4}개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="ARC_REGDATE" label="등록일" />
												<form:option value="ARC_TITLE" label="제목" />
												<form:option value="ARC_HIT" label="조회순" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<%--
										<div class="form-row mt-2 form-inline" >
											<label for="sc" class="mr-sm-3 mb-2">등록일</label>
											<form:input path="startDate" cssClass="form-control col-sm-1 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
											<span class="mx-2"> ~ </span>
											<form:input path="endDate" cssClass="form-control col-sm-1 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
										</div>
										 --%>
										<div class="form-row mt-2 form-inline" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											
											<c:if test="${not empty arcMetaCode1List || not empty arcMetaCode2List || not empty arcMetaCode3List}">
												<c:if test="${not empty arcMetaCode1Info }">
												<form:select path="metaCode1" cssClass="form-control mr-sm-2 mb-2" title="${arcMetaCode1Info.catName}">
													<option value="">${arcMetaCode1Info.catName}</option>
													<form:options items="${arcMetaCode1List}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												</c:if>
												
												<c:if test="${not empty arcMetaCode2Info }">
												<form:select path="metaCode2" cssClass="form-control mr-sm-2 mb-2" title="${arcMetaCode2Info.catName}">
													<option value="">${arcMetaCode2Info.catName}</option>
													<form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												</c:if>
												
												<%-- <form:select path="metaCode3" cssClass="form-control mr-sm-2 mb-2" title="공익광고 소분류">
													<option value="">공익광고 소분류</option>
													<form:options items="${arcMetaCode3List}" itemLabel="codeName" itemValue="codeId"/>
												</form:select> --%>
												
												<c:if test="${not empty arcMetaCode3Info }">
												<form:select path="metaCode3" cssClass="form-control mr-sm-2 mb-2" title="${arcMetaCode3Info.catName}">
													<option value="">${arcMetaCode3Info.catName}</option>
													<form:options items="${arcMetaCode3List}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												</c:if>
												
											</c:if>
											
											<fmt:formatDate value="${toDay }" pattern="yyyy" var="thisYear" />
											<form:select path="adtManufactureYear" cssClass="form-control mr-sm-2 mb-2" title="제작년도">
												<option value="">제작년도  </option>
												<c:forEach begin="0" end="${thisYear - 1980 }" step="1" var="idx" >
													<form:option label="${thisYear - idx } 년" value="${thisYear - idx }"/>
												</c:forEach>
											</form:select>
											
											<form:select path="arcSelected1" cssClass="form-control mr-sm-2 mb-2" title="추출 여부">
												<option value="">추출 여부</option>
												<form:option value="true" label="추출" />
												<form:option value="false" label="미추출" />
											</form:select>
											
											<form:select path="arcUse" cssClass="form-control mr-sm-2 mb-2" title="게시여부">
												<option value="">게시여부</option>
												<form:option value="true" label="게시" />
												<form:option value="false" label="게시안함" />
											</form:select>
											
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="ARC_TITLE" label="제목" />
												<form:option value="ARC_CONTENT" label="내용" />
											</form:select>	
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											<button class="btn btn-outline-success waves-effect mb-2 " type="submit">검색</button>
										</div>
									</form:form>
					            </div>
					        </div>
					    </div>
					</div>
					<!-- //검색폼 -->
					
					<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
								
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
									<!-- info -->
									<div class="p-3 mb-3 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> ${archiveCategory.catName } 목록 </div>
									</div>
									
									<div class="table-responsive">
										<table class="table table-striped table-hover ">
											<colgroup>
												<col style="width: 30px;" />
												<col style="width: 70px;" /><!-- 번호 -->
												<c:if test="${not empty archiveCategory.catMetaCode1 }">
													<col style="width: 10%;" /><!-- 메타코드1 -->
												</c:if>
												
												<c:if test="${not empty archiveCategory.catMetaCode2 }">
													<col style="width: 10%;" /><!-- 메타코드2 -->
												</c:if>
												<c:if test="${not empty archiveCategory.catMetaCode3 }">
													<col /><!-- 메타코드3 -->
												</c:if>
												<%-- <c:if test="${not empty arcMetaCode3ListAll }">
													<col style="width: 10%;" /><!-- 메타코드3 -->
												</c:if> --%>
												<col style="width: 80px;" /><!-- 이미지 -->
												<col  /><!-- 제목 -->
												<col style="width: 8%;" /><!-- 제작년도 -->
												<col style="width: 8%;" /><!-- 등록일 -->
												<c:if test="${archiveCategory.catSupportSelect1}">
													<col style="width: 6%;" /><!-- 추출 -->
												</c:if>
												<c:if test="${archiveCategory.catSupportRecommend}">
													<col style="width: 5%;" /><!-- 추천 -->
												</c:if>
												<col style="width: 6%;" /><!-- 게시여부 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=arcIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>번호</th>
													<c:if test="${not empty archiveCategory.catMetaCode1 }">
														<th>${arcMetaCode1Info.catName}</th>
													</c:if>
													<c:if test="${not empty archiveCategory.catMetaCode2 }">
														<th>${arcMetaCode2Info.catName}</th>
													</c:if>
													<c:if test="${not empty archiveCategory.catMetaCode3 }">
														<th>${arcMetaCode3Info.catName}</th>
													</c:if>
													<%-- <c:if test="${not empty arcMetaCode3ListAll }">
														<th>공익광고 소분류</th>
													</c:if> --%>
													<th>이미지</th>
													<th>제목</th>
													<th>제작년도</th>
													<th>등록일시</th>
													<c:if test="${archiveCategory.catSupportSelect1}">
														<th>추출여부</th>
													</c:if>
													<c:if test="${archiveCategory.catSupportRecommend}">
														<th>추천</th>
													</c:if>
													<th>게시여부</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="14" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="arcIds" value="${item.arcId}" class="custom-control-input" id="arcIds${vs.count }" />
																<label class="custom-control-label" for="arcIds${vs.count }"></label>
															</div>
														</td>
														<td>${paging.rowTop - vs.index}</td>
														<c:if test="${not empty archiveCategory.catMetaCode1 }">
															<td>${asapro:codeName(item.arcMetaCode1, arcMetaCode1List)}</td>
														</c:if>
														<c:if test="${not empty archiveCategory.catMetaCode2 }">
															<td>${asapro:codeName(item.arcMetaCode2, arcMetaCode2List)}</td>
														</c:if>
														<c:if test="${not empty archiveCategory.catMetaCode3 }">
															<td>${asapro:codeName(item.arcMetaCode3, arcMetaCode3List)}</td>
														</c:if>
														<%-- <c:if test="${not empty arcMetaCode3ListAll }">
															<td>${asapro:codeName(item.arcMetaCode3, arcMetaCode3ListAll)}</td>
														</c:if> --%>
														<td>
															<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
																<img class="img-thumbnail" style="max-width: 80px; max-height: 80px;" src="${item.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
															</c:if>
														</td>
														<td><a href="${ADMIN_PATH}/archive/advertising/update.do?arcId=${item.arcId}" class=""><strong>${item.arcTitle}</strong></a></td>
														<td>
															${item.adtManufactureYear }
														</td>
														<td>
															<fmt:formatDate value="${item.arcRegdate}" pattern="yyyy-MM-dd" />
														</td>
														
														<c:if test="${archiveCategory.catSupportSelect1}">
															<td>
																<c:if test="${item.arcSelected1}"><span class="badge badge-success">추출</span></c:if>
															</td>
														</c:if>
														<c:if test="${archiveCategory.catSupportRecommend}">
															<td>
																${item.arcRecommend}
															</td>
														</c:if>
														
														<td>
															<c:if test="${!item.arcUse}"><span class="badge badge-warning">미게시</span></c:if>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/archive/advertising/insert.do?arcCategory=${archiveCategory.catId}" class="btn btn-primary waves-effect waves-light">추가</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
										</div>
										<div>
											<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
										</div>
									</div>

									<!-- paging -->
									<div>
										<c:out value="${paging.adminTextTag}" escapeXml="false"/>
									</div>
									
								</div>
							</div>
						</div>
					</div><!-- 목록 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />