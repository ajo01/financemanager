package persistence;

import model.Date;
import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransaction(int amount, String whereFrom, String whereTo, Date date, Transaction t) {
        assertEquals(amount, t.getAmount());
        assertEquals(whereFrom, t.getWhereFrom());
        assertEquals(whereTo, t.getWhereTo());
        assertEquals(date.getDay(), t.getDate().getDay());
        assertEquals(date.getMonth(), t.getDate().getMonth());
        assertEquals(date.getYear(), t.getDate().getYear());
    }
}
