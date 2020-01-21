package com.skyguard.monitor.trace;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author : xingrufei
 * create at:  2020-01-21  10:17
 * @description:
 */
public class SystemInfo {

    private long totalCount;

    private long successCount;

    private long errorCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currentTime;


    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void addTotalCount(){
        this.totalCount += 1;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public void addSuccessCount(){
        this.successCount += 1;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public void addErrorCount(){
        this.errorCount += 1;
    }

    public double getSuccessPercent() {
        if(totalCount==0L){
            return 0;
        }
        return successCount/totalCount;
    }

    public double getErrorPercent() {
        if(totalCount==0L){
            return 0;
        }
        return errorCount/totalCount;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
