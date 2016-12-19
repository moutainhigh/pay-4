<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">批次查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
 <form action="" method="post" id="initForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
  
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >批次号：</td>
      <td class="border_top_right4" >
	        <input type="text"  name="batchNum"  />
      </td>
      <td class=border_top_right4 align="right" >批次规则名称：</td>
      <td class="border_top_right4" >
	        <!--<select id="ruleId" onchange="showReBulidBatch(this);">
	        <option selected="selected" value="choose">请选择</option>
	        	<c:forEach items="${list}" var="dto">
	        		<option value="${dto.sequenceId }">${dto.batchRuleDesc }</option>
	        	</c:forEach>
	        </select>-->
	        <select id="ruleKy" name="ruleKy" onchange="showReBulidBatch(this);">
	        <option selected="selected" value="">请选择</option>
	        	<c:forEach items="${list}" var="dto">
	        		<option value="${dto.sequenceId }">${dto.batchRuleDesc }</option>
	        	</c:forEach>
	        </select>
      </td>
      
    </tr>
    
        <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >批次生成时间：</td>
      <td class="border_top_right4" colspan="3" >
      	<input class="Wdate" type="text" id="startTime" name="startTime" style="width: 150px;"   
      			value='<fmt:formatDate value="${startTime}" type="both"/>' 
      			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'})">
       	 ～
		<input class="Wdate" type="text" id="endTime" name="endTime" style="width: 150px;"  
				value='<fmt:formatDate value="${endTime}" type="both"/>' 
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})">
      </td>
    </tr>
    
    <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >批次状态：</td>
      <td class="border_top_right4" colspan="3" >
      	<select name="batchStatus" >
      		<option value="" >全部</option>
      		<option value="4">已出批次文件</option>
      		<option value="5">未出批次文件</option>
      		<option value="3">已废除批次文件</option>
      	</select>
      </td>
    </tr>
     <tr class="trForContent1">
	     <td align="center" class="border_top_right4" colspan="4">
	     	 <input type="button" onclick="query();" name="submitBtn" value="查  询" class="button2">
	      </td><!--
	     <td align="center" class="border_top_right4" colspan="2">
	     	&nbsp;<input  id="generBatch" style="display: none" type="button" onclick="query();" name="submitBtn" value="生 成 报 警 批 次" class="button4">
	      </td>
    --></tr>
  </table>
 </form>
<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
  <script type="text/javascript">
	
	$(document).ready(function(){query();}); 
	function query(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#initForm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "fundout-withdraw-querybatchfile.do?method=queryBatch",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	function showReBulidBatch(obj) {
		if(obj != null && obj != "" && obj.value!="choose"){
			$("#generBatch").show();
		}else{
			$("#generBatch").hide();
		}
		
	}


	//重支付平台成批次
	function regenerateBatchFile(){
		var ruleId = $("#ruleId").val();
		var pars = $("#listForm").serialize()+ "&ruleId=" + ruleId ;
		$.ajax({
			type: "POST",
			url: "fundout-withdraw-querybatchfile.do?method=regenerateBatch",
			data: pars,
			success: function(transport) {
		          var result = eval('('+transport+')');
		          alert(result.infos);
		          query();
		          }
			});
	}
	
  </script>