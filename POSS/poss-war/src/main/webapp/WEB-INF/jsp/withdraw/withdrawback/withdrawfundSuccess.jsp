<%@ page contentType="text/html;charset=UTF-8" language="java"  import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">退款成功</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="fundout.withdraw.order.do?method=success" method="post" >
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr>
		<td class="textRight">交易流水号：</td>
	   	<td class="textLeft">${sequenceId}</td>
		<td class="textRight">退款金额 </td>
	   	<td class="textLeft">${amount}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 开户行名称：</td>
	   	<td class="textLeft">${bankBranch}</td>
		<td class="textRight"> 银行账户：</td>
	   	<td class="textLeft">${bankAcct}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 汇款金额：</td>
	   	<td class="textLeft">${amount}</td>
		<td class="textRight"> 收款人：</td>
	   	<td class="textLeft">${accountName}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 省份：</td>
	   	<td class="textLeft">${bankProvince}</td>
		<td class="textRight"> 城市：</td>
	   	<td class="textLeft">${bankCity}</td>
  	</tr>
  	<tr>
		<td class="textRight"> 交易时间：</td>
	   	<td class="textLeft">${createTime}</td>
		<td class="textRight"> 状态：</td>
	   	<td class="textLeft">
	   	   	<c:if test="${status=1}">处理中</c:if>
	   	    <c:if test="${status=2}">处理成功</c:if>
   	     	<c:if test="${status=3}">处理失败</c:if>
	   	</td>
  	</tr>
  	<tr>
		<td align="center"><a href="/">返回</a></td> 	
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