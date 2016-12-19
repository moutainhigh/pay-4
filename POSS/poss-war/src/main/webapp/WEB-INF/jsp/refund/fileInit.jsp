<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">查询提交银行充退文件</h2>
<form action="" method="post" id="mainfromId" name="mainfrom">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >批次号：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="batchNum" value="${webQueryRefundDTO.batchNum }"  />
      	</td>
      	<td align="right" class="border_top_right4">银行：</td>
      	<td class="border_top_right4">
      		<li:select name="bankCode" defaultStyle="true" itemList="${bankList}"/>
      	</td>
    </tr>
    <tr class="trForContent1">
      	<td class=border_top_right4 align="right" >银行文件状态：</td>
      	<td class="border_top_right4" >
        	<select name="fileStatus" >
        			<option value="">全部</option>
        			<option value="2">已生成</option>
        			<option value="3">已下载</option>
        			<option value="4">已导入</option>
        			<option value="5">已确认导入</option>
        	</select>
      	</td>
      	<td class=border_top_right4 align="right" >业务类型：</td>
      	<td class="border_top_right4" >
        	<select name="" >
        			<option value="">全部</option>
        			<option value="0">充退</option>
        	</select>
      	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="submitByHref();" name="submitBtn" value="查询" class="button2">
      </td>
    </tr>
  </table>
 </form>

<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">

	function submitByHref(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "refund.file.do?method=queryFileList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	
</script>