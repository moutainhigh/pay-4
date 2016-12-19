<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${ctx}/css/leftmenu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		$(document).ready(function(){
        $(".left_common > li").mousedown(function(){ 
			$(".left_common > li").removeClass("select");
			$(".left_common > li").addClass("noselect");
			$(this).removeClass("noselect");
			$(this).addClass("select");})					
		});	
		
		function processBodyUrl(url){
			parent.frames["bodyFrame"].location.href = "${ctx}/" + url;
		}

</script>	
</head>

<body style="overflow-x:hidden;overflow-y:yes; ">

	<c:forEach var="item" items="${leftMenu}">
	
		<div id="tagtitle_${item.code}" class="left_title">
			<li id="tag_${item.code}" class="title">${item.text}</li>
		</div>
		
		<script type="text/javascript">
			$("#tagtitle_${item.code} > #tag_${item.code}").mousedown(function(){ 
			 
			 if ($("#tags_${item.code}").css("display") == "none") {
			        $("#tagtitle_${item.code}").css("background" , "url(${ctx}/images/jqueryMenu/left_menu_top_1.gif) no-repeat");
			        $("body > #tags_${item.code}").removeClass("hide");
					$("body > #tags_${item.code}").addClass("left_common");
			 } else {		
	                //如果鼠标移到class为stripe的表格的tr上时，执行函数
	                $("#tagtitle_${item.code}").css("background" , "url(${ctx}/images/jqueryMenu/left_menu_top_2.gif) no-repeat");
					$("body > #tags_${item.code}").removeClass("left_common");
					$("body > #tags_${item.code}").addClass("hide"); }

				
			})
		</script>
		
		<div id="tags_${item.code}" class="left_common">
			<c:forEach var="sitem" items="${item.sonMenuList}">
				<li id="l_${sitem.code}" class="noselect" onmousedown="processBodyUrl('${sitem.url}');">${sitem.text}</li>
			</c:forEach>
		</div>
		
	</c:forEach>
	
</body>




</html>
