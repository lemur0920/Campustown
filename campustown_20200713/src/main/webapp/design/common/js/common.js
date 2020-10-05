//jQuery ajax IE cache fix
jQuery.ajaxSetup({cache: false});
//IE console fix
if(!window.console)window.console = {log: function(){}};

//statistics insert
function statisticsInsert(){
	if( !GLOBAL.menuId ){
		return;
	}
	$.ajax({
		url : GLOBAL.APP_PATH + '/statistics' + GLOBAL.API_PATH + '/insert'
		, type : 'post'
		, data : {
			menuId : GLOBAL.menuId
			
		}
	});
}

jQuery(function($){
	//통계자동
	statisticsInsert();
	
});

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
 * 한글문자 포함여부를 반환한다.
 * @param String string
 * @returns Boolean
 */
function hasKoreanChars(string){
	return string.match(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g);
}

//이메일 검사
function isEmail(value){
	return value.search(/^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/) != -1;
}

/**
 * 폰트 사이즈 조정
 */
var vbm = 100;

function plus() {
			vbm = vbm + 20;
			if(vbm >= 500) vbm = 500;
			processes();
	}
function processes(){
		document.body.style.zoom = vbm + '%';
	
	}

function reset(){
	vbm = 100;
	processes();
}

function minus() {
		vbm = vbm - 10;
		processes();
}
//===폰트 사이즈 조정 ===


/**
 * 순수 숫자만 입력가능
 */
var validCheck = {
    keyDown : function (e) {
        var key;
        if(window.event)
            key = window.event.keyCode; //IE
        else
            key = e.which; //firefox
        var event;
        if (key == 0 || key == 8 || key == 46 || key == 9){
            event = e || window.event;
            if (typeof event.stopPropagation != "undefined") {
                event.stopPropagation();
            } else {
                event.cancelBubble = true;
            }   
            return;
        }
        if (key < 48 || (key > 57 && key < 96) || key > 105 || e.shiftKey) {
            e.preventDefault ? e.preventDefault() : e.returnValue = false;
        }
    },        
    keyUp : function (e) {
        var key;
        if(window.event)
            key = window.event.keyCode; //IE
        else
            key = e.which; //firefox
        var event;
        event = e || window.event;        
        if ( key == 8 || key == 46 || key == 37 || key == 39 ) 
            return;
        else
            event.target.value = event.target.value.replace(/[^0-9]/g, "");
    },
    focusOut : function (ele) {
        ele.val(ele.val().replace(/[^0-9]/g, ""));
    }
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
					html += '<img src=\"/design/common/images/asset/btn_del.png\" class=\"deleteBtn ml-2 fa fa-times text-danger\" style=\"cursor: pointer;\" data-value=\"ck' + chkValue + '\"> ';
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
