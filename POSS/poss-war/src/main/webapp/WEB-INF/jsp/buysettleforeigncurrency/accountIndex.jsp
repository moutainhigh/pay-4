<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
		query();
});
	
	function query(pageNo, totalCount){
		var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
		+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./accountDreCheck.do?method=list",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
</script>
<!--  <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center">
				<font class="titletext">企业账户开通审核</font>
		</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">企业账户开通审核</h2>
<h3><font color="red">${error}</font></h3>
<form  method="post" name="mainfrom" id="mainfrom" >
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      	<td align="center" class="border_top_right4">会员号： <input
				type="text" onkeyup="checkNum(this);" name="partnerId">
			</td>
			<td class="border_top_right4" colspan="2">申请账户用途：
				<select name="reason" id="reason">
						<option value="">--请选择--</option>
						<option value="购结汇账户开通">购结汇账户开通</option>
						<option value="收单结算账户开通">收单结算账户开通</option>
				</select>
      		</td> 
      			<td class="border_top_right4" colspan="2">申请状态：
				<select name="status" id='status'  > 
						<option value="">--请选择--</option>
						<option value="0"  <c:if test="${status == '0'}">selected</c:if> >待处理</option>
						<option value="1" <c:if test="${status == '1'}">selected</c:if> >申请通过</option>
						<option value="2" <c:if test="${status == '2'}">selected</c:if> >申请拒绝</option>
				</select>
      		</td>
      		</tr>
      		<tr class="trForContent1">
				<td class=border_top_right4 colspan="6" align="center">
					<input  class="button2" type="button" value="查询" id="btn" onclick="query();"/>
				
					<input  class="button2" type="button" value="批量通过" id="btn" onclick="bacthReviewed('1');"/>
				
					<input  class="button2" type="button" value="批量拒绝" id="btn" onclick="bacthReviewed('2');"/>
				</td>
			</tr>
	  </table> 
</form>
 <div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>