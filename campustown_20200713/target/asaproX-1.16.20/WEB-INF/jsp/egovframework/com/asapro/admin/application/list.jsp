<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<!-- Left Sidebar -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

<script>
    $(function() {
        //삭제 Ajax
        $('#deleteBtn').on('click', function () {
            var applySeqs = [];
            $('[name=applySeq]:checked').each(function(idx, el){
                applySeqs.push(el.value);
            });

            swal({
                title: '삭제하시겠습니까?',
                html: '삭제한 항목은 복구할 수 없습니다.',
                showCancelButton: true,
                confirmButtonText: '삭제',
                showLoaderOnConfirm: true,
                cancelButtonText: '취소',
                confirmButtonClass: 'btn btn-success',
                cancelButtonClass: 'btn btn-danger m-l-10',
                preConfirm: function () {
                    return new Promise(function (resolve, reject) {
                        // setTimeout(function () {
                        if (applySeqs.length === 0) {
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
                    url : '${ADMIN_PATH}/application/form/delete.do'
                    , type : 'post'
                    , dataType: 'json'
                    , data: {
                        applySeqs : applySeqs
                    }
                }).done(function(result){
                    if(result.success){
                        swal({
                            type: 'success',
                            title: '삭제가 완료되었습니다.',
                            html: '삭제결과 : ' + result.successCnt + '건',
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
    });
</script>
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
                <!-- 검색폼 -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card m-b-15">
                            <div class="card-body">
<%--                                <form:form modelAttribute="XTechApply" cssClass="" action="${ADMIN_PATH}/archive/list.do" method="get">--%>
<%--                                    <form:hidden path="arcCategory"/>--%>

<%--                                    <div class="form-row mr-auto mt-2 form-inline">--%>
<%--                                        <label class="mr-sm-2 mb-2">정렬조건</label>--%>
<%--                                        <form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >--%>
<%--                                            &lt;%&ndash;--%>
<%--                                            <form:option value="${archiveCategory.catPageSize}">${archiveCategory.catPageSize}개씩</form:option>--%>
<%--                                            <form:option value="${archiveCategory.catPageSize * 2}">${archiveCategory.catPageSize * 2}개씩</form:option>--%>
<%--                                            <form:option value="${archiveCategory.catPageSize * 4}">${archiveCategory.catPageSize * 4}개씩</form:option>--%>
<%--                                             &ndash;%&gt;--%>
<%--                                            <form:option value="10">10개씩</form:option>--%>
<%--                                            <form:option value="20">20개씩</form:option>--%>
<%--                                            <form:option value="40">40개씩</form:option>--%>
<%--                                        </form:select>--%>
<%--                                        <form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">--%>
<%--                                            <form:option value="ARC_REGDATE" label="등록일" />--%>
<%--                                            <form:option value="ARC_TITLE" label="제목" />--%>
<%--                                            <form:option value="ARC_HIT" label="조회순" />--%>
<%--                                        </form:select>--%>
<%--                                        <form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">--%>
<%--                                            <form:option value="DESC" label="내림차순" />--%>
<%--                                            <form:option value="ASC" label="오름차순" />--%>
<%--                                        </form:select>--%>
<%--                                    </div>--%>
<%--                                    &lt;%&ndash;--%>
<%--                                    <div class="form-row mt-2 form-inline" >--%>
<%--                                        <label for="sc" class="mr-sm-3 mb-2">등록일</label>--%>
<%--                                        <form:input path="startDate" cssClass="form-control col-sm-1 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off"/>--%>
<%--                                        <span class="mx-2"> ~ </span>--%>
<%--                                        <form:input path="endDate" cssClass="form-control col-sm-1 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off"/>--%>
<%--                                    </div>--%>
<%--                                     &ndash;%&gt;--%>
<%--                                    <div class="form-row mt-2 form-inline" >--%>
<%--                                        <label for="sc" class="mr-sm-2 mb-2">검색조건</label>--%>

<%--                                        <c:if test="${not empty arcMetaCode1List || not empty arcMetaCode2List || not empty arcMetaCode3List}">--%>
<%--                                            <c:if test="${not empty arcMetaCode1Info }">--%>
<%--                                                <form:select path="metaCode1" cssClass="form-control mr-sm-2 mb-2" title="${arcMetaCode1Info.catName}">--%>
<%--                                                    <option value="">${arcMetaCode1Info.catName}</option>--%>
<%--                                                    <form:options items="${arcMetaCode1List}" itemLabel="codeName" itemValue="codeId"/>--%>
<%--                                                </form:select>--%>
<%--                                            </c:if>--%>
<%--                                            <c:if test="${not empty arcMetaCode2Info }">--%>
<%--                                                <form:select path="metaCode2" cssClass="form-control mr-sm-2 mb-2" title="${arcMetaCode2Info.catName}">--%>
<%--                                                    <option value="">${arcMetaCode2Info.catName}</option>--%>
<%--                                                    <form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>--%>
<%--                                                </form:select>--%>
<%--                                            </c:if>--%>
<%--                                            <c:if test="${not empty arcMetaCode3Info }">--%>
<%--                                                <form:select path="metaCode3" cssClass="form-control mr-sm-2 mb-2" title="${arcMetaCode3Info.catName}">--%>
<%--                                                    <option value="">${arcMetaCode3Info.catName}</option>--%>
<%--                                                    <form:options items="${arcMetaCode3List}" itemLabel="codeName" itemValue="codeId"/>--%>
<%--                                                </form:select>--%>
<%--                                            </c:if>--%>

<%--                                        </c:if>--%>

<%--                                        <form:input path="arcMedia" cssClass="form-control col-sm-1 mr-sm-1 mb-2" title="매체" placeholder="매체"/>--%>
<%--                                        <form:input path="arcYear" cssClass="form-control col-sm-1 mr-sm-1 mb-2" title="제작년도" placeholder="제작년도"/>--%>
<%--                                        <form:input path="arcAdvertiser" cssClass="form-control col-sm-1 mr-sm-1 mb-2" title="광고주" placeholder="광고주"/>--%>
<%--                                        <form:input path="arcProduct" cssClass="form-control col-sm-2 mr-sm-1 mb-2" title="제품명" placeholder="제품명"/>--%>
<%--                                        <form:input path="arcActor" cssClass="form-control col-sm-1 mr-sm-1 mb-2" title="성우" placeholder="성우"/>--%>

<%--                                        <form:select path="arcSelected1" cssClass="form-control mr-sm-2 mb-2" title="광고자료관추출 여부">--%>
<%--                                            <option value="">광고자료관추출 여부</option>--%>
<%--                                            <form:option value="true" label="추출" />--%>
<%--                                            <form:option value="false" label="미추출" />--%>
<%--                                        </form:select>--%>

<%--                                        <form:select path="arcUse" cssClass="form-control mr-sm-2 mb-2" title="게시여부">--%>
<%--                                            <option value="">게시여부</option>--%>
<%--                                            <form:option value="true" label="게시" />--%>
<%--                                            <form:option value="false" label="게시안함" />--%>
<%--                                        </form:select>--%>



<%--                                        <form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">--%>
<%--                                            <option value="">전체</option>--%>
<%--                                            <form:option value="ARC_TITLE" label="제목" />--%>
<%--                                            <form:option value="ARC_CONTENT" label="내용" />--%>
<%--                                        </form:select>--%>
<%--                                        <form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>--%>
<%--                                        <button class="btn btn-outline-success waves-effect mb-2 " type="submit">검색</button>--%>
<%--                                    </div>--%>
<%--                                </form:form>--%>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- //검색폼 -->

                <!-- 목록 -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card m-b-30">
                            <div class="card-body">


                                <!-- alert maeeage -->
                                <c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />

                                <!-- info -->
                                <div class="p-3 mb-3 bg-light text-info font-weight-bold">
                                    <div><i class="mdi mdi-information"></i> 신청서 목록 </div>
                                </div>

                                <div class="table-responsive">
                                    <table class="table table-striped table-hover ">
                                        <colgroup>
                                            <col style="width: 30px;" />
                                            <col style="width: 70px;" /><!-- 번호 -->
                                            <col style="width: auto" />
                                            <col style="width: auto" />
                                            <col style="width: auto" />
                                            <col style="width: auto" />
                                            <col style="width: auto" />
                                            <col style="width: auto" />
                                            <col style="width: auto" />
                                            <col style="width: auto" />

                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th class="text-center">
                                                <div class="custom-control custom-checkbox">
                                                    <input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=applySeq]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
                                                    <label class="custom-control-label" for="selectAll"></label>
                                                </div>
                                            </th>
                                            <th>번호</th>
                                            <th>참가기업(팀)</th>
                                            <th>참가구분</th>
                                            <th>아이템명</th>
                                            <th>대표자명</th>
                                            <th>대표자 연락처</th>
                                            <th>대표자 이메일</th>
                                            <th>신청일</th>
                                       </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${empty paging.result}">
                                            <tr>
                                                <td colspan="12" class="text-center">데이터가 없습니다.</td>
                                            </tr>
                                        </c:if>
                                        <c:forEach items="${paging.result}" var="item" varStatus="vs">
                                            <tr>
                                                <td class="text-center">
                                                    <div class="custom-control custom-checkbox">
                                                        <input type="checkbox" name="applySeq" value="${item.applySeq}" class="custom-control-input" id="applySeq${vs.count }" />
                                                        <label class="custom-control-label" for="applySeq${vs.count }"></label>
                                                    </div>
                                                </td>
                                                <td>${paging.rowTop - vs.index}</td>
                                                <td><a href="${ADMIN_PATH}/application/form/update.do?applySeq=${item.applySeq}" class=""><strong>${item.companyName}</strong></a></td>
                                                <td>${item.joinGubun}</td>
                                                <td>${item.itemName}</td>
                                                <td>${item.ownerName}</td>
                                                <td>${item.ownerPhoneNum}</td>
                                                <td>${item.ownerEmail}</td>
                                                <td><fmt:formatDate value="${item.applyRegDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="form-inline">
                                    <div class="mr-auto">
                                        <button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
                                    </div>
                                    <div>
                                        <span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
                                    </div>
                                </div>

                                <!-- paging -->
                                <div>
                                    <c:out value="${paging.adminTextTag}" escapeXml="false"/>
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



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />