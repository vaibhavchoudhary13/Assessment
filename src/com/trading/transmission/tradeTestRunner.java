package com.trading.transmission;





import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;




@TestMethodOrder(OrderAnnotation.class)
public class tradeTestRunner {

    tradeFlow tf=new tradeFlow();
    Date todaysDate=Calendar.getInstance ().getTime ();
    SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
    @Test
    void testIfTradeEmpty()
    {

        assertTrue(tf.checkIfTradeEmpty());
    }

    @Test
    void testCheckVersion()
    {

    }

    @Test
    void testAddTrade() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");
        Trade t1=new Trade("T1",1,"CP-1","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t1);

        assertEquals(1,tf.allTrade.size());
    }

    @Test
    @Order(1)
    void testVersionHigh() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");
        Trade t2=new Trade("T2",2,"CP-2","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t2);

       
        Trade t3=new Trade("T2",5,"CP-4","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t3);
        assertEquals("CP-4",tf.allTrade.get("T2").getCounterPartId());
    }
   
    @Test
    @Order(2)
    void testVersionSame() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");
       
        Trade t4=new Trade("T1",1,"CP-2","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t4);
        assertEquals("CP-2",tf.allTrade.get("T1").getCounterPartId());
    }

  
    @Test
    @Order(3)
    void testVersionLow() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");

        Trade t5=new Trade("T3",5,"CP-3","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t5);
        int sizeofList=tf.allTrade.size();

        Trade t6=new Trade("T3",1,"CP-2","B1",maturityDate, todaysDate, 'N');

        assertThrows(Exception.class,()->tf.addTrade(t6),"1 is less than 5");

    }
   
    @Test
    @Order(4)
    void testMaturityGreater() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");

        Trade t7=new Trade("T4",5,"CP-4","B3",maturityDate, todaysDate, 'N');
        tf.addTrade(t7);

        assertEquals(t7,tf.allTrade.get("T4"));

    }

  
    @Test
    @Order(5)
    void testMaurityLower() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2020");
        Trade t8=new Trade("T5",1,"CP-4","B3",maturityDate, todaysDate, 'N');
        tf.addTrade(t8);
        assertNull(tf.allTrade.get("T5"));
    }

   
    @Test
    @Order(6)
    void testMaturityLowerVersionSame() throws Exception
    {

        Date maturityDate1=sd.parse("20/05/2021");
        Trade t9=new Trade("T6",1,"CP-2","B1",maturityDate1, todaysDate, 'N');
        tf.addTrade(t9);
        Date maturityDate=sd.parse("20/05/2021");
        Trade t10=new Trade("T6",1,"CP-2","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t10);
        assertEquals(maturityDate1,tf.allTrade.get("T6").getMaturityDate());
    }

   
    @Test
    @Order(7)
    void testSameMaturity() throws Exception
    {
        Date todaysDate=Calendar.getInstance ().getTime ();
        Trade t11=new Trade("T7",7,"CP-5","B4",todaysDate, todaysDate, 'N');
        tf.addTrade(t11);
        assertNotNull(tf.allTrade.get("T7"));
    }

    @Test
    @Order(8)
    void testMaturitySameVersionMaturityLow() throws Exception
    {

        Date maturityDate=sd.parse("20/05/2021");

        Trade t12=new Trade("T8",1,"CP-3","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t12);
        maturityDate=sd.parse("20/05/2020");
        //Now Adding Another List
        Trade t13=new Trade("T8",5,"CP-2","B1",maturityDate, todaysDate, 'N');
        assertEquals(1,tf.allTrade.get("T8").getVersion());

    }

  
    @Test
    @Order(9)
    void testVersionAndMaturityLow() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");

        Trade t14=new Trade("T9",5,"CP-3","B1",maturityDate, todaysDate, 'N');
        tf.addTrade(t14);

        maturityDate=sd.parse("20/05/2020");
      
        Trade t15=new Trade("T9",1,"CP-2","B1",maturityDate, todaysDate, 'N');
        assertThrows(Exception.class,()->tf.addTrade(t15),"1 is less than 5");

    }

    @Test
    @Order(10)
    void testExpiry() throws ParseException
    {
        Date maturityDate=sd.parse("20/05/2020");
        Trade t16=new Trade("T10",6,"CP-4","B1",maturityDate, todaysDate, 'N');
        tf.allTrade.put("T10",t16); 
        tf.checkExpiredDates();
        assertEquals('Y',tf.allTrade.get("T10").getExpired());
    }
    
    
    void removeAllTrade()
    {
    	tf.allTrade.clear();
    }
    
    @Test
    @Order(11)
    void test1() throws Exception
    {
    	Date maturityDate=sd.parse("20/05/2020");
    	Trade t17=new Trade("T1",1,"CP-1","B1",maturityDate, todaysDate, 'N');
    	tf.addTrade(t17);
    	assertEquals(0, tf.allTrade.size());
    }
    
    
    @Test
    @Order(11)
    void test2() throws Exception
    {
    	Date maturityDate=sd.parse("20/05/2021");
    	Trade t18=new Trade("T2",2,"CP-2","B1",maturityDate, todaysDate, 'N');
    	tf.addTrade(t18);
    	assertEquals(1, tf.allTrade.size());
    }
    
    @Test
    @Order(11)
    void test3() throws Exception
    {
    	Date maturityDate=sd.parse("20/05/2021");
    	Trade t18=new Trade("T2",2,"CP-2","B1",maturityDate, todaysDate, 'N');
    	tf.addTrade(t18);
    	assertEquals(1, tf.allTrade.size());
    	maturityDate=sd.parse("20/05/2021");
    	Date createdDate=sd.parse("14/03/2015");
    	Trade t19=new Trade("T2",1,"CP-2","B1",maturityDate, createdDate, 'N');
    	 assertThrows(Exception.class,()->tf.addTrade(t19));
    }
    @Test
    @Order(12)
    void test4() throws Exception
    {
    	Date maturityDate=sd.parse("20/05/2020");
        Trade t17=new Trade("T1",1,"CP-1","B1",maturityDate, todaysDate, 'N');
        maturityDate=sd.parse("20/05/2021");
        Trade t18=new Trade("T2",2,"CP-2","B1",maturityDate, todaysDate, 'N');
    
        maturityDate=sd.parse("20/05/2020");
        Trade t20=new Trade("T3",3,"CP-3","B2",maturityDate, todaysDate, 'N');
        tf.allTrade.put("T3", t20);
        
        tf.checkExpiredDates();
        assertEquals('Y',tf.allTrade.get("T3").getExpired());
    }



}
