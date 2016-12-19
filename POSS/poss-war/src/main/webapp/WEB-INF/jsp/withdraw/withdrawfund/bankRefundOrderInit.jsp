<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<title>待审核退款订单</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			function query(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#withdrawbackForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/fundout-withdraw-withdrawrefund.do?method=queryRefundOrder",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}

			$(document).ready(function(){
				query();

				$("#btn").click(function(){
					if(checkInfo()){
						query();
					}
				});
			});

			function checkInfo(){
				var reg=/^\d+$/;
				var bankRefundOrderId=$("#bankRefundOrderId").val();
				var sequenceId=$("#sequenceId").val();
				if($.trim(sequenceId).length>0 && !reg.test(sequenceId)){
					alert("交易流水必须为数字");
					return false;
				}
				if($.trim(bankRefundOrderId).length>0 && !reg.test(bankRefundOrderId)){
					alert("退款流水必须为数字");
					return false;
				}
				return true;
			}
		</script>
	</head>
	<h2 class="panel_title">待审核退款订单</h2>
		<form id="withdrawbackForm" method="post">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4 textRight"> 交易流水号：</td>
			   	<td class="border_top_right4 textLeft"><input type="text" id ="sequenceId"   name="sequenceId" style="width: 150px;" /></td>
	   			<td class="border_top_right4 textRight"> 交易时间：</td>
			   	<td class="border_top_right4 textLeft"><input type="text" id ="createTime"   name="createTime"  style="width: 150px;" 
			                          	value='<fmt:formatDate value="${withdrawOrder.createTime}" type="date"/>' 
			   	                            onClick="WdatePicker()"/></td>
		   </tr>
		   <tr class="trForContent1">
		   		<td class="border_top_right4 textRight"> 银行名称：</td>
			   	<td class="border_top_right4 textLeft">
			   		<select name="bankKy">
			   		    <option value="-1">全部</option>
			   			<c:forEach var="bank" items="${bankList}">
			   				<option value="${bank.bankId}">${bank.bankName}</option>
			   			</c:forEach>
			   		</select>
                 </td>
	   			<td class="border_top_right4 textRight"> 银行账户：</td>
			   	<td class="border_top_right4 textLeft"><input type="text" id ="bankAcct"   name="bankAcct" style="width: 150px;" /></td>	
		   	</tr>
		   	<tr class="trForContent1">
		   		<td class="border_top_right4 textRight"> 银行流水号：</td>
			   	<td class="border_top_right4 textLeft">
			   		<input type="text" name="orderSeqId">
                </td>
                <td class="border_top_right4 textRight"> 退款流水号：</td>
			   	<td class="border_top_right4 textLeft">
			   		<input type="text" name="bankRefundOrderId" id="bankRefundOrderId">
                </td>
		   	</tr>
		   	<tr class="trForContent1">
		   	<td class="border_top_right4 textRight"  >业务类型：</td>
				<td class="border_top_right4 textLeft">
					<select name="busiType" style="width: 150px;">
						<option value="">全部</option>
						<option value='0'>提现</option>
						<!--<option value='1'>批量出款</option>
						<option value='2'>信用卡还款</option>
						--><option value='3'>付款到银行</option>
						<option value='4'>批量付款到银行</option>
					</select>
				</td>
				<td class="border_top_right4">&nbsp;</td>
				<td class="border_top_right4">&nbsp;</td>
		   	</tr>
		   	<tr class="trForContent1">
		   		<td class="border_top_right4" colspan="4" align="center">
		   			<input type="button" id="btn" value="查询"/>
		   		</td>
		   	</tr>
		</table>
		</form>
		
        <div id="resultListDiv" class="listFence"></div>
		<div id="deleteRoleDiv" title="删除用户信息"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>