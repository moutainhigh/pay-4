<%@ page contentType="text/html;charset=UTF-8" language="java"%> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查询分账退款明细</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId" name="mainfrom">
<input type="hidden" name="tradeOrderNo" id="tradeOrderNo" value="" width="130px;"/>
<input type="hidden" name="sharingDetailNo" id="sharingDetailNo" value="${sharingDetailNo}" width="130px;"/>
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td class=border_top_right4 align="right">商户会员号：</td>
      <td class="border_top_right4">
     		<input type="text" name="partnerId" id="partnerId" width="130px;"/>
      </td>
      <td class=border_top_right4 align="right">商户订单号：</td>
      <td class="border_top_right4">
        	<input type="text" name="merchantOrderId" id="merchantOrderId" width="130px;"/>
      </td>
    </tr>
    
    <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易状态：</td>
      <td class="border_top_right4">	
		   <select name="status" id="status" class="inp_normal w200" >
					<option value="">全部</option>
					<option value="0">初始化</option>
					<option value="1">处理中</option>
					<option value="2">成功</option>
					<option value="3">失败</option>
			</select>
      </td>
      
      <td class="border_top_right4" align="right" >时间范围：</td>
		<td class="border_top_right4" colspan="3">
			<input class="Wdate" type="text" id="startTime" name="startTime" 
				value='${startTime}' style="width: 150px;"  
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'});">
		&nbsp;至&nbsp;<input class="Wdate" type="text" id="endTime" name="endTime" 
				value='${endTime}' style="width: 150px;"  
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'});"></td>
    </tr>  
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
	  <input type="button" class="button2" name="executorsearchName" value="查 询" onclick="querysearch();"/>
      </td>
    </tr>
  </table>
 </form>

<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
<script type="text/javascript">
	     $(document).ready(function(){
			querysearch ();
		});
		function querysearch (pageNo,totalCount,pageSize) {
			
 			$('#infoLoadingDiv').dialog('open');
			var pars = $("#mainfromId").serialize();
			var url = "sharePaymentDetailed.htm?method=shareRefundSubmit";
			if(null!=pageNo){
				url+="&pageNo="+pageNo+"&totalCount="+totalCount+"&pageSize="+pageSize;
			}
			;
				$.ajax({
					type: "POST",
					url: url,
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
 		}
	</script>