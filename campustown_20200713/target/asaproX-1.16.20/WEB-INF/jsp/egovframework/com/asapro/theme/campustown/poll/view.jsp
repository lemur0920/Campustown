<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<%-- <c:if test="${archiveCategory.catSupportRecommend}"><script src="${CONTEXT_PATH }/design/common/js/recommend.js"></script></c:if> --%>

<script>
jQuery(function($){
	//투표하기
	$('.pollBtn').on('click', function(e){
		e.preventDefault();
		var poId = null;
		var pollData = null;
		
		if( $(this).data('poid') ){
			poId = $(this).data('poid');
		}
		if( $(this).data('poll') ){
			pollData = $(this).data('poll');
		}
		
		if( !poId || !pollData ){
			console.log('poll : poId & pollData is required!');
			swal({
	            type: 'warning',
	            title: '참여실패!!',
	            html : '투표에 필요한 데이터가 부족합니다.',

	        });
		} else {
			//쿠키 체크
			if( $.cookie('poll_' + GLOBAL.siteId + '_' + poId) ){
				swal({
		            type: 'warning',
		            title: '참여실패!!',
		            html : '이미 투표에 참여하셨습니다.',
		        });
			} else {
				$.ajax({
					url : GLOBAL.APP_PATH + '/poll/' + poId + GLOBAL.API_PATH + '/take'
					, type : 'post'
					, dataType: 'json'
					, data: {
						poId : poId
						, pollData : pollData
					}
				}).done(function(result){
					if(result.success){
						swal({
			                type: 'success',
			                title: '참여완료!',
			                html: '투표가 정상적으로 진행되었습니다.',
			            }).then(function(){
							location.reload();
			            });
					}else{
						if(result.text){
							swal({
				                type: 'warning',
				                title: '참여실패',
				                html: result.text,
				            });
						} else {
							swal({
				                type: 'error',
				                title: '참여실패![error]',
				                html: '관리자에게 문의해주세요.',
				            });
						}
					}
				}).fail(function(result){
					swal({
		                type: 'error',
	                	title: '참여실패![fail]',
		                html: '관리자에게 문의해주세요.',
		            });
				}); 
			}
		}
	});

});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
		
		<!-- content -->
		<div class="board-detail-head">
			<p class="title">${pollForm.poQuestion }</p>
			<div class="zt-tg">
				<dl>
					<dt>투표기간    </dt>
					<dd>${asapro:formatStringDate(pollForm.poStartDate,'yyyy-MM-dd','yyyy.MM.dd')}  ~${asapro:formatStringDate(pollForm.poEndDate,'yyyy-MM-dd','yyyy.MM.dd') }</dd>
				</dl>
				<dl>
					<dt>조회수</dt>
					<dd><fmt:formatNumber type="number" maxFractionDigits="3" value="${pollForm.poHit }" /> </dd>
				</dl>
				<dl>
					<dt>찬성</dt>
					<dd><fmt:formatNumber type="number" maxFractionDigits="3" value="${pollForm.poYesCnt }" /></dd>
				</dl>
				<dl>
					<dt>반대</dt>
					<dd><fmt:formatNumber type="number" maxFractionDigits="3" value="${pollForm.poNoCnt }" /></dd>
				</dl>
			</div>
		</div>
		<div class="board-detail-cont">
			<p style="text-align: center">
				<c:if test="${not empty pollForm.thumb && pollForm.thumb.fileId > 0}">
					<img src="${pollForm.thumb.fileServletUrl}" alt="${pollForm.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'" />
				</c:if>
			</p>
			<p style="text-align: center">${asapro:nl2br(pollForm.poDescription,true)}</p>

			<c:if test="${pollForm.poStartDate <= NOW and pollForm.poEndDate >= NOW }">
				<div class="bbv_tpb">
					<div>
						<div class="tx"><strong>여러분</strong>의 <strong>생각</strong>은 <p>어떠신가요?</p></div>
						<div class="btb">
							<p class="left">
								<a href="#n" class="bg1 pollBtn" data-poll="yes" data-poid="${pollForm.poId }"><span>찬성해요</span> </a>
							</p>
							<p class="right">
								<a href="#n" class="bg2 pollBtn" data-poll="no" data-poid="${pollForm.poId }"><span>반대해요</span></a>
							</p>
						</div>
					</div>
				</div>
			</c:if>

		</div>
		<div class="ct_btn">
			<a class="list" href="${APP_PATH}/poll/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a>
		</div>
		
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />