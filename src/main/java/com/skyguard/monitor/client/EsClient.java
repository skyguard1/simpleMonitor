package com.skyguard.monitor.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author : xingrufei
 * create at:  2020-01-20  18:55
 * @description:
 */
@Component
public class EsClient implements FactoryBean<Client> {


    @Override
    public Client getObject() throws Exception {

        /*
         * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象
         * 用完记得要关闭
         */
        Client client = new PreBuiltTransportClient(Settings.builder().put("cluster.name", "elasticsearch").build())
                .addTransportAddress(new TransportAddress(new InetSocketAddress("localhost", 9300)));

        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
