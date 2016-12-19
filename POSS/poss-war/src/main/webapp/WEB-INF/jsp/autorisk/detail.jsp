<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:if test="${not empty error}">
	<li style="color: red;">${error}</li>
</c:if>
<c:if test="${empty error}">
	<div>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="18">
					<div>
						<font class="titletext">基本信息</font>
					</div>
				</td>
			</tr>
		</table>
		<br>
		
		<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
			<tr>
				<td class="border_top_right4">
					会员号:
				</td>
				<td class="border_top_right4">
					${dto.memberCode}
				</td>
				<td class="border_top_right4">
					会员名称:
				</td>
				<td class="border_top_right4">
					${dto.memberName}
					<c:if test="${empty dto.memberName}">&nbsp;</c:if>
				</td>
				<td class="border_top_right4">
					建立时间:
				</td>
				<td class="border_top_right4">
					<fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td class="border_top_right4">
					风险等级:
				</td>
				<td class="border_top_right4">
					${dto.riskLeveCode}
					<c:if test="${empty dto.riskLeveCode}">&nbsp;</c:if>
				</td>
				<td class="border_top_right4">
					MCC:
				</td>
				<td class="border_top_right4">
					${dto.inIndustry}
					<c:if test="${empty dto.inIndustry}">&nbsp;</c:if>
				</td>
				<td class="border_top_right4">
					账户余额:
				</td>
				<td class="border_top_right4">
					${dto.balance}
					<c:if test="${empty dto.balance}">&nbsp;</c:if>
				</td>
			</tr>
			<tr>
				<td class="border_top_right4">
					网址:
				</td>
				<td class="border_top_right4">
					${dto.website}
					<c:if test="${empty dto.website}">&nbsp;</c:if>
				</td>
				<td class="border_top_right4">
					邮箱:
				</td>
				<td class="border_top_right4" colspan="3">
					${dto.email}
					<c:if test="${empty dto.email}">&nbsp;</c:if>
				</td>
			</tr>
			<tr>
				<td class="border_top_right4">
					地址:
				</td>
				<td class="border_top_right4" colspan="5">
					${dto.address}
					<c:if test="${empty dto.address}">&nbsp;</c:if>
				</td>
			</tr>
			<tr>
				<td class="border_top_right4">
					联系电话:
				</td>
				<td class="border_top_right4">
					${dto.mobile}
					<c:if test="${empty dto.mobile}">&nbsp;</c:if>
				</td>
				<td class="border_top_right4">
					签约人:
				</td>
				<td class="border_top_right4" colspan="3">
					${dto.signName}
					<c:if test="${empty dto.signName}">&nbsp;</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div id="ipDiv">
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="18">
					<div>
						<font class="titletext">IP关联</font>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>会员号</th>
					<th>会员名称</th>
					<th>关联IP</th>
					<th>IP城市</th>
					<th>主账户最近登录日期</th>
					<th>会员类型</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ip" items="${page.result}" varStatus="v">
					<tr>
						<td>
							${ip.memberCode}
						</td>
						<td>
							${ip.memberName}
						</td>
						<td>
							${ip.ip}
						</td>
						<td>
							${ip.city}
						</td>
						<td>
							${ip.latestLoginTime}
						</td>
						<td>
							<c:if test="${ip.memberType == 1}">个人</c:if>
							<c:if test="${ip.memberType == 2}">企业</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<li:pagination methodName="searchIp" pageBean="${page}" sytleName="black2" />
	</div>
	
	<div>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="18">
					<div>
						<font class="titletext">交易汇总</font>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>时间</th>
					<th>月总入款金额</th>
					<th>月总退款金额</th>
					<th>月总入款交易笔数</th>
					<th>月平均单笔入款交易额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="transSum" items="${transSumList}" varStatus="v">
					<tr>
						<td>
							${transSum.time}
						</td>
						<td>
							${transSum.toalInAmount}
						</td>
						<td>
							${transSum.totalOutAmount}
						</td>
						<td>
							${transSum.totalInNums}
						</td>
						<td>
							${transSum.averageInAmount}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<c:if test="${memberType == 2}">
		<div>
			<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
				<tr>
					<td height="18">
						<div>
							<font class="titletext">交易网址</font>
						</div>
					</td>
				</tr>
			</table>
			<br>
			<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>入款交易网址</th>
						<th>入款交易笔数</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="transWebsite" items="${transWebsiteList}" varStatus="v">
						<tr>
							<td>
								${transWebsite.inTransWebsite}
							</td>
							<td>
								${transWebsite.inTransNums}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</c:if>
<input type="hidden" name="code" id="code" value="${dto.memberCode}" />