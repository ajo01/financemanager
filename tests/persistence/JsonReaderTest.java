package persistence;

import model.Date;
import model.ListOfTransaction;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfTransaction lot = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyList.json");
        try {
            ListOfTransaction lot = reader.read();
            assertEquals(0, lot.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralList.json");
        try {
            ListOfTransaction lot = reader.read();
            List<Transaction> transactions = lot.getTransactions();
            assertEquals(2, transactions.size());
            checkTransaction(20000, "Part-time job", "Savings Account",
                    new Date(1, 28, 2020), transactions.get(0));
            checkTransaction(3000, "Savings Account", "Meal at buffet",
                    new Date(2, 1, 2020), transactions.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
