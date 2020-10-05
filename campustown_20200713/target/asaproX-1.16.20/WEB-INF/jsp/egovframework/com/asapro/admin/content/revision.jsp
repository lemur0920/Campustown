<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<script>
jQuery(function($){
	//삭제 Ajax
	$('#deleteRevisionBtn').on('click', function (e) {
		e.preventDefault();
		var contentIds = [];
		$('[name=contentIds]:checked').each(function(idx, el){
			contentIds.push(el.value);
		});

		swal({
            title: '삭제하시겠습니까?',
            html: '삭제한 항목은 복구할 수 없습니다.\r\n삭제하시겠습니까?',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (contentIds.length === 0) {
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
				url : '${ADMIN_PATH}/content/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					contentIds : contentIds
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '삭제가 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '삭제결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						//location.reload();
		            	location.href = '${ADMIN_PATH}/content/update.do?contentId=${currentContentId}';
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
});
</script>

<hr />
<div class="form-group row">
	<label class="col-sm-2 col-form-label">수정이력 </label>
	<div class="col-sm">
		<div class="p-3 mb-2 bg-light text-info font-weight-bold">
			<div><i class="mdi mdi-information"></i> 버전을 클릭하시면 최신버전과 해당버전의 비교 또는 과거버전으로의 복구를 수행할 수 있습니다.</div>
		</div>
		
		
		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<colgroup>
					<col style="width: 30px;" /><!-- 체크 -->
					<col style="width: 50px;" /><!-- 버전 -->
					<col style="width: 200px;" /><!-- 수정일 -->
					<col style="width: 200px;" /><!-- 작업자 -->
					<col/><!-- 비고 -->
				</colgroup>
				<thead>
					<tr>
						<th class="text-center">
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=contentIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
								<label class="custom-control-label" for="selectAll"></label>
							</div>
						</th>
						<th>버전</th>
						<th>수정일시</th>
						<th>작업자</th>
						<th>비고</th>
					</tr>
				</thead>
				<tbody>
				
					<c:if test="${empty paging.result}">
						<tr>
							<td colspan="5" class="text-center">수정 이력이 없습니다.</td>
						</tr>
					</c:if>
					<c:forEach items="${paging.result}" var="item" varStatus="vs">
						<tr>
							<td class="text-center">
								<c:if test="${item.contentVer > 0 && item.contentStatus == 'revision'}">
								<div class="custom-control custom-checkbox">
									<input type="checkbox" name="contentIds" value="${item.contentId}" class="custom-control-input" id="contentId${vs.count }" />
									<label class="custom-control-label" for="contentId${vs.count }"></label>
								</div>
								</c:if>
							</td>
							<td class="text-center">
								<c:if test="${item.contentVer == 0}"><span class="badge badge-success">생성</span></c:if>
								<c:if test="${item.contentVer > 0 && item.contentStatus == 'publish'}"><span class="badge badge-warning">최신</span></c:if>
								<c:if test="${item.contentVer > 0 && item.contentStatus == 'revision'}">
									<a href="${ADMIN_PATH}/content/revision/compare.do?contentRoot=${item.contentRoot}&amp;contentVer=${item.contentVer}&amp;menu.menuId=${menuId}">${item.contentVer}</a>
								</c:if>
							</td>
							<td>	
								<fmt:formatDate value="${item.contentLastModified}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								${item.contentLastWorker.adminId}
							</td>
							<td>
								${asapro:nl2br(item.contentMemo, false)}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-2 col-form-label"> </label>
	<div class="col-sm">
		<button id="deleteRevisionBtn" class="btn btn-danger waves-effect waves-light">이력삭제</button>
	</div>
</div>

<!-- paging -->
<div class="form-group row">
	<label class="col-sm-2 col-form-label"> </label>
	<div class="col-sm">
		<c:out value="${paging.adminTextTag}" escapeXml="false"/>
	</div>
</div>
