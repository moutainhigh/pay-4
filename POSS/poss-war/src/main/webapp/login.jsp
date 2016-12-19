<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<script src="${ctx}/js/jquery/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	  //页面validate
		$(document).ready(function() {
			//聚焦第一个输入框
				$("#j_username").focus();
				//为userInfoFrom注册validate函数
				var error = "";
			<c:if test="${not empty param.login_error || not empty param.access_error }">
			<c:choose>
				<c:when test="${param.login_error == 'error_rand_code'}">
					error = "验证码输入不正确！";
				</c:when>
				<c:when test="${param.login_error == 'error_auth_ip'}">
					error = "未授权的IP地址，不能登录！";
				</c:when>
				<c:when test="${param.login_error == 'user_not_active'}">
				error = "用户未激活，无法登录！请与系统管理员联系。";
				</c:when> 
				<c:when test="${param.login_error == 'user_not_found'}">
				error = " 用户不存在！";
				</c:when> 
				<c:when test="${param.login_error == 'userAuth_not_found'}">
				error =  "用户或密码不正确！";
				</c:when> 
				<c:when test="${param.login_error == 'securityStratety_not_found'}">
					error = " 用户的安全策略不存在！";
				</c:when> 
				<c:when test="${param.login_error == 'user_locked'}">
					error = " 用户已被锁定，无法登录！请与系统管理员联系。";
				</c:when> 
				<c:when test="${param.login_error == 'user_psw_error'}">
					error = " 无效的用户密码，请重试！";
				</c:when> 
				<c:when test="${param.login_error == 'code_error'}">
					error = " 验证码输入错误！";
				</c:when> 
				<c:when test="${param.login_error == 'too_many_error_login_times'}">
					error = " 用户已被锁定，因连续多次的错误登录！请与系统管理员联系。";
				</c:when> 
				<c:when test="${param.login_error == 'too_many_user_error'}">
					error = " 该用户已经登录，请先退出！";
				</c:when>
				<c:when test="${param.login_error == 'error_extend_auth'}">
					error = " 扩展权限信息错误！";
				</c:when>
				<c:when test="${param.login_error == 'error_logined_user'}">
					error = "用户已经其他客户端登录，换个账户或联系管理员！";
				</c:when>
				<c:when test="${param.access_error == 'no_user'}">
					error = "未登录或用户超时了，请重新登录！";
				</c:when>
				<c:when test="${param.access_error == 'no_resource'}">
					error = "您没有权限访问，可以更改用户！";
				</c:when>
				
				
				
				</c:choose>
				$("#errorMsg").text(error);
			</c:if>
			
			});

		function login(){
			var un = $("#j_username").val();
			var pwd = $("#j_password").val();
			var rand = $("#rand").val();
			if(!un){
				$("#errorMsg").text("用户名不能为空");
				return ;
			}
			if(!pwd){
				$("#errorMsg").text("密码不能为空");
				return ;
			}
			if(!rand){
				$("#errorMsg").text("验证码不能为空");
				return ;
			}
			$("#loginform").submit();
		}
		
</script>
<meta charset="utf-8">
<title>运营支撑系统</title>
<style type=text/css>
body { background-color: #c3cad2; background-repeat: repeat-x }
input { border-bottom: #2871a5 1px solid; border-left: #2871a5 1px solid; line-height: 18px; padding-left: 3px; width: 148px; padding-right: 3px; height: 18px; border-top: #2871a5 1px solid; border-right: #2871a5 1px solid }
</style>
</head>
<script language="javascript">
  g_blnCheckUnload = true;
  function RunOnBeforeUnload() {
     if (g_blnCheckUnload) {window.event.returnValue = 'You will lose any unsaved content';   
     }  
  }
</script>

<body style="padding-top: 137px">
<form method="post" action="${ctx}/j_spring_security_check" id="loginform">
	<table border=0 cellspacing=0 cellpadding=0 align=center style="margin:0 auto">
		<tbody>
			<tr>
				<td colspan=2><img alt=后台登录 src="images/login/login1_05.jpg" width=547 
      height=58></td>
			</tr>
			<tr>
				<td width=299><img src="${ctx}/images/login/login1_07.jpg" width=299 
    height=196></td>
				<td valign=center style="background:url(images/login/login1_17.jpg) -1px 0 no-repeat;" width=249><table style="margin-bottom: 10px; margin-left: 10px" border=0 
      cellspacing=0 cellpadding=0>
						<tbody>
													<tr>
				<td style="text-align: center;color:red;font-size: 13px" height=21 colspan=2 id="errorMsg"></td>
			</tr>
						
							<tr>
								<td style="color: #295888" class=font3 height=32 width=52>用户名</td>
								<td width=175><input class="input_txt" type="text" name="j_username" id="j_username" value="${sessionScope.username }" onkeydown="if (event.keyCode == 13)this.form.j_password.focus();"/></td>
							</tr>
							<tr>
								<td style="color: #295888" class=font3 height=32>密&nbsp;&nbsp;码</td>
								<td><input class="input_txt" type="password" name="j_password" id="j_password" /></td>
							</tr>
								<tr>
								<td style="color: #295888" class=font3 height=32>验证码</td>
								<td><input class="input_txt" type="text" id="rand" name="rand" style="width:70px" onkeydown="onkeyD(event);" title="输入右边的验证码,点图换一张" maxlength="32" />
								 <img alt="验证码" src="${ctx}/validatecode/validatecode.htm" height="25" align="top" title="点图换一张" 
								 onclick="this.src='${ctx}/validatecode/validatecode.htm?d='+Math.random()"
								 /></td>
							</tr>
							
							
							<tr>
						<td style="text-align: center" height=42 colspan=2><img id=img1 src="images/login/login1_12.jpg" width=77  height=35  onclick="login()" /></td>
							</tr>
						</tbody>
					</table></td>
			</tr>
			<tr>
				<td colspan=2><img src="images/login/login1_15.jpg" width=547   height=48  ></td>
			</tr>
			
			
		</tbody>
	</table>

</form>
<script type="text/javascript">

	function onkeyD(e) {
		var theEvent = window.event || e;
		var code = theEvent.keyCode || theEvent.which;
		if (code == 13) {
			login();
		}
	}
</script>
</body>
</html>


