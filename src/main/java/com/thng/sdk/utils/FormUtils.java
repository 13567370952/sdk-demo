package com.thng.sdk.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class FormUtils {

    @SuppressWarnings("unchecked")
    public static String toForm(Object obj) {
        String result = "";
        String method;
        Object objResult;
        if(obj instanceof Map) {
            for(String key : ((Map<String, Object>) obj).keySet()) {
                result += key + "=" + ((Map<String, Object>) obj).get(key) + "&";
            }
        } else if(obj instanceof TreeMap) {
            for(String key : ((TreeMap<String, Object>) obj).keySet()) {
                result += key + "=" + ((Map<String, Object>) obj).get(key) + "&";
            }
        } else {
            for (Field field : obj.getClass().getDeclaredFields()) {
                method = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                if(field.getName() != null && "serialVersionUID".equals(field.getName())) {
                	continue;
                }
                
                try {
                    objResult = obj.getClass().getMethod(method).invoke(obj);
                    if (objResult == null) {
                        continue;
                    }
                    result += field.getName() + "=" + objResult + "&";
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        if (result.length() > 1) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }
}
