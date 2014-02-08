package lk.swithana.calculator.client;

import lk.swithana.calculator.server.CalculatorServer;
import lk.swithana.calculator.server.CalculatorService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by swithana on 2/8/14.
 */
public class CalculatorClientService {
    private final Logger logger = Logger.getLogger(CalculatorServer.class);

    private TTransport transport;

    public CalculatorService.Client init(){
        try {
            transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            CalculatorService.Client client = new CalculatorService.Client(protocol);

            return client;
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException x) {
            x.printStackTrace();
        }
        return null;
    }

    public void close(){
        transport.close();
    }
}
