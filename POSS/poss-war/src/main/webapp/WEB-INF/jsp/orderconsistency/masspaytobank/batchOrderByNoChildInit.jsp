<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批量到银行有未生成子订单的总订单</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

			function query(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
					$.ajax({
						type: "POST",
						url: "${ctx}/orderMassPayToBank.htm?method=noChildList",
						data: pars,
						success: function(result) {
							$('#infoLoadingDiv').dialog('close');
							$('#resultListDiv').html(result);
						}
					});		
			}

		</script>
	</head>
	<table width="25%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
			<div align="center"><font class="titletext">批量到银行有未生成子订单的总订单</font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<form action="" method="post" id="mainfromId" name="mainfrom" 
			onkeydown="javascript:if(event.keyCode == 13){document.all.submitBtn.focus();document.all.submitBtn.click();}">
	  <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	   	  	<td class=border_top_right4 align="right" >交易时间：</td>
	      	<td class="border_top_right4" colspan="3">
	        	<input type="text" name="startDate" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'endDate\');}'})" class="Wdate"/>
	      	至
	        	<input type="text" name="endDate" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\');}',maxDate:'%y-%M-%d'})" class="Wdate"/>
	     	</td>
	    </tr>
	    <tr class="trForContent1">
	      	<td align="right" class="border_top_right4">交易流水号：</td>
	      	<td class="border_top_right4">
		    	<input type="text" name="massOrderSeq" style="width:160px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="20"/>   	
		    </td>
	      	<td class=border_top_right4 align="right" >会员号：</td>
	      	<td class="border_top_right4" >
	      		<input type="text" name="payerMemberCode" style="width:160px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="19"/>
	      	</td>
	    </tr>
	   <tr class="trForContent1">
	   		<td align="right" class="border_top_right4">上传批次号：</td>
	      	<td class="border_top_right4">
		    	<input type="text" name="businessNo" style="width:160px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="20"/>   	
		    </td>
	      <td align="center" class="border_top_right4" colspan="2">
	      <input type="button" onclick="query();" name="btn" value="查  询" class="button2">
	      </td>
	    </tr>
	  </table>
	 </form>
	<div id="resultListDiv" class="listFence"></div>
	<div id="deleteRoleDiv" title="删除用户信息"></div>
	<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
	<div id="infoLoadingDiv" title="加载信息"
		style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
		请稍候...
	</div>
</html>