<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">日利率维护</h2>
<script type="text/javascript">
	$(function(){
		<c:if test="${not empty errorMsg }">
			alert('${errorMsg}')
		</c:if>
	})
</script>

<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1" >
	      <td align="right" class="border_top_right4">会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
     	  <td align="right" class="border_top_right4" width="200">商户名称：</td>
		  <td class="border_top_right4" colspan="6" width="200" align="left">
			<input type="text" name="partnerName" id="partnerName" />	
		  </td>
	     </tr>
	    <tr class="trForContent1" >
	      <td align="right" class="border_top_right4">更新时间：</td>
	      <td class="border_top_right4">
	      		<input class="Wdate" type="text" id="startDate" name="startTime"
				value='<fmt:formatDate value="${startTime}" type="both"/>'
				style="width: 150px;"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});">
				至
				<input class="Wdate" type="text" id="endTime" name="endTime"
				value='<fmt:formatDate value="${endTime}" type="both"/>'
				style="width: 150px;"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});">
	      </td>
	      <td class="border_top_right4" colspan="2"></td>
	     </tr>
	   
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="button" onclick="javascript:add();"  name="butSubmit" value="新增" class="button2">
	      <input type="button" onclick="javascript:search();"  name="butSubmit" value="查询" class="button2">
	      <input type="button" onclick="javascript:searchHistory();"  name="butSubmit" value="历史利率查询" class="button2">
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


<div id="addDayRateDiv" style="display: none">
	<form action="./orderCreditDayRate.do"
		id="addDayRateForm">
		<input type="hidden" name="method" value="dayRateCreate">
		<br>
		<br>
		<table id="addDayRateTable" class="tablesorter" border="0" cellpadding="0" cellspacing="0" >
		
		</table>
		<br><br> 
		<input type="button" onclick="addDayRate();" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="cancelAdd();" value="取消">&nbsp;&nbsp;&nbsp;
	</form>
</div>


<script type="text/javascript">
	 $(document).ready(function() {
		search();
	}); 

	function search(pageNo, totalCount) {
		//$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./orderCreditDayRate.do?method=dayRateQuery",
			data : pars,
			success : function(result) {
				$('#resultListDiv').html(result);
			}
		});
		
	}
	function searchHistory(pageNo, totalCount) {
		//$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount + "&type=history";
		$.ajax({
			type : "POST",
			url : "./orderCreditDayRate.do?method=dayRateQuery",
			data : pars,
			success : function(result) {
				$('#resultListDiv').html(result);
			}
		});
		
	}
	
	//新增
	function add() {
		$('#addDayRateTable').empty();
		var dayRate = $("#dayRate").html();
		//onkeyup='checkNum(this);'
		$("#addDayRateTable").append("<tr class='trForContent1'><td align='left'>会员号:&nbsp;<input name='partnerId' id='_partnerId' ></td></tr>"
			+"<tr class='trForContent1'><td align='left'>日利率:&nbsp;<input name='dayRate' id='dayRate' onkeyup='checkNum(this);' onblur='decimals(this)'>&nbsp;%</td></tr>");
		$('#addDayRateDiv').dialog({
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
	
	
	function cancelAdd() {
		$('#addDayRateTable').empty();
		$('#addDayRateDiv').dialog("close");
	}
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
  
	function addDayRate(){
		var __partnerId = $("#_partnerId").val() ;
		var _dayRate = $("#dayRate").val() ;
		if(null == __partnerId || "" == __partnerId){
			alert("会员号不能为空！") ;
			return false ;
		}
		if(null == _dayRate || "" == _dayRate){
			alert("日利率不能为空！") ;
			return false ;
		}
		
		if(!isWantNum(_dayRate)){
			alert("日利率输入不合法！") ;
			return false ;
		}
		if(0 == _dayRate || 100 <= _dayRate){
			alert("日利率必须大于0且小于100！") ;
			return false; 
		}
		$("#addDayRateForm").submit();
	}
	
	function isWantNum(s){
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
				$("#addDayRateTable tr td input[name=dayRate]").attr("value","");
			}else if(index){
				$("#addDayRateTable tr td input[name=dayRate]").attr("value",dayRate.substr(index));
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
				$("#addDayRateTable tr td input[name=dayRate]").attr("value",left+"."+right);
			}else if(zeroCount>0){
				left=left.substr(zeroCount);
				dayRate=left+"."+right;
				$("#addDayRateTable tr td input[name=dayRate]").attr("value",left+"."+right);
			}
			if(right.length>5){
				dayRate=dayRate.substring(0,dayRate.indexOf(".")+6);
				$("#addDayRateTable tr td input[name=dayRate]").attr("value",dayRate);
			}
		}
	}
</script>


