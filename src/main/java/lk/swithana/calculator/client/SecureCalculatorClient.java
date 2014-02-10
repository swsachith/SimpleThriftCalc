package lk.swithana.calculator.client;

import lk.swithana.calculator.server.CalculatorServer;
import lk.swithana.calculator.server.CalculatorService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

/**
 * Created by swithana on 2/10/14.
 */
public class SecureCalculatorClient {
    private final Logger logger = Logger.getLogger(CalculatorServer.class);

    private CalculatorClientService clientService;
    private CalculatorService.Client client;

    public SecureCalculatorClient() {
        clientService = new CalculatorClientService();
        client = clientService.secure_init();
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
            e.printStackTrace();
        }
        return result;
    }

    public void closeClient() {
        clientService.close();
    }
}
