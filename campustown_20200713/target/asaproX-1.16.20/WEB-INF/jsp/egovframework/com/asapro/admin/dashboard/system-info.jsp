<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.FileReader"%>
<%@page import="com.sun.management.OperatingSystemMXBean"%>
<%@page import="java.lang.management.ManagementFactory"%>
<%@page import="org.apache.commons.lang3.SystemUtils"%>
<%@page import="java.io.File"%>
<%@page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@page import="org.apache.commons.io.FileSystemUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${currentUser.memberRole.roleAdmin}"> 
<script>
jQuery(function($){
	//시스템 정보
	var isDetailShow = false;
	$('.show-sysinfo-detail').click(function(e){
		if( !isDetailShow ){
			$('.sysinfo-detail').fadeIn();
			$('.show-sysinfo-detail').html('간단히');
			isDetailShow = true;
		} else {
			$('.sysinfo-detail').fadeOut();
			$('.show-sysinfo-detail').html('자세히');
			isDetailShow = false;
		}
	});
});
</script>
<div class="row-fluid">
	<%
		String webRoot = AsaproUtils.getWebRoot(request);
		File directory = new File(webRoot);
		long totalSpace = directory.getTotalSpace();
		long freeSpace = directory.getFreeSpace();
		long usableSpace = directory.getUsableSpace();
		long usedSpace = totalSpace - freeSpace;
		double diskRatio = usedSpace / (double)totalSpace;
		String diskCssClass = "label label-success";
		if( diskRatio >= 0.5 && diskRatio < 0.75 ){ diskCssClass = "label label-warning"; }
		if( diskRatio >= 0.75 ){ diskCssClass = "label label-important"; }
		
		OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		long totalMemory = systemMXBean.getTotalPhysicalMemorySize();
		long freeMemory = systemMXBean.getFreePhysicalMemorySize();

		long cachedMemory = 0l;
		//캐시메모리 계산 -  (주의)아래 소스는 리눅스에서만 되는거
		if( SystemUtils.IS_OS_LINUX ){
			List<String> memInfoList =  IOUtils.readLines(new FileReader("/proc/meminfo"));
			for( String str : memInfoList ){
				if( str.contains("Cached") ){
					Matcher m = Pattern.compile("-?\\d+").matcher(str);
					while( m.find() ){
						cachedMemory = Long.parseLong(m.group()) * 1024;
						break;
					}
					break;
				}
			}
		}
		
		long usedMemory = totalMemory - freeMemory - cachedMemory;
		double memoryRatio = usedMemory / (double)totalMemory;
	%>
	<div class="span12">
		<div class="well well-small">
			<blockquote style="margin-bottom: 10px"><strong>시스템정보</strong><button type="button" class="btn btn-mini btn-default pull-right show-sysinfo-detail">자세히</button></blockquote>
			<table class="table table-bordered table-hover table-striped table-condensed table-dashboard" style="margin-bottom: 0; background-color: #fff">
				<colgroup>
					<col/>
					<col />
					<col />
					<col />
					<col />
					<col />
					<col />
					<col />
				</colgroup>
				<tbody>
					<tr>
						<th>CPU Load</th>
						<td><fmt:formatNumber value="<%= systemMXBean.getSystemCpuLoad() %>" minFractionDigits="2" pattern="##.##%" /></td>
						<th>Memory</th>
						<td>
							<%= AsaproUtils.getFileSizeString(usedMemory, true, true) %>/<%= AsaproUtils.getFileSizeString(totalMemory, true, true) %>
							(<fmt:formatNumber value="<%= memoryRatio %>" minFractionDigits="2" pattern="##.##%" /> 사용중)
						</td>
						<th>Disk Usage</th>
						<td>
							<%= AsaproUtils.getFileSizeString(usedSpace, true, true) %>/<%= AsaproUtils.getFileSizeString(totalSpace, true, true) %>
							<span class="<%= diskCssClass %>"><fmt:formatNumber value="<%= diskRatio %>" minFractionDigits="2" pattern="##.##%" /> 사용중</span>
						</td>
					</tr>
					<tr class="sysinfo-detail">
						<th>Path</th>
						<td colspan="5"><code><%= webRoot %></code></td>
					</tr>
					<tr class="sysinfo-detail">
						<th>OS</th>
						<td colspan="5"><%= SystemUtils.OS_NAME %><%= SystemUtils.OS_VERSION %></td>
					</tr>
					<tr class="sysinfo-detail">
						<th>Server</th>
						<td colspan="5"><%= getServletContext().getServerInfo() %></td>
					</tr>
					<tr class="sysinfo-detail">
						<th>CPU</th>
						<td colspan="5"><%= systemMXBean.getArch() + " / " + systemMXBean.getAvailableProcessors() + "Core(s)" %></td>
					</tr>
					<tr class="sysinfo-detail">
						<th>JAVA_HOME</th>
						<td colspan="5"><code><%= SystemUtils.JAVA_HOME %></code></td>
					</tr>
					<tr class="sysinfo-detail">
						<th>JAVA_VM_NAME</th>
						<td colspan="5"><%= SystemUtils.JAVA_VM_NAME %></td>
					</tr>
					<tr class="sysinfo-detail">
						<th>JDK</th>
						<td colspan="5"><%= SystemUtils.JAVA_VERSION %>(<%= SystemUtils.JAVA_VENDOR %>)</td>
					</tr>
					<tr class="sysinfo-detail">
						<th>JRE</th>
						<td colspan="5"><%= SystemUtils.JAVA_RUNTIME_VERSION %>(<%= SystemUtils.JAVA_RUNTIME_NAME %>)</td>
					</tr>
					<tr class="sysinfo-detail">
						<th>Usable Space</th>
						<td><%= AsaproUtils.getFileSizeString(usableSpace, true, true) %></td>
						<th>Free Space</th>
						<td><%= AsaproUtils.getFileSizeString(freeSpace, true, true) %></td>
						<th>Total Space</th>
						<td><%= AsaproUtils.getFileSizeString(totalSpace, true, true) %></td>
					</tr>
					<tr class="sysinfo-detail">
						<th>Java Melody</th>
						<td colspan="5">
							<button type="button" class="btn btn-mini btn-default" onclick="window.open('/monitoring', 'javamelody-pop');">Open</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</c:if>