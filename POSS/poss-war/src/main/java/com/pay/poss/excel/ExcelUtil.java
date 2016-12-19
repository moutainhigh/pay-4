package com.pay.poss.excel;


import com.pay.poss.exception.IpayException;
import com.pay.poss.ipayconst.IpayConst;
import com.pay.poss.util.ExcelColNameUtil;
import com.pay.poss.util.IOUtil;
import com.pay.poss.util.IpayUtil;
import com.pay.poss.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel处理共通
 * Created by songyilin on 2016/10/11.
 */
public class ExcelUtil {

    private static final String COLTYPE_TEXT = "text";

    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    public static byte[] GetExcel(SheetData... sheets) throws IpayException {
        ByteArrayOutputStream baos = null;
        try {
            Workbook wb = new XSSFWorkbook();

            for (int i = 0; i < sheets.length; i++) {
                SheetData sheetData = sheets[i];
                createSheet(wb, sheetData);
            }

            baos = new ByteArrayOutputStream(1024 * 1024 * 10);
            wb.write(baos);
            return baos.toByteArray();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IpayException(IpayException.GET_EXCEL_EXCEPTION_MSG);
        } finally {
            IOUtil.closeOutputStream(baos);
        }
    }

    public static List<SheetData> GetData(byte[] data) throws IpayException {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            Workbook workbook = WorkbookFactory.create(bais);
            int count = workbook.getNumberOfSheets();

            List<SheetData> list = new ArrayList<SheetData>();
            for (int i = 0; i < count; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                SheetData sheetData = readSheet(sheet);
                list.add(sheetData);
            }

            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IpayException(IpayException.GET_DATA_EXCETION_MSG);
        }

    }

    private static SheetData readSheet(Sheet sheet) {
        SheetData sheetData = new SheetData();

        int lastRowNum = sheet.getLastRowNum();

        if (lastRowNum < 1) {
            return null;
        }

        // read header
        Row row = sheet.getRow(0);
        int lastColNum = row.getLastCellNum();
        List<String> header = readHeader(row, lastColNum);
        sheetData.setHeader(header);

        // read data
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        sheetData.setData(datas);
        for (int i = 2; i <= lastRowNum; i++) {
            row = sheet.getRow(i);
            Map<String, Object> data = readRow(header, row, lastColNum);
            datas.add(data);
        }

        return sheetData;
    }

    private static List<String> readHeader(Row row, int lastColNum) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < lastColNum; i++) {
            Cell cell = row.getCell(i);
            String value = cell.getStringCellValue();
            list.add(value);
        }
        return list;
    }

    private static Map<String, Object> readRow(List<String> header, Row row,
                                               int lastColNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < lastColNum; i++) {
            Cell cell = row.getCell(i);
            String value = getTextByCellType(cell);
            map.put(header.get(i), value);
        }
        return map;
    }

    private static String getTextByCellType(Cell cell) {
        switch (cell.getCellType()) {
            case 0:
                String num = IpayUtil.toStr(cell.getNumericCellValue());
                String[] numArray = num.split("[.]");
                if (numArray.length == 2 && numArray[1].equals("0")) {
                    num = numArray[0];
                }
                return num;
            case 1:
                return cell.getStringCellValue();
            case 2:
                return IpayUtil.toStr(cell.getCellFormula());
            case 3:
                return null;
            case 4:
                return IpayUtil.toStr(cell.getBooleanCellValue());
            case 5:
                return IpayUtil.toStr(cell.getErrorCellValue());
            default:
                return null;
        }

    }

    private static Sheet createSheet(Workbook wb, SheetData sheetData) {

        Map<String, CellStyle> styles = createStyles(wb, sheetData);

        // create a sheet
        Sheet sheet = wb.createSheet(sheetData.getSheetName());

        // turn off gridlines
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        // the following three statements are required only for HSSF
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short) 1);
        printSetup.setFitWidth((short) 1);

        int rIndex = 0;

        // the title row
        if (!StringUtils.isBlank(sheetData.getTitle())) {
            Row titleRow = sheet.createRow(rIndex++);
            titleRow.setHeightInPoints(80);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(sheetData.getTitle());
            titleCell.setCellStyle(styles.get("title"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$"
                    + indexToColumn(sheetData.getExcelBeans().size()) + "$1"));
        }

        // the header row
        createRowHeader(sheet, sheetData.getExcelBeans(), rIndex++, styles.get("header"));
        setColumnWidth(sheet, sheetData.getExcelBeans());
        createRow(sheet, sheetData, rIndex++, styles);
        createColumns(wb, sheet, sheetData.getExcelBeans().size());

        return sheet;
    }

    /**
     * 设置 整列单元格的类型 为 文字列
     *
     * @param sheet
     * @param column 列数
     */
    private static void createColumns(Workbook wb, Sheet sheet, int column) {
        short textFormat = HSSFDataFormat
                .getBuiltinFormat(IpayConst.CELL_FORMAT_TEXT);
        CellStyle style = wb.createCellStyle();

        style.setDataFormat(textFormat);
        for (int i = 0; i < column; i++) {
            sheet.setDefaultColumnStyle(i, style);
        }
    }

    private static void createRowHeader(Sheet sheet, List<ExcelBean> data, int rIndex,
                                        CellStyle style) {
        Row headerRow = sheet.createRow(rIndex);
        for (int i = 0; i < data.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(data.get(i).getHeaderName());
            cell.setCellStyle(style);
        }
    }


    private static void createRow(Sheet sheet, SheetData sheetData, int rIndex,
                                  Map<String, CellStyle> styles) {
        Map<String, Object> map = null;
        Row headerRow = null;
        Cell cell = null;
        for (int i = 0; i < sheetData.getData().size(); i++) {
            map = sheetData.getData().get(i);
            headerRow = sheet.createRow(rIndex++);
            for (int j = 0; j < sheetData.getExcelBeans().size(); j++) {
                cell = headerRow.createCell(j);
                if ("bigdecimal".equals(sheetData.getExcelBeans().get(j).getStyleNum())) {
                    cell.setCellStyle(styles.get("dataNum"));
                    cell.setCellValue(IpayUtil.toDouble(map.get(sheetData.getExcelBeans().get(j).getColumnKey())));
                } else if ("double".equals(sheetData.getExcelBeans().get(j).getStyleNum())) {
                    cell.setCellStyle(styles.get("dataDouble"));
                    cell.setCellValue(IpayUtil.toDouble(map.get(sheetData.getExcelBeans().get(j).getColumnKey())));
                } else {
                    cell.setCellStyle(styles.get("data"));
                    cell.setCellValue(IpayUtil.toStr(map.get(sheetData.getExcelBeans().get(j).getColumnKey())));
                }
            }
        }

    }

    private static void setColumnWidth(Sheet sheet, List<ExcelBean> data) {
        for (int i = 0; i < data.size(); i++) {
            sheet.setColumnWidth(i, (int) (1.5 * 256)
                    * data.get(i).getHeaderName().getBytes().length);
        }
    }

    /**
     * cell styles used for formatting calendar sheets
     */
    private static Map<String, CellStyle> createStyles(Workbook wb,
                                                       SheetData sheetData) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        DataFormat format = wb.createDataFormat();
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(format.getFormat("@"));

        styles.put("title", style);
        styles.put("header", style);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.setDataFormat((short) BuiltinFormats.getBuiltinFormat("General"));
        style.setDataFormat(format.getFormat("#,##0"));
        styles.put("dataNum", style);

        style = wb.createCellStyle();
        style.setDataFormat((short) BuiltinFormats.getBuiltinFormat("General"));
        style.setDataFormat(format.getFormat("#,##0.00"));
        styles.put("dataDouble", style);

        return styles;
    }

    /**
     * 用于将excel表格中列索引转成列号字母，从A对应1开始
     *
     * @param index 列索引
     * @return 列号
     */
    private static String indexToColumn(int index) {
        index--;
        String column = "";
        do {
            if (column.length() > 0) {
                index--;
            }
            column = ((char) (index % 26 + (int) 'A')) + column;
            index = (int) ((index - index % 26) / 26);
        } while (index > 0);
        return column;
    }

    public static <T>byte[] getExcelByte(Class<T> clazz, List<T> list){
        if(clazz == null || list == null){
            return null;
        }

        try {
            //定义,用于放置Excel内容数据
            SheetData sheet = new SheetData();
            //定义,用于放置Excel的标题头
            List<ExcelBean> beanList = new ArrayList<ExcelBean>();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                beanList.add(new ExcelBean(ExcelColNameUtil.getExcelColName(field.getName()), field.getName(), COLTYPE_TEXT));
            }

            sheet.setExcelBeans(beanList);

            //添加Excel数据
            ArrayList<Map<String, Object>> resList = (ArrayList<Map<String, Object>>) JsonUtil.json2Entity(JsonUtil.entity2Json(list), ArrayList.class);
            sheet.setData(resList);
            return ExcelUtil.GetExcel(sheet);
        } catch (IpayException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    public static byte[] getExcelMapByte(List<Map<String, Object>> list){
        if(list == null || list.size() == 0){
            return null;
        }
        Map<String, Object> colMap = list.get(0);

        try {
            //定义,用于放置Excel内容数据
            SheetData sheet = new SheetData();
            //定义,用于放置Excel的标题头
            List<ExcelBean> beanList = new ArrayList<ExcelBean>();
            Set<Map.Entry<String, Object>> entryseSet=colMap.entrySet();
            for(Map.Entry<String, Object> entry : entryseSet){
                beanList.add(new ExcelBean(ExcelColNameUtil.getExcelColName(entry.getKey()), entry.getKey(), COLTYPE_TEXT));
            }

            sheet.setExcelBeans(beanList);

            //添加Excel数据
            sheet.setData(list);
            return ExcelUtil.GetExcel(sheet);
        } catch (IpayException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

}
