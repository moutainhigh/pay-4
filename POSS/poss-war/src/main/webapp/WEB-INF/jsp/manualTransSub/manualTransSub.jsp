<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">人工交易提交</h2>

<form action="manualTransSub.do" method="post" id="manualTransSub" name="mainfrom">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">会员号：</td>
      	<td class="border_top_right4">
      	      	<input type="text" onkeyup="checkNum(this);" id="partnerId" name="partnerId"/>
      	</td>
      	<td class=border_top_right4 align="right" >卡数量：</td>
      	<td class="border_top_right4" >
        	<input type="text" onkeyup="checkNum(this);" id="cardNum" name="cardNum" />
      	</td>
      	<td class=border_top_right4 align="right" >交易时长：</td>
      	<td class="border_top_right4" >
        	<input type="text" id="estimatedTime" name="estimatedTime" />
      	</td>
      	<td class=border_top_right4 align="right" >卡组织：</td>
      	<td class="border_top_right4" >
         	<select style="width: 132px; height: 19px;"name="cardOrg" id="cardOrg">
					<option value="">--请选择--</option>
					<option value="Visa">Visa</option>
					<option value="MasterCard">MasterCard</option>
					<option value="JCB">JCB</option>
			</select> 
      	</td>
      	 <td align="center" class="border_top_right4" colspan="4">
      <input type="hidden" name="method" value="manualTransSub">
      <input type="button"  name="submitBtn" onclick="submitTran();"    value="提  交" class="button2">&nbsp;&nbsp;
      <input type="button"  name="submitBtn" onclick="cardInfQuery();"  value="卡片信息查询" class="button2">&nbsp;&nbsp;
      <input type="button"  name="submitBtn"  onclick="basicConfig();" value="基本配置管理" class="button2">
      </td>
    </tr>
  </table>
 </form>
<br><hr><br> 
 <form action="" method="post" id="mainfrom" name="mainfrom">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
		<td class=border_top_right4 align="right" >会员号：</td>
      		<td class="border_top_right4" >
        	<input type="text" onkeyup="checkNum(this);" name="partnerId" />
      	</td>
      	<td align="right" class="border_top_right4">提交日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startTime"  name="startTime" value='<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>'  onClick="WdatePicker({minDate:'%y-%M-{%d-720}',maxDate:'#F{$dp.$D(\'endTime\')}'})">
	        	～
			<input class="Wdate" type="text" id="endTime" name="endTime"  value='<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>' onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})">
      	</td>
      	<td class=border_top_right4 align="right" >状态：</td>
      		<td class="border_top_right4" >
        	<select  name="status" id="interestType" style="width:132px; height:20px;">
	      			<option value="">--请选择--</option>
	      			<option value="1">进行中</option>
	      			<option value="2">已完成</option>
	      		</select>
      	</td>
      	<td align="center" class="border_top_right4" colspan="4">
      		<input type="button" onclick="start();" name="submitBtn" value="启   动" class="button2">
      		<input type="button" onclick="query();" name="submitBtn" value="查  询" class="button2">
      	</td>
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
function submitTran(){
	var partnerId=$("#partnerId").val();
	if(partnerId==""){
		alert("请输入会员号！！");
		return;
	}
	var cardNum=$("#cardNum").val();
	if(cardNum==""){
		alert("请输入卡数量！！！");
		return ;
	}
	var cardOrg=$("#cardOrg").val();
	if(cardOrg==""){
		alert("请输入卡组织！！");
		return ;
	}
	$("#manualTransSub").submit();
}
function cardInfQuery(){
	window.location.href = 	"${ctx}/manualTransSub.do?method=cardInfQuery";
}
function basicConfig(){
	window.location.href = 	"${ctx}/manualTransSub.do?method=basicConfig";
}
function start(){
	window.location.href = 	"${ctx}/manualTransSub.do?method=start";
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
		url: "${ctx}/manualTransSub.do?method=queryManualTran",
		data: pars,
		success: function(result) {
			$('#resultListDiv').html(result);
		}
	});
}
</script>