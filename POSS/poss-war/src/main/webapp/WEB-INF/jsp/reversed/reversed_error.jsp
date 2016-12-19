<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>

<font color="red">${ msg }</font>

<br/>
<input type="button" id="backBtn" name="backBtn" value="返回冲正申请" onclick="entryOrderQueryIn()" />
<input type="button" id="backBtn" name="backBtn" value="返回冲正审核" onclick="queryReversedList()" />

<script type="text/javascript">
	function queryReversedList(){
		//window.location.href = "reversed.do?method=queryReversedList";
		window.location.href = "reversed.queryReversedList.do";
	}
	function entryOrderQueryIn(){
		//window.location.href = "reversed.query.do?method=entryOrderQueryIn";
		window.location.href = "reversed.query.entryOrderQueryIn.do";
	}
</script>
