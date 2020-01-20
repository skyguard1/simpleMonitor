package com.skyguard.monitor.trace;

import java.util.List;

/**
 * @author : xingrufei
 * create at:  2020-01-20  17:06
 * @description:
 */
public class MethodInfo {

    private String className;
    private String methodName;
    private List<String> params;
    private String returnObj;
    private String status;
    private String message;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(String returnObj) {
        this.returnObj = returnObj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MethodInfo buildClassName(String className){
        this.className = className;
        return this;
    }

    public MethodInfo buildMethodName(String methodName){
        this.methodName = methodName;
        return this;
    }

    public MethodInfo buildParams(List<String> params){
        this.params = params;
        return this;
    }

    public MethodInfo buildReturnObj(String returnObj){
        this.returnObj = returnObj;
        return this;
    }

    public MethodInfo buildStatus(String status){
        this.status = status;
        return this;
    }


    public MethodInfo buildMessage(String message){
        this.message = message;
        return this;
    }

}
