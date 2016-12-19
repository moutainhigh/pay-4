<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>手工生成批次文件</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function businessQuery(pageNo,totalCount,pageSize) {
				var strDate1 = $("#STARTTIME").val();
				var strDate2 = $("#ENDTIME").val();

				if(!validDate(strDate1,strDate2)){
					alert("开始日期不能大于结束日期!");
					return false;
				}
				
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#refundSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/refund.handwork.do?method=search",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}

			function processHandwork() {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#checkWorkorderKy").serialize();
				$.ajax({
					type: "POST",
					url: "${ctx}/refund.handwork.do?method=handwork",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			$(document).ready(function(){businessQuery();});

			//验证日期
			function validDate(strDate1,strDate2){
				if(null != strDate1 && null != strDate2 &&
						0 != strDate1.length && 0 != strDate2.length){
					var date1 = new Date(strDate1.replace("-","/"));
					var date2 = new Date(strDate2.replace("-","/"));
					if(date1 > date2){
						return false;
					}
				}
				return true;
			}
		</script>
	</head>

	<body>
		
		<h2 class="panel_title">查询充退数据</h2>
		<form id="refundSearchForm">
		<table  class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
			<tr class="trForContent1">
		      	<td align="right" class="border_top_right4">交易时间:</td>
		      	<td class="border_top_right4" colspan="3">
			      	<li:dateTime id="STARTTIME"  width="120" />&nbsp;至&nbsp;<li:dateTime id="ENDTIME" width="120" />
		      	</td>
		    </tr>
		    <tr class="trForContent1">
		   	  	<td class=border_top_right4 align="right" >会员号：</td>
		      	<td class="border_top_right4" >
		        	<input type="text" name="MEMBER_CODE" style="width: 120px;" />
		      	</td>
		      	<td class=border_top_right4 align="right" >交易流水号：</td>
		      	<td class="border_top_right4" >
		        	<input type="text" name="ORDER_KY" style="width: 120px;" />
		      	</td>
		    </tr>
		    <tr class="trForContent1">
		      <td align="center" class="border_top_right4" colspan="4">
		      <input type="button" onclick="businessQuery();" name="submitBtn" value="查  询" class="button2">
		      </td>
		    </tr>
		</table>
		</form>
		<div id="resultListDiv" class="listFence"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
		
