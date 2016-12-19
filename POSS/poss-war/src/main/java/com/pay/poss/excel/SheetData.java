package com.pay.poss.excel;

import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.List;
import java.util.Map;

/**
 * Excel数据封装类
 * Created by songyilin on 2016/10/11.
 */
public class SheetData {
    /**
     * sheet名
     */
    private String sheetName = "data";
    /**
     * 该sheet数据的标题
     */
    private String title;
    /**
     * 该sheet数据的表头
     */
    private List<String> header;
    /**
     * 该sheet数据列的KEY
     */
    private List<String> columns;
    /**
     * 该sheet数据的内容
     */
    private List<Map<String, Object>> data;
    /**
     * 字体名
     */
    private String fontName;
    /**
     * 标题字号
     */
    private short titleFontSize = 14;
    /**
     * 表头字号
     */
    private short headerFontSize = 12;
    /**
     * 内容字号
     */
    private short dataFontSize = 11;
    /**
     * 标题背景颜色
     */
    private short titleCellColor = IndexedColors.WHITE.getIndex();
    /**
     * 表头背景颜色
     */
    private short headerCellColor = IndexedColors.WHITE.getIndex();
    /**
     * 内容背景颜色
     */
    private short dataCellColor = IndexedColors.WHITE.getIndex();
    /**
     * 标题文字颜色
     */
    private short titleFontColor = IndexedColors.BLACK.getIndex();
    /**
     * 表头文字颜色
     */
    private short headerFontColor = IndexedColors.BLACK.getIndex();
    /**
     * 内容文字颜色
     */
    private short dataFontColor = IndexedColors.BLACK.getIndex();
    /**
     * 该sheet数据的表头全格式；
     */
    private List<ExcelBean> excelBeans;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public short getTitleFontSize() {
        return titleFontSize;
    }

    public void setTitleFontSize(short titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    public short getDataFontSize() {
        return dataFontSize;
    }

    public void setDataFontSize(short dataFontSize) {
        this.dataFontSize = dataFontSize;
    }

    public short getTitleCellColor() {
        return titleCellColor;
    }

    public void setTitleCellColor(short titleCellColor) {
        this.titleCellColor = titleCellColor;
    }

    public short getDataCellColor() {
        return dataCellColor;
    }

    public void setDataCellColor(short dataCellColor) {
        this.dataCellColor = dataCellColor;
    }

    public short getTitleFontColor() {
        return titleFontColor;
    }

    public void setTitleFontColor(short titleFontColor) {
        this.titleFontColor = titleFontColor;
    }

    public short getDataFontColor() {
        return dataFontColor;
    }

    public void setDataFontColor(short dataFontColor) {
        this.dataFontColor = dataFontColor;
    }

    public short getHeaderFontSize() {
        return headerFontSize;
    }

    public void setHeaderFontSize(short headerFontSize) {
        this.headerFontSize = headerFontSize;
    }

    public short getHeaderCellColor() {
        return headerCellColor;
    }

    public void setHeaderCellColor(short headerCellColor) {
        this.headerCellColor = headerCellColor;
    }

    public short getHeaderFontColor() {
        return headerFontColor;
    }

    public void setHeaderFontColor(short headerFontColor) {
        this.headerFontColor = headerFontColor;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<String> getColumn() {
        return columns;
    }

    public void setColumn(List<String> columns) {
        this.columns = columns;
    }

    public List<ExcelBean> getExcelBeans() {
        return excelBeans;
    }

    public void setExcelBeans(List<ExcelBean> excelBeans) {
        this.excelBeans = excelBeans;
    }

}
