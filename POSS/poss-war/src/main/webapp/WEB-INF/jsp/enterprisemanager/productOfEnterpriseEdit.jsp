<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>	
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"       uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" >
<link rel="stylesheet" href="poss/css/page.css">
<link href="${ctx}/css/main.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen">
<style>
input{ vertical-align:middle;}
.border_all { margin-top:5px; border-collapse:collapse; border:1px solid #8F93A7; }
.border_all td { padding:5px; border-collapse:collapse; border:1px solid #8F93A7; }
.table2, .table2 td, .table3, .table3 td {  border-collapse:collapse; border:1px solid #eee; vertical-align:top; text-align:left; background:#fffff6 }
.table2 .border_r { border-right:1px solid #8F93A7; }
.table3 td { background:#DDF0FE }
.txtC { text-align:center; }
.v_m { vertical-align:middle }
.v_t { vertical-align:top }
.blod { font-weight:700; }
.pl5 { padding-left:5px; }
dl, dt, dd { margin:0; padding:0; }
.table2 h3 { margin-bottom:3px; padding:3px; font-size:12px; background:#eee;  font-weight:400; }
.list1 dd { padding-left:22px; color:#777; }
.list2 dd { margin:4px 0;}
.list2 p { margin:0; padding:2px 0 0 17px; color:#777; }
.ml20 { margin-left:20px; }
.table2 td{ height: 100px; }
</style>


<script src="${ctx}/js/jquery/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<!-- ddr 2012-5-29 -->
<script type="text/javascript">
		
		var defaultProPrams = "";
		<%--
		//<c:forEach var="product" items="${defaultProducts}" varStatus="status">
		//defaultProPrams+="&product=${product.id}";
		//</c:forEach>
		--%>
		function submitForm (){
			var selectIds = $(":input[name=product][checked=true]").serialize();
			var paramsSeria = selectIds+defaultProPrams+"&memberCode=${memberInfo.memberCode}";
			$.post("productOfEnterpriseEdit.do",paramsSeria,function (msg){
				if(msg=="S"){
					alert("修改成功");
				}
				else{
					alert(msg);
				}
			} );
		}
		
		//init....
		$(function (){
			var selectPd = [];
			<c:forEach items="${curProducts }" var="selectedPd">
			selectPd.push("${selectedPd.id}");</c:forEach>
			$(":input[name=product]").val(selectPd);
			$(":input[type=text]").attr("readonly",true).css("background","#ccccdf");
			<c:if test="${type=='view'}">
				$(":input[name=product]").attr("disabled",true);
				$("#bcBtn").remove();
			</c:if>
			
			$(":input[type=checkbox][name=product]").click(function(){
				if(this.checked){
					$("#pro_td_"+this.value).css("background","#e0eedd");
				}else{
					$("#pro_td_"+this.value).css("background","");
				}
			});
			$(":input[type=checkbox][name=product][checked=true]").each(function(){
					$("#pro_td_"+this.value).css("background","#e0eedd");
			});
			
		});
	
</script>

</head>
<body>


<!-- <table style="" width="25%" align="center" border="0" cellpadding="0" cellspacing="0" height="21">
	<tbody>
		<tr>
			<td bgcolor="#000000" height="1"></td>
		</tr>
		<tr>
			<td height="18"><div align="center"><font class="titletext">会员产品配置</font></div></td>
		</tr>
		<tr>
			<td bgcolor="#000000" height="1"></td>
		</tr>
	</tbody>
</table> -->
<h2 class="panel_title">会员产品配置</h2>
<form action="" method="post" >
	<table class="border_all" width="100%" align="center" border="0" cellpadding="1" cellspacing="0">
		<tbody>
			<tr style="background:#DDF0FE;">
				<td colspan="3" style="padding:0;"><table class="table3" width="100%">
						<tr>
							<td width="15%" >会员号：<a style="text-decoration: underline;font-size: 11pt">
								${memberInfo.memberCode}</a></td>
							<td width="15%" >商户名称：<a style="text-decoration: underline;font-size: 11pt">
								${memberInfo.memberName}</a></td>
							<td width="15%" >登录名：<a style="text-decoration: underline;font-size: 11pt">
								${memberInfo.loginName}</a></td>
						</tr>
					</table></td>
			</tr>
			<tr style="background:#f6f6f6">
				<td width="100px" class="txtC">基础服务</td>
				<td width="100px" class="txtC" >人民币</td>
				<td>
				
				<table class="table2" >
					<%int index = 0; %>
					<c:forEach var="listMenu" items="${baseTrees}" varStatus="status">
					<% ++index;  %>		
					<% if (index%5==1){  %><tr></tr><% } %>
					<% if (index==1){  %></tr><% } %>
						<td width="20%">
							<dl class="list1">
								<c:forEach var="mu" items="${listMenu }">
									<c:if test="${mu.level == 1 }"><dt class="blod"><input type="checkbox" checked="checked" class="v_m" disabled="disabled" >${mu.name }</dt></c:if>
									<c:if test="${mu.level == 2  && mu.displayFlag==1 &&mu.type ==2 }"><dd>-${mu.name }</dd></c:if>
								</c:forEach>
							</dl>
							</td>
					
					</c:forEach>
					</tr>
					</table></td>
			</tr>
			<tr>
				<td width="100px" class="txtC" rowspan="${fn:length(productTypeOrderEnum) }">增值服务</td>
					<c:forEach var="ptoe" items="${productTypeOrderEnum}" begin="0" end="0">
						<td style="width:110px" valign="middle" align="center">${ ptoe.desc}</td>
						<td>
							<table class="table2" >
									<tr>
										
										<%int tdIndex=0; %>
										<c:forEach var="product" items="${extraProducts}" varStatus="status">
										<c:if test="${product.productType == ptoe.code }">
											<%if(tdIndex>1&&tdIndex%5==0){%></tr><tr><%}tdIndex++; %>
											<td style="width:150px" id="pro_td_${product.id }">
											<h3><input type="checkbox" name="product" value="${product.id }" /> ${product.name }</h3>
											<dl class="list2">
												<c:forEach var="mu" items="${product.menuList }">
												<c:if test="${mu.level == 1 }"><dt class="blod">${mu.name }</dt></c:if>
													<c:if test="${mu.level == 2 && mu.displayFlag==1 }"><dd>${mu.name }</dd></c:if>
														<c:if test="${mu.level == 3 && mu.displayFlag==1}">
														<p>-${mu.name }</p>
														</c:if>
												</c:forEach>
												</dl>
												</td>
										</c:if>
										</c:forEach>
								</tr>
							</table>
						
						</td>
					</c:forEach>
				</tr>
					
			
		<c:forEach var="ptoe" items="${productTypeOrderEnum}" begin="1" >
				<tr>
						<td style="width:110px" valign="middle" align="center">${ ptoe.desc}</td>
						<td>
							<table class="table2" >
									<tr>
										
										<%int tdIndex=0; %>
										<c:forEach var="product" items="${extraProducts}" varStatus="status">
										<c:if test="${product.productType == ptoe.code }">
											<%if(tdIndex>1&&tdIndex%5==0){%></tr><tr><%}tdIndex++; %>
											<td style="width:150px" id="pro_td_${product.id }">
											<h3><input type="checkbox" name="product" value="${product.id }" /> ${product.name }</h3>
											<dl class="list2">
												<c:forEach var="mu" items="${product.menuList }">
												<c:if test="${mu.level == 1 }"><dt class="blod">${mu.name }</dt></c:if>
													<c:if test="${mu.level == 2 && mu.displayFlag==1 }"><dd>${mu.name }</dd></c:if>
														<c:if test="${mu.level == 3 && mu.displayFlag==1}">
														<p>-${mu.name }</p>
														</c:if>
												</c:forEach>
												</dl>
												</td>
										</c:if>
										</c:forEach>
								</tr>
							</table>
						
						</td>
				</tr>
		</c:forEach>
				
					
				<td colspan="3" class="txtC"><input id="bcBtn" type = "button" value="保存" onclick="submitForm()" >	
					</td>
			</tr>
		</tbody>
	</table>
</form>

</body>



