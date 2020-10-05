<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Date"%>
<%
Date now = new Date();
%>
<fmt:formatDate value="<%= now %>" pattern="yyyyMMdd" var="now"/>
<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />


<script>
jQuery(function($){
	
	
	$("[data-year='"+$('#sesnYear').val()+"'][data-cardinum='"+$('#cardiNum').val()+"']").addClass('on');
	 
		
	$('.srcL').on('click', function(e){
		//alert("click~!!");
		e.preventDefault();
		//$('.srcA').prev().removeClass('on');
		//$(this).prev().addClass('on');
		
		var idxYear = $(this).data('year');
		var cardiNum = $(this).data('cardinum');
		
		//alert("idxYear: " + idxYear);
		//alert("cardiNum: " + cardiNum);
		
		// hidden값 셋팅
		$('#sesnYear').val(idxYear);
		$('#cardiNum').val(cardiNum);
		
		//$('.srcL').removeClass('on');
		//$(this).addClass('on');
		
		$('#startActIdxForm').submit();
		
	});
	
	
});
</script>

<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<div class="vil-tit3">상위에 랭크된 <span>창업팀</span>을 소개합니다! <br>매년 <span>상반기/하반기</span> 선정되니 많은 관심바랍니다. </div>
	
	<!-- tab -->
	<c:import url="${design.themeDir}/layout/startIdxtab.jsp" charEncoding="UTF-8" />
	<%-- ${APP_PATH}/startup/startActIdxList/${sortId} --%>
	
	<form:form id="startActIdxForm" modelAttribute="startHpMngrSearch" action="${APP_PATH}/startup/startActIdxList/${sortId}" method="get">
		<form:hidden path="sesnYear" />
		<form:hidden path="cardiNum" />
		 		
		<c:set var="now" value="<%=new java.util.Date()%>" />
		<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
		<fmt:formatDate var="nowMonth" value="${now}" pattern="MM" />
		<c:set var="cardi" value="1" />																			
		<c:if test="${nowMonth >= 7 and  nowMonth <= 12}">
			<c:set var="cardi" value="2" />	
		</c:if>
		
		<!-- 
			실제 매출 발생은 전 기수를 기준으로 발생하므로,
			해당 년도와 기수 조정
			ex> 현재 2020. 04월
			=> 2019. 하반기(2)
			ex> 현재 2019. 12월
			=> 2019. 상반기 (1)
		 -->							
		<c:set var="curYear" value="${nowYear }" />
		<c:if test="${cardi eq 2 }">
			<c:set var="curCardi" value="1" />
		</c:if>
		<c:if test="${cardi eq 1 }">
			<c:set var="curCardi" value="2" />
			<c:set var="curYear" value="${nowYear-1 }" />
		</c:if>
																				
		<div class="data-tab">
			<ul class="clearfix">
			
				<c:if test="${curCardi eq  2 }">
					<li class="srcL" data-year="${curYear}" data-cardinum="2"><a href="">${curYear } 하반기</a></li>
					<li class="srcL" data-year="${curYear}" data-cardinum="1"><a href="">${curYear} 상반기</a></li>
				</c:if>
				
				<c:if test="${curCardi eq  1 }">
					<li  class="srcL" data-year="${curYear}" data-cardinum="1"><a href="">${curYear } 상반기</a></li>
				</c:if>
				
				<c:set var="cnt" value="1" />
				<c:forEach var="i"  begin="${curYear-1 }" end="${curYear }">
					<li class="srcL" data-year="${curYear-cnt}" data-cardinum="2"><a href="">${curYear-cnt} 하반기</a></li>
					<li class="srcL" data-year="${curYear-cnt}" data-cardinum="1"><a href="">${curYear-cnt} 상반기</a></li>
					<c:set var="cnt" value="${cnt+1 }"/>
				</c:forEach>
			</ul>
		</div>
	</form:form>
	
	<%-- 
	<div class="mgt25 total">
		Total : ${paging.rowTotal}
		<br/>
		
		<font style="color:red;">
		<b>
			해당 등수에 맞춰 동적으로 모든 번호가 적용되야 할 것같은데, 5등까지만 왕관이 있음?
			아니면 5등까지 뿌려줘야 함?
		</b>
		</font>
		
	</div>
	 --%>
	<!-- startup list -->
	
	<c:if test="${empty paging.result}">
		<tr>
			<td colspan="4" class="text-center">등록된 게시글이 없습니다.</td>
		</tr>
	</c:if>
	
	<div class="imhtext-list mt20">
		<c:forEach items="${paging.result }" var="item" varStatus="vs">
			<c:if test="${(vs.count % 2) == 1}">
				<ul class="clearfix">
			</c:if>
					<li>
						<dl class="text1">
							<dt>
								<a href="${APP_PATH}/startup/intro?compId=${item.compId}">
									<c:if test="${not empty item.file1Info && item.file1Info.fileId > 0 }">
										<img src="${item.file1Info.fileServletUrl}" alt="${item.file1AltText}" 
											onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
									</c:if>	
									<c:if test="${empty item.file1Info}">
										<img src="${design.resource}/images/sub/imhtext-list_img02.jpg" alt="대체 이미지">
									</c:if>	
								</a>
							</dt>
							<dd>
								<span class="tit">${item.compNm } (${item.empNm })</span>
								<p>${item.ment } …</p>
							</dd>
							<dd>
								<div class="text2">
									<p>
										<span><b style="color:#111180">투자액</b>(<fmt:formatNumber value="${item.totInvtAmt }" pattern="#,###" />만원)</span>
										<span><b>매출액</b>(<fmt:formatNumber value="${item.totSaleAmt }" pattern="#,###" />만원)</span>
									</p>
									<p>	
										<span><b>고용인수</b>(<fmt:formatNumber value="${item.totEmplyCnt }" pattern="#,###" />명)</span>
										<span><b>지적재산</b>(<fmt:formatNumber value="${item.totIntellProp }" pattern="#,###" />건) </span>
									</p>
								</div>
								<p class="updata"><b>Update </b>
									<fmt:parseDate value="${item.idxUpdDt }" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
									<fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd"/>
								</p>
							</dd>
							<%-- 
							${paging.rowTotal}
							${paging.rowTop }
							${vs.count }
							${vs.index }
							${startHpMngrSearch.cp }
							 --%>
							<c:if test="${startHpMngrSearch.cp eq 1}">
								<p class="level-div"><img src="${design.resource }/images/sub/num0${vs.index + 1}_img.png" alt="랭킹 왕관 이미지${vs.index + 1}" /></p>
							</c:if>
						</dl>
					</li>		
			<c:if test="${(vs.count % 2) == 0 || vs.last}">
				</ul>
			</c:if>
		</c:forEach>
	</div>
	<!-- paging -->
	<%-- 
	<div class="paging">
		<c:out value="${paging.userImageTag}" escapeXml="false"/>
	</div>
	 --%>	
	<!--</div> -->  <!-- END .area -->
<!--</div> -->   <!-- END #content -->


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />