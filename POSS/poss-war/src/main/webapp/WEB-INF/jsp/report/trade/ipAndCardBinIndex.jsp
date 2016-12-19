<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>IP与卡BIN匹配报表</title>
<script type="text/javascript">


	function search(pageNo,totalCount,pageSize) {
		var memberCode = $("#memberCode").val();
		if(memberCode == ''){
			alert("请输入会员号！！！");
			return ;
		}
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#form1").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/ipAndCardBinMatchRep.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	function downLoad(){
		var pars = $("#form1").serialize();
		window.location.href = '${ctx}/ipAndCardBinMatchRep.do?method=download&'+pars
	}
</script>
</head>

<body>
	<h2 class="panel_title">IP与卡BIN匹配报表</h2>
<font color="red">强烈建议输入会员号查询</font>
<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	  <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="memberCode" name="memberCode">
	      </td>
	      <td align="right" class="border_top_right4">交易日期：</td>
	      	<td class="border_top_right4">
		      	<input class="Wdate" type="text" id="startTime" name="startTime" value="${dateStr}"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
		        	～
				<input class="Wdate" type="text" id="endTime" name="endTime"  value="${dateStr}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	      	</td>
	      <td align="right" class="border_top_right4" >卡种：</td>
	      <td class="border_top_right4">
	     		<select name="cardType" id="cardType" style="width: 137px;height: 20px">
	     				<option value='ALL'>----全部----</option>
	     				<option value='VISA'>VISA</option>
	     				<option value='MASTER'>MASTERCARD</option>
	     				<option value='JCB'>JCB</option>
	     				<option value='AE'>AE</option>
	     		</select>
	      </td>
	      <td align="right" class="border_top_right4" >IPAY/GC：</td>
	      <td class="border_top_right4">
	      	   	<select name="merchantType" id="merchantType" style="width: 137px;height: 20px">
	     				<option value='ALL'>----全部----</option>
	     					<option value='IPAY'>IPAY</option>
	     				<option value='GC'>GC</option>
	     		</select>
	      </td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="10">
		      <input type="button" onclick="search();" name="submitBtn" value="查  询" class="button2">
		       <input type="button" onclick="downLoad();" name="submitB" value="下 载" class="button2">
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
<p>&nbsp;</p>
</body>
</html>
