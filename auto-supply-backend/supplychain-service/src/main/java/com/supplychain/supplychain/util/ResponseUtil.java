package com.supplychain.supplychain.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    
    public static Map<String, Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "操作成功");
        response.put("data", data);
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
    
    public static Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 500);
        response.put("message", message);
        response.put("data", null);
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
    
    public static Map<String, Object> error(int code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", null);
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
}