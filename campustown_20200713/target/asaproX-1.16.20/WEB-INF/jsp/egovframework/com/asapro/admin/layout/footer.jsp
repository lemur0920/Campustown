<%@ page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
    
	<footer class="footer">
		Copyright© <%= Calendar.getInstance().get(Calendar.YEAR) %> Asadal<a href="${CONTEXT_PATH }/adminer/adminer-4.0.3.php" target="_blank">.</a> All Right Reserved<a href="${CONTEXT_PATH }/asset/worker/worker.jsp?worker=asadal" target="_blank">.</a>
		 (Was : <%=AsaproUtils.getServerAddress() %>	)
	</footer>
    </div>
    <!-- END wrapper -->
    
    
    <!-- 해더로 옮기지 마세요  -->
	
	<!-- Plugins js -->
	<script src="${CONTEXT_PATH }/assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.min.js"></script>
	<!-- <script src="${CONTEXT_PATH }/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script> -->
	<script src="${CONTEXT_PATH }/assets/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js"></script>
	<script src="${CONTEXT_PATH }/assets/plugins/bootstrap-touchspin/js/jquery.bootstrap-touchspin.min.js"></script>

	<!-- Plugins Init js -->
	<script src="${CONTEXT_PATH }/assets/pages/form-advanced.js"></script>
	<!-- <script src="${CONTEXT_PATH }/assets/pages/calendar-init.js"></script> -->

	
	
	
	
</body>
</html>