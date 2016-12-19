<#-- 帮助中心 头部  -->
<#macro headHelp channel=1 child=0 vertxt="帮助中心">  
<!--header str-->
<#assign _appParam = "?method=indexApp" />
<#if Session["userSession"]?exists && Session["userSession"].scaleType==2>
<#assign _appParam = "" />
</#if>

<div class="header">
	<h1 class="logo"><a href="${rc.contextPath}/index.htm${_appParam}"><img alt="安捷支付" src="<@sp.static/>/img/pay/logo.png"></a><i class="ver_txt">${vertxt}</i></h1>
	<div class="header_bar"><a class="pad" href="${rc.contextPath}/index.htm?method=indexApp">个人服务</a> | <a href="${rc.contextPath}/index.htm" class="pad">企业服务</a>| <a href="${rc.contextPath}/help/index.htm" class="help">帮助中心</a></div>
</div>

<!--header end--> 
</#macro> 

