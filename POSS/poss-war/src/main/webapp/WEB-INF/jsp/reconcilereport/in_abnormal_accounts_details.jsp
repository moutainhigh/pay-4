<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function showChannle(){

		var $option = $("#org option:selected");/**获取双击选中的对象*/

		if($option.val() == null || $option.val() == ''){
			return false;
		}
		
		$.get("reconcile.report.do",{ method:'queryChannelStrHTML',orgCode:$option.val() },
			function (data){
			
				var options = [];/**存储channel下拉框value的数组*/
				var $opts = $("#channel>option");/**获取channel下所有option的值,并push到options数组*/
				for(op in $opts){
					options.push($opts[op].value);
				}
				
				var json = eval("("+data+")");

				/**循环append到channel下拉框的option对象*/
				for(var ind = 0; ind<json.length;ind++){
					var obj = json[ind];

					/**如果obj.alias在option中不存在*/
					if($.inArray(obj.alias, options) == -1){
						$("#channel").append("<option value="+obj.alias+">"+obj.itemName+"</option>");
					}
				}
			}
		);
	}


	function queryInAbnormalAccountsDetails(){
		
		$options = $("#channel option:selected");

		var str = "";
		
		for( var ind = 0; ind < $options.length; ind++){
			if(ind > 0){
				str += ",";
			} 
			str += $options[ind].value;
		}

		if(str == '' || str.length < 1){
			alert("请选择网关");
			return;
		}

		window.location.href = "reconcile.report.do?method=queryInAbnormalAccountsDetails&channels="+str+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&reason="+$("#reason").val()+"&dateTemp="+new Date();
	}

	
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">入款挂账明细表</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="inAbnormalAccountsDetailsForm"  action="">
	<table width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr>
			<td>
				时间
				<input class="Wdate" type="text" id="startDate" name="startDate" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})"> -
 				<input class="Wdate" type="text" id="endDate" name="endDate" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})">
			</td>
			<td>
			</td>
		</tr>
		<tr><td>&nbsp;</td><td></td></tr>
		<tr><td>银行列表：<font color="red">（双击银行添加下属网关到网关列表）</font></td><td>网关列表：<font color="red">（支持多选查询）</font></td></tr>
		<tr>
			<td>
				<select id="org" name="org" multiple="multiple" style="width:300px; height:300px;" ondblclick="showChannle()">
					<c:forEach var="o" items="${ list }">
						<option value="${ o.orgCode }">${ o.orgName }</option>
					</c:forEach>
				</select>
			</td>
			
			<td rowspan="2">
				<select id="channel" name="channel" multiple="multiple" style="width:300px; height:300px;"></select>
			</td>
		</tr>
		<tr>
			<td>
				<select id="reason" name="reason" style="width:100px;">
					<option value="">全部</option>
					<option value="1">错账</option>
					<option value="2">单边账</option>
					<option value="3">其它</option>
				</select>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				<input type="button" id="selectBtn" name="selectBtn" value="查询" style="width:100px;" onclick="queryInAbnormalAccountsDetails()">
			</td>
			<td></td>
		</tr>
	</table>
  
  <c:if test="${not empty msg }"> 
  	<li style="color: red"><c:out value="${msg}" /> </li>
  </c:if>

</form>
