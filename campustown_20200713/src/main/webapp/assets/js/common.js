//jQuery ajax IE cache fix
jQuery.ajaxSetup({cache: false});
//IE console fix
if(!window.console)window.console = {log: function(){}};

/**
 * 1000단위마다 comma 표시
 * @param int x
 * @returns
 */
function numberWithCommas(x) {
    var parts = x.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
}

/**
 * 숫자만 입력되도록 하기
 * @param e
 * @returns
 */
function numkeyCheck(e) { 
	var keyValue = event.keyCode; 
	if( ((keyValue >= 48) && (keyValue <= 57)) ) return true; 
	else return false; 
}

/**
 * Date 객체의 시간을 정오로 고정시킨다
 * @param Date date
 * @returns {Date}
 */
function makeNoon(date){
	return new Date(date.getFullYear(), date.getMonth(), date.getDate(), 12, 0, 0);
}

/**
 * 단위 절삭 ex) where 이 10이면 10단위 절삭함 111 -> 110
 * @param number
 * @param int where
 * @returns {Number}
 */
function floored(number, where){
	return Math.floor(number / where) * where;
}

/**
 * 두 Date객체 사이의 차이나는 일수를 계산한다. 
 * @param Date date1
 * @param Date date2
 * @returns {Number}
 */
function getIntervalDays(date1, date2){
	return Math.abs(date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);
}

/**
 * 한글문자 포함여부를 반환한다.
 * @param String string
 * @returns Boolean
 */
function hasKoreanChars(string){
	return string.match(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g);
}

jQuery(function($){
	
	//숫자만 입력받기
	$('.number').on('keypress', function (event) {
		if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8) {
			event.preventDefault(); 
		} 
	});

});

//미디어 라이브러리 셀렉터 팝업
jQuery.fn.mediaBrowser = function(option){
	
	var defaultOption = {
		multiple : false,// 기본적으로 한개만 선택가능함, true 로 바꾸면 복수 선택이 가능해지고 json은 배열형태로 줌
		max: 0,//첨부가능개수
		fileItemCssSelector: null,//첨부가능개수 체크할대 사용되는 파일카운트용 css셀렉터
		initTab: null, //미디어브라우저 열릴때 선택되어져 있는 IMAGE, DOCUMENT, VIDEO, AUDIO, ETC
		initModule: 'media', //모듈값없으면 디폴트로 선택
		callback : function(){
			alert('mediaBrowser callback function is mandatory!');
		}	
	};
	
	this.each(function(idx, el){
		$(this).click(function(e){
			e.preventDefault();
			option = jQuery.extend( defaultOption, option );
			
			//첨부개수 제한 추가
			if( option.max > 0 && option.fileItemCssSelector ){
				if( $(option.fileItemCssSelector).size() >= option.max ){
					alert(option.max + '개까지 첨부할 수 있습니다.');
					return;					
				}
			}
			//최초탭 파라메터 설정
			//var fileMediaTypeKV = '';
			//var fileModuleKV = '';
			var fileMediaParam = '?';
			
			if( option.initTab ){
				fileMediaParam = fileMediaParam + 'fileMediaType=' +  option.initTab + '&';
			}
			if( option.initModule ){
				fileMediaParam = fileMediaParam + 'fileModule=' +  option.initModule;
			}
			
			
			
			option.triggerElement = e.target;
			window.mediaBrowser = {};
			window.mediaBrowser.option = option;
			window.mediaBrowser.option.lightbox = $('<iframe frameborder="0" style="width: 95%; height: 90%;border: 0, background-color: #fff;" src="' + GLOBAL.ADMIN_PATH + '/media/browser.do' + fileMediaParam + '"></iframe>').lightbox_me({
				centered : true
				, destroyOnClose : true
				, modalCSS : null
				, onClose : function(){
					//...
				}
			});
			
		});
	});
};

//첨부파일 추가
jQuery.fn.multiSelector = function(option){

	var defaultOption = {
		multiple : false,// 기본적으로 한개만 선택가능함, true 로 바꾸면 복수 선택이 가능해지고 json은 배열형태로 줌
		max: 0,//첨부가능개수
		list_target_id:'fileList',//첨부목록을 뿌려줄 엘리먼트
		fileItemCssSelector: 'file_add',//첨부가능개수 체크할대 사용되는 파일카운트용 css셀렉터
		list_delete_fileid: 'deleteFileList',//첨부가능개수 체크할대 사용되는 파일카운트용 css셀렉터
		callback : function(){
			alert('multiSelector callback function is mandatory!');
		}	
	};
	//input의 name을 추출하고 추가시 name+index 로 name 설정에 사용한다.
	var inputFileName = $(this).attr('name').split('[')[0];
	var inputParent = $(this).parent();
	var chkValue = 0;
	var deleCnt = 0;

	option = jQuery.extend( defaultOption, option );
//	this.each(function(idx, el){
		
		if( $(this).prop('tagName') == 'INPUT' && $(this).prop('type') == 'file' ){
			
			$(inputParent).on('change','input[type=file]', function(e){
				e.preventDefault();
				
				if($(this).val() != ''){
					//첨부개수 제한 추가
					if( option.max == 0 ){
						alert('첨부기능을 사용할 수 없습니다.');
						$(this).val('');
						return;					
					}
					//첨부개수 제한 추가
					if( option.max > 0 && option.fileItemCssSelector ){
						if( $('.'+option.fileItemCssSelector).size() >= option.max ){
							alert(option.max + '개까지 첨부할 수 있습니다.');
							$(this).val('');
							return;					
						}
					}
					
					// New file input 추가
					var cloneElements = $(this).clone();
					$(cloneElements).val('');
					$(inputParent).prepend(cloneElements);
					
					$(this).attr('data-chkvalue','ck'+chkValue);
					$(this).css('display', 'none');
					
					//list 추가
					var html = '<div class="' + option.fileItemCssSelector + '">';
					html += '<span>' + $(this).val() + '</span> ';
					html += '<i class=\"deleteBtn ml-2 fa fa-times text-danger\" style=\"cursor: pointer;\" data-value=\"ck' + chkValue + '\"></i> ';
					html += '</div> ';
					
					$('#'+option.list_target_id).append($(html).fadeIn(800));
					
					chkValue++;
					adjustFileIndex();
				}
			});
			
		}
		

		//첨부제거
		$('#'+option.list_target_id).on('click','.deleteBtn', function(e){
			e.preventDefault();

			//삭제 이미지의 데이터값
			var imgValue = $(this).data('value');
			//삭제할 파일의 아이디
			var deleteFileId = $(this).data('fileid');
			
			$(e.target).closest($('.'+option.fileItemCssSelector)).remove();
			if(deleteFileId != null && deleteFileId != ''){
				$('#deleteFileList').append('<input type="hidden" name="fileInfoDeleteIds[' + deleCnt + ']" value="' + deleteFileId + '"/>');
				deleCnt++;
			}
			
			if(imgValue != null && imgValue != ''){
				$('input[type=file]', inputParent).each(function(idx, el){
					if($(this).data('chkvalue') == imgValue){
						$(this).remove();
					}
				});
			}
			
			adjustFileIndex();
		});
		
		//인덱스 정리
		function adjustFileIndex(){

			$('input[type=file]', inputParent).each(function(idx, el){
				$(this).attr('name', inputFileName + '[' + idx + ']');
				$(this).attr('id', inputFileName + idx);
			});
		}
//	});
};