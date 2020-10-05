<%--
  Created by IntelliJ IDEA.
  User: eney
  Date: 2020-08-27
  Time: 05:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date"%>

<c:import url="${design.header}" charEncoding="UTF-8" />
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<c:choose>
    <c:when test="${userAgent.operatingSystem.mobileDevice eq true}">
        <c:import url="./form_mobile.jsp" charEncoding="utf-8" />
    </c:when>
    <c:otherwise>
        <c:import url="./form_pc.jsp" charEncoding="utf-8" />
    </c:otherwise>
</c:choose>
<script>


    $(function() {
        $('.datepicker-ui').datepicker({
            showOn: "focus",
            dateFormat:'yy-mm-dd'
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

        $('#applicationForm').submit(function(e) {

            e.preventDefault();


            if($("input[name=applyRoot]").val() == "etc"){
                $("input[name=applyRoot]").attr('value',$("#applyRootEtc").val());
            }

            if($("input[name=space]").val() == "etc"){
                $("input[name=space]").val($("#spaceEtc").val());
            }

            var queryString = $("#applicationForm").serialize();

            var form = $('#applicationForm')[0];

            // Create an FormData object
            var formData = new FormData(form);

            $.ajax({
                url : 'https://eney.co.kr:4005/apply/x_tech'
                , type : 'post'
                // , dataType: 'json'
                    ,processData: false
                    ,contentType: false
                    ,cache: false
                , enctype: "multipart/form-data"
                , data:formData
            }).done(function(result){
                if(result.success){
                    alert('신청이 완료되었습니다');
                    document.location.href = "/";
                }
                if(!result.success){
                    alert(result.msg);
                }
            }).fail(function(result){
                alert("에러가 발생하였습니다. 관리자에게 문의하세요")
            });

        });

    });


</script>
