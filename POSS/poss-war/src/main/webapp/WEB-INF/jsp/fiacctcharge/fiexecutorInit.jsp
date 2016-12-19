<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	
function executorsearch(pageNo,totalCount,pageSize) {
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		var url = "batchExecutor.htm?method=search";
		if(null!=pageNo){
			url+="&pageNo="+pageNo+"&totalCount="+totalCount+"&pageSize="+pageSize;
		}
		;
			$.ajax({
				type: "POST",
				url: url,
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">手工批量补单审核</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


 <form action="" method="post" id="mainfromId">
  
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
   
 
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">批次号：</td>
      <td class="border_top_right4">
        <input type="text" id="batchNo" name="batchNo" value="${batchNo}" size="23"/>
	
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">申请时间：</td>
      <td class="border_top_right4">
        <input class="Wdate" type="text" id="startTime" name="startTime" 
						value='${startDate}' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'});">
				至&nbsp;
					<input class="Wdate" type="text" id="endTime" name="endTime" 
						value='${endDate}' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'});">
		
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">申请人：</td>
      <td class="border_top_right4">
       <input type="text" id="operator" name="operator" value="${operator}" size="23"/>
      </td>
      </tr>
	<tr class="trForContent1">
      <td class="border_top_right4" align="right">状态：</td>
      <td class="border_top_right4">
        <select name="auditstatus" id="auditstatus" class="inp_normal w200" >
								<option value="0">未审核</option>
								<option value="">全部</option>
								<option value="1">成功</option>
								<option value="2">失败</option>
							</select>
	
      </td>
    </tr>
   <tr class="trForContent1">
      <td class="border_top_right4" align="center" colspan="2" >
        
          		<input type="button" class="button2" name="executorsearchName" value="查 询" onclick="executorsearch();"/>
      </td>
    </tr>
  </table>

</form>
<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div> 