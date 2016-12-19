<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo, totalCount) {
	//$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount;
	$.ajax({
		type : "POST",
		url : "./orderCreditDayRate.do?method=dayRateQuery",
		data : pars,
		success : function(result) {
			//	$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
	
}
</script>
<div id="editDayRateDiv" style="display: none">
	<form action="./orderCreditDayRate.do"
		id="editDayRateForm22">
		<input type="hidden" name="method" value="dayRateUpdate">
		<input type="hidden" name="partnerId" id="f_partnerId" />
		<input type="hidden" name="id" id="id"/>
		<br> 
		<br>
		<table id="editDayRateTable" class="tablesorter" border="0" cellpadding="0" cellspacing="0" >
		
		</table>
		<br><br> 
		<input type="button" onclick="editDayRate();" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="cancel();" value="取消">&nbsp;&nbsp;&nbsp;
	</form>
</div>
<form action="" method="post" name="listFrom">
</form>
<center>
<table  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th class="item15"><input type="checkbox" onclick="cheked();" id="check" /></th>
			<th class="item15">会员号</th>
			<th class="item15">商户名称</th>
			<th class="item15">日利率</th>
			<th class="item15">更新时间</th>
			<th class="item15">操作员</th>
			<th class="item10">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${dayRates}" var="dayRate" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><c:if test="${dayRate.partnerId !='0' }"><input type="checkbox" value="${dayRate.id }"/></c:if></td>
				<td align="center">${dayRate.partnerId}</td>
				<td align="center">${dayRate.partnerName}</td>
				<td align="center">${dayRate.rate}</td>
				<td align="center">${dayRate.updateDateStr}</td>
				<td align="center">${dayRate.operator }</td>
				<td align="center">
					<a href="javascript:edit('${dayRate.id }', '${dayRate.partnerId}')">修改</a>				
				</td>
			</tr>
		</c:forEach>
		<tr class="trForContent1">
			<td colspan="13" align="center"><input type="button" value="批量删除" onclick="bacthDelete() ;" /></td>
		</tr>
		
	</tbody>
	
</table>
</center>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">
	function cheked(){
		 if($("#check").attr("checked")==true){
			 $("input[type='checkbox']").each(function(){
				 this.checked=true;
			 })
		 }else{
			 $("input[type='checkbox']").each(function(){
				 this.checked=false;
			 })
		 }
	}
	
	function bacthDelete(){
		var ids="";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					ids+=$(this).val()+",";							
				}
			};
		 })
		 if(ids==''){
			alert("请选择要删除项！！！");
			return ;	
		}
		if (!confirm("确认删除?")) {
			return;
		} 
		
		 $.ajax({
			type : "POST",
			url : "./orderCreditDayRate.do?method=dayRatedelete",
			data : "ids="+ids,
			success : function(result) {
				alert(result)
				//$("#bouncedForm").submit();
				search() ;
		}
	});
}
	
	
	//新增
	function edit(id, partnerId) {
		$('#editDayRateTable').empty();
		var dayRate = $("#dayRate").html();
		//onkeyup='checkNum(this);'
		$("#editDayRateTable").append("<tr><td>日利率:&nbsp;<input name='dayRate' id='___dayRate' onkeyup='checkNum(this);' onblur='decimals(this)'>&nbsp;%</td></tr>");
		$("#f_partnerId").val(partnerId) ;
		$("#id").val(id) ;
		$('#editDayRateDiv').dialog({
			position : "top",
			width : 450,
			modal : true,
			title : '新增日利率配置',
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
		var ___dayRate = $("#___dayRate").val() ;
		if(null == ___dayRate || "" == ___dayRate){
			alert("日利率不能为空！") ;
			return false ;
		}
		if(!isWantNumForEdit(___dayRate)){
			alert("日利率输入不合法！") ;
			return false ;
		}
		if(0 == ___dayRate || 100 <= ___dayRate){
			alert("日利率必须大于0且小于100！") ;
			return false; 
		}
		$("#editDayRateForm22").submit();
	}
	
	function isWantNumForEdit(s){
	    //s = trim(s);
	    var p =/^[1-9](\d+(\.\d{1,3})?)?$/; 
	    var p1=/^[0-9](\.\d{1,3})?$/;
	    return p.test(s) || p1.test(s);
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