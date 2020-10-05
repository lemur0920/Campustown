<%@page import="egovframework.com.asapro.site.service.Site"%>
<%@page import="egovframework.com.asapro.support.ApplicationContextProvider"%>
<%@page import="egovframework.com.asapro.config.service.ConfigService"%>
<%@page import="egovframework.com.asapro.config.service.TextData"%>
<%@page import="com.google.api.client.googleapis.auth.oauth2.GoogleCredential"%>
<%@page import="java.util.Collections"%>
<%@page import="egovframework.com.asapro.config.service.Config"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row-fluid">
	<div class="span12">
<%
ConfigService configService = ApplicationContextProvider.getApplicationContext().getBean(ConfigService.class);
TextData oAuthJson = configService.getTextData("google_analytics", "ga_oauth_json");
Config gaConfig = (Config)request.getAttribute("gaConfig");

if( StringUtils.isNotBlank(oAuthJson.getTextContent()) && StringUtils.isNotBlank(gaConfig.getOption("ga_20_view_id")) ){
	
	GoogleCredential credential = GoogleCredential.fromStream( IOUtils.toInputStream(oAuthJson.getTextContent(), "UTF-8") );
	if( credential.createScopedRequired() ){
		//add scopes
		credential = credential.createScoped(Collections.singleton("https://www.googleapis.com/auth/analytics.readonly"));
	}
	credential.refreshToken();
	Site currentSite = (Site)request.getAttribute("currentSite");
	String siteId = currentSite.isSiteMain() ? "main" : currentSite.getSiteId();
%>
		<script>
		(function(w,d,s,g,js,fs){
		  g=w.gapi||(w.gapi={});g.analytics={q:[],ready:function(f){this.q.push(f);}};
		  js=d.createElement(s);fs=d.getElementsByTagName(s)[0];
		  js.src='https://apis.google.com/js/platform.js';
		  fs.parentNode.insertBefore(js,fs);js.onload=function(){g.load('analytics');};
		}(window,document,'script'));
		gapi.analytics.ready(function() {
			//Authorize the user with an access token obtained server side.
			gapi.analytics.auth.authorize({
				'serverAuth': {
					'access_token': '<%= credential.getAccessToken() %>'
				}
			});

			// Creates a new DataChart instance showing sessions over the past 30 days. It will be rendered inside an element with the id "chart-1-container".
			var dataChart1 = new gapi.analytics.googleCharts.DataChart({
				query: {
					'ids': 'ga:<%= gaConfig.getOption("ga_20_view_id") %>', // <-- Replace with the ids value for your view.
					'start-date': '30daysAgo',
					'end-date': 'yesterday',
					'metrics': 'ga:sessions,ga:users',
					'dimensions': 'ga:date',
					//'filters': 'ga:pagePathLevel1==/,ga:pagePathLevel2==/main/',
				},
				chart: {
					'container': 'chart-1-container',
					'type': 'LINE',
					'options': {
						'width': '100%',
					}
				}
			});
			dataChart1.execute();

			// Creates a new DataChart instance showing top 5 most popular demos/tools amongst returning users only. It will be rendered inside an element with the id "chart-3-container".
			var dataChart2 = new gapi.analytics.googleCharts.DataChart({
				query: {
					'ids': 'ga:<%= gaConfig.getOption("ga_20_view_id") %>', // <-- Replace with the ids value for your view.
					'start-date': '30daysAgo',
					'end-date': 'yesterday',
					'metrics': 'ga:pageviews',
					'dimensions': 'ga:pagePath',
					'sort': '-ga:pageviews',
					//'filters': 'ga:pagePathLevel1!=/',
					'filters': 'ga:pagePath!=${CONTEXT_PATH }/site/main/home;ga:pagePathLevel2==/<%= siteId %>/',//메인페이지제거, 서브사이트 구분
					'max-results': 10
				},
				chart: {
					'container': 'chart-2-container',
					'type': 'PIE',
					'options': {
						'width': '100%',
						'pieHole': 4/9,
					}
				}
			});
			dataChart2.execute();

		});
		</script>
		<div class="well well-small">
			<blockquote style="margin-bottom: 10px"><strong>구글 애널리틱스</strong></blockquote>
			<table class="table table-bordered table-hover table-striped table-condensed table-dashboard" style="margin-bottom: 0; background-color: #fff">
				<colgroup>
					<col style="width: 50%"/>
					<col style="width: 50%"/>
				</colgroup>
				<tbody>
					<tr>
						<th>30Days Sessions, User</th>
						<th>30Days Pageviews(메인페이지제외)</th>
					</tr>
					<tr>
						<td style="padding: 10px;text-align:center;"><div id="chart-1-container"><img src="${CONTEXT_PATH }/design/common/js/jquery-loadmask-0.4/images/loading.gif"/> Loading...</div></td>
						<td style="padding: 10px;text-align:center;"><div id="chart-2-container"><img src="${CONTEXT_PATH }/design/common/js/jquery-loadmask-0.4/images/loading.gif"/> Loading...</div></td>
					</tr>
				</tbody>
			</table>
		</div>
<%
} else {
	%>
	<div class="well well-small">
		<blockquote style="margin-bottom: 10px"><strong>Google Analytics Embed</strong></blockquote>
		<div class="alert alert-danger" style="margin-bottom: 0">
			OAuth Credential Json 또는 Google Analytics Report View ID 정보가 없습니다.
		</div>
	</div>
	<%
}  
%>
	</div>
</div>