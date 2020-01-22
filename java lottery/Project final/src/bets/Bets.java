package bets;

import java.util.ArrayList;
import java.util.Collections;


import javax.swing.JOptionPane;

public class Bets {
	//Set variable and arraylist for the enterantes name(which will be used as Unqiue ID) and the numbers that they choose.
	public String personsName;
	private ArrayList<Integer>personsNumbers = new ArrayList<>();
	
	public Bets() {
		//currently empty as the only time it is called as a net bet is with a set name therefor the other bets(string name) is more useful
	}
	
	//calls the setPersonName Method
	public Bets(String name) {
		setPersonsName(name);
	}

	//returns the persons name to whatever is callilng it
	public String getPersonsName() {
		return personsName;
	}
	
	//Sets the persons name to the current bet
	public void setPersonsName(String personsName) {
		this.personsName = personsName;
	}
	
	//returns the arraylist of all the users numbers that they had chosen
	public ArrayList<Integer> getPersonsNumbers() {
		return personsNumbers;
	}
	
	//sets the ArrayList of all the users numbers that they had chosen
	public void setPersonsNumbers(ArrayList<Integer> personsNumbers) {
		this.personsNumbers = personsNumbers;
	}
	
	//
	public void chosenNumberForBet (int numberChosen){
		this.personsNumbers.add(numberChosen);
		sortCollections();
	}
	//sorts the persons numbers going from highest to lowest, not required but makes it look nicer
	public void sortCollections(){
		Collections.sort(this.personsNumbers);
	}
	//calls the tostring and outputs it
	public void displayDetails(){
		String output=toString();
		JOptionPane.showMessageDialog(null, output);
	}
	//shows the persons name and the numbers they chose and returns it to the program
	public String toString() {
		String output = this.personsName + " has placed bets with numbers \n";
		for(Integer PersonsNumberFortoString : this.personsNumbers){
			output = output + PersonsNumberFortoString + ",";	
		}
		return output;		
	}
	
}
