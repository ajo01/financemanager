package model;

import exception.InvalidDateInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class ListOfTransactionTest {

    ListOfTransaction testLOT;
    Transaction in1;
    Transaction in2;
    Transaction in3;
    Transaction out3;
    Transaction out4;
    Transaction out5;

    @BeforeEach
    void setup() {
        testLOT = new ListOfTransaction();
        in1 = new Inflow(700000, "Job", "Account", new Date(4, 30, 2020));
        in2 = new Inflow(800000, "Investment", "Account", new Date(5, 7, 2020));
        in3 = new Inflow(40000, "Gift Money", "Account", new Date(5, 11, 2020));
        out3 = new Outflow(200000, "Account", "Rent", new Date(7, 7, 2017));
        out4 = new Outflow(300000, "Account", "Tuition",
                new Date(7, 13, 2017));
        out5 = new Outflow(100000, "Account", "Music Academy",
                new Date(5, 23, 2020));
    }

    @Test
    public void testAddTransaction() {

        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(out3);

        assertEquals(3, testLOT.size());
        assertTrue(testLOT.contains(in1));
        assertTrue(testLOT.contains(in2));
        assertTrue(testLOT.contains(out3));
    }

    @Test
    public void testAddTransactionInflow() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);

        assertEquals(2, testLOT.size());
        assertTrue(testLOT.contains(in1));
    }

    @Test
    public void testAddTransactionOutflow() {
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);

        assertEquals(2, testLOT.size());
        assertTrue(testLOT.contains(out4));
    }

    @Test
    public void testSize() {

        assertEquals(0, testLOT.size());
        testLOT.addTransaction(in1);
        assertEquals(1, testLOT.size());
    }

    @Test
    public void testContains() {

        assertFalse(testLOT.contains(in1));
        testLOT.addTransaction(in1);
        assertTrue(testLOT.contains(in1));
    }

    @Test
    public void testInflowOnly() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);

        assertEquals(3, testLOT.inflowOnly().size());
        assertTrue(testLOT.inflowOnly().contains(in1));
        assertTrue(testLOT.inflowOnly().contains(in2));
        assertTrue(testLOT.inflowOnly().contains(in3));
    }

    @Test
    public void testInflowNone() {
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);

        assertEquals(0, testLOT.inflowOnly().size());
    }

    @Test
    public void testInflowEmpty() {

        assertEquals(0, testLOT.inflowOnly().size());
    }

    @Test
    public void testOutflowOnly() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);

        assertEquals(2, testLOT.outflowOnly().size());
        assertTrue(testLOT.outflowOnly().contains(out3));
        assertTrue(testLOT.outflowOnly().contains(out4));
    }

    @Test
    public void testTotalBalance() {
        assertEquals(0, testLOT.getTotalBalance());

        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(out3);

        assertEquals(1300000, testLOT.getTotalBalance());
    }

    @Test
    public void testTotalBalanceMany() {
        assertEquals(0, testLOT.getTotalBalance());

        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);

        assertEquals(1040000, testLOT.getTotalBalance());
    }

    @Test
    public void testTotalBalanceZero() {
        assertEquals(0, testLOT.getTotalBalance());
    }

    @Test
    public void testTotalBalanceNegative() {
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);
        testLOT.addTransaction(out5);

        assertEquals(-600000, testLOT.getTotalBalance());
    }

    @Test
    public void testGetBalanceAtOutflow() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);


        String monthlyReport;
        monthlyReport = "Inflow: +" + 0 + " Outflow: -" + 500000 + " Monthly Balance: " + -500000;
        try {
            assertEquals(monthlyReport, testLOT.getBalanceAt(7, 2017));
        } catch (InvalidDateInputException e) {
            fail("InvalidDateInputException not expected");
        }
    }

    @Test
    public void testGetBalanceAtInflow() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);


        String monthlyReport;
        monthlyReport = "Inflow: +" + 840000 + " Outflow: -" + 0 + " Monthly Balance: " + 840000;
        try {
            assertEquals(monthlyReport, testLOT.getBalanceAt(5, 2020));
        } catch (InvalidDateInputException e) {
            fail("InvalidDateInputException not expected");
        }
    }

    @Test
    public void testGetBalanceAtNoBalanceInMonth() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);

        String monthlyReport;
        monthlyReport = "Inflow: +" + 0 + " Outflow: -" + 0 + " Monthly Balance: " + 0;
        try {
            assertEquals(monthlyReport, testLOT.getBalanceAt(1, 2020));
        } catch (InvalidDateInputException e) {
            fail("InvalidDateInputException not expected");
        }
    }

    @Test
    public void testGetBalanceAtInOutflow() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);
        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);
        testLOT.addTransaction(out5);

        String monthlyReport;
        monthlyReport = "Inflow: +" + 840000 + " Outflow: -" + 100000 + " Monthly Balance: " + 740000;
        try {
            assertEquals(monthlyReport, testLOT.getBalanceAt(5, 2020));
        } catch (InvalidDateInputException e) {
            fail("InvalidDateInputException not expected");
        }
    }

    @Test
    public void testGetBalanceListHasMonthNoYearMatch() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);
        try {
            testLOT.getBalanceAt(4, 2019);
        } catch (InvalidDateInputException e) {
            fail("InvalidDateInputException not expected");
        }
    }

    @Test
    public void testGetBalanceAtEmptyList() {

        String monthlyReport;
        monthlyReport = "Inflow: +" + 0 + " Outflow: -" + 0 + " Monthly Balance: " + 0;
        try {
            assertEquals(monthlyReport, testLOT.getBalanceAt(5, 2020));
        } catch (InvalidDateInputException e) {
            fail("InvalidDateInputException not expected");
        }
    }

    @Test
    public void testGetBalanceExceptionExpectedMonthLow() {
        testLOT.addTransaction(out3);
        try {
            testLOT.getBalanceAt(0, 2020);
            fail("InvalidDateInputException expected");
        } catch (InvalidDateInputException e) {
            // expected
        }
    }

    @Test
    public void testGetBalanceExceptionExpectedMonthHigh() {
        testLOT.addTransaction(out3);
        try {
            testLOT.getBalanceAt(20, 2020);
            fail("InvalidDateInputException expected");
        } catch (InvalidDateInputException e) {
            // expected
        }
    }

    @Test
    public void testGetBalanceExceptionExpectedYearLow() {
        testLOT.addTransaction(out3);
        try {
            testLOT.getBalanceAt(5, 1020);
            fail("InvalidDateInputException expected");
        } catch (InvalidDateInputException e) {
            // expected
        }
    }

    @Test
    public void testGetBalanceExceptionExpectedYearHigh() {
        testLOT.addTransaction(out3);
        try {
            testLOT.getBalanceAt(5, 2024);
            fail("InvalidDateInputException expected");
        } catch (InvalidDateInputException e) {
            // expected
        }
    }

    @Test
    public void testNotInDebt() {
        assertFalse(testLOT.isInDebt());
    }

    @Test
    public void testInDebt() {

        testLOT.addTransaction(out3);
        testLOT.addTransaction(out4);
        testLOT.addTransaction(out5);

        assertTrue(testLOT.isInDebt());
    }

    @Test
    public void testShowTransaction() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);

        assertEquals(3, testLOT.showTransactions().size());
    }

    @Test
    public void testGetI() {
        testLOT.addTransaction(in1);
        testLOT.addTransaction(in2);
        testLOT.addTransaction(in3);

        assertEquals(in1, testLOT.get(0));
        assertEquals(in2, testLOT.get(1));
    }


}
