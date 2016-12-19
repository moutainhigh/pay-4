<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
		query();
});
	function query(pageNo, totalCount){
		var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
		+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./feeConfigKF.do?method=list",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	function deleteConfig(feeConfigNo){
		if (!confirm("确认删除？")) {
			return;
		 }
		 window.location.href="${ctx}/feeConfigKF.do?method=delete&feeConfigNo="+feeConfigNo
	}

	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function decimals(obj){
		var markup=obj.value;//获取markup
		if(markup && markup.indexOf(".")==-1){
			var index;
			var zeroCount=0; 
			for(var i=0;i<markup.length;i++){
				if(markup.charAt(i)!="0"){
					index=i;
					break;
				}else{
					zeroCount++;	
				}
			}
			if(zeroCount==markup.length){
				$(obj).attr("value","");
			}else if(index){
				$(obj).attr("value",markup.substr(index));
			}
		}else if(markup && markup.indexOf(".")>-1){
			var left=markup.split(".")[0];
			var right=markup.split(".")[1];
			var zeroCount=0;  
			for(var i=0;i<left.length;i++){
				if(left.charAt(i)=="0"){
					zeroCount++;
				}else{
					break;
				}
			}
			if(zeroCount==left.length){
				left=left.substr(zeroCount-1);
				markup=left+"."+right;
				$(obj).attr("value",left+"."+right);
			}else if(zeroCount>0){
				left=left.substr(zeroCount);
				markup=left+"."+right;
				$(obj).attr("value",left+"."+right);
			}
			if(right.length>2){
				markup=markup.substring(0,markup.indexOf(".")+3);
				$(obj).attr("value",markup);
			}
		}
	}
	
	
		function updateConfig(partnerId,minimumValue,fixedFee,percentageFee,capValue,smallBaselin,smallServiceFee,feeConfigNo){
			$("#memberCode").attr("value",partnerId);
			$("#memberCode").attr("readonly","readonly");
		   $("#minimumValue").attr("value",minimumValue);
		   $("#id").attr("value",feeConfigNo);
		   $("#fixedFee").attr("value",fixedFee);
		   $("#percentageFee").attr("value",percentageFee);	
		   $("#capValue").attr("value",capValue);
		   $("#smallBaselin").attr("value",smallBaselin);
		   $("#smallServiceFee").attr("value",smallServiceFee);
			$('#feeConfDiv').dialog({ 
					position:["center","center"],
					width:574,
					height:312,
					modal:true, 	
					title:'购汇手续费配置修改', 
					overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
				});
			$("#type").attr("value","update");
	}
	
	function add(){
		removeAttr();
		$("#type").attr("value","add");
		$('#feeConfDiv').dialog({ 
			position:["center","center"],
			width:574,
			height:312,
			modal:true, 	
			title:'跨境手续费新增', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		});
	}
	function back(){
		$('#feeConfDiv').dialog("close");
	}
	
	function removeAttr(){
		$("#memberCode").removeAttr("value");
		$("#minimumValue").removeAttr("value");
		$("#fixedFee").removeAttr("value");
		$("#percentageFee").removeAttr("value");		
		$("#capValue").removeAttr("value");
		$("#memberCode").removeAttr("readonly");
		$("#smallServiceFee").removeAttr("value");
		$("#smallBaselin").removeAttr("value");
	}
	
	function validateConfig(){
		var memberCode =$("#memberCode").val();
		var	minimum=$("#minimumValue").val();		
		var	fixedFee=$("#fixedFee")	.val();	
		var percentageFee=$("#percentageFee").val();		
		var capValue=$("#capValue").val();		
		var smallServiceFee=$("#smallServiceFee").val();		
		var smallBaselin=$("#smallBaselin").val();		
		if(memberCode == ''){
			alert("请输入会员号！！！");
			return;
		}
		if(minimum == ''){
			alert("请输入保底值！！！");
			return;
		}
		if(fixedFee == ''){
			alert("请输入固定手续费！！！");
			return;
		}
		if(percentageFee == ''){
			alert("请输入百分比手续费！！！");
			return;
		}
		if(capValue == ''){
			alert("请输入 封顶值！！！");
			return;
		}
		if(smallServiceFee == ''){
			alert("请输入小额服务费！！！");
			return;
		}
		if(smallBaselin == ''){
			alert("请输入小额服务费基线！！！");
			return;
		}
		var type=$("#type").val();
		if(type=="add"){
			$("#method").attr("value","add");
			$("#feeConfDivForm").attr("action","${ctx}/feeConfigKF.do");
		}else if(type == "update"){
			$("#method").attr("value","update");
			$("#feeConfDivForm").attr("action","${ctx}/feeConfigKF.do");
		}
		$("#feeConfDivForm").submit()	;
	}
</script>
<h2 class="panel_title">跨境手续费设置</h2>
<h3><font color="red">${error}</font></h3>
<form  method="post" name="mainfrom" id="mainfrom" >
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      	<td align="center" class="border_top_right4">会员号： <input
				type="text" onkeyup="checkNum(this);" name="partnerId">
			</td>
			<td class="border_top_right4" colspan="2">创建时间：
		      	<input class="Wdate" type="text" id="beginCreateDate" name="beginCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
		      		至
		      	<input class="Wdate" type="text" id="endCreateDate" name="endCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	      		</td> 
				<td class="border_top_right4" colspan="2">更新时间：
		      	<input class="Wdate" type="text" id="beginUpdateDate" name="beginUpdateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
		 	     	至
		      	<input class="Wdate" type="text" id="endUpdateDate" name="endUpdateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
      		</td> 
	     </tr>
		   <tr class="trForContent1">
				<td class=border_top_right4 colspan="2" align="center">
						<input  type="button" class="button2" value="查询" id="btn" onclick="query();"/>
				</td>
				<td class=border_top_right4 colspan="2" align="center">
						<input  type="button" class="button2" value="新增" id="btn" onclick="add();"/>
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

<div id="feeConfDiv" style="display: none" align="center" >
	<form id="feeConfDivForm" name="feeConfDivForm"  method="post">
	<input type="hidden" name="feeConfigNo"  id="id">
	<input type="hidden" name="type" id="type" value="">
	<input type="hidden" name="method" id="method" value="">

<table border="0" cellspacing="0" cellpadding="0" align="center" width="95%" class="border_all2">
	<tr class="trForContent1">
				<td colspan="1" align="right"  class="border_top_right4">	
					会员号：
				</td>	
			<td colspan="2" align="left"  class="border_top_right4">
				 <input name="partnerId" id="memberCode"  onkeyup="checkNum(this);" >
			</td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
		</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right"  class="border_top_right4">	
				保底值：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
				 <input name="minimumValue" id="minimumValue" onkeyup="checkNum(this);" onblur="decimals(this)">CNY
			</td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
		</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
				 固定手续费：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
				<input name="fixedFee" id="fixedFee"  onkeyup="checkNum(this);" onblur="decimals(this)">CNY
			</td>
	</tr>
		<tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
		</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
			百分比手续费：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
			 <input name="percentageFee" id="percentageFee" onkeyup="checkNum(this);" onblur="decimals(this)">%
			</td>
	</tr><tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
		</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
			 封顶值：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
			<input name="capValue" id="capValue" onkeyup="checkNum(this);" onblur="decimals(this)">CNY
			</td>
	</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
			 小额服务费基线：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
			<input name="smallBaselin" id="smallBaselin" onkeyup="checkNum(this);" onblur="decimals(this)">CNY
			</td>
	</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
			 小额服务费：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
			<input name="smallServiceFee" id="smallServiceFee"   onkeyup="checkNum(this);" onblur="decimals(this)" > CNY
			</td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="center" class="border_top_right4">
			<input type="button" class="button2"  value="确定" onclick="validateConfig();"> &nbsp;&nbsp;&nbsp;
			<input type="button" class="button2"  value="取消" onclick="back();">
		</td>		
	</tr>
</table>
</form>
</div>