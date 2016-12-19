<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<html>
	<head>
		<title>手工调账审核</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

			//条件查询分页
			function auditQuery(pageNo,totalCount,pageSize) {
				var strDate1 = $("#STARTTIME").val();
				var strDate2 = $("#ENDTIME").val();

				if("" == strDate1 && "" == strDate2){
					alert("请您选择业务日期!");
					return false;
				}
				
				if(!validDate(strDate1,strDate2)){
					alert("开始日期不能大于结束日期!");
					return false;
				}
				
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#auditSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/fo_rc_foreconcileauditcontroller.do?method=queryauditlist",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}

			//页面加载时查询分页
			//$(document).ready(function(){applyQuery();});

			//验证日期
			function validDate(strDate1,strDate2){
				if(null != strDate1 && null != strDate2 &&
						0 != strDate1.length && 0 != strDate2.length){
					var date1 = new Date(strDate1.replace("-","/"));
					var date2 = new Date(strDate2.replace("-","/"));
					if(date1 > date2){
						return false;
					}
				}
				return true;
			}
		</script>
	</head>

	<body>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">手工调账审核</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		<br>
		
		<form id="auditSearchForm">
		<table  class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
		      	<td align="right" class="border_top_right4">业务日期:</td>
		      	<td class="border_top_right4" colspan="3">
			      	<input class="Wdate" type="text" id="STARTTIME"  name="STARTTIME" 
			      		value='<fmt:formatDate value="<%=new Date() %>" type="date"/>'  onClick="WdatePicker({maxDate:'#F{$dp.$D(\'STARTTIME\',{d:0});}'})">
			        	～
					<input class="Wdate" type="text" id="ENDTIME" name="ENDTIME" 
						value='<fmt:formatDate value="<%=new Date() %>" type="date"/>' onClick="WdatePicker({minDate:'#F{$dp.$D(\'STARTTIME\',{d:0});}'})">
		      	</td>
		    </tr>
		    <tr class="trForContent1">
		   	  	<td class=border_top_right4 align="right">选择出款银行：</td>
		      	<td class="border_top_right4" >
		      		<li:codetable fieldName="WITHDRAW_BANK_ID" style="select" attachOption="true" codeTableId="fundOutBankTable" />
		      	</td>
		      	<td class=border_top_right4 align="right" >交易流水号：</td>
		      	<td class="border_top_right4" >
		        	<input type="text" name="BANK_TRADE_SEQ" style="width: 120px;" onfocus="" />
		      	</td>
		    </tr>
		    <tr class="trForContent1">
		      	<td class=border_top_right4 align="right" >选择出款业务：</td>
		      	<td class="border_top_right4">
		      		<li:codetable fieldName="WITHDRAW_BUSI_TYPE"  style="select" attachOption="true" codeTableId="fundOutBusinessTable" />
		      	</td>
		      	<td class=border_top_right4 align="right" >对账状态：</td>
		      	<td class="border_top_right4" >
		      		<li:select name="BUSI_FLAG" itemList="${busiFlagList}" />
		      	</td>
		    </tr>
		    <tr class="trForContent1">
		      <td align="center" class="border_top_right4" colspan="4">
		      <input type="button" onclick="auditQuery();" name="submitBtn" value="查  询" class="button2">
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
</html>
		
