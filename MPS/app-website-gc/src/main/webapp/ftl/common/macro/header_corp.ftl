<#macro headCorp index=false channel=1 child=1 matrix=false mCode="">
<#if Session["userSession"]?exists && (Session["userSession"].scaleType==2 || Session["userSession"].scaleType==10)>
	<#assign verifyName = Session["userSession"].verifyName/>
	<#--<#assign msgCountTemplate = "com.pay.app.common.template.MsgCountTemplate"?new()>
    <#assign countUnRead=msgCountTemplate()>  -->
		<div class="header">
		        <div class="wrap clearfix" style="height:50px;" id="logo">
			        <h1 class="l">
		            <img src="<@sp.static/>/gcpayment/images/logo.png" alt="">
	                </h1>
				    <ul class="nav l">
					<li class="nav-item" style="padding:0px;"><a href="javascript:void(0)" title="商户控台" style="padding:5px 0px 0px 5px;color:#333333 !important;cursor:default;">商户控台</a>
					</li>
				   </ul>
				   <font class="last_login" onclick="window.location.href='${rc.contextPath}/corp/operatorManage.htm?method=showOperatorLogin'">
				   	上次登录时间：	
				   	<#if Session["epLastLoginTime"]?exists >
					<#assign lastLoginTime = Session["epLastLoginTime"]/>
						${lastLoginTime?default('')}
					</#if>
				   </font>
				</div>
	    
	<div class="topbar2">
	<#if index>
		<#include "/common/include/corp_nav.html">
	<#else>
		<#assign menuTemplate = "com.pay.app.common.template.MenuTemplate"?new()>
	     <#assign selfPmList=menuTemplate()>
	     <div class="menu">
			<div class="wrap">
			<ul class="mainmenu clearfix" id="sideheader">
			<#if  selfPmList?exists && selfPmList?has_content>
			<#list  selfPmList as pm>
			 <#assign  rootIndex=pm_index+1>
		     <li name="f_header" index="${rootIndex}" >
		     		<a name="f_header_a" id="subLi_${rootIndex}"   
		            <#if pm.menuUrl?exists && pm.menuUrl!='#'>href="<@contextPath/>${pm.menuUrl}"<#else>href="javascript:void(0);"</#if>><strong>${pm.menuName}</strong><i></i></a>
					<div class="subNav2 subNav2_pjb" name="c_header" id="subNav_${rootIndex}"  style="display:none;<#if pm.menuCode?default("")=='ecard_manager'>left:40%;</#if>">
					<#assign  childIndex=0>
					<#if pm.childlist?has_content>
					<#list pm.childlist as ch>

						<#if ch.status==1>
						<#if ch.displayFlag==1>
						<#assign  childIndex=childIndex+1>
						<#if childIndex==1><script>$(function(){$("#subLi_${rootIndex}").attr("href","<@contextPath/>${ch.menuUrl}");})</script></#if>
						<#if sp.hasMenu(ch.menuCode?default(""),mCode)>
							<script>
								$(function(){$("#subLi_${rootIndex}").addClass("cur");$("#defaultNav").val("${rootIndex}");$("#subNav_${rootIndex}").show();})
							</script>
						</#if>
							<a  href="javascript:void(0);" class="<#if sp.hasMenu(ch.menuCode?default(""),mCode)>subActive</#if>" ><span  name="enabledMenu"   url="<@contextPath/>${ch.menuUrl}">${ch.menuName}</span></a>
						</#if>
						<#else>
							<a class="subNav3" href="javascript:void(0)"><span  name="nofeature">${ch.menuName}</span></a>
						</#if>
						
					</#list>
					</div>
					</#if>
			</li>
			</#list>
			<li class="last"></li>
			</#if>
			<input type="hidden" id="defaultNav" value="1"/>
			<script type="text/javascript" src="<@sp.static/>/js/pay/common/header.js"></script>
	</#if>
	 
</ul>
			</div>
		</div>
	</div>

<#else>
<div class="bg_fff">
        <div class="wrap980">
            <img src="images/logo_yt.png" alt="">
            <label class="fr mt40 c_666"><span class="lab_icon_home"><a href="${rc.contextPath}">盈通首页</a></span> <span class="mr5 ml5">|</span> <a href="${rc.contextPath}/outapp.htm?mtype=2">登录</a> <span class="mr5 ml5">|</span> <a href="${rc.contextPath}/register.htm">注册</a></label>
        </div>
    </div>

	    <div class="nav_w empty">
    </div>
<#include "/common/include/corp_nav.html">

</#if>
</#macro>