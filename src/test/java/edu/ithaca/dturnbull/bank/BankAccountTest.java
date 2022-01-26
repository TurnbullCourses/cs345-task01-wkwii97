package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   //valid email
        assertFalse(BankAccount.isEmailValid(""));  //invalid email - empty
        assertFalse(BankAccount.isEmailValid("1@b.com"));   //invalid email - only contains digits
        assertTrue(BankAccount.isEmailValid("a@bc"));   //valid email
        //***  ^  should this be false?  not sure if ".com/.cc/.org, etc." is required***//
        assertFalse(BankAccount.isEmailValid("a@b"));   //invalid email - domain should be at least two characters
        assertTrue(BankAccount.isEmailValid("a-b@c.com"));  //valid email
        assertTrue(BankAccount.isEmailValid("a_b@c.com"));  //valid email
        assertTrue(BankAccount.isEmailValid("a.b@c.com"));  //valid email
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}