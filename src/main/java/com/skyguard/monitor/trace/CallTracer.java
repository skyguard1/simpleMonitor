package com.skyguard.monitor.trace;

import com.skyguard.monitor.client.EsTransportClient;
import com.skyguard.monitor.client.Transport;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : xingrufei
 * create at:  2020-01-20  16:57
 * @description:
 */
public class CallTracer implements Tracer {

    private LongCounter callStarted = new LongCounter();
    private LongCounter callSucceeded = new LongCounter();
    private LongCounter callFailed = new LongCounter();

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,16,0,TimeUnit.SECONDS,new LinkedBlockingQueue<>(20));


    private Transport esTransportClient = new EsTransportClient();


    private MethodInfo methodInfo;

    public CallTracer(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
    }

    @Override
    public void start() {
       callStarted.add();

    }

    @Override
    public void end(String result) {
       callSucceeded.add();
       methodInfo.setReturnObj(result);
       methodInfo.setStatus("end");
       methodInfo.setMessage("method end");
       ServiceCounter.addSuccessCount();
       sendResult();
    }

    @Override
    public void throwsException(String message) {
       callFailed.add();
       methodInfo.setStatus("exception");
       methodInfo.setMessage(message);
       ServiceCounter.addErrorCount();
       sendResult();
    }

    private void sendResult(){
        threadPoolExecutor.submit(()->{
            esTransportClient.sendResult(methodInfo);
        });
    }

}
