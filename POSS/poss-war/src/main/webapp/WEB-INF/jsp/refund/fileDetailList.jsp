<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="mainfrom" id="resultForm">
 	<input type="hidden" name="batchNum"  />
 </form>

已生成的批次如下
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>充退流水号</th>
			<th>银行充值流水号</th>
			<th>充退渠道</th>
			<th>充值金额（元）</th>
			<th>充退金额（元</th>
			<th>充退申请时间</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody>
	<!--<c:forEach items="${page.result}" var="dDto" varStatus="status">
		<tr>
			<td ><c:out value="${status.count}" /></td>
			<td > ${dDto.refundDKy} </td>
			<td > ${dDto.rechargeBankSeq} </td>
			<td > ${dDto.rechargeBank} </td>
			<td > 
				<fmt:formatNumber value="${dDto.applyAmount}" pattern="#,##0.00"/>&nbsp;
			</td>
			<td > 
				<fmt:formatNumber value="${dDto.rechargeAmount}" pattern="#,##0.00"/>&nbsp;
			</td>
			<td > 
				<fmt:formatDate value="${dDto.rechargeTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td > &nbsp; </td>
		</tr>
	</c:forEach>
	--></tbody>
</table>
<li:pagination methodName="queryDetailList" pageBean="${page}" sytleName="black2"/>

<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr align="center" >
	    <td align="center" colspan="3">
	      <input type="button" onclick="downloadExcel('${webQueryRefundDTO.batchNum }');" class="button4" value="下载">
	      <input type="button" onclick="javascript:history.go(-1);" class="button2" value="返  回">
	    </td>
	 </tr>
</table>
      
<c:set var="listSize" value="${fn:length(page.result)}"/>
<script type="text/javascript">

function downloadExcel(batchNum) {
	document.mainfrom.action = "refund.file.do?method=downloadBatchFile";
	document.mainfrom.submit();
}
</script>
