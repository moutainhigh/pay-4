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
			url : "./buySettleOrderQuery.do?method=list",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
					/* $(".createDate").each(function(index,ele){
						$(".createDate").html(getFormatDateByLong(	$(this).text(),'yyyy-MM-dd hh:mm:ss'));
					}); */
			/* 		$.each($(".createDate"),function(){
						alert($(this).html())
						alert(new Date($(this).html()));
						//alert(getFormatDateByLong());
					}) */
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
<h2 class="panel_title">购结汇查询</h2>
<h2><font color="red">${error}</font></h2>
<form  method="post" name="mainfrom" id="mainfrom" >
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      	<td align="center" class="border_top_right4">会员号： <input
				type="text" onkeyup="checkNum(this);" name="partnerId">
			</td>
	      	<td align="center" class="border_top_right4">购结汇流水号： <input
				type="text" onkeyup="checkNum(this);" name="exchangeNo">
			</td>
      		<td align="center" class="border_top_right4">交易类型： 
      				<select name="type" id ="type">
      						<option value="">--请选择--</option>
      						<option value="0">购汇</option>
      						<option value="1">结汇</option>
      				</select>
			</td>
	     </tr>
		   <tr class="trForContent1">
				<td class=border_top_right4 colspan="4" align="center">
					<input type="button" value="查询" id="btn" onclick="query();"/>
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