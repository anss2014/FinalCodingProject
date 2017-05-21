package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class rate_test {

	int creditRate = 0;
	double interest = 0;

	@Test
	public void rateTest() {

		try {
			interest = RateBLL.getRate(800);
		} catch (RateException e) {
			e.printStackTrace();
		}
		System.out.println(interest);
		assertEquals(interest, 3.5, 0.01);

	}

	@Test
	public void RateExceptionTest() throws Exception {

		ArrayList<RateDomainModel> RateList = RateDAL.getAllRates();

		creditRate = 742;

		try {
			double rate = RateBLL.getRate(creditRate);
		} catch (RateException e) {

			throw e;
		}

	}
}