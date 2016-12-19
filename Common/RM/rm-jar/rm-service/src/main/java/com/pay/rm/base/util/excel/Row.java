/**
 *  File: Row.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.rm.base.util.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zliner
 *
 */
public class Row {
	/**
     * Creates a <code>Row</code> with no <code>Cell</code>s
     *
     */
    public Row() {
    }

    /**
     * Creates a <code>Row</code> with specified <code>Cell</code>s
     * @param cells The cells to be added to a row
     */
    public Row(List cells) {
        this.cells = cells;
    }

    private List cells;

    /**
     * Returns <code>Cell</code> from a row
     * @param cellNo The cell index in a row
     * @return The <code>Cell</code> at that position
     */
    public Cell getCell(int cellNo) {
        if (cellNo < 0 || cellNo >= getCells().size()) {
            throw new IllegalArgumentException();
        }
        return (Cell) getCells().toArray()[cellNo];
    }

    /**
     * Adds (appends) a <code>Cell</code> at the end of a row
     * @param cell A new cell to append
     */
    public void addCell(Cell cell) {
        getCells().add(cell);
    }

    /**
     * Returns all cells in a row in data structure of ArrayList
     * @return A list of cells in a row
     */
    public List getCells() {
        return cells == null ? cells = new ArrayList() : cells;
    }

    /**
     * Sets new cells to a row
     * @param cells New cells to set
     */
    public void setCells(List cells) { //by reference
        this.cells = cells;
    }

    /**
     * Literal representation of a <code>Row</code>
     */
    public String toString() {
        String delimit = "; ";
        String result = "";
        for (Iterator it = getCells().iterator(); it.hasNext();) {
            result = result + (Cell) it.next() + delimit;
        }
        return result;
    }
}
