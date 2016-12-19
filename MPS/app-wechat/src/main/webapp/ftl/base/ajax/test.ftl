<#macro test tmap={"p1":78,"p2":80}>
 <#assign resultStr="">
<#if tmap?has_content>
 <#assign len=tmap?size>
   <#assign keys=tmap?keys>
				<#list keys as key>
					<#if key_index=(len-1)>
				    <#assign resultStr=resultStr+key+"="+tmap[key]>
				    <#else>
				    <#assign resultStr=resultStr+key+"="+tmap[key]+"&">
				    </#if>
					
				</#list>

</#if>

${resultStr}
</#macro> 