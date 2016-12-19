/**
 *  File: IndexSearcherFactory.java
 *  Description:
 *  Copyright 2010 -2011 pay Corporation. All rights reserved.
 *  2011-1-5      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.common.util;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Jason_wang
 * 
 */
public class IndexSearcherFactory {

	private static IndexSearcher indexSearcher = null;
	private static IndexSearcher specialIndexSearcher = null;

	private IndexSearcherFactory() {

	}

	public synchronized static IndexSearcher getIndexSearcherInstance(
			String indexPath, boolean flag) throws CorruptIndexException,
			IOException {
		if (null == indexSearcher) {
			setIndexSearcher(indexPath, flag);
		}
		return indexSearcher;
	}

	public synchronized static IndexSearcher getSpecialIndexSearcherInstance(
			String indexPath, boolean flag) throws CorruptIndexException,
			IOException {

		if (null == specialIndexSearcher) {
			setSpecialIndexSearcher(indexPath, flag);
		}
		return specialIndexSearcher;
	}

	public static void setIndexSearcher(String indexPath, boolean flag)
			throws CorruptIndexException, IOException {

		indexSearcher = new IndexSearcher(
				FSDirectory.open(new File(indexPath)), flag);
	}

	public static void setSpecialIndexSearcher(String indexPath, boolean flag)
			throws CorruptIndexException, IOException {

		specialIndexSearcher = new IndexSearcher(FSDirectory.open(new File(
				indexPath)), flag);
	}

}
