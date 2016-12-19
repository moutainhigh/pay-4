<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	1: {sorter: true}
				 	
				 }});      
		});


		function typeQuery() {
			
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#typeSearchForm").serialize();
			//$("#id").val();
			//var pars ="id=" + form1.id.value + "&status=" + form1.status.value+"&name="+form1.name.value;
			$.ajax({
				type: "POST",
				url: "${ctx}/rulemanage.do?method=searchRules",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}

		function showAll() {
			
			$('#infoLoadingDiv').dialog('open');
			var pars ="";// $("#typeSearchForm").serialize();
			//$("#id").val();
			//var pars ="id=" + form1.id.value + "&status=" + form1.status.value+"&name="+form1.name.value;
			$.ajax({
				type: "POST",
				url: "${ctx}/rulemanage.do?method=searchRules",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
		
	</script>
</head>

<body onload="showAll()">
	   <form id="typeSearchForm">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">目的银行：</td>
				<td class="textLeft">	
					<select name="toBankCode">	
						<option value="" selected>请选择</option>
						<c:forEach items="${banklist}" var="bank">
							<option value="${bank.bankCode}">${bank.bankName}</option>
						</c:forEach>
					</select>
				</td>			
				<td class="textRight">出款银行：</td>
				<td class="textLeft">	
					<select name="withdrawBankId">
						<option value="" selected>请选择</option>
						<c:forEach items="${banklist}" var="bank">
							<option value="${bank.bankCode}">${bank.bankName}</option>
						</c:forEach>
					</select>
				</td>
				<td class="textRight">优先级：</td>
				<td class="textLeft">
				<select name="priority">
					<option value="" selected>请选择</option>
					<option value="1" >1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
				</td>
				<td class="textRight">交易类型：</td>
				<td class="textLeft">
						<select name="withdrawType">
						<option value="" selected>请选择</option>
						<option value="1" >对公</option>
						<option value="2">对私</option>
					</select>
			    </td>	
			    <td class="textLeft">
					    出款业务:
				<select name="bankAcctType">
					<option value="" selected>请选择</option>
					<c:forEach items="${businesslist}" var="business">
						<option value="${business.sequenceId}">${business.businessName}</option>
						</c:forEach>
					</select>
			    </td>			
				<td class="textRight">状态：</td>
				<td class="textLeft">
					<select name="status">
						<option value="0">0</option>
						<option value="1" selected>1</option>
					</select>
				</td>
				<td class="textRight">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="typeQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</form>
		
		<div id="resultListDiv" class="listFence"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
</body>

