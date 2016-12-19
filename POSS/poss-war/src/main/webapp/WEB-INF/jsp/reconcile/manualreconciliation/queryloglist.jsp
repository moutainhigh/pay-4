<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		//返回结果列表页内排序
		$(document).ready(function(){
			 $("#logTable").tablesorter({
				 headers: {
				 	7: {sorter: false}
				 }});      
		});
		//查看
		function processDetailed(id,applyId){
			location.href = "fo_rc_foreconcilelogcontroller.do?method=log&id=" + id +"&applyId="+applyId;
		}
	</script>
</head>

<body>
	<table id="logTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>银行名称</th>
				<th>出款业务</th> 
				<th>交易流水号</th> 
				<th>交易金额</th> 
				<th>交易日期</th> 
				<th>对账状态</th> 
				<th>调账状态</th> 
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="rc"> 
			<tr>   
				<td><li:codetable selectedValue="${rc.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable" /></td>     
				<td>
					<li:codetable fieldName="WITHDRAW_BUSI_TYPE" selectedValue="${rc.withdrawBusiType}"  style="desc" attachOption="true" codeTableId="fundOutBusinessTable" />
				</td>     
				<td>${rc.transactionNumber}</td>     
				<td><fmt:formatNumber value="${rc.tradeAmount*0.001}" pattern="#,##0.00"  /></td>     
				<td><fmt:formatDate value="${rc.tradeTime}" type="both"/></td> 
				<td><li:code2name itemList="${busiFlagList}" selected="${rc.busiFlag}"/></td>
				<td><li:code2name itemList="${reviseStatusList}" selected="${rc.reviseStatus}"/></td>
				<td><a href="javascript:processDetailed('${rc.resultId}','${rc.applyId }')">查看日志</a></td>  
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="logQuery" pageBean="${page}" sytleName="black2"/>
</body>

