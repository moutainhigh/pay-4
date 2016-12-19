<#macro head index=false channel=1 child=0 type=2 matrix=false mCode="" v=1> 
<#--第一版本 -->
<#if v = 1>
	<#setting number_format="0">
	<#assign utype=type>
	<#if Session["userSession"]?exists && index=false>
	<#if Session["userSession"].scaleType==2 || Session["userSession"].scaleType==10>
	<#assign utype=2>
	<#else>
	<#assign utype=1>
	</#if>
	</#if>
	<#if utype=1>
	<@sp.headApp index=index channel=channel child=child matrix=matrix mCode=mCode/>
	<#else>
	<@sp.headCorp index=index channel=channel child=child matrix=matrix  mCode=mCode/>
	</#if>
	<!--导航 end--> 
	  <form method="post" id="menuForm" name="sidermenuForm" action="">
	<input type="hidden" name="menuId"/>
	</form>
	<script type="text/javascript" src="<@sp.static/>/js/pay/common/index.js"></script>
<#else>
<#--第二版本 -->
	<!--header-->
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
	<!--/header-->

	<!--导航-->
	<div class="nav_w">
        <div class="wrap980">
            <ul class="clearfix ml10">
                <li class="cur"><a href="#">首 页</a></li>
                <li><a href="#">商户服务</a></li>
                <li><a href="#">个人服务</a></li>
                <li><a href="#">行业解决方案</a></li>
                <li><a href="#">安全中心</a></li>
            </ul>
        </div>
    </div>
	<!--/导航-->
	
</#if>
</#macro>