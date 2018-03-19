package com.github.framework.simple.rpc.client;

import com.github.framework.simple.rpc.api.PersonService;
import com.github.framework.simple.rpc.client.proxy.IAsyncObjectProxy;
import com.github.framework.simple.rpc.domain.Person;
import com.github.framework.simple.rpc.registry.ServiceDiscovery;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class ClientBootStap {

    public static void main(String[] args){
        withoutSpring();
    }

    public static void withoutSpring() {
        ServiceDiscovery serviceDiscovery = new ServiceDiscovery("10.200.6.197:2181");
        final RpcClient rpcClient = new RpcClient(serviceDiscovery);
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            IAsyncObjectProxy client = rpcClient.createAsync(PersonService.class);
            int num = 5;
            RpcFuture helloPersonFuture = client.call("findPerson", "xiaoming", num);
            helloPersonFuture.addCallback(new AsyncRpcCallback() {
                @Override
                public void success(Object result) {
                    List<Person> persons = (List<Person>) result;
                    for (int i = 0; i < persons.size(); ++i) {
                        System.out.println(persons.get(i));
                    }
                    countDownLatch.countDown();
                }

                @Override
                public void fail(Exception e) {
                    System.out.println(e);
                    countDownLatch.countDown();
                }
            });

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rpcClient.stop();

        System.out.println("End");
    }
}
