<%@page import="egovframework.com.asapro.member.admin.service.AdminMember"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<!-- Nestable css -->
<link href="${CONTEXT_PATH }/assets/plugins/nestable/jquery.nestable.css" rel="stylesheet" />


<script>
jQuery(function($){
		
	//삭제 Ajax
    $('[id^=deleteBtn_]').on('click', function(e){
    	e.preventDefault();
    	var menuId = this.id.replace('deleteBtn_', ''); 

        swal({
            title: '삭제하시겠습니까?',
            html: '※주의※\n메뉴 삭제시 하위메뉴가 있으면 삭제할 수 없고 만족도평가도 함께 삭제되며 삭제한 메뉴는 복구할 수 없습니다.',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (menuId == null || menuId == '') {
                            reject('삭제할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/menu/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					menuId : menuId
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '삭제가 완료되었습니다.',
		                html: '삭제결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '삭제하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '삭제하지 못하였습니다.[fail]'
	            });
			});
        });
    });
	
	
	//===========================================================

	//순서저장
	$('.saveOrderBtn').on('click', function(e){
		e.preventDefault();

		var dataJson = window.JSON.stringify(  $('#nestable_list_1').nestable('serialize'));

		swal({
            title: '메뉴 구조를 바꾸시겠습니까?',
            html: '메뉴 위치와 순서를 바꿉니다.',
            showCancelButton: true,
            confirmButtonText: '변경',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (dataJson == null || dataJson == '') {
                            reject('변경할 항목이 없습니다.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/menu/reBuild.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					dataJson : dataJson
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '수정 완료',
		                html: '수정이 완료되었습니다.' ,
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '수정하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '수정하지 못하였습니다.[fail]'
	            });
			});
        });
	});
	
	//
	/*
	var updateOutput = function (e) {
        var list = e.length ? e : $(e.target),
            output = list.data('output');
        if (window.JSON) {
            //output.val(window.JSON.stringify(list.nestable('serialize')));
            alert(window.JSON.stringify(list.nestable('serialize')));
        } else {
            output.val('JSON browser support required for this page.');
        }
    };*/
    
    //init
	// activate Nestable for list 1
	$('#nestable_list_1').nestable({
	    group: 1,
	    maxDepth: 6
	});
    $('.dd').nestable('collapseAll');
	
	// output initial serialised data
    $('#nestable_list_1').data('output', $('#nestable_list_1_output'));
	
	//메뉴 접기, 펼치기(expandAll, collapseAll)
	$('.menuAction').on('click', function(e){
		var action = $(this).data('action');
		
		$('.dd').nestable(action);
	});
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
					
					<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
									<div class="form-inline mb-4">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/menu/insert.do" class="btn btn-primary waves-effect waves-light">메뉴추가</a>
											<button class="saveOrderBtn btn btn-info waves-effect waves-light">순서저장</button>
										</div>
										<div class="">
											<button class="menuAction btn btn-outline-primary waves-effect waves-light" data-action="expandAll">메뉴펼침</button>
											<button class="menuAction btn btn-outline-secondary waves-effect" data-action="collapseAll">메뉴접기</button>
										</div>
										<%--
										<div class="">
											<a href="/sitemap/Sitemap_${currentSite.siteId}.xml" class="btn btn-secondary waves-effect waves-light" target="_blank" title="Google, Yahoo, Bing, naver 등 검색엔진 제출용으로 사용되는 Sitemap.xml입니다.">사이트맵(xml)</a>
										</div>
										 --%>
									</div>
									
									<div class="custom-dd-empty dd" id="nestable_list_1">
										<c:if test="${empty menuList}">
											등록된 메뉴가 없습니다.
										</c:if>
										
										<c:forEach items="${menuList}" var="item" varStatus="vs">
											<c:if test="${vs.first }">
												<ol class="dd-list">
											</c:if>
											<li class="dd-item dd3-item" data-id="${item.menuId}" id="${item.menuId}">
											
												<div class="dd-handle dd3-handle">
													<!--Drag-->
												</div>
												<div class="dd3-content">
													<a class="" href="${ADMIN_PATH}/menu/update.do?menuId=${item.menuId}" style="<c:if test="${item.menuStatus == 'hidden'}">color: #bbb; </c:if><c:if test="${item.menuStatus == 'locked'}">color: #ccc; text-decoration: line-through; font-style: italic</c:if>">${item.menuName} (ID : ${item.menuId})</a>
													<div class="ml-4 badge badge-secondary" style="display: inline;">
														<span calss=""><c:if test="${item.menuType == 'content' }">콘텐츠</c:if><c:if test="${item.menuType == 'link' }">링크</c:if><c:if test="${item.menuType == 'program' }">프로그램</c:if> </span>
														/ <span calss=""><c:if test="${item.menuStatus == 'public' }">노출</c:if><c:if test="${item.menuStatus == 'hidden' }">숨김</c:if><c:if test="${item.menuStatus == 'locked' }">폐쇄</c:if> </span>
													</div>
													<c:if test="${!item.home}">
														<a class="btn btn-outline-danger waves-effect waves-light btn-sm pull-right mx-2" href="${ADMIN_PATH}/menu/delete.do?menuId=${item.menuId}" id="deleteBtn_${item.menuId}" style="padding: .10rem .5rem;">삭제<i class="mdi mdi-table-row-remove"></i></a>
														<a class="btn btn-outline-success waves-effect waves-light btn-sm pull-right mx-2" href="${item.menuLink}" target="_blank" style="padding: .10rem .5rem;">바로가기</a>
													</c:if>
													<%--<c:if test="${item.menuType == 'content' && item.menuTemplate == 'content.jsp'}"> --%>
													<c:if test="${item.menuType == 'content'}">
														<a href="${ADMIN_PATH}/content/update.do?menu.menuId=${item.menuId}" class="btn btn-outline-info waves-effect waves-light btn-sm pull-right mx-2" style="padding: .10rem .5rem;">콘텐츠편집</a>
													</c:if>
												</div>
												
												<c:if test="${!vs.last }">
													<c:if test="${not empty menuList[vs.index + 1].menuParent }">
														<c:if test="${menuList[vs.index + 1].menuParent.menuId == item.menuId }">
															<ol class="dd-list">
														</c:if>
														
														<c:if test="${menuList[vs.index + 1].menuDepth <= item.menuDepth }">
															</li>
														</c:if>
														
														<c:if test="${menuList[vs.index + 1].menuDepth < item.menuDepth }">
															<c:forEach begin="${menuList[vs.index + 1].menuDepth}" end="${item.menuDepth - 1}" step="1" >
																	</ol>
																</li>
															</c:forEach>
														</c:if>
													</c:if>
													
													<c:if test="${empty menuList[vs.index + 1].menuParent }">
														<c:forEach begin="1" end="${item.menuDepth - 1 }" step="1" >
																</li>
															</ol>
														</c:forEach>
													</c:if>
												</c:if>	
												
												
												<c:if test="${vs.last }">
													<c:forEach begin="1" end="${item.menuDepth }" step="1" >
															</li>
														</ol>
													</c:forEach>
												</c:if>	
										</c:forEach>
									</div>
									
									<div class="form-inline mt-4">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/menu/insert.do" class="btn btn-primary waves-effect waves-light">메뉴추가</a>
											<button class="saveOrderBtn btn btn-info waves-effect waves-light">순서저장</button>
										</div>
										<div class="">
											<button class="menuAction btn btn-outline-primary waves-effect waves-light" data-action="expandAll">메뉴펼침</button>
											<button class="menuAction btn btn-outline-secondary waves-effect" data-action="collapseAll">메뉴접기</button>
										</div>
										<%--
										<div class="">
											<a href="/sitemap/Sitemap_${currentSite.siteId}.xml" class="btn btn-secondary waves-effect waves-light" target="_blank" title="Google, Yahoo, Bing, naver 등 검색엔진 제출용으로 사용되는 Sitemap.xml입니다.">사이트맵(xml)</a>
										</div>
										 --%>
									</div>
									
								</div>
							</div>
						</div>
					</div><!-- 목록 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->
<script src="${CONTEXT_PATH }/assets/plugins/nestable/jquery.nestable.js"></script>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />