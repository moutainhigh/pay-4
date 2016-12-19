<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">查询目的行与出款行配置</h2>
 <form action="" method="post" id="mainfromId">
		<table class="border_all2" width="80%" border="0" cellspacing="0"
			cellpadding="1" align="center">	
		    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >配置名称：</td>
		      <td class="border_top_right4">
		      	<input type="text" name="configName" id="configName" value="${dto.configName }" />
		      </td>
		      
		       <td class=border_top_right4 align="right" >目的银行：</td>
		      <td class="border_top_right4" >
		      	<li:select name="targetBankId"  defaultStyle="true" selected="${dto.targetBankId}"  itemList="${targetBankList}"  />
		      </td>
		    </tr>
		     <tr class="trForContent1"> 
			      <td class=border_top_right4 align="right" >出款渠道：</td>
			      <td class="border_top_right4" >
			        <li:codetable fieldName="channelId" style="select" attachOption="true" codeTableId="fundOutChannelTable" />
			      </td>
			      <td class=border_top_right4 align="right" >状态：</td>
			      <td class="border_top_right4" >
			      	<li:select name="status" selected="${dto.status}" itemList="${statusList}" />
			      </td>
			 </tr>
		    <tr class="trForContent1">
		      <td align="center" class="border_top_right4" colspan="4">
		      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="searchConfig();">
		      <input type="button"  name="butAdd" value="添加" class="button2" onclick="addConfig();">
		      <input type="button"  name="butReset" value="清空" class="button2" onclick="resetForm();">
		      </td>
		    </tr>
	   </table>
	
 </form>
 
<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
  <script type="text/javascript">
	
	$(document).ready(function(){
		searchConfig();
	}); 
	
	function searchConfig(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
		;
			$.ajax({
				type: "POST",
				url: "context_fundout_configbank.controller.htm?method=search",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	function resetForm(){
		$("#configName").val("");
		$("#targetBankId").val("");
		$("#channelId").val("");
		$("#status").val("1");
	}
	
	function addConfig(){
		window.location.href="${ctx}/context_fundout_configbank.controller.htm";
	}
	
  </script>