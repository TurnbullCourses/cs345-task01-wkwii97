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
        
        BankAccount bankAccount3 = new BankAccount("c@d.com", -20); // *Boarder Case* - Negative Balance in Account
        assertEquals(-20, bankAccount3.getBalance(), 0.001);

        BankAccount bankAccount4 = new BankAccount("d@e.com", 18.20);
        assertEquals(18.20, bankAccount4.getBalance(), 0.001); // *Boarder Case* - Balance with decimal
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); // *Boarder Case* - Insufficient Funds withdrawn 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100)); // *Boarder Case* - Negative amount is being withdrawn
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
    }

    @Test
    void isAmountValidTest() {

        assertTrue(BankAccount.isAmountValid(20.00)); // valid amount - positive amount, 2 or less decimal points
        assertFalse(BankAccount.isAmountValid(-10)); // invalid amount - negative amount *Boarder Case*
        assertFalse(BankAccount.isAmountValid(5.555)); // invalid amount - more than 2 decimal points *Boarder Case*
        assertFalse(BankAccount.isAmountValid(-2.231)); // invalid amount - negative amount, more than 2 decimal places 

    }

}