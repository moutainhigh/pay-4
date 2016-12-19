<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>



<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">
function spEdit(spMerchantCardId){
			location.href = "${ctx}/specialMerchantCardEdit.do?sp_merchant_id=" + spMerchantCardId;
		}
function spDelete(spMerchantCardId){
	if(confirm("确定要删除吗?")){
		location.href = "${ctx}/specialMerchantCardDel.do?sp_merchant_id=" + spMerchantCardId;
	}
}		
</script>
</head>

<body>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">卡种名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">卡ID</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">折扣信息</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">操作</font> </a></td>
	</tr>
	 <c:forEach items="${spCardInfoList}" var="cardInfo">    
        <tr class="trForContent2">
          
          <td class="border_top_right4" align="center" nowrap>${cardInfo.enumName}&nbsp;</td>
          <td class="border_top_right4" align="center" nowrap>${cardInfo.cardTypeId}&nbsp;</td>
          <td class="border_top_right4" align="center" nowrap>${cardInfo.discountInfo}&nbsp;</td>
          <td class="border_top_right4" align="center" nowrap>
				<a href="javascript:modifyCard(${cardInfo.spMerchantCardId});">编辑</a>
				&nbsp;
				<a href="javascript:removeCard(${cardInfo.spMerchantCardId});">删除</a>
			</td>	
        </tr>
    </c:forEach>
	
</table>

</body>

