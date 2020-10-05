<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>

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

					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<div class="row">
                         <div class="col-12">
                             <div class="card m-b-30">
                                 <div class="card-body">

                                     <div class="row">
                                         <div class="col-xl-2 col-lg-3 col-md-4">

                                             <h4 class="m-t-5 m-b-15 font-16">Created Events</h4>
                                             <form method="post" id="add_event_form" class="m-t-5 m-b-20">
                                                 <input type="text" class="form-control new-event-form" placeholder="Add new event..." />
                                             </form>

                                             <div id='external-events'>
                                                 <h4 class="m-b-15 font-16">Draggable Events</h4>
                                                 <div class='fc-event'>My Event 1</div>
                                                 <div class='fc-event'>My Event 2</div>
                                                 <div class='fc-event'>My Event 3</div>
                                                 <div class='fc-event'>My Event 4</div>
                                                 <div class='fc-event'>My Event 5</div>
                                             </div>

                                             <!-- checkbox -->
                                             <div class="custom-control custom-checkbox mt-3">
                                                 <input type="checkbox" class="custom-control-input" id="drop-remove" data-parsley-multiple="groups" data-parsley-mincheck="2">
                                                 <label class="custom-control-label" for="drop-remove">Remove after drop</label>
                                             </div>

                                         </div>

										<div id="calendar"
											class="col-xl-10 col-lg-9 col-md-8 fc fc-unthemed fc-ltr">
											<div class="fc-toolbar">
												<div class="fc-left">
													<div class="fc-button-group">
														<button type="button"
															class="fc-prev-button fc-button fc-state-default fc-corner-left">
															<span class="fc-icon fc-icon-left-single-arrow"></span>
														</button>
														<button type="button"
															class="fc-next-button fc-button fc-state-default fc-corner-right">
															<span class="fc-icon fc-icon-right-single-arrow"></span>
														</button>
													</div>
													<button type="button"
														class="fc-today-button fc-button fc-state-default fc-corner-left fc-corner-right fc-state-disabled"
														disabled="">today</button>
												</div>
												<div class="fc-right">
													<div class="fc-button-group">
														<button type="button"
															class="fc-month-button fc-button fc-state-default fc-corner-left fc-state-active">month</button>
														<button type="button"
															class="fc-basicWeek-button fc-button fc-state-default">week</button>
														<button type="button"
															class="fc-basicDay-button fc-button fc-state-default fc-corner-right">day</button>
													</div>
												</div>
												<div class="fc-center">
													<h2>May 2018</h2>
												</div>
												<div class="fc-clear"></div>
											</div>
											<div class="fc-view-container" style="">
												<div class="fc-view fc-month-view fc-basic-view" style="">
													<table>
														<thead class="fc-head">
															<tr>
																<td class="fc-head-container fc-widget-header"><div
																		class="fc-row fc-widget-header">
																		<table>
																			<thead>
																				<tr>
																					<th class="fc-day-header fc-widget-header fc-sun">Sun</th>
																					<th class="fc-day-header fc-widget-header fc-mon">Mon</th>
																					<th class="fc-day-header fc-widget-header fc-tue">Tue</th>
																					<th class="fc-day-header fc-widget-header fc-wed">Wed</th>
																					<th class="fc-day-header fc-widget-header fc-thu">Thu</th>
																					<th class="fc-day-header fc-widget-header fc-fri">Fri</th>
																					<th class="fc-day-header fc-widget-header fc-sat">Sat</th>
																				</tr>
																			</thead>
																		</table>
																	</div></td>
															</tr>
														</thead>
														<tbody class="fc-body">
															<tr>
																<td class="fc-widget-content"><div
																		class="fc-scroller fc-day-grid-container"
																		style="overflow: hidden; height: 929px;">
																		<div class="fc-day-grid fc-unselectable">
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 154px;">
																				<div class="fc-bg">
																					<table>
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-other-month fc-past"
																									data-date="2018-04-29"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-other-month fc-past"
																									data-date="2018-04-30"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-past"
																									data-date="2018-05-01"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-past"
																									data-date="2018-05-02"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-past"
																									data-date="2018-05-03"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-past"
																									data-date="2018-05-04"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-past"
																									data-date="2018-05-05"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td
																									class="fc-day-number fc-sun fc-other-month fc-past"
																									data-date="2018-04-29">29</td>
																								<td
																									class="fc-day-number fc-mon fc-other-month fc-past"
																									data-date="2018-04-30">30</td>
																								<td class="fc-day-number fc-tue fc-past"
																									data-date="2018-05-01">1</td>
																								<td class="fc-day-number fc-wed fc-past"
																									data-date="2018-05-02">2</td>
																								<td class="fc-day-number fc-thu fc-past"
																									data-date="2018-05-03">3</td>
																								<td class="fc-day-number fc-fri fc-past"
																									data-date="2018-05-04">4</td>
																								<td class="fc-day-number fc-sat fc-past"
																									data-date="2018-05-05">5</td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td rowspan="2"></td>
																								<td class="fc-event-container" colspan="5"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																											class="fc-content">
																											<span class="fc-title">My Event 5</span>
																										</div>
																										<div class="fc-resizer fc-end-resizer"></div></a></td>
																								<td rowspan="2"></td>
																							</tr>
																							<tr>
																								<td></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">12a</span> <span
																												class="fc-title">All Day Event</span>
																										</div></a></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 154px;">
																				<div class="fc-bg">
																					<table>
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-past"
																									data-date="2018-05-06"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-past"
																									data-date="2018-05-07"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-past"
																									data-date="2018-05-08"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-past"
																									data-date="2018-05-09"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-past"
																									data-date="2018-05-10"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-past"
																									data-date="2018-05-11"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-past"
																									data-date="2018-05-12"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-number fc-sun fc-past"
																									data-date="2018-05-06">6</td>
																								<td class="fc-day-number fc-mon fc-past"
																									data-date="2018-05-07">7</td>
																								<td class="fc-day-number fc-tue fc-past"
																									data-date="2018-05-08">8</td>
																								<td class="fc-day-number fc-wed fc-past"
																									data-date="2018-05-09">9</td>
																								<td class="fc-day-number fc-thu fc-past"
																									data-date="2018-05-10">10</td>
																								<td class="fc-day-number fc-fri fc-past"
																									data-date="2018-05-11">11</td>
																								<td class="fc-day-number fc-sat fc-past"
																									data-date="2018-05-12">12</td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																											class="fc-content">
																											<span class="fc-title">My Event 5</span>
																										</div>
																										<div class="fc-resizer fc-end-resizer"></div></a></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																											class="fc-content">
																											<span class="fc-title">My Event 4</span>
																										</div>
																										<div class="fc-resizer fc-end-resizer"></div></a></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																											class="fc-content">
																											<span class="fc-title">My Event 3</span>
																										</div>
																										<div class="fc-resizer fc-end-resizer"></div></a></td>
																								<td></td>
																								<td></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-not-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">12a</span> <span
																												class="fc-title">Long Event</span>
																										</div></a></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 154px;">
																				<div class="fc-bg">
																					<table>
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-past"
																									data-date="2018-05-13"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-past"
																									data-date="2018-05-14"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-past"
																									data-date="2018-05-15"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-past"
																									data-date="2018-05-16"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-today fc-state-highlight"
																									data-date="2018-05-17"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-future"
																									data-date="2018-05-18"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-future"
																									data-date="2018-05-19"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-number fc-sun fc-past"
																									data-date="2018-05-13">13</td>
																								<td class="fc-day-number fc-mon fc-past"
																									data-date="2018-05-14">14</td>
																								<td class="fc-day-number fc-tue fc-past"
																									data-date="2018-05-15">15</td>
																								<td class="fc-day-number fc-wed fc-past"
																									data-date="2018-05-16">16</td>
																								<td
																									class="fc-day-number fc-thu fc-today fc-state-highlight"
																									data-date="2018-05-17">17</td>
																								<td class="fc-day-number fc-fri fc-future"
																									data-date="2018-05-18">18</td>
																								<td class="fc-day-number fc-sat fc-future"
																									data-date="2018-05-19">19</td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td class="fc-event-container" colspan="2"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-not-start fc-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-title">Long Event</span>
																										</div></a></td>
																								<td rowspan="2"></td>
																								<td rowspan="2"></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">10:30a</span> <span
																												class="fc-title">Meeting</span>
																										</div></a></td>
																								<td class="fc-event-container" rowspan="2"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">7p</span> <span
																												class="fc-title">Birthday Party</span>
																										</div></a></td>
																								<td rowspan="2"></td>
																							</tr>
																							<tr>
																								<td></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">4p</span> <span
																												class="fc-title">Repeating Event</span>
																										</div></a></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">12p</span> <span
																												class="fc-title">Lunch</span>
																										</div></a></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 154px;">
																				<div class="fc-bg">
																					<table>
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-future"
																									data-date="2018-05-20"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-future"
																									data-date="2018-05-21"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-future"
																									data-date="2018-05-22"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-future"
																									data-date="2018-05-23"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-future"
																									data-date="2018-05-24"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-future"
																									data-date="2018-05-25"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-future"
																									data-date="2018-05-26"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-number fc-sun fc-future"
																									data-date="2018-05-20">20</td>
																								<td class="fc-day-number fc-mon fc-future"
																									data-date="2018-05-21">21</td>
																								<td class="fc-day-number fc-tue fc-future"
																									data-date="2018-05-22">22</td>
																								<td class="fc-day-number fc-wed fc-future"
																									data-date="2018-05-23">23</td>
																								<td class="fc-day-number fc-thu fc-future"
																									data-date="2018-05-24">24</td>
																								<td class="fc-day-number fc-fri fc-future"
																									data-date="2018-05-25">25</td>
																								<td class="fc-day-number fc-sat fc-future"
																									data-date="2018-05-26">26</td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">4p</span> <span
																												class="fc-title">Repeating Event</span>
																										</div></a></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 154px;">
																				<div class="fc-bg">
																					<table>
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-future"
																									data-date="2018-05-27"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-future"
																									data-date="2018-05-28"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-future"
																									data-date="2018-05-29"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-future"
																									data-date="2018-05-30"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-future"
																									data-date="2018-05-31"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-other-month fc-future"
																									data-date="2018-06-01"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-other-month fc-future"
																									data-date="2018-06-02"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-number fc-sun fc-future"
																									data-date="2018-05-27">27</td>
																								<td class="fc-day-number fc-mon fc-future"
																									data-date="2018-05-28">28</td>
																								<td class="fc-day-number fc-tue fc-future"
																									data-date="2018-05-29">29</td>
																								<td class="fc-day-number fc-wed fc-future"
																									data-date="2018-05-30">30</td>
																								<td class="fc-day-number fc-thu fc-future"
																									data-date="2018-05-31">31</td>
																								<td
																									class="fc-day-number fc-fri fc-other-month fc-future"
																									data-date="2018-06-01">1</td>
																								<td
																									class="fc-day-number fc-sat fc-other-month fc-future"
																									data-date="2018-06-02">2</td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end fc-draggable"
																									href="http://google.com/"><div
																											class="fc-content">
																											<span class="fc-time">12a</span> <span
																												class="fc-title">Click for Google</span>
																										</div></a></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 159px;">
																				<div class="fc-bg">
																					<table>
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-other-month fc-future"
																									data-date="2018-06-03"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-other-month fc-future"
																									data-date="2018-06-04"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-other-month fc-future"
																									data-date="2018-06-05"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-other-month fc-future"
																									data-date="2018-06-06"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-other-month fc-future"
																									data-date="2018-06-07"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-other-month fc-future"
																									data-date="2018-06-08"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-other-month fc-future"
																									data-date="2018-06-09"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td
																									class="fc-day-number fc-sun fc-other-month fc-future"
																									data-date="2018-06-03">3</td>
																								<td
																									class="fc-day-number fc-mon fc-other-month fc-future"
																									data-date="2018-06-04">4</td>
																								<td
																									class="fc-day-number fc-tue fc-other-month fc-future"
																									data-date="2018-06-05">5</td>
																								<td
																									class="fc-day-number fc-wed fc-other-month fc-future"
																									data-date="2018-06-06">6</td>
																								<td
																									class="fc-day-number fc-thu fc-other-month fc-future"
																									data-date="2018-06-07">7</td>
																								<td
																									class="fc-day-number fc-fri fc-other-month fc-future"
																									data-date="2018-06-08">8</td>
																								<td
																									class="fc-day-number fc-sat fc-other-month fc-future"
																									data-date="2018-06-09">9</td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																		</div>
																	</div></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>


                                     </div>
                                     <!-- end row -->

                                 </div>
                             </div>
                         </div> <!-- end col -->
                     </div> <!-- end row -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->        
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->



<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />