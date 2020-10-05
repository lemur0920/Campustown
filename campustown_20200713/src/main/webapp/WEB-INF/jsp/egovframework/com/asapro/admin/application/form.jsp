<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<!-- Left Sidebar -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

<!-- Start right Content here -->
<head>
    <style type="text/css">
        .table-con.style2{border-top: 2px solid #767676;}
        .table-con.bd-top{border-top: 1px solid #dcdcdc;}
        .table-con.style2.mb30{margin-bottom:30px;}
        .table-con.style2.mb50{margin-bottom:50px;}
        .table-con.style2 table{width:100%;}
        .table-con.style2 table th{height: 35px;padding: 11px 20px;border-right: 0;border-bottom: 1px solid #dcdcdc;background: #f8f8f8;color: #333333;font-size: 17px;}
        .table-con.style2 table td{height: 35px;padding: 11px 20px;border-right: 1px solid #dcdcdc;border-bottom: 1px solid #dcdcdc;color: #666666;font-size: 15px;}
        .table-con.style2 table tr th:last-child, .table-con table tr td:last-child{border-right: 0;}
        .table-con.style2 table td .add-file span{margin-right: 5px;}
        .table-con.style2 table th.th-txl {text-align: left;}
        .table-con.style2 table td.tb-txl {text-align: left;}
        /*table-input*/
        .table-con.style2 table td input[type="text"]{border:1px solid #dcdcdc;height: 46px;font-size:17px;color:#666666;}
        .table-con.style2 table td select{border:1px solid #dcdcdc;height: 46px;font-size:17px;color:#666666;}
        .table-con.style2 table td input[type="checkbox"]{border:1px solid #dcdcdc;width:15px;height:15px;margin: 0 5px;}
        .table-con.style2 table td input[type="radio"]{border:1px solid #dcdcdc; width:13px;height:13px;}
        .table-con.style2 table td textarea{border:1px solid #dcdcdc; width:100%;padding:8px;}
        .table-con.style2 table td .input-60p{width:60%;}
        .table-con.style2 table td .input-80p{width:80%;}
        .table-con.style2 table td .input-100p{width:100%;}
        .table-con.style2 table td .input-150px{width:150px;text-align:center;}
        .table-con.style2 table td .input-180px{width:180px;}
        .table-con.style2 table td .input-320px{width:320px;}
        .table-con.style2 table td p.mt10{margin-top:10px;}
        .table-con.style2 table td .mr15{margin-right:15px;}
        .table-con.style2.applyTb table th{text-align: center;}
        .table-con.style2 table td{padding:10px 10px;}
        .table-con.style2.applyTb table td input[type="text"],input[type="email"]{border:1px solid #dcdcdc;height: 46px;font-size:17px;color:#666666;width:100%;}
        .table-con.style2.applyTb table td input.datepicker-ui{border:1px solid #dcdcdc;height: 46px;font-size:17px;color:#666666;
            margin-right: 10px;}

        .btn_01{
            border:none;
            color: #ffffff;
            background: #3567c6;
            margin: 5px;

        }
        .btn-area .btn02 {
            border:none;
            display: inline-block;
            vertical-align: middle;
            padding: 12px 25px;
            min-width: 170px;
            font-size: 18px;
            line-height: 22px;
            color: #ffffff;
            background: #3567c6;
            margin: 5px;
        }
    </style>
</head>
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

                                <!-- info -->
                                <div class="p-3 mb-3 bg-light text-info font-weight-bold">
                                    <div><i class="mdi mdi-information"></i> 신청서 </div>
                                </div>

                                <form name="applicationForm" id="applicationForm" method="post" enctype="multipart/form-data">
                                    <input name="applySeq" value="${item.applySeq}"hidden>
                                    ${SECURITY_TOKEN_TAG}
                                    <div class="area">
                                        <div class="table-con style2 mb30 applyTb">
                                            <table>
<%--                                                <caption>2020 X-TECH 창업 경진대회 참가신청서</caption>--%>
                                                <colgroup>
                                                    <col style="width: auto">
                                                    <col style="width: 10%;">
                                                    <col style="width: auto">
                                                    <col style="width: auto">
                                                    <col style="width: auto">
                                                    <col style="width: auto">
                                                    <col style="width: auto">
                                                    <col style="width: 70px;">
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th scope="row" class="th-txl"><label for="lb01">아이템명</label></th>
                                                    <td colspan="7">
                                                        <input type="text" id="lb01" name="itemName" class="input-100p" value="${item.itemName}" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th rowspan="4" scope="row" class="th-txl"><label for="">참가기업(팀)<br/>기본사항</label></th>
                                                    <th class="th-txl">참가구분</th>
                                                    <td colspan="6">
                                                        <p>${item.joinGubun}</p>
                                                        <input type="radio" id="gubun1" name="joinGubun" class="input-100p" value="예비창업자" checked>
                                                        <label for="gubun1">예비창업자</label>
                                                        <input type="radio" id="gubun2" name="joinGubun" class="input-100p" value="개인사업자">
                                                        <label for="gubun2">개인사업자</label>
                                                        <input type="radio" id="gubun3" name="joinGubun" class="input-100p" value="법인사업자">
                                                        <label for="gubun3">법인사업자</label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="th-txl">회사명(팀명)</th>
                                                    <td colspan="3">
                                                        <input type="text" name="companyName" value="${item.companyName}" class="input-100p" required>
                                                    </td>
                                                    <th class="th-txl">사업자번호</th>
                                                    <td colspan="2">
                                                        <input type="text" name="licenseNum"  value="${item.companyLicense}" class="input-100p" placeholder="없을 시 미기재">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="th-txl">설립일</th>
                                                    <td colspan="2">
                                                        <input name="setUpDate" class="input-80p datepicker-ui" value="${item.setUpDate}" placeholder="없을 시 미기재" readonly>
                                                        <a href="#n"><img src="/design/theme/campustown/images/sub/databox-icon.png" alt="달력" class="datepicker-img"></a>
                                                    </td>
                                                    <th class="th-txl">대표연락처</th>
                                                    <td colspan="3">
                                                        <input type="text" name="companyPhoneNum" value="${item.companyPhoneNum}" class="input-100p" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="th-txl">주소</th>
                                                    <td colspan="6">
                                                        <input type="text" name="companyAddr" value="${item.companyAddr}" class="input-100p" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th rowspan="3" class="th-txl">대표자<br/>기본사항</th>
                                                    <th class="th-txl">성명</th>
                                                    <td colspan="2">
                                                        <input type="text" name="ownerName" value="${item.ownerName}" class="input-100p" required>
                                                    </td>
                                                    <th class="th-txl">연락처</th>
                                                    <td colspan="3">
                                                        <input type="text" name="ownerPhoneNum" value="${item.ownerPhoneNum}" class="input-100p" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="th-txl">생년월일</th>
                                                    <td colspan="2">
                                                        <input name="ownerBirth" class="input-80p datepicker-ui" value="${item.ownerBirth}" readonly required>
                                                        <a href="#n"><img src="/design/theme/campustown/images/sub/databox-icon.png" alt="달력" class="datepicker-img"></a>
                                                    </td>
                                                    <th class="th-txl">E-MAIL</th>
                                                    <td colspan="3">
                                                        <input type="email" name="ownerEmail" value="${item.ownerEmail}"class="input-100p" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="th-txl">주소</th>
                                                    <td colspan="6">
                                                        <input type="text" name="ownerAddr" value="${item.ownerAddr}" class="input-100p" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th id="memHeader" rowspan="${fn:length(item.memberList)+2} " class="th-txl">참가인원<br/>기본사항</th>
                                                    <th class="th-txl">성명</th>
                                                    <th class="th-txl">생년월일</th>
                                                    <th class="th-txl">소속</th>
                                                    <th class="th-txl">직위</th>
                                                    <th class="th-txl">전화번호</th>
                                                    <th class="th-txl">이메일</th>
                                                    <th class="th-txl"> </th>
                                                </tr>
                                                <c:if test="${not empty item.memberList}">
                                                    <c:forEach items="${item.memberList}" var="mem" varStatus="vs">
                                                        <tr class="mem_tr">
                                                            <td><input type="text" name="memName" value="${mem.memName}" required></td>
                                                            <td><input name="memBirth" class="datepicker-ui input-60p" value="${mem.memBirth}" readonly required><a href="#n"><img src="/design/theme/campustown/images/sub/databox-icon.png" alt="달력" class="datepicker-img"></a></td>
                                                            <td><input type="text" name="memCompany" value="${mem.memCompany}" required></td>
                                                            <td><input type="text" name="memPosition" value="${mem.memPosition}" required></td>
                                                            <td><input type="text" name="memPhoneNum" value="${mem.memPhoneNum}" required></td>
                                                            <td><input type="email" name="memEmail" value="${mem.memEmail}" required></td>
                                                            <td style="text-align: center;"><button type="button" class="mem_remove btn_01" >삭제</button></td>
                                                        </tr>

                                                    </c:forEach>
                                                </c:if>
                                                <tr>
                                                    <td colspan="7" style="text-align: center;"><button type="button" id="memAdd" class="btn_01">참가인원 추가</button></td>
                                                </tr>
                                                <tr>
                                                    <th class="th-txl">『2020 X-TECH<br/>창업경진대회』<br/>지원경로</th>
                                                    <td colspan="7">
                                                        <p>${item.applyRoot}</p>
                                                        <input id="applyRoot01" class="applyRoot" name="applyRoot" type="radio" value="현수막, 포스터 등 홍보물" checked>
                                                        <label for="applyRoot01">현수막, 포스터 등 홍보물</label>
                                                        <input id="applyRoot02" class="applyRoot" name="applyRoot" type="radio" value="홈페이지(창업지원단, K-Startup등)">
                                                        <label for="applyRoot02">홈페이지(창업지원단, K-Startup등)</label>
                                                        <input id="applyRoot03" class="applyRoot" name="applyRoot" type="radio" value="유튜브, 페이스북, 블로그 등 SNS 홍보">
                                                        <label for="applyRoot03">유튜브, 페이스북, 블로그 등 SNS 홍보</label>
                                                        <br/>
                                                        <input id="applyRoot04" class="applyRoot" name="applyRoot" type="radio" value="E-Mail 수신">
                                                        <label for="applyRoot04">E-Mail 수신</label>
                                                        <input id="applyRoot05" class="applyRoot" name="applyRoot" type="radio" value="문자 수신">
                                                        <label for="applyRoot05">문자 수신</label>
                                                        <input id="applyRoot06" class="applyRoot" name="applyRoot" type="radio" value="etc">
                                                        <label for="applyRoot06">기타</label>
                                                        <input type="text" name="applyRoot" id="applyRootEtc"class="input-100p" value="" disabled  hidden>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="th-txl">희망 입주 공간</th>
                                                    <td colspan="7">
                                                        <p>${item.space}</p>
                                                        <input id="space01" class="space" name="space" type="radio" value="광운대" checked>
                                                        <label for="space01">광운대</label>
                                                        <input id="space02" class="space" name="space" type="radio" value="서울대">
                                                        <label for="space02">서울대</label>
                                                        <input id="space03" class="space" name="space" type="radio" value="세종대">
                                                        <label for="space03">세종대</label>
                                                        <input id="space04" class="space" name="space" type="radio" value="숭실대">
                                                        <label for="space04">숭실대</label>
                                                        <input id="space05" class="space" name="space" type="radio" value="etc">
                                                        <label for="space05">기타</label>
                                                        <input type="text" name="space" id="spaceEtc" class="input-100p" value="" disabled hidden>
                                                        <br/>
                                                        <p class="fc-red">※ 최종 선정 기업은 캠퍼스타운 입주 필수</p>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="btn-area txc" style="text-align: center;">
                                            <input type="button" class="btn02 fc-bg-blue" id="applyBtn" value="수정하기">
                                        </div>
                                    </div>
                            </div>
                            </form>

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
<script>

    $(function() {
        $('.datepicker-ui').datepicker({
            showOn: "focus",
            dateFormat:'yy-mm-dd'
        });

        $(".datepicker-ui").each(function() {
            $(this).datepicker('setDate', $(this).val());
        });

        $(document).on( "click" , ".datepicker-img", function() {
            $(this).closest("a").prev().focus();
        });

        $('.applyRoot').change(function() {
            if ($(this).val() == 'etc') {
                $('#applyRootEtc').removeAttr('hidden');
                $('#applyRootEtc').removeAttr('disabled');
                $('#applyRootEtc').attr("required",true);
            } else {
                $('#applyRootEtc').attr("hidden",true);
                $('#applyRootEtc').attr("disabled",true);
                $('#applyRootEtc').removeAttr('required');
            }
        });

        $('.space').change(function() {
            if ($(this).val() == 'etc') {
                $('#spaceEtc').removeAttr('hidden');
                $('#spaceEtc').removeAttr('disabled');
                $('#spaceEtc').attr("required",true);
            } else {
                $('#spaceEtc').attr("hidden",true);
                $('#spaceEtc').attr("disabled",true);
                $('#spaceEtc').removeAttr('required');
            }
        });

        $('#memAdd').click(function() {

            addRow = "<tr class=\"mem_tr\">\n" +
                "                    <td><input type=\"text\" name=\"memName\" required></td>\n" +
                "                    <td><input name=\"memBirth\" class=\"datepicker-ui input-60p\" readonly required><a href=\"#n\"><img src=\"/design/theme/campustown/images/sub/databox-icon.png\" alt=\"달력\" class=\"datepicker-img\"></a></td>\n" +
                "                    <td><input type=\"text\" name=\"memCompany\" required></td>\n" +
                "                    <td><input type=\"text\" name=\"memPosition\" required></td>\n" +
                "                    <td><input type=\"text\" name=\"memPhoneNum\" required></td>\n" +
                "                    <td><input type=\"email\" name=\"memEmail\" required></td>\n" +
                "                    <td style=\"text-align: center;\"><button type=\"button\" class=\"mem_remove btn_01\" >삭제</button></td>\n" +
                "                </tr>"
            rowspan = $("#memHeader").attr("rowSpan");
            rowspan = Number(rowspan)+1;
            $("#memHeader").attr("rowSpan",rowspan);

            if($(".mem_tr").length <= 0){
                $("#memHeader").closest("tr").after(addRow);
            }else{
                $(".mem_tr").last().after(addRow);
            }
            $('.datepicker-ui').datepicker({
                showOn: "focus"
            });
        });


        $(document).on( "click" , ".mem_remove", function() {
            rowspan = $("#memHeader").attr("rowSpan");
            rowspan = Number(rowspan)-1;
            $("#memHeader").attr("rowSpan",rowspan);
            $(this).closest("tr").remove();
        });

        $(document).on( "click" , "#applyBtn", function() {
            if($("input[name=applyRoot]").val() == "etc"){
                $("input[name=applyRoot]").val($("#applyRootEtc").val());
            }

            if($("input[name=space]").val() == "etc"){
                $("input[name=space]").val($("#spaceEtc").val());
            }

            var queryString = $("#applicationForm").serialize();
            $.ajax({
                url : '${ADMIN_PATH}/application/form/update.do'
                , type : 'post'
                , dataType: 'json'
                , data:queryString
            }).done(function(result){
                if(result.success){
                    swal({
                        type: 'success',
                        title: '수정이 완료되었습니다.',
                    }).then(function(){
                        location.href = "${ADMIN_PATH}/application/list";
                    });
                }
            }).fail(function(result){
                alert('에러가 발생했습니다. 관리자에게 문의하세요');
            });
        });


    });


</script>


<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />