<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<textarea id="audit_desc" name="audit_desc" cols="40" rows="10"></textarea>
<input type="button" id="submitBtn" name="submitBtn" value="确定" onclick="returnPhone('yes')" />
<input type="button" id="cannelBtn" name="cannelBtn" value="取消" onclick="returnPhone('no')" />

<script language="javascript">
	function returnPhone(value){
		var audit_desc = $("#audit_desc").val();
		if(value != 'yes'){
			audit_desc = "";
		}
		window.returnValue=audit_desc;
		window.close();
	}
</script>