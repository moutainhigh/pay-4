<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<script type="text/javascript">
function update(id){
	parent.addMenu("充值渠道分配规则修改","${ctx}/mobileChannelRule.do?method=channelRuleInfo&id="+id);
}
</script>
</head>
   <table id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
              <tbody>
              <tr style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
                <td>地区</td>
                <td>运营商</td>
                <td>直充渠道</td>
                <td>比例</td>
                <td>操作</td>
               </tr>
               <c:if test="${empty ruleList}">
                  <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td colspan="16" align="center">没有记录</td>
              	  </tr>
              </c:if>               
              <c:forEach items="${ruleList}" var="channelRule">
                 <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                   <td>${channelRule.mobileProvinces}</td>
                   <td>
                    <c:if test="${channelRule.bossType eq 0}">移动</c:if>
                   	<c:if test="${channelRule.bossType eq 1}">联通</c:if>
                   	<c:if test="${channelRule.bossType eq 2}">电信</c:if>
                   	<c:if test="${channelRule.bossType eq '默认'}">默认</c:if>
                   </td>
                   <td>
                   <c:forEach items="${channelRule.channelInfos}" var="channelList">
                   		${channelList.channelCode}<br/>
                   </c:forEach>
                   </td>
                   <td>
                   <c:forEach items="${channelRule.channelInfos}" var="channelList">
                   		${channelList.ratio*100}%<br/>
                   </c:forEach>
                   </td>
                   <td><a href="#" onclick="update('${channelRule.ruleId}')">修改</a></td>
			     </tr>
              </c:forEach>
           </tbody>
           </table>