<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
var saveId = 0; //글 번호 가져오기
var cmd = 0; //0:저장 1:수정
var seq = 0; //변호사 배정 ID

    function fn_paramId(idx, falg, seq){
        saveId = idx;
        cmd = falg;
        seq = seq;
    }

    function fn_lawyerSave(){
        var lawyerId = $(":input:radio[name=lawyer]:checked").val();

        if(saveId == 0){
            alert("서버와 통신 중 장애가 발생하였습니다. 관리자에게 문의 해주세요");
            throw error;
        }
        var statusCode = 4; //담당 변호사 배정
        var url = cmd == 0 ? "${ADMIN_PATH}/counsel/legal_counsel/lawyer/save.do" : "${ADMIN_PATH}/counsel/legal_counsel/lawyer/update.do";

        $.ajax({
            url : url,
            data : {
                id : saveId , lawyerId : lawyerId, statusCode : statusCode
            }
        }).done(function(result){
            alert("저장 했습니다.");
            //location.href = "${ADMIN_PATH}/counsel/legal_counsel/list.do?cp="+cp;
            location.reload();
        }).fail(function(result){
            alert('통신실패 [fail]');
        });
    }

function fn_lawyerUpdate(){
    var lawyerId = $(":input:radio[name=lawyer]:checked").val();

    if(saveId == 0){
        alert("서버와 통신 중 장애가 발생하였습니다. 관리자에게 문의 해주세요");
        throw error;
    }
    var statusCode = 4; //변호사 다시 배
    var url = "${ADMIN_PATH}/counsel/legal_counsel/lawyer/update.do";
    $.ajax({
        url : url,
        data : {
            id : saveId , lawyerId : lawyerId, statusCode : statusCode, seq : seq
        }
    }).done(function(result){
        alert("변호사 수정 완료");
    }).fail(function(result){
        alert('통신실패 [fail]');
    });
}




    $( document ).ready(function() {
    });
</script>
<div class="modal fade bs-example-modal-center" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title mt-0">변호사 배정</h5>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            </div>
            <div class="modal-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">선택</th>
                        <th scope="col">변호사 이름</th>
                        <th scope="col">아이디</th>
<%--                        <th scope="col">등록일</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${lawyersList}" var="lawyer" varStatus="lvs">
                    <tr>
                            <td>
                                <div class="custom-control custom-radio" id="lawyerId">
                                    <input type="radio" name="lawyer" class="custom-control-input" id="lawyer_${lvs.count}" value="${lawyer.adminId}" >
                                    <label class="custom-control-label" for="lawyer_${lvs.count}"></label>
                                </div>
                            </td>
                            <td>${lawyer.adminName}</td>
                            <td>${lawyer.adminId}</td>
<%--                            <td>2020-01-21</td>--%>
                    </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="fn_lawyerSave()">저장</button>
<%--                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="fn_lawyerSave(1)">수정</button>--%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->