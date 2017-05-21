package rocketDomain;

public class RateDomainModel {

	private int iRateID;
	private int iMinCreditScore;
	private double dInterestRate;
	
	public RateDomainModel()
	{
		
	}

	public int getiRateID() {
		return iRateID;
	}

	public void setiRateID(int iRateID) {
		this.iRateID = iRateID;
	}

	public int getiMinCreditScore() {
		return iMinCreditScore;
	}

	public void setiMinCreditScore(int iMinCreditScore) {
		this.iMinCreditScore = iMinCreditScore;
	}

	public double getdInterestRate() {
		return dInterestRate;
	}

	public void setdInterestRate(double dInterestRate) {
		this.dInterestRate = dInterestRate;
	}
	
	
}
