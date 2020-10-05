<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- %@ page import="egovframework.com.asapro.member.admin.service.AdminMember"% -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<%@ page import="java.util.Date"%>
<%@ page import="com.ibm.icu.text.SimpleDateFormat"%>
<% //AdminMember currentAdmin = (AdminMember)request.getSession().getAttribute("currentAdmin"); %>

<%
Date now = new Date();
%>
<fmt:formatDate value="<%= now %>" pattern="yyyyMMdd" var="now"/>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />



<script src="${CONTEXT_PATH }/assets/pages/dashborad.js"></script>

<script>
jQuery(function($){
	
});
</script>
		
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />
	
	<!-- Start right Content here -->
	<div class="content-page">
	    <!-- Start content -->
	    <div class="content">
	
			<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />
	        
	        <div class="page-content-wrapper ">
	
	            <div class="container-fluid">
	
	                <div class="row">
	                
	                	<!-- ----------------------------------------------------------------------------------------------------------- -->
						<!-- ----- 0. 캠타, 창업팀 개수 ----- -->
						<!-- ----------------------------------------------------------------------------------------------------------- -->
						
	                    <div class="col-md-6 col-xl-3">
	                        <div class="mini-stat clearfix bg-white">
	                            <span class="mini-stat-icon bg-light"><i class="mdi mdi-cart-outline text-danger"></i></span>
	                            <div class="mini-stat-info text-right text-muted">
	                                <span class="counter text-danger" id="counter1" >${univListTot }</span>
	                               	 캠퍼스타운
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <div class="col-md-6 col-xl-3">
	                        <div class="mini-stat clearfix bg-success">
	                            <span class="mini-stat-icon bg-light"><i class="mdi mdi-currency-usd text-success"></i></span>
	                            <div class="mini-stat-info text-right text-white">
	                                <span class="counter text-white" id="counter4">${startListTot }</span>
	                               	 창업팀
	                            </div>
	                        </div>
	                    </div>
					
					</div>
					
					
					
	                <div class="row">
	
						<!-- ----------------------------------------------------------------------------------------------------------- -->
						<!-- ----- 1.공지사항----- -->
						<!-- ----------------------------------------------------------------------------------------------------------- -->
	                    
	                     <div class="col-md-6">
	                        <div class="card m-b-30">
	                            <div class="card-body">
	                            
	                            
	                                <h4 class="mt-0 m-b-15 header-title">공지사항</h4>
	
	                                <table class="table table-hover mb-0">
	                                    <thead>
	                                    <tr>
	                                        <th>번호</th>
	                                        <th>구분</th>
	                                        <th>제목</th>
	                                        <th>등록일</th>
	                                        <th>조회수</th>
	                                        <th>좋아요</th>
	                                    </tr>
	                                    </thead>
	                                    
	                                    
	                                    <tbody>
		                                    <!-- 상단 고정 -->
											<c:if test="${not empty noticeList}">
												<c:forEach items="${noticeList}" var="item" varStatus="vs">
													<tr>
														<td>
															<img src="${CONTEXT_PATH }/assets/images/tack.png" alt="상단고정">
														</td>
														<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
															<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
														</c:if>
														<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
															<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
														</c:if>
														<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
															<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
														</c:if>
														
														<td class="tb-txl">
															<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
															<a class="art" href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}"  target="_blank">
																${asapro:abbreviate(item.baTitle, 12) }
																<c:if test="${now - regdate <= 5}"><img src="${CONTEXT_PATH }/assets/images/new.gif" alt="새글"></c:if>
																<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
															</a>
														</td>
														<td class="mb-none">
															<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
														</td>
														<c:if test="${not empty boardConfig.bcStatusCode}">
															<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
														</c:if>
														<td class="mb-none">${item.baHit}</td>
														<c:if test="${boardConfig.bcSupportRecommend}">
															<td class="mb-none">
																${item.baRecommend}
															</td>
														</c:if>
														
													</tr>
												</c:forEach>
											</c:if>
	                                    	
	                                    	<c:forEach items="${paging.result}" var="item" varStatus="vs">
												<tr>
													<td class="mb-none">
														${paging.rowTop - vs.index} 
													</td>
													<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
														<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
													</c:if>
													<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
														<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
													</c:if>
													<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
														<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
													</c:if>
													
													<td class="tb-txl">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
														<a class=art href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}" target="_blank">
															${asapro:abbreviate(item.baTitle, 12) }
															<c:if test="${now - regdate <= 5}"><img src="${CONTEXT_PATH }/assets/images/new.gif" alt="새글"></c:if>
															<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
														</a>
													</td>
													
													<td class="mb-none">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
													</td>
													<c:if test="${not empty boardConfig.bcStatusCode}">
														<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
													</c:if>
													
													<td class="mb-none">${item.baHit}</td>
													<c:if test="${boardConfig.bcSupportRecommend}">
														<td class="mb-none">
															${item.baRecommend}
														</td>
													</c:if>
												</tr>
											</c:forEach>
	
	                                    </tbody>
	                                </table>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <!-- ----------------------------------------------------------------------------------------------------------- -->
						<!-- ----- 2.명예의 전당 ----- -->
						<!-- ----------------------------------------------------------------------------------------------------------- -->
	                    
	                    <div class="col-md-6">
	                        <div class="card m-b-30">
	                            <div class="card-body">
	                                <h4 class="mt-0 m-b-15 header-title">명예의전당</h4>
	
	                                <table class="table table-hover mb-0">
	                                    <thead>
	                                    <tr>
	                                        <th>번호</th>
	                                        <th>회기</th>
	                                        <th>제목</th>
	                                        <th>등록일</th>
	                                        <th>조회수</th>
	                                        <th>좋아요</th>
	                                    </tr>
										
	                                    </thead>
	                                    
	                                    <tbody>
	                                    	<!-- 일반 게시물 -->
											<c:if test="${empty paging4.result}">
												<tr>
													<td colspan="7" class="text-center">등록된 게시글이 없습니다.</td>
												</tr>
											</c:if>
											<c:forEach items="${paging4.result}" var="item" varStatus="vs">
												<tr>
													<td class="mb-none">${paging4.rowTop - vs.index } </td>
													<c:if test="${not empty boardConfig4.bcCategory1 and not empty boardConfig4.bcCategory1Name}">
														<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList4) }</td>
													</c:if>
													<c:if test="${not empty boardConfig4.bcCategory2 and not empty boardConfig4.bcCategory2Name}">
														<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList4) }</td>
													</c:if>
													<c:if test="${not empty boardConfig4.bcCategory3 and not empty boardConfig4.bcCategory3Name}">
														<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList4) }</td>
													</c:if>
													
													<td class="tb-txl">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
														<a class=art href="${item.permLink}?cp=${boardArticleSearch2.cp}${boardArticleSearch2.queryString}"  target="_blank">
															${asapro:abbreviate(item.baTitle, 12) }
														</a>
														<c:if test="${now - regdate <= 5}"><img src="${CONTEXT_PATH }/assets/images/new.gif" alt="새글"></c:if>
														<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
													</td>
													<td class="mb-none">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
													</td>
													<c:if test="${not empty boardConfig2.bcStatusCode}">
														<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
													</c:if>
													
													<td class="mb-none">${item.baHit}</td>
													<c:if test="${boardConfig2.bcSupportRecommend}">
														<td class="mb-none">
															${item.baRecommend}
														</td>
													</c:if>
													
												</tr>
											</c:forEach>
	                                    </tbody>
	                                    
	                                </table>
	                            </div>
	                        </div>
	                    </div>
	                
	                </div>
	                
	                
					
	                <div class="row">
	
		                <!-- ----------------------------------------------------------------------------------------------------------- -->
						<!-- ----- 3.캠퍼스타운 새소식 ----- -->
						<!-- ----------------------------------------------------------------------------------------------------------- -->
	                     
	                     <div class="col-md-6">
	                        <div class="card m-b-30">
	                            <div class="card-body">
	                                <h4 class="mt-0 m-b-15 header-title">캠퍼스타운 새소식</h4>
	                                <table class="table table-hover mb-0">
	                                    <thead>
	                                    
	                                    <tr>
	                                        <th>번호</th>
	                                        <th>캠퍼스타운</th>
	                                        <th>구분</th>
	                                        <th>제목</th>
	                                        <th>등록일</th>
	                                        <th>조회수</th>
	                                        <th>좋아요</th>
	                                    </tr>
	                                    </thead>
	                                    <tbody>
	                                    	
	                                    	
	                                    	<!-- 상단 고정 -->
											<c:if test="${not empty noticeList2}">
												<c:forEach items="${noticeList2}" var="item" varStatus="vs">
													<tr>
														<td class="mb-none"><img src="${CONTEXT_PATH }/assets/images/tack.png" alt="상단고정"></td>
														<td>
															<c:if test="${not empty item.depId and not empty departmentList}">
																<c:forEach items="${departmentList}" var="department" varStatus="dvs">
																	<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
																</c:forEach>
															</c:if>
														</td>
														<c:if test="${not empty boardConfig2.bcCategory1 and not empty boardConfig2.bcCategory1Name}">
															<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList2) }</td>
														</c:if>
														<c:if test="${not empty boardConfig2.bcCategory2 and not empty boardConfig2.bcCategory2Name}">
															<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList2) }</td>
														</c:if>
														<c:if test="${not empty boardConfig2.bcCategory3 and not empty boardConfig2.bcCategory3Name}">
															<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList2) }</td>
														</c:if>
														
														<td class="tb-txl">
															<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
															<a class="art" href="${item.permLink}?cp=${boardArticleSearch2.cp}${boardArticleSearch2.queryString}" target="_blank">
																${asapro:abbreviate(item.baTitle, 12) }
															</a>
															<c:if test="${now - regdate <= 5}"><img src="${CONTEXT_PATH }/assets/images/new.gif" alt="새글"></c:if>
															<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
														</td>
														<td class="mb-none">
															<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
														</td>
														<c:if test="${not empty boardConfig2.bcStatusCode}">
															<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
														</c:if>
														<td class="mb-none">${item.baHit}</td>
														<c:if test="${boardConfig2.bcSupportRecommend}">
															<td class="mb-none">
																${item.baRecommend}
															</td>
														</c:if>
														
													</tr>
												</c:forEach>
											</c:if>
											
											
											<!-- 일반 게시물 -->
											<c:if test="${empty paging2.result}">
												<tr>
													<td colspan="7" class="text-center">등록된 게시글이 없습니다.</td>
												</tr>
											</c:if>
											<c:forEach items="${paging2.result}" var="item" varStatus="vs">
												<tr>
													<td class="mb-none">${paging2.rowTop - vs.index } </td>
													<td>
														<c:if test="${not empty item.depId and not empty departmentList}">
															<c:forEach items="${departmentList}" var="department" varStatus="dvs">
																<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
															</c:forEach>
														</c:if>
													</td>
													<c:if test="${not empty boardConfig2.bcCategory1 and not empty boardConfig2.bcCategory1Name}">
														<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList2) }</td>
													</c:if>
													<c:if test="${not empty boardConfig2.bcCategory2 and not empty boardConfig2.bcCategory2Name}">
														<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList2) }</td>
													</c:if>
													<c:if test="${not empty boardConfig2.bcCategory3 and not empty boardConfig2.bcCategory3Name}">
														<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList2) }</td>
													</c:if>
													
													<td class="tb-txl">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
														<a class=art href="${item.permLink}?cp=${boardArticleSearch2.cp}${boardArticleSearch2.queryString}" target="_blank">
															${asapro:abbreviate(item.baTitle, 12) }
														</a>
														<c:if test="${now - regdate <= 5}"><img src="${CONTEXT_PATH }/assets/images/new.gif" alt="새글"></c:if>
														<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
													</td>
													<td class="mb-none">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
													</td>
													<c:if test="${not empty boardConfig2.bcStatusCode}">
														<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
													</c:if>
													
													<td class="mb-none">${item.baHit}</td>
													<c:if test="${boardConfig2.bcSupportRecommend}">
														<td class="mb-none">
															${item.baRecommend}
														</td>
													</c:if>
													
												</tr>
											</c:forEach>
	
	                                    </tbody>
	                                </table>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <!-- ----------------------------------------------------------------------------------------------------------- -->
						<!-- ----- 4.창업팀 새소식 ----- -->
						<!-- ----------------------------------------------------------------------------------------------------------- -->
	                     
	                     <div class="col-md-6">
	                        <div class="card m-b-30">
	                            <div class="card-body">
	                                <h4 class="mt-0 m-b-15 header-title">창업팀 새소식</h4>
	
	                                <table class="table table-hover mb-0">
	                                    <thead>
	                                    
	                                    <tr>
	                                        <th>번호</th>
	                                        <th>창업팀</th>
	                                        <th>구분</th>
	                                        <th>제목</th>
	                                        <th>등록일</th>
	                                        <th>조회수</th>
	                                        <th>좋아요</th>
	                                    </tr>
	                                    </thead>
	                                    <tbody>
											<!-- 상단 고정 -->
											<c:if test="${not empty noticeList3}">
												<c:forEach items="${noticeList3}" var="item" varStatus="vs">
													<tr>
														<td class="mb-none"><img src="${CONTEXT_PATH }/assets/images/tack.png" alt="상단고정"></td>
														<td>
															<c:if test="${not empty item.teamId and not empty teamList}">
																<c:forEach items="${teamList}" var="team" varStatus="tvs">
																	<c:if test="${team.compId eq item.teamId}">${team.compNm}</c:if>
																</c:forEach>
															</c:if>
														</td>
														<c:if test="${not empty boardConfig3.bcCategory1 and not empty boardConfig3.bcCategory1Name}">
															<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList3) }</td>
														</c:if>
														<c:if test="${not empty boardConfig3.bcCategory2 and not empty boardConfig3.bcCategory2Name}">
															<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList3) }</td>
														</c:if>
														<c:if test="${not empty boardConfig3.bcCategory3 and not empty boardConfig3.bcCategory3Name}">
															<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList3) }</td>
														</c:if>
														
														<td class="tb-txl">
															<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
															<a class="art" href="${item.permLink}?cp=${boardArticleSearch3.cp}${boardArticleSearch3.queryString}" target="_blank">
																${asapro:abbreviate(item.baTitle, 12) }
																<c:if test="${now - regdate <= 5}"><img src="${CONTEXT_PATH }/assets/images/new.gif" alt="새글"></c:if>
																<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
															</a>
														</td>
														<td class="mb-none">
															<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
														</td>
														<c:if test="${not empty boardConfig.bcStatusCode}">
															<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
														</c:if>
														<td class="mb-none">${item.baHit}</td>
														<c:if test="${boardConfig.bcSupportRecommend}">
															<td class="mb-none">
																${item.baRecommend}
															</td>
														</c:if>
														
													</tr>
												</c:forEach>
											</c:if>
											
											<c:forEach items="${paging3.result}" var="item" varStatus="vs">
												<tr>
													<td class="mb-none">${paging3.rowTop - vs.index} </td>
													<td>
														<c:if test="${not empty item.teamId and not empty teamList}">
															<c:forEach items="${teamList}" var="team" varStatus="tvs">
																<c:if test="${team.compId eq item.teamId}">${team.compNm}</c:if>
															</c:forEach>
														</c:if>
													</td>
													<c:if test="${not empty boardConfig3.bcCategory1 and not empty boardConfig3.bcCategory1Name}">
														<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList3) }</td>
													</c:if>
													<c:if test="${not empty boardConfig3.bcCategory2 and not empty boardConfig3.bcCategory2Name}">
														<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList3) }</td>
													</c:if>
													<c:if test="${not empty boardConfig3.bcCategory3 and not empty boardConfig3.bcCategory3Name}">
														<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList3) }</td>
													</c:if>
													
													<td class="tb-txl">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
														<a class=art href="${item.permLink}?cp=${boardArticleSearch3.cp}${boardArticleSearch3.queryString}" target="_blank">
															${asapro:abbreviate(item.baTitle, 12) }
															<c:if test="${now - regdate <= 5}"><img src="${CONTEXT_PATH }/assets/images/new.gif" alt="새글"></c:if>
															<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
														</a>
													</td>
													<td class="mb-none">
														<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
													</td>
													<c:if test="${not empty boardConfig.bcStatusCode}">
														<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
													</c:if>
													
													<td class="mb-none">${item.baHit}</td>
													<c:if test="${boardConfig.bcSupportRecommend}">
														<td class="mb-none">
															${item.baRecommend}
														</td>
													</c:if>
												</tr>
											</c:forEach>
											
	                                    </tbody>
	                                </table>
	                            </div>
	                        </div>
	                    </div>
	                
	                </div>
	                <!-- end row -->
	
	
	            </div><!-- container -->
	
	
	        </div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />