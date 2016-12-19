<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>

<p></p>


<c:if test="${ refusedStr != '' && refusedStr != null }">
<font color="blue">被拒绝的有：${ refusedStr }</font>
</c:if>

<br/>
<c:if test="${ succStr != '' && succStr != null }">
<font color="green">冲正成功的有：${ succStr }</font>
</c:if>

<br/>
<c:if test="${ dunningStr != '' && dunningStr != null }">
<font color="yellow">冲正失败的有：${ dunningStr }</font>
</c:if>

<br/>
<c:if test="${ exceptionStr != '' && exceptionStr != null }">
<font color="red">冲正异常的有：${ exceptionStr }</font>
</c:if>

<input type="button" id="backBtn" name="backBtn" value="返回冲正审核" onclick="queryReversedList()" />

<script type="text/javascript">
	function queryReversedList(){
		//window.location.href = "reversed.do?method=queryReversedList";
		window.location.href = "reversed.queryReversedList.do";
	}
</script>