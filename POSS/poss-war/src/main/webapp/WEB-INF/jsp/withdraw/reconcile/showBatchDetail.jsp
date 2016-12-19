<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">批次结果详情</h2>
<form action="" method="post" name="mainfrom" id="mainfromId">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >批次号：</td>
      	<td class="border_top_right4" >
        	${wdRcResSummaryDto.batchNum }
      	</td>
      	<td class=border_top_right4 align="right" >批次规则名称：</td>
       	<td class="border_top_right4" >
        	${wdRcResSummaryDto.ruleName }
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >导入总条数：</td>
      	<td class="border_top_right4" >
        	${wdRcResSummaryDto.allImportCount }条
      	</td>
      	<td class=border_top_right4 align="right" >导入总金额：</td>
       	<td class="border_top_right4" >
        	<fmt:formatNumber value="${wdRcResSummaryDto.allImportAmount*0.001}" pattern="#,##0.00" />元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >完全匹配交易成功总条数：</td>
      	<td class="border_top_right4" >
        	${wdRcResSummaryDto.sMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >完全匹配交易成功总金额：</td>
       	<td class="border_top_right4" >
        	<fmt:formatNumber value="${wdRcResSummaryDto.sMatchAmount*0.001}" pattern="#,##0.00" />元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >完全匹配交易失败总条数：</td>
      	<td class="border_top_right4" >
        	${wdRcResSummaryDto.fMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >完全匹配交易失败总金额：</td>
       	<td class="border_top_right4" >
        	<fmt:formatNumber value="${wdRcResSummaryDto.fMatchAmount*0.001}" pattern="#,##0.00" />元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >完全匹配交易进行中总条数：</td>
      	<td class="border_top_right4" >
        	${wdRcResSummaryDto.matchIngCount }条
      	</td>
      	<td class=border_top_right4 align="right" >完全匹配交易进行中总金额：</td>
       	<td class="border_top_right4" >
        	<fmt:formatNumber value="${wdRcResSummaryDto.matchIngAmount*0.001}" pattern="#,##0.00" />元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >不匹配交易总条数：</td>
      	<td class="border_top_right4" >
        	${wdRcResSummaryDto.notMatchCount }条
      	</td>
      	<td class=border_top_right4 align="right" >不匹配交易总金额：</td>
       	<td class="border_top_right4" >
        	<fmt:formatNumber value="${wdRcResSummaryDto.notMatchAmount*0.001}" pattern="#,##0.00" />元
     	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td colspan="4" class=border_top_right4 align="center" >
   	  	<a href="#" onclick="back();" ><input type="button" name="back" onclick="back();" value="返回" class="button2"/></a></td>
   	  </tr>
  </table>
</form>
 
<script language="javascript">
	function back(pageNo,totalCount,pageSize){
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "fundout-withdraw-importwdresult.do?method=queryImportedFileList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>
 
