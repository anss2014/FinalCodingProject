package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception {

	private RateDomainModel JudgeRate;
	
	public RateException(RateDomainModel JudgeRate) {
		super();
		this.JudgeRate = JudgeRate;
	}
	
	public RateException() {
		System.out.println("Sorry, we cant accept you with your credit.");
	}
	
	public RateDomainModel getRateModel() {
		return JudgeRate;
	}

}