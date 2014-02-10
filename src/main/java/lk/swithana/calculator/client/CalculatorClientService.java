package lk.swithana.calculator.client;

import lk.swithana.calculator.server.CalculatorServer;
import lk.swithana.calculator.server.CalculatorService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by swithana on 2/8/14.
 */
public class CalculatorClientService {
    private final Logger logger = Logger.getLogger(CalculatorService.class);

    private TTransport transport;

    public CalculatorService.Client init() {
        CalculatorService.Client client = null;
        try {
            logger.info("Initializing the client ...");
            transport = new TSocket("localhost", 9888);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            client = new CalculatorService.Client(protocol);

            logger.info("client initiation complete ...");
        } catch (TTransportException e) {
            logger.error(e.toString());
        } catch (TException e) {
            logger.error(e.toString());
        }
        return client;
    }

    public CalculatorService.Client secure_init() {
        CalculatorService.Client client = null;
        try {
            logger.info("Initializing the Secure Client ...");

            TSSLTransportFactory.TSSLTransportParameters params =
                    new TSSLTransportFactory.TSSLTransportParameters();
            params.setTrustStore("/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/bin/truststore.jks", "*******");
            transport = TSSLTransportFactory.getClientSocket("localhost", 4030, 10000, params);

            TProtocol protocol = new TBinaryProtocol(transport);
            client = new CalculatorService.Client(protocol);

            logger.info("Secure client initiation complete ...");
        } catch (TTransportException e) {
            logger.error(e.toString());
        } catch (TException e) {
            logger.error(e.toString());
        }
        return client;
    }

    public void close() {
        transport.close();
    }
}
