package com.pay.fundout.securitycheck.chain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.pay.fundout.securitycheck.voter.Voter;

public abstract class AbstractVoterChain implements VoterChain {
	protected Log logger = LogFactory.getLog(getClass());
	protected List<Voter> respository = new LinkedList<Voter>();// 此处用LinkList，保证FIFO
	protected List<String> supportBusiness = new ArrayList<String>();
	protected String descript;

	@Override
	public boolean supports(String busiType) {
		return supportBusiness.contains(busiType);
	}

	@Override
	public Iterator<Voter> iterator() {
		return respository.iterator();
	}

	@Override
	public void addNextVoter(Voter voter) {
		respository.add(voter);
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getDescript() {
		return descript;
	}

	public void registBusiType(String busiType) {
		supportBusiness.add(busiType);
	}

}
