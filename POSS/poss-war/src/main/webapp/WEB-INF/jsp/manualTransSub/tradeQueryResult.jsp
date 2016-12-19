<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">交易查询</h2>
<form action="manualTransSub.do" method="post" id="mainfromId" name="mainfrom" enctype="multipart/form-data">
	<input type="hidden" name="method" value="uploadCardInfo">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
		<td class=border_top_right4 align="right" >会员号：</td>
      		<td class="border_top_right4" >
        	<input type="text" name="partnerId"/>
      	</td>
      	<td align="right" class="border_top_right4">批次号：</td>
      	<td class="border_top_right4">
			<input type="text" name="batchNum" value="${batchNumber}">
      	 </td>
      	<td class=border_top_right4 align="right" >交易状态：</td>
      		<td class="border_top_right4" >
        	<select  name="tradeStatus" id="tradeStatus" style="width:132px; height:20px;">
	      				<option value="">--请选择--</option>
						<option value="4,3">交易成功</option>
						<option value="5">交易失败</option>
	      	</select>
      	</td>
    </tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right" >系统交易号：</td>
      		<td class="border_top_right4" >
        	<input type="text" name="tradeOrderNo"/>
      	</td>
      			<td class=border_top_right4 align="right" >交易创建时间：</td>
		<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startTime"  name="startTime"  onClick="WdatePicker({minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'endTime\')}'})">
	        	～
			<input class="Wdate" type="text" id="endTime" name="endTime"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})">
   		<td class="border_top_right4" colspan="2"> 
   		</td>    
    </tr>
 <tr class="trForContent1">
      	<td class=border_top_right4 colspan="6" align="center">
				<input type="button"  value=" 查 询 " onclick="find();">   &nbsp;
				   	<input type="button"  value=" 返 回" onclick="window.history.back(-1);"> 
      	</td>
    </tr>
  </table>
 </form>
 <div id="resultListDiv" class="listFence"></div>
 <script type="text/javascript">
	$(document).ready(function() {
		find();
	});
	function find(pageNo,totalCount,pageSize) {
		var pars = $("#mainfromId").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "${ctx}/manualTransSub.do?method=tradeQueryResult",
			data: pars,
			success: function(result) {
				$('#resultListDiv').html(result);
			}
		});
	}
 </script>