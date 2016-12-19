<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("tr.enterprise").hide();
		$("#btn").click(function(){
			if(checkInfo()){
				var url="${ctx}/fundoutChannel.htm";
				var data="method=checkChannel&channelName="+$("#channelName").val()+
				         "&bankId="+$("#bankId").val()+"&modeId="+$("#mode").val();
		         $.post(url,data,function(res){
					if(res=="nameRepeat"){
						alert("渠道名称已存在，请重新填写");
					} else if(res=="channelRepeat"){
						alert("出款银行、出款方式编号的组合不能重复");
					} else if(res=="yes"){
						$("#frm").submit();
					}
			     });
			}
		});
	});

	function changeEnterprise(){
		if($("#mode").val() == 0){
			$("tr.enterprise").show();
		}else{
			$("tr.enterprise").hide();
		}
		if($("#mode").val() == ""){
			$("tr.enterprise").hide();
		}
	}

	function checkInfo(){
		var channelName=$("#channelName").val();
		var bank=$("#bankId").val();
		var mode=$("#mode").val();
		var rule1 = /^[0-9]+$/; //非负整数正则

		var bankAcc=$("#bankAcc").val();
		var bankAccName=$("#bankAccName").val();
		var serverAddress=$("#serverAddress").val();
		var serverPort=$("#serverPort").val();
		var macKey=$("#macKey").val();
		
		if($.trim(channelName).length<1 || $.trim(channelName).length>50){
			alert("名称长度必须在1-50之间");
			return false;
		}
		if(bank.length<1){
			alert("请选择出款银行");
			return false;
		}
		if(mode.length<1){
			alert("请选择出款方式");
			return false;
		}
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
		}
		return true;
	}
	
	function channelQuery(){
		window.location.href='${ctx}/fundoutChannel.htm?method=query';
	}	
</script>
<h2 class="panel_title">渠道新增</h2>
<form action="${ctx}/fundoutChannel.htm?method=createChannel" method="post" id="frm">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		
	    <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >出款渠道名称：</td>
		      <td class="border_top_right4" >
		      <input type="text" name="channelName" id="channelName" />
		      </td>
		      <td class=border_top_right4 align="right" >出款银行：</td>
		      <td class="border_top_right4">
			  	   <select name="bankId" id="bankId">
						<option value="">--请选择--</option>
						<c:forEach items="${channels}" var="channel">
							<option value="${channel.bankId }">${channel.bankName}</option>
						</c:forEach>
					</select>
		      </td>
	    </tr>
	    
	    <tr  class="trForContent1">
		      <td class=border_top_right4 align="right" >出款方式：</td>
		      <td class="border_top_right4">
			  	   <li:codetable fieldName="mode" onChange="changeEnterprise();" style="select" attachOption="true" codeTableId="fundOutModeTable"></li:codetable>
		      </td>
	    	<td class=border_top_right4 align="right" >状态：</td>
	      	<td class="border_top_right4" >
	      		<li:select name="status" itemList="${statusList}" />
	     	 </td>
	    </tr>
	    
	    	<tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >银企账号：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="bankAcc" id="bankAcc" />
		      </td>
	    	<td class=border_top_right4 align="right" >银企账号名称：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="bankAccName" id="bankAccName" />
		      </td>
	    </tr>
	    <tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >最低提醒余额 ：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="minRemindedBalance" id="minRemindedBalance" />
		      </td>
	    	<td class=border_top_right4 align="right" >是否支持多笔：</td>
		      <td class="border_top_right4">
			  	   <select name="isSupportMultiple" id="isSupportMultiple">
			  	       <option value="0">不支持</option>
			  	       <option value="1">支持</option>
			  	   </select>
		      </td>
	    </tr>
	    <tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >单笔金额上限：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="upperLimit" id="upperLimit" />
		      </td>
	    	<td class=border_top_right4 align="right" >单笔金额下限：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="lowerLimit" id="lowerLimit" />
		      </td>
	    </tr>
	    <tr class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >最多支持笔数：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="maxSupportItems" id="maxSupportItems" />
		      </td>
	    	<td class=border_top_right4 align="right" >服务器地址：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="serverAddress" id="serverAddress" />
		      </td>
	    </tr>
	    <tr  class="trForContent1 enterprise">
		      <td class=border_top_right4 align="right" >服务端口：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="serverPort" id="serverPort" />
		      </td>
	    	<td class=border_top_right4 align="right" >MAC密钥：</td>
		      <td class="border_top_right4">
			  	   <input type="text" name="macKey" id="macKey" />
		      </td>
	    </tr>
	    
	    <tr class="trForContent1">
	    	<td class=border_top_right4 align="right" >备注：</td>
	      	<td class="border_top_right4" colspan="3">
	      		<textarea rows="5" cols="30" name="mark"></textarea>
	     	 </td>
	    </tr>
	     
	     <tr class="trForContent1">
	      <td class="border_top_right4" align="center" colspan="4">
	      	
	      	<input id="btn" type="button" class="button2" value="保存">
	      	<input id="btn_back" type="button" class="button2" value="返回" onClick="channelQuery()">
	      </td>
	    </tr>
	    <c:if test="${not empty info}">
			<li style="color: red;">${info }</li>
		</c:if>
	  </table>
</form>