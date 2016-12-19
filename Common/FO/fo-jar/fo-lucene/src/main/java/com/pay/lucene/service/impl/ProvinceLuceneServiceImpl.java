/**
 *  File: ProvinceLuceneServiceImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-14   terry     Create
 *
 */
package com.pay.lucene.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.ProvinceService;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.ProvinceLuceneService;
import com.pay.lucene.synonym.KeywordSynonymEngine;
import com.pay.lucene.synonym.SynonymAnalyzer;
import com.pay.lucene.synonym.SynonymReader;

/**
 * 
 */
public class ProvinceLuceneServiceImpl implements ProvinceLuceneService {

	/**
	 * 关键字文件
	 */
	private String dataKayFile;
	private String indexPath;
	private Log logger = LogFactory.getLog(ProvinceLuceneServiceImpl.class);
	private ProvinceService provinceService;
	private static List<ProvinceDTO> provinces;
	private static Searcher searcher;

	@Override
	public void buildProvinceIndex() {

		// IndexWriter writer; //用指定的语言分析器构造一个新的写索引器（第3个参数表示是否为追加索引）

		// 关键字
		KeywordSynonymEngine engine = new KeywordSynonymEngine();
		SynonymReader bs = new SynonymReader(dataKayFile);

		// 索引创建
		try {
			engine.setMap(bs.readConfig());
			SynonymAnalyzer synonymAnalyzer = new SynonymAnalyzer(engine);
			IndexWriter writer = new IndexWriter(FSDirectory.open(new File(
					indexPath)), synonymAnalyzer, true,
					IndexWriter.MaxFieldLength.UNLIMITED);

			List<ProvinceDTO> provinces = getProvinces();
			if (null != provinces && !provinces.isEmpty()) {
				for (ProvinceDTO province : provinces) {
					Document doc = new Document();
					doc.add(new Field("provinceName", province
							.getProvincename(), Field.Store.YES,
							Field.Index.ANALYZED));
					doc.add(new Field("provinceCode", String.valueOf(province
							.getProvincecode()), Field.Store.YES,
							Field.Index.ANALYZED));
				}
				// 索引优化
				writer.optimize();
				// 关闭
				writer.close();
			}

		} catch (IOException e) {
			logger.error("build province index error:", e);
		}
	}

	@Override
	public SearchResultInfoDTO searchProvince(String provinceName) {

		SearchResultInfoDTO result = new SearchResultInfoDTO();

		try {
			
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_30,
					new String[] { provinceName },
					new String[] { "provinceName" },
					new BooleanClause.Occur[] { BooleanClause.Occur.MUST },
					new ComplexAnalyzer());
			// 搜索结果使用Hits存储
			Searcher searcher = getSearcher();
			TopDocs hits = searcher.search(query, 1);
			for (int i = 0; i < hits.scoreDocs.length; i++) {
				Document doc = searcher.doc(hits.scoreDocs[i].doc);
				result.setProvinceCode(doc.get("provinceCode"));
				result.setProvinceName(provinceName);
			}
			;
		} catch (IOException e) {
			logger.error("search province index error:", e);
		} catch (ParseException e) {
			logger.error("search province index error:", e);
		}
		return result;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public void setDataKayFile(String dataKayFile) {
		this.dataKayFile = dataKayFile;
	}

	private List<ProvinceDTO> getProvinces() {
		if (null == provinces || provinces.isEmpty()) {
			provinces = provinceService.findAll();
		}
		return provinces;
	}

	private Searcher getSearcher() {
		if (null == searcher) {
			try {
				searcher = new IndexSearcher(FSDirectory.open(new File(
						indexPath)), true); // 查询解析器：使用和索引同样的语言分析器
			} catch (IOException e) {
				logger.error("search error:", e);
			}
		}
		return searcher;
	}
}
