<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<h2 class="panel_title">拒付显示原因映射管理</h2>
<c:if test="${not empty info}">
	<p>		${info}</p>
</c:if>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td class="border_top_right4" align="right" >渠道：</td>
			<td class="border_top_right4" align="left" >
			<select name="orgCode" id="orgCode" onchange="findCode();">
	      		<option value="">请选择</option>
	      		<option value="10076001">中银卡司</option>
	      		<option value="10079001">中银MOTO</option>
	      		<option value="10080001">中银MIGS</option>
	      		<option value="10003001">中国银行</option>
	      		<option value="10002001">农业银行</option>
	      		<option value="10075001">CREDORAX</option>
	      		<option value="10077001">Adyen</option>
	      		<option value="10077002">Belto</option>
	      		<option value="10077003">Cashu</option>
	      		<option value="10078001">农行CTV</option>
				<option value="10081016">前海万融</option>
	      	</select>	
			<td align="right" class="border_top_right4"  >拒付原因：</td>
	      <td class="border_top_right4">
		      <select name="reasonCode" id="reasonCode" >
		     	<option value="">全部</option>
		     	
		      </select>
	      </td>
	      <td class="border_top_right4" align="right" >显示原因：</td>
			<td class="border_top_right4" align="left" >
				<select name="visableCode" id="visableCode">
			   		<option value="">全部</option>
		      		<option value="1">1——需要交易凭证</option>
		      		<option value="2">2——未收到货物或服务</option>
		      		<option value="3">3——未授权交易</option>
		      		<option value="4">4——重复处理</option>
		      		<option value="5">5——欺诈伪冒</option>
		      		<option value="6">6——货不对版</option>
		      		<option value="7">7——不承认</option>
		      		<option value="8">8——要求个人纪录</option>
		      		<option value="9">9——商品已退回未退款</option>
		      		<option value="10">10——未收到退款</option>
		      		<option value="11">11——金额不符</option>
		      		<option value="12">12——未提供单据</option>
		      		<option value="13">13——重复扣款</option>
			      </select>
	    </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit" value="添  加" class="button2" onclick="add();">
	      </td>
	    </tr>
   </table>
</form>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
 <div id="addBouncedDiv" style="display: none">
	<br><br><br>
	<form action="${ctx}/bouncedReasonMapping.do" method="post" id="addBouncedForm">
		<input type="hidden" name="method" value="addBouncedReasonMapping">
		<table class="border_all2" width="80%" border="0" cellspacing="0"
			cellpadding="1" align="center" id="addBouncedTable">
		<tr class="trForContent1">
				 <td class="border_top_right4" align="right" >序号：</td>
					<td class="border_top_right4" align="left" >
					<div id="serialNumberDev"></div></td>
		</tr>
		<tr class="trForContent1">
				 <td class="border_top_right4" align="right" >渠道：</td>
					<td class="border_top_right4" align="left" >
			<select name="orgCode" id="orgCode1">
	      		<option value="">请选择</option>
	      		<option value="10076001">中银卡司</option>
	      		<option value="10079001">中银MOTO</option>
	      		<option value="10080001">中银MIGS</option>
	      		<option value="10003001">中国银行</option>
	      		<option value="10002001">农业银行</option>
	      		<option value="10075001">CREDORAX</option>
	      		<option value="10077001">Adyen</option>
	      		<option value="10077002">Belto</option>
	      		<option value="10077003">Cashu</option>
	      		<option value="10078001">农行CTV</option>
				<option value="10081016">前海万融</option>
	      	</select>	
		</tr>
		<tr class="trForContent1">
				 <td class="border_top_right4" align="right" >拒付原因：</td>
					<td class="border_top_right4" align="left" >
					<input	type="text" id="bouncedReason" name="bouncedReason"></td>
		</tr>
		<tr class="trForContent1">
				 <td class="border_top_right4" align="right" >拒付原因码：</td>
					<td class="border_top_right4" align="left" >
					<input	type="text" id="reasonCode1" name="reasonCode"></td>
		</tr>
		<tr class="trForContent1">
				 <td class="border_top_right4" align="right" >显示原因：</td>
					<td class="border_top_right4" align="left" >
				<select name="visableCode" id="visable">
		      		<option value="">请选择</option>
		      		<option value="1">1——需要交易凭证</option>
		      		<option value="2">2——未收到货物或服务</option>
		      		<option value="3">3——未授权交易</option>
		      		<option value="4">4——重复处理</option>
		      		<option value="5">5——欺诈伪冒</option>
		      		<option value="6">6——货不对版</option>
		      		<option value="7">7——不承认</option>
		      		<option value="8">8——要求个人纪录</option>
		      		<option value="9">9——商品已退回未退款</option>
		      		<option value="10">10——未收到退款</option>
		      		<option value="11">11——金额不符</option>
		      		<option value="12">12——未提供单据</option>
		      		<option value="13">13——重复扣款</option>
		      	</select>	
		      	<input type="hidden" name="visableName">
	      	</td>
		</tr>
		<tr class="trForContent1">
		  <td align="center" class="border_top_right4" colspan="8">
	    	  <input type="button"  name="butSubmit" value="保 存" class="button2" onclick="save();">
	    	  <input type="button"  name="butSubmit" value="取 消" class="button2" onclick="cancel();">
	      </td>	
		</tr>
		</table>
	</form>	
</div>
  <script type="text/javascript">
 	 $(document).ready(function(){
		search();
	});  
 	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
		$.ajax({
			type: "POST",
			url: "${ctx}/bouncedReasonMapping.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
 	
 	function cancel() {
		$('#addBouncedDiv').dialog("close");
	}
	
	function add(){
		var serialNumber=$("#serialNumber").val();
		$("#serialNumberDev").html(serialNumber);
		$('#addBouncedDiv').dialog({
			position : "top",
			width : 440,
			modal : true,
			title : '新增映射',
			height : 281,
			overlay : {
				opacity : 0.5,
				background : "black",
				overflow : 'auto'
			}
		});
	}
	
	function save(){
		  var visableNmae=$("#visable option:selected").html();
		  var visable=$("#visable").val();
		  var  reasonCode=$("#reasonCode1").val();
		  var bouncedReason=$("#bouncedReason").val();
		  var orgCode=$("#orgCode1").val();
		  if(visable == ''){
			  alert("显示原因不能为空！！");
			  return;
		  }
		  if(reasonCode == ''){
			  alert("拒付原因码不能为空！！");
			  return;
		  }
		  if(bouncedReason == ''){
			  alert("拒付原因不能为空！！");
			  return;
		  }
		  if(orgCode == ''){
			  alert("请选择渠道！！");
			  return;
		  }
		  $("input[name='visableName']").attr("value",visableNmae);	
	  	  $("#addBouncedForm").submit();
	  	} 
	
	function findCode(){
		var orgCode=$("#orgCode").val();
		$("#reasonCode").empty();
		$("#reasonCode").append("<option value=''>全部</option>");
		$.ajax({
			type: "POST",
			url: "${ctx}/bouncedReasonMapping.do?method=queryCode",
			data: "&orgCode="+orgCode,
			success: function(result) {
				var parsedJson = jQuery.parseJSON(result)				
				for (var i = 0; i < parsedJson .length; i++) {
					var reasonCode = parsedJson [i].reasonCode;
					var bouncedReason = parsedJson [i].bouncedReason;
					
					$("#reasonCode").append("<option value="+reasonCode+">"+reasonCode+"——"+bouncedReason+"</option>");
					/* $("#visableCode").append("<option value="+visableCode+">"+visableCode+"</option>"); */
				}
			}
		});
	}
  </script>