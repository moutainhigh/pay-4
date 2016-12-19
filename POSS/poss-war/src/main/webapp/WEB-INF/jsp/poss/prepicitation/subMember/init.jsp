<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>资金沉淀报表查询</title>
<script type="text/javascript">
	function trim(str) { //删除左右两端的空格
	    return str.replace(/(^\s*)|(\s*$)/g, ""); //把空格替换为空
	}

	function changeQueryType(){
		document.getElementById("startDate").value="";
		document.getElementById("endDate").value="";
	}

	function changeWdate(obj){
		var queryType=document.getElementById("queryType").value;
		var myDateFmt='yyyy-MM-dd';
		var myMaxDate='%y-%M-{%d-1}'; 
		if(queryType==2){  
		    myDateFmt='yyyy'; 
		    myMaxDate='{%y-1}'; 
		}  
		else if(queryType==1){  
		    myDateFmt='yyyy-MM';  
		    myMaxDate='%y-{%M-1}'; 
		}
		//alert(obj.name);
		
		if(obj.name=="startDate"){
			var endDate=document.getElementById("endDate").value;
			WdatePicker({dateFmt:myDateFmt,maxDate:endDate!=''?'#F{$dp.$D(\'endDate\')}':myMaxDate});
		}else{
			WdatePicker({dateFmt:myDateFmt,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:myMaxDate});
		}
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
		sumAmount();
	}
	//取消一个选择单位，去掉全选框的勾
	function selectAllcheckBoxunchecked(obj){
		var s = 0;
	  if(!obj.checked){
		  $("#checkAll").attr("checked",false);
	  }
	  sumAmount();
	}
	function sumAmount(){
		var s= 0;
		var c= 0;
		 $("[name='wkKey']:checkbox:checked").each(function(){
			  	s += parseInt($(this).val());
			  	c++;
			});
		 changeDiv((s/1000).toFixed(2), c);
	}
	function changeDiv(s, c) {
		var amount =cc(s);
		$("#showSum").html("选中金额共 " + amount + " 元，笔数共 " + c + " 笔");
	}
	function cc(s){
        if(/[^0-9\-\.]/.test(s)) return "invalid value";
        s=s.replace(/^(\d*)$/,"$1.");
        s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
        s=s.replace(".",",");
        var re=/(\d)(\d{3},)/;
        while(re.test(s))
                s=s.replace(re,"$1,$2");
        s=s.replace(/,(\d\d)$/,".$1");
        return s.replace(/^\./,"0.")
    }
	
	//验证起止日期
	function isEmpty(fData){
	    return ((fData==null) || (fData.length==0) )
	}
	function validateQuery(){
		var startDate = document.getElementById("startDate").value;
		var endDate = document.getElementById("endDate").value;
		if(isEmpty(startDate)){
			alert("开始时间不能为空");
			document.getElementById("startDate").focus();
			return false;
		}
		if(isEmpty(endDate)){
			alert("结束时间不能为空");
			document.getElementById("endDate").focus();
			return false;
		}
		if(startDate > endDate){
			alert("结束时间不可早于开始时间");
			document.getElementById("endDate").focus();
			return false;
		}
		return validateForm();
	}

	function validateForm(){	
		var obj=document.getElementById("subMemberCode");
		obj.value=trim(obj.value);
		
	 	 // 校验定长的数字型字符串
		 jQuery.validator.addMethod("fixedNumStr", function(value, element, param) { 
	 		 var length = value.length; 
			 var chrnum = /^([0-9]+)$/;
			 return this.optional(element) || (length == param[0] && chrnum.test(value)); 
		 }, $.validator.format("请输入{0}位数字"));
	 	 
		$("#form1").validate({
			rules: { 
				subMemberCode:{
					fixedNumStr:[11]
				}				
			}
		});
		obj.focus();
		return $("#form1").valid();	
	}

	//页面validate
	$(document).ready(function(){
		//聚焦第一个输入框
		//$("#subMemberCode").focus();
		validateForm();
	});	

	function userQuery(pageNo,totalCount,pageSize){
		if(!validateQuery()){
			return false;
		}
	  	$('#infoLoadingDiv').dialog('open');
		var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "${ctx}/precipitation/querySubMbrPrepicitation.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#paginationResult').html(result);
			}
		});
    }	
	
	//下载导出到EXCEL表格
	function exportExcel(totalCount) {
		if(totalCount <= 0){
			alert("无结果集,不能下载！");
		}else if(totalCount > 60000){
			alert("结果集大于60000,不能下载！");
		}else{
			if(!validateQuery()){
				return false;
			}			
			var pars = $("#form1").serialize();
			window.location="${ctx}/precipitation/querySubMbrPrepicitation.do?method=list&export=1&"+pars; 
		}
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
						<font class="titletext">会员资金沉淀报表</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>
	<br>
	
	<form action="" method="post" id="form1" name="form1">
	  <table class="border_all2" width="80%" border="1" cellspacing="0"	cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td class="border_top_left4">
	    		<label>
					查询对象：
				</label>
	        	<select name="queryType" id="queryType" onChange="changeQueryType()">
	       			<option selected="selected" value="0">每日</option>
	       			<option value="1">月日均</option>
	       			<option value="2">年日均</option>
	        	</select>
	      	</td>    
	    	<td align="left" class="border_top_left4" colspan="2">
	    	    <label>
	    			交易日期：
	    		</label>
	    		<label> 
			      	<input class="Wdate" type="text" id="startDate" name="startDate" onFocus="changeWdate(form1.startDate)">
			        	～
					<input class="Wdate" type="text" id="endDate" name="endDate" onFocus="changeWdate(form1.endDate)">
				</label>
	      	</td>
	    </tr>
	    <tr class="trForContent1">
	    	<td class="border_top_left4">
	    		<label >
					用户类型：
				</label>
	        	<select name="memberType"">
	       			<option selected="selected" value="">全部</option>
	       			<option value="1">个人</option>
	       			<option value="2">企业</option>
	        	</select>
	      	</td>    
	      	<td align="left" class="border_top_left4">
		      	<label>
					会员编号：
				</label>
		    	<input type="text" name="subMemberCode" id="subMemberCode" maxlength="11" size="12"/>
	      	</td>
	      	<td align="left" class="border_top_left4">
	    		<label for="r1">
					商户等级：
				</label>
	        	<select name="memberLevel" id="memberLevel">
	        		<option value="" selected>全部</option>
			    	<c:forEach items="${mlList}" var="ml">
			    		<option value="${ml.serviceLevelCode}">${ml.serviceLevelName}</option>
			    	</c:forEach>		
	        	</select>	
		    </td>				
	    </tr>
  	</table>
	<br>
    <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
 </form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>
