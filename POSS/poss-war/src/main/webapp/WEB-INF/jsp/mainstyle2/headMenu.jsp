<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>head菜单</title>
<link href="${ctx}/css/head.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		$(document).ready(function(){

		parent.frames["menuFrame"].location.href = "${ctx}/menu.do?method=left&menuCode="+${menuCode};
		
		
        $(".head_menu > li").mousedown(function(){ 
				$(".head_menu > li").addClass("noselect");
				$(this).removeClass("noselect");
				$(this).addClass("select");})						
		});
		
		//$(".head_menu > li:l_${menuCode}").addClass("select");
		//$("#l_${menuCode}").removeClass("noselect");
		//$("#l_${menuCode}").addClass("select");
		//alert($("#l_${menuCode}"));

		function processUrl(code){
			parent.frames["menuFrame"].location.href = "${ctx}/menu.do?method=left&menuCode=" + code;
		}
						
</script>
</head>

<body>
<div class="head_top"></div>
<div class="head_menu">
<c:forEach var="item" items="${headMenu}">
	<li id="l_${item.code}" class="noselect" onmousedown="processUrl('${item.code}')">
		${item.text}
	</li>
</c:forEach>
</div>
</body>
</html>
