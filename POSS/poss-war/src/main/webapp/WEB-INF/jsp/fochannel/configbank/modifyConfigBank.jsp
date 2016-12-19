<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript"><!--
	function goSubmit(configId){
		document.mainfrom.action="context_fundout_configbank.controller.htm?method=modify&configId="+configId;
		if($("input[name='configName']").val() == ""){
			alert("请填写出款配置名称!");
			return false;
		}
		if($("#targetBankId").val() == ""){
			alert("请选择目的银行!");
			return false;
		}
		if($("#channelId").val() == ""){
			alert("请选择出款渠道!");
			return false;
		}
		checkRepeat(configId);
	}

	function checkRepeat(configId) {
		var targetBankId = $("#targetBankId").val();
		var channelIdOrg = $("#channelId").val();
		var index = channelIdOrg.indexOf("-");
		var channelId = channelIdOrg.substring(0, index);
		$.ajax({
		   type: "POST",
		   url: "context_fundout_configbank.controller.htm?method=checkRepeat",
		   data: "targetBankId=" + targetBankId + "&channelId=" + channelId + "&configId=" + configId,
		   success: function(msg){
			   if(msg != '0') {
					$("#msgInfo").html("同一渠道同一出款银行只能存在一个配置!");
					return;
			   }else{
				   checkChannel(configId);
			   }
		   }
		});
	}

	function checkChannel(configId) {
		var targetBankId = $("#targetBankId").val();
		var channelIdOrg = $("#channelId").val();
		var index = channelIdOrg.indexOf("-");
		var modeCode = channelIdOrg.substring(index+1);
		$.ajax({
		   type: "POST",
		   url: "context_fundout_configbank.controller.htm?method=checkChannel",
		   data: "targetBankId=" + targetBankId + "&configId=" + configId + "&modeCode=" + modeCode,
		   success: function(msg){
			   if(msg != '0') {
					$("#msgInfo").html("同一目的银行只能存在一个手工出款渠道配置!");
			   }else{
					document.mainfrom.submit();
			   }
		   }
		});
	}

	function changeChannel(){
		var channelIdOrg = $("#channelId").val();
		if(channelIdOrg != ""){
			var index = channelIdOrg.indexOf("-");
			var modeCode = channelIdOrg.substring(index+1);
			var channelId = channelIdOrg.substring(0, index);
			$.ajax({
				type: "POST",
				url: "context_fundout_configbank.controller.htm?method=findChannel",
				data: "channelId=" + channelId + "&modeCode=" + modeCode,
				success: function(msg){
					$("#targetBankId").html(msg);
				}
			});
		}
	}

	function goBack() {
		document.mainfrom.action="context_fundout_configbank.controller.htm?method=initSearch";
		document.mainfrom.submit();
	}
--></script>

<h2 class="panel_title">修改目的行与出款行配置</h2>

<form action="" method="post" name="mainfrom">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		
	    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >配置名称：</td>
		      <td class="border_top_right4">
		      	<input type="text" name="configName" id="configName" value="${dto.configName}"/>
		      </td>
				<td align="right" class="border_top_right4" >出款渠道：</td>
		      <td class="border_top_right4">
<!--		      <li:codetable fieldName="channelId" selectedValue="${dto.channelId}" style="select" attachOption="true" codeTableId="fundOutChannelTable" />-->
		      	<select name="channelId" id="channelId" onChange="changeChannel();">
					<option>--请选择--</option>
					<c:forEach var="channel" items="${channelList}">
						<option value="${channel.channelId}-${channel.modeCode}" <c:if test="${channel.channelId == dto.channelId}">selected="selected"</c:if>>
							${channel.channelName}
						</option>
					</c:forEach>
				</select>
		      </td>
	     </tr>
	      
	    <tr class="trForContent1">
	    	<td class=border_top_right4 align="right" >目的银行：</td>
		      <td class="border_top_right4" >
		      	<li:select name="targetBankId"  selected="${dto.targetBankId}" itemList="${targetBankList}"  />
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
	      	<input type="button" onclick="goSubmit('${dto.configId }');" class="button2" value="确定">
	      </td>
	      <td class=border_top_right4 align="left" colspan="2">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返回">
	      </td>
	    </tr>
	  </table>
	  <span id="msgInfo" style="color:red;font-weight: bold;"></span>
</form>
 