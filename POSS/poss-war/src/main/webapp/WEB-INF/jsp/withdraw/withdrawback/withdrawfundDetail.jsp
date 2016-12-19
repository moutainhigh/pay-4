<%@ page contentType="text/html;charset=UTF-8" language="java"  import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>


<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">提现退款</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="fundout.withdraw.order.do?method=submit" method="post" >
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr>
		<td class="textRight"> 交易流水号：</td>
	   	<td class="textLeft">${order.sequenceId}</td>
		<td class="textRight">退款金额 </td>
	   	<td class="textLeft">${order.amount}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 开户行名称：</td>
	   	<td class="textLeft">${order.bankBranch}</td>
		<td class="textRight"> 银行账户：</td>
	   	<td class="textLeft">${order.bankAcct}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 汇款金额：</td>
	   	<td class="textLeft">${order.amount}</td>
		<td class="textRight"> 收款人：</td>
	   	<td class="textLeft">${order.accountName}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 省份：</td>
	   	<td class="textLeft">${order.bankProvince}</td>
		<td class="textRight"> 城市：</td>
	   	<td class="textLeft">${order.bankCity}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 交易时间：</td>
	   	<td class="textLeft">${order.createTime}</td>
		<td class="textRight">状态</td>
	   	<td class="textLeft">
		   	<c:if  test="${order.status=1}">处理中</c:if>
		   	<c:if  test="${order.status=2}">处理成功</c:if>
		   	<c:if  test="${order.status=3}">处理失败</c:if>
	   	</td>
  	</tr> 
  </table>
 </form>

<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">
function processEdit(){
	location.href = "riskconctrol.msLimitation.do?method=modifySave";
}

</script>