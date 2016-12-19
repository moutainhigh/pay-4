<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">复核导入文件信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" name="mainfrom" id="mainfromId">
	<input type="hidden" name="batchNum" value="${importFileDTO.batchNum }">
	<input type="hidden" name="bankCode" value="${importFileDTO.bankCode }">
	<input type="hidden" name="tradeType" value="${importFileDTO.tradeType }">
	<input type="hidden" name="busiFlag">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >批次号：</td>
      	<td class="border_top_right4" >
        	${resultSummaryDto.batchNum }
      	</td>
      	<td class=border_top_right4 align="right" >批次规则名称：</td>
       	<td class="border_top_right4" >
        	${resultSummaryDto.ruleName }
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >导入总条数：</td>
      	<td class="border_top_right4" >
        	${resultSummaryDto.allImportCount }条
      	</td>
      	<td class=border_top_right4 align="right" >导入总金额：</td>
       	<td class="border_top_right4" >
       		<fmt:formatNumber value="${resultSummaryDto.allImportAmount/1000}" pattern="#,##0.00" />元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >完全匹配交易总条数：</td>
      	<td class="border_top_right4" >
        	${resultSummaryDto.sMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >完全匹配交易总金额：</td>
       	<td class="border_top_right4" >
        	<fmt:formatNumber value="${resultSummaryDto.sMatchAmount/1000}" pattern="#,##0.00" />元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >不匹配交易总条数：</td>
      	<td class="border_top_right4" >
        	${resultSummaryDto.notMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >不匹配交易总金额：</td>
       	<td class="border_top_right4" >
        	<fmt:formatNumber value="${resultSummaryDto.notMatchAmount/1000}" pattern="#,##0.00" />元
     	</td>
    </tr>
  </table>
</form>
 
<input type="button" onclick="queryMatchOk();" name="submitBtn" value="完全匹配交易" class="button8">
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" onclick="queryDisMatch();" name="submitBtn" value="不匹配交易" class="button5">
      	
<div id="resultListDiv" class="listFence"></div>

<input type="button" onclick="goBack();" name="submitBtn" value="返回" class="button5">

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>  
 <script type="text/javascript">
 function goBack(){
	location.href = "fundout-withdraw-reviewfile.do";
 }

 $(document).ready(function(){queryMatchOk();}); 
 
	function queryMatchOk(pageNo,totalCount,pageSize) {
		$("input[name='busiFlag']").val("201");
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "fundout-withdraw-reviewfile.do?method=queryReviewFileListScuess",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	function queryDisMatch(pageNo,totalCount,pageSize) {
		$("input[name='busiFlag']").val('202');
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "fundout-withdraw-reviewfile.do?method=queryReviewFileListNoMatch",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>
 
