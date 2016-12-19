/**
 *  File: Cell.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.rm.base.util.excel;

/**
 * @author zliner
 *
 */
public class Cell {
	/**
     * Creates a <code>Cell</code> with null value
     *
     */
    public Cell() {
    }

    /**
     * Creates a <code>Cell</code> and initializes its value
     * @param value
     */
    public Cell(String value) {
        this.value = value;
    }

    private String value;

    /**
     * Returns cell value
     * @return The value of this cell
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets cell value
     * @param value The cell value of string data type 
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * A literal representation of cell
     */
    public String toString() {
        return value;
    }
}
