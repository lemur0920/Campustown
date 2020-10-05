/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	 config.language = 'ko';
	 config.uiColor = '#E8F3F8';
	 config.skin = 'office2013';	//office2013 , moonocolor
	
	// Default setting.
	/*
	config.toolbarGroups = [
	    { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
	    { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
	    { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
	    { name: 'forms' },
	    '/',
	    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
	    { name: 'links' },
	    { name: 'insert' },
	    '/',
	    { name: 'styles' },
	    { name: 'colors' },
	    { name: 'tools' },
	    { name: 'others' },
	    { name: 'about' }
	];
	
	config.toolbar = [
	['Source','-','Save','NewPage','Preview','-','Templates'],
	['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print','SpellChecker', 'Scayt'],
	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	['Form', 'Checkbox', 'Radio','TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],'/',
	['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
	['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],['Link','Unlink','Anchor'],
	['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],'/',
	['Styles','Format','Font','FontSize'],['TextColor','BGColor'],['Maximize', 'ShowBlocks','-','About']];
	
	
	config.language = 'ko';			//언어설정
	config.uiColor = '#EEEEEE';		//ui 색상
	config.height = '300px';		//Editor 높이  
	config.width = '777px';			//Editor 넓이
	config.contentsCss = ['/css/style.css'],['/css/main.css'];	//홈페이지에서 사용하는 Css 파일 인클루드
	config.font_defaultLabel = 'Gulim';	
	config.font_names='Gulim/Gulim;Dotum/Dotum;Batang/Batang;Gungsuh/Gungsuh/Arial/Arial;Tahoma/Tahoma;Verdana/Verdana';
	config.fontSize_defaultLabel = '12px';
	config.fontSize_sizes='8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;';
	config.enterMode =CKEDITOR.ENTER_BR;		//엔터키 입력시 br 태그 변경
	config.shiftEnterMode = CKEDITOR.ENTER_P;	//엔터키 입력시 p 태그로 변경
	config.startupFocus = true;					// 시작시 포커스 설정
	config.allowedContent = true;				// 기본적인 html이 필터링으로 지워지는데 필터링을 하지 않는다.
	config.filebrowserImageUploadUrl = '/include/editor/upload/upload.asp';		//이미지 업로드 경로 (설정하면 업로드 플러그인에 탭이생김)
	config.filebrowserFlashUploadUrl = '/include/editor/upload/upload.asp;		//플래쉬 업로드 경로 (설정하면 업로드 플러그인에 탭이생김)	
	config.toolbarCanCollapse = true;		//툴바가 접히는 기능을 넣을때 사용합니다.
	config.docType = "<!DOCTYPE html>";		//문서타입 설정

	config.extraAllowedContent = 'video[*]{*};source[*]{*}';		//video , embed 등 막힌 태그를 허용하게 하는 설정
	
	위 설정은 콜백 파일에서 최종으로 넘겨야하는 인자가 있습니다.

	첫번째 인수 : 넘어오는 값 그대로 리턴
	두번째 인수 : 보여질 이미지 주소
	세번째 인수 : 완료 alert 문구

	window.parent.CKEDITOR.tools.callFunction(, '','업로드 완료 ');
	
	
	내용의 값을 가져올경우는 getDate() 로 가져오시면 됩니다. 

	CKEDITOR.instances.board_contents.getData();
	
	*/
	
	config.toolbarGroups = [
        { name: 'mode',    groups: [ 'mode', /*'document', 'doctools'*/ ] },
        { name: 'insert' },
        { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'links' },
        { name: 'others' },
        { name: 'paragraph',   groups: [ 'list', /*'indent', */'blocks', 'align'/*, 'bidi' */] },
        { name: 'styles' },
        { name: 'colors' },
    ];
	config.removeButtons = 'Flash';
	
	
	//config.filebrowserBrowseUrl='/ckfinder/ckfinder.html',
	//config.filebrowserImageBrowseUrl='/ckfinder/ckfinder.html?type=Images',
	config.filebrowserImageBrowseUrl = GLOBAL.ADMIN_PATH + '/fileinfo/imageBrowse.do?fileMediaType=IMAGE',
	//config.filebrowserFlashBrowseUrl='/ckfinder/ckfinder.html?type=Flash',
	//config.filebrowserUploadUrl='/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
	//config.filebrowserImageUploadUrl='/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
	config.filebrowserImageUploadUrl = GLOBAL.ADMIN_PATH + '/file/editor/upload.do',
	//config.filebrowserFlashUploadUrl='/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash',
	config.filebrowserWindowWidth='800',
	config.filebrowserWindowHeight='400';
	config.filebrowserUploadMethod = 'form';
	

	config.height = '400px';		//Editor 높이  
	//config.width = '800px';			//Editor 넓이

	// Remove some buttons, provided by the standard plugins, which we don't
	// need to have in the Standard(s) toolbar.
	//config.removeButtons = 'Underline,Subscript,Superscript';
	
	config.entities = true;
	config.enterMode = CKEDITOR.ENTER_DIV;
	config.allowedContent = true;
	//config.extraAllowedContent = 'a div';
	//fonts
	config.font_names =
	    '굴림;' +
	    '굴림체;' +
	    '궁서;' +
	    '궁서체;' +
	    '돋움;' +
	    '돋움체;' +
	    '바탕;' +
	    '바탕체;' +
	    '나눔고딕;' +
	    '맑은고딕;' +
	    '다음;' +
	    'Arial/Arial, Helvetica, sans-serif;' +
	    'Comic Sans MS/Comic Sans MS, cursive;' +
	    'Courier New/Courier New, Courier, monospace;' +
	    'Georgia/Georgia, serif;' +
	    'Lucida Sans Unicode/Lucida Sans Unicode, Lucida Grande, sans-serif;' +
	    'Tahoma/Tahoma, Geneva, sans-serif;' +
	    'Times New Roman/Times New Roman, Times, serif;' +
	    'Trebuchet MS/Trebuchet MS, Helvetica, sans-serif;' +
	    'Verdana/Verdana, Geneva, sans-serif';
	
	//붙여넣기 자동 링크
	//config.extraPlugins = 'autolink';
	
	//에디터 영역 빨간라인 제거
	config.removePlugins = 'magicline';
	
	contentsCss : [GLOBAL.CONTEXT_PATH + '/assets/css/style.css', GLOBAL.CONTEXT_PATH + '/assets/css/editor.css']
};
