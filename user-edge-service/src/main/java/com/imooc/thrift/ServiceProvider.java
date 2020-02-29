package com.imooc.thrift;

import com.imooc.thrift.user.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description: ServiceProvider <br>
 * date: 2020/2/29 19:41 <br>
 *
 * @author K.O <br>
 * @version 1.0 <br>
 */
@Component
public class ServiceProvider {

    @Value(("${thrift.user.ip}"))
    private String ip;

    @Value("${thrift.user.port}")
    private Integer port;

    public UserService.Client getUserService() {
        TSocket socket = new TSocket(ip, port, 3000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        UserService.Client client = new UserService.Client(protocol);
        return client;
    }
}
