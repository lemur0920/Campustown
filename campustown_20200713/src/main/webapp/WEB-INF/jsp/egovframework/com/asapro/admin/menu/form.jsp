<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ page import="egovframework.com.asapro.menu.service.Menu"%>
<%@ page import="egovframework.com.asapro.support.Constant"%>
<%@ page import="egovframework.com.asapro.site.service.Site"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<% 
Menu menuForm = (Menu)request.getAttribute("menuForm"); 
Site currentSite = (Site)request.getAttribute("currentSite");
%>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<!-- Magnific popup -->
        <link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
        <script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
        <script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>

<script>
jQuery(function($){
	
	
});
</script>

	<!-- Left Sidebar -->	
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />
	
	<!-- Start right Content here -->
	<div class="content-page">
	    <!-- Start content -->
	    <div class="content">
			
			<!-- Top Bar -->
			<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />
			
	        <div class="page-content-wrapper ">

				<div class="container-fluid">
			
					<!-- location -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />

					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 입력폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
									
									
									
									
									
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/menu/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/menu/update.do" /></c:if>
									<form:form modelAttribute="menuForm" action="${actionUrl}" method="post" cssClass="form-horizontal" enctype="multipart/form-data">
										<form:hidden path="menuOrder" />
										<form:hidden path="menuDepth" />
										<form:hidden path="menuGnbExtOn" />
										<form:hidden path="menuGnbExtOff" />
										<form:hidden path="menuSnbExtOn" />
										<form:hidden path="menuSnbExtOff" />
										<form:hidden path="menuTitleExt" />
										
										
										
										
										<div class="form-group row">
											<form:label path="menuType" cssClass="col-sm-2 col-form-label">메뉴 유형 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuType" class="custom-control-input" value="content"/>
													<label for="menuType1" class="custom-control-label"> 콘텐츠</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuType" class="custom-control-input" value="program"/>
													<label for="menuType2" class="custom-control-label"> 프로그램</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuType" class="custom-control-input" value="link"/>
													<label for="menuType3" class="custom-control-label"> 링크</label>
												</div>
												<form:errors path="menuType" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuId" cssClass="col-sm-2 col-form-label">메뉴아이디  <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<c:if test="${formMode == 'INSERT'}">
													<form:input path="menuId" maxlength="50" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" />
												</c:if>
												<c:if test="${formMode == 'UPDATE'}">
													<form:hidden path="menuId"/>
													<span class="bg-light p-1">${menuForm.menuId}</span>
												</c:if>
												<form:errors path="menuId" element="div" cssClass="text-danger col-form-label ml-2" />
												<span class="mx-2 bg-light font-weight-bold text-info"> - 영문, 숫자, _, -만 사용할 수 있습니다. 4자이상 50자 이하여야 합니다.</span>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuName" cssClass="col-sm-2 col-form-label">메뉴이름  <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="menuName" maxlength="50" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid" />
												<form:errors path="menuName" element="div" cssClass="text-danger col-form-label ml-2" />
												<span class="mx-2 bg-light font-weight-bold text-info"> - 50자까지 입력가능합니다.</span>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuTitleSubText" cssClass="col-sm-2 col-form-label">메뉴 추가텍스트  </form:label>
											<div class="col-sm form-inline">
												<form:input path="menuTitleSubText" maxlength="50" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid" />
												<form:errors path="menuTitleSubText" element="div" cssClass="text-danger col-form-label ml-2" />
												<span class="mx-2 bg-light font-weight-bold text-info"> - 메뉴 옆에 추가적으로 출력되는 텍스트입니다.</span>
											</div>
										</div>
										
										
										<div class="form-group row">
											<label class="col-sm-2 col-form-label"> </label>
											<div class="col-sm">
												<button type="button" class="btn btn-sm btn-primary waves-effect waves-light contentSearchBtn">콘텐츠 검색 </button>
												<span class="bg-light font-weight-bold text-info ml-3">- 콘텐츠 메뉴의 경우 등록된 콘텐츠를 선택하세요.</span>
											</div>
										</div>
	
										<div id="content" class="mfp-hide">
											<h5 class="modal-title mt-0">콘텐츠 목록</h5>
											<hr>
											<div id="contentList">
											
											</div>
										</div>
										<script>
											jQuery(function($){
												//콘텐츠 검색 폼 및 목록을 가져온다.
												//$('.searchBtn').on('click',function(){
													$('#contentList').load('${ADMIN_PATH}/content/searchLayer.do');									
												//});
												
												
												// From an element with ID #popup
												$('.contentSearchBtn').magnificPopup({
												  items: {
												      src: '#content',
												      type: 'inline',
												     ///mainClass: 'mfp-with-zoom', // class to remove default margin from left and right side
												     // zoom: {
													//		enabled: true,
													//		duration: 300 // don't foget to change the duration also in CSS
													//	}
												  }
												, closeOnBgClick:false
												//, showCloseBtn:true 
												});
												
												
											});
										
										</script>
										<form:hidden path="content.contentRoot" id="contentRoot"/>
										<div class="form-group row">
											<label  class="col-sm-2 col-form-label">콘텐츠 명  </label>
											<div class="col-sm">
												<form:input id="contentTitle" path="content.contentTitle" cssClass="form-control col-sm-8" readonly="true" />
											</div>
										</div>
										
										
										
										
										<div class="form-group row">
											<form:label path="menuParent.menuId" cssClass="col-sm-2 col-form-label">상위메뉴  </form:label>
											<div class="col-sm">
												<c:set var="minDepthStart" value="false"/>
												<select name="menuParent.menuId" class="form-control col-sm-3">
													<option value="">최상위</option>
													<%
														//하위 뎁스로는 이동할 수 없도록 하기 위해 로직 처리 - jstl로 처리하기엔 복잡해서 스크립트릿으로 함
														List<Menu> List = (List<Menu>)request.getAttribute("menuList");
														Menu Form = (Menu) request.getAttribute("menuForm");
														boolean minDepthStarted = false;
														int minDepth = 0;
														String disabled = null; 
														String cross = null; 
														String selected = null; 
														String nbsp = "";
														for(Menu item : List){
															String pMenuId = StringUtils.defaultString(Form.getMenuParent().getMenuId());
															if(item.getMenuId().equals(pMenuId)){
																selected = " selected=\"selected\" ";
															} else {
																selected = " ";
															}
															if( item.getMenuId().equals(Form.getMenuId()) ){
																minDepthStarted = true;
																minDepth = item.getMenuDepth();
															}
															if( !item.getMenuId().equals(Form.getMenuId()) && item.getMenuDepth() <= minDepth){
																minDepthStarted = false;
															}
															if(minDepthStarted){
																disabled = " disabled=\"disabled\" ";
																cross = "[X]";
															} else {
																disabled = " ";
																cross = "";
															}
															if(item.getMenuDepth() > 1){
																for(int i = 1; i< item.getMenuDepth(); i++){
																	nbsp += "&nbsp;&nbsp;&nbsp;&nbsp;";
																}
															}
															%>
															<option<%= disabled %>value="<%= item.getMenuId() %>"<%= selected %>><%= nbsp %><%= cross %><%= item.getMenuName() %> (ID:<%= item.getMenuId() %>)</option>
															<%
															nbsp = "";
														}
													%>
												</select>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuLink" cssClass="col-sm-2 col-form-label">메뉴링크 Url <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="menuLink" maxlength="100" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid" />
												<form:errors path="menuLink" element="div" cssClass="text-danger col-form-label ml-2" />
												<div class="mx-2 bg-light font-weight-bold text-info"> - 키보드 아래쪽 방향키를 입력하면 사용가능한 url목록이 나타납니다.</div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuNewWin" cssClass="col-sm-2 col-form-label">메뉴링크 대상 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuNewWin" class="custom-control-input" value="false"/>
													<label for="menuNewWin1" class="custom-control-label"> 현재창</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuNewWin" class="custom-control-input" value="true"/>
													<label for="menuNewWin2" class="custom-control-label"> 새창</label>
												</div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuHeader" cssClass="col-sm-2 col-form-label">메뉴 헤더파일 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="menuHeader" items="${layoutFileList}" class="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"/>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuTemplate" cssClass="col-sm-2 col-form-label">콘텐츠메뉴 템플릿파일  </form:label>
											<div class="col-sm">
												<form:select path="menuTemplate" items="${contentTemplateFileList}" class="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"/>
												<div class="form-text bg-light font-weight-bold text-info">
													<div> - 콘텐츠 유형의 메뉴에만 적용됩니다.</div>
													<div> - content.jsp 기본 뷰파일 대신 사용할 필요가 있을 경우 변경 선택하세요.</div>
													<div> - <%= Constant.THEME_ROOT %>${currentSite.siteTheme}/content 폴더 하위의 파일만 사용가능 하므로 해당 폴더에 템플릿을 추가하세요.</div>
												</div>
											</div>
										</div>

										<div class="form-group row">
											<form:label path="menuFooter" cssClass="col-sm-2 col-form-label">메뉴 풋터파일 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="menuFooter" items="${layoutFileList}" class="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid"/>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuStatus" cssClass="col-sm-2 col-form-label">메뉴 상태 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuStatus" class="custom-control-input" value="public"/>
													<label for="menuStatus1" class="custom-control-label"> 표시</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuStatus" class="custom-control-input" value="hidden"/>
													<label for="menuStatus2" class="custom-control-label"> 숨김(접근가능)</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuStatus" class="custom-control-input" value="locked"/>
													<label for="menuStatus3" class="custom-control-label"> 폐쇄(접근불가)</label>
												</div>
											</div>
										</div>
										
										<c:if test="${not empty menuHashTagCodeList }">
											<div class="form-group row">
												<form:label path="tagCode" cssClass="col-sm-2 col-form-label">해시 태그 </form:label>
												<div class="col-sm col-form-label">
													<c:forEach items="${menuHashTagCodeList }" var="menuHashTagCode" varStatus="vs"> 
														<c:set var="chkday" value="" />
														<c:forEach items="${hashTagArray }" var="hashTag" >
															<c:if test="${hashTag eq menuHashTagCode.codeId }">
																<c:set var="chkday" value="checked" />
															</c:if>
														</c:forEach>
														<div class="custom-control custom-checkbox custom-control-inline">
															<input type="checkbox" name="tagCode" data-dayidx="${vs.index }" id="tagCode${vs.count }" class="custom-control-input" value="${menuHashTagCode.codeId }" ${chkday } />
															<label for="tagCode${vs.count }" class="custom-control-label"> ${menuHashTagCode.codeName }</label>
														</div>
													</c:forEach>
													<div class="form-text text-muted text-info">- 맞춤서비스를 위한 태그별 메뉴를 검색하기 위한 해시태그</div>
													<div class="form-text text-muted text-info">- 메뉴설정에 등록된 코드를 코드관리 에서 수정, 추가</div>
													<form:errors path="tagCode" element="div" cssClass="text-danger" />
												</div>
											</div>
										</c:if>
										
										<hr>
										<div class="form-group row">
											<form:label path="menuUseManagerInfo" cssClass="col-sm-2 col-form-label">메뉴관리자 정보 노출여부  <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label form-inline">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuUseManagerInfo" class="custom-control-input" value="true"/>
													<label for="menuUseManagerInfo1" class="custom-control-label"> 노출</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuUseManagerInfo" class="custom-control-input" value="false"/>
													<label for="menuUseManagerInfo2" class="custom-control-label"> 노출안함</label>
												</div>
												<div class="mx-2 bg-light font-weight-bold text-info"> - 해당메뉴의 관리자(부서) 정보를 표시할지 여부입니다.</div>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-sm-2 col-form-label"> </label>
											<div class="col-sm">
												<button type="button" class="btn btn-sm btn-primary waves-effect waves-light searchBtn">담당자 검색 </button>
												<span class="bg-light font-weight-bold text-info ml-3">- 메뉴 관리 담당자를 선택하세요.</span>
											</div>
										</div>
	
										<div id="adminMember" class="mfp-hide">
											<h5 class="modal-title mt-0">관리자목록</h5>
											<hr>
											<div id="memberList">
											
											</div>
										</div>
										<script>
											jQuery(function($){
												//관리자 회원 검색 폼 및 목록을 가져온다.
												//$('.searchBtn').on('click',function(){
													$('#memberList').load('${ADMIN_PATH}/member/admin/searchLayer.do');									
												//});
												
												
												// From an element with ID #popup
												$('.searchBtn').magnificPopup({
												  items: {
												      src: '#adminMember',
												      type: 'inline',
												     ///mainClass: 'mfp-with-zoom', // class to remove default margin from left and right side
												     // zoom: {
													//		enabled: true,
													//		duration: 300 // don't foget to change the duration also in CSS
													//	}
												  }
												, closeOnBgClick:false
												//, showCloseBtn:true 
												});
												
												
											});
										
										</script>
										
										<div class="form-group row">
											<form:label path="menuManager" cssClass="col-sm-2 col-form-label">메뉴관리자  </form:label>
											<div class="col-sm">
												<form:input path="menuManager" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" readonly="true" />
												<form:errors path="menuManager" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuDepartment" cssClass="col-sm-2 col-form-label">메뉴관리자 부서 </form:label>
											<div class="col-sm">
												<form:input path="menuDepartment" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" readonly="true" />
												<form:errors path="menuDepartment" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuPhone" cssClass="col-sm-2 col-form-label">메뉴관리자 전화번호 </form:label>
											<div class="col-sm">
												<form:input path="menuPhone" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" readonly="true" />
												<form:errors path="menuPhone" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuEmail" cssClass="col-sm-2 col-form-label">메뉴관리자 이메일 </form:label>
											<div class="col-sm">
												<form:input path="menuEmail" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" readonly="true" />
												<form:errors path="menuEmail" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuEtc" cssClass="col-sm-2 col-form-label">메뉴관리자 기타정보 </form:label>
											<div class="col-sm">
												<form:input path="menuEtc" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" />
												<form:errors path="menuEtc" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<hr>
										<div class="form-group row">
											<form:label path="menuUseSatisfaction" cssClass="col-sm-2 col-form-label">만족도 조사 사용여부  <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label form-inline">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuUseSatisfaction" class="custom-control-input" value="true"/>
													<label for="menuUseSatisfaction1" class="custom-control-label"> 사용함</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuUseSatisfaction" class="custom-control-input" value="false"/>
													<label for="menuUseSatisfaction2" class="custom-control-label"> 사용안함</label>
												</div>
												<div class="mx-2 bg-light font-weight-bold text-info"> - 페이지 하단에 만족도 조사양식을 표시할지 여부입니다.</div>
											</div>
										</div>
										
										
										
										
									<div style="display : none">	
										<hr />
										
										<div class="form-group row">
											<label class="col-sm-2 col-form-label"></label>
											<div class="p-3 bg-light font-weight-bold text-info">
												<div><i class="mdi mdi-information"></i> 이미지를 사용한 메뉴는 테마 변경시 해당하는 이미지가 없을 경우 엑박으로 표시될 수 있습니다.</div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuGnbType" cssClass="col-sm-2 col-form-label">GNB메뉴 유형 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label form-inline">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuGnbType" class="custom-control-input" value="text"/>
													<label for="menuGnbType1" class="custom-control-label"> 텍스트</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuGnbType" class="custom-control-input" value="image"/>
													<label for="menuGnbType2" class="custom-control-label"> 이미지</label>
												</div>
												<div class="mx-2 bg-light font-weight-bold text-info"> - 메뉴이미지는 메뉴유형에서 이미지를 선택했을때만 적용됩니다.</div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuGnbImageOnFile" cssClass="col-sm-2 col-form-label">GNB메뉴 활성 이미지 </form:label>
											<div class="col-sm">
												<c:if test="${not empty menuForm.menuGnbExtOn}">
													<img src="<%= menuForm.getMenuImageUrl(currentSite.getSiteId(), currentSite.getSiteTheme(), menuForm.getMenuGnbExtOn()) %>" alt="${menuForm.menuName}" class="img-thumbnail"/>
													<label class="checkbox inline"><form:checkbox path="menuGnbImageOnDelete" value="true"/> 삭제</label>
													<br />
												</c:if>
												<input type="file" name="menuGnbImageOnFile" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuGnbImageOffFile" cssClass="col-sm-2 col-form-label">GNB메뉴 비활성 이미지 </form:label>
											<div class="col-sm">
												<c:if test="${not empty menuForm.menuGnbExtOff}">
													<img src="<%= menuForm.getMenuImageUrl(currentSite.getSiteId(), currentSite.getSiteTheme(), menuForm.getMenuGnbExtOff()) %>" alt="${menuForm.menuName}" class="img-thumbnail"/>
													<label class="checkbox inline"><form:checkbox path="menuGnbImageOffDelete" value="true"/> 삭제</label>
													<br />
												</c:if>
												<input type="file" name="menuGnbImageOffFile" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuSnbType" cssClass="col-sm-2 col-form-label">SNB메뉴 유형 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label form-inline">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuSnbType" class="custom-control-input" value="text"/>
													<label for="menuSnbType1" class="custom-control-label"> 텍스트</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuSnbType" class="custom-control-input" value="image"/>
													<label for="menuSnbType2" class="custom-control-label"> 이미지</label>
												</div>
												<div class="mx-2 bg-light font-weight-bold text-info"> - 메뉴이미지는 메뉴유형에서 이미지를 선택했을때만 적용됩니다.</div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuSnbImageOnFile" cssClass="col-sm-2 col-form-label">SNB메뉴 활성 이미지 </form:label>
											<div class="col-sm">
												<c:if test="${not empty menuForm.menuSnbExtOn}">
													<img src="<%= menuForm.getMenuImageUrl(currentSite.getSiteId(), currentSite.getSiteTheme(), menuForm.getMenuSnbExtOn()) %>" alt="${menuForm.menuName}" class="img-thumbnail"/>
													<label class="checkbox inline"><form:checkbox path="menuSnbImageOnDelete" value="true"/> 삭제</label>
													<br />
												</c:if>
												<input type="file" name="menuSnbImageOnFile" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuSnbImageOffFile" cssClass="col-sm-2 col-form-label">SNB메뉴 비활성 이미지 </form:label>
											<div class="col-sm">
												<c:if test="${not empty menuForm.menuSnbExtOff}">
													<img src="<%= menuForm.getMenuImageUrl(currentSite.getSiteId(), currentSite.getSiteTheme(), menuForm.getMenuSnbExtOff()) %>" alt="${menuForm.menuName}" class="img-thumbnail"/>
													<label class="checkbox inline"><form:checkbox path="menuSnbImageOffDelete" value="true"/> 삭제</label>
													<br />
												</c:if>
												<input type="file" name="menuSnbImageOffFile" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuTitleType" cssClass="col-sm-2 col-form-label">본문타이틀 유형  <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label form-inline">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuTitleType" class="custom-control-input" value="text"/>
													<label for="menuTitleType1" class="custom-control-label"> 텍스트</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="menuTitleType" class="custom-control-input" value="image"/>
													<label for="menuTitleType2" class="custom-control-label"> 이미지</label>
												</div>
												<div class="mx-2 bg-light font-weight-bold text-info"> - 메뉴이미지는 메뉴유형에서 이미지를 선택했을때만 적용됩니다.</div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="menuTitleImageFile" cssClass="col-sm-2 col-form-label">본문타이틀 이미지 </form:label>
											<div class="col-sm">
												<c:if test="${not empty menuForm.menuTitleExt}">
													<img src="<%= menuForm.getMenuImageUrl(currentSite.getSiteId(), currentSite.getSiteTheme(), menuForm.getMenuTitleExt()) %>" alt="${menuForm.menuName}" class="img-thumbnail"/>
													<label class="checkbox inline"><form:checkbox path="menuTitleImageDelete" value="true"/> 삭제</label>
													<br />
												</c:if>
												<input type="file" name="menuTitleImageFile" />
											</div>
										</div>
										
										<hr />
										
										
										
										
										
										
								</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/menu/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
										</div>

									</form:form>
	
					            </div>
					        </div>
					    </div>
					</div>
					<!-- //입력폼 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
		
		
		    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->

<script>
jQuery(function($){
	var APP_PATH = '${APP_PATH}';
	
	/*----------------------------
		페이지 열릴때 기본 세팅
	----------------------------*/
	//메뉴링크
	if( $('[name=menuType]:checked').val() != 'content' ){
		$('#menuTemplate').attr('disabled', 'disabled');
	}
	
	//메뉴 만족도 평가 사용여부
	if( $('[name=menuType]:checked').val() == 'link' ){
		//링크메뉴는 만족도 off
		$('[name=menuUseSatisfaction]').val(['false']);
		$('.menu-use-satisfaction').hide();
	}
	
	/*----------------------------
		콘텐츠메뉴 url세팅
	----------------------------*/
	$('#menuId').bind('keyup change', function(e){
		if( $('[name=menuType]:checked').val() == 'content' ){
			e.preventDefault();
			var link = '${APP_PATH}/content/' + $('#menuId').val();
			$('#menuLink').val(link);
		} else {
			$('#menuLink').val('');
		}
	});
	
	/*----------------------------
		이벤트 처리
	----------------------------*/
	//템플릿 파일은 콘텐츠 유형의 메뉴에만 허용하도록
	$('[name=menuType]').click(function(e){
		var menuId = $('#menuId').val();
		switch( e.target.value ){
			case 'content' :
				$('#menuLink').autocomplete('enable');
				$('#menuLink').removeAttr('placeholder');
				$('#menuTemplate').removeAttr('disabled');
				$('.menu-use-satisfaction').show();
				break;
			case 'program' :
				$('#menuLink').autocomplete('enable');
				$('#menuLink').removeAttr('placeholder');
				$('#menuTemplate').attr('disabled', 'disabled');
				$('.menu-use-satisfaction').show();
				break;
			case 'link' :
				$('#menuLink').autocomplete('disable');
				$('#menuTemplate').attr('disabled', 'disabled');
				//$('#menuLink').val('내부url일 경우 상대주소를, 외부url일 경우 http(s)://를 포함한 전체 링크주소를 입력해 주세요.');
				$('#menuLink').attr('placeholder', '내부url일 경우 상대주소를, 외부url일 경우 http(s)://를 포함한 전체 링크주소를 입력해 주세요.');
				//링크메뉴는 만족도 off
				$('[name=menuUseSatisfaction]').val(['false']);
				$('.menu-use-satisfaction').hide();
				break;
			default :
				break;
		}
	});
	
	//메뉴 링크 자동완성
	$('#menuLink').autocomplete({
		source : "requestMappingList.do"
		, minLength : 0
		, select : function(e, ui){
			$('#menuLink').val(ui.item);
		}
		, focus: function(e, ui){
			//$('#menuLink').val(ui.item);
			//$(this).trigger('keydown.autocomplete');
		} 
	});
});
</script>


<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />