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
			url : "./positionAllocaAudit.do?method=list",
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
 
<h2 class='panel_title'>头寸调拨审核</h2>
<h2><font color="red">${error}</font></h2>
<form  method="post" name="mainfrom" id="mainfrom" >
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      	<td align="center" class="border_top_right4">调拨流水号： <input
				type="text" onkeyup="checkNum(this);" name="allotSequence">
			</td>
			<td class="border_top_right4" colspan="2">申请时间：
		      	<input class="Wdate" type="text" id="beginCreateDate" name="beginCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
		      	至
		      	<input class="Wdate" type="text" id="endCreateDate" name="endCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
      		</td> 
      		<td align="center" class="border_top_right4">审核状态： 
      				<select name="status" id ="status">
      						<option value="">--请选择--</option>
      						<option value="0">待审核</option>
      						<option value="1">审核通过</option>
      						<option value="2">审核拒绝</option>
      				</select>
			</td>
	     </tr>
		   <tr class="trForContent1">
				<td class=border_top_right4 colspan="4" align="center">
					<input style="width: 100px;" type="button" value="查询" id="btn" onclick="query();"/>
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