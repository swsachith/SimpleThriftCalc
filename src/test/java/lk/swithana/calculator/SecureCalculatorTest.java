package lk.swithana.calculator;

import lk.swithana.calculator.client.SecureCalculatorClient;
import lk.swithana.calculator.server.SecureCalculatorServer;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by swithana on 2/10/14.
 */
public class SecureCalculatorTest {
    private final Logger logger = Logger.getLogger(SecureCalculatorTest.class);

    private SecureCalculatorClient client;
    private SecureCalculatorServer server;


    @BeforeTest
    public void setUp() {
        server = new SecureCalculatorServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
        client = new SecureCalculatorClient();

    }

    @Test(groups = "secureCalcTester")
    public void add(){
        logger.info("Secure: Adding:  ");
        Assert.assertEquals(client.add(5, 6), 11);
    }
    @AfterTest
    public void cleanUp(){
        client.closeClient();
    }

}
