/**
 *  File: LuceneGenerateServiceImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   terry     Create
 *
 */
package com.pay.lucene.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

import com.pay.lucene.common.util.IndexSearcherFactory;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.lucene.service.LuceneGenerateService;
import com.pay.lucene.synonym.KeywordSynonymEngine;
import com.pay.lucene.synonym.SynonymAnalyzer;
import com.pay.lucene.synonym.SynonymReader;
import com.pay.util.StringUtil;

/**
 * 
 */
public class LuceneGenerateServiceImpl implements LuceneGenerateService {

	private Log logger = LogFactory.getLog(this.getClass());
	private BankBrancheInfoService bankBrancheInfoService;
	private String defaultIndexFilePath;
	private Map<String, String> indexFilePaths;
	private String dataKeyFile;

	@Override
	public void buildIndex(final String bankName) throws Exception {

		String indexFilePath = defaultIndexFilePath;
		if (!StringUtil.isEmpty(bankName)) {
			String value = indexFilePaths.get(bankName);
			if (null != value) {
				indexFilePath = value;
			}
		}
		IndexWriter writer = getIndexWriter(indexFilePath, dataKeyFile);
		// get lucene datas
		List<BankBrancheInfoDto> bankBrancheInfos = getBankBranches(bankName);

		// 删除原有的索引信息，重成
		writer.deleteAll();
		writerIndex(writer, bankBrancheInfos);
		logger.info("doc size:" + writer.maxDoc());
		IndexSearcherFactory.setIndexSearcher(indexFilePath, true);
	}

	public void setBankBrancheInfoService(
			BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}

	public void setDataKeyFile(String dataKeyFile) {
		this.dataKeyFile = dataKeyFile;
	}

	public void setDefaultIndexFilePath(String defaultIndexFilePath) {
		this.defaultIndexFilePath = defaultIndexFilePath;
	}

	public void setIndexFilePaths(Map<String, String> indexFilePaths) {
		this.indexFilePaths = indexFilePaths;
	}

	private void writerIndex(IndexWriter writer,
			List<BankBrancheInfoDto> bankBrancheInfos)
			throws CorruptIndexException, IOException {
		Document doc = null;

		if (null != bankBrancheInfos && !bankBrancheInfos.isEmpty()) {
			for (BankBrancheInfoDto bankBrancheInfo : bankBrancheInfos) {

				String bankName = bankBrancheInfo.getBankName();
				String province = bankBrancheInfo.getProvince();
				String city = bankBrancheInfo.getCity();
				String bankNo = bankBrancheInfo.getBankNumber();
				String bankKaihu = bankBrancheInfo.getBankKaihu();

				doc = new Document();
				doc.add(new Field("bankNo", bankNo, Field.Store.YES,
						Field.Index.ANALYZED)); // #2
				doc.add(new Field("bankName", bankName, Field.Store.YES,
						Field.Index.ANALYZED)); // #2
				doc.add(new Field("proviceField", province, Field.Store.YES,
						Field.Index.ANALYZED)); // #2
				doc.add(new Field("cityField", city, Field.Store.YES,
						Field.Index.ANALYZED,
						Field.TermVector.WITH_POSITIONS_OFFSETS)); // #2
				doc.add(new Field("bankKaihu", bankKaihu, Field.Store.YES,
						Field.Index.ANALYZED,
						Field.TermVector.WITH_POSITIONS_OFFSETS)); // #2
				writer.addDocument(doc);
			}
			// 索引优化
			writer.optimize();
			// 关闭
			writer.close(true);
		}
	}

	private IndexWriter getIndexWriter(final String indexPath,
			final String keyWorldFilePath) throws IOException,
			FileNotFoundException, CorruptIndexException,
			LockObtainFailedException {
		KeywordSynonymEngine engine = new KeywordSynonymEngine();
		SynonymReader bs = new SynonymReader(keyWorldFilePath);
		engine.setMap(bs.readConfig());
		SynonymAnalyzer synonymAnalyzer = new SynonymAnalyzer(engine);
		// 索引创建
		File file = new File(indexPath);
		if (!file.exists()) {
			file.mkdir();
		}
		Directory indexDirectory = FSDirectory.open(file);
		IndexWriter writer = new IndexWriter(indexDirectory, synonymAnalyzer,
				true, IndexWriter.MaxFieldLength.UNLIMITED);
		return writer;
	}

	private List<BankBrancheInfoDto> getBankBranches(final String bankName) {
		BankBrancheInfoDto brancheInfo = new BankBrancheInfoDto();
		if (StringUtil.isEmpty(bankName)) {
			brancheInfo.setType(1);
		} else {
			brancheInfo.setType(2);
			brancheInfo.setBankName(bankName);
		}
		brancheInfo.setFlag(1);
		List<BankBrancheInfoDto> bankBrancheInfos = bankBrancheInfoService
				.findByCondition(brancheInfo);
		return bankBrancheInfos;
	}
}
