package rocketServer;

import java.io.IOException;

import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;


public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			try {
				lq.setdRate(RateBLL.getRate(lq.getiCreditScore()));
			} catch (RateException e) {
				sendToAll(e);
			}
			
			try {
				
				if ((RateBLL.getPayment(lq.getdRate(), lq.getiTerm()*12, lq.getdAmount(), 0, false)<=((lq.getIncome()*.36)-lq.getExpenses()) 
					&& (RateBLL.getPayment(lq.getdRate(), lq.getiTerm()*12, lq.getdAmount(), 0, false)<=(lq.getIncome()*.28)))){
				lq.setdPayment(RateBLL.getPayment(lq.getdRate(), lq.getiTerm()*12, lq.getdAmount(), 0, false));
				}
			} catch (Exception e){
				sendToAll(e);
			}
			
			
			
		sendToAll(lq);
		}
	}
}