package lk.swithana.calculator;

import lk.swithana.calculator.client.CalculatorClient;
import lk.swithana.calculator.server.CalculatorServer;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by swithana on 2/8/14.
 */
public class CalculatorTestCase {
    private final Logger logger = Logger.getLogger(CalculatorTestCase.class);

    private CalculatorClient client;
    private CalculatorServer server;


    @BeforeTest
    public void setUp() {
        server = new CalculatorServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
        client = new CalculatorClient();

    }

    @Test(groups = "calcTester")
    public void add(){
        logger.info("inside the add tester: ");
        Assert.assertEquals(client.add(5, 6), 11);
    }
    @AfterTest
    public void cleanUp(){
        client.closeClient();
    }

}
