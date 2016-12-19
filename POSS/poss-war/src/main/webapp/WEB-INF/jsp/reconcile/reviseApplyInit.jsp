<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 

<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/bankChange.js"></script>

<body onload="bankSelectInit('bankCode','所有');bankCodeSelectInit('providerCodeId','所有');">
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">手工调账申请</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainfrom">
 <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >选择交易日期：</td>
      <td class="border_top_right4" colspan="3" >
      	<input class="Wdate" type="text" name="startTime"  id="startTime" value='<fmt:formatDate value="${webQueryReconcileDTO.startTime}" type="date"/>'    onClick="WdatePicker()">
       	 ～
		<input class="Wdate" type="text" name="endTime" id="endTime"  value='<fmt:formatDate value="${webQueryReconcileDTO.endTime}" type="date"/>'  onClick="WdatePicker()">
      </td>
    </tr>
  
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >选择银行：</td>
      <td class="border_top_right4" >
 			<select name="bankCode" id="bankCode" >
	        </select>
      </td>
      
      <td class=border_top_right4 align="right" >服务代码：</td>
      <td class="border_top_right4"  align="left"  >
        	<select id="providerCodeId" name="providerCode" >
       		</select>
      </td>
      
    </tr>
    
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >调账状态：</td>
      <td class="border_top_right4" >
        	<select id="adjustStatusId" name="adjustStatus" >
        			<option value="1">未申请</option>
        			<!-- <option value="2">待审核</option>-->
        			<option value="3">审核通过</option> 
        			<option value="4">审核退回</option>
        	</select>
      </td>
      
      <td class=border_top_right4 align="right" >错账方：</td>
      <td class="border_top_right4" align="left" >
        	<select id="busiFlagId" name="busiFlag" >
        			<option value="" selected="selected">所有</option>
        			<option value="200">银行</option>
        			<option value="210">系统</option>
        			<!--<option value="220">金额不匹配</option>-->
        	</select>
      </td>
      
    </tr>
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >银行订单号：</td>
      <td class="border_top_right4" >
	        <input type="text"  name="accountSeq" value="${webQueryReconcileDTO.accountSeq }"  />
      </td>
      
         <td align="center" class="border_top_right4" colspan="2">
     	 <a class="s03" href="#" onclick="queryReviseReconcileAudit();"><img
			src="./images/query.jpg" border="0"> </a>
      </td>
    </tr>
  </table>
 </form>
   <div id="resultListDiv" class="listFence"></div>
 
  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div> 
</body>

<script type="text/javascript">

   //$(document).ready(function(){queryReviseReconcileAudit();}); 

	function queryReviseReconcileAudit(pageNo,totalCount) {
		var start =  $('#startTime').val() ;
		var end = $('#endTime').val() ;
		if ( !validDate ( start ,end )) {
			alert ( "开始日期不能大于结束日期" ) ;
			return false ;
		}
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfrom").serialize()  + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
			$.ajax({
				type: "POST",
				//url:  "reconcile.reviseReconcile.do?method=queryReviseReconcile" ,
				url:  "reconcile.reviseApply.do?method=queryReviseApplyList" ,
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	function changeBankInfo(obj){
		if(obj.value == 'UnionPay'){
			$("#providerId").hide();
			$("#providerId").val('002');
		}else{
			$("#providerId").show();
			$("#providerId").val('001');
		}
	}
</script>