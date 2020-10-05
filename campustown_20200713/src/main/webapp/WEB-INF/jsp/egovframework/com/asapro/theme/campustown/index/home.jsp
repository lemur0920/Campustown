<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<%-- 메인비쥬얼
<c:import url="${APP_PATH }/banner/mainVisual" /> --%>

<%-- 팝업존
<c:import url="${APP_PATH }/banner/popupzone" />--%> 

<%-- 배너
<c:import url="${APP_PATH }/banner/banner" />--%> 

<%-- 레이어팝업
<c:import url="${APP_PATH }/banner/layer" />--%> 

<%-- <!-- 메인 고정배너 -->
<c:import url="${IMPORT_APP_PATH }/banner/static" /> --%>

<%-- header --%>
<c:import url="${design.header}" charEncoding="UTF-8" />


<script>
jQuery(function($){
	
	$(".startIdxRank").hide();

	$("div.startIdxRank").eq(0).show(); // 모바일, iPad
	$("div.startIdxRank").eq(4).show(); // PC
	
	/*jaseo - 2020.04.30. 메인 탭 메뉴 2*/
	
	$(".board-btnS1 > ul > li").click(function(){
		//alert("click~!!");
		var idx = $(this).data("idx");
		$(this).addClass("on").siblings().removeClass("on");
		
		$("div.startIdxRank").hide();
		$("div.startIdxRank").eq(idx).show();
		$("div.startIdxRank").not(':eq('+idx+')').hide();
	});	
	
	/*창업팀 현황 counting*/
	function numberCounter(target_frame, target_number) {
		this.count = 0; this.diff = 0;
		this.target_count = parseInt(target_number);
		this.target_frame = document.getElementById(target_frame);
		this.timer = null;
		this.counter();
	};
	numberCounter.prototype.counter = function() {
		var self = this;
		this.diff = this.target_count - this.count;
		 
		if(this.diff > 0) {
			self.count += Math.ceil(this.diff / 5);
		}
		 
		this.target_frame.innerHTML = this.count.toString().replace();
		 
		if(this.count < this.target_count) {
			this.timer = setTimeout(function() { self.counter(); }, 300);
		} else {
			clearTimeout(this.timer);
		}
	};

	/*창업팀 현황 counting*/
	new numberCounter("counter1", 54);
	new numberCounter("counter2", ${univListTot });
	new numberCounter("counter3", ${startListTot });
	new numberCounter("counter4", 500);
	
	
	
});

</script>

	<!-- main -->
	<div id="content" class="mcontents">

        <!-- main visual -->
       	<%-- <c:import url="${design.themeDir}/banner/mainVisual.jsp" charEncoding="UTF-8" /> --%>
       	
		<div class="mct_slb">
			<ul class="mv_bg clearfix">
				<li class="con1">
					<a href="${APP_PATH}/board/sct_news/list">
						<div class="bg">
							<p class="tit">새소식</p>
							<p class="txt">서울 캠퍼스타운의<br>새로운 소식을 확인할 수 있습니다.</p>
							<p class="txt txt2">대학도시 서울,<br> 서울 캠퍼스타운</p>
							<p class="bn"><span>자세히보기</span></p>
						</div>
					</a>
				</li>
				<li class="con2">
					<a href="${APP_PATH}/board/idea_talk/list">
						<div class="bg">
							<p class="tit">커뮤니티</p>
							<p class="txt">캠퍼스타운 창업자간<br>활발한 소통이 이루어집니다.</p>
							<p class="txt txt2">서울의 중심,<br>청년의 열정을 붓다</p>
							<p class="bn"><span>자세히보기</span></p>
						</div>
					</a>
				</li>
				<li class="con3">
					<a href="${APP_PATH}/content/sct040_010_10">
						<div class="bg">
							<p class="tit">창업지원정보</p>
							<p class="txt">창업에 대한<br>다양한 정보를 전달합니다.</p>
							<p class="txt txt2">창업기업<br>비즈니스 지원 강화</p>
							<p class="bn"><span>자세히보기</span></p>
						</div>
					</a>
				</li>
				<li class="con4">
					<a href="${APP_PATH}/board/journal/list">
						<div class="bg">
							<p class="tit">캠타는 지금</p>
							<p class="txt">서울캠퍼스타운을 <br>홍보합니다.</p>
							<p class="txt txt2">대학, 지역의<br> 상생성장 및 미래가치 창출</p>
							<p class="bn"><span>자세히보기</span></p>
						</div>
					</a>
				</li>
			</ul>
		</div>     
		   
		<div class="m-bn">
			<div class="m-bn-in clearfix">
				<div class="mbn-left">
					<c:set var="now" value="<%=new java.util.Date()%>" />							
					<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
					<fmt:formatDate var="nowMonth" value="${now}" pattern="MM" />
					<p class="mbn-tit">서울 캠퍼스 타운<br /><span>창업팀 현황</span></p>
					<p class="mbn-date">${nowYear }. ${nowMonth } 기준</p>
				</div>
				
				<div class="mbn-right clearfix">
					<p class="mbn-year" style="display:none;" >서울소재 <br />2020년 내</p>
					<div class="mbn-rihgt-in">
						<div class="mbn-data-info">
							<p class="tit">서울소재</p>
							<p class="ct01"><span id="counter1">55</span>개 대학에서</p>
							<p class="ct02"><span id="counter2"></span>개 캠퍼스타운</p>
							<p class="ct03"><span id="counter3"></span>개 창업팀이 활동중이며,</p>
						</div>
						<div class="mbn-data-info">
							<p class="tit">2020년 내</p>
							<p class="ct04"><span id="counter4">512</span>개 창업팀 유치를 목표로 하고 있습니다.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		
        <!-- ============= 명예의전당 ========== -->
		<div class="mgs_all">
			<div class="mgs_top">
				<h3>서울 캠퍼스타운 <span>유니콘을 꿈꾸다</span></h3>
				<ul>
					<li>서울 캠퍼스타운에서 꿈꾸는 우리의 모습을 소개합니다</li>
				</ul>
			</div>
			
			<div class="">
				<div class="owl-demo owl-carousel owl-m1">
					<c:forEach items="${fameList }" var="item" varStatus="vs">
						<div class="item">
							<a href="${item.permLink }">
								<p class="img">
									<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" />
								</p>
								<div class="txt_area">
									<p class="tit">${item.baTitle }</p>
									<p class="txt">${asapro:abbreviate(item.baContentPlain,60) }</p>
									<p class="day"><fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" /></p>
								</div>
								<!-- <span class="ps ps1">창업공간</span> -->
							</a>
						</div>
					</c:forEach>
					
				</div>
			</div>
		</div> 
		 <!-- ============= //명예의전당 ========== -->
		<!-- .mgs_all end -->
		
		<!-- 서울 캠퍼스타운 What’s New? //모든게시판 글 -->
		<div class="main-wt clearfix">
			<div class="wt-left">
				<p class="lg-name">서울 캠퍼스타운</p>
				<p class="eng-txt">What’s New?</p>
				<p class="wt-explain">서울 캠퍼스타운의 새로운 소식을 알려드립니다. <br />사업에 대한 설명, 홍보, 언론보도, 창업자료 등 <br />다양한 정보를 확인 하실 수 있습니다.</p>
			</div>
			<div class="wt-right">
				<div class="owl-demo owl-carousel owl-m3">
					
					<c:forEach items="${boardAllList }" var="item" varStatus="vs">
						<div class="item">
							<a href="${item.permLink }">
								<div class="img">
									<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" />
									<span class="tip tip-bg${vs.count }">
										<c:forEach items="${boardConfigList }" var="config" varStatus="vsc">
											<c:if test="${config.bcId eq item.bcId }">${config.bcName }</c:if>
										</c:forEach>
									</span>
								</div>
								<div class="info">
									<p class="explain">${item.baTitle }</p>
									<p class="date"><fmt:formatDate value="${item.baRegDate}" pattern="yyyy.MM.dd" /></p>
								</div>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- //서울 캠퍼스타운 What’s New? //모든게시판 글 -->
		
		<div class="main-board">
			<!--PC 시작-->
			<div class="pc-display-owl owl-demo owl-m2 clearfix pc-style">
				
				<!-- ===========  최다 HOT TOP5=============== -->
				<div class="item">
					<p class="board-tit s1">최다 <span>HOT TOP5</span>
					</p>
					<div class="top-art clearfix">
						<c:if test="${not empty boardRecommendBestList }">
							<div class="left-art">
								<div><a href="${boardRecommendBestList[0].permLink }">
										<c:if test="${not empty boardRecommendBestList[0].thumb && boardRecommendBestList[0].thumb.fileId > 0}">
											<img src="${boardRecommendBestList[0].thumb.fileServletThumbnailUrl}" alt="${boardRecommendBestList[0].baThumbText }"
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</c:if>	
										<c:if test="${empty boardRecommendBestList[0].thumb }">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</c:if>	
									</a>
								</div>
								<a href="${boardRecommendBestList[0].permLink }" class="clearfix">
									<span class="first-tit"><span class="num"><img src="${design.resource }/images/main/num01.gif" alt="번호1" /></span> ${boardRecommendBestList[0].baTitle }</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/fine_ico.gif" alt="좋아요" />
										<span>${boardRecommendBestList[0].baRecommend }</span>
									</div>
								</a>
							</div>
							<div class="right-art">
								<ul>
									<c:forEach items="${boardRecommendBestList }" var="item" varStatus="vs">
										<c:if test="${vs.count > 1 }">
											<li class="clearfix">
												<span class="num">
													<img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" />
												</span>
												<a href="${item.permLink}" class="clearfix">
													<span class="first-tit">${item.baTitle}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/fine_ico.gif" alt="좋아요" />
														<span>${item.baRecommend}</span>
													</div>
												</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
				
				<!-- ===========  최대 HIT TOP5=============== -->
				<div class="item">
					<p class="board-tit s1">최대 <span>HIT TOP5</span>
					</p>
					<div class="top-art clearfix">
						<c:if test="${not empty boardHitBestList }">
							<div class="left-art">
								<div><a href="${boardHitBestList[0].permLink }">
										<c:if test="${not empty boardHitBestList[0].thumb && boardHitBestList[0].thumb.fileId > 0}">
											<img src="${boardHitBestList[0].thumb.fileServletThumbnailUrl}" alt="${boardHitBestList[0].baThumbText }"
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</c:if>	
										<c:if test="${empty boardHitBestList[0].thumb }">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</c:if>	
									</a>
								</div>
								<a href="${boardHitBestList[0].permLink }" class="clearfix">
									<span class="first-tit"><span class="num"><img src="${design.resource }/images/main/num01.gif" alt="번호1" /></span> ${boardHitBestList[0].baTitle }</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/view_ico.gif" alt="좋아요" />
										<span>${boardHitBestList[0].baHit }</span>
									</div>
								</a>
							</div>
							<div class="right-art">
								<ul>
									<c:forEach items="${boardHitBestList }" var="item" varStatus="vs">
										<c:if test="${vs.count > 1 }">
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												<a href="${item.permLink}" class="clearfix">
													<span class="first-tit">${item.baTitle}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/view_ico.gif" alt="좋아요" />
														<span>${item.baHit}</span>
													</div>
												</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>	
					</div>
				</div>
				
				<!-- ===========  창업활동랭킹=============== -->
				<div class="item add-tab">
					<p class="board-tit"><span>창업</span> 활동 랭킹 
						<a href="#none" class="popup-btn03"><img src="${design.resource }/images/sub/que_icon.gif" alt="창업 활동 랭킹은?" /></a>
					</p>
					<div class="main-board-popup posi3" >
						<p class="tit">창업 활동 랭킹은</p>
						<p class="con">매년 상반기/하반기에 <br>창업팀의 창업활동정보를 바탕으로 선정됩니다.</p>
						<a href="#none" class="close">X</a>
					</div>
					
					
					<div class="board-btnS1">
						<ul class="clearfix">
							<li><a href="#n" id="tab-box-cont01" class="on">매출액(만원)</a></li>
							<li><a href="#n" id="tab-box-cont02">투자액(만원)</a></li>
							<li><a href="#n" id="tab-box-cont03">고용인수(명)</a></li>
							<li><a href="#n" id="tab-box-cont04">지적재산(건)</a></li>
						</ul>
					</div>
					
					<c:set var="cardi" value="1" />
					<c:if test="${nowMonth >=7 and nowMonth <= 12 }">
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
					

					<!-- 1. 매출액 (totSaleAmt)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont01">
							<div class="left-art">
								<div>
									<c:if test="${not empty compActL2[0].file1Info && compActL2[0].file1Info.fileId > 0 }">
										<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${compActL2[0].file1Info.fileServletUrl}" alt="${compActL2[0].file1AltText}" 
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >	
										</a>	
									</c:if>
									<c:if test="${empty compActL2[0].file1Info}">
										<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</a>
									</c:if>
								</div>
								
								<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
								
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span> 
										${compActL2[0].compNm}
									</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span><fmt:formatNumber value="${compActL2[0].totSaleAmt }" pattern="#,###" />만원</span>
									</div>
								</a>
							</div>
							
							<div class="right-art">
								<c:forEach items="${compActL2}" var="item" varStatus="vs">
									<c:if test="${vs.count > 1 }">
										<ul>
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												
												<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
													<span class="first-tit"> ${item.compNm}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span><fmt:formatNumber value="${item.totSaleAmt }" pattern="#,###" />만원</span>
													</div>
												</a>
											</li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
					</div>
					<!-- 2. 투자액 (totInvtAmt)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont02" style="display: block;">
						<div class="left-art">
							<div>
								<!-- 클릭하고 해당 div on/off로 보이게 해주세요 0403 -->
								<c:if test="${not empty compActL1[0].file1Info && compActL1[0].file1Info.fileId > 0 }">
									<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
										<img src="${compActL1[0].file1Info.fileServletUrl}" alt="${compActL1[0].file1AltText}"
											 onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
									</a>
								</c:if>
								<c:if test="${empty compActL1[0].file1Info}">
									<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
										<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
									</a>
								</c:if>
							</div>

							<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span>
										${compActL1[0].compNm}
									</span>
								<div class="first-right">
									<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
									<span><fmt:formatNumber value="${compActL1[0].totInvtAmt }" pattern="#,###" />만원</span>
								</div>
							</a>
						</div>

						<div class="right-art">
							<c:forEach items="${compActL1}" var="item" varStatus="vs">
								<c:if test="${vs.count > 1 }">
									<ul>
										<li class="clearfix">
											<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>

											<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
												<span class="first-tit"> ${item.compNm}</span>
												<div class="first-right">
													<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
													<span><fmt:formatNumber value="${item.totInvtAmt }" pattern="#,###" />만원</span>
												</div>
											</a>
										</li>
									</ul>
								</c:if>
							</c:forEach>
						</div>
					</div>
					
					<!-- 3. 고용인수 (totEmplyCnt)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont03">
							<div class="left-art">
								<div>
									<c:if test="${not empty compActL3[0].file1Info && compActL3[0].file1Info.fileId > 0 }">
										<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${compActL3[0].file1Info.fileServletUrl}" alt="${compActL3[0].file1AltText}" 
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</a>	
									</c:if>
									<c:if test="${empty compActL3[0].file1Info}">
										<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</a>
									</c:if>
								</div>
								
								<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span> 
										${compActL3[0].compNm}
									</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span><fmt:formatNumber value="${compActL3[0].totEmplyCnt }" pattern="#,###" />명</span>
									</div>
								</a>
							</div>
							
							<div class="right-art">
								<c:forEach items="${compActL3}" var="item" varStatus="vs">
									<c:if test="${vs.count > 1 }">
										<ul>
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												
												<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
													<span class="first-tit"> ${item.compNm}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span><fmt:formatNumber value="${item.totEmplyCnt }" pattern="#,###" />명</span>
													</div>
												</a>
											</li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
					</div>
					
					<!-- 4. 지적재산 (totIntellProp)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont04">
							<div class="left-art">
								<div>
									<c:if test="${not empty compActL4[0].file1Info && compActL4[0].file1Info.fileId > 0 }">
										<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${compActL4[0].file1Info.fileServletUrl}" alt="${compActL4[0].file1AltText}" 
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</a>	
									</c:if>
									<c:if test="${empty compActL4[0].file1Info}">
										<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</a>
									</c:if>
								</div>
								
								<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span> 
										${compActL4[0].compNm}
									</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span><fmt:formatNumber value="${compActL4[0].totIntellProp }" pattern="#,###" />건</span>
									</div>
								</a>
							</div>
							
							<div class="right-art">
								<c:forEach items="${compActL4}" var="item" varStatus="vs">
									<c:if test="${vs.count > 1 }">
										<ul>
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												
												<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
													<span class="first-tit"> ${item.compNm}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span><fmt:formatNumber value="${item.totIntellProp }" pattern="#,###" />건</span>
													</div>
												</a>
											</li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
					</div>
					<!-- //4. 지적재산 (totIntellProp)-->
				</div>
				
				<!-- ===========  홈페이지 활동랭킹 =============== -->
				<div class="item">
					<p class="board-tit s1"><span>홈페이지 </span>활동 랭킹 
						<a href="#none" class="popup-btn03"><img src="${design.resource }/images/sub/que_icon.gif" alt="홈페이지 활동 랭킹은?" /></a>
					</p>
					<div class="main-board-popup posi4">
						<p class="tit">홈페이지 활동 랭킹은</p>
						<p class="con">서울캠퍼스타운 홈페이지에서의 활동을 바탕으로<br>매달 업데이트 되니, 많은 관심 부탁드립니다.</p>
						<a href="#none" class="close">X</a>
					</div>
					<div class="top-art clearfix">
						<c:if test="${not empty startHomepageRankList }">
							<div class="left-art">
								<div>
									<a href="${APP_PATH}/startup/intro?compId=${startHomepageRankList[0].compId}">
										<c:if test="${not empty startHomepageRankList[0].file1Info && startHomepageRankList[0].file1Info.fileId > 0 }">
											<img src="${startHomepageRankList[0].file1Info.fileServletUrl}" alt="${startHomepageRankList[0].file1AltText }"
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</c:if>	
										<c:if test="${empty startHomepageRankList[0].file1Info }">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</c:if>	
									</a>
								</div>
								<a href="${APP_PATH}/startup/intro?compId=${startHomepageRankList[0].compId}" class="clearfix">
									<span class="first-tit"><span class="num"><img src="${design.resource }/images/main/num01.gif" alt="번호1" /></span> ${startHomepageRankList[0].compNm }</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span>${startHomepageRankList[0].score }</span>
									</div>
								</a>
							</div>
							<div class="right-art">
								<ul>
									<c:forEach items="${startHomepageRankList }" var="item" varStatus="vs">
										<c:if test="${vs.count > 1 }">
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												<a href="${APP_PATH}/startup/intro?compId=${item.compId}" class="clearfix">
													<span class="first-tit"> ${item.compNm }</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span>${item.score }</span>
													</div>
												</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
				
			</div>
			<!--PC 끝-->
			<!--모바일 시작-->
			<div class="mob-display-owl owl-demo owl-carousel owl-m2" style="overflow: visible;" >
				
				<!-- ===========  최다 HOT TOP5=============== -->
				<div class="item">
					<p class="board-tit s1">최다 <span>HOT TOP5</span>
					</p>
					<div class="top-art clearfix">
						<c:if test="${not empty boardRecommendBestList }">
							<div class="left-art">
								<div><a href="${boardRecommendBestList[0].permLink }">
										<c:if test="${not empty boardRecommendBestList[0].thumb && boardRecommendBestList[0].thumb.fileId > 0}">
											<img src="${boardRecommendBestList[0].thumb.fileServletThumbnailUrl}" alt="${boardRecommendBestList[0].baThumbText }"
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</c:if>	
										<c:if test="${empty boardRecommendBestList[0].thumb }">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</c:if>	
									</a>
								</div>
								<a href="${boardRecommendBestList[0].permLink }" class="clearfix">
									<span class="first-tit"><span class="num"><img src="${design.resource }/images/main/num01.gif" alt="번호1" /></span> ${boardRecommendBestList[0].baTitle }</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/fine_ico.gif" alt="좋아요" />
										<span>${boardRecommendBestList[0].baRecommend }</span>
									</div>
								</a>
							</div>
							<div class="right-art">
								<ul>
									<c:forEach items="${boardRecommendBestList }" var="item" varStatus="vs">
										<c:if test="${vs.count > 1 }">
											<li class="clearfix">
												<span class="num">
													<img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" />
												</span>
												<a href="${item.permLink}" class="clearfix">
													<span class="first-tit">${item.baTitle}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/fine_ico.gif" alt="좋아요" />
														<span>${item.baRecommend}</span>
													</div>
												</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
				
				<!-- ===========  최대 HIT TOP5=============== -->
				<div class="item">
					<p class="board-tit s1">최대 <span>HIT TOP5</span>
					</p>
					<div class="top-art clearfix">
						<c:if test="${not empty boardHitBestList }">
							<div class="left-art">
								<div><a href="${boardHitBestList[0].permLink }">
										<c:if test="${not empty boardHitBestList[0].thumb && boardHitBestList[0].thumb.fileId > 0}">
											<img src="${boardHitBestList[0].thumb.fileServletThumbnailUrl}" alt="${boardHitBestList[0].baThumbText }"
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</c:if>	
										<c:if test="${empty boardHitBestList[0].thumb }">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</c:if>	
									</a>
								</div>
								<a href="${boardHitBestList[0].permLink }" class="clearfix">
									<span class="first-tit"><span class="num"><img src="${design.resource }/images/main/num01.gif" alt="번호1" /></span> ${boardHitBestList[0].baTitle }</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/view_ico.gif" alt="좋아요" />
										<span>${boardHitBestList[0].baHit }</span>
									</div>
								</a>
							</div>
							<div class="right-art">
								<ul>
									<c:forEach items="${boardHitBestList }" var="item" varStatus="vs">
										<c:if test="${vs.count > 1 }">
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												<a href="${item.permLink}" class="clearfix">
													<span class="first-tit">${item.baTitle}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/view_ico.gif" alt="좋아요" />
														<span>${item.baHit}</span>
													</div>
												</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>	
					</div>
				</div>
				
				<!-- ===========  창업활동랭킹=============== -->
				<div class="item add-tab">
					<p class="board-tit"><span>창업</span> 활동 랭킹 
						<a href="#none" class="popup-btn03"><img src="${design.resource }/images/sub/que_icon.gif" alt="창업 활동 랭킹은?" /></a>
					</p>
					<div class="main-board-popup posi3" >
						<p class="tit">창업 활동 랭킹은</p>
						<p class="con">매년 상반기/하반기에 <br>창업팀의 창업활동정보를 바탕으로 선정됩니다.</p>
						<a href="#none" class="close">X</a>
					</div>
					
					
					<div class="board-btnS1">
						<ul class="clearfix">
							<li><a href="#n" class="on" id="tab-box-cont01">투자액(만원)</a></li>
							<li><a href="#n" id="tab-box-cont02">매출액(만원)</a></li>
							<li><a href="#n" id="tab-box-cont03">고용인수(명)</a></li>
							<li><a href="#n" id="tab-box-cont04">지적재산(건)</a></li>
						</ul>
					</div>
					
					<c:set var="cardi" value="1" />
					<c:if test="${nowMonth >=7 and nowMonth <= 12 }">
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
					
					
					<!-- 1. 투자액 (totInvtAmt)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont01" style="display: block;">
							<div class="left-art">
								<div>
									<!-- 클릭하고 해당 div on/off로 보이게 해주세요 0403 -->
									<c:if test="${not empty compActL1[0].file1Info && compActL1[0].file1Info.fileId > 0 }">
										<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${compActL1[0].file1Info.fileServletUrl}" alt="${compActL1[0].file1AltText}" 
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</a>	
									</c:if>
									<c:if test="${empty compActL1[0].file1Info}">
										<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</a>
									</c:if>
								</div>
								
								<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span> 
										${compActL1[0].compNm}
									</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span><fmt:formatNumber value="${compActL1[0].totInvtAmt }" pattern="#,###" />만원</span>
									</div>
								</a>
							</div>
							
							<div class="right-art">
								<c:forEach items="${compActL1}" var="item" varStatus="vs">
									<c:if test="${vs.count > 1 }">
										<ul>
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												
												<a href="${APP_PATH }/startup/startActIdxList/invest?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
													<span class="first-tit"> ${item.compNm}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span><fmt:formatNumber value="${item.totInvtAmt }" pattern="#,###" />만원</span>
													</div>
												</a>
											</li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
					</div>
					<!-- 2. 매출액 (totSaleAmt)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont02">
							<div class="left-art">
								<div>
									<c:if test="${not empty compActL2[0].file1Info && compActL2[0].file1Info.fileId > 0 }">
										<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${compActL2[0].file1Info.fileServletUrl}" alt="${compActL2[0].file1AltText}" 
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >	
										</a>	
									</c:if>
									<c:if test="${empty compActL2[0].file1Info}">
										<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</a>
									</c:if>
								</div>
								
								<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
								
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span> 
										${compActL2[0].compNm}
									</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span><fmt:formatNumber value="${compActL2[0].totSaleAmt }" pattern="#,###" />만원</span>
									</div>
								</a>
							</div>
							
							<div class="right-art">
								<c:forEach items="${compActL2}" var="item" varStatus="vs">
									<c:if test="${vs.count > 1 }">
										<ul>
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												
												<a href="${APP_PATH }/startup/startActIdxList/sale?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
													<span class="first-tit"> ${item.compNm}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span><fmt:formatNumber value="${item.totSaleAmt }" pattern="#,###" />만원</span>
													</div>
												</a>
											</li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
					</div>
					
					<!-- 3. 고용인수 (totEmplyCnt)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont03">
							<div class="left-art">
								<div>
									<c:if test="${not empty compActL3[0].file1Info && compActL3[0].file1Info.fileId > 0 }">
										<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${compActL3[0].file1Info.fileServletUrl}" alt="${compActL3[0].file1AltText}" 
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</a>	
									</c:if>
									<c:if test="${empty compActL3[0].file1Info}">
										<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</a>
									</c:if>
								</div>
								
								<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span> 
										${compActL3[0].compNm}
									</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span><fmt:formatNumber value="${compActL3[0].totEmplyCnt }" pattern="#,###" />명</span>
									</div>
								</a>
							</div>
							
							<div class="right-art">
								<c:forEach items="${compActL3}" var="item" varStatus="vs">
									<c:if test="${vs.count > 1 }">
										<ul>
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												
												<a href="${APP_PATH }/startup/startActIdxList/employees?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
													<span class="first-tit"> ${item.compNm}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span><fmt:formatNumber value="${item.totEmplyCnt }" pattern="#,###" />명</span>
													</div>
												</a>
											</li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
					</div>
					
					<!-- 4. 지적재산 (totIntellProp)-->
					<div class="top-art clearfix tab-box-cont tab-box-cont04">
							<div class="left-art">
								<div>
									<c:if test="${not empty compActL4[0].file1Info && compActL4[0].file1Info.fileId > 0 }">
										<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${compActL4[0].file1Info.fileServletUrl}" alt="${compActL4[0].file1AltText}" 
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</a>	
									</c:if>
									<c:if test="${empty compActL4[0].file1Info}">
										<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</a>
									</c:if>
								</div>
								
								<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
									<span class="first-tit">
										<span class="num">
											<img src="${design.resource }/images/main/num01.gif" alt="번호1" />
										</span> 
										${compActL4[0].compNm}
									</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span><fmt:formatNumber value="${compActL4[0].totIntellProp }" pattern="#,###" />건</span>
									</div>
								</a>
							</div>
							
							<div class="right-art">
								<c:forEach items="${compActL4}" var="item" varStatus="vs">
									<c:if test="${vs.count > 1 }">
										<ul>
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												
												<a href="${APP_PATH }/startup/startActIdxList/intellProp?sesnYear=${curYear }&cardiNum=${curCardi}" class="clearfix">
													<span class="first-tit"> ${item.compNm}</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span><fmt:formatNumber value="${item.totIntellProp }" pattern="#,###" />건</span>
													</div>
												</a>
											</li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
					</div>
					<!-- //4. 지적재산 (totIntellProp)-->
				</div>
				
				<!-- ===========  홈페이지 활동랭킹 =============== -->
				<div class="item">
					<p class="board-tit s1"><span>홈페이지 </span>활동 랭킹 
						<a href="#none" class="popup-btn03"><img src="${design.resource }/images/sub/que_icon.gif" alt="홈페이지 활동 랭킹은?" /></a>
					</p>
					<div class="main-board-popup posi4">
						<p class="tit">홈페이지 활동 랭킹은</p>
						<p class="con">서울캠퍼스타운 홈페이지에서의 활동을 바탕으로<br>매달 업데이트 되니, 많은 관심 부탁드립니다.</p>
						<a href="#none" class="close">X</a>
					</div>
					<div class="top-art clearfix">
						<c:if test="${not empty startHomepageRankList }">
							<div class="left-art">
								<div>
									<a href="${APP_PATH}/startup/intro?compId=${startHomepageRankList[0].compId}">
										<c:if test="${not empty startHomepageRankList[0].file1Info && startHomepageRankList[0].file1Info.fileId > 0 }">
											<img src="${startHomepageRankList[0].file1Info.fileServletUrl}" alt="${startHomepageRankList[0].file1AltText }"
												onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'" >
										</c:if>	
										<c:if test="${empty startHomepageRankList[0].file1Info }">
											<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체이미지" >
										</c:if>	
									</a>
								</div>
								<a href="${APP_PATH}/startup/intro?compId=${startHomepageRankList[0].compId}" class="clearfix">
									<span class="first-tit"><span class="num"><img src="${design.resource }/images/main/num01.gif" alt="번호1" /></span> ${startHomepageRankList[0].compNm }</span>
									<div class="first-right">
										<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
										<span>${startHomepageRankList[0].score }</span>
									</div>
								</a>
							</div>
							<div class="right-art">
								<ul>
									<c:forEach items="${startHomepageRankList }" var="item" varStatus="vs">
										<c:if test="${vs.count > 1 }">
											<li class="clearfix">
												<span class="num"><img src="${design.resource }/images/main/num0${vs.count }.gif" alt="번호${vs.count }" /></span>
												<a href="${APP_PATH}/startup/intro?compId=${item.compId}" class="clearfix">
													<span class="first-tit"> ${item.compNm }</span>
													<div class="first-right">
														<img src="${design.resource }/images/main/star_ico.gif" alt="좋아요" />
														<span>${item.score }</span>
													</div>
												</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
				
			</div>
			<!--모바일 끝-->
		
		</div>
		
		
		
		<div class="business-main">
			<div class="bm-in">
				<p class="bm-tit">서울 캠퍼스타운 <span>창업 이모저모</span></p>
				<p class="bm-sub-tit">서울캠퍼스타운에서 제공하는 <br />다양한 창업 관련 정보를 얻을 수 있습니다.</p>
				<div class="bm-cont clearfix">
					<div class="bmc-left">
						
						<!-- ===========  지식 TALK =============== -->
						<div class="bmc1">
							<div class="bmc1-bg"><img src="${design.resource }/images/main/bmc1_bg01.gif" alt="리본" /></div>
							<div class="bmc1-head clearfix">
								<p class="bmc1-tit">지식 TALK</p>
								<a href="${APP_PATH}/board/idea_talk/list" class="bmc1-more">더보기</a>
							</div>
							<ul class="bmc1-ul">
								<c:forEach items="${ideaTalkList }" var="item" varStatus="vs">
									<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
									<fmt:formatDate value="${NOWDate }" pattern="yyyyMMdd" var="now"/>
									<li>
										<p class="type">${asapro:codeName(item.baCategory1, bcCategory1CodeListIdeaTalk) }</p>
										<a href="${item.permLink}" class="art-name">${item.baTitle}</a>
										<c:if test="${now - regdate <= 5}"><a href="#none" class="btn-s01 bg1">신규등록</a></c:if>
										
									</li>
								</c:forEach>
							</ul>
						</div>
						<div class="bmc2">
							<p class="bmc2-tit">자유 TALK</p>
							<p class="bmc2-exp">다른 창업팀과<br />소통해보세요</p>
							<a href="${APP_PATH}/board/free_talk/list" class="bmc2-btn"><span>바로가기</span></a>
							<!--<a href="#n" class="bmc2-btn"><span>공모전 바로가기</span></a>-->
						</div>
					</div>
					
					<!-- ===========  공지사항 =============== -->
					<div class="bmc-right">
						<div class="bmc6">
							<div class="bmc6-head clearfix">
								<p class="bmc6-tit">공지사항</p>
								<a href="${APP_PATH}/board/sct_news/list" class="bmc6-more">더보기</a>
							</div>
							<ul class="bmc6-ul">
								<c:forEach items="${noticeList }" var="item" varStatus="vs">
									<li>
										<p class="bmc6-tip">${asapro:codeName(item.baCategory1, bcCategory1CodeListNotice) }</p>
										<p class="bu-cont"><a href="${item.permLink}">${item.baTitle}</a></p>
										
										<div class="bu-tail clearfix">
											<p class="date"><fmt:formatDate value="${item.baRegDate}" pattern="yyyy.MM.dd" /></p>
											<!--<span class="stats stat-bg01">신규</span>-->
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					
					<div class="bmc-center">
						<div class="bmc3">
							<p class="bmc3-tit">캠퍼스타운 <span>Best Know-how</span></p>
							<p class="bmc3-sub-tit">다양한 분야의 검증된 전문가가 창업을 <br />상담해드립니다</p>
							<a href="${APP_PATH}/content/sct040_010_10" class="bmc3-btn"><span>기술자문 바로가기</span></a>
							<a href="${APP_PATH}/counsel/legal_counsel/list" class="bmc3-btn"><span>법률/회계 자문 바로가기</span></a>
						</div>
						<div class="bmc4 clearfix">
							<div class="bmc4-left">
								<div class="bl-in">
									<p class="bl-tit">시제품제작 및 <br />자문</p>
									<a href="${APP_PATH}/counsel/make_counsel/list" class="bl-btn"><span>바로가기</span></a>
								</div>
							</div>
							<div class="bmc4-right">
								<div class="br-in">
									<p class="br-tit">창업자료실</p>
									<p class="br-exp">다양한 분야의 창업 관련 <br />자료를 확인하세요.</p>
									<a href="${APP_PATH}/board/library/list" class="br-btn"><span>바로가기</span></a>
								</div>
							</div>
						</div>
						
						<div class="bmc5 clearfix">
							<p class="bmc5-tit">창업팀 <br />홍보배너<a href="#none" class="popup-btn"><img src="${design.resource }/images/sub/que_icon.gif" alt="" /></a></p>
							<div class="bmc5-popup" style="display:none;" >
								<p class="tit">창업팀 홍보배너는</p>
								<p class="con">홈페이지 활동 1위 팀의 홍보배너로 <br>매달 업데이트 되니, 많은 관심 부탁드립니다.</p>
								<a href="#none" class="close">X</a>
							</div>
							
							<!-- 창업팀 홍보배너 -->
							<c:import url="${IMPORT_APP_PATH }/banner/static" />
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- ===========  서울 캠퍼스타운 공간정보 =============== -->
		<div class="space-floor">
			<p class="space-tit"> 서울 캠퍼스타운 <span>공간정보</span></p>
			<p class="space-sub-tit">창업팀에게 제공되는 공간과 쉼터입니다.</p>
			<ul class="space-tab">
				<li class="on"><a href="#n">입주공간</a></li>
				<li><a href="#n">공유공간</a></li>
			</ul>
			
			<div class="tb-tab-cont9">
				<div>
					<ul class="mgs_slid4_b">
						<li>
							<ul class="clearfix">
								<c:forEach items="${spaceList }" var="item" varStatus="vs">
									<li>
										<a href="${item.permLink }">
											<p class="img">
												<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
													<img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" 
														onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'">
												</c:if>
												<c:if test="${empty item.thumb}">
													<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체 이미지" >
												</c:if>
											</p>
											<div class="txt_area">
												<p class="tit">${item.arcTitle }</p>
												<p class="txt">${asapro:depName(item.arcUnivId,departmentList)}</p>
												<p class="day">${item.spaComment }</p>
											</div>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</ul>
					<div class="more-btn-e1">
						<a href="${APP_PATH}/archive/space/move_space">더보기 +</a>
					</div>
				</div>
				
				<div class="hide">
					<ul class="mgs_slid5_b">
						<li>
							<ul class="clearfix">
								<c:forEach items="${rentalList }" var="item" varStatus="vs">
									<li>
										<a href="${APP_PATH}/rental/view?rentId=${item.rentId}">
											<p class="img">
												<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
													<img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" 
														onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg'">
												</c:if>
												<c:if test="${empty item.thumb}">
													<img src="${CONTEXT_PATH }/design/common/images/asset/noImage600x338.jpg" alt="대체 이미지" >
												</c:if>
												<!-- <img src="http://www.campustown.or.kr/img/2bnr_02.png" alt="" style="height:275px"> -->
											</p>
											<div class="txt_area">
												<p class="tit">${item.rentTitle }</p>
												<p class="txt">${asapro:depName(item.rentCate1,departmentList)}</p>
												<p class="day">${item.rentShortDescription }</p>
											</div>
										</a>
									</li>
								</c:forEach>
							</ul>
							<div class="more-btn-e1">
								<a href="${APP_PATH}/rental/list">더보기 +</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
       
        <%-- 배너 --%> 
		<div class="mgy_bg">
			<div class="bot_slide_bg">
				<c:import url="${APP_PATH}/banner/banner" charEncoding="UTF-8" />
			</div>
		</div>
		<!-- mgy_bg -->
	</div>
	<!-- #content end -->
			
<%-- 레이어팝업 --%>
<c:import url="${APP_PATH }/banner/layer" />

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />