package edu.ithaca.dturnbull.bank;

import java.math.BigDecimal;


public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address or starting balance is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * 
     * @param amount 
     * returns true if amount is positive and has 2 decimal points or less, false if otherwise
     */
    public static boolean isAmountValid(double amount){
        if (amount < 0){
            return false;
        }
        /* Used the inbuilt java method BigDecimal.scale to see if the number 
        has more than 2 decimals. For zero or positive values, the scale is the 
        number of digits to the right of the decimal point. For negative value, 
        the unscaled value of the number is multiplied by ten to the power of 
        the negation of the scale. 
        */
        else if (BigDecimal.valueOf(amount).scale() > 2){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * amount can't be greater than account balance
     * amount can't be a negative value, it must be positive
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        boolean val = isAmountValid(amount);
        if(val == true){
            if(amount <= balance){
                balance -= amount;
            }
            else {
                throw new InsufficientFundsException("Invalid amount");
            }
            
        }
        else {
            throw new IllegalArgumentException("Amount invalid");
        }
    }

    public static boolean isEmailValid(String email){
        //check for special characters
        for(int i = 0; i < email.length(); i++){
            if (email.charAt(i) == '#' || email.charAt(i) == '!' || email.charAt(i) == '&' || email.charAt(i) == '$' || email.charAt(i) == '%' || email.charAt(i) == '^' || email.charAt(i) == '*'){
                return false;
            }
        }

        //check if first character is a number
        if(email.indexOf("0") == 0 || email.indexOf("1") == 0 || email.indexOf("2") == 0 || email.indexOf("3") == 0 || email.indexOf("4") == 0 || email.indexOf("5") == 0 || email.indexOf("6") == 0 || email.indexOf("7") == 0 || email.indexOf("8") == 0 || email.indexOf("9") == 0){
            return false;
        } //prob an easier way to do this ^

        //check fr @ and .
        if (email.indexOf('@') == -1 || email.indexOf(".") == -1){
            return false;
        }
        
        //check if empty
        if (email.isEmpty() == true){
            return false;
        }
        
        else{
            return true;
        }
    }

    /**
     * 
     * @param amount
     * @post checks if amount is valid then adds it to balance
     */
    public void deposit(double amount){
        boolean val = isAmountValid(amount);
        if (val == true){
            balance += amount;
        }
        else {
            throw new IllegalArgumentException("Error! Invalid Deposit Amount");
        }

    }

    /**
     * @param location,amount
     * @post if amount is valid, will transfer money from current account to location specified by user
     */
    public void transfer(BankAccount location, double amount){
        boolean val = isAmountValid(amount);
        if (val == true){
            this.balance -= amount;
            location.balance += amount;
        }

        else {
            throw new IllegalArgumentException("Invalid Amount");

        }

    }

    

}