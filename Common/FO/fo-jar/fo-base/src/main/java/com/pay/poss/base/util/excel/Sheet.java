/**
 *  File: Sheet.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.poss.base.util.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author zliner
 *
 */
public class Sheet {
	/**
     * Creates a empty <code>Sheet</code>
     *
     */
    public Sheet() {
    }

    /**
     * Creates a <code>Sheet</code> with a list of rows
     * @param rows The <code>Row</code>s in a sheet
     */
    public Sheet(List rows) {
        this.rows = rows;
    }

    /**
     * Creates a <code>Sheet</code> with a sheet name
     * @param sheetName The sheet name
     */
    public Sheet(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * Creates a <code>Sheet</code> with a sheet name and a list of rows
     * @param sheetName The sheet name
     * @param rows The <code>Row</code>s to be added to sheet
     */
    public Sheet(String sheetName, List rows) {
        this.sheetName = sheetName;
        this.rows = rows;
    }

    private List rows;

    private String sheetName;

    /**
     * Returns a <code>Row</code> at the given index
     * @param rowNo The row index
     * @return A row in the sheet
     */
    public Row getRow(int rowNo) {
        if (rowNo < 0 || rowNo >= getRows().size()) {
            throw new IllegalArgumentException();
        }
        return (Row) getRows().toArray()[rowNo];
    }

    /**
     * Addes a new <code>Row</code> to the end of a sheet
     * @param row A new row to be added
     */
    public void addRow(Row row) {
        getRows().add(row);
    }

    /**
     * Returns the sheet name
     * @return Sheet name
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Sets sheet name
     * @param sheetName New sheet name 
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * Returns all rows in the sheet
     * @return A list of rows
     */
    public List getRows() {
        return rows == null ? rows = new ArrayList() : rows;
    }

    /**
     * Sets rows to the sheet, overwrites original rows if any
     * @param rows A list of rows
     */
    public void setRows(List rows) {
        this.rows = rows;
    }

    /**
     * Override method to return literal representation of a <code>Sheet</code>
     */
    public String toString() {
        String delimit = "\r\n";
        String result = "Sheet [" + getSheetName() + "]" + delimit;
        for (Iterator it1 = getRows().iterator(); it1.hasNext();) {
            result = result + ((Row) it1.next()).toString() + delimit;
        }
        return result;
    }
}
