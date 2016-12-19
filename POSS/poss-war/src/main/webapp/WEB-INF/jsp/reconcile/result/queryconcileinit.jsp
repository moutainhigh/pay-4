<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<html>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
			<div align="center"><font class="titletext">对账结果列表</font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript">

			$(document).ready(function(){applyReconcileQuery();}); 
			
			function applyReconcileQuery(pageNo,totalCount,pageSize) {
				var strDate1 = $("#startDate").val();
				var strDate2 = $("#endDate").val();

				if("" == strDate1 && "" == strDate2){
					alert("请您选择业务日期!");
					return false;
				}
				
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#queryResult").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/fo-rc-queryreconcile.do?method=queryResultList",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			
			function downloadExcel(){
				document.queryResult.action = 'fo_rc_downloadexcelcontroller.do?method=downloadResultList2Excel';
				document.queryResult.submit();
			}
		
		</script>
	</head>
	 <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
	
	 <form id="queryResult" name="queryResult" method="post" action="">
			<table class="border_all2" width="80%" border="0" cellspacing="0"
				cellpadding="1" align="center">	
			    <tr class="trForContent1">
			      <td class=border_top_right4 align="right" width="50%">选择出款银行：</td>
			      <td class="border_top_right4" width="50%">
			        	 <li:codetable fieldName="withDrawBankId" style="select" attachOption="true" codeTableId="fundOutBankTable" />
			      </td>
			    </tr>
			    <tr class="trForContent1">
			      <td align="right" class="border_top_right4">业务日期:</td>
			      <td class="border_top_right4">
			      	<input class="Wdate" type="text" id="startDate"  name="startDate" 
			      		value='<fmt:formatDate value="<%=new Date() %>" type="date"/>'  
			      		onClick="WdatePicker()">
			        	～
					<input class="Wdate" type="text" id="endDate" name="endDate" 
						value='<fmt:formatDate value="<%=new Date() %>" type="date"/>' 
						onClick="WdatePicker()">
			      </td>
			    </tr>
			  
			    <tr class="trForContent1">
			      <td align="center" class="border_top_right4" colspan="2">
			      <input type="button"  name="doQueryData" value="查  询" class="button2" onclick="applyReconcileQuery();">
			      </td>
			    </tr>
		   </table>
	 </form>
		  <div>
		    <div style="text-align:right;">
				完全匹配的系统自动入历史流水
				<input type="button" class="button2" onclick="downloadExcel();" value="下载EXCEL"/>
		    </div>
		    <div id="resultListDiv" ></div>
		  </div>
</html>
