<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	$('#deleteBtn').click(function(e){
		e.preventDefault();
		var catIds = [];
		$('[name=catIds]:checked').each(function(idx, el){
			catIds.push(el.value);
		});
		if(catIds.length == 0){
			alert('삭제할 항목을 선택해 주세요.');
			return;
		}
		if(confirm('코드분류를 삭제할 경우 상세코드도 함께 삭제됩니다.\n\n삭제한 항목은 복구할 수 없습니다.\n\n삭제하시겠습니까?')){
			$.ajax({
				url : '${ADMIN_PATH}/code/category/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					catIds : catIds
				}
			}).done(function(result){
				if(result.success){
					alert('삭제되었습니다.');
					location.href = 'list.do';
				} else {
					if(result.text){
						alert(result.text);
					} else {
						alert('삭제하지 못하였습니다.[error]');
					}
				}
			}).fail(function(result){
				alert('삭제하지 못하였습니다.[fail]');
			});
		}
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

					<!-- alert maeeage -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />