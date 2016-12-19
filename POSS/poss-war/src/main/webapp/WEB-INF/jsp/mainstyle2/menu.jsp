<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link type="text/css" href="${ctx}/style/basic.css" rel="stylesheet" />
<html>
	<head>
		<title></title>
		<script type="text/javascript">
			$(function() {
				$("#accordion").accordion({ header: "h3" });
				$('#accordion').find('li').hover(
					function() { $(this).addClass('ui-state-hover'); }, 
					function() { $(this).removeClass('ui-state-hover'); }
				);
			});
		</script>
	</head>

	<body>
		<div id="accordion">
			<c:forEach var="item" items="${leftMenu}">
			<div>
				<h3><a href="#">${item.text}</a></h3>
				<div name="list_menu_list_1" class="left_menu_list">
					<c:forEach var="sitem" items="${item.sonMenuList}">
					<li id="l_${sitem.code}">
						<a id="${sitem.url}" href="#" onclick="loadHandlePage(this)">${sitem.text}</a>
					</li>
					</c:forEach>
				</div>
			</div>
			</c:forEach>
		</div>
	</body>
</html>

