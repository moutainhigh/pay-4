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
		<div align="center"><font class="titletext">退款明细对账单</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId" name="mainfrom">
<input type="hidden" name="tradeOrderNo" id="tradeOrderNo" value="" width="130px;"/>
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td class=border_top_right4 align="right">商户会员号：</td>
      <td class="border_top_right4">
     		<input type="text" name="partnerId" id="partnerId" value="${partnerId}" width="130px;"/>
      </td>
    </tr>
    
    <tr class="trForContent1">  
      <td class="border_top_right4" align="right" >时间范围：</td>
				<td class="border_top_right4" colspan="3">
					<input class="Wdate" type="text" id="startTime" name="startTime" 
						value='${startTime}' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'});">
				&nbsp;至&nbsp;
					<input class="Wdate" type="text" id="endTime" name="endTime" 
						value='${endTime}' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'});">
				</td>


				
     
    </tr>  
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="paymentDownList();" name="submitBtn" value="下  载" class="button2">
      </td>
    </tr>
  </table>
 </form>

 <script type="text/javascript">

	  function paymentDownList(){
		document.mainfromId.action="shareDownload.htm?method=shareRefundDownloadSubmit";
		document.mainfromId.submit();
	}
 		
 		
 		
	
	</script>
