package lk.swithana.calculator.client;

import lk.swithana.calculator.server.CalculatorServer;
import lk.swithana.calculator.server.CalculatorService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

/**
 * Created by swithana on 2/8/14.
 */
public class CalculatorClient {
    private final Logger logger = Logger.getLogger(CalculatorServer.class);

    private CalculatorClientService clientService;
    private CalculatorService.Client client;

    public CalculatorClient() {
        clientService = new CalculatorClientService();
        client = clientService.init();
    }

    public int add(int a, int b) {
        int result = 0;
        try {
            logger.info("Client: Adding " + a + " and " + b);
            if (client == null)
                logger.info("client is null ...");
            result = client.add(a, b);
            logger.info("Client: Result " + result);

        } catch (TException e) {
            logger.error(e.toString());
        }
        return result;
    }

    public void closeClient() {
        clientService.close();
    }
}
