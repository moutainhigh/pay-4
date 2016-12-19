<#-- 安全控件封装-->
<#macro password 
		id 						<#--form元素id(不可为空)-->
		name 					<#--form元素name(不可为空)-->
		cid 					<#--安全控件id(不可为空)-->
		width="150" 			<#--安全控件width-->
		height="20" 			<#--安全控件height-->
		style="" 				<#--安全控件style-->
		class="" 				<#--password样式(当控件不使用的时候)-->
		from="normal" 			<#--来源控制，默认normal(在config.properties中有配置来源)-->
		onactivate="" 			<#--控件激活回调函数-->
		p_style="" 				<#--password inline样式(当控件不使用的时候)-->
		form="" 				<#--当按下enter时，触发提交的表单-->
		onEnter=""				<#--控件回车回调函数(和form参数有冲突，同时存在，则都不生效)-->
		tabindex = ""           <#--控件tabindex-->
		nextTabid = ""          <#--控件内按tab以后切换到下一个页面元素得id-->
		useCert = "1"			<#--证书用户是否使用证书控件1默认使用0不使用  -->
		memberCode=""			<#--会员编号-->
		operatorId="0"			<#--会员操作员id-->
		placeholder=""
		>
	 	<input type="password" name="${name}" tabindex="${tabindex}" id="${id}" class="${class}" style="${p_style}" maxlength="20"/>
</#macro>