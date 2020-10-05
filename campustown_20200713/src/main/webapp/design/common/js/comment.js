jQuery(function($){
	
	if( !window.Asapro ){
		window.Asapro = {};
	}
	
	if( !GLOBAL.APP_PATH || !GLOBAL.API_PATH ){
		console.log('can not find GLOBAL.APP_PATH or GLOBAL.API_PATH...');
	}
	
	window.Asapro.SnsComment = function(){
		this.current_service = $('[name=cmtLoginType]').val();;
	};
	
	//메세지 프로퍼티
	var messages = {
		'ko_KR' : {
			'FBE001' : '페이스북 사용자정보를 조회하지 못하였습니다.'	
			, 'FBE002' : '페이스북 거부됨 denied user'
			, 'TWE001' : '트위터 리퀘스트토큰 신청 실패'
			, 'TWE002' : '트위터 요청오류'
			, 'GPE001' : '댓글작성자 정보가 없습니다. 댓글 작성자 정보를 선택해주세요.'
			, 'NVE001' : '네이버 사용자정보를 조회하지 못하였습니다.'
			, 'DAE001' : '다음 사용자정보를 조회하지 못하였습니다.'
			, 'CME001' : '로그인 후 작성해 주세요.'
			, 'CMM001' : '현재 로그인중입니다.'
			, 'CMM003' : '댓글이 작성되었습니다.'
			, 'CMM004' : '댓글이 작성되었습니다. 작성된 댓글은 관리자 검토 후 게재됩니다.'
		}
		, 'en_US' : {
			'FBE001' : 'Failed to get Facebook user information.'	
			, 'FBE002' : 'Denied by user'
			, 'TWE001' : 'Failed Twitter Request.'
			, 'TWE002' : 'Twitter Request Error.'
			, 'GPE001' : 'Comment profile was not selected. Please choose comment profile.'
			, 'NVE001' : 'Failed to get Naver user information.'
			, 'DAE001' : 'Failed to get Daum user information.'
			, 'CME001' : 'Comment profile was not selected. Please choose comment profile.'
			, 'CMM001' : 'Currently logged in profile.'
			, 'CMM003' : 'Your comment was registered.'
			, 'CMM004' : 'Your comment was registered. Your comment has been queued for moderation by site administrator.'
		}
	};
	
	//--------------------------------------------------------------------------------
	// 시작 : 팝업상태 또는 SNS쪽에서 리다이렉트로 넘어왔을 경우에만 해당되는 소스임
	//--------------------------------------------------------------------------------
	var location_hash = location.hash ? location.hash : '#' + location.href.split('#')[1];
	if(/#sync/.test(location_hash)){
		var hashData = /^#sync_([_a-zA-Z0-9]+)$/.exec( location_hash );
		//sns 인증 후 싱크
//		opener.asaproSnsComment.sync(hashData[1]);
		//오프너 핸들링 후 창 닫음.
		self.close();
	} else if(/#error/.test(location_hash)){
		var hashData = /^#error_([a-zA-Z0-9]+)$/.exec( location_hash );
		//에러메세지 출력 후 창닫음
		alert(messages.ko_KR[hashData[1]]);
		self.close();
	}
	//--------------------------------------------------------------------------------
	// 끝 : 팝업상태 또는 SNS쪽에서 리다이렉트로 넘어왔을 경우에만 해당되는 소스임
	//--------------------------------------------------------------------------------
	
	window.Asapro.SnsComment.prototype = {
		init : function(){
			this.addEventListeners();
		}
		//현재 페이지url
		, getCurrentUrl : function(){
			return encodeURIComponent(location.href);
			//return location.href;
		}
		//이벤트 리스너 부착
		, addEventListeners : function(){
			//글쓰기
			$('.comment-submit').click( $.proxy(function(e){
				e.preventDefault();
				var formContext = $(e.target).closest('form');
				if(this.current_service <= 0){
					alert(messages[GLOBAL.SITE_LOCALE]['CME001']);
					return;
				}
				
				this.writeNewComment.call(this, this.refresh, formContext);
			}, this) );
			
			//로그인 안한 상태에서 텍스트 에어리어 클릭
			$('textarea[name=cmtContent]').click( $.proxy(function(e){
				if(this.current_service <= 0){
//					alert(messages[GLOBAL.SITE_LOCALE]['CME001']);
//					return;
				}
			}, this) );
			$('textarea[name=cmtContent]').keyup( $.proxy(function(e){
				var content = $('textarea[name=cmtContent]').val();
			    $('#counter').html(content.length + " / 1000");    //글자수 실시간 카운팅

			}, this) );
			
		}
		//글쓰기
		, writeNewComment : function(callback, formContext){
			//var me = this;
			var cmtLoginType	= this.current_service;
			var cmtMenuId 		= $('[name=cmtMenuId]', formContext).val();
			var cmtModule 		= $('[name=cmtModule]', formContext).val();
			var cmtModuleSub 	= $('[name=cmtModuleSub]', formContext).val();
			var cmtModCategory 	= $('[name=cmtModCategory]', formContext).val();
			var cmtModItemId 	= $('[name=cmtModItemId]', formContext).val();
			var cmtPageUrl 		= $('[name=cmtPageUrl]', formContext).val();
			var cmtTitle 		= $('[name=cmtTitle]', formContext).val();
			var cmtContent 		= $.trim($('[name=cmtContent]', formContext).val());
			var csrf_token 		= $('[name=csrf_token]', formContext).val();
			var cmtParentId 		= $('[name=cmtParentId]', formContext).val();

			if( !cmtContent ){
				alert('댓글 내용을 입력해 주세요.');
				$('[name=cmtContent]', formContext)[0].focus();
				return;
			} else {
				if( cmtContent.length < 5 ){
					alert('댓글 내용은 5자 이상이어야 합니다.');
					return;
				}
				if( cmtContent.length > 1000 ){
					alert('댓글 내용은 1000자까지 입력가능합니다.');
					return;
				}
			}

			$.ajax({
				url : GLOBAL.APP_PATH + '/comment' + GLOBAL.API_PATH + '/new'
				, type : 'post'
				, dataType : 'json'
				, data : {
					menuId : cmtMenuId 
					, cmtLoginType : cmtLoginType
					, cmtModule : cmtModule
					, cmtModuleSub : cmtModuleSub
					, cmtModCategory : cmtModCategory
					, cmtModItemId : cmtModItemId
					, cmtPageUrl : cmtPageUrl
					, cmtTitle : cmtTitle
					, cmtContent : cmtContent
					, cmtParentId : cmtParentId ? cmtParentId : 0
//					, cmRating : cmRating
//					, cmGuestName : cmGuestName
//					, cmGuestEmail : cmGuestEmail
//					, cmGuestPassword : cmGuestPassword
					, csrf_token : csrf_token
				}
				, context : this
			}).done(function(data){
				if( data.success == 'true' ){
					if( GLOBAL.COMMENT_USE_MODERATE ){
						alert( this.getMessage('CMM004') );
						callback(data);
					} else {
						alert( this.getMessage('CMM003') );
						callback(data);
					}
				} else {
					if( data.error ){
						alert(this.getMessage(data.error));
					} else {
						alert('연결하지 못하였습니다.[error]');
					}
					return;
				}
			}).fail(function(data){
				alert('연결하지 못하였습니다.[failure]');
				return;
			});
		}
		, refresh : function(data){
			location.reload();
		}
		, getMessage : function( code ){
			if( messages[GLOBAL.SITE_LOCALE][code] ){
				return messages[GLOBAL.SITE_LOCALE][code];
			} else {
				return code;
			}
		}
		//이메일체크
		, isEmail: function(string){
			return string.search(/^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/) != -1;
		}
	};
	
	window.asaproSnsComment = new Asapro.SnsComment();
	window.asaproSnsComment.init();
	
	//시간 표시 변경 - jquery.timeago.js 플러그인이 필요하다!
	$.timeago.settings.strings = {
		prefixAgo: null,
        prefixFromNow: null,
        suffixAgo: "전",
        suffixFromNow: "지금으로부터",
        inPast: '오래',
        seconds: "조금",
        minute: "1분",
        minutes: "%d분",
        hour: "한시간",
        hours: "약 %d시간",
        day: "하루",
        days: "%d일",
        month: "1개월",
        months: "%d개월",
        year: "1년",
        years: "%d년",
        wordSeparator: " ",
        numbers: []	
	};
	$('.comment-item-regdate').timeago();
});