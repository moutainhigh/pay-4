<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function goSubmit(configId){
		document.mainfrom.action="context_fundout_configchannelrelation.controller.htm?method=modify&configId="+configId;
		if($("input[name='configName']").val() == ""){
			alert("请填写出款配置名称!");
			return false;
		}
		if($("#productCode").val() == ""){
			alert("请选择出款产品!");
			return false;
		}
		if($("#channelId").val() == ""){
			alert("请选择出款渠道!");
			return false;
		}
		checkRepeat(configId);
	}

	function checkRepeat(configId) {
		var productCode = $("#productCode").val();
		var channelId = $("#channelId").val();
		$.ajax({
		   type: "POST",
		   url: "context_fundout_configchannelrelation.controller.htm?method=checkRepeat",
		   data: "productCode=" + productCode + "&channelId=" + channelId + "&configId=" + configId,
		   success: function(msg){
			   if(msg != '0') {
					$("#msgInfo").html("同一产品同一渠道只能存在一个配置!");
			   }else{
					document.mainfrom.submit();
			   }
		   }
		});
	}

	function goBack() {
		document.mainfrom.action="context_fundout_configchannelrelation.controller.htm?method=initSearch";
		document.mainfrom.submit();
	}
</script>
<h2 class="panel_title">修改出款产品与出款渠道配置</h2>

<form action="" method="post" name="mainfrom">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		
	    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >配置名称：</td>
		      <td class="border_top_right4">
		      	<input type="text" name="configName" id="configName" value="${dto.configName}"/>
		      </td>

		      <td class=border_top_right4 align="right" >出款产品：</td>
		      <td class="border_top_right4" >
		      	<li:codetable fieldName="productCode" selectedValue="${dto.productCode}" style="select" attachOption="true" codeTableId="fundOutProductTable" />
		      </td>
	     </tr>
	      
	    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >出款渠道：</td>
		      <td class="border_top_right4">
		      <li:codetable fieldName="channelId" selectedValue="${dto.channelId}" style="select" attachOption="true" codeTableId="fundOutChannelTable" />
		      
		      </td>

		      <td class=border_top_right4 align="right" >状态：</td>
		      <td class="border_top_right4" >
		      	<li:select name="status" selected="${dto.status}" itemList="${statusList}" />
		      </td>
	     </tr>
	      
	      <Tr class="trForContent1">
		      <td class=border_top_right4 align="right" colspan="2" >备注：</td>
		      <td class="border_top_right4" colspan="2" >
		         	<textarea name="mark" rows="5" cols="20">${dto.mark }</textarea>
		      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="right" colspan="2">
	      	<input type="button" onclick="goSubmit('${dto.configId }');" class="button2" value="确  定">
	      </td>
	      <td class=border_top_right4 align="left" colspan="2">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
	  <span id="msgInfo" style="color:red;font-weight: bold;"></span>
</form>
 