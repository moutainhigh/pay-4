<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html> 
<head> 
<script type="text/javascript"> 
function show_confirm(message) 
{ 
alert(message); 
var url="bounced-register.do?method=batchinit";
parent.closePage(url);
} 
</script> 
</head> 
<body onload="show_confirm('${message}')" >
</body> 
</html>
