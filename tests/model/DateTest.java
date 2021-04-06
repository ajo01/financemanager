package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;


public class DateTest {


    private Date date1;
    private Date date2;
    private Date date3;

    private Date date4;
    private Date date5;
    private Date invalidDate6;
    private Date invalidDate7;

    private String strdate4;
    private String strdate5;


    @BeforeEach
    public void setup() {
        date1 = new Date(5, 6, 2018);
        date2 = new Date(6, 7, 2019);
        date3 = new Date(8, 25, 2020);

        strdate4 = "1-5-2019";
        strdate5 = "11-30-2020";
        date4 = new Date(1, 5, 2019);
        date5 = new Date(11, 30, 2020);
        invalidDate6 = new Date(13, 56, 1000);
        invalidDate7 = new Date(2, 31, 2020);

    }

    @Test
    public void testGetDay() {
        assertEquals(6, date1.getDay());
        assertEquals(25, date3.getDay());
    }

    @Test
    public void testGetMonth() {
        assertEquals(5, date1.getMonth());
        assertEquals(8, date3.getMonth());
    }

    @Test
    public void testGetYear() {
        assertEquals(2019, date2.getYear());
        assertEquals(2020, date3.getYear());
    }

    @Test
    public void testIsValidDate() {
        assertTrue(date4.validDate());
    }

    @Test
    public void testIvValidDate() {
        assertFalse(invalidDate6.validDate());
        assertFalse(invalidDate7.validDate());
    }

    @Test
    public void testDateToString() {
        assertEquals(strdate4, date4.dateToString());
        assertEquals(strdate5, date5.dateToString());
        assertEquals("Invalid date", invalidDate6.dateToString());
    }


}
