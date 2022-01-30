package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);
        
        BankAccount bankAccount2 = new BankAccount("b@c.com", 0);
        assertEquals(0, bankAccount2.getBalance(), 0.001); // *Boarder Case* - No money in account, should return 0

        BankAccount bankAccount3 = new BankAccount("d@e.com", 18.20);
        assertEquals(18.20, bankAccount3.getBalance(), 0.001); // *Boarder Case* - Balance with decimal
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); // *Boarder Case* - Insufficient Funds withdrawn 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100)); // *Boarder Case* - Negative amount is being withdrawn
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-0.001)); // new test 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(10.111)); // new test
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   //valid email
        assertFalse(BankAccount.isEmailValid(""));  //invalid email - empty 
        assertFalse(BankAccount.isEmailValid("1@b.com"));   //invalid email-only contains digits
        assertFalse(BankAccount.isEmailValid("a@bc"));   //invalid email-domain should have .com/.cc/etc - *Boarder case*
        assertFalse(BankAccount.isEmailValid("a@b"));   //invalid email-domain should be at least two characters
        assertTrue(BankAccount.isEmailValid("a-b@c.com"));  //valid email
        assertTrue(BankAccount.isEmailValid("a_b@c.com"));  //valid email
        assertTrue(BankAccount.isEmailValid("a.b@c.com"));  //valid email
        assertFalse(BankAccount.isEmailValid("#@c.com")); //invalid email - speicial char in the prefix - *Boarder Case*
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check sfor exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 20.157)); // new test 
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -1000.00)); // new test 
    }

    @Test
    void isAmountValidTest() {

        assertTrue(BankAccount.isAmountValid(20.00)); // valid amount - positive amount, 2 or less decimal points
        assertFalse(BankAccount.isAmountValid(-10)); // invalid amount - negative amount *Boarder Case*
        assertFalse(BankAccount.isAmountValid(5.555)); // invalid amount - more than 2 decimal points *Boarder Case*
        assertFalse(BankAccount.isAmountValid(-2.231)); // invalid amount - negative amount, more than 2 decimal places 

    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 50);

        bankAccount.deposit(50);
        assertEquals(100, bankAccount.getBalance()); // valid amount 

        bankAccount.deposit(10.50);
        assertEquals(110.50, bankAccount.getBalance()); // valid amount 


        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-20)); // invalid deposit - negative amount *Boarder Case*
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(5.555)); // invalid deposit - more than 3 decimal places *Boarder Case*
    }

    @Test
    void transferTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 50);
        BankAccount bankAccount2 = new BankAccount("b@c.com", 0);


        bankAccount.transfer(bankAccount2, 5);
        assertEquals(45, bankAccount.getBalance()); // valid amount transferred
        assertEquals(5, bankAccount2.getBalance());
        
        bankAccount.transfer(bankAccount2, 40.50);
        assertEquals(4.50, bankAccount.getBalance()); // valid amount transferred 
        assertEquals(45.50, bankAccount2.getBalance());
        
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, -10)); // invalid amount - amount negative *Boarder Case*
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, 0.253)); // invalid amount - amount has > 2 decimals *Boarder Case*
        
        






    }

}