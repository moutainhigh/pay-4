<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script src="./js/common.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<h2 class="panel_title">商户通知Email功能管理</h2>
<script type="text/javascript">
	$(function(){
		<c:if test="${not empty errorMsg }">
			alert('${errorMsg}')
		</c:if>
	})
</script>

<form  method="post" name="mainfrom" id="mainfrom" >
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="memberCode" name="memberCode" value="${memberCode}" /></td>
		<td class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
			<input type="text" id="loginName" name="loginName" value="${loginName}" />
		</td>
		<td class="border_top_right4" align="right" >商户名称：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantName" name="merchantName" value="${merchantName}" />
		</td>		
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >商户号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantCode" name="merchantCode" value="${merchantCode}" />
		</td>
		<td class="border_top_right4" align="right" >开通状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="openFlag" name="openFlag" size="1">
				<option value="" selected>---请选择---</option>
				<option value="0" <c:if test="${openFlag == 0}">selected</c:if>>未开通</option>
				<option value="1" <c:if test="${openFlag == 1}">selected</c:if>>开通</option>
			</select>
		</td>
		
		<td class="border_top_right4" align="right" >创建时间：</td>
		<td class="border_top_right4" align="left" colspan="6">
			<input class="Wdate" type="text" id= "startDate" name="startDate"  value='<fmt:formatDate value="${beginDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
			至
			<input class="Wdate" type="text" id= "endDate" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}'})">
		 </td>
	</tr>		
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
		  <input type="button" onclick="javascript:add();"  name="butSubmit" value="新增" class="button2">
	      <input type="button" onclick="javascript:search();"  name="butSubmit" value="查询" class="button2">
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

<div id="orderEmailNotifyDiv" style="display: none" align="center" >
	<form id="orderEmailNotifyForm" name="orderEmailNotifyForm"  method="post" ><!-- onsubmit="return validator(this);"  -->
		<input type="hidden" name="id"  id="id">
		<input type="hidden" name="type" id="type" value="">
		<table id="addOrderEmailNotifyTable" border="0" cellpadding="0" cellspacing="0" align="center" width="95%">
			<tr class="trForContent1"><td align="right"  class="border_top_right4">会员号:</td>
				<td align="left"  class="border_top_right4">
				<input name='_memberCode' id='_memberCode' onkeyup='checkNum(this);' onblur='setInfo(this.value);' /><font color="red">*</font>
				<select id="merchantInof" name="merchantInof" onchange="setInfo(this.value)">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${merchantStatusEnum}" var="merchantStatus">
					<option value="${merchantStatus.code}">${merchantStatus.description}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr class="trForContent1"><td align="right"  class="border_top_right4">商户号:</td><td align="left"  class="border_top_right4"><input name='_merchantCode' id='_merchantCode'  onkeyup='checkNum(this);' /><font color="red">*</font></td></tr>
			<tr class="trForContent1"><td align="right"  class="border_top_right4">商户名称:</td><td align="left"  class="border_top_right4"><input name='_merchantName' id='_merchantName' /><font color="red">*</font></td></tr>
			<tr class="trForContent1"><td align="right"  class="border_top_right4">登录名:</td><td align="left"  class="border_top_right4"><input name='_loginName' id='_loginName' /></td></tr>
			<tr class="trForContent1"><td align="right"  class="border_top_right4">公司邮箱:</td><td align="left"  class="border_top_right4"><input name='_emailCompany' id='_emailCompany' /></td></tr>
			<tr class="trForContent1"><td align="right"  class="border_top_right4">通知邮箱:</td><td align="left"  class="border_top_right4"><input name='_emailNotify' id='_emailNotify' valid="required|isEmail" errmsg="通知Email不能为空!|Email格式不对!" /><font color="red">*</font></td></tr>
			<tr class="trForContent1"><td align="right"  class="border_top_right4">开通与否:</td><td align="left"  class="border_top_right4">
				<select id="_openFlag" name="_openFlag" size="1">
					<option value="0" >未开通</option>
					<option value="1" selected>开通</option>
				</select>
			</td></tr>
			<tr class="trForContent1" style="display:none"><td align="right"  class="border_top_right4">操作人:</td><td align="left"  class="border_top_right4"><input name='_operator' id='_operator' /></td></tr>
			<tr class="trForContent1">
				<td align="center"  class="border_top_right4"><input type="button" onclick="back();" value="取消" /></td>
				<td align="center"  class="border_top_right4"><input type="button" id="submitForm" name="submitForm"  onclick="validateFromData();" value="确定"></td>
			</tr>
		</table>
	</form>
</div>


<script type="text/javascript">
	 $(document).ready(function() {
		search();
	}); 

	function search(pageNo, totalCount) {
		//$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./orderEmailNotify.do?method=orderEmailNotifyQuery",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
		
	}
	
	//新增
	function add() {
		removeAttr();
		$('#orderEmailNotifyDiv').dialog({
			position : ["center","center"],//"top",
			width : 450,
			height: 330,
			modal : true,
			title : '新增商户邮件通知',
			overlay : {
				opacity : 0.5,
				background : "black",
				overflow : 'auto'
			}    
		});	
		$("#type").attr("value","add");
		showSelect();
	}
	//加载会员数据
	function showSelect() {
		var url = "${ctx}/orderEmailNotify.do?method=queryEnterprise";
		var pars = {"type":"1","htmlType":"json"};
		$.ajaxSetup({ cache: false });
		$.getJSON(url, pars, function(data) {
			var str = "<option value='' selected>---请选择---</option>";
			//debugger;
	        for(var i=0;i<data.length;i++){
	        	//console.log(data[i]);
	        	str += "<option value='" + data[i].memberCode + "'>" + data[i].merchantName + "</option>";
	        }
	        $("#merchantInof").html(str);
	    });
		//$("#merchantInof").css("display", "block");//显示选择框
		$("#merchantInof").show();//显示div
	}
	//自动填充数据
	function setInfo(memberCode){
		// TODO
		var url = "${ctx}/orderEmailNotify.do?method=getEnterprise";
		var pars = {"memberCode":memberCode,"htmlType":"json"};
		var type=$("#type").val();
		//debugger;
		
		$.ajaxSetup({ cache: false });
		$.getJSON(url, pars, function(data) {
			if (data == null || data == undefined || data == '') 
			{
				if(type=="add")//修改的时候不能清空
				{
					$("#_merchantCode").attr("value","");
					$("#_merchantName").attr("value","");
					$("#_loginName").attr("value","");
					$("#_emailNotify").attr("value","");
					$("#_emailCompany").attr("value","");
					$("#_operator").attr("value","");
				}
			}
			else 
			{
				$("#_memberCode").attr("value",data.memberCode);
				$("#_merchantCode").attr("value",data.merchantCode);
				$("#_merchantName").attr("value",data.merchantName);
				$("#_loginName").attr("value",data.loginName);
				$("#_operator").attr("value",data.operator);
				$("#_emailCompany").attr("value",data.loginName);
				// $("#_emailNotify").attr("value",data.loginName);//通知邮箱地址一定要手动填一遍，不用自动填充
			}
	    });
	}

	function removeAttr(){
		$("#_memberCode").removeAttr("value");
		$("#_memberCode").removeAttr("readonly");
		$("#_merchantCode").removeAttr("value");
		$("#_merchantName").removeAttr("value");
		$("#_loginName").removeAttr("value");
		$("#_emailNotify").removeAttr("value");		
		$("#_emailCompany").removeAttr("value");	
		$("#_operator").removeAttr("value");	
	}
	//修改记录
	function update(id,_memberCode,_merchantCode,_merchantName,_loginName,_emailCompany,_emailNotify,_openFlag,_operator){
		$("#id").attr("value",id);
		$("#_memberCode").attr("value",_memberCode);
		$("#_memberCode").attr("readonly","readonly");
		$("#_merchantCode").attr("value",_merchantCode);
		$("#_merchantName").attr("value",_merchantName);
		$("#_loginName").attr("value",_loginName);
		$("#_emailNotify").attr("value",_emailNotify);
		$("#_emailCompany").attr("value",_emailCompany);		
		$("#_openFlag").attr("value",_openFlag);
		$("#_operator").attr("value",_operator);
		$('#orderEmailNotifyDiv').dialog({
			position : ["center","center"],//"top",
			width : 450,
			height: 330,
			modal : true,
			title : '修改商户邮件通知',
			overlay : {
				opacity : 0.5,
				background : "black",
				overflow : 'auto'
			}    
		});	

		$("#type").attr("value","update");

		//$('#merchantInof').hide();
		$("#merchantInof").css("display", "none");//不显示选择框
	}
	//修改开通状态
	function updateFlag(id,openFlag){
		if (openFlag=='1') {
			openFlag = '0';
		}else{
			 openFlag = '1';
		}
		window.location.href="${ctx}/orderEmailNotify.do?method=updateFlag&id="+id+"&_openFlag="+openFlag;
	}
	//删除
	function del(id){
		if (!confirm("确认删除？")) {
			return;
		 }
		window.location.href="${ctx}/orderEmailNotify.do?method=orderEmailNotifydelete&ids="+id;
	}

	function back(){
		$('#orderEmailNotifyDiv').dialog("close");
	}
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
  
	function validateFromData(){
		var memberCode = $("#_memberCode").val() ;
		var merchantCode = $("#_merchantCode").val() ;
		var merchantName = $("#_merchantName").val() ;
		var emailNotify = $("#_emailNotify").val() ;
		if(null == memberCode || "" == memberCode){
			alert("会员号不能为空！") ;
			return false ;
		}
		if(null == merchantCode || "" == merchantCode){
			alert("商户号不能为空！") ;
			return false ;
		}
		if(null == merchantName || "" == merchantName){
			alert("商户名称不能为空！") ;
			return false ;
		}
		if(null == emailNotify || "" == emailNotify){
			alert("通知邮箱不能为空！") ;
			return false ;
		}
		if (!emailNotify.isEmail()) {
			alert("通知邮箱地址格式不正确！") ;
			return false ;
        }
		var type=$("#type").val();
		//debugger;
		if(type=="add"){
			checkMemberCode(memberCode);
		}else if(type == "update"){
			$("#orderEmailNotifyForm").attr("action","${ctx}/orderEmailNotify.do?method=orderEmailNotifyUpdate");
			$("#orderEmailNotifyForm").submit();
			back();
		}
		//
	}//orderEmailNotifyIsExists
	
	function isWantNum(s){
	    //s = trim(s);
	    var p =/^[1-9](\d+(\.\d{1,3})?)?$/; 
	    var p1=/^[0-9](\.\d{1,3})?$/;
	    return p.test(s) || p1.test(s);
	}

	function checkMemberCode(memberCode){
		if(memberCode=="80000000000")
		{
			alert("平台会员号不需要添加，请重新填写!");
			return false;
		}
			
		var url="${ctx}/orderEmailNotify.do";
		var data="method=checkMemberCode&memberCode="+memberCode; //$("#_memberCode").val();
         $.post(url,data,function(res){
			if(res=="ipRepeat"){
				alert("会员号已经存在，请重新填写!");
			} 
			else if(res=="yes"){
				$("#orderEmailNotifyForm").attr("action","${ctx}/orderEmailNotify.do?method=orderEmailNotifyCreate");
				$("#orderEmailNotifyForm").submit();
				back();
			}
	     });
		
	}
	
	function checkMerchantName(merchantName)
	{
		
	}
	
</script>









