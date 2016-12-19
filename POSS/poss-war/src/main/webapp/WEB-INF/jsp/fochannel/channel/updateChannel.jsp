<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		if(${channel.modeCode} == 0){
			$("tr.enterprise").show();
		}
		if(${channel.modeCode} == 1){
			$("tr.enterprise").hide();
		}
		$("#btn").click(function(){
			var mode=${channel.modeCode};
			var rule1 = /^[0-9]+$/; //非负整数正则

			var bankAcc=$("#bankAcc").val();
			var bankAccName=$("#bankAccName").val();
			var serverAddress=$("#serverAddress").val();
			var serverPort=$("#serverPort").val();
			var macKey=$("#macKey").val();
			var status=$("#status  option:selected").val();
			// 银企直连方式增加校验
			if(mode == 0){
				if(bankAcc == ""){
					alert("请填写银企账号");
					return false;
				}
				if(bankAccName == ""){
					alert("请填写银企账号名称");
					return false;
				}
				if($("#minRemindedBalance").val() != ""){
					if(!(rule1.test($("#minRemindedBalance").val()))){
						alert("最低提醒余额只能是非负整数");
						return false;
					}
				}
				if($("#upperLimit").val() != ""){
					if(!(rule1.test($("#upperLimit").val()))){
						alert("单笔金额上限只能是非负整数");
						return false;
					}
				}
				if($("#lowerLimit").val() != ""){
					if(!(rule1.test($("#lowerLimit").val()))){
						alert("单笔金额下限只能是非负整数");
						return false;
					}
				}
				if($("#upperLimit").val() != "" && $("#lowerLimit").val() != ""){
					if($("#lowerLimit").val() > $("#upperLimit").val()){
						alert("单笔金额下限值不能超过上限值");
						return false;	
					}
				}
				if($("#maxSupportItems").val() != ""){
					if(!(rule1.test($("#maxSupportItems").val()))){
						alert("最多支持笔数只能是非负整数");
						return false;
					}
				}
				if(serverAddress == ""){
					alert("请填写服务器地址");
					return false;
				}
				if(serverPort == ""){
					alert("请填写服务端口");
					return false;
				}else if(!(rule1.test(serverPort))){
					alert("服务端口只能是非负整数");
					return false;
				}
				if(macKey == ""){
					alert("请填写MAC密钥");
					return false;
				}
				if(status == ''){
					alert('请选择状态');
					return false;
				}
			}
			$("#frm").submit();
		});
	});
	
	function channelQuery(){
		window.location.href='${ctx}/fundoutChannel.htm?method=query';
	}	

</script>

<h2 class="panel_title">修改出款渠道</h2>
<form action="${ctx}/fundoutChannel.htm?method=updateChannel" method="post" id="frm">
	 <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	 	<input type="hidden" name="channelId" value="${channel.channelId}"/>
	 	
	    <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >出款渠道名称：</td>
		      <td class="border_top_right4" >
		      ${channel.channelName }
		      </td>
		      <td class=border_top_right4 align="right" >出款银行：</td>
		      <td class="border_top_right4">
			  	  ${channel.bankName }
		      </td>
	    </tr>
	    
	    <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >出款方式：</td>
		      <td class="border_top_right4">
			  	   ${channel.modeName }
		      </td>
	    	<td class=border_top_right4 align="right" >状态：</td>
	      	<td class="border_top_right4" >
	      		<li:select name="status" itemList="${statusList}" />
	     	 </td>
	    </tr>
	    
	    <tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >银企账号：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="bankAcc" id="bankAcc" value="${channel.bankChannelConfig.bankAcc}" />
		      </td>
	    	<td class=border_top_right4 align="right" >银企账号名称：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="bankAccName" id="bankAccName" value="${channel.bankChannelConfig.bankAccName}" />
		      </td>
	    </tr>
	    <tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >最低提醒余额 ：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="minRemindedBalance" id="minRemindedBalance" value="${channel.bankChannelConfig.minRemindedBalance}" />
		      </td>
	    	<td class=border_top_right4 align="right" >是否支持多笔：</td>
		      <td class="border_top_right4">
			  	   <select name="isSupportMultiple" id="isSupportMultiple">
			  	       <option value="0" <c:if test="${channel.bankChannelConfig.isSupportMultiple == 0}">selected="selected"</c:if>>不支持</option>
			  	       <option value="1" <c:if test="${channel.bankChannelConfig.isSupportMultiple == 1}">selected="selected"</c:if>>支持</option>
			  	   </select>
		      </td>
	    </tr>
	    <tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >单笔金额上限：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="upperLimit" id="upperLimit" value="${channel.bankChannelConfig.upperLimit}" />
		      </td>
	    	<td class=border_top_right4 align="right" >单笔金额下限：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="lowerLimit" id="lowerLimit" value="${channel.bankChannelConfig.lowerLimit}" />
		      </td>
	    </tr>
	    <tr class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >最多支持笔数：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="maxSupportItems" id="maxSupportItems" value="${channel.bankChannelConfig.maxSupportItems}" />
		      </td>
	    	<td class=border_top_right4 align="right" >服务器地址：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="serverAddress" id="serverAddress" value="${channel.bankChannelConfig.serverAddress}" />
		      </td>
	    </tr>
	    <tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >服务端口：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="serverPort" id="serverPort" value="${channel.bankChannelConfig.serverPort}" />
		      </td>
	    	<td class=border_top_right4 align="right" >MAC密钥：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="macKey" id="macKey" value="${channel.bankChannelConfig.macKey}" />
		      </td>
	    </tr>
	    
	    <tr class="trForContent1">
	    	<td class=border_top_right4 align="right" >备注：</td>
	      	<td class="border_top_right4" colspan="3">
	      		<textarea rows="5" cols="30" name="mark">${channel.mark}</textarea>
	     	 </td>
	    </tr>
	     
	     <tr class="trForContent1">
	      <td class="border_top_right4" align="center" colspan="4">
	      	
	      	<input id="btn" type="button" class="button2" value="修改">
	      	<input id="btn_back" type="button" class="button2" value="返回" onClick="channelQuery()">
	      </td>
	    </tr>
	  </table>
</form>