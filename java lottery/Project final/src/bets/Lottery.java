package bets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

public class Lottery {
	//Declare Variables for the class
	
	//setup the treemap possible cash win able in  tree map, this seems to function similar to a 2-D array allowing for setting up the first number to be the amount of matches on each ticket and the second number the number being the amount you can win, orgionally had used an arraylist and read through certain sections of text depending on amount of matches hwoever using a treelist simplifed this and allowed for easier editiing of the balance betwen matching tickets and cash earnable
	private TreeMap<Integer, Integer> possibleCashWinable = new TreeMap<>() ;
	
	//Setup the arraylist to save all informatoin about who has chosen which numbers and there name, formated later as "name has placed bet withg numbers [numbers,] and outputed later as showing the amount of matching tickets, allows for easier outputing and can act as a undefined index, could be done without using arraylist and using indexs however this allows the program to be more effective and less compiucated as well as meaning that any changing to format is consistant through the whole program
	private ArrayList<Bets> informationOnEachBet = new ArrayList<>() ;
	
	//Saves the winning lottery tickets in an arraylist, this allows for the use of a .contain later while comparing the numbers in the bets from the users and the numbers randomly generated for the winning ticket making it easier than the orginal idea of having it as a string and splitting it up into sections by reading numbers through looping
	private ArrayList<Integer> winningLotteryTickets= new ArrayList<>() ;
	
	public TreeMap<String,Integer>numberOfAllowedBetsPerPerson = new TreeMap<>();
	
	public TreeMap<String,Integer>cashEarnedPerPerson = new TreeMap<>();
	
	public TreeMap<String,String>outputingAllInformationAboutBets = new TreeMap<>();

	public  ArrayList<String> allPersonsNames = new ArrayList<>();
	//the maximum number of users that are allowed to place bets, once this number is reached the program will automatically draw the lottery
	public int maxNumberOfAllowedUsers;
	
	//the lowest number that is allowed for a bet
	public int minNumberForBets ;
	
	//the maximum number that is allowed for each bet, combined with minNumberForBets sets the acceptable range for bets
	public int maxNumberForBets ;
	
	//was going to be used to set he maximum allowed bets of 3 per person however after testing it let to issues were if multipl,e people entered duplicate names it would fail

	//was going to be used to set the amount of bets used per person so far but had issues and caused the program to function incorrectly therefor was removed in this verison
	public int numberOfCurrentBetsPerPerson;
	
	//this sets the maximum number of numbers choseable on each ticket, currently set to 6 and if this number is changed it will allow for more or less numbers on each ticket
	public int maxNumberOfNumbersChoseableForBet ;
	
	//This tracks the number of matches on each ticket for each bet, this will be used with the  treemap to find out how much money each person wins.
	public int numberOfMatchesOnEachTicket;
	
	//is the total money going in and out from the amount that each ticket costs and the amount going out fgrom the amount of winnings from the treemap
	public int cashGoingInOrOut ;
	
	//tracks the amount of money made from the tickets
	public int moneyMade;
	
	//tracks the money lost for each ticket if they have matching numbers
	public int moneyLost;
	
	//used to check if the lottery has made a profit, changes the ending output to saying if it made or lost moneyt depending on this boolean
	public boolean profit = true;
	
	//setup the string to output the amount of money going in, going out, and the total profit
	public String outputingTheAmountOfMoneyMadeOrLost;
	
	//used to check if the lottery has been drawn
	public boolean hasLotteryBeenDrawn;
	
	public int costOfTicket;
	
	public boolean firstTimeRound;
	//started from the lotterywithMain, sets the rest of the program up
	public Lottery(){
		//calls the setupvariable method
		setUpVariables() ;	
	}

	private void setUpVariables() {
		//set all the variables for the program to be ready	
		setVariables();
		setUpTreeMap();
		}
	
	private void setVariables() {
		
		//sets max number of allowed users to 20, the draw will happen automatically after this number is reached
		this.maxNumberOfAllowedUsers = 20;
	
		//sets the minimum number for bets, if someone enters anything lekkss than 1 it will now allow the user to enter the number
		this.minNumberForBets = 1 ;
		
		//sets the maximum number allowed for bets, if its above this number it will not be accepted
		this.maxNumberForBets = 50 ;
		
		
		//was orgionally used for the setting the starting amount of bets per person , however currently unused as it caused issues with multiple people entering duplicated names it has been removed
		this.numberOfCurrentBetsPerPerson = 0;
		
		//sets thhe number of nuimbers on each ticket to 6, this limits the used to enter 6 different numbers
		this.maxNumberOfNumbersChoseableForBet = 6 ;
		
		//sets the starting value of matches on each ticket
		this.numberOfMatchesOnEachTicket=0;
		
		//Sets the inital value of cash to 0
		this.cashGoingInOrOut = 0;
		
		//Sets the inital value money made to 0
		this.moneyMade = 0;
		
		//Sets the inital value of money lost to 0
		this.moneyLost = 0; 

		//profit is set to true as the default 
		this.profit=true;
		
		//sets the string to be empty so that it can be editied later
		this.outputingTheAmountOfMoneyMadeOrLost="";
		
		//sets has lotterybeendrawn to false, this variable is just used as a backup to make sure that it is not drawn multiple times
		this.hasLotteryBeenDrawn=false;
	
		this.firstTimeRound=true;
		
		this.costOfTicket=1;
		
		
	}

	private void setUpTreeMap() {
		//set the possible Cash winable for the lottery for having multiple matching numbers in the Tree map, setup similar to a 2-D array
		this.possibleCashWinable.put(1 , 3 );
		this.possibleCashWinable.put(2 , 6 );
		this.possibleCashWinable.put(3 , 25 );
		this.possibleCashWinable.put(4 , 750 );
		this.possibleCashWinable.put(5 , 5000 );
		this.possibleCashWinable.put(6 , 5000000 );	
		this.numberOfAllowedBetsPerPerson.put("",0);
	}

	
	public void startLottery(){
		//the startLottery method allows the user to press a button to make there deicsion, this is recusrive as it is called from both placing bet manually and lucky dip, unless either there is 20 users or if draw lottery is pressed
		
		//the if loop is setup to limit the amount of users that are allowed to place a bet, if the limit is reached it will automattically draw
		if(this.informationOnEachBet.size() == maxNumberOfAllowedUsers && this.hasLotteryBeenDrawn==false){
			
			//outputs  message dialog to the users to let them know that the maximum  number have placed 
			JOptionPane.showMessageDialog(null, "The lottery will now be drawing as the maximum amount of users have entered");
			
			//calls the drawLottery Method
			drawLottery();
		}else{
			
			//this sets up the options that a person can place inside the option dialog
			Object[] options = {"Place Own Bet","Lucky Dip","Draw Lottery!"};
			
			////outputs a number (0,1,2) for the options on screen, this is done to limit a person entering incorrect data!
			int nextOption = JOptionPane.showOptionDialog(null, "What would you like to do? " + "Would you like to place a bet or would you like to Draw the lottery!","What do you want to do now?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[2]);

			//if the first button is pressed
			if (nextOption == 0){
				
				//calls teh placebetManually method
				placeBetManually();
			}
			
			//if the second button is pressed
			if(nextOption == 1){
				
				//calls the placeBetLuckyDip method
				placeBetLuckyDip();
			}
			
			//if the third button is pressed
			if(nextOption == 2){
				
				//calls the drawLottery method
				drawLottery();
			}
		}
	}

	//the placeBetManually method allows the user to enter there name and chosen numbers
	private void placeBetManually() {
		
		//checkingForDuplicates must be kept inside the loop to make sure that it is always set to false
		boolean checkingForDuplicates = false;
		
		//sets the name as string so it can be used as a input
		String name;
		
		//makes sure that the loop is reset every time 
		int loop = 0;
		
		//asks the user to enter there name
		name = JOptionPane.showInputDialog("please enter your name!");
		
		//creates a new object based off the Bets class, called nextBet setting the name to the one just entered
		Bets nextBet = new Bets(name);
		
		if(firstTimeRound==true){
			this.numberOfAllowedBetsPerPerson.put(name, Integer.valueOf(1));
		}else{
			
			while(this.numberOfAllowedBetsPerPerson.containsKey(name) && this.numberOfAllowedBetsPerPerson.get(name).intValue() > 2){ 
				System.out.println(this.numberOfAllowedBetsPerPerson.get(name).intValue());
				name = JOptionPane.showInputDialog("you have already placed three bets please allower another user to play, Please neter a different name");
				nextBet.setPersonsName(name);
			}
			
			if (this.numberOfAllowedBetsPerPerson.containsKey(name)){
				this.numberOfAllowedBetsPerPerson.put(name, (this.numberOfAllowedBetsPerPerson.get(name).intValue()+1));
			System.out.println(this.numberOfAllowedBetsPerPerson.get(name).intValue());
			}else{
				this.numberOfAllowedBetsPerPerson.put(name, Integer.valueOf(1));
				System.out.println(this.numberOfAllowedBetsPerPerson.get(name).intValue());	
			}
		}
		
		while(loop < maxNumberOfNumbersChoseableForBet){
			int numberchosen = Integer.parseInt(JOptionPane.showInputDialog("Please enter your number between 1 and 50"));
			while (numberchosen < minNumberForBets || numberchosen > maxNumberForBets ){
				numberchosen = Integer.parseInt(JOptionPane.showInputDialog("Previous number was outside of allowed range, please enter a number between 1 and 50"));
			}
			if(loop == 0){
				nextBet.chosenNumberForBet(numberchosen);
			}
			if(loop > 0){
				if(nextBet.getPersonsNumbers().contains(numberchosen)== false){
					checkingForDuplicates = false;
					nextBet.chosenNumberForBet(numberchosen);
					
				}else{
					checkingForDuplicates = true;
				}
				while(loop > 0 && checkingForDuplicates == true){
					numberchosen = Integer.parseInt(JOptionPane.showInputDialog("you entered a duplicate number please enter a different number"));
					while (numberchosen < minNumberForBets || numberchosen > maxNumberForBets){
						numberchosen = Integer.parseInt(JOptionPane.showInputDialog("Previous number was outside of allowed range, please enter a number between 1 and 50"));
					}
					if(nextBet.getPersonsNumbers().contains(numberchosen)== false){
						checkingForDuplicates = false;
						nextBet.chosenNumberForBet(numberchosen);
					}else{
						checkingForDuplicates = true;
					}		
				}
			}
			loop=loop+1;
		}
		firstTimeRound = false;
		this.moneyMade=this.moneyMade+costOfTicket;
		this.informationOnEachBet.add(nextBet);
		nextBet.displayDetails();
		startLottery();
	}

	//Does the same as the placeBetManually method but automatically generates the numbers instead.
	private void placeBetLuckyDip() {
		boolean checkingForDuplicates = false;
		String name;
		int loop = 0;
		name = JOptionPane.showInputDialog("please enter your name!");
		Bets nextBet = new Bets(name);
		
		if(firstTimeRound==true){
			this.numberOfAllowedBetsPerPerson.put(name, Integer.valueOf(1));
		}else{
			while(this.numberOfAllowedBetsPerPerson.containsKey(name) && this.numberOfAllowedBetsPerPerson.get(name).intValue() > 2){
				name = JOptionPane.showInputDialog("you have already placed three bets please allower another user to play, Please neter a different name");
				nextBet.setPersonsName(name);
			}
			
			if (this.numberOfAllowedBetsPerPerson.containsKey(name)){
				this.numberOfAllowedBetsPerPerson.put(name, (this.numberOfAllowedBetsPerPerson.get(name).intValue()+1));
			System.out.println(this.numberOfAllowedBetsPerPerson.get(name).intValue());
			}else{
				this.numberOfAllowedBetsPerPerson.put(name, Integer.valueOf(1));
				System.out.println(this.numberOfAllowedBetsPerPerson.get(name).intValue());	
			}
		}
		
		while(loop < maxNumberOfNumbersChoseableForBet){
			int numberchosen =  ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);		
			while (numberchosen < minNumberForBets || numberchosen > maxNumberForBets ){
				numberchosen = ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);
			}
			if(loop == 0){
				nextBet.chosenNumberForBet(numberchosen);
			}
			if(loop > 0){
				if(nextBet.getPersonsNumbers().contains(numberchosen)== false){
					checkingForDuplicates = false;
					nextBet.chosenNumberForBet(numberchosen);
				}else{
					checkingForDuplicates = true;
				}
				while(loop > 0 && checkingForDuplicates == true){
					numberchosen = ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);
					while (numberchosen < minNumberForBets || numberchosen > maxNumberForBets ){
						numberchosen = ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);
					}
					if(nextBet.getPersonsNumbers().contains(numberchosen)== false){
						checkingForDuplicates = false;
						nextBet.chosenNumberForBet(numberchosen);
					}else{
						checkingForDuplicates = true;
					}		
				}
			}
			loop=loop+1;
		}
		this.moneyMade=this.moneyMade+costOfTicket;
		this.informationOnEachBet.add(nextBet);
		nextBet.displayDetails();
		firstTimeRound = false;
		startLottery();
		
	}
//draws the lottery by randomly generating numbers and palces into a arraylist
	private void drawLottery() {
		boolean checkingForDuplicates = false;
		this.hasLotteryBeenDrawn=true;
		int loop = 0;
		
		
		
		
		while(loop < maxNumberOfNumbersChoseableForBet){
			int numberchosen =  ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);		
			while (numberchosen < minNumberForBets || numberchosen > maxNumberForBets ){
				numberchosen = ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);
			}
			if(loop == 0){
				this.winningLotteryTickets.add(numberchosen);
			}
			if(loop > 0){
				if(this.winningLotteryTickets.contains(numberchosen)== false){
					checkingForDuplicates = false;
					this.winningLotteryTickets.add(numberchosen);;
				}else{
					checkingForDuplicates = true;
				}
				while(loop > 0 && checkingForDuplicates == true){
					numberchosen = ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);
					while (numberchosen < minNumberForBets || numberchosen > maxNumberForBets ){
						numberchosen = ThreadLocalRandom.current().nextInt(minNumberForBets, maxNumberForBets + 1);
					}
					if(this.winningLotteryTickets.contains(numberchosen)== false){
						checkingForDuplicates = false;
						this.winningLotteryTickets.add(numberchosen);;
					}else{
						checkingForDuplicates = true;
					}		
				}
			}
			loop=loop+1;
		}
		sortLotteryNumbers();
		displayTheWinningNumbers();
		dealingWithBets();
	}
//sorrys the arraylist into lowest to highest using collections
	private void sortLotteryNumbers() {
		Collections.sort(this.winningLotteryTickets);
	}

	//display the winning lottery numbers
	private void displayTheWinningNumbers() {
		String output = "The winning lottery numbers are \n";
		for(Integer winningLotteryNumbersForDisplay : this.winningLotteryTickets){
			output = output + + winningLotteryNumbersForDisplay+ ",";	
		}
		JOptionPane.showMessageDialog(null, output);		
	}

	
	private void dealingWithBets() {
		findAndDisplayNumberOfMatches();
		profitLossByLottery();
	}
	
	//finds the matching tickets by looping around using informationoneachbet as a index to find each of the matching numbers, calculates how much each person makes and displays it
	private void findAndDisplayNumberOfMatches() {
		
		for(Bets checkingBetsPlaced : this.informationOnEachBet ){
			String outputForDisplayingMatches = "";
			numberOfMatchesOnEachTicket = 0;
			for(Integer numberFromTheBet : checkingBetsPlaced.getPersonsNumbers()){
				if(this.winningLotteryTickets.contains(numberFromTheBet)){
					numberOfMatchesOnEachTicket=numberOfMatchesOnEachTicket+1;
					
				}
			}
			String name = checkingBetsPlaced.getPersonsName();
			outputForDisplayingMatches = checkingBetsPlaced.toString()+ "\n";
			if(allPersonsNames.contains(name)){
				
			}else{
				allPersonsNames.add(name);
			}
			
			if(numberOfMatchesOnEachTicket > 0){
				
				if(this.cashEarnedPerPerson.containsKey(name)){
					int tempcash;
					String tempinfo;
					tempcash=this.cashEarnedPerPerson.get(name);
					
					this.cashEarnedPerPerson.put(name, tempcash+ this.possibleCashWinable.get(Integer.valueOf(numberOfMatchesOnEachTicket)).intValue()); 
					tempinfo = this.outputingAllInformationAboutBets.get(name);
					
					this.outputingAllInformationAboutBets.put(name, tempinfo + "\n" + "you have won with " +numberOfMatchesOnEachTicket + "match(s)");
				
				}else{
					this.cashEarnedPerPerson.put(name, this.possibleCashWinable.get(Integer.valueOf(numberOfMatchesOnEachTicket)).intValue()); 
					this.outputingAllInformationAboutBets.put(name, "\n" + "you have won with " +numberOfMatchesOnEachTicket + "match(s)");
				
				}
				
				outputForDisplayingMatches= outputForDisplayingMatches + "\n their are " +this.numberOfMatchesOnEachTicket + " matching tickets meaning you have won" +this.possibleCashWinable.get(Integer.valueOf(numberOfMatchesOnEachTicket)).intValue();
				this.moneyLost = this.moneyLost+this.possibleCashWinable.get(Integer.valueOf(numberOfMatchesOnEachTicket)).intValue();
				
				}else{
				outputForDisplayingMatches=outputForDisplayingMatches + "sorry you did not get any matches \n";
				if(this.cashEarnedPerPerson.containsKey(name)){
					String tempinfo;
					int tempcash;
					tempinfo = this.outputingAllInformationAboutBets.get(name);
					tempcash=this.cashEarnedPerPerson.get(name);
					
					this.cashEarnedPerPerson.put(name, tempcash+0);
					this.outputingAllInformationAboutBets.put(name,tempinfo + "\n" + "you have no matching Tickets");
				
					
					
				}else{
					this.cashEarnedPerPerson.put(name, 0); 
					this.outputingAllInformationAboutBets.put(name,"you have no matching Tickets");
					
				}
				
			}
			JOptionPane.showMessageDialog(null, outputForDisplayingMatches);
		}
		int amountAroundLoop;
		amountAroundLoop=this.allPersonsNames.size();
		
		
		
		int index = 0;
		String name = "";
		String output = "";
		while (index < amountAroundLoop){
			
		name=this.allPersonsNames.get(index);
		System.out.println(name);
		output=this.outputingAllInformationAboutBets.get(name)+ "" +this.cashEarnedPerPerson.get(name);
		JOptionPane.showMessageDialog(null,name + " here is the results of all of your tickets:" +this.outputingAllInformationAboutBets.get(name)+ "\n" + "you earned a total of £" +this.cashEarnedPerPerson.get(name) );
		index=index+1;
		System.out.println(output);
		}
	}
		//calculates the profit loss, money lost and money earned to display to the user
		private void profitLossByLottery() {
		this.cashGoingInOrOut=this.moneyMade-this.moneyLost;
		this.outputingTheAmountOfMoneyMadeOrLost = "The total amount of money made by lottery through tickets was £" +this.moneyMade + "\n";
		this.outputingTheAmountOfMoneyMadeOrLost = this.outputingTheAmountOfMoneyMadeOrLost + "The amount of money lost by the lottery through winnings was £"+this.moneyLost + "\n";
		if(this.moneyMade > this.moneyLost){
			outputingTheAmountOfMoneyMadeOrLost	= outputingTheAmountOfMoneyMadeOrLost + "The amount of profit made in total by the lottery £"+(this.moneyMade-this.moneyLost) + "\n";		
		}else{
			outputingTheAmountOfMoneyMadeOrLost	= outputingTheAmountOfMoneyMadeOrLost + "The amount of money lost in total by the lottery £"+(this.moneyLost-this.moneyMade)+ "\n";		
		}
	}

		
		//ouputs the previosuly clautlacte
	public String toString() {
		String output = "";
		output=this.outputingTheAmountOfMoneyMadeOrLost;
		return output;
	}

}

