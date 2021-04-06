package persistence;

import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ListOfTransaction lot = new ListOfTransaction();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyList() {
        try {
            ListOfTransaction lot = new ListOfTransaction();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyList.json");
            writer.open();
            writer.write(lot);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyList.json");
            lot = reader.read();
            assertEquals(0, lot.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralList() {
        try {
            ListOfTransaction lot = new ListOfTransaction();


            lot.addTransaction(new Inflow(80000, "Allowance", "RBC account",
                    new Date(4, 2, 2020)));
            lot.addTransaction(new Outflow(30000, "Savings Account", "Shopping Spree",
                    new Date(5, 20, 2020)));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralList.json");
            writer.open();
            writer.write(lot);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralList.json");
            lot = reader.read();
            List<Transaction> transactions = lot.getTransactions();
            assertEquals(2, transactions.size());
            checkTransaction(80000, "Allowance", "RBC account",
                    new Date(4, 2, 2020), transactions.get(0));
            checkTransaction(30000, "Savings Account", "Shopping Spree",
                    new Date(5, 20, 2020), transactions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
