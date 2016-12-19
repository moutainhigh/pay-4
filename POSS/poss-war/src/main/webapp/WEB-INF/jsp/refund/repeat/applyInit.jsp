<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">重复支付订单查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainfromId" name="mainfrom"
		onkeydown="javascript:if(event.keyCode == 13){document.all.submitBtn.focus();document.all.submitBtn.click();}">
  <input type="hidden" name="rechargeStatus" value="1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">选择交易日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startTime"  name="startTime" value='<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>'  onClick="WdatePicker({minDate:'%y-%M-{%d-720}',maxDate:'#F{$dp.$D(\'endTime\')}'})">
	        	～
			<input class="Wdate" type="text" id="endTime" name="endTime"  value='<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>' onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})">
      	</td>
      	<td class=border_top_right4 align="right" >登录名：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="userId" value="${webQueryRefundDTO.userId }"  />
      	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >会员号：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="account" value="${webQueryRefundDTO.account }"  />
      	</td>
      	<td class=border_top_right4 align="right" >会员类型：</td>
      	<td class="border_top_right4" >
        	<select name="memberType" >
        			<option value="">全部</option>
        			<option value="1">个人</option>
        			<option value="2">企业</option>
        	</select>
      	</td>
    </tr>
    <tr class="trForContent1">
      	<td class=border_top_right4 align="right" >账户类型：</td>
      	<td class="border_top_right4" >
        	<select name="accountType" >
        			<option value="">全部</option>
        			<option value="10">人民币</option>
        	</select>
      	</td>
      	<td class=border_top_right4 align="right" >商户订单号：</td>
       	<td class="border_top_right4" >
        	<input type="text"  name="merchantOrderId" value="${webQueryRefundDTO.merchantOrderId }"  />
     	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="submitByHref();" name="submitBtn" value="查  询" class="button2">
      </td>
    </tr>
  </table>
 </form>
<!--<font color="red"><b>${err }</b></font>-->
<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">
	showMsg();
	function showMsg(){
		var errorMsg = '${err}';
		errorMsg = '<font color="red"><b>'+errorMsg+'</b></font>';
		$('#resultListDiv').html(errorMsg);
	}
	
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
				url: "refund.manage.do?method=queryRepeatList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	//验证日期
	function validDate(strDate1,strDate2){
		if(null != strDate1 && null != strDate2 &&
				0 != strDate1.length && 0 != strDate2.length){
			var date1 = new Date(strDate1.replace("-","/"));
			var date2 = new Date(strDate2.replace("-","/"));
			var now = new Date();
			if(date1 > date2){
				return false;
			}
		}
		return true;
	}
</script>