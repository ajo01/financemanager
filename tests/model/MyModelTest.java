package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Inflow testInflow;
    Inflow testInflow1;
    Outflow testOutflow;
    Outflow testOutflow1;

    Date date1;
    Date date2;
    Date date3;

    @BeforeEach
    void setup() {
        testInflow = new Inflow(50000, "Job", "Account", new Date(4, 2, 2019));
        testInflow1 = new Inflow(80000, "Investment", "Account",
                new Date(12, 31, 2020));
        testOutflow = new Outflow(1000, "Account", "Starbucks",
                new Date(6, 30, 2019));
        testOutflow1 = new Outflow(3000, "Account",
                "Supermarket Groceries", new Date(9, 17, 2020));
    }

    @Test
    public void testGetTypeInflow() {
        assertEquals("Inflow", testInflow.getType());
    }

    @Test
    public void testGetTypeOutflow() {
        assertEquals("Outflow", testOutflow.getType());
    }

    @Test
    public void testGetAmount() {
        assertEquals(50000, testInflow.getAmount());
    }

    @Test
    public void testGetWhereFromInflow() {
        assertEquals("Job", testInflow.getWhereFrom());
    }

    @Test
    public void testGetWhereFromOutflow() {
        assertEquals("Account", testOutflow.getWhereFrom());
    }

    @Test
    public void testGetWhereTo() {

        assertEquals("Supermarket Groceries", testOutflow1.getWhereTo());
    }

    @Test
    public void testGetDate() {
        date1 = new Date(4, 2, 2019);
        date2 = new Date (6, 30, 2019);
        date3 = new Date(12, 31, 2020);

        assertEquals(date1.dateToString(), testInflow.getDate().dateToString());
        assertEquals(date2.dateToString(), testOutflow.getDate().dateToString());
        assertEquals(date3.dateToString(), testInflow1.getDate().dateToString());
    }


}