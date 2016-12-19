<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


		<title></title>
		<script type="text/javascript">
			$(function() {
				var isLogin = true;
				
				<c:if test="${isLogin == false}">isLogin = false;</c:if>
				
				if (!isLogin) {
					window.location = "${serverPath}basic/manager";
					return;
				}
			
				$("#accordion").accordion({ header: "h3" });
				$('#accordion').find('li').hover(
					function() { $(this).addClass('ui-state-hover'); }, 
					function() { $(this).removeClass('ui-state-hover'); }
				);
			});
		</script>
		<div id="accordion">
			<c:forEach var="item" items="${JQmenu}">
			<div>
				<h3><a href="#">${item.text}</a></h3>
				<div name="list_menu_list_1" class="left_menu_list">
					<c:forEach var="sitem" items="${item.sonMenuList}">
					<li id="l_${sitem.code}">
						<a id="${serverPath}basic/${sitem.url}" href="#" onclick="loadHandlePage(this)">${sitem.text}</a>
					</li>
					</c:forEach>
				</div>
			</div>
			</c:forEach>
		</div>

