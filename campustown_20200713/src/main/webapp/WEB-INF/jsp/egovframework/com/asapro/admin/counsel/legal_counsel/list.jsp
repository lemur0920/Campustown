<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<!-- Magnific popup -->
<link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
<script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
<script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.css">
<script src="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.js"></script>

<script>
    $(document).ready(function(){ //DOM이 준비되고
        $(legalListForm).submit(function(){ //문서의 모든 form이 submit될때
            // alert("검색시작");
            // alert($('#startDate').val())
            <%--if(${lawyerAssingId.lawyerId ne sessionScope.currentAdmin.adminId}){--%>
            <%--    alert("해당 자문에 배정 되지 않았습니다.")--%>
            <%--    return false;--%>
            <%--}--%>
            // alert("등록되었습니다.")
        });

        $('.datepicker-ui').datepicker();
    });

    function findParam(strParamName){
        var strReturn = "";
        var strHref = window.location.href;
        var bFound=false;

        var cmpstring = strParamName + "=";
        var cmplen = cmpstring.length;

        if ( strHref.indexOf("?") > -1 ){
            var strQueryString = strHref.substr(strHref.indexOf("?")+1);
            var aQueryString = strQueryString.split("&");
            for ( var iParam = 0; iParam < aQueryString.length; iParam++ ){
                if (aQueryString[iParam].substr(0,cmplen)==cmpstring){
                    var aParam = aQueryString[iParam].split("=");
                    strReturn = aParam[1];
                    bFound=true;
                    break;
                }

            }
        }
        if (bFound==false) return null;
        return strReturn;
    }




    function fn_statusUpdate(id, statusCode, msg) {
        // alert($(location).attr('host'))
        // alert($(location).attr('hostname'))

        // alert($(location).attr('port'))
        // alert($(location).attr('protocol'))

        //alert($(location).attr('href'))

//        alert(msg);

        var url = "${ADMIN_PATH}/counsel/legal_counsel/status/update.do";
        var cp = findParam("cp");

        if(cp == null){
            cp = 1;
        }
        $.ajax({
            url : url,
            data :
                {   statusCode : statusCode, id : id},
        }).done(function(result){
            //alert(result)
            location.href = "${ADMIN_PATH}/counsel/legal_counsel/list.do?cp="+cp;
        }).fail(function(result){
            alert('통신실패 [fail]');
        });
    }
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

                <!-- 목록 -->
                <form name="legalListForm" id="legalListForm" action="${ADMIN_PATH}/counsel/legal_counsel/list.do" method="post">
                    <div class="col-lg-12">
                        <div class="card m-b-30 search-area">
                            <div class="card-body">
                                <div class="form-group row">
                                    <label for="example-text-input" class="col-lg-2 col-sm-3 col-form-label">정렬조건${legalSearch.pageSize}</label>
                                    <div class="col-lg-10 row">
                                        <div class="col-lg-4 col-sm-4">
                                            <select id="pageSize" name="pageSize" path="pageSize" class="form-control" title="페이지크기" >
                                                <option value="10"<c:if test="${legalSearch.pageSize eq 10}">selected</c:if>>10개씩</option>
                                                <option value="20"<c:if test="${legalSearch.pageSize eq 20}">selected</c:if>>20개씩</option>
                                                <option value="40"<c:if test="${legalSearch.pageSize eq 40}">selected</c:if>>40개씩</option>
                                            </select>
                                        </div>
                                        <div class="col-lg-4 col-sm-4">
                                            <select id="sortOrder" name="sortOrder" path="sortOrder" class="form-control" title="정렬기준">
                                                <option value="LEGAL_ADVICE_REG_DATE"<c:if test="${legalSearch.sortOrder eq 'LEGAL_ADVICE_REG_DATE'}">selected</c:if>>등록일시</option>
                                                <option value="LEGAL_ADVICE_TITLE" <c:if test="${legalSearch.sortOrder eq 'LEGAL_ADVICE_TITLE'}">selected</c:if>>제목</option>
                                            </select>
                                        </div>
                                        <div class="col-lg-4 col-sm-4">
                                            <select id="sortDirection" name="sortDirection" path="sortDirection" class="form-control" title="정렬방향">
                                                <option value="DESC"<c:if test="${legalSearch.sortDirection eq 'DESC'}">selected</c:if>>내림차순</option>
                                                <option value="ASC"<c:if test="${legalSearch.sortDirection eq 'ASC'}">selected</c:if> >오름차순</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="example-text-input" class="col-lg-2 col-sm-3 col-form-label">작성일</label>
                                    <div class="col-lg-10 col-md-12 row">
                                        <div class="col-lg-3 col-md-4 col-sm-5">
                                            <div class="input-group">
                                                <input type="text" class="form-control datepicker-ui" placeholder="mm/dd/yyyy" id="startDate" name="startDate" value="${legalSearch.startDate}" autocomplete="off">
<%--                                                <div class="input-group-append bg-custom b-0"><span class="input-group-text"><i class="mdi mdi-calendar"></i></span></div>--%>
                                            </div>
                                        </div>
                                        <div class="col-lg-1 col-sm-2">
                                            ~
                                        </div>
                                        <div class="col-lg-3 col-md-4 col-sm-5">
                                            <div class="input-group">
                                                <input type="text" class="form-control datepicker-ui" placeholder="mm/dd/yyyy" id="endDate" name="endDate" value="${legalSearch.startDate}" autocomplete="off">
<%--                                                <div class="input-group-append bg-custom b-0"><span class="input-group-text"><i class="mdi mdi-calendar"></i></span></div>--%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
<%--                                    <label for="example-text-input" class="col-lg-2 col-sm-3 col-form-label">검색조건</label>--%>
                                    <div class="col-lg-10 row">
<%--                                        <div class="col-lg-3 col-md-4 col-sm-4">--%>
<%--                                            <select class="form-control">--%>
<%--                                                <option>테스트</option>--%>
<%--                                            </select>--%>
<%--                                        </div>--%>
<%--&lt;%&ndash;                                        <div class="col-lg-3 col-md-4 col-sm-4">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                            <select class="form-control">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                                <option>구분</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                            </select>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                        </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                        <div class="col-lg-3 col-md-4 col-sm-4">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                            <select class="form-control">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                                <option>게시여부</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                            </select>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                        </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                        <div class="col-lg-3 col-md-4 col-sm-4">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                            <select class="form-control">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                                <option>전체</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                            </select>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                        </div>&ndash;%&gt;--%>
<%--                                        <div class="col-lg-3 col-md-4 col-sm-4">--%>
<%--                                            <input class="form-control" type="text" value="Search">--%>
<%--                                        </div>--%>
                                        <div class="col-lg-3 col-md-4 col-sm-4">
                                            <button type="submit" class="btn btn-success waves-effect waves-light">검색</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> <!-- end col -->
                </form>

                    <div class="col-lg-12">
                        <div class="card m-b-30 search-area">
                            <div class="card-body">
                                <div class="table-rep-plugin">
                                    <div class="table-responsive b-0" data-pattern="priority-columns">
                                        <table id="tech-companies-1" class="table  table-striped">
                                            <thead>
                                            <tr>
                                                <th scope="col">번호</th>
                                                <th scope="col">분야</th>
                                                <th scope="col">제목</th>
                                                <th scope="col">창업팀</th>
                                                <th scope="col">작성자</th>
                                                <th scope="col">담당 변호사</th>
                                                <%-- <c:if test = "${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_LAWYER')}"> --%>
                                                <th scope="col">관리</th>
                                                <%-- </c:if> --%>
                                                <th scope="col">상태</th>
                                                <th scope="col">등록일</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                    <c:if test="${empty paging.result}">
                                                        <tr>
                                                            <td colspan="8" class="text-center">등록된 게시글이 없습니다.</td>
                                                        </tr>
                                                    </c:if>
                                                    <c:forEach items="${paging.result}" var="item" varStatus="vs">
                                                        <tr>
                                                            <td>${paging.rowTop - vs.index}</td>
                                                            <td>${item.realm}</td>
                                                            <td class="tb-txl"><a href="${ADMIN_PATH}/counsel/legal_counsel/view/${item.id}.do" >${item.title}</a></td>
                                                            <td>${item.teamName}</td>
                                                            <td>${item.regName}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${!empty item.lawyerId}">${item.lawyerId}</c:when>
                                                                    <c:otherwise>-</c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <%-- <c:if test="${sessionScope.currentAdmin.adminRole ne 'ROLE_LAWYER'}"> --%>
                                                            <%-- <c:if test = "${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_LAWYER')}"> --%>
                                                            <td class="table-btn">
                                                                <c:import url="statusManagement.jsp" charEncoding="UTF-8">
                                                                    <c:param name="id" value="${item.id}"></c:param>
                                                                    <c:param name="status" value="${item.status}"></c:param>
                                                                    <c:param name="userRole" value="${sessionScope.currentAdmin.adminRole}"></c:param>
                                                                </c:import>
                                                            </td>
                                                            <%-- </c:if> --%>
                                                            <td>
                                                                <c:import url="statusName.jsp" charEncoding="UTF-8">
                                                                    <c:param name="status" value="${item.status}"></c:param>
                                                                </c:import>
                                                            </td>
                                                            <td>
                                                            	<fmt:formatDate value="${item.regDate}" pattern="yyyy.MM.dd"/>
                                                            	<%-- ** ${item.status} --%>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                            </tbody>
                                        </table>
                                        <div>
<%--                                            <input type="button" onclick="fn_write()" value="등록"/>--%>
                                            <%-- <c:if test="${sessionScope.currentAdmin.adminRole eq 'ROLE_STARTUP_RPRSNTV_MNGR'}"> --%>
                                            <c:if test = "${fn:contains(sessionScope.currentAdmin.adminRole, 'ROLE_STARTUP_RPRSNTV_MNGR')}">
                                                <a href="${ADMIN_PATH}/counsel/legal_counsel/write.do" class="btn btn-primary waves-effect waves-light">등록</a>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <c:out value="${paging.adminTextTag}" escapeXml="false"/>
                                </div>

                                <c:import url="lawyerList.jsp" charEncoding="UTF-8"></c:import>

                            </div>
                        </div>
                    </div> <!-- end col -->

                </div>

            </div><!-- container -->


        </div> <!-- Page content Wrapper -->

    </div> <!-- content -->

</div>
<!-- End Right content here -->



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />
