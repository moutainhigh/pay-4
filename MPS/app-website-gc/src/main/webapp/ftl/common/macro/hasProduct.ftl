<#function hasProduct productCode> 
<#assign hasProductTemplate = "com.pay.app.common.template.HasProductTemplate"?new()>
<#assign flag=hasProductTemplate(productCode)/> 
 <#return flag>
</#function>   