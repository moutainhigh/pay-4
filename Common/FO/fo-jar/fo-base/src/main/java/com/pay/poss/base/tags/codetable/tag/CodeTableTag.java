/**
 * CodeTableTag
 * Description: 基础代码表在页面的处理。
 * @author Henry_Zeng
 */
package com.pay.poss.base.tags.codetable.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.tags.codetable.dto.CodeTableDTO;
import com.pay.poss.base.tags.codetable.service.CodeTableService;

/**
 * 基础代码表在页面的处理。
 * 
 * 基础代码表在页面参数的存取方法及页面处理。
 */
public class CodeTableTag extends BodyTagSupport {
	
	private transient CodeTableService codeTableService = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 代码表名字
	 */
	private String codeTableId = null;

	/**
	 * 页面上该对象的名字
	 */
	private String fieldName = null;

	/**
	 * 缺省选中的值
	 */
	private String selectedValue = "";
	
	/**
	 * 缺省值，当缺省选中的值为空是，使用此值
	 */
	private String defaultValue = "";

	/**
	 * 样式风格 
	 */
	private String style;

	// 对代码表进行筛选的where条件
	// private String msWhereClause = null;

	/**
	 * 文本框显示长度，仅当pStyle=codedesc时有效
	 */
	private String displayLength = "10";

	/**
	 * 文本框输入最大长度，仅当pStyle=codedesc时有效
	 */
	private String maxLength = "15";

	/**
	 * 文本描述栏位长度，仅当pStyle=codedesc时有效
	 */
	private String descLength = "20";

	/**
	 * 附加属性，作为控件一部分输出 
	 */
	private String props = "";

	/**
	 * 用户定义的onblur属性
	 */
	private String onBlur = "";

	/**
	 * 用户定义的onblur之前的事件
	 */
	private String beforeBlur = "";

	/**
	 * 用户定义的值变化事件
	 */
	private String onChange = "";

	/**
	 * true (将显示”请选择”)、 false(不显示“请选择”)(default)
	 */
	private String attachOption;

	/**
	 * 输入错误代码时的提示信息 
	 */
	private String errMsg = "输入的代码不存在";

	/**
	 * 校验错误后，焦点是否需要保留(codedes下)
	 */
	private String errFocus;

	/**
	 * 获取是否显示"请选择"
	 * @return attachOption
	 */
	public String getAttachOption() {
		return attachOption;
	}
	
	/**
	 * 设置是否显示"请选择"
	 * @param attachOption
	 * @throws JspException
	 */
	public void setAttachOption(final String attachOption) throws JspException {
		this.attachOption = attachOption ;
	}
	/**
	 * 获取用户定义的onblur之前的事件
	 * @return beforeBlur
	 */
	public String getBeforeBlur() {
		return beforeBlur;
	}
	/**
	 * 设置用户定义的onblur之前的事件
	 * @param beforeBlur
	 * @throws JspException
	 */
	public void setBeforeBlur(final String beforeBlur) throws JspException {
		this.beforeBlur = beforeBlur ;
		if (this.beforeBlur == null) {
			this.beforeBlur = "";
		}
		if (!this.beforeBlur.endsWith(";")) {
			this.beforeBlur += ";";
		}
	}

	/**
	 * 获取代码表名字
	 * @return codeTableId
	 */
	public String getCodeTableId() {
		return codeTableId;
	}
	
	/**
	 * 设置代码表名字
	 * @param codeTableId 代码表名字
	 * @throws JspException
	 */
	public void setCodeTableId(final String codeTableId) throws JspException {
		this.codeTableId = codeTableId;
	}

	/**
	 * 获取文本描述栏位长度 
	 * @return descLength
	 */
	public String getDescLength() {
		return descLength;
	}
	
	/**
	 * 设置文本描述栏位长度
	 * @param descLength 文本描述栏位长度
	 * @throws JspException
	 */
	public void setDescLength(final String descLength) throws JspException {
		this.descLength = descLength ;
	}

	/**
	 * 获取文本框显示长度
	 * @return displayLength
	 */
	public String getDisplayLength() {
		return displayLength;
	}

	/**
	 * 设置文本框显示长度
	 * @param displayLength 文本框显示长度
	 * @throws JspException
	 */
	public void setDisplayLength(final String displayLength) throws JspException {
		this.displayLength = displayLength;
	}
	/**
	 * 获取校验错误后，焦点是否需要保留
	 * @return errFocus
	 */
	public String getErrFocus() {
		return errFocus;
	}
	
	/**
	 * 设置校验错误后，焦点是否需要保留
	 * @param errFocus 校验错误后，焦点是否需要保留
	 * @throws JspException
	 */
	public void setErrFocus(final String errFocus) throws JspException {
		this.errFocus = errFocus;
	}

	/**
	 * 获取输入错误代码时的提示信息
	 * @return  errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * 设置输入错误代码时的提示信息
	 * @param errMsg 输入错误代码时的提示信息
	 * @throws JspException
	 */
	public void setErrMsg(final String errMsg) throws JspException {
		this.errMsg = errMsg;
	}

	/**
	 * 获取页面上该对象的名字
	 * @return fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	
	/**
	 * 设置页面上该对象的名字
	 * @param fieldName 页面上该对象的名字
	 * @throws JspException
	 */
	public void setFieldName(final String fieldName) throws JspException {
		this.fieldName = fieldName;
	}
	/**
	 * 获取样式风格
	 * @return style 
	 */
	public String getStyle() {
		return style;
	}
	
	/**
	 * 设置样式风格
	 * @param style 样式风格
	 * @throws JspException
	 */
	public void setStyle(final String style) throws JspException {
		this.style = style;
	}

	/**
	 * 获取文本框输入最大长度
	 * @return maxLength
	 */
	public String getMaxLength() {
		return maxLength;
	}
	
	/**
	 * 设置文本框输入最大长度
	 * @param maxLength 文本框输入最大长度
	 * @throws JspException
	 */
	public void setMaxLength(final String maxLength) throws JspException {
		this.maxLength = maxLength;
	}
	
	/**
	 * 获取用户定义的onblur属性
	 * @return onBlur
	 */
	public String getOnblur() {
		return onBlur;
	}

	/**
	 * 设置用户定义的onblur属性
	 * @param onBlur 用户定义的onblur属性
	 * @throws JspException
	 */
	public void setOnblur(final String onBlur) throws JspException {
		this.onBlur = onBlur;
	}
	/**
	 * 获取用户定义的值变化事件
	 * @return onChange
	 */
	public String getOnChange() {
		return onChange;
	}

	/**
	 * 设置用户定义的值变化事件
	 * @param onChange 用户定义的值变化事件
	 * @throws JspException
	 */
	public void setOnChange(final String onChange) throws JspException {
		this.onChange = onChange;
		if (onChange == null)
			this.onChange = "";
		if (onChange.endsWith(";")) {
			this.onChange = onChange;
		} else {
			this.onChange = onChange + ";";
		}
	}
	
	/**
	 * 获取附加属性
	 * @return props
	 */
	public String getOtherProps() {
		return props;
	}

	/**
	 * 设置附加属性
	 * @param props 附加属性
	 * @throws JspException
	 */
	public void setProps(final String props) throws JspException {
		this.props = props;
	}
	/**
	 * 获取缺省选中的值
	 * @return selectedValue
	 */
	public String getSelectedValue() {
		return selectedValue;
	}

	/**
	 * 设置缺省选中的值
	 * @param selectedValue 缺省选中的值
	 * @throws JspException
	 */
	public void setSelectedValue(final String selectedValue) throws JspException {
		this.selectedValue = selectedValue;
	}
	
	/**
	 * 获取缺省值
	 * @return defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * 设置缺省值
	 * @param defaultValue
	 * @throws JspException
	 */
	public void setDefaultValue(final String defaultValue) throws JspException {
		this.defaultValue = defaultValue;
	}

	/**
	 * 计算JSP标记
	 * @return 
	 * @throwsJspException
	 */
	public int doStartTag() throws JspException {
		try {
			codeTableService = (CodeTableService) ContextService.getBean(CodeTableService.CODE_TABLE_SERVICE_NAME);
			if (codeTableId == null) {
				throw new JspException("(代码表TAG)没有设置codeTableId属性值");
			}
			
			//用缺省值代替
			if (this.selectedValue == null || this.selectedValue.trim().length()==0) {
			  if (this.defaultValue !=null && this.defaultValue.trim().length()>0) {
			    this.selectedValue = this.defaultValue;
			  }
			}
			
			String sResult = generateCodeList();
		
			JspWriter writer = pageContext.getOut();

			try {
				writer.print(sResult);
			} catch (IOException e) {
				e.printStackTrace();
				throw new JspException(e);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		return (BodyTagSupport.EVAL_BODY_BUFFERED);
	}

	public int doEndTag() throws JspException {
		return (BodyTagSupport.EVAL_PAGE);
	}

	/**
	 * 在页面上显示一个文本框和下拉框。
	 * 里面包含相应的代码表数据 如果是文本框，在第一个文本框输入代码后会在第二个文本框显示相应的描述，如果是下拉框，
	 * 在下拉框中包含了所有的代码 本方法将代替原先的getHtmlListOption方法 根据出入参数的不同，共有六个方法变体。
	 * 
	 * @param inputFieldName：页面上该对象的名字
	 * @param codeTableId   ：代码表Id
	 * @param selectedValue ：缺省选中的值
	 * @param style			：样式风格，STYLE_LISTBOX、STYLE_TEXTBOX
	 * @param displayLength ：文本框显示长度，仅当pStyle=STYLE_TEXTBOX时有效
	 * @param maxLength		：文本框输入最大长度，仅当pStyle=STYLE_TEXTBOX时有效
	 * @param request		：request对象包含了当前用户的语言选择信息，
	 * @return ： 相应对象的HTML代码
	 * @throws Exception
	 */
	public String generateCodeList() throws Exception {
		String sResult = null;

		if ("select".equalsIgnoreCase(this.style)) {

			sResult = generateListBoxStyleCodeList(false);

		} else if ("selectCodeDesc".equalsIgnoreCase(this.style)) {

			sResult = generateListBoxStyleCodeList(true);

		} else if ("codeDesc".equalsIgnoreCase(style)) {

			sResult = generateTextBoxStyleCodeList();

		} else if ("desc".equalsIgnoreCase(style)) {

			sResult = generateDescriptionOnly();

		} else {

			throw new Exception("(代码表TAG)无效的Style属性值:" + style);
		}

		return sResult;
	}

	/**
	 * 返回一个下拉框列表，里面包含所有代码表内容
	 * 
	 * @param inputFieldName ：页面上该对象的名字
	 * @param pTableName     ：代码表名字
	 * @param pSelectedValue ：缺省选中的值
	 * @param request        ：request对象包含了当前用户的语言选择信息，
	 * @param pWhereClause   ：对代码表进行筛选的where条件
	 * @return 下拉框列表的HTML 代码
	 * @throws Exception
	 */
	private String generateListBoxStyleCodeList(boolean addCodeToSelect) throws Exception {
		// 取得代码表数据
		List<CodeTableDTO> optionList = codeTableService.getCodeTables(this.codeTableId);

		// 格式成HTML SELECT标签需要的格式
		String sOption = generateOptionsFromList(optionList,addCodeToSelect);
		StringBuffer sResult = new StringBuffer();
		sResult.append("<select name=\"").append(fieldName).append("\" id=\"").append(fieldName).append("\"")
				.append(props);
		if (onBlur != null && !"".equals(onBlur)) {
			sResult.append(" onblur=\"javascript:").append(beforeBlur).append(
					";").append(onBlur).append("\"");
		}
		if (onChange != null && !"".equals(onChange)) {
			sResult.append(" onchange=\"javascript:").append(onChange).append(
					"\"");
		}

		sResult.append(" >\n");
		// if (msStartOptions != null && !"".equals(msStartOptions)) {
		// sResult = sResult + msStartOptions + "\n";
		// }
		// 请选择
		if ("true".equalsIgnoreCase(attachOption)) {
			sResult.append("<option value=\"\" >--请选择--</option>\n");
		}
		sResult.append(sOption);
		// if (msEndOptions != null && !"".equals(msEndOptions)) {
		// sResult = sResult + msEndOptions + "\n";
		// }
		sResult.append("</select>");
		return sResult.toString();
	}

	/**
	 * 把List中的数据格式化成形如<option value='xx'>xxx</option>
	 * 
	 * @param dataList
	 * @return <option value='xx'>xxx</option>
	 */ 
	private String generateOptionsFromList(List<CodeTableDTO> dataList,boolean addCodeIdToSelect) {

		StringBuffer result = new StringBuffer();
		for (int i = 0; dataList != null && i < dataList.size(); i++) {
			CodeTableDTO codeTableDTO = (CodeTableDTO) dataList.get(i);
			result.append("<option value=\"").append(codeTableDTO.getCodeId())
					.append("\"");
			// 是否被选中
			if (codeTableDTO.getCodeId().equalsIgnoreCase(selectedValue)) {
				result.append(" selected ");
			}
			result.append(">");
			if( addCodeIdToSelect)
				result.append("["+codeTableDTO.getCodeId()+"]");
			result.append(codeTableDTO.getDescription()).append("</option>\n");
		}

		return result.toString();
	}

	/**
	 * 两个文本框，第一个输入代码，第二个显示描述，无缓冲
	 * 
	 * @param pFieldName 	：页面上该对象的名字
	 * @param pTableName 	：代码表名字
	 * @param pSelectedValue ：缺省选中的值
	 * @param request 		：request对象包含了当前用户的语言选择信息，
	 * @param pWhereClause  ：对代码表进行筛选的where条件
	 * @return 下拉框列表的HTML 代码
	 * @throws Exception
	 */
	private String generateTextBoxStyleCodeList() throws Exception {
		if (selectedValue == null) {
			selectedValue = "";
		}

		boolean bKeepFocus = false;
		if (!"false".equalsIgnoreCase(errFocus)) {
			bKeepFocus = true;
		}

		String newFieldName = fieldName.replaceAll("\\.", "_").replaceAll(
				"\\[", "_").replaceAll("\\]", "_");
		StringBuffer sResult = new StringBuffer();

		sResult.append("<script language=\"javaScript\">\n").append(
				"function "+this.fieldName+"DisplayCodeTableText(codeId,descId) {\n").append(
				"var vObject = document.getElementById(codeId);\n").append(
				"var dObject = document.getElementById(descId);\n").append(
				"var vId = \"\";\n").append("if (vObject) {\n").append(
				"vId = vObject.value;\n").append("if (vId == \"\") {\n")
				.append("if(dObject) {\n").append("dObject.value = \"\";\n")
				.append("}\n").append("}\n");

		List<CodeTableDTO> BocpCodeTableDTOList = codeTableService
				.getCodeTables(codeTableId);
		CodeTableDTO selectedDto = null;
		boolean selectedFlag = false;
		if (selectedValue != null && selectedValue.trim().length() > 0) {
			selectedFlag = true;
		}
		for (int i = 0; i < BocpCodeTableDTOList.size(); i++) {
			CodeTableDTO dto = (CodeTableDTO) BocpCodeTableDTOList
					.get(i);
			if (dto!=null) { 
			   	sResult.append("else if (vId == \"").append(dto.getCodeId())
						.append("\"){\n").append("if(dObject) {\n").append(
								"dObject.value = \"").append(dto.getDescription())
						.append("\";\n").append("}\n").append("}\n");
				if (selectedFlag && dto.getCodeId().equals(selectedValue)) {
					selectedDto = dto;
				}
		  }

		}

		sResult.append("else {\n").append("if(dObject) {\n").append(
				"dObject.value = \"").append("输入代码错误\";\n");

		if (bKeepFocus) {
			sResult.append("window.event.srcElement.focus();\n");
		}

		sResult.append("}\n");

		sResult.append("}\n").append("}\n").append("}\n").append("</script>\n");

		sResult.append("<input type='text' name='").append(newFieldName)
				.append("' id='").append(newFieldName).append("' value='")
				.append(selectedValue).append("' size='").append(displayLength)
				.append("' maxlength='").append(maxLength).append(
						"' onblur=\"javascript:").append(beforeBlur).append(
						this.fieldName+"DisplayCodeTableText('").append(newFieldName).append(
						"','").append(newFieldName).append("_desc');").append(
						onBlur).append("\" ").append(props).append(">\n");

		String strValue = errMsg;
//		if (errMsg != null && errMsg.trim().length() > 0) {
//			strValue = errMsg;
//		} else {
//			strValue = "输入代码错误";
//		}

		if (selectedDto != null) {
			strValue = selectedDto.getDescription();
		}

		if (strValue == null || "null".equalsIgnoreCase(strValue.trim())) {
			strValue = "";
		}

		sResult.append("<input type='text' name='").append(newFieldName)
				.append("_desc' size='").append(descLength).append(
						"'  readonly value=\"").append(
						replaceString("\"", "&quot;", strValue)).append("\">");

		return sResult.toString();

	}

	/**
	 * 把字符串wholeStr中的所有字符串oldStr替换成newStr。
	 *  注意：区别于String对象的 replaceAll(String\u00A0regex,String\u00A0replacement)方法。
	 * replaceAll方法中的regex是一种规则表达式，比如"a*b"表示所有头为a尾为b的字符串。而本方法中oldStr="a*b"就是指这个a*b字符串本身
	 * 
	 * @param oldStr  旧串
	 * @param newStr  新串
	 * @param wholeStr 
	 * @return  wholeStr
	 */
	private String replaceString(String oldStr, String newStr, String wholeStr) {
		if (wholeStr == null)
			return "";

		if (oldStr == null)
			return wholeStr;
		if (newStr == null)
			return wholeStr;
		int start, end;
		StringBuffer result = new StringBuffer();
		result = result.append(wholeStr);
		start = 0;

		while (wholeStr.indexOf(oldStr, start) > -1) {
			start = wholeStr.indexOf(oldStr, start);
			end = start + oldStr.length();
			result.replace(start, end, newStr);
			wholeStr = result.toString();
			start += newStr.length();
		}
		return wholeStr;
	}

	/**
	 * 返回CODE对应的描述字符串
	 * 
	 * @return CODE对应的描述字符串
	 * @throws Exception
	 */
	private String generateDescriptionOnly() throws Exception {
		CodeTableDTO dto = codeTableService.getCodeTable(codeTableId,
				selectedValue);
				
		String desc = null;
		if (dto != null) {
		   desc = dto.getDescription();
		}
		   
		if (desc == null || "null".equalsIgnoreCase(desc.trim()))
			desc = "";
		return desc;
	}
}
