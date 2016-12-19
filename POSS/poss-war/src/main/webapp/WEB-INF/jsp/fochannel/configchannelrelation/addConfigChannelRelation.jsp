<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function goSubmit(){
		document.mainfrom.action="context_fundout_configchannelrelation.controller.htm?method=addConfig";
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
		checkRepeat();
	}

	function checkRepeat() {
		var productCode = $("#productCode").val();
		var channelId = $("#channelId").val();
		$.ajax({
		   type: "POST",
		   url: "context_fundout_configchannelrelation.controller.htm?method=checkRepeat",
		   data: "productCode=" + productCode + "&channelId=" + channelId,
		   success: function(msg){
			   if(msg != '0') {
					$("#msgInfo").html("同一产品同一渠道只能存在一个配置!");
			   }else{
					document.mainfrom.submit();
			   }
		   }
		});
	}
	
	function goBack(){
		window.location.href="${ctx}/context_fundout_configchannelrelation.controller.htm?method=initSearch";
	}
</script>
<h2 class="panel_title">新增出款产品与出款渠道配置</h2>
<form action="" method="post" name="mainfrom">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		
	    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >配置名称：</td>
		      <td class="border_top_right4">
		      	<input type="text"  name="configName" value=""  />
		      </td>
		      <td class=border_top_right4 align="right" >出款产品：</td>
		      <td class="border_top_right4" >
		      	<li:codetable fieldName="productCode" style="select" attachOption="true" codeTableId="fundOutProductTable" />
		      </td>
	     </tr>
	      
	    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >出款渠道：</td>
		      <td class="border_top_right4">
		      <li:codetable fieldName="channelId" style="select" attachOption="true" codeTableId="fundOutChannelTable" />
		      </td>

		      <td class=border_top_right4 align="right" >状态：</td>
		      <td class="border_top_right4" >
		      	<li:select name="status" itemList="${statusList}" />
		      </td>
	     </tr>
	      
	      <tr class="trForContent1">
		      <td class=border_top_right4 align="right" colspan="2"  >备注：</td>
		      <td class="border_top_right4" colspan="2">
		         	<textarea name="mark" rows="5" cols="20"></textarea>
		      </td>
	     	
	    </tr>
	      <tr class="trForContent1">
		      <td class="border_top_right4" align="center" colspan="4">
	      	<input type="button" onclick="goSubmit();" class="button2" value="新增">
	      	<input type="button" onclick="goBack();" class="button2" value="返回">
	      </td>
	     	
	    </tr>
			<c:if test="${not empty info}">
				<li style="color: red;">${info }</li>
			</c:if>
	  </table>
	  	<span id="msgInfo" style="color:red;font-weight: bold;"></span>
</form>
 