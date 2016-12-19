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
			url : "./payFileDownload.do?method=list",
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
<h2 class="panel_title">付款文件下载</h2>
<h3><font color="red">${error}</font></h3>
<form  method="post" name="mainfrom" id="mainfrom" >
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      	<td align="center" class="border_top_right4">会员号：
	      	 <input	type="text" onkeyup="checkNum(this);" name="partnerId">
			</td>
			<td class="border_top_right4" colspan="2">批次号：
				<input	type="text"  name="batchNo">
      		</td> 
      			<td class="border_top_right4" colspan="2">业务类型：
				<select name="type" id='type'  > 
						<option value="">--请选择--</option>
						<option value="1"  <c:if test="${type == '1'}">selected</c:if> >货物贸易</option>
						<option value="2" <c:if test="${type== '2'}">selected</c:if> >机票旅游</option>
						<option value="3" <c:if test="${type== '3'}">selected</c:if> >酒店住宿</option>
						<option value="4" <c:if test="${type== '4'}">selected</c:if> >留学交易</option>
				</select>
      		</td>
      		</tr>
	    <tr class="trForContent1">
	      	<td align="center" class="border_top_right4">创建时间：
	      	 		<input class="Wdate" type="text" id="beginCreateDate" name="beginCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	      				至
	      			<input class="Wdate" type="text" id="endCreateDate" name="endCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
			</td>
      			<td class="border_top_right4" colspan="4">状态：
				<select name="status" id='status'  > 
						<option value="">--请选择--</option>
						<option value="0"  <c:if test="${status == '0'}">selected</c:if> >处理中</option>
						<option value="1" <c:if test="${status == '1'}">selected</c:if> >处理成功</option>
						<option value="2" <c:if test="${status == '2'}">selected</c:if> >处理失败</option>
						<option value="2" <c:if test="${status == '3'}">selected</c:if> >部分成功</option>
				</select>
      		</td>
      		</tr>
      		<tr class="trForContent1">
					<td class=border_top_right4 colspan="4" align="center">
							<input class="button2" type="button" value="查询" id="btn" onclick="query();"/>
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