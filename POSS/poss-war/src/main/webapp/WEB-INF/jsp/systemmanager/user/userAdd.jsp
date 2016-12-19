
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script type="text/javascript">
	function processBack(){
		location.href = "user.do";
	}
	function isBD(){
		var bd=$("#ifBD  option:selected").val();
		var leader=$("#ifLeader option:selected").val();
		var superior=$("#superior  option:selected").val();
		$("input[name='ifBD']").attr("value",bd);
		$("input[name='ifLeader']").attr("value",leader);
		$("input[name='superior']").attr("value",superior);
		if(bd=="1"){//不是市场部
		//	alert(bd);
           $("#userInfoFrom").submit();
		}else{
		//	alert(bd);
				leader= $("#ifLeader option:selected").val();//是否市场部的leader
			if(leader=="1"){//否 必须输入他的上级领导
				 superior=$("#superior option:selected").val();
					if(superior==""){
						alert("请输入上级领导");
					return ;
					}
					$("#userInfoFrom").submit();					
			}else{//是
				/* $.ajax({
					type : "POST",
					url : "${ctx}/bdController.do?method=getLeader",
					dataType:"json",
					success : function(result) {
						if(result!=null){
							alert("Leader已经存在");
							return;
						}						
					}
				});  */
				$("#userInfoFrom").submit();
			}
		}
	}
	
	$(function(){ 
		$.ajax({
			type : "POST",
			url : "${ctx}/bdController.do?method=getBDName",
			dataType:"json",
			success : function(result) {
				$.each(result,function(i,item){ 
				$("#superior").append("<option value="+item.id+">"+item.name+"</option>");	
				});
			}
		}); 
	}); 
	//页面validate
	$(document).ready(function(){
		// 手机号码验证    
		jQuery.validator.addMethod("isMobile", function(value, element) {
		  value = jQuery.trim(value);
		  var length = value.length;
		  return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
		}, "请输入正确的手机号码!"); 

		// 密码复杂度
		jQuery.validator.addMethod("passwordHard", function(pwd, element) {
		  var length = pwd.length;
		  var p1= (pwd.search(/[a-zA-Z]/)!=-1) ? 1 : 0; 
		  var p2= (pwd.search(/[0-9]/)!=-1) ? 1 : 0; 
		  var p3= (pwd.search(/[^A-Za-z0-9_]/)!=-1) ? 1 : 0; 
				  
		  return  this.optional(element)  ||(p1 && p2);    
		}, "密码必须是8位以上，并且包含大小写字母和数字!"); 

		// 不能包含全角字符
		jQuery.validator.addMethod("hasChn", function(value, element) {
		  return  (escape(value).indexOf("%u") < 0);    
		}, "不能包含中文字符"); 

		// 只能输入中文
		jQuery.validator.addMethod("onlyChn", function(value, element) {
		  return  ((/^[\u4e00-\u9fa5]+$/.test(value)));
		  }, "只能输入中文字符"); 
		
		
		//聚焦第一个输入框
		$("#userCode").focus();
		//为userInfoFrom注册validate函数
		$("#userInfoFrom").validate({
			rules: { 
			userCode:{
			required:true,
			rangelength:[4,40],
			hasChn:true
		},
				userName:{
				required:true,
				rangelength:[2,10],
				onlyChn:true
			},
				
				userPassword:{
					required:true,
					rangelength:[8,16]
					//passwordHard:true
				},
				userOrgKy:"required",
				userDutyKy:"required",
				userEmail:"email",
				userPhone:{
					number:true, 
					rangelength:[2,8]
				},
				userRTX:{
					number:true
				},
				userMobile:{
					isMobile:true
				}
				
			}
		});
		$(".must").each(function(i){
				$(this).html("<span style='color:red'>*</span>"+$(this).html());
			 });
	

		//定义默认值
		<c:if test="${not empty command}" >
			<c:set var="user" value="${command}" />
			$("#userName").val("${user.userName}");
			$("#userCode").val("${user.userCode}");
			$(":radio[name=userStatus]").val(["${user.userStatus}"]);
			$("#userOrgCode").val("${user.userOrgCode}");
			$("#userDutyCode").val("${user.userDutyCode}");
			$("#userPhone").val("${user.userPhone}");
			$("#userMobile").val("${user.userMobile}");
			$("#userRTX").val("${user.userRTX}");
			$("#userEmail").val("${user.userEmail}");
		</c:if>
		<c:if test="${not empty error}" >
			alert('${error}');
		</c:if>
		
		
		
	});
	
</script>
</head>

<body>
<!-- <br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr class="trForContent1">
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr class="trForContent1">
		<td height="18">
		<div align="center"><font class="titletext">用 户 新 增
</font></div>
		</td>
	</tr>
	<tr class="trForContent1">
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title titletext">用户新增</h2>


<form method="post" id="userInfoFrom"  >
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">	
		<td align="right" class="must border_top_right4">登录账号</td>
		<td class="border_top_right4" >
			<input type="text" name="userCode" id="userCode" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="must border_top_right4">用户姓名</td>
		<td class="border_top_right4" >
			<input type="text" name="userName" id="userName" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="must border_top_right4">密码</td>
		<td class="border_top_right4" >
			<input type="password" name="userPassword" id="userPassword"/>&nbsp;&nbsp;
		</td>
		</tr>
	<tr class="trForContent1">
		<td align="right" class="must border_top_right4">状态</td>
		<td class="border_top_right4" >
			<input type="radio" value="1" name="userStatus" checked="checked">激活
			<input type="radio" value="0" name="userStatus">禁用
		 </td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class=" border_top_right4">邮箱</td>
		<td class="border_top_right4" >
			<input type="text" name="userEmail" id="userEmail">
		</td></tr>
	<tr class="trForContent1">
		<td align="right" class=" border_top_right4">电话</td>
		<td class="border_top_right4" >
			<input type="text" name="userPhone" id="userPhone">
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class=" border_top_right4"  id="userMobile">手机</td>
		<td class="border_top_right4" >
			<input type="text" name="userMobile">
		</td>
		</tr>
	<tr class="trForContent1">
		<td align="right" class=" border_top_right4">员工编号</td>
		<td class="border_top_right4" >
			<input type="text" name="userRTX" id="userRTX">
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="must border_top_right4">是否市场部</td>
	<td class="border_top_right4" >
			<select id="ifBD" style="width:132px; height:20px;">
	      			<option value="1">否</option>
	      			<option value="2">是</option>
	    	</select>
	    	<input type="hidden" name="ifBD">
	    </td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="must border_top_right4" >是否市场部leader</td>
		<td class="border_top_right4" >
				<select id="ifLeader" style="width:132px; height:20px;">
	      			<option value="1">否</option>
	      			<option value="2">是</option>
	    		</select>
		<input type="hidden" name="ifLeader">
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="must border_top_right4">上级领导</td>
			<td class="border_top_right4" >
			<select id="superior" style="width:132px; height:20px;">
			<option value=""></option>
	    	</select>
			<input type="hidden" name="superior">
	    </td>
	</tr>
	<tr class="trForContent1">
		<td colspan="2" class="border_top_right4" align="center">
		 	<input type="button" name="Submit" class="button2"  onclick="javascript:isBD();" value="提交">
		 	<input type="button" class="button2" onclick="javascript:processBack()" name="Submit2" value="返回">
		</td>
	</tr>
</table>
<br>
<br>
<!-- 
<table class="border_all4" width="80%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td class="border_top_right4" >
		 	<input type="button" name="Submit" class="button2"  onclick="javascript:isBD();" value="提交">
		 	<input type="button" class="button2" onclick="javascript:processBack()" name="Submit2" value="返回">
		</td>
	</tr>
</table> -->
</form>
</body>