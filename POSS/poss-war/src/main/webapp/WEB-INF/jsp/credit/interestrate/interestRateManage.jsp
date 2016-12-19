<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<br />
<br />
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">日利率维护</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br />
<br />
<h3>
	<font color="red">${error}</font>
</h3>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0"
	cellspacing="1" align="center"
	style="float: left; width: 50%; float: left; width: 50%; text-align: center; margin-left: 450px">
	<thead>
		<tr align="center">
			<th class="item15">当前日利率</th>
			<th class="item10">更新时间</th>
			<th class="item10">操作</th>
		</tr>
	</thead>
	<tbody>
		<tr class="even" align="center">
			<td id="dayRate">${rate.dayRate}</td>
			<td id="updateTime">${rate.updateTime}</td>
			<td><a href="#" onclick="update();">修改</a></td>
		</tr>
</table>
<br>
<br>
<br>
<br>
<br>
<br>
<form action="./interestRate.do" id="sorterForm">
	时间: &nbsp;&nbsp;<input class="Wdate" type="text" name="startTime"
		id="startTime"
		value='<fmt:formatDate value="${webQueryFIReportDTO.startTime}" type="date"/>'
		onClick="WdatePicker()"> <input type="hidden" name="method"
		value="searchRate"> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
	&nbsp;&nbsp; <input type="button" onclick="search();" value="查询"><br>
</form>
<br>
<c:if test="${!empty dayRateDTO}">
		 	历史日利率 ： &nbsp;&nbsp;<span>${dayRateDTO.dayRate}</span>
</c:if>
</tbody>
<br />
<br />
<div id="editDayRateDiv" style="display: none">
	<form action="./orderCreidtDayRate.do"
		id="editDayRateForm">
		<input type="hidden" name="method" value="editDayRate">
		<input type="hidden" name="id" value="${rate.rateId}">
		<br>
		<br>
		<table id="editDayRateTable" class="tablesorter" border="0"
			cellpadding="0" cellspacing="0" align="center"
			style="float: left; width: 50%; float: left; width: 50%; text-align: center; margin-left: 150px">
		
		</table>
		<br> <br> <br> <br>		<br> <br> <br> <br> 
		<input type="button" onclick="editDayRate();" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="cancel();" value="取消">&nbsp;&nbsp;&nbsp;
	</form>
</div>
<script type="text/javascript">
	function search() {
		var startTime = $("#startTime").val();
		if (startTime == null || startTime.length == 0) {
			alert("请选择时间！！");
			return;
		}
		$("#sorterForm").submit();
	}
	function update() {
		$('#editDayRateTable').empty();
		var updateTime = $("#updateTime").html();
		var dayRate = $("#dayRate").html();
		$("#editDayRateTable").append("<tr><td>当前日利率:&nbsp;<input name='dayRate' onkeyup='checkNum(this);' onblur='decimals(this)' value="+dayRate+"></td></tr>");
	/* 	$("#editDayRateTable").append("<tr><td> 更新时间:&nbsp;<input  class='Wdate' type='text' id='endDate' name='updateTime' value='"+updateTime+"' style='width: 150px;'"+
		"onClick='WdatePicker({dateFmt:'yyyy-mm-dd hh24:mi:ss.ff',minDate:'#F{$dp.$D(\'startTime\')}'}></td></tr>");
	 */	$('#editDayRateDiv').dialog({
			position : "top",
			width : 450,
			modal : true,
			title : '日率利修改',
			height : 300,
			overlay : {
				opacity : 0.5,
				background : "black",
				overflow : 'auto'
			}    
		});
	}

	function cancel() {
		$('#editDayRateTable').empty();
		$('#editDayRateDiv').dialog("close");
	}
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
  
	function editDayRate(){
		
		$("#editDayRateForm").submit();
	}
	
	function decimals(obj){
		var dayRate=obj.value;//获取利率
		if(dayRate && dayRate.indexOf(".")==-1){
			var index;
			var zeroCount=0; 
			for(var i=0;i<dayRate.length;i++){
				if(dayRate.charAt(i)!="0"){
					index=i;
					break;
				}else{
					zeroCount++;	
				}
			}
			if(zeroCount==dayRate.length){
				$("#editDayRateTable tr td input[name=dayRate]").attr("value","");
			}else if(index){
				$("#editDayRateTable tr td input[name=dayRate]").attr("value",dayRate.substr(index));
			}
		}else if(dayRate && dayRate.indexOf(".")>-1){
			var left=dayRate.split(".")[0];
			var right=dayRate.split(".")[1];
			var zeroCount=0;  
			for(var i=0;i<left.length;i++){
				if(left.charAt(i)=="0"){
					zeroCount++;
				}else{
					break;
				}
			}
			if(zeroCount==left.length){
				left=left.substr(zeroCount-1);
				dayRate=left+"."+right;
				$("#editDayRateTable tr td input[name=dayRate]").attr("value",left+"."+right);
			}else if(zeroCount>0){
				left=left.substr(zeroCount);
				dayRate=left+"."+right;
				$("#editDayRateTable tr td input[name=dayRate]").attr("value",left+"."+right);
			}
			if(right.length>5){
				dayRate=dayRate.substring(0,dayRate.indexOf(".")+6);
				$("#editDayRateTable tr td input[name=dayRate]").attr("value",dayRate);
			}
		}
	}

</script>















