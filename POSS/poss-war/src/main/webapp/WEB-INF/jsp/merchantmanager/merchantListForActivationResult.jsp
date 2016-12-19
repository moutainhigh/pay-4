<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>



<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script src="./js/mainstyle1/body.js"></script>
<script language="javascript">
var a;

function checkedAll(){            
	if(a==1){
	 	for(var i=0;i<window.document.merchantActivationFormBean.elements.length;i++){                
	       var e = merchantActivationFormBean.elements[i];
	       e.checked =false;                  
	    }
	    a=0;
	}       
	else{
	    for(var i=0;i<window.document.merchantActivationFormBean.elements.length;i++){                
	       var e = merchantActivationFormBean.elements[i];
	       e.checked =true;                  
	    }
	    a=1;
	}     
}
function checkChecked(){
  var number=0;
  for(var i=0;i<window.document.merchantActivationFormBean.elements.length;i++){
       var e = merchantActivationFormBean.elements[i];
       if (e.name != "allChecked"){    	  
         if(e.checked==true){
             number=number+1;
                       
         }
       }
    }
    if(number==0){
       alert("请选择需要发送Email的商户！");
       return ;
     }
    if (window.confirm("您确认要发送Email吗？")){
        document.merchantActivationFormBean.submit();
 	   return ;
	}else{
	   return ;
	}
}
function closePage(url){
	parent.closePage(url);
}
</script>
</head>

<body>
<form id="merchantActivationFormBean" name="merchantActivationFormBean" action="merchantSendEmail.do" method="post">
	<table class="tablesorter" width="100%" align="center" border="0" cellpadding="0" cellspacing="1">
		<thead> 
		<tr class="" align="center" valign="middle">
		<th class=""  ><input type="checkbox" id="allChecked" name ="allChecked" onclick="checkedAll()"/></td>
			<th class=""  >
				商户号</th>
			<th class=""  >
				商户名称</th>
				<th class=""  >
				邮箱地址</th>
			<th class=""  >
				审核通过时间</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.result}" var="merchant" varStatus = "merchantStatus">
		<c:choose>
	       <c:when test="${merchantStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="" align="center" ><input type="checkbox" id="merchantChecked" name ="merchantChecked" value="${merchant.memberCode}|${merchant.merchantName}|${merchant.email}"/></td>						
				<td class="" align="center" >${merchant.merchantCode}&nbsp;</td>
				<td class="" align="center" >${merchant.merchantName}&nbsp;</td>
				<td class="" align="center" >${merchant.email}&nbsp;</td>
				<td class="" align="center" >${merchant.checkCreateDate}&nbsp;</td>					
			</tr>
		</c:forEach>
		</tbody>
	</table>	
</form>

<li:pagination methodName="merchantQuery" pageBean="${page}" sytleName="black2"/>
<input type = "button" class="button2"  onclick="javascript:checkChecked();" value="邮件通知激活">
<input type = "button" class="button2"  onclick="javascript:closePage('merchantListForActivation.do');" value="关闭">

</body>

