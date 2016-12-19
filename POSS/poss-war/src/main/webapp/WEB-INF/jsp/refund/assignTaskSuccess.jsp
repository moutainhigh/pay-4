<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<br/>
<br/>
<br/>
<form name="backQueryForm" method="POST" action="${requestUrl}">
<p>
	<center>
		<font color="blue">
			<b>
				${resultMsg}
			</b>
		</font>
		<br/>
		<br/>
		<input type="button" name="btnBack" class="button4" value="返 回" onclick="backPage();"/>
	</center>
</p>
</form>
<script type="text/javascript">
	function backPage(){
		document.backQueryForm.submit();
	}
</script>
