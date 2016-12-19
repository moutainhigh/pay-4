<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">下载银行代扣请求</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
		<td class="border_top_right4" align="right" >渠道代码：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="bankCode" name="bankCode" value="" maxlength= "17">
		</td>
		<td class="border_top_right4" align="right" >批次号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="batchNo" name="batchNo" value=""  maxlength= "32"></td>
		</td>
	</tr>
	
	<tr class="trForContent1"> 
		<td  class="border_top_right4" align="right" >业务类型：</td>
		<td  class="border_top_right4" align="left" >
			<select id="type" name="type">	
				<option value="">全部</option>
				<option value="01"  >签约</option>
				<option value="02" >代扣</option>
				
		   </select>	
		</td>
		<td class="border_top_right4" align="right" >文件名称：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="fileName" name="fileName" value=""  maxlength= "32"></td>
		</td>
		
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:bankfileDownloadQuery()"><img src="./images/query.jpg" border="0"></a>
			
		</td>
	</tr>
   </table>
</form>


 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
  <script type="text/javascript">
	/* $(document).ready(function(){
		search();
	});  */
	
	function bankfileDownloadQuery(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
		;
			$.ajax({
				type: "POST",
				url: "context_withhold_bankfile_download.htm?method=queryList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
  </script>