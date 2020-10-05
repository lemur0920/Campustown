<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date"%>
<form name="applicationForm" id="applicationForm" method="post" enctype="multipart/form-data">
    ${SECURITY_TOKEN_TAG}
    <div class="area">
        <div class="table-con style2 mb30 applyTb mobile">
            <table>
                <caption>2020 X-TECH 창업 경진대회 참가신청서</caption>
                <colgroup>
                    <col style="width: auto">
                    <col style="width: auto">
                    <col style="width: auto">
                    <col style="width: auto">
                    <col style="width: auto">
                    <col style="width: auto">
                    <col style="width: 40px;">
                </colgroup>
                <tbody>
                <tr>
                    <th colspan="7">아이템명</th>
                </tr>
                <tr>
                    <td colspan="7">
                        <input type="text" id="lb01" name="itemName" class="input-100p" required>
                    </td>
                </tr>
                <tr>
                    <th colspan="7">참가기업(팀) 기본사항</th>
                </tr>
                <tr>
                    <th class="th-txl">참가<br/>구분</th>
                    <td colspan="6">
                        <input type="radio" id="gubun1" name="joinGubun" class="input-100p" value="예비창업자" checked>
                        <label for="gubun1">예비창업자</label>
                        <input type="radio" id="gubun2" name="joinGubun" class="input-100p" value="개인사업자">
                        <label for="gubun2">개인사업자</label>
                        <input type="radio" id="gubun3" name="joinGubun" class="input-100p" value="법인사업자">
                        <label for="gubun3">법인사업자</label>
                    </td>
                </tr>
                <tr>
                    <th class="th-txl">회사명<br>/(팀명)</th>
                    <td colspan="2">
                        <input type="text" name="companyName" class="input-100p" required>
                    </td>
                    <th class="th-txl">사업자<br>/번호</th>
                    <td colspan="3">
                        <input type="text" name="companyLicense" class="input-100p" placeholder="없을시 미기재">
                    </td>
                </tr>
                <tr>
                    <th class="th-txl">설립일</th>
                    <td colspan="2">
                        <input name="setUpDate" class="input-100p datepicker-ui" placeholder="없을시 미기재" readonly>
                    </td>
                    <th class="th-txl">대표<br/>연락처</th>
                    <td colspan="3">
                        <input type="text" name="companyPhoneNum" class="input-100p" required>
                    </td>
                </tr>
                <tr>
                    <th class="th-txl">주소</th>
                    <td colspan="6">
                        <input type="text" name="companyAddr" class="input-100p" required>
                    </td>
                </tr>
                <tr><th class="th-txl" colspan="7">대표자 기본사항</th></tr>
                <tr>
                    <th class="th-txl">성명</th>
                    <td colspan="2">
                        <input type="text" name="ownerName" class="input-100p" required>
                    </td>
                    <th class="th-txl">연락처</th>
                    <td colspan="3">
                        <input type="text" name="ownerPhoneNum" class="input-100p" required>
                    </td>
                </tr>
                <tr>
                    <th class="th-txl">생년<br/>월일</th>
                    <td colspan="2">
                        <input name="ownerBirth" class="input-100p datepicker-ui" readonly required>
                    </td>
                    <th class="th-txl">E-MAIL</th>
                    <td colspan="3">
                        <input type="email" name="ownerEmail" class="input-100p" required>
                    </td>
                </tr>
                <tr>
                    <th class="th-txl">주소</th>
                    <td colspan="6">
                        <input type="text" name="ownerAddr" class="input-100p" required>
                    </td>
                </tr>
                <tr><th colspan="7">참가인원 기본사항</th></tr>
                <tr id="memHeader">
<%--                    <th id="memHeader" rowspan="2" class="th-txl">참가인원<br/>기본사항</th>--%>
                    <th class="th-txl">성명</th>
                    <th class="th-txl">생년<br/>월일</th>
                    <th class="th-txl">소속</th>
                    <th class="th-txl">직위</th>
                    <th class="th-txl">전화번호</th>
                    <th class="th-txl">이메일</th>
                    <th class="th-txl"> </th>
                </tr>
                <tr>
                    <td colspan="7" style="text-align: center;"><button type="button" class="btn_01" id="memAdd">참가인원 추가</button></td>
                </tr>
                <tr><th colspan="7">『2020 X-TECH 창업경진대회 지원경로</th></tr>
                <tr>
<%--                    <th class="th-txl">『2020 X-TECH 창업경진대회』<br/>지원경로</th>--%>
                    <td colspan="7">
                        <input id="applyRoot01" class="applyRoot" name="applyRoot" type="radio" value="현수막, 포스터 등 홍보물" checked>
                        <label for="applyRoot01">현수막, 포스터 등 홍보물</label>
                        <input id="applyRoot02" class="applyRoot" name="applyRoot" type="radio" value="홈페이지(창업지원단, K-Startup등)">
                        <label for="applyRoot02">홈페이지(창업지원단, K-Startup등)</label>
                        <input id="applyRoot03" class="applyRoot" name="applyRoot" type="radio" value="유튜브, 페이스북, 블로그 등 SNS 홍보">
                        <label for="applyRoot03">유튜브, 페이스북, 블로그 등 SNS 홍보</label>
                        <br/>
                        <br/>
                        <input id="applyRoot04" class="applyRoot" name="applyRoot" type="radio" value="E-Mail 수신">
                        <label for="applyRoot04">E-Mail 수신</label>
                        <input id="applyRoot05" class="applyRoot" name="applyRoot" type="radio" value="문자 수신">
                        <label for="applyRoot05">문자 수신</label>
                        <input id="applyRoot06" class="applyRoot" name="applyRoot" type="radio" value="etc">
                        <label for="applyRoot06">기타</label>
                        <input type="text" id="applyRootEtc"class="input-100p" name="applyRootText" value="" hidden disabled>
                    </td>
                </tr>
                <tr><th colspan="7">희망 입주 공간</th></tr>
                <tr>
<%--                    <th class="th-txl">희망 입주 공간</th>--%>
                    <td colspan="7">
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
                        <input type="text" id="spaceEtc" name="spaceText" class="input-100p" hidden disabled>
                        <br/>
                        <br/>
                        <p class="fc-red">※ 최종 선정 기업은 캠퍼스타운 입주 필수</p>
                    </td>
                </tr>
                <tr>
                    <th class="th-txl">사업계획서</th>
                    <td colspan="7">
                        <input id="plan_file" class="" name="plan_file" type="file"  accept=".hwp" required>
                        <a href="${APP_PATH}/file/download/uu/93910f77ca4342e58c1a25c84434e3d5" class="btn btn-outline-primary waves-effect waves-light btn-sm">사업계획서 양식 다운로드</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="btn-area txc mobile">
            <a href="/" class="btn02 fc-bg-gray">메인으로</a>
            <input type="submit" class="btn02 fc-bg-blue" id="applyBtn" value="신청하기">
        </div>
    </div>
    </div>
</form>

<script>
    $(function() {
        $('#memAdd').click(function() {

            addRow = "<tr class=\"mem_tr\">\n" +
                "                    <td><input type=\"text\" name=\"memName\" required></td>\n" +
                "                    <td><input name=\"memBirth\" class=\"datepicker-ui input-60p\" readonly required></td>\n" +
                "                    <td><input type=\"text\" name=\"memCompany\" required></td>\n" +
                "                    <td><input type=\"text\" name=\"memPosition\" required></td>\n" +
                "                    <td><input type=\"text\" name=\"memPhoneNum\" required></td>\n" +
                "                    <td><input type=\"email\" name=\"memEmail\" required></td>\n" +
                "                    <td style=\"text-align: center;\"><button type=\"button\" class=\"mem_remove btn_01\" >삭제</button></td>\n" +
                "                </tr>"
            // rowspan = $("#memHeader").attr("rowSpan");
            // rowspan = Number(rowspan)+1;
            // $("#memHeader").attr("rowSpan",rowspan);

            if($(".mem_tr").length <= 0){
                $("#memHeader").after(addRow);
            }else{
                $(".mem_tr").last().after(addRow);
            }
            $('.datepicker-ui').datepicker({
                showOn: "focus"
            });
        });


        $(document).on( "click" , ".mem_remove", function() {
            // rowspan = $("#memHeader").attr("rowSpan");
            // rowspan = Number(rowspan)-1;
            // $("#memHeader").attr("rowSpan",rowspan);
            $(this).closest("tr").remove();
        });

    })
</script>