<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">订单过滤规则配置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">订单过滤规则配置</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号</td>
	      <td class="border_top_right4"><input type="text"  name="memberCode"></td>
          <td align="right" class="border_top_right4" >创建日期：</td>
	      <td class="border_top_right4">
	      	  <input class="Wdate" type="text" id="startDate" name="startTime"  
	      	   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">至
	      	   <input class="Wdate" type="text" id="endDate" name="endTime"  
	      	   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="button" class="button2"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button" class="button2"  name="butSubmit" value="添  加" class="button2" onclick="toAdd();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseCode && responseCode == '0000'}">
	<font color="red"><b>操作成功！</b></font>	
</c:if>
<c:if test="${not empty responseCode && responseCode != '0000'}">
	<font color="red"><b>${responseDesc }</b></font>
</c:if>
<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   	 
<script type="text/javascript">  
    $(function(){
    	search();
    });
    
	function toAdd(){
		window.location.href="${ctx}/orderfilter/orderfilterRule.do?method=toAdd";
	}
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/orderfilter/orderfilterRule.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>