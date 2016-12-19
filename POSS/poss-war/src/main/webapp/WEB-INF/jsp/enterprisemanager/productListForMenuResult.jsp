<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>



<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">

function addNewMenu(url){
	parent.addMenu("产品菜单配置",url);
	
}
//offline
function productDelete(productId){
	if(confirm("确认下线该产品吗？")){
		document.getElementById('productId').value=productId;
		this.productQuery();
		document.getElementById('productId').value='';
	}
}

function productEdit(productId){
	addNewMenu("${ctx}/productAdd.do?productId="+productId,"产品菜单编辑");
}

var delProductId = null;
function delCheck(productId){
	delProductId = productId;
	$('#delCheckDiv').dialog('open'); //修改标题 
}

$(document).ready(function(){

	$('#delCheckDiv').dialog( 
			{ 
			autoOpen:false,	
			height:170, 
			width:400, 
			title:'删除确认', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 
					var checkPassword= $("#checkPassword").val();
					$.post("productDeleteForMenu.do",{"checkPassword":checkPassword,"id":delProductId},function cbk(msg){
						if(msg=="S"){
							productQuery();
							$('#delCheckDiv').dialog('close');
						}else{
							alert(msg);
							$("#delCheckDiv").dialog("close");
						}
						});
			}, 
			'取消':function(){ 
				$("#delCheckDiv").dialog("close");
			} 
			} 
			} 
			); 	
});
</script>
</head>
<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			产品名称</th>
		<th class=""  >
			产品描述</th>
		<th class=""  >
			产品适用类型</th>
		
		<th class=""  >
			产品类型</th>				
		<th class=""  >
			是否默认产品</th>
		<th class=""  >
			产品激活方式</th>
		<th class=""  >
			产品状态</th>
		<th class=""  >
			产品编号</th>	
		<th class=""  >
			操作</th>
		
		
		

	</tr>
	</thead>
	<c:forEach items="${page.result}" var="product" varStatus = "productStatus">
	<c:choose>
       <c:when test="${productStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>
			<td class="" align="center" >${product.name}&nbsp;</td>			
			<td class="" align="center" >${product.description}&nbsp;</td>
			<td class="" align="center" >
				<c:forEach items="${allowObjectEnum}" var="allowObject">
					<c:if test="${allowObject.code == product.allowObject}">
						${allowObject.description}
					</c:if>
				</c:forEach>
			&nbsp;
			</td>
					
			<td class="" align="center" >
				<c:forEach items="${productTypeEnum}" var="productType">
					<c:if test="${productType.code == product.type}">
						${productType.description}
					</c:if>
				</c:forEach>&nbsp;
			</td>
			
				<c:choose>
	       			<c:when test="${product.isDefault==1}">
	             		<td class="" align="center" >是</td>
	       			</c:when>
	       			<c:when test="${product.isDefault==0}">
	             		<td class="" align="center" >否</td>
	       			</c:when>
	       			<c:otherwise>
	             		<td class="" align="center" >&nbsp;</td>
	       			</c:otherwise>
				</c:choose>	
			
			<td class="" align="center" >
				<c:forEach items="${activationModeEnum}" var="activationMode">
					<c:if test="${activationMode.code == product.activationMode}">
						${activationMode.description}
					</c:if>
				</c:forEach>&nbsp;
			</td>
			<td class="" align="center" >
				<c:forEach items="${productStatusEnum}" var="productStatus">
					<c:if test="${productStatus.code == product.immediacyPass}">
						${productStatus.description}
					</c:if>
				</c:forEach>&nbsp;
			</td>		
			<td class="" align="center" ><c:out value="${product.productCode}" />&nbsp;</td>	
			<td class="" align="center" >
				<a href="javascript:addNewMenu('productJoinMenu.do?productId=${product.id}');">菜单分配</a>
				&nbsp;
				<a href="javascript:void(0);" onclick="productEdit('${product.id}')">编辑</a>
				<c:if test="${product.immediacyPass==1}">
						<a href="javascript:productDelete(${product.id});">下线</a>
				</c:if>
				<c:if test="${product.immediacyPass==2}">
						<a href="javascript:void(0);" onclick="delCheck('${product.id}')"> 删除</a>
				</c:if>
					
			</td>
			
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="productQuery" pageBean="${page}" sytleName="black2"/>


</body>

