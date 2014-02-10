package lk.swithana.calculator.server;

import org.apache.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by swithana on 2/10/14.
 */
public class SecureCalculatorServer implements CalculatorService.Iface, Runnable {
    private final Logger logger = Logger.getLogger(SecureCalculatorServer.class);

    private String serverKeyStore;
    private String keystorePassword;

    public SecureCalculatorServer() {

        try {
            //reading the property file
            Properties properties = new Properties();
            InputStream inputStream = this.getClass().getResourceAsStream("/server.properties");
            properties.load(inputStream);

            serverKeyStore = properties.getProperty("server.keystore");
            keystorePassword = properties.getProperty("keystore.password");
        } catch (IOException e) {
            logger.error("Can't read serverProperties " + e.toString());
        }
    }

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
            params.setKeyStore(serverKeyStore, keystorePassword);

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
