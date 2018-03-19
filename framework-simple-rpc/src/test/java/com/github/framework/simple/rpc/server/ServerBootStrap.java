package com.github.framework.simple.rpc.server;

import com.github.framework.simple.rpc.api.PersonService;
import com.github.framework.simple.rpc.domain.Person;
import com.github.framework.simple.rpc.registry.ServiceRegistry;
import com.github.framework.simple.rpc.server.service.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ServerBootStrap {

    private static final Logger logger = LoggerFactory.getLogger(ServerBootStrap.class);

    public static void main(String[] args){
        withoutSpring();
    }

    public static void withoutSpring() {
        String serverAddress = "127.0.0.1:18866";
        ServiceRegistry serviceRegistry = new ServiceRegistry("10.200.6.197:2181");
        RpcServer rpcServer = new RpcServer(serverAddress, serviceRegistry);
        PersonService personService = new PersonServiceImpl();
        rpcServer.addService("com.github.framework.simple.rpc.api.PersonService", personService);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            logger.error("Exception: {}", ex);
        }
    }
}
