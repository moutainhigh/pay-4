<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 
	
<table id="reviseAudit" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
	<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="10" align="right">
			<input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" />
		</td>
	</tr>
	</thead>
</table>
<div style="width:100%;height:100%;overflow-x:auto;overflow-y:auto;">
<table border='0' cellspacing='0' cellpadding='0' style="width:${fn:length(query_business_pay_report_s_list)*500}px;">
	<tr>
		<td rowspan="2" class="winbd02" style="width:80px"><b>时间\银行</b></td>
		<c:forEach items="${query_business_pay_report_s_list}" var="tit" varStatus="status">
			<td colspan="6" class="winbd02" <c:if test="${fn:length(query_business_pay_report_s_list)==status.count}">style='border-right-width:1px;'</c:if>><b>${tit}</b></td>
		</c:forEach>
	</tr>
	<tr>
	 <c:forEach var="i" begin="1" end="${fn:length(query_business_pay_report_s_list)}" step="1">  
		<td class="winbd02" style="border-bottom-width:1px;"><b>总笔数</b></td>
		<td class="winbd02" style="border-bottom-width:1px;"><b>总金额</b></td>
		<td class="winbd02" style="border-bottom-width:1px;"><b>完成笔数</b></td>
		<td class="winbd02" style="border-bottom-width:1px;"><b>完成金额</b></td>
		<td class="winbd02" style="border-bottom-width:1px;"><b>退款笔数</b></td>
		<td class="winbd02" style="<c:if test='${i==fn:length(query_business_pay_report_s_list)}'>border-right-width:1px;</c:if>border-bottom-width:1px;"><b>退款金额</b></td>
	</c:forEach>
	</tr>
	
	<c:set var="lenTmp" value="${fn:length(query_business_pay_report_list)}"/>
	<c:forEach items="${query_business_pay_report_list}" var="reportInfo" varStatus="status">
	<tr>
		<td class="winbd02" style="border-right-width:1px;<c:if test="${lenTmp == status.count}">border-bottom-width:1px;</c:if>">
			&nbsp;<b>${query_business_pay_report_date_list[status.count-1]}</b>
		</td>

		<c:forEach items="${reportInfo}" var="ri" varStatus="status1">
			<c:if test="${lenTmp == status.count}">
				<td class="winbd04" <c:if test='${status1.count==fn:length(reportInfo)}'>style='border-right-width:1px;'</c:if>>&nbsp;${ri}</td>	
			</c:if>
			<c:if test="${ lenTmp != status.count}">
				<td class="winbd03" <c:if test='${status1.count==fn:length(reportInfo)}'>style='border-right-width:1px;'</c:if>>&nbsp;${ri}</td>	
			</c:if>		
		</c:forEach>
	</tr>
	</c:forEach>
</table>
</div>

<c:set var="listSize" value="${fn:length(query_business_pay_report_list)}"/>
<script type="text/javascript">
	function downloadExcel() {
			var sellers = document.getElementsByName("sellers") ;
 			var isc = false ;
 			for ( var cnt=0 ; cnt<sellers.length ; cnt++ ) {
				if ( sellers[cnt].checked == true ){
					isc = true ;
					break ;
				}
			}
 			if (isc == false  ) {
				alert ( "请选择平台商!" ) ;
				return ;
 			}
		if("${listSize}" > 1){
			document.mainfrom.action = "fi.businessPayReport.do?method=downLoadReport&rnd=" + Math.random();
			document.mainfrom.submit();
		}else{
			alert("没有任何数据,无法提供下载");
		}
	}
</script>
