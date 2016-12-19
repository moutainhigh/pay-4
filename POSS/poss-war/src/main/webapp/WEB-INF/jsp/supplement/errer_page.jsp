<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
出现异常，请检查！

<font color="red">${ msg }</font>

<br/>
<input type="button" id="back" name="back" value="返回重新上传" onclick="goonSupplement()" />
<input type="button" id="btn" name="btn" value="返回重新审核" onclick="supplementAudit()" />


<script type="text/javascript">

	function goonSupplement(){
		window.location.href = "supplement.upload.do?method=entryUpload";
	}

	function supplementAudit(){
		window.location.href = "supplement.upload.do?method=queryRemedyItemListByExample";
	}

</script>