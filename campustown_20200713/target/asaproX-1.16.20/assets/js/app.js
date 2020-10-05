/*
 Template Name: Upcube - Bootstrap 4 Admin Dashboard
 Author: Themesdesign
 Website: www.themesdesign.in
 File: Main js
 */

jQuery(function($){
	
	!function ($) {
		"use strict";
		
		var MainApp = function () {
			this.$body = $("body"),
			this.$wrapper = $("#wrapper"),
			this.$btnFullScreen = $("#btn-fullscreen"),
			this.$leftMenuButton = $('.button-menu-mobile'),
			this.$menuItem = $('.has_sub > a'),
      /*3댑스추가*/
			this.$menuThirdItem = $('.has_third > a')
			
		};
		
		//reques parameter 추출
		var urlParam = function(name){
		    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
		    if (results==null){
		       return null;
		    }
		    else{
		       return decodeURIComponent(results[1]) || 0;
		    }
		}

		var activeUrl = urlParam("activeUrl");	//파라메터 activeUrl 를 추출
		//activeUrl = decodeURIComponent(activeUrl);
		
		var currentUrl = window.location.href.split('?')[0];	//현제 메뉴의 파라메터를 제거한 url
		

		//alert(activeUrl);
		
		//scroll
		MainApp.prototype.initSlimscroll = function () {
			$('.slimscrollleft').slimscroll({
				height: 'auto',
				position: 'right',
				size: "10px",
				color: '#9ea5ab'
			});
		},
		//left menu
		MainApp.prototype.initLeftMenuCollapse = function () {
			var $this = this;
			this.$leftMenuButton.on('click', function (event) {
				event.preventDefault();
				$this.$body.toggleClass("fixed-left-void");
				$this.$wrapper.toggleClass("enlarged");
			});
		},
		//left menu
		MainApp.prototype.initComponents = function () {
			$('[data-toggle="tooltip"]').tooltip();
			$('[data-toggle="popover"]').popover();
		},
		//full screen
		MainApp.prototype.initFullScreen = function () {
			var $this = this;
			$this.$btnFullScreen.on("click", function (e) {
				e.preventDefault();
				
				if (!document.fullscreenElement && /* alternative standard method */ !document.mozFullScreenElement && !document.webkitFullscreenElement) {  // current working methods
					if (document.documentElement.requestFullscreen) {
						document.documentElement.requestFullscreen();
					} else if (document.documentElement.mozRequestFullScreen) {
						document.documentElement.mozRequestFullScreen();
					} else if (document.documentElement.webkitRequestFullscreen) {
						document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
					}
				} else {
					if (document.cancelFullScreen) {
						document.cancelFullScreen();
					} else if (document.mozCancelFullScreen) {
						document.mozCancelFullScreen();
					} else if (document.webkitCancelFullScreen) {
						document.webkitCancelFullScreen();
					}
				}
			});
		},
		//full screen
		MainApp.prototype.initMenu = function () {
			var $this = this;
			$('.content').on('click', 'a,button', function() {

				if($(this).get(0).tagName  == 'A'){
					//alert(activeUrl);
					//파라메터 activeUrl을 전달한다
					if($(this).attr('href') != null  && $(this).attr('href').indexOf('javascript:') == -1 && $(this).attr('href').indexOf('#') == -1){
						//관리자 페이지 url 인 경우만 activeUrl 파라메터를 붙여준다.
						if($(this).attr('href').indexOf(GLOBAL.ADMIN_PATH) >= 0 ){
							if(activeUrl != null ){
								if($(this).attr('href').indexOf('?') != -1 ){
									//a테그에 activeUrl 파라메터를 강제로 입력하여 메뉴를 찾아가 가게하는경우
									if($(this).attr('href').indexOf('activeUrl') == -1){
										$(this).attr('href', $(this).attr('href') + '&activeUrl=' + activeUrl);
									}
								}else{
									$(this).attr('href', $(this).attr('href') + '?activeUrl=' + activeUrl);
								}
							}
						}
					}
				}else if($(this).get(0).tagName  == 'BUTTON'){
					//파라메터 activeUrl을 전달한다
					//alert(decodeURIComponent(activeUrl));
					//alert(encodeURIComponent(activeUrl));
					if($(this).closest('form').attr('action') != undefined){
						if(activeUrl != null ){
							$(this).closest('form').append("<input type='hidden' name='activeUrl' value='" + decodeURI(activeUrl) +"'/>");
							/*
							if($(this).closest('form').attr('action').indexOf('?') == -1 ){
								$(this).closest('form').attr('action', $(this).closest('form').attr('action') + '?activeUrl=' + activeUrl);
							}else{
								$(this).closest('form').attr('action', $(this).closest('form').attr('action') + '&activeUrl=' + activeUrl);
							}*/
						}
						//$(this).closest('form').append('<input type=\'hidden\' name=\'activeUrl\' value=\'' + activeUrl + '\'>')
					}
				}else{
					
				}
				
				//alert($(this).attr('href'));
			});
			
			$this.$menuItem.on('click', function () {
				var parent = $(this).parent();//ul>li
				var sub = parent.find('> ul');//ul>li>ul
				
				if (!$this.$body.hasClass('sidebar-collapsed')) {
					if (sub.is(':visible')) {
						sub.slideUp(300, function () {
							parent.removeClass('nav-active');
							$('.body-content').css({height: ''});
							adjustMainContentHeight();
						});
					} else {
						visibleSubMenuClose();
				/*3댑스추가*/
						visibleThirdMenuClose
						parent.addClass('nav-active');
						sub.slideDown(300, function () {
							adjustMainContentHeight();
						});
					}
				}
				return false;
			});
      /*3댑스추가*/
			$this.$menuThirdItem.on('click', function () {
				var parent = $(this).parent();//ul>li>ul>li
				var sub = parent.find('> ul');//ul>li>ul>li>ul
				
				if (!$this.$body.hasClass('sidebar-collapsed')) {
					if (sub.is(':visible')) {
						sub.slideUp(300, function () {
							parent.removeClass('nav-active');
							$('.body-content').css({height: ''});
							adjustMainContentHeight();
						});
					} else {
						visibleThirdMenuClose();
						parent.addClass('nav-active');
						sub.slideDown(300, function () {
							adjustMainContentHeight();
						});
					}
				}
				return false;
			});
			
			//inner functions
			function visibleSubMenuClose() {
				$('.has_sub').each(function () {
					var t = $(this);
					if (t.hasClass('nav-active')) {
						t.find('> ul').slideUp(300, function () {
							t.removeClass('nav-active');
						});
					}
				});
			}
      /*3댑스추가*/
			function visibleThirdMenuClose() {
				$('.has_third').each(function () {
					var t = $(this);
					if (t.hasClass('nav-active')) {
						t.find('> ul').slideUp(300, function () {
							t.removeClass('nav-active');
						});
					}
				});
			}
			
			function adjustMainContentHeight() {
				// Adjust main content height
				var docHeight = $(document).height();
				if (docHeight > $('.body-content').height())
					$('.body-content').height(docHeight);
			}
		},
		MainApp.prototype.activateMenuItem = function () {
			// === following js will activate the menu in left side bar based on url ====
			
			var match = false;
			//메뉴 목록과 현제 url이 일치하는 경우
			$("#sidebar-menu a").each(function () {
				if (this.href == window.location.href) {
					//메뉴네비처리 
					active($(this));
					
					//left에 존제하는 메뉴인경우 activeUrl에 현제 url을 담아둔다.
					activeUrl = window.location.href;
					match = true;
					return false;
				}
				
			});
			
			if(!match){
				//메뉴 목록과 현제 url이 일치하는 경우가 없어 파라메터로 넘겨받은 activeUrl과 일치하는 경우와 매칭
				$("#sidebar-menu a").each(function () {
					if (this.href == activeUrl) {
						//메뉴네비처리 
						active($(this));
						//activeUrl에 현제 매핑된 메뉴의 url을 담아둔다.
						activeUrl = activeUrl;
						match = true;
						return false;
					} 
					
				});
				//alert(activeUrl);
			}
			
			if(!match){
				//컨트롤러에서 리다이렉트된 경우 activeUrl 파라메터는 없고 다른 파라메너타만 있어 메뉴목록과 매칭되지않는경우
				$("#sidebar-menu a").each(function () {
					if (this.href == currentUrl) {
						//메뉴네비처리 
						active($(this));
						//activeUrl에 현제 매핑된 메뉴의 url을 담아둔다.
						activeUrl = currentUrl;
						match = true;
						return false;
					} 
					
				});
				//alert(activeUrl);
			}
			
			//메뉴네비처리 함수
			function active(matchItem){
				matchItem.addClass("active");
				matchItem.parent().addClass("active"); // add active to li of the current link
				matchItem.parent().parent().prev().addClass("active"); // add active class to an anchor
				matchItem.parent().parent().parent().addClass("active"); // add active class to an anchor
				//3댑스추가
				var isThird = matchItem.parent().parent().parent().hasClass("has_third");
				if(isThird){
					matchItem.parent().parent().parent().addClass("nav-active"); // add active class to an anchor
					matchItem.parent().parent().parent().parent().prev().addClass("active"); // add active class to an anchor
					matchItem.parent().parent().parent().parent().parent().addClass("active"); // add active class to an anchor
					matchItem.parent().parent().parent().parent().prev().click(); // click the item to make it drop
				}else{
					matchItem.parent().parent().prev().click(); // click the item to make it drop
				}
			}
		},
		MainApp.prototype.Preloader = function () {
			$(window).load(function() {
				$('#status').fadeOut();
				$('#preloader').delay(350).fadeOut('slow');
				$('body').delay(350).css({
					'overflow': 'visible'
				});
			});
		},
		MainApp.prototype.init = function () {
			this.initSlimscroll();
			this.initLeftMenuCollapse();
			this.initComponents();
			this.initFullScreen();
			this.initMenu();
			this.activateMenuItem();
			this.Preloader();
		},
		//init
		$.MainApp = new MainApp, $.MainApp.Constructor = MainApp
	}(window.jQuery),
	
//initializing
	function ($) {
		"use strict";
		$.MainApp.init();
	}(window.jQuery);
});