$(document).ready(function(){


	$(".nav-ul > li > a").click(function(){
		if (window.innerWidth > 768){
			if ($(this).hasClass("on")){
				$(this).removeClass("on");
				$(this).next("ul").stop(true,true).slideUp("fast");
			}else{
				$(this).addClass("on");
				$(this).next("ul").slideDown("fast");
			}
		}
	});
	$(".nav-ul > li:eq(4) > a").click(function(){
		if (window.innerWidth <= 768){
			if ($(this).hasClass("on")){
				$(this).removeClass("on");
				$(this).next("ul").stop(true,true).slideUp("fast");
			}else{
				$(this).addClass("on");
				$(this).next("ul").slideDown("fast");
			}
		}
	})


	/*$("#menu-con > ul > li").bind("mouseenter mouseleave", function(e){
		if($(window).width() > 1024 && e.type == "mouseenter"){
			$(".deapth2").css({"height":"155px"})
			$(this).children(".deapth2").stop().slideDown();
		}else if($(window).width() > 1024 && e.type == "mouseleave"){
			$(".deapth2").css({"height":"auto"}).stop().slideUp();
		}
	});*/
	
	$(".hc-con").hide(); /*ehsong - popup close*/

	$(".hc-con .close").click(function(){
		$(".hc-con").slideUp();
	});
	/*$("#menu-con > ul > li > a").click(function(e){
		if(window.innerWidth <= 1024 && $(this).siblings().length > 0){
			e.preventDefault();
		}
		if($(window).width() < 1024){
			if($(this).hasClass("on")){
				$("#menu-con > ul > li > a").removeClass("on");
				$(".deapth2").stop().slideUp();
			}else{
				$("#menu-con > ul > li > a").removeClass("on");
				$(".deapth2").stop().slideUp();
				$(this).addClass("on");
				$(this).next().stop().slideDown();
			}
		}
	});*/

	/*gnb menu*/
	/*$("#menu-con > ul > li > a").bind("mouseover click", function(e){
		if (window.innerWidth > 1024 && e.type == "mouseover"){
			$(".menu-bg").slideDown("fast");
			$(".deapth2").slideDown("fast");
		}else if (window.innerWidth <= 1024 && e.type == "click"){
			e.preventDefault();
			if ($(this).hasClass("on")){
				$("#menu-con > ul > li > a").removeClass("on");
				$(".deapth2").stop().slideUp("fast");
			}else{
				$("#menu-con > ul > li > a").removeClass("on");
				$(".deapth2").stop().slideUp("fast");
				$(this).addClass("on");
				$(this).next().stop().slideDown("fast");
			}
		}
	});*/





	$(".hb-con").mouseleave(function(){
		if (window.innerWidth > 1024){
			$(".menu-bg").slideUp("fast");
			$(".deapth2").slideUp("fast");
		}
	});
	
	$(".op-all-menu").click(function(){
		$(".menu-bg").slideDown("fast");
		$(".deapth2").slideDown("fast");
	});

	/*mobile menu*/
	/*$(".deapth2 > ul > li > a").click(function(e){
		if (window.innerWidth <= 1024){
			if ($(this).next().is("ul")){
				e.preventDefault();
				if ($(this).hasClass("ov")){
					$(".deapth2 > ul > li > a").removeClass("ov");
					$(".deapth3").stop().slideUp("fast");
				}else{
					$(".deapth2 > ul > li > a").removeClass("ov");
					$(".deapth3").stop().slideUp("fast");
					$(this).addClass("ov");
					$(this).next().stop().slideDown("fast");
				}
			}else{
				
			}
		}
	});*/
	



	/*메인 탭 메뉴*/
	$(".space-tab > li").click(function(){
		var index = $(this).index();
		$(this).addClass("on").siblings().removeClass("on");
		$(".tb-tab-cont9 > div").eq(index).removeClass("hide").siblings().addClass("hide");
	});
	
	/*jaseo - 2020.04.30. 메인 탭 메뉴 2*/
	/*
	$(".board-btnS1 > ul > li").click(function(){
		//alert("click~!!");
		var idx = $(this).data("idx");
		$(this).addClass("on").siblings().removeClass("on");
		
		$("div.startIdxRank").hide();
		<c:if test="${not empty compActL1}">
			$("div.startIdxRank").eq(idx).show();
		</c:if>
		$("div.startIdxRank").not(':eq('+idx+')').hide();
	});
	*/
	/* POPUP */
	// jaseo - 2020.04.07. 레이어팝업 관련 내용 수정
	$('.popup-link a').bind('click', function(e){
		
		e.preventDefault();
		$('body').addClass("hidden-popup");
		var currentTab = $(this).attr('href');
		
		//$('.popup-box').hide();
		$('.popup-box').show();  
		$(".popup-content").show();  
		
		$(currentTab).show();
		var tabCon_num=currentTab.replace(/[^0-9]/ig,"");
		var tabCon_num_eq=tabCon_num - 1;
	});
	
	$(".popup-content .popup-header > .close").click(function(){
		$('.popup-box').hide();
		$(".popup-content").hide();
		$('body').removeClass('hidden-popup');
	});
	/* //POPUP */

	/*q&a*/
	$(".faq-list > li > a.open").click(function(){
		if($(this).hasClass('on')){
			$(this).next("div").slideUp();
			$(this).removeClass('on');
		}else{
			$(".faq-list > li > a.open").removeClass('on');
			$('.answer-box').slideUp();
			$(this).next().slideDown();
			$(this).addClass('on');
		}
	});
	/*q&a*/


	/*En*/
	$("#allmenu").click(function(){
			$("#menu-con").stop().slideToggle();
			$("#menu-con > ul > li > a").removeClass("on");
			$(".deapth2").stop().slideUp();
	});
	/*En*/

	/*$("#menu-con > ul > li:first-child > a").focus(function(){
		if (window.innerWidth > 1024){
			$(".deapth2").slideDown("fast");
			$(".menu-bg").slideDown("fast")
		}
	});
	$("#menu-con > ul > li:last-child > .deapth2 > ul > li:last-child > a").blur(function(){
		if (window.innerWidth > 1024){
			$(".deapth2").slideUp("fast");
			$(".menu-bg").slideUp("fast");
		}
	});
	$("#allmenu").click(function(){
		if($(window).width() < 1025){
			$("#menu-con").stop().slideToggle();
			$("#menu-con > ul > li > a").removeClass("on");
			$(".deapth2").stop().slideUp();
		}
	});*/
	
	/* jaseo - 2020.04.07. 레이어팝업 관련 내용 수정*/
	$(".art-box .ab-right .view-detail").click(function(){
		//$(".pop-bg").show();
		//$(".pop-view").show();
		$(".popup-box").show();
		$(".popup-content").show();
		$("body").css({"overflow":"hidden"});
	});
	
	/* jaseo - 2020.04.07. 레이어팝업 관련 내용 수정*/
	$("#content").on('click', '.popup-box .pv-title .pop-view-close', function(){
		//$(".pop-bg").hide();
		//$(".pop-view").hide();
		$(".popup-box").hide();
		$(".popup-content").hide();
		$("body").css({"overflow":"auto"});
	});
		

	$(".year_list ul > li > a").click(function(){
		$(".pop-bg").show();
		$(".pop-view").show();
		$("body").css({"overflow":"hidden"});
	});


	var sw=true;
	$('.footer_gs > a').click(function(){
		sw=!sw;
		if(sw==true){
			$('.footer_gs .family_list').hide();
		} else {
			$('.footer_gs .family_list').show();
			
		}
	});
	$('.family_list > li > a').click(function(){
		$('.footer_gs .family_list').hide();
		sw=!sw;
	});


	var slS = 'destroy';
	var sl2 = [];
	function sl_mct(){
		if( !sl2.length ){
			if ($(window).width() <= 768 ){
				$(".mv_bg").each(function(j){
					sl2[j] = $(".mv_bg").bxSlider({
						minSlides: 1,
						maxSlides: 1,
						moveSlides: 1,
						slideWidth: 1000,
						auto: true,
						pause: 3000,
						autoControls: true,
					});
					slS = 'build';
				});
			}
		}else{
				if ($(window).width() <= 768 && slS == 'destroy') {
					$.each(sl2, function(j){
						this.reloadSlider();
					});
					slS = 'build';
				} else if ($(window).width() > 768 && slS == 'build') {
					$.each(sl2, function(j){
						$.when(this.destroySlider()).then(function(){
							setTimeout(function(){
								$(".mv_bg").removeAttr('style').find("li").removeAttr('style');
							}, 100);
						});
					});
					slS= 'destroy';
				}
		}
	}
	sl_mct();


	var slSa = 'destroy';
	var slg = [];
	function sl_gs(){

		var mgs_l = $(".mgs_slid > li").length;
		if(mgs_l <= 1){
			setTimeout(function(){
				$(".mgs_wid .bx-controls").css({"display":"none"})
			}, 100);
		}

		if( !slg.length ){
			if ($(window).width() >= 1024 ){
				$(".mgs_slid").each(function(j){
					slg[j] = $(".mgs_slid").bxSlider({
						auto: true,
						pause: 111000,
						autoControls: true,
						infiniteLoop: true
					});
					slSa = 'build';
				});
			}
		}else{
				if ($(window).width() >= 1024 && slSa == 'destroy') {
					$.each(slg, function(j){
						this.reloadSlider();
					});
					slSa = 'build';
				} else if ($(window).width() < 1024 && slSa == 'build') {
					$.each(slg, function(j){
						$.when(this.destroySlider()).then(function(){
							setTimeout(function(){
								$(".mgs_slid").removeAttr('style').find("li").removeAttr('style');
							}, 100);
						});
					});
					slSa= 'destroy';
				}
		}
	}
	sl_gs();


	var slSb = 'destroy';
	var slGa = [];
	function sl_ga2(){

		var mgs_l = $(".mgs_slid4 > li").length;
		if(mgs_l <= 1){
			setTimeout(function(){
				$(".mgs_wid .bx-controls").css({"display":"none"})
			}, 100);
		}

		if( !slGa.length ){
			if ($(window).width() >= 1024 ){
				$(".mgs_slid4").each(function(j){
					slGa[j] = $(".mgs_slid4").bxSlider({
						auto: true,
						pause: 111000,
						autoControls: true,
						infiniteLoop: true,
						pager: false
					});
					slSb = 'build';
				});
			}
		}else{
				if ($(window).width() >= 1024 && slSb == 'destroy') {
					$.each(slGa, function(j){
						this.reloadSlider();
					});
					slSb = 'build';
				} else if ($(window).width() < 1024 && slSb == 'build') {
					$.each(slGa, function(j){
						$.when(this.destroySlider()).then(function(){
							setTimeout(function(){
								$(".mgs_slid4").removeAttr('style').find("li").removeAttr('style');
							}, 100);
						});
					});
					slSb= 'destroy';
				}
		}
	}
	sl_ga2();


	var slSc = 'destroy';
	var slGw = [];
	function sl_ga4(){

		var mgs_l = $(".mgs_slid5 > li").length;
		if(mgs_l <= 1){
			setTimeout(function(){
				$(".mgs_wid .bx-controls").css({"display":"none"})
			}, 100);
		}

		if( !slGw.length ){
			if ($(window).width() >= 1024 ){
				$(".mgs_slid5").each(function(j){
					slGw[j] = $(".mgs_slid5").bxSlider({
						auto: true,
						pause: 111000,
						autoControls: true,
						infiniteLoop: true,
						pager: false
					});
					slSc = 'build';
				});
			}
		}else{
				if ($(window).width() >= 1024 && slSc == 'destroy') {
					$.each(slGw, function(j){
						this.reloadSlider();
					});
					slSc = 'build';
				} else if ($(window).width() < 1024 && slSc == 'build') {
					$.each(slGw, function(j){
						$.when(this.destroySlider()).then(function(){
							setTimeout(function(){
								$(".mgs_slid5").removeAttr('style').find("li").removeAttr('style');
							}, 100);
						});
					});
					slSc= 'destroy';
				}
		}
	}
	sl_ga4();




	$(window).on('resize', function() {
		sl_mct();
		sl_gs();
	});

	 $('.bot_slide').bxSlider({
        slideMargin: 10,
        autoReload: true,
        useCSS: false,
       // easing:'easeInOutCubic',
        pager: false,
        controls: true,
        auto:true,
        speed:500,
        pause:3000,
        maxSlides:6,
		minSlides:2,
		slideWidth: 187,
        moveSlides: 1
    });



	/*$(".sub-tab2 li").width($(".tab2").width() / $(".sub-tab2 li").length);*/


	/**/
	$('.slider-sub01').bxSlider({ 
		adaptiveHeight: true,
		startSlides: 0, 
		slideMargin: 0
	});


	/*go-top*/
	$("#go-top").click(function() {
		$("html,body").animate({scrollTop:0}, 500);
	}); 
	$(window).scroll(function(){
		if ($(window).scrollTop()>200) { 
			$('#go-top').css({'display':'block'})
		}else{
			$('#go-top').css({'display':'none'})
		};
	});
	/*go-top*/

	/*popup*/
	$(".popup-btn01").click(function(){ 
		$(".popup-box").css({"display":"block"}); 
	});
	$(".popup-btn").click(function(){
		$(".bmc5-popup").css({"display":"block"}); 
	});
	$(".bmc5-popup .close").click(function(){
		$(".bmc5-popup").css({"display":"none"}); 
	});
	$(".popup-btn02").click(function(){
		$(".bmc5-popup .02").css({"display":"block"}); 
	});
	$(".bmc5-popup .close").click(function(){
		$(".bmc5-popup02").css({"display":"none"}); 
	});
	$(".popup-btn02").click(function(){
		$(".main-item").css({"display":"none"}); 
	});

		/*0403s*/
			$(".popup-btn03").click(function(){
				$(this).parents(".board-tit").next(".main-board-popup").css({"display":"block"}); 
			});
			$(".main-board-popup .close").click(function(){
				$(this).parents(".main-board-popup").css({"display":"none"}); 
			});
		/*0403e*/

		/*0413s*/
			$(".sch-div").mouseover(function(){
				$(".sch-text").animate({width:"220px"}, 100);
				$(".sch-text").css({"margin-right":"50px"});
			});
			$(".sch-div").mouseleave(function(){
				$(".sch-text").animate({width:"0px"}, 100);
				$(".sch-text").css({"margin-right":"0px"});
				//$(".sch-text").css({"display":"none"});
			});
			/*0420 추가*/
			$(".sch-text").focus(function(){
				$(".sch-text").animate({width:"220px"}, 100);
				$(".sch-text").css({"margin-right":"50px"});
			});
			$(".sch-text").blur(function(){
				$(".sch-text").animate({width:"0px"}, 100);
				$(".sch-text").css({"margin-right":"0px"});
				//$(".sch-text").css({"display":"none"});
			});
		/*0413e*/




	/*popup*/



	$(".owl-m1.owl-carousel").owlCarousel({
		items:4,
		margin:20,
		loop:true,
		nav:true,
		touchDrag:true,
		mouseDrag:false,
		autoplay:false,
		autoplayTimeout:3000,
		autoplayHoverPause:true,
		responsiveClass:true,
		responsive:{
			1:{
			  items:1, 
			  margin:0,
			center:false 
			},
			512:{
			  items:2, 
			  margin:20, 
			  center:false 
			},
			1024:{
			  items:4, 
			  margin:20, 
			  center:false 
			}
		}
	});
	$(".owl-m2.owl-carousel").owlCarousel({
		items:4,
		margin:2,
		loop:true,
		nav:true,
		touchDrag:false,
		mouseDrag:false,
		autoplay:false,
		autoplayTimeout:3000,
		autoplayHoverPause:true,
		responsiveClass:true,
		responsive:{
			1:{
			  items:1, 
			  margin:0,
			center:false 
			},
			512:{
			  items:2, 
			  margin:20, 
			  center:false 
			},
			1024:{
			  items:4, 
			  margin:20, 
			  center:false 
			}
		}
	});

	$(".owl-m3.owl-carousel").owlCarousel({
		items:4,
		margin:2,
		loop:true,
		nav:true,
		touchDrag:false,
		mouseDrag:false,
		autoplay:false,
		autoplayTimeout:3000,
		autoplayHoverPause:true,
		responsiveClass:true,
		responsive:{
			1:{
			  items:1, 
			  margin:0,
			center:false 
			},
			512:{
			  items:2, 
			  margin:20, 
			  center:false 
			},
			1024:{
			  items:4, 
			  margin:20, 
			  center:false 
			}
		}
	});
	$(".owl-dot a,.owl-nav a").attr("href","#n");
	$(".owl-m1 .owl-dot:eq(0) a").text("1페이지");
	$(".owl-m1 .owl-dot:eq(1) a").text("2페이지");
	$(".owl-m1 .owl-dot:eq(2) a").text("3페이지");
	$(".owl-m2 .owl-dot:eq(0) a").text("1페이지");
	$(".owl-m2 .owl-dot:eq(1) a").text("2페이지");








});


$(window).on("load resize", function(){
	if($(window).width() >= "1024"){
		$("#menu-con").removeAttr("style");
		$(".deapth1").removeAttr("style");
		$(".deapth2").removeAttr("style");
		$(".deapth3").removeAttr("style");
		$(".deapth1 > li > a").removeClass("on");
		$(".deapth2 > ul > li > a").removeClass("ov");
		
	}

	/*$(".sub-tab2 li").width($(".sub-tab2").width() / $(".sub-tab2 li").length);*/



});

