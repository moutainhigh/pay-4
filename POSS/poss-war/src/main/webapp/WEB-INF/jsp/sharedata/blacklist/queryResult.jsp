<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>黑名单文件列表</title>
	</head>
	
	<body>
		<c:if test="${blackList == null}">
			<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th align="center"">
							<c:choose>
								<c:when test="${result == '服务结果:没有符合条件的记录，请到主管部门进行核实'}">
									服务结果:没有符合条件的记录
								</c:when>
								<c:otherwise>
									${result}
								</c:otherwise>
							</c:choose>
						</th>
					</tr>
				</thead>
			</table>
		</c:if>
		<c:if test="${blackList != null}">
			<c:if test="${blackList.type == '个人黑名单'}">
				<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">黑名单ID：</td>
						<td class="border_top_right4">
							${blackList.id}&nbsp;
						</td>
						<td class=border_top_right4 align="right">黑名单类型：</td>
						<td class="border_top_right4">
							个人黑名单
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">成员单位编码：</td>
						<td class="border_top_right4">
							${blackList.memberUnitCode}&nbsp;
						</td>
						<td class=border_top_right4 align="right">身份证号码：</td>
						<td class="border_top_right4">
							${blackList.identityCode}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">姓名：</td>
						<td class="border_top_right4">
							${blackList.name}&nbsp;
						</td>
						<td class=border_top_right4 align="right">发生地区：</td>
						<td class="border_top_right4">
							${blackList.occuredArea}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">录黑途径：</td>
						<td class="border_top_right4">
							${blackList.way}&nbsp;
						</td>
						<td class=border_top_right4 align="right">黑名单事件：</td>
						<td class="border_top_right4">
							${blackList.event}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">手机号码：</td>
						<td class="border_top_right4">
							${blackList.mobile}&nbsp;
						</td>
						<td class=border_top_right4 align="right">银行卡号：</td>
						<td class="border_top_right4">
							${blackList.bankCode}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">开户行：</td>
						<td class="border_top_right4">
							${blackList.bankName}&nbsp;
						</td>
						<td class=border_top_right4 align="right">邮箱：</td>
						<td class="border_top_right4">
							${blackList.email}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">ICP编号：</td>
						<td class="border_top_right4">
							${blackList.icpCode}&nbsp;
						</td>
						<td class=border_top_right4 align="right">ICP备案人：</td>
						<td class="border_top_right4">
							${blackList.icpMember}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">支付人：</td>
						<td class="border_top_right4">
							${blackList.payerName}&nbsp;
						</td>
						<td class=border_top_right4 align="right">标记时间：</td>
						<td class="border_top_right4">
			   	   			${blackList.markTimeStr}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">数据状态：</td>
						<td class="border_top_right4" colspan="3">
							<c:if test="${blackList.status == 1}">黑名单</c:if>
							<c:if test="${blackList.status == 2}">无效黑名单</c:if>&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">固定电话号码：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.telephone}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">IP地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.ip}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">MAC地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.mac}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">URL地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.urlAddress}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">URL跳转地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.urlBranchAddress}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.address}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">黑名单事件备注：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.remark}&nbsp;
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${blackList.type == '机构黑名单'}">
				<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">黑名单ID：</td>
						<td class="border_top_right4">
							${blackList.id}&nbsp;
						</td>
						<td class=border_top_right4 align="right">黑名单类型：</td>
						<td class="border_top_right4">
							机构黑名单
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">成员单位编码：</td>
						<td class="border_top_right4">
							${blackList.memberUnitCode}&nbsp;
						</td>
						<td class=border_top_right4 align="right">法人代表公民身份证号码：</td>
						<td class="border_top_right4">
							${blackList.identityCode}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">法人代表姓名：</td>
						<td class="border_top_right4">
							${blackList.name}&nbsp;
						</td>
						<td class=border_top_right4 align="right">发生地区：</td>
						<td class="border_top_right4">
							${blackList.occuredArea}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">录黑途径：</td>
						<td class="border_top_right4">
							${blackList.way}&nbsp;
						</td>
						<td class=border_top_right4 align="right">黑名单事件：</td>
						<td class="border_top_right4">
							${blackList.event}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">手机号码：</td>
						<td class="border_top_right4">
							${blackList.mobile}&nbsp;
						</td>
						<td class=border_top_right4 align="right">银行卡号：</td>
						<td class="border_top_right4">
							${blackList.bankCode}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">开户行：</td>
						<td class="border_top_right4">
							${blackList.bankName}&nbsp;
						</td>
						<td class=border_top_right4 align="right">邮箱：</td>
						<td class="border_top_right4">
							${blackList.email}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">ICP编号：</td>
						<td class="border_top_right4">
							${blackList.icpCode}&nbsp;
						</td>
						<td class=border_top_right4 align="right">ICP备案人：</td>
						<td class="border_top_right4">
							${blackList.icpMember}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">支付人：</td>
						<td class="border_top_right4">
							${blackList.payerName}&nbsp;
						</td>
						<td class=border_top_right4 align="right">营业执照编号：</td>
						<td class="border_top_right4">
							${blackList.businessCode}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">组织结构代码：</td>
						<td class="border_top_right4">
							${blackList.orgCode}&nbsp;
						</td>
						<td class=border_top_right4 align="right">机构名称：</td>
						<td class="border_top_right4">
							${blackList.orgName}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">数据状态：</td>
						<td class="border_top_right4">
							<c:if test="${blackList.status == 1}">黑名单</c:if>
							<c:if test="${blackList.status == 2}">无效黑名单</c:if>&nbsp;
						</td>
						<td class=border_top_right4 align="right">标记时间：</td>
						<td class="border_top_right4">
			   	   			${blackList.markTimeStr}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">固定电话号码：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.telephone}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">IP地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.ip}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">MAC地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.mac}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">URL地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.urlAddress}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">URL跳转地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.urlBranchAddress}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">地址：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.address}&nbsp;
						</td>
					</tr>
					<tr class="trForContent1">
						<td class=border_top_right4 align="right">黑名单事件备注：</td>
						<td class="border_top_right4" colspan="3">
							${blackList.remark}&nbsp;
						</td>
					</tr>
				</table>
			</c:if>
		</c:if>
	</body>
</html>