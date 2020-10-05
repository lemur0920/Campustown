/**
 * 게시물 추천
 * 
 */
jQuery(function($){
	
	//게시판 게시물 추천하기 버튼 클릭
	$('body').on('click', '.ba-recommend', function(e){
		e.preventDefault();
		var bcId = null;
		var baId = null;
		
		if( $(this).data('bcid') ){
			bcId = $(this).data('bcid');
		}
		if( $(this).data('baid') ){
			baId = $(this).data('baid');
		}
		
		if( !bcId || !baId ){
			console.log('recommend : bcId & baId is required!');
		} else {
			//쿠키 체크
			if( $.cookie('ba_reco_' + GLOBAL.siteId + '_' + baId) ){
				alert('이미 추천하셨습니다.');
			} else {
				$.ajax({
					url : GLOBAL.APP_PATH + '/board/' + bcId + GLOBAL.API_PATH + '/recommend'
					, type : 'post'
					, dataType: 'json'
					, data: {
						baId : baId
					}
				}).done(function(result){
					alert(result.text);
					if(result.success){
						location.reload();
					}
				}).fail(function(result){
					alert('추천 실패');
				});
			}
		}
	});
	
	
	//아카이브 추천하기 버튼 클릭
	$('body').on('click', '.archive-recommend', function(e){
		e.preventDefault();
		var catId = null;
		var arcId = null;
		
		if( $(this).data('catid') ){
			catId = $(this).data('catid');
		}
		if( $(this).data('arcid') ){
			arcId = $(this).data('arcid');
		}
		
		if( !catId || !arcId ){
			console.log('recommend : catId & arcId is required!');
		} else {
			//쿠키 체크
			if( $.cookie('arc_reco_' + GLOBAL.siteId + '_' + arcId) ){
				alert('이미 추천하셨습니다.');
			} else {
				$.ajax({
					url : GLOBAL.APP_PATH + '/archive/' + catId + GLOBAL.API_PATH + '/recommend'
					, type : 'post'
						, dataType: 'json'
							, data: {
								arcId : arcId
							}
				}).done(function(result){
					alert(result.text);
					if(result.success){
						location.reload();
					}
				}).fail(function(result){
					alert('추천 실패');
				});
			}
		}
	});
	
	
	//댓글 추천하기 버튼 클릭
	$('.commentWrapper').on('click', '.comm-recommend', function(e){
		e.preventDefault();
		var cmtId = null;
		
		if( $(this).data('cmtid') ){
			cmtId = $(this).data('cmtid');
		}
		
		if( !cmtId ){
			console.log('recommend : cmtId is required!');
		} else {
			//쿠키 체크
			if( $.cookie('comm_reco_' + GLOBAL.siteId + '_' + cmtId) ){
				alert('이미 추천하셨습니다.');
			} else {
				$.ajax({
					url : GLOBAL.APP_PATH + '/comment/' + cmtId + GLOBAL.API_PATH + '/recommend'
					, type : 'post'
					, dataType: 'json'
					, data: {
						cmtId : cmtId
					}
				}).done(function(result){
					alert(result.text);
					if(result.success){
						location.reload();
					}
				}).fail(function(result){
					alert('추천 실패');
				});
			}
		}
	});
	
	
});