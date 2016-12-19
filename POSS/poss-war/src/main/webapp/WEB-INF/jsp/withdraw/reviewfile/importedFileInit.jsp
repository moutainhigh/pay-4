<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>


<%@page import="java.util.Date"%><table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">复核导入文件查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainfromId" name="mainfrom">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >批次号：</td>
      	<td class="border_top_right4" >
        	<input type="text" id="batchNum" name="batchNum" value=""  />
      	</td>
      	<td class=border_top_right4 align="right" >批次名称：</td>
       	<td class="border_top_right4" >
        	<input type="text"  name="batchName" value=""  />
     	</td>
    </tr>
    <tr class="trForContent1">
      	<td align="right" class="border_top_right4">批次生成日期：</td>
      	<td class="border_top_right4">
	      	
      	   	<input class="Wdate" type="text" id="startTime" name="startTime" style="width: 150px;"   
      			value='<fmt:formatDate value="${startTime}" type="both"/>' 
   	   			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'})">
     	  	 ～
			<input class="Wdate" type="text" id="endTime" name="endTime" style="width: 150px;"  
				value='<fmt:formatDate value="${endTime}" type="both"/>' 
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})">
      	</td>
      <td class=border_top_right4 align="right" >业务类型：</td>
      <td class="border_top_right4" >
	        <select name="batchType" >
      		<option value="" >全部</option>
      		<option value="1">提现</option>
      	</select>
      </td>
    </tr>
   <!--  <tr class="trForContent1">
      	<td align="right" class="border_top_right4">渠道：</td>
      	<td class="border_top_right4">
	      	<select name="" >
        			<option value="">全部</option>
        			<option value="0">网银</option>
        	</select>
      	</td>
      	<td class=border_top_right4 align="right" >业务类型：</td>
      	<td class="border_top_right4" >
        	<select name="" >
        			<option value="">全部</option>
        			<option value="0">充退</option>
        			<option value="1">提现</option>
        	</select>
      	</td>
    </tr> -->
    <tr class="trForContent1">
      	<td align="right" class="border_top_right4">出款银行：</td>
      	<td class="border_top_right4">
	      	<select name="bankCode" >
        			<option value="">全部</option>
        			<option value="0">东亚银行</option>
        	</select>
      	</td>
      	<td class="border_top_right4" colspan="2">
      		&nbsp;
      	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="query();" name="submitBtn" value="查   询" class="button2">
      </td>
    </tr>
  </table>
  <c:if test="${not empty errorMsg }"> 
  	<li style="color: red"><c:out value="${errorMsg}" /> </li>
  </c:if>
  
 </form>

<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">
$(document).ready(function(){query();}); 
	function query(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "fundout-withdraw-reviewfile.do?method=queryImportedFileList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>