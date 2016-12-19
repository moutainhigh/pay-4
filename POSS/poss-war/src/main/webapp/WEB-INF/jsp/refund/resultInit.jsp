<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">导入充退结果文件信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" name="mainfrom" id="mainfromId">
	<input type="hidden" name="batchNum" value="${webQueryRefundDTO.batchNum}">
	<input type="hidden" name="bankCode" value="${webQueryRefundDTO.bankCode}">
	<input type="hidden" name="busiFlag" value="">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >批次号：</td>
      	<td class="border_top_right4" >
        	${webQueryRefundDTO.batchNum }
      	</td>
      	<td class=border_top_right4 align="right" >批次名称：</td>
       	<td class="border_top_right4" >
        	${webQueryRefundDTO.batchName }
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >导入总条数：</td>
      	<td class="border_top_right4" >
        	${resultStatDTO.allImportCount }条
      	</td>
      	<td class=border_top_right4 align="right" >导入总金额：</td>
       	<td class="border_top_right4" >
        	${resultStatDTO.allImportAmount }元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >完全匹配交易成功总条数：</td>
      	<td class="border_top_right4" >
        	${resultStatDTO.sMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >完全匹配交易成功总金额：</td>
       	<td class="border_top_right4" >
        	${resultStatDTO.sMatchAmount } 元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >完全匹配交易失败总条数：</td>
      	<td class="border_top_right4" >
        	${resultStatDTO.fMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >完全匹配交易失败总金额：</td>
       	<td class="border_top_right4" >
        	${resultStatDTO.fMatchAmount } 元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >完全匹配交易进行中总条数：</td>
      	<td class="border_top_right4" >
        	${resultStatDTO.matchIngCount }条
      	</td>
      	<td class=border_top_right4 align="right" >完全匹配交易进行中总金额：</td>
       	<td class="border_top_right4" >
        	${resultStatDTO.matchIngAmount }元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >不匹配交易总条数：</td>
      	<td class="border_top_right4" >
        	${resultStatDTO.notMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >不匹配交易总金额：</td>
       	<td class="border_top_right4" >
        	${resultStatDTO.notMatchIngAmount } 元
     	</td>
    </tr>
  </table>
</form>
 
<input type="button" onclick="queryMatchOk();" name="submitBtn" value="完全匹配交易成功" class="button8">
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" onclick="queryMatchFail();" name="submitBtn" value="完全匹配交易失败" class="button8">
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" onclick="queryMatchProcessing();" name="submitBtn" value="完全匹配交易进行中" class="button9">
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" onclick="queryDisMatch();" name="submitBtn" value="不匹配交易" class="button5">
<br/>
<input type="button" onclick="history.go(-1);" name="rtnBtn" value="返  回" class="button2">      	
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>  
 <script type="text/javascript">

 $(document).ready(function(){queryMatchOk();}); 
 
	function queryMatchOk(pageNo,totalCount) {
		$("input[name='busiFlag']").val('1');
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
			$.ajax({
				type: "POST",
				url: "refund.file.do?method=getResultMatchOkList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	function queryMatchFail(pageNo,totalCount) {
		$("input[name='busiFlag']").val('2');
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
			$.ajax({
				type: "POST",
				url: "refund.file.do?method=getResultMatchFailList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	function queryMatchProcessing(pageNo,totalCount) {
		$("input[name='busiFlag']").val('3');
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
			$.ajax({
				type: "POST",
				url: "refund.file.do?method=getResultMatchProcessingList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	function queryDisMatch(pageNo,totalCount,pageSize) {
		$("input[name='busiFlag']").val('4');
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "refund.file.do?method=getResultMatchDisMatchList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>
 
