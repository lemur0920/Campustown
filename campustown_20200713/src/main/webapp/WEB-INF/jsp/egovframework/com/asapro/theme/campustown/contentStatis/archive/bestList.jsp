<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	
	<div class="vil-tit">전체 게시글 중 <span>좋아요</span>를 가장 많이 받은 게시글이 선정됩니다.</div>
	<ul class="sub-tab2 clearfix tab2 bline">
		<li class="on"><a href="#n">2020년</a></li>
		<li><a href="#n">2019년</a></li>
	</ul>
	
	<div class="data-tab">
		<ul class="clearfix">
			<li class="on"><a href="#n">1월</a></li>
			<li><a href="#n">2월</a></li>
			<li><a href="#n">3월</a></li>
		</ul>
	</div>
	
	<ul class="gallery-list clearfix mgt40">
		<li>
			<i class="img"><a href="#n"><img src="${design.resource }/images/fame/1.jpg" alt=""></a>
			</i>
			<div class="txt-box">
				<h4 class="tit"><a href="#n">서울형 캠퍼스타운 시작 대외발표</a></h4>
				<p class="txt"><a href="#n">서울형 캠퍼스타운 시작 대외발표 새로운 창업육성·지역혁신 모델, 대학시절부터 창업의 꿈이 시작될 수 있도록...</a></p>
				<p class="time">2019-02-05</p>
				<div class="info-con">
					<span>40</span>
					<span>30</span>
				</div>
			</div>
			<p class="level-div"><img src="${design.resource }/images/sub/num01_img.png" alt=""></p>
		</li>
		<li>
			<i class="img"><a href="#n"><img src="${design.resource }/images/fame/sooho.jpg" alt=""></a>
			</i>
			<div class="txt-box">
				<h4 class="tit"><a href="#n">수호가 우수사례 IT기술산업부문에서 1위</a></h4>
				<p class="txt"><a href="#n">주 근로시간 단축제 도입, 제도도입후 법정 근로시간 준수, 제도 도입후 , 전사적 방식 근태관리..</a></p>
				<p class="time">2019-02-05</p>
				<div class="info-con">
					<span>38</span>
					<span>22</span>
				</div>
			</div>
			<p class="level-div"><img src="${design.resource }/images/sub/num02_img.png" alt=""></p>
		</li>
		
		<li>
			<i class="img"><a href="#n"><img src="https://designgram.cafe24.com/09seminar/seminar_01.jpg" alt=""></a>
			</i>
			<div class="txt-box">
				<h4 class="tit"><a href="#n">IoT관련 기술 세미나 2차 자료</a></h4>
				<p class="txt"><a href="#n">스마트홈 사물인터넷 관련 디바이스의 급격한 수요증가로 인해 예상되는</a></p>
				<p class="time">2019-02-05</p>
				<div class="info-con">
					<span>34</span>
					<span>19</span>
				</div>
			</div>
			<p class="level-div"><img src="${design.resource }/images/sub/num03_img.png" alt=""></p>
		</li>
		<li>
			<i class="img"><a href="#n"><img src="${design.resource }/images/fame/13.jpg" alt=""></a>
			</i>
			<div class="txt-box">
				<h4 class="tit"><a href="#n">고려대 캠퍼스타운 입주기업 백재현 에이올코리아 대표</a></h4>
				<p class="txt"><a href="#n">에이올코리아 백재현 대표님! 세계적 유니콘 기업 기대합니다...</a></p>
				<p class="time">2019-01-05</p>
				<div class="info-con">
					<span>39</span>
					<span>30</span>
				</div>
			</div>
		</li>
		
		<li>
			<i class="img"><a href="#n"><img src="${design.resource }/images/main/bmc3_bg01.gif" alt=""></a>
			</i>
			<div class="txt-box">
				<h4 class="tit"><a href="#n">대학의 혁신창업, 대한민국의 미래</a></h4>
				<p class="txt"><a href="#n">주 근로시간 단축제 도입, 제도도입후 법정 근로시간 준수, 제도 도입후 , 전사적 방식 근태관리..</a></p>
				<p class="time">2019-02-05</p>
				<div class="info-con">
					<span>36</span>
					<span>22</span>
				</div>
			</div>
		</li>
		<li>
			<i class="img"><a href="#n"><img src="${design.resource }/images/fame/2.jpg" alt=""></a></i>
			<div class="txt-box">
				<h4 class="tit"><a href="#n">일자리 함께하기 지원 확대</a></h4>
				<p class="txt"><a href="#n">주 근로시간 단축제 도입, 제도도입후 법정 근로시간 준수, 제도 도입후 , 전사적 방식 근태관리..</a></p>
				<p class="time">2019-02-05</p>
				<div class="info-con">
					<span>38</span>
					<span>29</span>
				</div>
			</div>
		</li>
	</ul>

	<div class="paging">
		<a href="#none" class="img"><img src="${design.resource }/images/sub/first_page.gif" alt="맨처음"></a>
		<a href="#none" class="img"><img src="${design.resource }/images/sub/prev_page.gif" alt="이전"></a>
		<a href="#none" class="on" title="현재 페이지">1</a>
		<a href="#none">2</a>
		<a href="#none">3</a>
		<a href="#none">4</a>
		<a href="#none">5</a>
		<a href="#none" class="img"><img src="${design.resource }/images/sub/next_page.gif" alt="다음"></a>
		<a href="#none" class="img"><img src="${design.resource }/images/sub/last_page.gif" alt="맨뒤"></a>
	</div>
		
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />