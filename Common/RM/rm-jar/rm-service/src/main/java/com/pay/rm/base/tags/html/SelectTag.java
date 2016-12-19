package com.pay.rm.base.tags.html;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.pay.rm.base.exception.PossUntxException;
import com.pay.rm.base.tags.AbstractTag;

/**
 * select下拉框
 * @Description 
 * @project 	poss-base
 * @file 		SelectTag.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-8-28		Volcano.Wu			Create
 */
public class SelectTag extends AbstractTag {

	private static final long serialVersionUID = 1L;
	private String selected = "";  //默认选中的值
	private String title = ""; //标题
	private Boolean defaultStyle = false; //默认样式，增加请选择
	private String otherAttribute = ""; //String 其他的属性在select 上的，如onchange=
	private Boolean disabled = false; //是否启用

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setOtherAttribute(String otherAttribute) {
		this.otherAttribute = otherAttribute;
	}
	
	public void setDefaultStyle(Boolean defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public int doStartTag() {
		try {
			print(doFormatSub(getDatas()));
		} catch (PossUntxException e) {
			log.error(e.getMessage(),e);
		}
		return (BodyTagSupport.EVAL_BODY_BUFFERED);
	}
	
	protected String doFormatSub(List<Map<String, String>> items) {
		
		StringBuffer selectTagHtml = new StringBuffer();
		selectTagHtml.append("<select name=\""  + name + "\" id=\"" + name + "\" " 
				+ (StringUtils.isNotEmpty(otherAttribute) ? otherAttribute : "") +
				(disabled ? " disabled" : "")
				+" class=\"" + sytleName + "\" title=\"" + title + "\">");
		if(defaultStyle)selectTagHtml.append("<option value=\"\">请选择 </option>");
		//如果items
		if(this.itemList != null ) {
			log.debug("itemList:"+itemList);
			items = itemList;
		}
		log.debug("items:"+items);
		for (Map<String, String> map : items) {
			String text = map.get("text");
			String value = map.get("value");
			selectTagHtml.append("<option value=\"" + value + "\"" + (selected.equals(value) ? " selected" : "") + ">" + text + "</option>");
		}
		selectTagHtml.append("</select>");
		return selectTagHtml.toString();
	}
}
