<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>算财务数据报表查询</title>
<script type="text/javascript">

		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}

		function validateQuery(){
			var startDate = document.getElementById("startDate").value;
			var endDate = document.getElementById("endDate").value;
			var businessType = document.getElementById("businessType").value;
			if(isEmpty(startDate)){
				alert("开始时间不能为空");
				return false;
			}
			if(isEmpty(endDate)){
				alert("结束时间不能为空");
				return false;
			}
			if(startDate > endDate){
				alert("开始时间必须小于结束时间");
				return false;
			}
			if(isEmpty(businessType)){
				alert("请选择业务类型");
				return false;
			}
			return true;
		}
		
		function userQuery(pageNo,totalCount,pageSize){
			if(!validateQuery()){
				return false;
			}
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/if_poss_query/queryReport.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});	       
        }

		function downLoad(pageNo,totalCount,pageSize){
			if(!validateQuery()){
				return false;
			}
			var pars = $("#form1").serialize();
			window.location="${ctx}/if_poss_query/queryReport.do?method=downloadRecordList&"+pars; 
        }

		//id的全选或全部取消
		function selectAll() {
			if($("#checkAll").attr("checked")){
				$("input[name='wkKey']").each(function(){
					this.checked = true;
				});
			} else {
				$("input[name='wkKey']").each(function() {
					this.checked = false;
				});
			}
			sunAmount();
		}
		//取消一个选择单位，去掉全选框的勾
		function selectAllcheckBoxunchecked(obj){
			var s = 0;
		  if(!obj.checked){
			  $("#checkAll").attr("checked",false);
		  }
		  sunAmount();
		}
		function sunAmount(){
			var s= 0;
			 $(":checkbox:checked").each(function(){
				  	s += parseInt($(this).val());
				});
			 changeDiv((s/1000).toFixed(2));
		}
		function changeDiv(s) {
			var amount =cc(s);
			$("#showSum").html(" 选中订单金额：" + amount);
		}
		function cc(s){
	        if(/[^0-9\.]/.test(s)) return "invalid value";
	        s=s.replace(/^(\d*)$/,"$1.");
	        s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	        s=s.replace(".",",");
	        var re=/(\d)(\d{3},)/;
	        while(re.test(s))
	                s=s.replace(re,"$1,$2");
	        s=s.replace(/,(\d\d)$/,".$1");
	        return s.replace(/^\./,"0.")
	    }
	    
</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">清算财务报表查询</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>



<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">交易日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value='<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>'  onClick="WdatePicker()">
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value='<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>' onClick="WdatePicker()">
      	</td>
      	
      	<td class=border_top_right4 align="right" >业务类型：</td>
      	<td class="border_top_right4" >
        	<select id="businessType" name="businessType" onChange="redirec(document.form1.businessType.options.selectedIndex)">
        			<option selected="selected">选择</option>
        			<option value="1">出款</option>
        			<option value="2">出款退票</option>
        			<option value="3">网关入款</option>
        			<option value="4">网关退款</option>
        	</select>
      	</td>
    </tr>
    <tr class="trForContent1">
      	<td class=border_top_right4 align="right" >订单状态：</td>
      	<td class="border_top_right4" >
        	<select name="orderStatus" >
       			<option selected="selected">选择所有</option>
        	</select>
      	</td>
	        <td class=border_top_right4 align="right" >
	        	渠道:
	        </td>
	        
	       	<td class="border_top_right4" >
       			<select name="channel" >
        			<option selected="selected">选择所有</option>
        	</select>
	       	</td>
     	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
       <input type="button" onclick="downLoad();" name="submitB" value="下 载" class="button2">
      </td>
    </tr>
  </table>
 </form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>

<script type="text/javascript">
	//获取一级菜单长度     
	var select1_len = document.form1.businessType.options.length;     
	var select2 = new Array(select1_len);     
	//把一级菜单都设为数组     
	for (i=0; i < select1_len; i++)     
	{ 
		select2[i] = new Array();
	}     
	//定义基本选项     
	select2[0][0] = new Option("选择所有", "");  
	
	select2[1][0] = new Option("选择所有", "");  
	select2[1][1] = new Option("初始状态", "100");     
	select2[1][2] = new Option("处理中", "101");
	select2[1][3] = new Option("申请失败", "102");
	select2[1][4] = new Option("处理成功", "111");
	select2[1][5] = new Option("处理失败", "112");   
	select2[1][6] = new Option("已经退票", "113");

	select2[2][0] = new Option("选择所有", "");
	select2[2][1] = new Option("退款处理中", "101");     
	select2[2][2] = new Option("退款成功", "111");     
	select2[2][3] = new Option("退款失败", "112");     

	select2[3][0] = new Option("选择所有", "");
	select2[3][1] = new Option("处理中", "0");     
	select2[3][2] = new Option("成功", "1");     
	select2[3][3] = new Option("失败", "2");    
	
	select2[4][0] = new Option("选择所有", "");
	select2[4][1] = new Option("退帐户处理中", "1");     
	select2[4][2] = new Option("退帐户成功", "2");
	select2[4][3] = new Option("退帐户失败", "3");
	select2[4][4] = new Option("退帐户成功,充退处理中", "4");
	select2[4][5] = new Option("退帐户成功,充退成功", "5");
	select2[4][6] = new Option("退帐户成功,充退失败", "6");
	select2[4][7] = new Option("处理失败", "7");

	var channel = new Array(3); 
	channel[0] = new Array();
	channel[1] = new Array();
	channel[2] = new Array();
	channel[0][0] = new Option("选择所有", "");
    
    channel[1][0] = new Option("选择所有", "");
    channel[2][0] = new Option("选择所有", "");
    
    var i=1;
	<c:forEach items="${fundOutChennal}" var="dto">
		channel[1][i] = new Option('${dto.bankName}', '${dto.bankCode}');
		i++;
	</c:forEach>

	var j = 1;
	<c:forEach items="${fundInChennal}" var="dto">
		channel[2][j] = new Option('${dto.bankName}', '${dto.bankCode}');
		j++;
	</c:forEach>
	//联动函数     
	function redirec(x)
	{
		var temp = document.form1.orderStatus;
		for(m=temp.options.length-1;m >= select2[x].length;m--){
			 temp.options[m]=null;
		}
		for (i=0;i<select2[x].length;i++){
			temp.options[i]=new Option(select2[x][i].text,select2[x][i].value);
		}
		temp.options[0].selected=true;  

		
		var tempChannel = document.form1.channel;
		if(x > 2){
			for(a = tempChannel.options.length-1;a >= channel[2].length;a--){
				tempChannel.options[a] = null;
			}
			for (i=0;i<channel[2].length;i++){
				tempChannel.options[i] = new Option(channel[2][i].text,channel[2][i].value);
			}
		}else{
			for(a = tempChannel.options.length-1;a >= channel[1].length;a--){
				tempChannel.options[a] = null;
			}
			for (i=0;i<channel[1].length;i++){
				tempChannel.options[i] = new Option(channel[1][i].text,channel[1][i].value);
			}
		}
		tempChannel.options[0].selected = true; 
			   
	}

</script>
</html>
