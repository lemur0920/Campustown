<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//삭제 Ajax
	$('#deleteBtn').on('click', function(e){
		var noteIdsByType = [];
		$('[name=noteIdsByType]:checked').each(function(idx, el){
			noteIdsByType.push(el.value);
		});
		
		swal({
            title: '삭제한 항목은 복구할 수 없습니다.\n삭제하시겠습니까?',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (noteIdsByType.length === 0) {
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
				url : '${APP_PATH}/mypage/note/${noteSearch.noteDiv}/delete'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteIdsByType : noteIdsByType
					,${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
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
						location.reload();
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
	
	
	//발송취소 Ajax
	$('#cancelBtn').on('click', function(e){
		var cancelId = $(this).data('cancelid');
		
		swal({
            title: '발송취소를 하시겠습니까?',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (!cancelId ) {
                            reject('취소할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${APP_PATH}/mypage/note/cancel'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteReceptionId : cancelId
					,${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '발송취소가 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '취소결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
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
			                title: '취소하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '취소하지 못하였습니다.[fail]'
	            });
			});
        });
	});
	
	
	//검색
	$('.searchBtn').on('click', function(e){
		e.preventDefault();
		$('#noteSearch').submit();
	});
	
	//기간 달력
	$('.datepicker-ui').datepicker();
	$(".ui-datepicker-trigger").hide();

	
	

});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />


<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<!-- content -->
	
	<!-- 탭메뉴 -->
	<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
	
	
	<div class="table-con">
		<form:form modelAttribute="noteSearch" cssClass="" action="${APP_PATH}/mypage/note/${noteSearch.noteDiv}/list" method="get">
		<table>
			<caption>쪽지</caption>
			<colgroup>
				<col style="width: 150px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">발신일자	</th>
					<td class="databox">
						<span><form:input path="startDate" cssClass="datepicker-ui" autocomplete="off"/></span>&nbsp;&nbsp;~&nbsp;&nbsp;
						<span><form:input path="endDate" cssClass="datepicker-ui" autocomplete="off"/></span>
					</td>
				</tr>
				<tr>
					<th scope="row">검색어</th>
					<td class="sub03-select-input">
						<form:select path="sc" cssClass="" title="검색조건">
							<option value="">검색어 구분</option>
							<c:if test="${noteSearch.noteDiv eq 'reception'}">
								<form:option value="NOTE_TRANSMITER_ID" label="발신자아이디" />
								<form:option value="ADMIN_NAME" label="발신자이름" />
							</c:if>
							<c:if test="${noteSearch.noteDiv eq 'transmit'}">
								<form:option value="NOTE_RECEIVER_ID" label="수신자아이디" />
								<form:option value="ADMIN_NAME" label="수신자이름" />
							</c:if>
						</form:select>	
						<span><form:input path="sv" title="검색어" placeholder="Search"/><a href="#n" class="searchBtn"><img src="${design.resource }/images/sub/t-searchstyle1-icon01.png" alt="검색버튼"></a></span>
					</td>
				</tr>
			</tbody>
		</table>
		</form:form>
	</div>
	
	<!-- =======  받은편지함 ============ -->
	<!-- =======  받은편지함 ============ -->
	<!-- =======  받은편지함 ============ -->
	<c:if test="${noteSearch.noteDiv eq 'reception'}">
		<div class="board-table style2 mtp50">
			<table>
				<caption>받은쪽지함</caption>
				<colgroup>
					<col style="width: 4%">
					<col style="width: 5%">
					<col style="width: 15%">
					<col style="width: auto">
					<col style="width: 20%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">
							<input type="checkbox" id="selectAll" onclick="jQuery('[name=noteIdsByType]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});">
						</th>
						<th scope="col"></th>
						<th scope="col">보낸사람</th>
						<th scope="col">내용</th>
						<th scope="col">날짜</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty paging.result}">
						<tr>
							<td colspan="5" class="text-center">쪽지가 없습니다.</td>
						</tr>
					</c:if>
					<c:forEach items="${paging.result}" var="item" varStatus="vs">
						<tr>
							<td>
								<input type="checkbox" name="noteIdsByType" value="${item.noteReceptionId}">
							</td>
							<td>
								<a href="${APP_PATH}/mypage/note/reception/view?noteReceptionId=${item.noteReceptionId}&cp=${noteSearch.cp}${noteSearch.queryString}" title="쪽지보기">
									<c:if test="${item.noteOpen }">
										<img src="${CONTEXT_PATH }/assets/images/note_open.png" alt="오픈쪽지이미지"  style="height: 20px;" />
									</c:if>
									<c:if test="${!item.noteOpen }">
										<img src="${CONTEXT_PATH }/assets/images/note_nonopen.png" alt="미오픈쪽지이미지"  style="height: 20px;" />
									</c:if>
								</a>
							</td>
							<td>
								<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.noteTransmiterId}&reply=true" class="noteWrite" title="쪽지쓰기">
									<c:if test="${!item.noteOpen }"><b class="text-dark">${item.transmiter.adminName}(${item.noteTransmiterId})</b></c:if>
									<c:if test="${item.noteOpen }">${item.transmiter.adminName}(${item.noteTransmiterId})</c:if>
									<%-- <img src="${design.resource }/images/sub/sub01_limg33.gif" alt=""> --%>
								</a>
								<%-- <a  href="#none" class=""> <img src="${design.resource }/images/sub/sub01_limg33.gif" alt=""></a> --%>
							</td>
							<td class="tb-txl">
								<a href="${APP_PATH}/mypage/note/reception/view?noteReceptionId=${item.noteReceptionId}&cp=${noteSearch.cp}${noteSearch.queryString}" class="" title="쪽지보기">
								<c:if test="${!item.noteOpen }"><b class="text-dark">${asapro:abbreviate(item.noteContent,40)}</b></c:if>
								<c:if test="${item.noteOpen }">${asapro:abbreviate(item.noteContent,40)}</c:if>
								</a>
							</td>
							<td>
								<c:if test="${!item.noteOpen }"><b class="text-dark"><fmt:formatDate value="${item.noteSendDate}" pattern="yy-MM-dd [HH:mm]" /></b></c:if>
								<c:if test="${item.noteOpen }"><fmt:formatDate value="${item.noteSendDate}" pattern="yy-MM-dd [HH:mm]" /></c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	
	<!-- =======  보낸편지함 ============ -->
	<!-- =======  보낸편지함 ============ -->
	<!-- =======  보낸편지함 ============ -->
	<c:if test="${noteSearch.noteDiv eq 'transmit'}">
		<div class="board-table style2 mtp50">
			<table>
				<caption>보낸쪽지함</caption>
				<colgroup>
					<col style="width: 4%">
					<col style="width: 5%">
					<col style="width: 15%">
					<col style="width: auto">
					<col style="width: 16%">
					<col style="width: 16%">
					<col style="width: 10%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">
							<input type="checkbox" id="selectAll" onclick="jQuery('[name=noteIdsByType]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});">
						</th>
						<th scope="col"></th>
						<th scope="col">받는사람</th>
						<th scope="col">내용</th>
						<th scope="col">보낸날짜</th>
						<th scope="col">받은날짜</th>
						<th scope="col">발송취소</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty paging.result}">
						<tr>
							<td colspan="7" class="text-center">쪽지가 없습니다.</td>
						</tr>
					</c:if>
					<c:forEach items="${paging.result}" var="item" varStatus="vs">
						<tr>
							<td>
								<input type="checkbox" name="noteIdsByType" value="${item.noteTransmitId}">
							</td>
							<td>
								<a href="${APP_PATH}/mypage/note/transmit/view?noteTransmitId=${item.noteTransmitId}&cp=${noteSearch.cp}${noteSearch.queryString}" title="쪽지보기">
									<img src="${CONTEXT_PATH }/assets/images/note_open.png" alt="오픈쪽지이미지"  style="height: 20px;" />
								</a>
							</td>
							<td>
								<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.noteReceiverId}&reply=true" class="noteWrite" title="쪽지쓰기">
									${item.receiver.adminName}(${item.noteReceiverId})
									<%-- <img src="${design.resource }/images/sub/sub01_limg33.gif" alt=""> --%>
								</a>
								<%-- <a  href="#none" class=""> <img src="${design.resource }/images/sub/sub01_limg33.gif" alt=""></a> --%>
							</td>
							<td class="tb-txl">
								<a href="${APP_PATH}/mypage/note/transmit/view?noteTransmitId=${item.noteTransmitId}&cp=${noteSearch.cp}${noteSearch.queryString}" class="" title="쪽지보기">
								${asapro:abbreviate(item.noteContent,35)}
								</a>
							</td>
							<td>
								<fmt:formatDate value="${item.noteSendDate}" pattern="yy-MM-dd [HH:mm]" />
							</td>
							<td>
								<c:if test="${item.noteCancelYn eq 'N'}">
									<c:if test="${item.noteOpen}"><fmt:formatDate value="${item.noteReceiveDate}" pattern="yy-MM-dd [HH:mm]" /></c:if>
									<c:if test="${!item.noteOpen}"><span class="badge badge-secondary">읽지않음</span></c:if>
								</c:if>
								<c:if test="${item.noteCancelYn eq 'Y'}"><span class="badge badge-warning">발송취소</span></c:if>
							</td>
							<td>
								<c:if test="${!item.noteOpen and item.noteCancelYn eq 'N'}"><button id="cancelBtn" class="btn btn-warning waves-effect waves-light btn-sm" data-cancelid="${item.noteReceptionId}"> 발송취소</button></c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	
	<div class="btn-area txl clearfix">
		<a class="btn04" id="deleteBtn" href="#n">삭제</a>
	</div>
	

	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>


	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />