package com.skyguard.monitor.client;

import com.skyguard.monitor.trace.MethodInfo;
import com.skyguard.monitor.trace.Monitor;
import com.skyguard.monitor.trace.SystemInfo;
import com.skyguard.monitor.util.JsonUtil;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author : xingrufei
 * create at:  2020-01-20  18:48
 * @description:
 */
public class EsTransportClient implements Transport {

    private static final Logger LOG = LoggerFactory.getLogger(Monitor.class);

    private static final String ES_MONITOR_NAME = "monitor";


    private static Client client;

    private Client getClient(){

        if(client==null) {
            /*
             * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象
             * 用完记得要关闭
             */
            client = new PreBuiltTransportClient(Settings.builder().put("cluster.name", "elasticsearch").build())
                    .addTransportAddress(new TransportAddress(new InetSocketAddress("localhost", 9300)));

        }

        return client;
    }

    @Override
    public void sendResult(MethodInfo methodInfo) {

        try {
            String result = JsonUtil.toJsonString(methodInfo);
            Map<String,Object> map = JsonUtil.toObject(result,Map.class);
            getClient().prepareIndex(methodInfo.getServiceName(), ES_MONITOR_NAME).setSource(map).get();
        }catch (Exception e){
            LOG.error("set methodInfo error",e);
        }


    }

    public void sendSystemInfo(SystemInfo systemInfo){

        try {
            String result = JsonUtil.toJsonString(systemInfo);
            Map<String,Object> map = JsonUtil.toObject(result,Map.class);
            getClient().prepareIndex("SYSTEM-INFO", ES_MONITOR_NAME).setSource(map).get();
        }catch (Exception e){
            LOG.error("set methodInfo error",e);
        }

    }


}
