<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
function rebuild(method,type){
	if(confirm('确认更新？')){
		window.location.href='${ctx}/rebuildLucene.do?method=' + method + "&type=" + type;
	}
}
</script>
<body>
<h2 class="panel_title">重新生成联行号索引</h2>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td align="center" class="border_top_right4"><a href="javascript:rebuild('rebuildWebsite')">通知前台重生成开户行索引</a></td>
	</tr>
	<tr class="trForContent1">
		<td align="center" class="border_top_right4"><a href="javascript:rebuild('rebuildWebsite','specia')">通知前台重生成中国银行开户行索引</a></td>
	</tr>
	<tr class="trForContent1">
		<td align="center" class="border_top_right4"><a href="javascript:rebuild('rebuildPoss')">后台重支付平台成开户行索引</a></td>
	</tr>
	<tr class="trForContent1">
		<td align="center" class="border_top_right4"><a href="javascript:rebuild('rebuildPoss','specia')">后台重生成中国银行开户行索引</a></td>
	</tr>
</table>
</body>
</html>