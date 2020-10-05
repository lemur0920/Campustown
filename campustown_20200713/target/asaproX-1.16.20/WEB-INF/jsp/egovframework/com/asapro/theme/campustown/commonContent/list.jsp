<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	$('.popupBtn').on('click', function(){
		var comContId = $(this).data('id');
		$.ajax({
			url : '${APP_PATH}/common/content${API_PATH}/view'
			, type : 'get'
			, dataType : 'json'
			, data : {
				comContId : comContId
			}
		}).done(function(result){
			if(result != null){
				// 팝업 포커스 이동   
				$(this).addClass('cfocus')
				$('#popup_con').show();
				$('#popup_con .popup').focus();
				
				//alert(result.content);
				$('#popup_con .con').html(result.comContContent);
				$('#popup_con .pop_tit').html(result.comContTitle);
			}
			
			
		}).fail(function(){
			alert('[해당 콘텐츠가 없습니다.]');
		});
		
	});
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- content -->
			<div id="content">
				<div class="box_line mb_30">
					<div>
						<form:form modelAttribute="commonContentSearch" action="${APP_PATH}/common/content/list" method="get">
							<div class="table tb_3">
								<table>
									<caption>맞춤형 청년정책 검색</caption>
									<colgroup>
										<col style="width:84px;">
										<col style="">
									</colgroup>
									<tbody>
										<tr>
											<th>누가</th>
											<td>
												<p class="chk_set chks clearfix">
												
												<input type="checkbox" id="id00" class="all" name="checkAll">
												<label for="id00">전체</label>
												
												<c:if test="${not empty cate1CodeList }">
													<c:forEach items="${cate1CodeList }" var="cate1Code" varStatus="vs"> 
														<c:set var="chkday" value="" />
														<c:forEach items="${selectComContCate1Array }" var="cate1" >
															<c:if test="${cate1 eq cate1Code.codeId }">
																<c:set var="chkday" value="checked" />
															</c:if>
														</c:forEach>
														<input type="checkbox" name="comContCate1" data-dayidx="${vs.index }" id="comContCate1${vs.count }" value="${cate1Code.codeId }" ${chkday } />
														<label for="comContCate1${vs.count }"> ${cate1Code.codeName }</label>
													</c:forEach>
												</c:if>
												</p>
											</td>
										</tr>
										<tr>
											<th>무엇을</th>
											<td>
												<p  class="chks clearfix">
												
													<c:if test="${not empty cate2CodeList }">
														<c:forEach items="${cate2CodeList }" var="cate2Code" varStatus="vs"> 
															<c:set var="chkday" value="" />
															<c:forEach items="${selectComContCate2Array }" var="cate2" >
																<c:if test="${cate2 eq cate2Code.codeId }">
																	<c:set var="chkday" value="checked" />
																</c:if>
															</c:forEach>
															<input type="checkbox" name="comContCate2" data-dayidx="${vs.index }" id="comContCate2${vs.count }" value="${cate2Code.codeId }" ${chkday } />
															<label for="comContCate2${vs.count }"> ${cate2Code.codeName }</label>
														</c:forEach>
													</c:if>
												</p>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<p class="tct"><button type="submit" class="btn t_sch cfocus">검색</button></p>
						</form:form>
					</div>
				</div>
			
				<h4 class="tit_blue mb_40">나에게 꼭 필요한 지원 정보를  찾아보세요.</h4>
				
				<c:if test="${empty paging.result}">
					<p class="mb_40">데이터가 없습니다.</p>
				</c:if>
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<a href="#n" class="popupBtn" data-id="${item.comContId }"><span class="tit_backb mb_15">${item.comContTitle }</span></a>
					<p class="mb_40">${item.comContSubTitle }</p>
				</c:forEach>
				
				<!-- 공통컨텐츠 팝업 -->
				<c:import url="${design.themeDir}/commonContent/popupView.jsp" charEncoding="UTF-8" />
				
				<!-- paging -->
				<c:out value="${paging.userTextTag}" escapeXml="false"/>
			
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />