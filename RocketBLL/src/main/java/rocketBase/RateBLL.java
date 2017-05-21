package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore)throws RateException 
	{
		
		//		Call RateDAL.getAllRates... this returns an array of rates
		//		write the code that will search the rates to determine the 
		//		interest rate for the given credit score
		//		hints:  you have to sort the rates...  you can do this by using
		//			a comparator... or by using an OrderBy statement in the HQL
		ArrayList<RateDomainModel> RateList = new ArrayList<RateDomainModel>(RateDAL.getAllRates());
		double interestRate = 0;
		
		for (RateDomainModel rate : RateList) {
		    if (GivenCreditScore == rate.getiMinCreditScore()) {
			    	interestRate = rate.getdInterestRate();	
			    }
		    else if (RateList.contains(GivenCreditScore)) {
		    	continue;
		    } 
		    else {
				throw new RateException(rate);
			}
		
		}
		return interestRate;
		
	}
		
		
	
	
	
	
	
	
	
	
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		double Payment;
		Payment = Math.abs(FinanceLib.pmt(r,n,p,f,t));
		return Payment; 
	}
}
