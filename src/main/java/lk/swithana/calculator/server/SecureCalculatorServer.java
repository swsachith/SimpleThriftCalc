package lk.swithana.calculator.server;

import org.apache.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by swithana on 2/10/14.
 */
public class SecureCalculatorServer implements CalculatorService.Iface, Runnable {
    private final Logger logger = Logger.getLogger(SecureCalculatorServer.class);

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

    @Override
    public void run() {
        try {
            //for the secure communication
            TSSLTransportFactory.TSSLTransportParameters params = new TSSLTransportFactory.TSSLTransportParameters();
            params.setKeyStore("/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/bin/keystore.jks", "******");

            TServerSocket serverTransport = TSSLTransportFactory.getServerSocket(
                    4030, 10000, InetAddress.getByName("localhost"), params);

            CalculatorService.Processor processor = new CalculatorService.Processor(this);

            TThreadPoolServer.Args args1 = new TThreadPoolServer.Args(serverTransport);
            args1.processor(processor);
            TServer server = new TThreadPoolServer(args1);

            logger.info("Started the secured service successfully...");
            server.serve();
        } catch (TTransportException e) {
            logger.error(e.toString());
        } catch (UnknownHostException e) {
            logger.error(e.toString());
        }
    }

    private void log(String message) {
        logger.info(message);
    }

}
