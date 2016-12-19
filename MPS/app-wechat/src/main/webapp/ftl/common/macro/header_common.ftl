<#macro headCommon txt="收银台">

<#if Session["userSession"]?exists >
	<#assign verifyName = Session["userSession"].verifyName/>
		<div class="header">
	            <img src="images/logo_yt.png" alt="">
	            <div class="fr p5 c_999">
	                <label class="blue mr10">${verifyName} 您好！</label>
	                <a href="${rc.contextPath}/logout.htm?mtype=2">退出</a> | 
	                <a href="${rc.contextPath}/help/index.htm">帮助中心</a>
	            </div>
	    </div>
<#else>
<div class="bg_fff">
        <div class="wrap980">
            <img src="images/logo_yt.png" alt="">
            <label class="fr mt40 c_666"><span class="lab_icon_home"><a href="<@sp.contextPath/>">盈通首页</a></span> <span class="mr5 ml5">|</span> <a href="${rc.contextPath}/outapp.htm?mtype=2">登录</a> <span class="mr5 ml5">|</span> <a href="${rc.contextPath}/register.htm">注册</a></label>
        </div>
    </div>


</#if>
</#macro>