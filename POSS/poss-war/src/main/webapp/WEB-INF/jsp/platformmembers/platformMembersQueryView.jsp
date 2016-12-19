<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">平台会员号绑定管理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">平台会员号绑定管理</h2>

<form action="platformMembersQuery.do?method=findByCriteria" method="post" id="findByCriteriaForm" name="findByCriteriaForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<thead>
	<tr class="trForContent1">
      	<th align="right" class="border_top_right4">平台会员号：</th>
      	<th class="border_top_right4">
      	      	<input type="text" onkeyup="checkNum(this);" id="fatherMemberCode" name="fatherMemberCode"/>
      	</th>
      	<th class=border_top_right4 align="right" >会员号：</th>
      	<th class="border_top_right4" >
        	<input type="text" id="sonMemberCode" name="sonMemberCode" />
      	</th>
      	<th class=border_top_right4 align="right" >审核状态：</th>
      	<th class="border_top_right4" >
        	<select name="status" id="status">
        		<option value="">——请选择——</option>
        		<option value="1">待审核</option>
        		<option value="2">已审核</option>
        		<option value="3">已拒绝</option>
        		<option value="4">已解绑</option>
        	</select>
      	</th>
    </tr>
    </thead>
	<tr class="trForContent1">
      	<td align="center" class="border_top_right4" colspan="6">
		      <input type="hidden" name="method" value="manualTransSub">
		      <input type="button"  name="submitBtn" onclick="submitfindByCriteriaForm();" value="查询" class="button2">&nbsp;&nbsp;
      	 </td>
      </tr>
    </tr>
  </table>
 </form>
 

 <div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<script type="text/javascript">
function submitfindByCriteriaForm(){
	$("#findByCriteriaForm").submit();
}
function checkNum(obj) {
	//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
/* $(document).ready(function() {
	query();
});
function query(pageNo,totalCount,pageSize) {
	var pars = $("#mainfrom").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	//alert(pars);
	$.ajax({
		type: "POST",
		url: "${ctx}/tokenpay.do?method=queryManualTran",
		data: pars,
		success: function(result) {
			$('#resultListDiv').html(result);
		}
	});
} */
</script>