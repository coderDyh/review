package com.xiangqin.web.test;

/**
 * @author dengyh
 * @version 1.0
 * @date 2024/11/20 21:16
 * @description  json 扁平化实现
 * 将嵌套的 JSON 数据（即多层级的 JSON 对象或数组）进行扁平化处理
 * {"key1":{"key1":"key2"},"key2":["key1","key2"]}  输入
 * 输出
 * key1.key1 : key2
 * key2.0 : key1
 * key2.1 : key2
 */



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonFlattener {

    public static Map<String, Object> flattenJson(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return flatten(jsonNode, "");
    }


    public static Map<String, Object> flatten(JsonNode jsonNode, String prefix) throws IOException {
        Map<String, Object> map = new LinkedHashMap<>();
        if (jsonNode.isObject()){
            Iterator<Map.Entry<String, JsonNode>> entries = jsonNode.fields();
            while (entries.hasNext()){
                Map.Entry<String, JsonNode> entry = entries.next();
                String key = prefix + entry.getKey();
                map.putAll(flatten(entry.getValue(), key + "."));
            }
        }else  if (jsonNode.isArray()){
            for (int i = 0; i < jsonNode.size(); i++){
                JsonNode elment = jsonNode.get(i);
                String key = prefix + i;
                map.putAll(flatten(elment, key + "."));
            }
        }else {
            map.put(prefix, jsonNode.textValue());
        }
        return map;
    }

    public static void main(String[] args) {
        String jsonString = "{\"key1\":{\"key1\":\"key2\"},\"key2\":[\"key1\",\"key2\"]}";
        try {
            Map<String, Object> flattenedMap = flattenJson(jsonString);
            for (Map.Entry<String, Object> entry : flattenedMap.entrySet()) {
                String key  = entry.getKey().endsWith(".") == true? entry.getKey().substring(0,entry.getKey().length() - 1) : entry.getKey() ;
                System.out.println(key + " : " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
