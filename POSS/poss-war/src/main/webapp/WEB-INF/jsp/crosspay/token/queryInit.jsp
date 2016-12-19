<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">Token支付查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">Token支付查询</h2>

<form action="tokenpay.do?method=list" method="post" id="tokenQuerySubmit" name="mainfrom">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">会员号：</td>
      	<td class="border_top_right4">
      	      	<input type="text" onkeyup="checkNum(this);" id="partnerId" name="partnerId"/>
      	</td>
      	<!-- <td class=border_top_right4 align="right" >Token：</td>
      	<td class="border_top_right4" >
        	<input type="text" onkeyup="checkNum(this);" id="tokenID" name="tokenID" />
      	</td> -->
      	<td class=border_top_right4 align="right" >用户ID：</td>
      	<td class="border_top_right4" >
        	<input type="text" id="registerUserId" name="registerUserId" />
      	</td>
      	<td class=border_top_right4 align="right" >使用状态：</td>
      	<td class="border_top_right4" >
        	<select name="status" id="status">
        		<option value="">——请选择——</option>
        		<option value="0">已解绑</option>
        		<option value="1">使用中</option>
        	</select>
      	</td>
      	<tr class="trForContent1">
      	<td class="border_top_right4"  align="right">绑卡日期：</td>
      	<td class="border_top_right4" colspan="3">
			<input class="Wdate" type="text" id= "beginTime" name="beginTime"  onClick="WdatePicker()">~
			<input class="Wdate" type="text" id= "endTime" name="endTime"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		</td>
		<td class="border_top_right4"  align="right">卡号：</td>
      	<td class="border_top_right4">
      		<input type="text" id="cardNo" name="cardNo" />
		</td>
	</tr>
	<tr class="trForContent1">
      	<td align="center" class="border_top_right4" colspan="6">
		      <input type="hidden" name="method" value="manualTransSub">
		      <input type="button"  name="submitBtn" onclick="submitTokenQuery();" value="查询" class="button2">&nbsp;&nbsp;
      	 </td>
      </tr>
    </tr>
  </table>
 </form>
 

 <div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<script type="text/javascript">
function submitTokenQuery(){
	/*
	var partnerId=$("#partnerId").val();
	if(partnerId==""){
		alert("请输入会员号！！！");
		return;
	}
	var cardNum=$("#tokenID").val();
	if(cardNum==""){
		alert("请输入卡数量！！！");
		return ;
	}
	var cardOrg=$("#registerUserID").val();
	if(cardOrg==""){
		alert("请输入用户ID！！！");
		return ;
	}
	*/
	$("#tokenQuerySubmit").submit();
}
function tokenCardQuery(){
	window.location.href = 	"${ctx}/tokenpay.do?method=tokenCardQuery";
}
function unbindCard(){
	window.location.href = 	"${ctx}/tokenpay.do?method=unbindCard";
}
function checkNum(obj) {
	//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
$(document).ready(function() {
	query();
});
function query(pageNo,totalCount,pageSize) {
	var pars = $("#mainfrom").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	//alert(pars);
	$.ajax({
		type: "POST",
		url: "${ctx}/tokenpay.do?method=queryManualTran",
		data: pars,
		success: function(result) {
			$('#resultListDiv').html(result);
		}
	});
}
</script>