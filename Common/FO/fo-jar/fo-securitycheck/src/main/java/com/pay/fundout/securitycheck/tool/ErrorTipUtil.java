package com.pay.fundout.securitycheck.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pay.fundout.securitycheck.exception.DeniedException;
import com.pay.inf.dto.PageMsgDto;

public class ErrorTipUtil {
	private static final Pattern innerPattern = Pattern.compile("\\{[^{]*\\}");
	private static Map<String, PageMsgDto> pageMsgMap = new HashMap<String, PageMsgDto>();

	public static void addErrorTip(Map<String, PageMsgDto> errorTip) {
		if (errorTip != null) {
			pageMsgMap.putAll(errorTip);
		}
	}

	public static void replaceErrorTip(DeniedException denyException,
			int index, String newTips) {
		String[] tips = denyException.getTips();
		if (tips != null || tips.length > index) {
			tips[index] = newTips;
		}
	}

	public static String getErrorTip(DeniedException denyException) {
		replaceErrorTipIfNecessary(denyException);

		String[] tips = denyException.getTips();
		PageMsgDto pageMsgDTO = pageMsgMap.get(denyException.getCode());
		if (pageMsgDTO == null) {
			return denyException.getCode();
		}

		String errorBefore = pageMsgDTO.getMsg();
		if (tips != null) {
			Matcher matcher = innerPattern.matcher(errorBefore);
			StringBuffer temp = new StringBuffer();
			while (matcher.find()) {
				String matchString = matcher.group();
				matcher.appendReplacement(temp, tips[Integer
						.parseInt(matchString.substring(1,
								matchString.length() - 1))]);
			}
			matcher.appendTail(temp);

			return temp.toString();
		}
		return errorBefore;
	}

	private static void replaceErrorTipIfNecessary(DeniedException denyException) {
		// String[] tips = denyException.getTips();
	}
}
