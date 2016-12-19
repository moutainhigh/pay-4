<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">域名管理</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      <td align="right" class="border_top_right4" >域名：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="siteId" name="siteId">
	      </td>
	      <td align="right" class="border_top_right4" >状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="status">
	      		<option value="">请选择</option>
	      		<option value='0'>冻结</option>
				<option value='1'>正常</option>
				<option value='2'>待审核</option>
				<option value='3'>审核未通过</option>
				<option value='4'>已删除</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit" value="添加" class="button2" onclick="toAdd();">
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

	/* $(document).ready(function(){
		search();
	});  */
	
	function toAdd(){
		window.location.href="${ctx}/crosspay/siteset.do?method=toAdd";
	}
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/crosspay/siteset.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>