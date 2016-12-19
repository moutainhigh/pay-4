<#-- 帮助中心 头部  -->
<#macro headHelp channel=1 child=0 vertxt="帮助中心">  
<!--header str-->
<#assign _appParam = "?method=indexApp" />
<#if Session["userSession"]?exists && Session["userSession"].scaleType==2>
<#assign _appParam = "" />
</#if>

<#if Session["userSession"]?exists>
		<#assign verifyName = Session["userSession"].verifyName/>
		<#if Session["userSession"].scaleType==3>
		<div class="top">
		        <div class="wrap980 tr c_666">
		            <a class="pad" href="${rc.contextPath}/app/myAccount.htm"><strong>${verifyName}</strong></a> 您好！ | <a class="pad" href="${rc.contextPath}/logout.htm">退出</a> |  <a class="help" href="${rc.contextPath}/help/index.htm">帮助中心</a>
		        </div>
		    </div>
		    
		    <div class="wrap980">
		        <img src="images/logo_yt.png" alt="">
		        <label class="fr mt30 f18 c_666">服务热线：400-8888-888</label>
		    </div>
		<#else>
		<div class="top">
		        <div class="wrap980 tr c_666">
		            <a class="pad" href="${rc.contextPath}/app/myAccount.htm"><strong>${verifyName}</strong></a> 您好！ | <a class="pad" href="${rc.contextPath}/logout.htm">退出</a> |  <a class="help" href="${rc.contextPath}/help/index.htm">帮助中心</a>
		        </div>
		    </div>
		    
		    <div class="wrap980">
		        <img src="images/logo_yt.png" alt="">
		        <label class="fr mt30 f18 c_666">服务热线：400-8888-888</label>
		    </div>
		</#if>
	<#else>
		    <div class="top">
		        <div class="wrap980 tr c_666">
		            欢迎光临煜烁金融服务中心 <span class="mr5 ml10">|</span> <a href="${rc.contextPath}/outapp.htm?mtype=2">登录</a> <span class="mr5 ml5">|</span> <a href="${rc.contextPath}/register.htm">注册</a> <span class="mr5 ml5">|</span> <a href="${rc.contextPath}/help/index.htm">帮助中心</a>
		        </div>
		    </div>
		    
		    <div class="wrap980">
		        <img src="images/logo_yt.png" alt="">
		        <label class="fr mt30 f18 c_666">服务热线：400-8888-888</label>
		    </div>
	</#if>

<!--header end--> 
</#macro> 

