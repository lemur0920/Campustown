<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date"%>
<%
Date now = new Date();
%>
<fmt:formatDate value="<%= now %>" pattern="yyyyMMdd" var="now"/>
<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
});

function go_detail(seq, id) {

	var loginId = '${cu.userId}';

	if(id === loginId) {
		location.href = "${APP_PATH}/counsel/legal_counsel/view/"+seq;
	} else {
		alert("작성자만 조회 가능합니다.")
	}
}
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<div class="area">
		<div class="vil-tit7 mb45">
			서울 캠퍼스타운은 <span>법률 및 회계문제를 해소</span>해주는<br> <span>법률 및 회계 자문 서비스</span>를 캠퍼스타운 창업팀에게 제공합니다.<!--창업 관련 전문 변호사 2명을 위촉하여,-->
		</div>
		<h4 class="title2 mb25">자문 프로세스</h4>
<%--		<ul class="technical_introduce law type2">--%>
<%--			<li>--%>
<%--				<div class="box">--%>
<%--					<div class="d_table">--%>
<%--						<span class="d_cell">자문 신청<br>(창업팀)</span>--%>
<%--					</div>--%>
<%--					<!-- <div class="document">자문신청서 작성</div> -->--%>
<%--				</div>--%>
<%--				<ul class="list_type">--%>
<%--					<li>창업팀 자문 요청(온라인플랫폼)</li>--%>
<%--				</ul>--%>
<%--			</li>--%>
<%--			<li class="arrow">--%>
<%--				<img alt="" src="${design.resource }/images/sub/icon_arrow4.png">--%>
<%--			</li>--%>
<%--			<li>--%>
<%--				<div class="box">--%>
<%--					<div class="d_table">--%>
<%--						<span class="d_cell">(1차) 신청 승인<br>(캠퍼스타운)</span>--%>
<%--					</div>--%>
<%--					<!-- <div class="document">서류 검토</div> -->--%>
<%--				</div>--%>
<%--				<ul class="list_type">--%>
<%--					<li>신청내용 1차 검토&middot;승인</li>--%>
<%--				</ul>--%>
<%--			</li>--%>
<%--			<li class="arrow">--%>
<%--				<img alt="" src="${design.resource }/images/sub/icon_arrow4.png">--%>
<%--			</li>--%>
<%--			<li>--%>
<%--				<div class="box">--%>
<%--					<div class="d_table">--%>
<%--						<span class="d_cell">(2차) 신청 승인<br>(서울시)</span>--%>
<%--					</div>--%>
<%--					<!-- <div class="document">변호사 배정</div> -->--%>
<%--				</div>--%>
<%--				<ul class="list_type">--%>
<%--					<li>신청내용 2차 검토&middot;승인</li>--%>
<%--					<li>담당 변호&middot;회계&middot;세무사 배정(운영사)</li>--%>
<%--				</ul>--%>
<%--			</li>--%>
<%--			<li class="arrow">--%>
<%--				<img alt="" src="${design.resource }/images/sub/icon_arrow4.png">--%>
<%--			</li>--%>
<%--			<li>--%>
<%--				<div class="box">--%>
<%--					<div class="d_table">--%>
<%--						<span class="d_cell">자문 실시 <br> (자문위원)</span>--%>
<%--					</div>--%>
<%--					<!-- <div class="document">법률 자문 완료</div> -->--%>
<%--				</div>--%>
<%--				<ul class="list_type">--%>
<%--					<li>1&middot;2차 자문실시</li>--%>
<%--					<li>자문서 플랫폼 업로드</li>--%>
<%--					<li>자문료 지급(서울시)</li>--%>
<%--				</ul>--%>
<%--			</li>--%>
<%--		</ul>--%>

		<ul class="advice_proc type3">
			<li>
				<div>
					<p>자문신청<br>(창업팀)</p>
				</div>
				<ul class="list_type">
					<li>창업팀 자문 요청(온라인플랫폼)</li>
				</ul>
			</li>
			<li>
				<div>
					<p>(1차) 신청 승인<br>(캠퍼스타운)</p>
				</div>
				<ul class="list_type">
					<li>신청내용 1차 검토&middot;승인</li>
				</ul>
			</li>
			<li>
				<div>
					<p>(2차) 신청 승인<br>(서울시)</p>
				</div>
				<ul class="list_type">
					<li>신청내용 2차 검토&middot;승인</li>
					<li>자문위원 배정(운영사)</li>
				</ul>
			</li>
			<li>
				<div>
					<p>자문실시<br>(자문위원)</p>
				</div>
				<ul class="list_type">
					<li>1,2차 자문실시</li>
					<li>자문서 플랫폼 업로드</li>
					<li>자문료 지급(서울시)</li>
				</ul>
			</li>
		</ul>

		<div class="gap80" style="clear: both;"></div>
		<h4 class="title2">자문 방법</h4>
		<h4 class="p10">1차 유선 컨설팅, 2차 온라인 서면(자문서) 컨설팅</h4>
		<div class="gap10"></div>
		<div class="board-table style2 gap10">
			<table>
				<colgroup>
					<col style="width: 25%">
					<col style="width: 17%">
					<col style="width: 27%">
					<col style="width: auto">
				</colgroup>
				<thead>
				<tr>
					<th>구분</th>
					<th>자문방법</th>
					<th>자문기한</th>
					<th>내용</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>1차 컨설팅</td>
					<td><span class="f-skyblue">유선</span></td>
					<td>상담위원 배정 후 <span class="f-skyblue">2일</span> 이내<br>(주말, 공휴일 제외)</td>
					<td>
						<ul class="list_type li_c_g">
							<li>- 정확한 신청자 자문내용 파악</li>
							<li>- 자문서 작성 전 1차 컨설팅</li>
							<li>- 필요시 추가 검토자료 요청</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>2차 컨설팅</td>
					<td><span class="f-skyblue">서면</span></td>
					<td>1차 컨설팅 후 <span class="f-skyblue">5일</span> 이내<br>(주말, 공휴일 제외)</td>
					<td>
						<ul class="list_type li_c_g">
							<li>- 온라인 서면 처리(자문서)<br>&#8251;캠퍼스타운 온라인플랫폼 자문서 업로드</li>
							<br>
						</ul>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<h4 class="gap10">&#8251;자문방법 및 기한은 필요시 자문위원과 신청자간 합의하에 조정 가능</h4>
		<div class="gap_b50"></div>

		<h4 class="title2 mb25">자문단 소개</h4>
		<div class="advisory_g_box">
			<div class="advisory">
				<div class="info">
					<img src="${design.resource }/images/sub/lawyer_01.png"/>
					<span>자문분야 / 법률</span><br>
					<span class="name">김성훈</span><br>
					<span class="now">법무법인(유) 로고스 스타트업센터 미션 센터장</span>
				</div>
				<div class="history">
					<p>학력 및 이력</p>
					<ul class="list_type li_c_g">
						<li>법무법인(유) 로고스 파트너 변호사 (2020. 3. ~)</li>
						<li>연세대학교 스타트업 법률지원단 SHERPA 단장 (2020. 1.~ )</li>
						<li>연세대학교 법학전문대학원 겸임교수 (2019. 9. ~)</li>
						<li>연세대학교 법학전문대학원 JD</li>
						<li>연세대학교 법과대학 졸업</li>
					</ul>
				</div>
			</div>
			<div class="advisory">
				<div class="info">
					<img src="${design.resource }/images/sub/lawyer_02.png"/>
					<span>자문분야 / 회계&middot;세무</span><br>
					<span class="name">김판준</span><br>
					<span class="now">(주)파인드어스 대표이사</span>
				</div>
				<div class="history">
					<p>학력 및 이력</p>
					<ul class="list_type li_c_g">
						<li>2013.1. : 한미회계법인 이사</li>
						<li>2009.9. : 삼정회계법인 감사본부/세무본부</li>
						<li>2008.12. : 안진회계법인 감사본부</li>
						<li>연세대학교 법학석사 경영법무전공</li>
						<li>중앙대학교 경영학사</li>

					</ul>
				</div>
			</div>
		</div>
		<div class="gap_b50" style="clear: both;"></div>
<%--		<div class="board-table style2">--%>
<%--			<table>--%>
<%--				<colgroup>--%>
<%--					<col style="width: 14%">--%>
<%--					<col style="width: 14%">--%>
<%--					<col style="width: 18%">--%>
<%--					<col style="width: auto">--%>
<%--					<col style="width: 8%">--%>
<%--				</colgroup>--%>
<%--				<thead>--%>
<%--				<tr>--%>
<%--					<th>자문분야</th>--%>
<%--					<th>자문위원</th>--%>
<%--					<th>현직</th>--%>
<%--					<th>학력 및 이력</th>--%>
<%--					<th>비고</th>--%>
<%--				</tr>--%>
<%--				</thead>--%>
<%--				<tbody>--%>
<%--				<tr>--%>
<%--					<td>법률</td>--%>
<%--					<td></td>--%>
<%--					<td>법무법인(유) 로고스<br>소속변호사 </td>--%>
<%--					<td>--%>
<%--						<ul class="list_type li_c_g">--%>
<%--							<li>18.8 ~ : 서울지방변호사회 대외협위원</li>--%>
<%--							<li>18.1 ~ : 사단법인 희망과 동행 이사</li>--%>
<%--							<li>12.2 ~ : 법무법인(유) 로고스 소속변호사</li>--%>
<%--							<li>09.3 ~ 12.2 : 연세대학법전문대학원</li>--%>
<%--							<li>02.3 ~ 09.02 : 연세대학교</li>--%>
<%--						</ul>--%>
<%--					</td>--%>
<%--					<td>--%>
<%--					</td>--%>
<%--				</tr>--%>
<%--				</tbody>--%>
<%--			</table>--%>
<%--		</div>--%>

<%--		<div class="gap80"></div>--%>
<%--		<h4 class="title2 mb25">자문변호사이력</h4>--%>
<%--		<div class="board-table style2">--%>
<%--			<table>--%>
<%--				<colgroup>--%>
<%--					<col style="width: 14%">--%>
<%--					<col style="width: 14%">--%>
<%--					<col style="width: 18%">--%>
<%--					<col style="width: auto">--%>
<%--					<col style="width: 8%">--%>
<%--				</colgroup>--%>
<%--				<thead>--%>
<%--				<tr>--%>
<%--					<th>자문분야</th>--%>
<%--					<th>자문위원</th>--%>
<%--					<th>현직</th>--%>
<%--					<th>학력 및 이력</th>--%>
<%--					<th>비고</th>--%>
<%--				</tr>--%>
<%--				</thead>--%>
<%--				<tbody>--%>
<%--				<tr>--%>
<%--					<td>법률</td>--%>
<%--					<td></td>--%>
<%--					<td>법무법인(유) 로고스<br>소속변호사 </td>--%>
<%--					<td>--%>
<%--						<ul class="list_type li_c_g">--%>
<%--							<li>18.8 ~ : 서울지방변호사회 대외협위원</li>--%>
<%--							<li>18.1 ~ : 사단법인 희망과 동행 이사</li>--%>
<%--							<li>12.2 ~ : 법무법인(유) 로고스 소속변호사</li>--%>
<%--							<li>09.3 ~ 12.2 : 연세대학법전문대학원</li>--%>
<%--							<li>02.3 ~ 09.02 : 연세대학교</li>--%>
<%--						</ul>--%>
<%--					</td>--%>
<%--					<td>--%>
<%--					</td>--%>
<%--				</tr>--%>
<%--				</tbody>--%>
<%--			</table>--%>
<%--		</div>--%>

		<!--
        <h4 class="title2 mb25 mgt25">법률자문 신청안내</h4>
        <div class="legaladvice-box clearfix">
            <span>법률 자문을 받고자 하는 창업팀은 문의바랍니다</span>
            <p><span>02-0000-0000</span><span>수정일 : 2020-01-23</span></p>
        </div>
        -->
		<div class="gap80" style="clear: both;"></div>
		<h4 class="title2 mb25">자문현황</h4>

<!-- 		<div class="board-table style2 "> -->
		<div class="board-table style2 m-noT1 m-noT2 m-noT5 m-noT6">
			<table class="apply-status-tb">
				<caption>법률자문</caption>
				<colgroup>
					<col style="width: 7%">
					<col style="width: 15%">
					<col style="width: 25%">
					<col style="width: 15%">
					<col style="width: 15%">
					<col style="width: 13%">
					<col style="width: 10%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">분야</th>
					<th scope="col">제목</th>
					<th scope="col">상태</th>
					<th scope="col">창업팀</th>
					<th scope="col">작성자</th>
					<th scope="col">등록일</th>
				</tr>
				</thead>
				<tbody>
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="7" class="text-center">등록된 게시글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<tr>
						<td>${paging.rowTop - vs.index}</td>
						<td>${item.realm}</td>
						<td class="tb-txl" onclick="go_detail(${item.id},'${item.regId}'); return false;"><a class="title" href="${APP_PATH}/counsel/legal_counsel/view/${item.id}" >${item.title}</a></td>
						<td>
							<c:import url="statusName.jsp" charEncoding="UTF-8">
								<c:param name="status" value="${item.status}"></c:param>
							</c:import>
						</td>
						<td>${item.teamName}</td>
						<td>${item.regName} 
							<c:if test="${not empty item.regId}">
								<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.regId}&reply=true" class="noteWrite" title="쪽지쓰기"><img src="${design.resource }/images/sub/sub01_limg33.gif" alt="쪽지"></a>
							</c:if>
						</td>
						<td><fmt:formatDate value="${item.regDate}" pattern="yyyy.MM.dd"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="btn-area txr">
			<a href="${APP_PATH}/counsel/legal_counsel/write" class="btn02 fc-bg-blue">창업팀 자문 신청하기</a>
		</div>
		<!-- paging -->
		<div class="paging">
			<c:out value="${paging.userImageTag}" escapeXml="false"/>
		</div>



	</div>

	


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />