<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">批次文件查询</h2>
 <form action="" method="post" id="initForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
  
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >批次号：</td>
      <td class="border_top_right4" >
	        <input type="text"  name="batchNum" value="${batchNum}" />
      </td>
    
      <td class=border_top_right4 align="right" >出款流水号：</td>
      <td class="border_top_right4" >
	        <input type="text"  name="tradeSeq" value="${tradeSeq}" />
      </td>
    
      <td align="right" class="border_top_right4" colspan="1"  >批次文件状态：</td>
      <td class="border_top_right4" colspan="3" >
      	<select name="batchFileStatus" >
      		<option value="">全部</option>
      		<option value="2">已生成  </option>
      		<option value="3">已下载  </option>
      		<option value="6">已废除  </option>
      	</select>
      </td>
    </tr>
      <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >文件生成时间：</td>
      <td class="border_top_right4" colspan="2" >
  	    	<input class="Wdate" type="text" id="startTime" name="startTime" style="width: 150px;"   
      			value='<fmt:formatDate value="${startTime}" type="both"/>' 
      			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'})">
       	 ～
		<input class="Wdate" type="text" id="endTime" name="endTime" style="width: 150px;"  
				value='<fmt:formatDate value="${endTime}" type="both"/>' 
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})">
      </td>
      <td class="border_top_right4" align="right" colspan="2">处理状态:</td>
       <td class="border_top_right4" >
       		<select name="finStatus" >
	      		<option value="">全部</option>
	      		<option value="12">处理成功</option>
	      		<option value="10">处理失败</option>
	      		<option value="4">未处理</option>
	      		<option value="7,8,9">处理中</option>
     	 	</select>
      </td>
    </tr>
     <tr class="trForContent1">
	     <td align="center" class="border_top_right4" colspan="6">
	     	 <input type="button" onclick="query();" name="submitBtn" value="查询" class="button2">
	     &nbsp;	 <input type="button" onclick="download();" name="submitBtn" value="下载" class="button2">
	      </td>
    </tr>
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
				url: "fundout-withdraw-querybatchfile.do?method=queryBatchFile",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	
	function download(){
		var pars = $("#initForm").serialize();
		window.location.href = 	"./fundout-withdraw-querybatchfile-download.do?method=downloadBatchFile&"+pars;
	}
  </script>