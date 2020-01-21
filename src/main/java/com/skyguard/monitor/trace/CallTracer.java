package com.skyguard.monitor.trace;

import com.skyguard.monitor.client.EsTransportClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : xingrufei
 * create at:  2020-01-20  16:57
 * @description:
 */
public class CallTracer implements Tracer {

    private LongCounter callStarted = new LongCounter();
    private LongCounter callSucceeded = new LongCounter();
    private LongCounter callFailed = new LongCounter();

    @Autowired
    private EsTransportClient esTransportClient;


    private MethodInfo methodInfo;

    public CallTracer(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
    }

    @Override
    public void start() {
       callStarted.add();

    }

    @Override
    public void end() {
       callSucceeded.add();
       methodInfo.setStatus("end");
       methodInfo.buildMessage("method end");
       sendResult();
    }

    @Override
    public void throwsException(String message) {
       callFailed.add();
       methodInfo.setStatus("exception");
       methodInfo.buildMessage(message);
       sendResult();
    }

    private void sendResult(){
       esTransportClient.sendResult(methodInfo);
    }

}
