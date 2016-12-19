<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>


	<table class="border_all2" width="600" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">证书状态 </font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">商户数</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">使用数</font> </a></td>
		</tr>
	
	         <tr class="trForContent1">
	   
				<td class="border_top_right4" align="center" nowrap>已申请</td>
				<td class="border_top_right4" align="center" nowrap>${dto.certMemberCount}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.certOperatorCount}</td>
			</tr>
	</table>	



