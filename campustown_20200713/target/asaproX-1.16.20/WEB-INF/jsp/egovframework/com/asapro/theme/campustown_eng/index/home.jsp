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
<style>
	.mv_bg > li > a p.tit{padding-top : 135px}
	.mbn-tit span {font-size :28px}
</style>

	<!-- main -->
	<div id="content" class="mcontents">

        <!-- main visual -->
       	<%-- <c:import url="${design.themeDir}/banner/mainVisual.jsp" charEncoding="UTF-8" /> --%>
       	
		<div class="mct_slb">
			<ul class="mv_bg clearfix">
				<li class="con1">
					<a href="${APP_PATH}/content/scte010">
						<div class="bg">
							<p class="tit">Seoul Campus Town</p>
							<p class="txt txt2">What is <br /> Seoul Campus Town?</p>
							<p class="bn"><span>more</span></p>
						</div>
					</a>
				</li>
				<li class="con2">
					<a href="${APP_PATH}/content/scte020">
						<div class="bg">
							<p class="tit">Participating Universities</p>
							<p class="txt txt2">“University City Seoul” that </br> creates future value <br /> and mutual growth of youths, <br /> universities, and regions</p>
							
							<p class="bn"><span>more</span></p>
						</div>
					</a>
				</li>
				<li class="con3">
					<a href="${APP_PATH}/content/scte030">
						<div class="bg">
							<p class="tit">Results</p>
							<p class="txt txt2">Startups, revitalization of commerce, </br> residence, local cooperation</p>
							<p class="bn"><span>more</span></p>
						</div>
					</a>
				</li>
				<li class="con4">
					<a href="${APP_PATH}/content/scte050">
						<div class="bg">
							<p class="tit">BI Introduction</p>
							<p class="txt txt2">the BI of Seoul Campus Town signifies </br> the success  of startups through cooperation </br> between the Campus Town <br /> and university student entrepreneurs</p>
							<p class="bn"><span>more</span></p>
						</div>
					</a>
				</li>
			</ul>
		</div>     
		   
		<%-- <div class="m-bn">
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
		</div> --%>
		
		<div class="m-bn">
			<div class="m-bn-in clearfix">
				<div class="mbn-left">
					<p class="mbn-tit">Current State of<br /><span>Start-up and Universities</span></p>
					<p class="mbn-date">in 2020. 01</p>
				</div>
				<script type="text/javascript">
					$(document).ready(function(){ 
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

							new numberCounter("counter1", 55);
							new numberCounter("counter2", 34);
							new numberCounter("counter3", 175);
							new numberCounter("counter4", 600);
						/*창업팀 현황 counting*/
					});
				</script>
				<div class="mbn-right clearfix">
					<p class="mbn-year" style="display:none;" >서울 소재 <br />2020년 내</p>
					<div class="mbn-rihgt-in">
						<div class="mbn-data-info">
							<p class="tit">In Seoul,</p>
							<p class="ct01"><span id="counter1">34</span> of </p>
							<p class="ct02"><span id="counter2">55</span> universities, </p>
							<p class="ct03"><span id="counter3">175</span> start-up teams are participated.</p>
						</div>
						<div class="mbn-data-info">
							<p class="tit">  Within 2020,</p>
							<p class="ct04"> aim to participate <span id="counter4">600</span>start-up teams.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		
       
        <%-- 배너 --%> 
		<%-- <div class="mgy_bg">
			<div class="bot_slide_bg">
				<c:import url="${APP_PATH}/banner/banner" charEncoding="UTF-8" />
			</div>
		</div> --%>
		<!-- mgy_bg -->
	</div>
	<!-- #content end -->
			
<%-- 레이어팝업 --%>
<c:import url="${APP_PATH }/banner/layer" />

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />