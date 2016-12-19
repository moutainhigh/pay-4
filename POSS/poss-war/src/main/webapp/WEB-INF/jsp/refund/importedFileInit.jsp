<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查询充退导入结果交易</font></div>
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
        	<input type="text"  name="batchNum" value="${webQueryRefundDTO.batchNum }"  />
      	</td>
      	<td class=border_top_right4 align="right" >批次名称：</td>
       	<td class="border_top_right4" >
        	<input type="text"  name="batchName" value="${webQueryRefundDTO.batchName }"  />
     	</td>
    </tr>
    <tr class="trForContent1">
      	<td align="right" class="border_top_right4">批次生成日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startTime" name="startTime" value='<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>'  onClick="WdatePicker()">
	        	～
			<input class="Wdate" type="text" id="endTime" name="endTime"  value='<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>' onClick="WdatePicker()">
      	</td>
      	<td class=border_top_right4 align="right" >银行文件状态：</td>
      	<td class="border_top_right4" >
        	<select name="fileStatus" >
        			<option value="">全部</option>
        			<option value="4">已导入</option>
        			<option value="5">已确认导入</option>
        	</select>
      	</td>
    </tr>
    <tr class="trForContent1">
      	<td class=border_top_right4 align="right" >业务类型：</td>
      	<td class="border_top_right4" >
        	<select name="" >
        			<option value="">全部</option>
        			<option value="0">充退</option>
        	</select>
      	</td>
      	<td align="right" class="border_top_right4">银行：</td>
      	<td class="border_top_right4">
	      	<li:select name="bankCode" defaultStyle="true" itemList="${bankList}"/>
      	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="submitByHref();" name="submitBtn" value="查  询" class="button2">
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
		var strDate1 = $("#startTime").val();
		var strDate2 = $("#endTime").val();

		if(!validDate(strDate1,strDate2)){
			alert("开始日期不能大于结束日期!");
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "refund.file.do?method=queryImportedFileList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>