package lk.swithana.calculator.server;

import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.log4j.Logger;

/**
 * Created by swithana on 2/8/14.
 */
public class CalculatorServer implements CalculatorService.Iface{
    private final Logger logger = Logger.getLogger(CalculatorServer.class);

    public int add(int a, int b) {
        log(String.format("Add: %d and %d", a, b));
        return a + b;
    }

    public int sub(int a, int b) {
        log(String.format("substract: %d and %d", a, b));
        return a - b;
    }

    public int div(int a, int b) {
        log(String.format("division: %d and %d", a, b));
        return a / b;
    }

    public int mult(int a, int b) {
        log(String.format("multiplication: %d and %d", a, b));
        return a * b;
    }

    public void initialize(){
        try {
            TServerSocket serverTransport = new TServerSocket(9888);
            CalculatorService.Processor processor =
                    new CalculatorService.Processor(this);
            Args args1 = new Args(serverTransport);
            args1.processor(processor);
            TServer server = new TThreadPoolServer(args1);

            System.out.println("Started service successfully...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    private void log(String message){
        logger.info(message);
    }
}
