package com.fdw.fdd.tool;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Gson助手
 * Created by kayroc on 2018/8/2
 * Email：kayroc@126.com
 */
public class GsonHelper {

    private static Gson create() {
        return GsonHolder.gson;
    }

    private static class GsonHolder {
        private static Gson gson = new Gson();
    }

    /**
     * 此方法将指定的Json反序列化为指定类的对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     * @throws JsonIOException
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(String json, Class<T> type) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(json, type);
    }

    /**
     * 此方法将指定读取器的Json读取反序列化为指定类型的对象。
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type) {
        return create().fromJson(json, type);
    }

    /**
     * 从JsonReader读取下一个JSON值并将其转换为typeOfT类型的对象
     *
     * @param reader
     * @param typeOfT
     * @param <T>
     * @return
     * @throws JsonIOException
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(reader, typeOfT);
    }

    /**
     * 此方法将指定读取器的Json读取反序列化为指定类的对象
     *
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     * @throws JsonIOException
     */
    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return create().fromJson(json, classOfT);
    }

    /**
     * 此方法将指定读取器的Json读取反序列化为指定类型的对象。
     *
     * @param json
     * @param typeOfT
     * @param <T>
     * @return
     * @throws JsonIOException
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(json, typeOfT);
    }

    /**
     * 此方法将指定对象序列化为其等效的Json表示形式
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return create().toJson(obj);
    }

    /**
     * 此方法将指定对象（包括泛型类型的对象）序列化为其等效的Json表示形式。
     *
     * @param src
     * @param typeOfSrc
     * @return
     */
    public static String toJson(Object src, Type typeOfSrc) {
        return create().toJson(src, typeOfSrc);
    }

}
