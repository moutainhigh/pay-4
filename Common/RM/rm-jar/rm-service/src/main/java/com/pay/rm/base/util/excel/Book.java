/**
 *  File: Book.java
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
public class Book {

    /**
     * Creates an empty book
     *
     */
    public Book() {       
    }
    
    /**
     * Creates an empty book with a name
     * @param bookName
     */
    public Book(String bookName) {
        this.bookName = bookName;
    }
    
    /**
     * Creates a book with sheets
     * @param sheets The sheets
     */
    public Book(List sheets) {
        this.sheets = sheets;
    }
    
    /**
     * Creates a book with name and sheets
     * @param bookName The book name
     * @param sheets The sheets
     */
    public Book(String bookName, List sheets) {
        this.bookName = bookName;
        this.sheets = sheets;
    }
       
    private List sheets;

    private String bookName;

    /**
     * Returns all sheets in current book
     * @return A list of sheets
     */
    public List getSheets() {
        return sheets == null ? sheets = new ArrayList() : sheets;
    }

    /**
     * Sets sheets to a book, and overrides original sheets if any
     * @param sheets A list of sheets
     */
    public void setSheets(List sheets) {
        this.sheets = sheets;
    }

    /**
     * Returns a <code>Sheet</code> in a book
     * @param index The index
     * @return A sheet
     */
    public Sheet getSheet(int index) {
        if (index < 0 || index >= getSheets().size()) {
            throw new IllegalArgumentException();
        }

        return (Sheet) getSheets().toArray()[index];
    }

    /**
     * Adds a new <code>Sheet</code> to the end of a <code>Book</code>
     * @param sheet A new sheet to add
     */
    public void addSheet(Sheet sheet) {
        getSheets().add(sheet);
    }   
   
    /**
     * Returns book name
     * @return Book name
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Sets book name
     * @param bookName The new book name
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    /**
     * Literal representation of a work sheet, seperate by carriage return
     */
    public String toString() {
        String delimit = "\r\n";
        String result = "Book [" + getBookName() + "]" + delimit;
        for (Iterator iter = getSheets().iterator(); iter.hasNext();) {
            result = result + ((Sheet) iter.next()).toString() + delimit;
        }
        return result;
    }  
}
