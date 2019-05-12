package com.example.xjj.libraryapplication.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public final class GsonUtils {

    public static <T> T parseJSON(String json, Class<T> clazz) throws Exception {
        Gson gson = new Gson();
        T info = gson.fromJson(json, clazz);
        return info;
    }

    /**
     * Type type = new TypeToken&lt;ArrayList&lt;TypeInfo>>(){}.getType();
     * Type所在的包：java.lang.reflect
     * TypeToken所在的包：com.google.gson.reflect.TypeToken
     *
     * @param jsonArr
     * @param type
     * @return
     */
    public static <T> T parseJSONArray(String jsonArr, Type type) {
        Gson gson = new Gson();
        T infos = gson.fromJson(jsonArr, type);
        return infos;
    }


    private GsonUtils() {
    }

    public static Map<String, String> getSortMap() {
        Map<String, String> map = new TreeMap<>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        //升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        return map;
    }
}
