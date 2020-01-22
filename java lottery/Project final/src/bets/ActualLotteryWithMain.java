package bets;

import javax.swing.JOptionPane;

public class ActualLotteryWithMain {
	//i decided to keep the amount of code in the main at minimual, orgionally i had most of the code from inside lottery inside this class however i found out that i was calling to lottery so much that the program could be made similar by just placing the code inside that class
	//similar to how i programed previously with differnet lanuages with the front end having minimal code only final outputs
	public static void main(String[] args) {
		
		//starts a new lottery class which setsup the vairables
		Lottery Lottery = new Lottery();
		
		//calls the startlottery method inside the LotteryClass
		Lottery.startLottery();
		
		//outputs the final informatoin about the money made lost and final profit/loss
		JOptionPane.showMessageDialog(null, Lottery.toString());
	}
}
