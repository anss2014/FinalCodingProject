package rocketCode;

import java.io.Serializable;

import eNums.eAction;
import rocketData.LoanRequest;

public class Action implements Serializable {

	private eAction eAction;
	private LoanRequest LoanRequest;
	
	public Action(eNums.eAction eAction) {
		super();
		this.eAction = eAction;
	}

	public eAction geteAction() {
		return eAction;
	}

	public LoanRequest getLoanRequest() {
		return LoanRequest;
	}

	public void setLoanRequest(LoanRequest LoanRequest) {
		this.LoanRequest = LoanRequest;
	}
	
	
	
	
}
