<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">高频交易黑名单</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">高频交易黑名单</h2>
<c:if test="${not empty msg}">
		<font color="red">${msg}</font>
</c:if>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="memberCode" name="memberCode">
	      </td>
	      <td align="right" class="border_top_right4" >业务类型：</td>
	      <td class="border_top_right4">
	      	<select id="businessTypeId" name="businessTypeId">
	      			      		<option value=''>全部</option>
	      			      		<option value='1'>卡号</option>
	      			      		<option value='2'>邮箱</option>
	      			      		<option value='3'>IP</option>
	      			      		<option value='8'>收货地址</option>
	      			      		<option value='9'>账单地址</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="status">
	      		<option value='0'>未处理</option>
				<option value='1'>已交付审核</option>
				<option value='2'>已处理</option>
	      	</select>
	      </td>
	     </tr>
	     <tr class="trForContent1">
	          <td align="right" class="border_top_right4">选择交易日期：</td>
      		<td class="border_top_right4" colspan="6">
		      		<input class="Wdate" type="text" id="startTime"  name="startTime" value=''' onClick="WdatePicker()">
		        	～
					<input class="Wdate" type="text" id="endTime" name="endTime"  value='' onClick="WdatePicker()">
      		</td>
      	</tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="2">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="search();">
	      </td>
	      <td align="center" class="border_top_right4" colspan="2">
	      <input type="button"  name="butSubmit" value="加入黑名单" class="button2" onclick="dispose();">
	      </td>
	      <td align="center" class="border_top_right4" colspan="2">
	      <input type="button"  name="butSubmit" value="致为已处理" class="button2" onclick="processed();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
<script type="text/javascript">

	 $(document).ready(function(){
		search();
	});  
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/blacklistCheck.do?method=query",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function dispose(){
		var ids="";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					ids+=$(this).val()+",";							
				}
			};
		 })
		if(ids==''){
			alert("请选择需要处理的黑名单！");
			return;
		}
		if (!confirm("确认加入黑名单？")) {
			return;
		 }
		 window.location.href="${ctx}/blacklistCheck.do?method=dispose&ids="+ids;
	}
	
	function processed(){
		var ids="";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					ids+=$(this).val()+",";							
				}
			};
		 })
		if(ids==''){
			alert("请选择需要处理的黑名单！");
			return;
		}
		 window.location.href="${ctx}/blacklistCheck.do?method=processed&ids="+ids;
	}
  </script>