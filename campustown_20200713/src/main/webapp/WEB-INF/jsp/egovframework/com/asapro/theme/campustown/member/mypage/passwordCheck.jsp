<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script>

jQuery(function($){
	
	
});

</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	
	<div class="sub_wrap${menuClassNames}" id="${currentMenu.menuId}">
		<div class="sub_page">
			<ul class="sub_sns_list">
				<li><a href="" title=""><img src="/design/theme/sccf/images/sub/sub_sns01.png" alt="페이스북" /></a></li>
				<li><a href="" title=""><img src="/design/theme/sccf/images/sub/sub_sns02.png" alt="트위터" /></a></li>
				<li><a href="" title=""><img src="/design/theme/sccf/images/sub/sub_sns03.png" alt="카카오스토리" /></a></li>
				<li><a href="" title=""><img src="/design/theme/sccf/images/sub/sub_sns04.png" alt="네이버 밴드" /></a></li>
				<li><a href="" title=""><img src="/design/theme/sccf/images/sub/sub_sns05.png" alt="인쇄(출력)" /></a></li>
			</ul>
			<div class="sub_cont_sec">
				<div class="sub_cont_line">
					<div class="sub_title_sec">
						<c:if test="${'text' eq currentMenu.menuTitleType}">
							<div class="sub_title">${currentMenu.menuName} <span>${currentMenu.menuTitleSubText}</span></div>
						</c:if>
						<c:if test="${'image' eq currentMenu.menuTitleType}">
							<div class="sub_title"><img src="${currentMenu.menuTitleImageUrl}" alt="${currentMenu.menuPlainName}" /></div>
						</c:if>
	
						<%-- Nav 위치 --%>
						${seo.location}
						<%-- // Nav 위치 --%>
					</div><!--// sub_title_sec -->
	
					<div class="sub_cont member">					
						<div class="member-form mypage-password">
							<div class="top_copy">
								<div class="top_tit"><img src="/design/theme/sccf/images/sub/my_pw_img01.png" />비밀번호 확인</div>
								<ul class="blt_info">
									<li>외부로부터 회원님의 정보를 안전하게 보호하기 위해 비밀번호를 다시 한 번 확인합니다.</li>
									<li>비밀번호는 타인에게 노출되지 않도록 항상 주의해 주시기 바랍니다.</li>
								</ul>
							</div><!--// top_copy -->
							<div class="my_pw_form">
								<form:form modelAttribute="passwordCheckForm" action="${APP_PATH}/member/mypage/${passwordMode}" method="post">
									${SECURITY_TOKEN_TAG}
									<ul>
										<c:if test="${passwordMode == 'updatePasswordCheck'}">
											<input type="hidden" name="memberId" value="${currentUser.userId}" />
										</c:if>
										<c:if test="${passwordMode == 'passwordCheck'}">
											<li>
												<form:label path="memberId" cssClass="control-label">아이디</form:label>
												<form:password path="memberId" />
												<form:errors path="memberId" element="span" cssClass="form-error" />
											</li>
										</c:if>
										<li>
											<form:label path="oldPassword" cssClass="control-label">비밀번호</form:label>
											<form:password path="oldPassword" />
											<form:errors path="oldPassword" element="span" cssClass="form-error" />
										</li>
									</ul>
									<div class="btm_btn"><button class="btn_modi_pw" type="submit">확인</button></div>
								</form:form>	
							</div><!--// my_pw_form -->
						</div><!--// mypage-password -->
	
						
					</div>
				</div>
			</div><!--// sub_cont_sec -->
	
		</div><!--// sub_page -->
	</div><!--// sub_wrap -->
	<!-- content end -->
	
<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />