package com.skyguard.monitor.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper(){

        if(objectMapper==null){
            objectMapper = new ObjectMapper();
        }

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS,false);

        return objectMapper;
    }

    public static String toJsonString(Object object){
        String json = null;
        try {
            json = getObjectMapper().writeValueAsString(object);
        }catch(Exception e){
            LOG.error("get json error",e);
        }

        return json;
    }

    public static <T> T toObject(String json,Class<T> clazz){

        T data = null;

        try {
            data = getObjectMapper().readValue(json, clazz);
        }catch(Exception e){
            LOG.error("get object error",e);
        }

        return data;
    }

    public static <T> T getObject(String json, TypeReference<T> typeReference){

        T data = null;

        try {
            data = getObjectMapper().readValue(json, typeReference);
        }catch(Exception e){
            LOG.error("get object error",e);
        }

        return data;
    }


}
