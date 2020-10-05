<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<!-- Magnific popup -->
        <link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
        <script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
        <script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>
        
        <link href="${CONTEXT_PATH }/assets/plugins/Groupable-Searchable-Dual-Listbox-Transfer/icon_font/css/icon_font.css" rel="stylesheet" type="text/css">
        <link href="${CONTEXT_PATH }/assets/plugins/Groupable-Searchable-Dual-Listbox-Transfer/css/jquery.transfer.css" rel="stylesheet" type="text/css">
        <script src="${CONTEXT_PATH }/assets/plugins/Groupable-Searchable-Dual-Listbox-Transfer/js/jquery.transfer.js"></script>
<script>
jQuery(function($){
	//내용 글자 카운트
	/* $('textarea[name=noteContent]').keyup( $.proxy(function(e){
		var content = $('textarea[name=noteContent]').val();
	    $('#counter').html(content.length + " / 1000자");    //글자수 실시간 카운팅

	}, this) ); */
	
	//보내기
	$('#sendBtn').on('click', function(e){
		e.preventDefault();
		$('#noteForm').submit();
	});
	
	
	
	/* var members = [
		<c:forEach items="${adminList }" var="adminMember" varStatus="vs">
			{
				"adminName": "${adminMember.adminName } (${adminMember.organization.orgName }-${adminMember.department.depName })",
				"value": "${adminMember.adminId }"
			}
			<c:if test="${!vs.last }">,</c:if>
		</c:forEach>
     ]; */
	
	var groupDataArray2 = [
	<c:set var="depId" value="" />
		<c:forEach items="${adminList }" var="adminMember" varStatus="vs">
			<c:if test="${vs.first }">
			{
	            "groupName": "${adminMember.department.depName } [${adminMember.organization.orgName }]",
	            "groupData": [
			</c:if>	
			<c:if test="${!vs.first and depId ne adminMember.department.depId}">
 				]
		    },
	        {
		    	"groupName": "${adminMember.department.depName } [${adminMember.organization.orgName }]",
	            "groupData": [					
			</c:if>
				<c:if test="${depId eq adminMember.department.depId }">,</c:if>
				{
					<%--"adminName": '${adminMember.adminName }<c:if test="${not empty adminMember.team }"> (${adminMember.team.teamName })</c:if>',--%>
					"adminName": '${adminMember.adminName }<c:if test="${not empty adminMember.adminTeam }"> (${adminMember.adminTeam })</c:if>',
					"value": "${adminMember.adminId }"
				}
		<c:set var="depId" value="${adminMember.department.depId }" />
		<c:if test="${vs.last }">
			]
		}
		</c:if>
	</c:forEach>
    ];

    var settings = {
    	/* "dataArray": members, */
        "groupDataArray": groupDataArray2,
        "groupItemName": "groupName",
        "groupArrayName": "groupData",
        "itemName": "adminName",
        "valueName": "value",
        "tabNameText": "주소록",
        "rightTabNameText": "받는사람",
        "searchPlaceholderText": "검색",
        "callable": function (items) {
            console.dir(items);
            var tempValue = '';
            $.each(items, function(index, item){
            	tempValue += item.value;
            	if(index < items.length-1){
            		tempValue += ',';
            	}
            });
            $("#noteReceiverId").val(tempValue);
        }
    };

	
	
	var transfer = $("#memberTree").transfer(settings);
	
	 // get selected items
    var items = transfer.getSelectedItems()
    //console.log("Manually get selected items: %o", items);
	
	$('#receiverSearchBtn').on('click', function(e){
		e.preventDefault();
		
	    //var items = transfer.getSelectedItems()
	    //console.log("Manually get selected items: %o", items);
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
					<%-- <c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" /> --%>
					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 입력폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					        
					        	<div class="card-header">
									<button id="sendBtn" class="btn btn-primary waves-effect waves-light"><i class="mdi mdi-send"></i> 보내기</button>
								</div>
								
					            <div class="card-body">
					            	<!-- 탭메뉴 -->
									<ul class="nav nav-pills mb-2" role="tablist" style="display: none">
										<li class="nav-item">
											<a class="nav-link btn-outline-success active show" href="${ADMIN_PATH}/note/insert.do" role="tab" aria-selected="true">쪽지쓰기</a>
										</li>
									</ul>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					            	
						            <form:form modelAttribute="noteForm" cssClass="" action="${ADMIN_PATH}/note/insert.do" method="post">
										<div class="form-group row">
											<form:label path="noteReceiverId" cssClass="col-sm-2 col-form-label">받는사람 </form:label>
											<c:if test="${param.reply }">
												<div class="col-sm form-inline">
													<form:input path="noteReceiverId" cssClass="form-control col-sm-6 mr-sm-2" cssErrorClass="form-control col-sm-6 is-invalid mr-sm-2" readonly="true" placeholder="여러 명은 쉼표(,) 또는 세미콜론(;)구분" />
												</div>
											</c:if>
											<c:if test="${!param.reply }">
												<div class="col-sm form-inline">
													<form:input path="noteReceiverId" cssClass="form-control col-sm-6 mr-sm-2" cssErrorClass="form-control col-sm-6 is-invalid mr-sm-2" placeholder="여러 명은 쉼표(,) 또는 세미콜론(;)구분" />
													<button id="receiverSearchBtn" class="btn btn-secondary waves-effect waves-light" data-toggle="modal" data-target="#myModal">주소록</button>
													<form:errors path="noteReceiverId" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
											</c:if>
										</div>
										
										<div id="myModal${vs.count }" class="modal fade rolechk" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="display: none;" aria-hidden="true">
											<div class="modal-dialog modal-lg">
												<div class="modal-content">
													<div class="modal-header">
														<h5 class="modal-title mt-0" id="myModalLabel">주소록</h5>
														<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
													</div>
													<div class="modal-body">
														<!-- <div><span class="text-danger">*</span><span class="mx-2 bg-light font-weight-bold text-info">는 다중 부여 할 수 없습니다.</span></div> -->
														<!-- 주소록 -->
														<div id="memberTree" ></div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal">닫기</button>
														<!-- <button type="button" class="btn btn-primary waves-effect waves-light">Save changes</button> -->
													</div>
												</div><!-- /.modal-content -->
											</div><!-- /.modal-dialog -->
										</div>
										
										
										
										
										
										<div class="form-group row">
											<form:label path="noteContent" cssClass="col-sm-2 col-form-label">내용 </form:label>
											<div class="col-sm">
												<form:textarea path="noteContent" cssClass="form-control col-sm-5" rows="8" maxlength="1000" id="textarea" placeholder="" />
												<form:errors path="noteContent" element="div" cssClass="text-danger" />
												<!-- <span class="txt_cnt" id="counter">0 / 1000자</span> -->
											</div>
										</div>
										
									</form:form>
					            </div>
					            <div class="card-footer">
									<a href="${ADMIN_PATH}/note/reception/list.do" class="btn btn-outline-info waves-effect waves-light">
										<img src="${CONTEXT_PATH }/assets/images/note_reception.png" alt="받은쪽지함" class="img-fluid m-r-5" style="height: 20px;" /> 받은쪽지함</a>
									<a href="${ADMIN_PATH}/note/transmit/list.do" class="btn btn-outline-info waves-effect waves-light">
										<img src="${CONTEXT_PATH }/assets/images/note_transmit.png" alt="보낸쪽지함" class="img-fluid m-r-5" style="height: 20px;" /> 보낸쪽지함</a>
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



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />